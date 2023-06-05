package top.javap.hermes.common;

import top.javap.hermes.constant.DictConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/27
 **/
public class Properties {

    private final Map<String, Object> map = new HashMap<>();

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public String getString(String key) {
        return get(key);
    }

    public int getInt(String key) {
        return get(key);
    }

    public <T> T get(String key) {
        return (T) map.get(key);
    }

    public String getAppName() {
        return getString(DictConstant.APPLICATION_NAME);
    }

    public String getHost() {
        return getString(DictConstant.HOST);
    }

    public int getPort() {
        return getInt(DictConstant.PORT);
    }

    public Map<String, String> getMetadata() {
        Map<String, String> metadata = (Map<String, String>) map.get(DictConstant.METADATA);
        if (Objects.isNull(metadata)) {
            put(DictConstant.METADATA, metadata = new HashMap<>());
        }
        return metadata;
    }
}