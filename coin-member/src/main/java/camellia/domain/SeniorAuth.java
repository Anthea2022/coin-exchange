package camellia.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 墨染盛夏
 * @version 2023/12/15 16:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeniorAuth {
    private Long uid;

    /**
     *身份证正面照；2-身份证反面照；3-手持身份证照片；
     */
    @NotBlank
    private String imageUrl1;

    /**
     *身份证正面照；2-身份证反面照；3-手持身份证照片；
     */
    @NotBlank
    private String imageUrl2;

    /**
     *身份证正面照；2-身份证反面照；3-手持身份证照片；
     */
    @NotBlank
    private String imageUrl3;
}
