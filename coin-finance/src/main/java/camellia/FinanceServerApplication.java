package camellia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 墨染盛夏
 * @version 2023/12/23 0:06
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class FinanceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinanceServerApplication.class, args);
    }
}
