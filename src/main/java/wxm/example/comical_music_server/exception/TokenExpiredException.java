package wxm.example.comical_music_server.exception;


import org.apache.shiro.authc.AuthenticationException;

/**
 * @author Alex Wang
 * @date 2020/05/28
 */
public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException() {
        super();
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
