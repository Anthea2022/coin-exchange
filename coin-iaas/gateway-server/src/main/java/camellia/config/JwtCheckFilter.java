package camellia.config;

import com.alibaba.fastjson.JSONObject;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author anthea
 * @date 2023/10/28 23:02
 */
@Component
public class JwtCheckFilter implements GlobalFilter, Ordered {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Value("${no.token.urls:/admin/login,/admin/verify_code/get, /admin/verify_code/check, /user/login, /user/verify_code/get, /user/verify_code/check}")
    private Set<String> noRequestTokenURls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 是否需要token
        if (!isRequiredToken(exchange)) {
            return chain.filter(exchange); //直接放心
        }
        // 取出token
        String token = getUserToken(exchange);
        if (StringUtils.isEmpty(token)) {
            return buildNoAuthorizationResult(exchange);
        }
        Boolean hasKey = redisTemplate.hasKey(token);
        if (hasKey!=null && BooleanUtils.isTrue(hasKey)) {
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }

    // 没有token的响应
    private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-Type", "application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "NO AUTHORIZATION");
        jsonObject.put("errorMsg", "Token is Null or error");
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(wrap));
    }

    public boolean isRequiredToken(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        if (noRequestTokenURls.contains(path)) {
            return false;
        }
        return true;
    }

    // 从请求头中获取token
    public String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token == null ? null : token.replace("bearer", "");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
