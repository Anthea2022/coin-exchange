package camellia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.websocket.starter.EnableTioWebSocketServer;

/**
 * @author 墨染盛夏
 * @version 2024/1/6 12:48
 */
@SpringBootApplication
@EnableTioWebSocketServer // 开启tio的webSocket
public class ChanServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChanServerApplication.class, args);
    }
}
