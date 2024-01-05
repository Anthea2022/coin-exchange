package camellia.domain.entity;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 20:08
 */
@Table(name = "user_favorite_market")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteMarket {
    private Long id;

    private Byte type;

    @Column(name = "market_id")
    private Long marketId;

    @Column(name = "user_id")
    private Long uid;
}
