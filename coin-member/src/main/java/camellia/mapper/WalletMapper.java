package camellia.mapper;

import camellia.domain.Wallet;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 墨染盛夏
 * @version 2023/12/9 0:30
 */
@Repository
public interface WalletMapper extends CrudMapper<Wallet, Long> {
}
