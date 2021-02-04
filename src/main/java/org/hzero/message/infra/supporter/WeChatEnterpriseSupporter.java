package org.hzero.message.infra.supporter;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hzero.boot.message.entity.WeChatSender;
import org.hzero.message.domain.entity.Message;
import org.hzero.message.infra.exception.SendMessageException;
import org.hzero.message.infra.util.ClearHtmlUtils;
import org.hzero.wechat.enterprise.dto.MessageSendResultDTO;
import org.hzero.wechat.enterprise.dto.TextMessageDTO;
import org.hzero.wechat.enterprise.service.WechatCorpMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import io.choerodon.core.convertor.ApplicationContextHelper;
import io.choerodon.core.exception.CommonException;

/**
 * description
 *
 * @author shuangfei.zhu@hand-china.com 2019/10/25 9:42
 */
public class WeChatEnterpriseSupporter {

    private WeChatEnterpriseSupporter() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatEnterpriseSupporter.class);

    private static final ObjectMapper OBJECT_MAPPER = ApplicationContextHelper.getContext().getBean(ObjectMapper.class);

    private static TextMessageDTO generateMessage(List<String> userList, Message message) {
        TextMessageDTO textMessage = new TextMessageDTO();
        try {
            textMessage.setTouser(buildParam(userList));
            // 处理额外参数
            Map<String, String> map = OBJECT_MAPPER.readValue(message.getSendArgs(), new TypeReference<Map<String, String>>() {
            });
            textMessage.setAgentid(Long.parseLong(map.get(WeChatSender.FIELD_AGENT_ID)));
            if (map.containsKey(WeChatSender.FIELD_PARTY_LIST)) {
                textMessage.setToparty(buildParam(OBJECT_MAPPER.readValue(map.get(WeChatSender.FIELD_PARTY_LIST), new TypeReference<List<String>>() {
                })));
            }
            if (map.containsKey(WeChatSender.FIELD_TAG_LIST)) {
                textMessage.setTotag(buildParam(OBJECT_MAPPER.readValue(map.get(WeChatSender.FIELD_TAG_LIST), new TypeReference<List<String>>() {
                })));
            }
            if (map.containsKey(WeChatSender.FIELD_TAG_LIST)) {
                textMessage.setTotag(buildParam(OBJECT_MAPPER.readValue(map.get(WeChatSender.FIELD_TAG_LIST), new TypeReference<List<String>>() {
                })));
            }
            if (map.containsKey(WeChatSender.FIELD_SAFE)) {
                textMessage.setSafe(Integer.parseInt(map.get(WeChatSender.FIELD_SAFE)));
            }
            textMessage.setMsgtype("text");
            TextMessageDTO.TextBean textBean = new TextMessageDTO.TextBean();
            textBean.setContent(String.format("%s%n %s", message.getSubject(), ClearHtmlUtils.clearHtml(message.getContent())));
            textMessage.setText(textBean);
            return textMessage;
        } catch (Exception e) {
            LOGGER.error("Incorrect parameter format");
            throw new CommonException(e);
        }
    }

    private static String buildParam(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        list.forEach(item -> sb.append(item).append("|"));
        return sb.toString().endsWith("|") ? sb.substring(0, sb.length() - 1) : null;
    }

    /**
     * 发送微信公众号模板消息
     */
    public static void sendMessage(WechatCorpMessageService messageService, String token, List<String> userList, Message message) {
        TextMessageDTO textMessage = generateMessage(userList, message);
        MessageSendResultDTO response = messageService.sendTextMsg(textMessage, token);
        if (response.getErrcode() != 0) {
            throw new SendMessageException(String.format("wechat official message send failed! code: [%s] , message: [%s]", response.getErrcode(), response.getErrmsg()));
        }
    }
}
