package camellia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 19:32
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MatchEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(MatchEngineApplication.class, args);
    }
}
