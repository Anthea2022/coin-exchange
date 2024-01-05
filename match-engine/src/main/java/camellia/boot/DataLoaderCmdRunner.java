package camellia.boot;

import camellia.disruptor.DisruptorTemplate;
import camellia.domain.model.EntrustOrder;
import camellia.mapper.EntrustOrderMapper;
import camellia.util.BeanUtil;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * @author 墨染盛夏
 * @version 2024/1/5 15:33
 */
@Slf4j
public class DataLoaderCmdRunner implements CommandLineRunner {
    private EntrustOrderMapper entrustOrderMapper;

    private DisruptorTemplate disruptorTemplate;

    /**
     * 项目启动后会执行该方法
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        List<EntrustOrder> entrustOrders = entrustOrderMapper.list(new Query().eq("status", (byte) 0).orderby("created", Sort.ASC));
        if (CollectionUtils.isEmpty(entrustOrders)) {
            return;
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (EntrustOrder entrustOrder : entrustOrders) {
            disruptorTemplate.onData(BeanUtil.entrustOrderToOrder(entrustOrder));
        }
        stopWatch.stop();
        log.info("总条数：" + entrustOrders.size() + "，总共耗时：" + stopWatch.getLastTaskTimeMillis() + "ms");
    }
}
