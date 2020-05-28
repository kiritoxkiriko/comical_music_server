package wxm.example.comical_music_server.entity.respone;

import wxm.example.comical_music_server.constant.StatusCode;

/**
 * @author Alex Wang
 * @date 2020/05/07
 */
public class ResponseData {

    // http 状态码
    private int code;

    // 返回信息
    private String msg;

    // 返回的数据
    private Object data;

    public ResponseData(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseData() {
    }

    public ResponseData(StatusCode statusCode,Object data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    public ResponseData(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = null;
    }

    public static ResponseData of(StatusCode statusCode){
        return new ResponseData(statusCode);
    }

    public static ResponseData of(StatusCode statusCode,Object o){
        return new ResponseData(statusCode,o);
    }

    public static ResponseData success(Object o){
        return new ResponseData(StatusCode.SUCCESS,o);
    }

    public static ResponseData failed(){
        return new ResponseData(StatusCode.FAILED);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}