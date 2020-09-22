package org.hzero.message.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Regexs;
import org.hzero.message.domain.repository.MessageEventRepository;
import org.hzero.message.domain.repository.ReceiverTypeRepository;
import org.hzero.message.domain.repository.TemplateServerRepository;
import org.hzero.message.infra.constant.HmsgConstant;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.util.Assert;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;

/**
 * 消息事件
 *
 * @author like.zhang@hand-china.com 2018-09-12 13:59:26
 */
@ApiModel("消息事件")
@VersionAudit
@ModifyAudit
@Table(name = "hmsg_message_event")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageEvent extends AuditDomain {

    public static final String FIELD_MESSAGE_EVENT_ID = "messageEventId";
    public static final String FIELD_EVENT_ID = "eventId";
    public static final String FIELD_EVENT_CODE = "eventCode";
    public static final String FIELD_RECEIVER_TYPE_CODE = "receiverTypeCode";
    public static final String FIELD_TEMP_SERVER_ID = "tempServerId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";



    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    /**
     * 校验收件者类型和消息模板是否存在
     * <p>
     * 校验消息模板 Code 是否存在
     * * 短信消息校验：租户 + 模板编码 + 语言 + 服务类型编码
     * * 其他消息校验：租户 + 模板编码 + 语言
     * 接收者类型校验
     * * 租户 + 类型code
     *
     * @param receiverTypeRepository   ReceiverTypeRepository
     * @param templateServerRepository TemplateServerRepository
     */
    public void validateReceiverTypeAndMessageTemplate(ReceiverTypeRepository receiverTypeRepository,
                                                       TemplateServerRepository templateServerRepository) {
        ReceiverType receiverType = receiverTypeRepository.selectByPrimaryKey(receiverTypeId);
        if (receiverType == null || receiverType.getTypeCode() == null) {
            throw new CommonException(HmsgConstant.ErrorCode.RECEIVER_TYPE_NOT_EXIST);
        }
        this.receiverTypeCode = receiverType.getTypeCode();

        //校验消息模板
        Assert.notNull(templateServerRepository.selectByPrimaryKey(tempServerId), HmsgConstant.ErrorCode.TEMPLATE_SERVER_NOT_EXIST);
        if (this.tenantId == null) {
            this.tenantId = BaseConstants.DEFAULT_TENANT_ID;
        }
        //校验接受者类型
        if (CollectionUtils.isEmpty(receiverTypeRepository.select(new ReceiverType().setTenantId(this.tenantId).setTypeCode(receiverTypeCode)))) {
            throw new CommonException(HmsgConstant.ErrorCode.RECEIVER_TYPE_NOT_EXIST);
        }
    }

    /**
     * 创建消息事件验证主键
     *
     * @param messageEventRepository MessageEventRepository
     */
    public void validateUniqueIndex(MessageEventRepository messageEventRepository) {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setEventId(eventId);
        messageEvent.setReceiverTypeCode(receiverTypeCode);
        messageEvent.setTempServerId(tempServerId);

        if (CollectionUtils.isNotEmpty(messageEventRepository.select(messageEvent))) {
            throw new CommonException(BaseConstants.ErrorCode.DATA_EXISTS);
        }
    }

    //
    // 数据库字段
    // ------------------------------------------------------------------------------

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long messageEventId;
    @ApiModelProperty(value = "事件ID", required = true)
    @NotNull
    @Encrypt
    private Long eventId;
    @ApiModelProperty(value = "接收者类型代码")
    @Pattern(regexp = Regexs.CODE_UPPER)
    private String receiverTypeCode;
    @ApiModelProperty(value = "消息模板id", required = true)
    @NotNull
    @Encrypt
    private Long tempServerId;
    @ApiModelProperty(value = "是否启用。1启用，0未启用", required = true)
    @NotNull
    private Integer enabledFlag;
    @ApiModelProperty(value = "事件编码", required = true)
    @NotBlank
    @Pattern(regexp = Regexs.CODE_UPPER)
    private String eventCode;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;


    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    @Transient
    private String lang;
    @Transient
    private String serverTypeCode;
    @Transient
    @ApiModelProperty(value = "消息模板代码")
    private String templateCode;
    @Transient
    @ApiModelProperty(value = "接收者类型id", required = true)
    @Encrypt
    private Long receiverTypeId;
    @Transient
    @Encrypt
    private Long templateId;

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Long getReceiverTypeId() {
        return receiverTypeId;
    }

    public void setReceiverTypeId(Long receiverTypeId) {
        this.receiverTypeId = receiverTypeId;
    }

    public Long getTempServerId() {
        return tempServerId;
    }

    public void setTempServerId(Long tempServerId) {
        this.tempServerId = tempServerId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getServerTypeCode() {
        return serverTypeCode;
    }

    public void setServerTypeCode(String serverTypeCode) {
        this.serverTypeCode = serverTypeCode;
    }

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getMessageEventId() {
        return messageEventId;
    }

    public void setMessageEventId(Long messageEventId) {
        this.messageEventId = messageEventId;
    }

    /**
     * @return 事件ID, hpfm_fnd_event.event_id
     */
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * @return 接收者类型代码，hmsg_receiver_type.type_code
     */
    public String getReceiverTypeCode() {
        return receiverTypeCode;
    }

    public void setReceiverTypeCode(String receiverTypeCode) {
        this.receiverTypeCode = receiverTypeCode;
    }

    /**
     * @return 消息模板代码，hmsg_message_template.template_code
     */
    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    /**
     * @return 是否启用。1启用，0未启用
     */
    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }


}
