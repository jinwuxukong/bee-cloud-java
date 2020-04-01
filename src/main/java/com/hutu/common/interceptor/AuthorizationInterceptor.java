package com.hutu.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.util.HttpContextUtils;
import com.hutu.common.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;

import static com.hutu.common.constant.Constant.TOKEN;

/**
 * 权限(Token)验证
 *
 * @author hutu
 * @date 2019/6/6 14:40
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Value("${spring.profiles.active}")
    public String profile;
    private final static String DEV = "dev";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        logger.info("开发模式： {}",profile);
        if (DEV.contains(profile)){
            return true;
        }
        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }
        //如果有@AuthIgnore，或白名单，则不验证token
        if (whilePath() || annotation != null) {
            return true;
        }

        String token = JwtUtils.refreshToken();
        //token 为空则不需要刷新
        if (StrUtil.isNotEmpty(token)) {
            response.setHeader(TOKEN, token);
        }
        return true;
    }

    public static boolean whilePath() {
        String servletPath = HttpContextUtils.getHttpServletRequest().getServletPath();
        // 白名单
        String[] whileWords = {".css", ".js", ".html", ".map", ".woff", ".woff2", ".jpg", ".png", ".gif", ".jpeg", ".bmp",
                "ttf", "mp4", "m3u8", "flv", "pdf", "docx", "doc", "xlsx", "xls", ".txt", "/druid", "/error", "/configuration/ui",
                "/swagger-resources", "/v2/", "/services"};
        HashSet<String> whitePaths = new HashSet<>(Arrays.asList(whileWords));
        for (String path : whitePaths) {
            if (servletPath.contains(path) || "".equals(servletPath) || "/".equals(servletPath)) {
                return true;
            }
        }
        return false;
    }
}
