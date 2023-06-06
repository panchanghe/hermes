package top.javap.hermes.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public class NetUtil {

    private static String HOST_ADDRESS = null;

    public static String getLocalHost() {
        if (Objects.isNull(HOST_ADDRESS)) {
            HOST_ADDRESS = getLocalAddress().getHostAddress();
        }
        return HOST_ADDRESS;
    }

    private static InetAddress getLocalAddress() {
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface network = enumeration.nextElement();
                if (network.isVirtual() || !network.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> inetAddresses = network.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (Objects.nonNull(inetAddress) && (inetAddress instanceof Inet4Address)) {
                        return inetAddress;
                    }
                }
            }
            return InetAddress.getLocalHost();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}