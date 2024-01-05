package camellia.factory;

import camellia.service.MatchService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 14:57
 */
@Slf4j
public class MatchServiceFactory {
    private static Map<String, MatchService> matchServiceMap = new HashMap<>();

    public static void addMatchService(String matchName, MatchService matchService) {
        matchServiceMap.put(matchName, matchService);
    }

    public static MatchService getMatchByName(String matchName) {
        if (!matchServiceMap.containsKey(matchName)) {
            log.info("无此策略");
            return null;
        }
        return matchServiceMap.get(matchName);
    }
}
