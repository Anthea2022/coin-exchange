package camellia.domain.entity;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 10:54
 */
@Table(name = "trade_area")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeArea {
    private Long id;

    @NotBlank
    private String name;

    private String code;

    @NotNull
    private Byte type;

    @NotNull
    @Column(name = "coin_id")
    private Long coinId;

    @NotBlank
    @Column(name = "coin_name")
    private String coinName;

    @NotNull
    private Byte sort;

    @ApiModelProperty("启用1，禁用0")
    private Byte status;

    @Column(name = "base_coin")
    private Long baseCoin;

    @Column(name = "created")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date updateTime;
}
