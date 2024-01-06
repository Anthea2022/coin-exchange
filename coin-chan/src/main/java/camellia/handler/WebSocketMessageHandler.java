package camellia.handler;

import camellia.domain.vo.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Objects;

/**
 * @author 墨染盛夏
 * @version 2024/1/6 13:00
 */
@Slf4j
@Component
public class WebSocketMessageHandler implements IWsMsgHandler {
    /**
     * 开始握手
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientIp = httpRequest.getClientIp();
        log.info("开始和{}客户端连接", clientIp);
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        log.info("和客户端握手成功");
    }

    /**
     * 前端发送byte时的处理
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 前端选择关闭时的处理
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        log.info("客户端关闭握手");
        Tio.remove(channelContext, "remove channelContext");
        return null;
    }

    /**
     * 前端发送文本时的处理
     * @param wsRequest
     * @param text
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        log.info("客户端发送文本过来了");

        if (Objects.equals("ping", text)) {
            return "pong";
        }
        JSONObject payload = JSON.parseObject(text);
        String id = payload.getString("id"); //订阅的id
        String sub = payload.getString("sub"); // 要订阅的组
        String req = payload.getString("req"); // 当前的request
        String cancel = payload.getString("cancel"); // 要取消订阅的组
        String authorization = payload.getString("authorization");

        if (StringUtils.hasText(sub)) {
            Tio.bindGroup(channelContext, sub);
        }
        if (StringUtils.hasText(cancel)) {
            Tio.unbindGroup(cancel, channelContext);
        }
        if (StringUtils.hasText(authorization) && authorization.startsWith("bearer ")) {
            String token = authorization.replace("bearer ", "");
            Jwt jwt = JwtHelper.decode(token);
            String jwtStr = jwt.getClaims();
            JSONObject jsonObject = JSON.parseObject(jwtStr);
            String userId = jsonObject.getString("user_name");
            // 由用户绑定用户
            Tio.unbindUser(channelContext.getTioConfig(), userId);
        }
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setId(id);
        responseEntity.setCh(sub);
        responseEntity.setEvent(req);
        responseEntity.setSubbed(sub);
        responseEntity.setStatus("OK");
        responseEntity.setCanceled(cancel);
        return responseEntity.build();
    }
}
