package com.zhaodongxx.common.utils.token;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <P></P>
 *
 * @author zhaodong
 * @version v1.0
 * @since 2018/2/1 16:26
 */
public class TokenUtils {

    public static final String SECRET = "itpower";

    public static String generateToken(Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims)
                //.setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
    }

    /**
     * 由字符串生成加密key
     * @return
     */
    public String generalKey(){
        //String stringKey = jianshu+Constant.JWT_SECRET;
        //byte[] encodedKey = Base64.decodeBase64(stringKey);
        //SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

        String key = SECRET;

        return key;
    }


    /**
     * 创建jwt
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public String createJWT(String id, String subject, long ttlMillis) throws Exception {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        String key = generalKey();

        HashMap<String,Object> claims = new HashMap<String, Object>();
        //jwt签发者
        claims.put("iss","itpower.com");
        // jwt所面向的用户
        claims.put("sub","dev");

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setClaims(claims)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }
}
