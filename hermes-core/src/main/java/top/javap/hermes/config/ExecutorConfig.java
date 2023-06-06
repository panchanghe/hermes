package top.javap.hermes.config;

import top.javap.hermes.constant.CommConstant;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/6
 **/
public class ExecutorConfig {

    private int corePoolSize = CommConstant.DEFAULT_BIZ_THREADS;
    private int maximumPoolSize = CommConstant.DEFAULT_BIZ_THREADS;
    private int keepAliveSeconds = CommConstant.DEFAULT_KEEPALIVE_SECONDS;
    private int queues = CommConstant.DEFAULT_QUEUES;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getQueues() {
        return queues;
    }

    public void setQueues(int queues) {
        this.queues = queues;
    }
}