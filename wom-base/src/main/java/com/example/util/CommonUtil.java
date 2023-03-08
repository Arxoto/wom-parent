package com.example.util;

import com.example.common.CommonException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommonUtil {
    public static String getHostAddress() {
        try {
            InetAddress localHost = Inet4Address.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            throw CommonException.of(e);
        }
    }
}
