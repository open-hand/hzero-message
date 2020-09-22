package org.hzero.message.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.message.domain.entity.MessageEvent;
import org.hzero.mybatis.domian.SecurityToken;
import org.hzero.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.choerodon.mybatis.domain.AuditDomain;

/**
 * 消息事件DTO
 *
 * @author like.zhang@hand-china.com 2018/09/12 14:36
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageEventDTO extends AuditDomain {
    @Encrypt
    private Long messageEventId;
    @ApiModelProperty(value = "事件ID")
    @Encrypt
    private Long eventId;
    private String receiverTypeCode;
    private String templateCode;
    private Integer enabledFlag;
    private String receiverTypeName;
    private String messageTypeCode;
    private String templateName;
    @Encrypt
    private Long tempServerId;
    private String messageName;
    private String messageCode;


    @ApiModelProperty(value = "租户id")
    @NotNull
    private Long tenantId;
    @ApiModelProperty(value = "语言", required = true)
    @NotBlank
    private String lang;
    @ApiModelProperty(value = "短信服务类型编码")
    private String serverTypeCode;

    @ApiModelProperty(value = "消息模板id")
    private Long templateId;
    @ApiModelProperty(value = "接收者类型id")
    @Encrypt
    private Long receiverTypeId;
    @ApiModelProperty(value = "事件编码", required = true)
    @NotBlank
    private String eventCode;

    @Override
    public Class<? extends SecurityToken> associateEntityClass() {
        return MessageEvent.class;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public Long getTempServerId() {
        return tempServerId;
    }

    public void setTempServerId(Long tempServerId) {
        this.tempServerId = tempServerId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getReceiverTypeId() {
        return receiverTypeId;
    }

    public void setReceiverTypeId(Long receiverTypeId) {
        this.receiverTypeId = receiverTypeId;
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

    public Long getMessageEventId() {
        return messageEventId;
    }

    public void setMessageEventId(Long messageEventId) {
        this.messageEventId = messageEventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getReceiverTypeCode() {
        return receiverTypeCode;
    }

    public void setReceiverTypeCode(String receiverTypeCode) {
        this.receiverTypeCode = receiverTypeCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getReceiverTypeName() {
        return receiverTypeName;
    }

    public void setReceiverTypeName(String receiverTypeName) {
        this.receiverTypeName = receiverTypeName;
    }

    public String getMessageTypeCode() {
        return messageTypeCode;
    }

    public void setMessageTypeCode(String messageTypeCode) {
        this.messageTypeCode = messageTypeCode;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
