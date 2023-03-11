package com.example.util;

import com.example.common.CommonException;

import java.net.*;
import java.util.Enumeration;

public class CommonUtil {
    public static String getHostAddress() {
//        // linux 中无法正确获取
//        try {
//            InetAddress localHost = Inet4Address.getLocalHost();
//            return localHost.getHostAddress();
//        } catch (UnknownHostException e) {
//            throw CommonException.of(e);
//        }
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = networkInterfaces.nextElement();
                Enumeration<InetAddress> nis = ni.getInetAddresses();
                while (nis.hasMoreElements()) {
                    InetAddress ia = nis.nextElement();
                    if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) {
                        return ia.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            throw CommonException.of(e);
        }
        return null;
    }
}
