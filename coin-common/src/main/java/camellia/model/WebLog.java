package camellia.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author anthea
 * @date 2023/10/29 11:59
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WebLog {
    private String description;

    private String username;

    private Integer spendTime;

    private String basePath;

    private String uri;

    private String url;

    private String ip;

    private String method;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 返回结果
     */
    private Object result;
}
