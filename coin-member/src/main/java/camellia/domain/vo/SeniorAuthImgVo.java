package camellia.domain.vo;

import com.gitee.fastmybatis.annotation.Column;

/**
 * @author 墨染盛夏
 * @version 2023/12/22 15:45
 */
public class SeniorAuthImgVo {
    private Integer serialno;

    @Column(name = "image_url")
    private String imgUrl;
}
