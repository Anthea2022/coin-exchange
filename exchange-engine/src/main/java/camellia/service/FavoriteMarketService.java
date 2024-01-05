package camellia.service;

import camellia.domain.entity.FavoriteMarket;
import camellia.mapper.FavoriteMarketMapper;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 墨染盛夏
 * @version 2024/1/3 20:21
 */
@Service
public class FavoriteMarketService extends BaseService<FavoriteMarket, Long, FavoriteMarketMapper> {
    @Autowired
    private FavoriteMarketMapper favoriteMarketMapper;

    public Boolean saveFavorite(FavoriteMarket favoriteMarket) {
        return favoriteMarketMapper.saveIgnoreNull(favoriteMarket) > 0;
    }

    public Boolean deleteFavorite(Query query) {
        return favoriteMarketMapper.deleteByQuery(query) > 0;
    }
}
