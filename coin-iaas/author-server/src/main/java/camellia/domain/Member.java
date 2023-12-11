package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
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
public class Member {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 用户类型：1-普通用户；2-代理人
     */
    private Byte type;

    /**
     * 用户名
     */
    private String username;

    /**
     * 国际电话区号
     */
    @Column(name = "country_code")
    private String countryCode;

    /**
     * 手机号
     */
    @NotBlank
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 交易密码
     */
    private String paypassword;

    /**
     * 交易密码设置状态
     */
    @Column(name = "paypass_setting")
    private Boolean paypassSetting;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 证件类型:1，身份证；2，军官证；3，护照；4，台湾居民通行证；5，港澳居民通行证；9，其他；
     */
    @Column(name = "id_card_type")
    private Integer idCardType;

    /**
     * 认证状态：0-未认证；1-初级实名认证；2-高级实名认证
     */
    @Column(name = "auth_status")
    private Byte authStatus;

    /**
     * Google令牌秘钥
     */
    @Column(name = "ga_secret")
    private String gaSecret;

    /**
     * Google认证开启状态,0,未启用，1启用
     */
    @Column(name = "ga_status")
    private Boolean gaStatus;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 代理商级别
     */
    private Integer level;

    /**
     * 认证时间
     */
    private Date authtime;

    /**
     * 登录数
     */
    private Integer logins;

    /**
     * 状态：0，禁用；1，启用；
     */
    private Boolean status;

    /**
     * 邀请码
     */
    @Column(name = "invite_code")
    private String inviteCode;

    /**
     * 邀请关系
     */
    @Column(name = "invite_relation")
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
    private Integer isDeductible;

    /**
     * 审核状态,1通过,2拒绝,0,待审核
     */
    @Column(name = "reviews_status")
    private Integer reviewsStatus;

    /**
     * 代理商拒绝原因
     */
    @Column(name = "agent_note")
    private String agentNote;

    /**
     * API的KEY
     */
    @Column(name = "access_key_id")
    private String accessKeyId;

    /**
     * API的密钥
     */
    @Column(name = "access_key_secret")
    private String accessKeySecret;

    /**
     * 引用认证状态id
     */
    @Column(name = "refe_auth_id")
    private Long refeAuthId;

    /**
     * 修改时间
     */
    @Column(name = "last_update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "created")
    private Date createTime;
}
