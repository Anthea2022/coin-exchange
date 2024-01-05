package camellia.enums;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 15:09
 */
public enum MatchStrategy {
    /**
     * 限价交易
     */
    LIMIT_PRICE,

    /**
     * 市场交易
     */
    MARKET_PRICE;

    private String name;

    private int code;

    public String getName() {
        return name;
    }
}
