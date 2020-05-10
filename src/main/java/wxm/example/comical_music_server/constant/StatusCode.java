package wxm.example.comical_music_server.constant;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
public enum StatusCode {
    SUCCESS("成功",200),
    UNAUTHORIZED("无权限",400),
    FAILED("失败",4001),
    VERIFY_FAILED("验证失败",4002),
    REGISTER_FAILED("注册失败",4003),
    NO_SUCH_USER("无用户",4004),
    ;
    private String msg;
    private int code;


    StatusCode(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
