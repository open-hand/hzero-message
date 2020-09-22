package org.hzero.message.domain.repository;

import org.hzero.mybatis.base.BaseRepository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

import org.hzero.message.domain.entity.ReceiverTypeLine;
import org.hzero.message.domain.entity.Unit;
import org.hzero.message.domain.entity.UserGroup;
/**
 * 接收者类型行服务接口
 *
 * @author minghui.qiu@hand-china.com
 * @date 2019-06-12 09:03:01
 */
public interface ReceiverTypeLineRepository extends BaseRepository<ReceiverTypeLine> {

	Page<ReceiverTypeLine> listReceiveTypeLine(PageRequest pageRequest,Long receiverTypeId);
	
	Page<UserGroup> listUserGroups(PageRequest pageRequest,long receiverTypeId,String groupName,String groupCode);
	
	Page<Unit> listUnits(PageRequest pageRequest,long receiverTypeId,String unitName,String unitCode);
}

