package camellia.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;


/**
 * @author anthea
 * @date 2023/10/29 10:03
 */
@Order(2)
@EnableResourceServer
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final static String[] matchers = {"/admin/login", "/user/login", "/admin/verify_code/get", "/admin/verify_code/check",
    "/user/verify_code/get", "/user/verify_code/check"};

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .sessionManagement().disable()
                .requestMatchers().antMatchers(matchers)
                .and()
                .authorizeRequests()
                .antMatchers(matchers).permitAll()
                .antMatchers("/**").authenticated()
                .and().headers().cacheControl();
    }

    // 设置公匙
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(jwtTokenStore());
    }

    private TokenStore jwtTokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter());
        return jwtTokenStore;
    }

    @Bean // 放在ioc容器的
    public JwtAccessTokenConverter accessTokenConverter() {
        //resource 验证token（公钥） authorization 产生 token （私钥）
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        String s = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("coinexchange.txt");
            byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            s = new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        tokenConverter.setVerifierKey(s);
        return tokenConverter;
    }
}
