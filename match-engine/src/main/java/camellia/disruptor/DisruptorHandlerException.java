package camellia.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 19:50
 */
@Slf4j
public class DisruptorHandlerException implements ExceptionHandler {
    @Override
    public void handleEventException(Throwable throwable, long l, Object o) {
        log.info("EventHandler Exception===>{}, sequence===>{}, event===>{}", throwable.getMessage(), l, o);
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        log.info("OnStartHandler Exception===>{}", throwable.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        log.info("OnShutHandler Exception===>{}", throwable.getMessage());
    }
}
