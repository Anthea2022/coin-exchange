package camellia.domain.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.tio.websocket.common.WsResponse;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 墨染盛夏
 * @version 2024/1/6 13:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResponseEntity {
    private String id;

    private String ch;

    private String subbed;

    private String event;

    private String status;

    private String canceled;

    public Long getTs() {
        return new DateTime().getMillis();
    }

    private Map<String, Object> extend = new LinkedHashMap<>();

    public WsResponse build() {
        extend.put("id", this.id);
        extend.put("ch", this.ch);
        extend.put("subbed", this.subbed);
        extend.put("event", this.event);
        extend.put("status", this.status);
        extend.put("canceled", this.canceled);
        extend.put("ts", this.getTs());
        return WsResponse.fromText(JSONObject.toJSONString(extend), "utf-8");
    }

    public ResponseEntity put(String key, Object value) {
        extend.put(key,value);
        return this;
    }
}
