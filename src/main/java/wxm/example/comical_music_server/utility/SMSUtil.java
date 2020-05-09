package wxm.example.comical_music_server.utility;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wxm.example.comical_music_server.shiro.MyRealm;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
public class SMSUtil {

    private static final Logger LOGGER = LogManager.getLogger(SMSUtil.class);
    public static boolean sendSms(String phoneNumber, String code) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", Constant.ALIYUN_ACCESS_KEY_ID, Constant.ALIYUN_ACCESS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", Constant.ALIYUN_SMS_SIGN_NAME);
        request.putQueryParameter("TemplateCode", Constant.ALIYUN_SMS_TEMPLATE_CODE);
        request.putQueryParameter("TemplateParam", "{ \"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            if (response.getData().contains("OK")) {
                return true;
            }else {
                LOGGER.error("API异常:\n"+response.getData());
                return false;
            }
        } catch (ServerException e) {
            LOGGER.error("ServerException");
            return false;
        } catch (ClientException e) {
            LOGGER.error("ClientException");
            return false;
        }
    }
}