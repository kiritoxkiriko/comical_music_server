package wxm.example.comical_music_server.utility;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Alex Wang
 * @date 2020/05/07
 */

public class AuthUtil {
    public static final String SALT="huaji";

    public static boolean verifyPassword(String password, String saltedPassword){
        return signPassword(password).equals(saltedPassword);
    }

    public static String signPassword(String password){
        return DigestUtils.md5Hex(password+"/"+SALT);
    }
}
