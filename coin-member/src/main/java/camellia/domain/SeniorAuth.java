package camellia.domain;

import camellia.domain.vo.SeniorAuthImgVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2023/12/15 16:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeniorAuth {
    private Long uid;

    // 身份证的图片集合
    private List<SeniorAuthImgVo> seniorAuthImgs;
}
