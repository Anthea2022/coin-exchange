package camellia.enums;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 20:04
 */
public enum OrderDirection {
    SELL((byte) 1, "卖出"),

    BUY((byte) 2, "买入");

    private byte code;

    private String desc;

    OrderDirection(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public Byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static OrderDirection getOrderDirection(byte code) {
        OrderDirection[] orderDirections = OrderDirection.values();
        for (OrderDirection orderDirection : orderDirections) {
            if (orderDirection.getCode() == code) {
                return orderDirection;
            }
        }
        return null;
    }
}
