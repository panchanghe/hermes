package top.javap.hermes.enums;

/**
 * @Author: pch
 * @Date: 2023/5/17 20:01
 * @Description:
 */
public enum ProtocolEnum {

    HERMES("hermes", "he"),
    ;

    private final String name;
    private final String scheme;

    ProtocolEnum(String name, String scheme) {
        this.name = name;
        this.scheme = scheme;
    }

    public String getName() {
        return name;
    }

    public String getScheme() {
        return scheme;
    }
}