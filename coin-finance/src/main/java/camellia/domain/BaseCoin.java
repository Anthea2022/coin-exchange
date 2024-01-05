package camellia.domain;

import com.gitee.fastmybatis.annotation.Column;
import com.gitee.fastmybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 11:34
 */
@Table(name = "config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCoin {
    private Long id;

    @NotBlank
    private String type;

    private String code;

    @NotBlank
    private String name;

    @NotBlank
    private String desc;

    @NotNull
    private Long value;

    @Column(name = "created")
    private Date createTime;
}
