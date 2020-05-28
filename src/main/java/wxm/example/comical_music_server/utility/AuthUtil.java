package wxm.example.comical_music_server.utility;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wxm.example.comical_music_server.constant.Constant;


/**
 * @author Alex Wang
 * @date 2020/05/07
 */

public class AuthUtil {
    private static Logger LOGGER= LogManager.getLogger(AuthUtil.class);

    public static boolean verifyPassword(String password, String saltedPassword){
        return signPassword(password).equals(saltedPassword);
    }

    public static String signPassword(String password){
        return DigestUtils.md5Hex((password+"/"+ Constant.PASSWORD_SALT));
    }
}
