package camellia.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 墨染盛夏
 * @version 2023/12/28 14:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinParam extends Coin {
    private MultipartFile file;
}
