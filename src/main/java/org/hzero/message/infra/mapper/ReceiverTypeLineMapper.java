package org.hzero.message.infra.mapper;

import java.util.List;
import org.hzero.message.domain.entity.ReceiverTypeLine;
import io.choerodon.mybatis.common.BaseMapper;

/**
 * 接收者类型用户组Mapper
 *
 * @author minghui.qiu@hand-china.com 2019-06-12 09:03:01
 */
public interface ReceiverTypeLineMapper extends BaseMapper<ReceiverTypeLine> {

	List<ReceiverTypeLine> listReceiveTypeLine(Long receiverTypeId);
}

