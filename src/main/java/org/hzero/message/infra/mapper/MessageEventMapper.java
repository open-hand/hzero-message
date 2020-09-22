package org.hzero.message.infra.mapper;

import java.util.List;

import org.hzero.message.api.dto.MessageEventDTO;
import org.hzero.message.domain.entity.MessageEvent;

import io.choerodon.mybatis.common.BaseMapper;

/**
 * 消息事件Mapper
 *
 * @author like.zhang@hand-china.com 2018-09-12 13:59:26
 */
public interface MessageEventMapper extends BaseMapper<MessageEvent> {

    /**
     * 查询事件消息
     *
     * @param messageEvent 事件消息DTO
     * @return
     */
    List<MessageEventDTO> selectMessageEvents(MessageEventDTO messageEvent);
}
