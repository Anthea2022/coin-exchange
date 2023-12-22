package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2023/12/9 1:24
 */
@Data
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    /**
     * 自增id
     */
    @ApiModelProperty(value="自增id")
    private Long id;

    /**
     * 用户类型：1-普通用户；2-代理人
     */
    @ApiModelProperty(value="用户类型：1-普通用户；2-代理人")
    private Byte type;

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String username;

    /**
     * 国际电话区号
     */
    @Column(name = "country_code")
    @ApiModelProperty(value="国际电话区号")
    private String countryCode;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    @NotBlank
    private String phone;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 交易密码
     */
    @ApiModelProperty(value="交易密码")
    private String paypassword;

    /**
     * 交易密码设置状态
     */
    @Column(name = "paypass_setting")
    @ApiModelProperty(value="交易密码设置状态")
    private Boolean paypassSetting;

    /**
     * 邮箱
     */
    @Column(name = "email")
    @ApiModelProperty(value="邮箱")
    private String email;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    @ApiModelProperty(value="真实姓名")
    private String realName;

    /**
     * 证件类型:1，身份证；2，军官证；3，护照；4，台湾居民通行证；5，港澳居民通行证；9，其他；
     */
    @Column(name = "id_card_type")
    @ApiModelProperty(value="证件类型:1，身份证；2，军官证；3，护照；4，台湾居民通行证；5，港澳居民通行证；9，其他；")
    private Integer idCardType;

    /**
     * 认证状态：0-未认证；1-初级实名认证；2-高级实名认证
     */
    @Column(name = "auth_status")
    @ApiModelProperty(value="认证状态：0-未认证；1-初级实名认证；2-高级实名认证")
    private Byte authStatus;

    /**
     * Google令牌秘钥
     */
    @Column(name = "ga_secret")
    @ApiModelProperty(value="Google令牌秘钥")
    private String gaSecret;

    /**
     * Google认证开启状态,0,未启用，1启用
     */
    @Column(name = "ga_status")
    @ApiModelProperty(value="Google认证开启状态,0,未启用，1启用")
    private Boolean gaStatus;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    @ApiModelProperty(value="身份证号")
    private String idCard;

    /**
     * 代理商级别
     */
    @ApiModelProperty(value="代理商级别")
    private Integer level;

    /**
     * 认证时间
     */
    @ApiModelProperty(value="认证时间")
    private Date authtime;

    /**
     * 登录数
     */
    @ApiModelProperty(value="登录数")
    private Integer logins;

    /**
     * 状态：0，禁用；1，启用；
     */
    @ApiModelProperty(value="状态：0，禁用；1，启用；")
    private Byte status;

    /**
     * 邀请码
     */
    @Column(name = "invite_code")
    @ApiModelProperty(value="邀请码")
    private String inviteCode;

    /**
     * 邀请关系
     */
    @Column(name = "invite_relation")
    @ApiModelProperty(value="邀请关系")
    private String inviteRelation;

    /**
     * 直接邀请人ID
     */
    @Column(name = "direct_invite_id")
    private String directInviteId;

    /**
     * 0 否 1是  是否开启平台币抵扣手续费
     */
    @Column(name = "is_deductible")
    @ApiModelProperty(value="0 否 1是  是否开启平台币抵扣手续费")
    private Integer isDeductible;

    /**
     * 审核状态,1通过,2拒绝,0,待审核
     */
    @Column(name = "reviews_status")
    @ApiModelProperty(value="审核状态,1通过,2拒绝,0,待审核")
    private Byte reviewsStatus;

    /**
     * 代理商拒绝原因
     */
    @Column(name = "agent_note")
    @ApiModelProperty(value="代理商拒绝原因")
    private String agentNote;

    /**
     * API的KEY
     */
    @Column(name = "access_key_id")
    @ApiModelProperty(value="API的KEY")
    private String accessKeyId;

    /**
     * API的密钥
     */
    @Column(name = "access_key_secret")
    @ApiModelProperty(value="API的密钥")
    private String accessKeySecret;

    /**
     * 引用认证状态id
     */
    @Column(name = "refe_auth_id")
    @ApiModelProperty(value="引用认证状态id")
    private Long refeAuthId;

    /**
     * 修改时间
     */
    @Column(name = "last_update_time")
    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "created")
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}
