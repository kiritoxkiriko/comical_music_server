package wxm.example.comical_music_server.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import wxm.example.comical_music_server.constant.Constant;
import wxm.example.comical_music_server.entity.bbs.User;

import java.sql.Date;

public class JWTUtil {


    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, long userId, String secret) throws TokenExpiredException{
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", userId)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户id
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,EXPIRE_TIME 后过期
     * @param userId 用户id
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(Long userId, String secret) {
        try {
            Date date = new Date(System.currentTimeMillis()+ Constant.TOKEN_EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    public static User getCurrentUser(){
        Subject subject = SecurityUtils.getSubject();
        User user= (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        return user;
    }
}
