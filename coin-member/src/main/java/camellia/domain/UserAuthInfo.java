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
    * 实名认证信息
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_auth_info")
public class UserAuthInfo {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 图片地址
     */
    @Column(name = "image_url")
    @ApiModelProperty(value="图片地址")
    private String imageUrl;

    /**
     * 序号：1-身份证正面照；2-身份证反面照；3-手持身份证照片；
     */
    @ApiModelProperty(value="序号：1-身份证正面照；2-身份证反面照；3-手持身份证照片；")
    private Integer serialno;

    /**
     * 更新时间
     */
    @Column(name = "last_update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "created")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 用户每组审核记录唯一标识
     */
    @Column(name = "auth_code")
    @ApiModelProperty(value="用户每组审核记录唯一标识")
    private Long authCode;
}