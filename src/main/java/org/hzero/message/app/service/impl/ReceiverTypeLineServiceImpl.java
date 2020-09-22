package org.hzero.message.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hzero.message.app.service.ReceiverTypeLineService;
import org.hzero.message.domain.entity.ReceiverTypeLine;
import org.hzero.message.domain.entity.Unit;
import org.hzero.message.domain.entity.UserGroup;
import org.hzero.message.domain.repository.ReceiverTypeLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

@Service
public class ReceiverTypeLineServiceImpl implements ReceiverTypeLineService {

    @Autowired
    private ReceiverTypeLineRepository receiverTypeLineRepository;

    @Override
    public void deleteUserGroup(List<ReceiverTypeLine> receiverLineList) {

        if (CollectionUtils.isEmpty(receiverLineList)) {
            return;
        }
        receiverTypeLineRepository.batchDeleteByPrimaryKey(receiverLineList);
    }

    @Override
    public Page<ReceiverTypeLine> listReceiverTypeLine(PageRequest pageRequest, Long receiverTypeId) {
        return receiverTypeLineRepository.listReceiveTypeLine(pageRequest, receiverTypeId);
    }

    @Override
    public List<ReceiverTypeLine> createReceiverTypeLine(long receiverTypeId, List<ReceiverTypeLine> receiverTypeLineList) {

        List<ReceiverTypeLine> oldLineList = receiverTypeLineRepository.select(new ReceiverTypeLine().setReceiverTypeId(receiverTypeId));
        if (!CollectionUtils.isEmpty(oldLineList)) {
            receiverTypeLineList.addAll(oldLineList);
            receiverTypeLineRepository.batchDelete(oldLineList);
        }
        return receiverTypeLineRepository.batchInsert(receiverTypeLineList.stream().distinct().collect(Collectors.toList()));
    }

    @Override
    public Page<UserGroup> listUserGroups(PageRequest pageRequest, long receiverTypeId, String groupName, String groupCode) {
        return receiverTypeLineRepository.listUserGroups(pageRequest, receiverTypeId, groupName, groupCode);
    }

    @Override
    public Page<Unit> listUnits(PageRequest pageRequest, long receiverTypeId, String unitName, String unitCode) {
        return receiverTypeLineRepository.listUnits(pageRequest, receiverTypeId, unitName, unitCode);
    }

}
