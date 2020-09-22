package org.hzero.message.domain.repository;

import org.hzero.message.api.dto.MessageEventDTO;
import org.hzero.message.domain.entity.MessageEvent;
import org.hzero.mybatis.base.BaseRepository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * 消息事件资源库
 *
 * @author like.zhang@hand-china.com 2018-09-12 13:59:26
 */
public interface MessageEventRepository extends BaseRepository<MessageEvent> {

    /**
     * 分页查询事件消息
     *
     * @param pageRequest 分页条件
     * @param messageEvent 消息事件
     * @return 事件消息分页数据
     */
    Page<MessageEventDTO> pageMessageEvent(PageRequest pageRequest, MessageEventDTO messageEvent);

}
