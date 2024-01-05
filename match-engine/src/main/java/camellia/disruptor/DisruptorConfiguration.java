package camellia.disruptor;

import camellia.match.MatchProperties;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import net.openhft.affinity.AffinityThreadFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * @author 墨染盛夏
 * @version 2024/1/4 20:22
 */
@Configuration
@EnableConfigurationProperties(value = DisruptorProperties.class)
public class DisruptorConfiguration {
    private DisruptorProperties disruptorProperties;

    public DisruptorConfiguration(DisruptorProperties disruptorProperties) {
        this.disruptorProperties = disruptorProperties;
    }

    @Bean
    public EventFactory<OrderEvent> eventEventFactory() {
        EventFactory<OrderEvent> orderEventEventFactory = new EventFactory<OrderEvent>() {
            @Override
            public OrderEvent newInstance() {
                return new OrderEvent();
            }
        };
        return orderEventEventFactory;
    }

    @Bean
    public ThreadFactory threadFactory() {
        return new AffinityThreadFactory("Match-Handler:") ;
    }

    @Bean
    public WaitStrategy waitStrategy() {
        return new YieldingWaitStrategy();
    }

    /**
     * @param eventEventFactory 事件工厂
     * @param threadFactory 执行者（消费者）的线程创建
     * @param waitStrategy 没有数据时
     * @return
     */
    @Bean
    public RingBuffer<OrderEvent> ringBuffer(EventFactory<OrderEvent> eventEventFactory, ThreadFactory threadFactory,
                                             WaitStrategy waitStrategy, EventHandler<OrderEvent> []eventHandlers) {
        ProducerType producerType = ProducerType.MULTI;
        if (disruptorProperties.isMultiProducer()) {

        }
        Disruptor<OrderEvent> disruptor = new Disruptor<>(eventEventFactory, disruptorProperties.getRingBufferSize(), threadFactory, producerType, waitStrategy);
        disruptor.setDefaultExceptionHandler(new DisruptorHandlerException());

//        一个symbol对应一个消费者
        disruptor.handleEventsWith(eventHandlers);

        //开始监听
        disruptor.start();

        // 停止监听
        final Disruptor<OrderEvent> disruptorShutDown = disruptor;
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    disruptorShutDown.shutdown();
                }, "DisruptorShutDownThread"
        ));
        return disruptor.getRingBuffer();
    }

    @Bean
    public DisruptorTemplate disruptorTemplate(RingBuffer<OrderEvent> orderEventRingBuffer) {
        return new DisruptorTemplate(orderEventRingBuffer);
    }
}
