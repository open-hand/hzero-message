package org.hzero.message.app.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.message.app.service.MessageEventService;
import org.hzero.message.app.service.RelSendMessageService;
import org.hzero.message.domain.entity.MessageEvent;
import org.hzero.message.domain.repository.MessageEventRepository;
import org.hzero.message.domain.repository.ReceiverTypeRepository;
import org.hzero.message.domain.repository.TemplateServerRepository;
import org.hzero.message.infra.constant.HmsgConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.choerodon.core.exception.CommonException;

/**
 * 消息事件应用服务默认实现
 *
 * @author like.zhang@hand-china.com 2018-09-12 13:59:26
 */
@Service
public class MessageEventServiceImpl implements MessageEventService {

    @Autowired
    private MessageEventRepository messageEventRepository;
    @Autowired
    private ReceiverTypeRepository receiverTypeRepository;
    @Autowired
    private TemplateServerRepository templateServerRepository;
    @Autowired
    private RelSendMessageService relSendMessageService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageEvent createMessageEvent(MessageEvent messageEvent) {

        messageEvent.validateReceiverTypeAndMessageTemplate(receiverTypeRepository, templateServerRepository);
        messageEvent.validateUniqueIndex(messageEventRepository);
        messageEventRepository.insertSelective(messageEvent);
        return messageEvent;
    }

    @Override
    public void sendMessageEvent(String eventCode, Map<String, String> map, Long tenantId, List<String> typeCodeList, String lang) {
        List<MessageEvent> messageEvents = messageEventRepository.select(MessageEvent.FIELD_EVENT_CODE, eventCode);
        if (CollectionUtils.isNotEmpty(messageEvents)) {
            messageEvents.forEach(messageEvent -> {
                if (messageEvent.getReceiverTypeCode() != null && messageEvent.getTempServerId() != null &&
                        BaseConstants.Flag.YES.equals(messageEvent.getEnabledFlag())) {
                    // 事件消息默认发送到平台下
                    relSendMessageService.relSendMessage(messageEvent.getTempServerId(), lang, typeCodeList, map, messageEvent.getReceiverTypeCode());
                } else {
                    throw new CommonException(HmsgConstant.ErrorCode.MESSAGE_EVENT_NOT_AVAILABLE);
                }
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeMessageEventById(Long messageEventId) {
        MessageEvent messageEvent = messageEventRepository.selectByPrimaryKey(messageEventId);
        Assert.notNull(messageEvent, BaseConstants.ErrorCode.DATA_NOT_EXISTS);
        messageEventRepository.deleteByPrimaryKey(messageEventId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageEvent updateMessageEvent(MessageEvent messageEvent) {
        MessageEvent entity = messageEventRepository.selectByPrimaryKey(messageEvent.getMessageEventId());
        Assert.notNull(entity, BaseConstants.ErrorCode.DATA_NOT_EXISTS);

        messageEvent.setReceiverTypeCode(ObjectUtils.defaultIfNull(messageEvent.getReceiverTypeCode(), entity.getReceiverTypeCode()));
        messageEvent.setTenantId(ObjectUtils.defaultIfNull(messageEvent.getTenantId(), entity.getTenantId()));
        messageEvent.setTempServerId(ObjectUtils.defaultIfNull(messageEvent.getTempServerId(), entity.getTempServerId()));

        messageEvent.validateReceiverTypeAndMessageTemplate(receiverTypeRepository, templateServerRepository);
        messageEventRepository.updateOptional(messageEvent, MessageEvent.FIELD_ENABLED_FLAG,
                MessageEvent.FIELD_RECEIVER_TYPE_CODE, MessageEvent.FIELD_TEMP_SERVER_ID);
        return messageEvent;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchRemove(List<MessageEvent> messageEvents) {
        if (!CollectionUtils.isEmpty(messageEvents)) {
            messageEvents.forEach(messageEvent -> {
                MessageEvent select = new MessageEvent();
                try {
                    select.setEventId(messageEvent.getEventId());
                    select.setReceiverTypeCode(messageEvent.getReceiverTypeCode());
                    select.setTemplateCode(messageEvent.getTemplateCode());
                } catch (Exception e) {
                    throw new CommonException(BaseConstants.ErrorCode.DATA_INVALID, e);
                }

                if (CollectionUtils.isNotEmpty(messageEventRepository.select(select))) {
                    messageEventRepository.delete(messageEvent);
                }
            });
        }
    }
}
