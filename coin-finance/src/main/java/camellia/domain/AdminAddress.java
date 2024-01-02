package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
    * 平台归账手续费等账户
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin_address")
public class AdminAddress {
    /**
     * 编号
     */
    @ApiModelProperty(value="编号")
    private Long id;

    /**
     * 币种Id
     */
    @Column(name = "coin_id")
    @ApiModelProperty(value="币种Id")
    @NotNull
    private Long coinId;

    /**
     * eth keystore
     */
    @ApiModelProperty(value="eth keystore")
    private String keystore;

    /**
     * eth账号密码
     */
    @ApiModelProperty(value="eth账号密码")
    private String pwd;

    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 1:归账(冷钱包地址),2:打款,3:手续费
     */
    @ApiModelProperty(value="1:归账(冷钱包地址),2:打款,3:手续费")
    private Byte status;
}