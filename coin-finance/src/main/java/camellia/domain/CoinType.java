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
 * @version 2023/12/23 0:12
 */
@Data
@Table(name = "coin_type")
@NoArgsConstructor
@AllArgsConstructor
public class CoinType {
    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private String description;

    private Byte status;

    @Column(name = "created")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date updateTime;
}
