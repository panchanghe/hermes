package top.javap.hermes.common;

/**
 * @Author: pch
 * @Date: 2023/6/6 16:37
 * @Description:
 */
public interface Ordered {
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    int getOrder();
}