package camellia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.starter.EnableTioWebSocketServer;
import org.tio.websocket.starter.TioWebSocketServerBootstrap;

import java.util.Date;

/**
 * @author 墨染盛夏
 * @version 2024/1/6 12:48
 */
@SpringBootApplication
@EnableTioWebSocketServer // 开启tio的webSocket
public class ChanServerApplication {
    @Autowired
    private TioWebSocketServerBootstrap bootstrap;

    public static void main(String[] args) {
        SpringApplication.run(ChanServerApplication.class, args);
    }

    @Scheduled(fixedRate = 5000)
    public void pushData() {
        Tio.sendToGroup(bootstrap.getServerTioConfig(), "test", WsResponse.fromText("现在是北京时间：" + System.currentTimeMillis(), "utf-8"));
    }
}
