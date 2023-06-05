package top.javap.hermes.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class URL {

    private String protocol;

    private String host;

    private int port;

    private Map<String, String> parameters = new HashMap<>();

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static URL valueOf(String address) {
        if (address == null || (address = address.trim()).length() == 0) {
            throw new IllegalArgumentException("url == null");
        }
        URL url = new URL();
        int protocolEnd = address.indexOf(":");
        int hostStart = address.indexOf("//") + 2;
        int hostEnd = address.indexOf(":", hostStart);
        int portEnd = address.indexOf("/", hostEnd);
        if (portEnd < 0) {
            portEnd = address.length();
        }
        url.setProtocol(address.substring(0, protocolEnd));
        url.setHost(address.substring(hostStart, hostEnd));
        url.setPort(Integer.valueOf(address.substring(hostEnd + 1, portEnd)));
        int parameterStart = address.indexOf("?");
        if (parameterStart > 0) {
            String paramStr = address.substring(parameterStart + 1);
            String[] split = paramStr.split("&");
            if (split.length > 0) {
                for (String item : split) {
                    String[] pair = item.split("=");
                    if (pair.length == 2) {
                        url.getParameters().put(pair[0], pair[1]);
                    }
                }
            }
        }
        return url;
    }
}