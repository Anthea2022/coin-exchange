package camellia.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

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
     * @param s
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onText(WsRequest wsRequest, String s, ChannelContext channelContext) throws Exception {
        log.info("客户端发送文本过来了");
        // 最常使用订阅模式
        Tio.bindGroup(channelContext, "test");
        return WsResponse.fromText("你好呀", "utf-8"); // 发送给别人的数据
    }
}
