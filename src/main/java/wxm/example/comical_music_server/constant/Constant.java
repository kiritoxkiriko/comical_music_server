package wxm.example.comical_music_server.constant;

/**
 * @author Alex Wang
 * @date 2020/05/07
 */
public class Constant {
    public final static String COOKIE_TOKEN_NAME="token";

    public static final String SECRET_CODE = "36d11172-b6ea-42e7-95b7-005d3a93147b";

    public static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000;

    public static final String PASSWORD_SALT="huaji";

    public static final long SMS_EXPIRE_TIME=60*5;

    //阿里云
    public static final String ALIYUN_ACCESS_KEY_ID="LTAI4Fc43JzfBGxDXf7qkb7N";

    public static final String ALIYUN_ACCESS_SECRET="bRGeZvuYtwYAdyzjBiFSWPXP6hTojn";

    public static final String ALIYUN_SMS_TEMPLATE_CODE = "SMS_189621502";

    public static final String ALIYUN_SMS_SIGN_NAME= "滑稽音乐";

    public static final String REDIS_SMS_CODE_MAP_NAME= "code";

    //redis
    public static final String PREFIX_PHONE="PHONE_";

    //path

    public static final String RESOURCE_PATH="/Users/Alex/Desktop/static";

    public static final String IMG_PATH="/img";

    public static final String AUDIO_PATH="/audio";

    public static final String LRC_PATH="/lrc";

    public static final String STATIC_URL_PATH ="/static";

    //Domain

    public static final String DOMAIN_URL="http://localhost:8088";


}
