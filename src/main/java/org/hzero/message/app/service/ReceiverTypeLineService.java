package org.hzero.message.app.service;

import java.util.List;
import org.hzero.message.domain.entity.ReceiverTypeLine;
import org.hzero.message.domain.entity.Unit;
import org.hzero.message.domain.entity.UserGroup;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

public interface ReceiverTypeLineService {
	
	Page<ReceiverTypeLine> listReceiverTypeLine(PageRequest pageRequest,Long receiverTypeId);
	
	void deleteUserGroup(List<ReceiverTypeLine> receiverLineList);
	
	List<ReceiverTypeLine> createReceiverTypeLine(long receiverTypeId,List<ReceiverTypeLine> receiverTypeLineList);
	
	Page<UserGroup> listUserGroups(PageRequest pageRequest,long receiverTypeId,String groupName,String groupCode);
	
	Page<Unit> listUnits(PageRequest pageRequest,long receiverTypeId,String unitName,String unitCode);

}
