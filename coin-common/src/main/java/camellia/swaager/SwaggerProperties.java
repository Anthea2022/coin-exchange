package camellia.swaager;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author anthea
 * @date 2023/10/29 11:14
 */
@Data
@ConfigurationProperties(prefix = "swagger2")
public class SwaggerProperties {
    /**
     * 包扫码的路径
     */
    private String basePackage;

    private String name;

    private String url;

    private String email;

    private String title;

    private String description;

    private String version;

    private String termsOfServiceUrl;


}
