package camellia.domain.dto;

import com.gitee.fastmybatis.annotation.Column;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 墨染盛夏
 * @version 2023/12/22 14:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
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
    private String mobile;

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

    @Column(name = "id_card")
    @ApiModelProperty(value="身份证号")
    private String idCard;
}
