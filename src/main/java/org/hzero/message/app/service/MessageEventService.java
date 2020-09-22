package org.hzero.message.app.service;

import org.hzero.message.domain.entity.MessageEvent;

import java.util.List;
import java.util.Map;

/**
 * 消息事件应用服务
 *
 * @author like.zhang@hand-china.com 2018-09-12 13:59:26
 */
public interface MessageEventService {



    /**
     * 根据id删除消息事件
     *
     * @param messageEventId 消息事件id
     */
    void removeMessageEventById(Long messageEventId);

    /**
     * 更新消息事件
     *
     * @param messageEvent 消息事件对象
     * @return
     */
    MessageEvent updateMessageEvent(MessageEvent messageEvent);

    /**
     * 批量删除消息事件
     *
     * @param messageEvents 消息事件列表
     */
    void batchRemove(List<MessageEvent> messageEvents);

    /**
     * 创建消息事件
     * 
     * @param messageEvent 消息事件
     * @return 消息事件
     */
    MessageEvent createMessageEvent(MessageEvent messageEvent);

    /**
     * 发送消息事件
     * 
     * @param eventCode 事件编码
     * @param map args
     * @param tenantId 租户id
     * @param typeCodeList 发送类型编码列表,为空表示发送左右类型消息
     * @param lang 语言编码
     */
    void sendMessageEvent(String eventCode, Map<String, String> map, Long tenantId, List<String> typeCodeList, String lang);
}
