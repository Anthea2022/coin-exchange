package camellia.rocket;

import camellia.domain.model.MessagePayload;
import camellia.domain.vo.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.starter.TioWebSocketServerBootstrap;

/**
 * @author 墨染盛夏
 * @version 2024/1/6 13:33
 */
@Slf4j
@Component
public class RocketMessageListener {
    @Autowired
    private TioWebSocketServerBootstrap bootstrap;

    @StreamListener("tio_group")
    public void handlerMessage(MessagePayload messagePayload) {
        log.info("接收到rocket的消息==============>{}", messagePayload);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setSubbed(messagePayload.getChannel());
        responseEntity.put("result", messagePayload.getBody());
        if (StringUtils.hasText(messagePayload.getUid())) {
            Tio.sendToUser(bootstrap.getServerTioConfig(), messagePayload.getUid(), responseEntity.build());
            return;
        }
        Tio.sendToGroup(bootstrap.getServerTioConfig(), messagePayload.getChannel(), responseEntity.build());
    }
}
