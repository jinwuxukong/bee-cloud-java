package com.hutu.modules.app.login.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hutu.common.entity.CallerInfo;
import com.hutu.common.enums.ResultCode;
import com.hutu.common.exception.GlobalException;
import com.hutu.common.util.HttpContextUtils;
import com.hutu.common.util.IdGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * jwt工具类
 *
 * @author hutu
 * @date 2018/4/2 16:41
 */
@UtilityClass
public class AppJwtUtils {

    private final static Logger logger = LoggerFactory.getLogger(AppJwtUtils.class);
    /**
     * 签名key
     */
    private final static String KEY = "bWluZyBodWEgcWlhbmcgTE9WRSB4dSB0YWkgbGlhbiAh";

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(KEY.getBytes());
    /**
     * 可刷新时间段7天到15天
     */
    private final static long REFRESH_TIME = 1000 * 60 * 60 * 24 * 7;
    /**
     * 过期时间
     */
    private final static long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 15;

    /**
     * 生成 jwt token
     */
    public String createToken(Object sourceToken) {
        String subject = JSON.toJSONString(sourceToken);
        //发布时间
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + EXPIRE_TIME);
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .setId(IdGenerator.getIdStr())
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    /**
     * 解析jwt token
     */
    public Claims parseToken(String sourceToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(sourceToken)
                    .getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            throw new GlobalException(ResultCode.TOKEN_IS_EXPIRE, e);
        } catch (Exception e) {
            throw new GlobalException(e);
        }
    }

    /**
     * 刷新Token
     */
    public String refreshToken() {
        String token = HttpContextUtils.getRequestToken();
        if (StrUtil.isEmpty(token)) {
            throw new GlobalException(ResultCode.NOT_FOUNT_TOKEN);
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            long issuedAt = claims.getIssuedAt().getTime();
            long difference = System.currentTimeMillis() - issuedAt;
            if (difference > REFRESH_TIME) {
                //发布时间
                Date nowDate = new Date();
                //过期时间
                Date expireDate = new Date(nowDate.getTime() + EXPIRE_TIME);
                return Jwts.builder()
                        .setHeaderParam("typ", "JWT")
                        .setClaims(claims)
                        .setIssuedAt(nowDate)
                        .setExpiration(expireDate)
                        .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                        .compact();
            } else {
                return null;
            }
        } catch (ExpiredJwtException e) {
            throw new GlobalException(ResultCode.TOKEN_IS_EXPIRE, e);
        } catch (Exception e) {
            throw new GlobalException(e);
        }
    }

    /**
     * 获取用户id 此系统以用户id做subject
     *
     * @return subject
     */
    public CallerInfo getCallerInfo() {
        String token = HttpContextUtils.getRequestToken();
        if (StrUtil.isNotEmpty(token)) {
            Claims claim = parseToken(token);
            if (claim != null) {
                return JSON.parseObject(claim.getSubject(), CallerInfo.class);
            }
            throw new GlobalException(ResultCode.INTERNAL_SERVER_ERROR);
        } else {
            throw new GlobalException(ResultCode.NOT_FOUNT_TOKEN);
        }
    }

    public Integer getUserId() {

        try {
            return getCallerInfo().uid;
        } catch (Exception e) {
            logger.info("getUserId 失败");
            return 0;
        }
    }

    public String getUserName() {
        try {
            return getCallerInfo().nick;
        } catch (Exception e) {
            logger.info("getUserName 失败");
            return "无";
        }
    }

    public Integer getTenantId() {
        try {
            return getCallerInfo().tenantId;
        } catch (Exception e) {
            logger.error("getTenantId 失败");
            return 0;
        }
    }

    public static void main(String[] args) {
        CallerInfo callerInfo = new CallerInfo();
        callerInfo.uid = 123;
        callerInfo.tenantId = 456;
    }

}

