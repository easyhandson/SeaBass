package cn.com.seabase.framework.web.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具类, 用来获取请求IP地址
 */
@Slf4j
public class IPUtil {

    /** unknown */
    private static final String UNKNOWN = "unknown";
    /** 本地32位IP */
    public static final String LOCAL_32 = "127.0.0.1";
    /** 本地64位IP */
    public static final String LOCAL_64 = "0:0:0:0:0:0:0:1";

    private static final String[] HEADER_KEYS = {
            "Proxy-Client-IP", "X-Real-IP",
            "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    };

    /**
     * 获取客户端ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (null != ip && !ip.isBlank() && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if(ip.contains(",")){
                return ip.split(",")[0];
            }
            return ip;
        }

        for (String headerKey : HEADER_KEYS) {
            ip = request.getHeader(headerKey);
            if (StringUtils.isNotEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        // 从本地访问时根据网卡取本机配置的IP
        ip = request.getRemoteAddr();
        if (LOCAL_32.equals(ip) || LOCAL_64.equals(ip)) {
            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getLocalHost();
                ip = inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                log.error("[getRealIp][获取本地IP地址信息失败! 错误信息: {}]", e.getMessage(), e);
                ip = LOCAL_32;
            }
        }

        return ip;
    }

}
