package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
    * 实名认证审核信息
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_auth_audit_record")
public class UserAuthAuditRecord {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 对应user_auth_info表code
     */
    @Column(name = "auth_code")
    @ApiModelProperty(value="对应user_auth_info表code")
    private Long authCode;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 状态1同意2拒絕
     */
    @ApiModelProperty(value="状态1同意2拒絕")
    private Byte status;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 当前审核级数
     */
    @ApiModelProperty(value="当前审核级数")
    private Byte step;

    /**
     * 审核人ID
     */
    @Column(name = "audit_user_id")
    @ApiModelProperty(value="审核人ID")
    private Long auditUserId;

    /**
     * 审核人
     */
    @Column(name = "audit_user_name")
    @ApiModelProperty(value="审核人")
    private String auditUserName;

    /**
     * 创建时间
     */
    @Column(name = "created")
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}