package camellia.disruptor;


import lombok.Data;

import java.io.Serializable;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 19:57
 */
@Data
public class OrderEvent implements Serializable {
    /**
     * 时间戳
     */
    private final long timestamp;

    /**
     * 时间携带的数据
     */
    protected transient Object resource;

    public OrderEvent() {
        this.timestamp = System.currentTimeMillis();
    }

    public OrderEvent(Object resource) {
        this.timestamp = System.currentTimeMillis();
        this.resource = resource;
    }
}
