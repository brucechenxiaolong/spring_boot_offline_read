package com.pde.pdes.offline.utils;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt生成token
 * @atuther dc
 */
public class JwtUtil {

    //密钥
    public static final String SECRET = "lzwy-offline";
    //过期时间:秒
    public static final int EXPIRE = 600;

    /**
     * 生成Token
     * @param userId
     * @param userName
     * @return
     * @throws Exception
     */
    public static String createToken(String userId, String userName) throws Exception {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, EXPIRE);
        Date expireDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)//头
                .withClaim("userId", userId)
                .withClaim("loginName", userName)
                .withSubject("测试")//
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(SECRET));//签名
        return token;
    }

    /**
     * 验证Token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token)throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        }catch (Exception e){
            throw new RuntimeException("token已过期，请重新登录");
        }
        return jwt.getClaims();
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static Map<String, Claim> parseToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }

    public static void main(String[] args) {
        try {
            String token = JwtUtil.createToken("d4dd212ccc7145448ee717c81b2d916a","test1");
            System.out.println(token);
            Map<String, Claim> map = JwtUtil.verifyToken(token);
            System.out.println(map);
            System.out.println(JSONObject.toJSONString(map));
            Claim claim = map.get("loginName");
            String mapp = claim.asString();
            System.out.println(mapp);
            Map<String, Claim> map1 = JwtUtil.parseToken(token);
            System.out.println(map1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}