package top.javap.hermes.registry;

import top.javap.hermes.common.Properties;

import java.util.List;

/**
 * @Author: pch
 * @Date: 2023/5/27 21:06
 * @Description:
 */
public interface NotifyListener {

    void notify(List<Properties> list);

}