package org.hzero.message.infra.repository.impl;

import org.hzero.message.api.dto.MessageEventDTO;
import org.hzero.message.domain.entity.MessageEvent;
import org.hzero.message.domain.repository.MessageEventRepository;
import org.hzero.message.infra.mapper.MessageEventMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * 消息事件 资源库实现
 *
 * @author like.zhang@hand-china.com 2018-09-12 13:59:26
 */
@Component
public class MessageEventRepositoryImpl extends BaseRepositoryImpl<MessageEvent> implements MessageEventRepository {

    @Autowired
    private MessageEventMapper messageEventMapper;

    @Override
    public Page<MessageEventDTO> pageMessageEvent(PageRequest pageRequest, MessageEventDTO messageEvent) {
        return PageHelper.doPageAndSort(pageRequest, () -> messageEventMapper.selectMessageEvents(messageEvent));
    }
}
