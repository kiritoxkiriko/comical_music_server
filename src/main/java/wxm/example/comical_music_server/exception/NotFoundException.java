package wxm.example.comical_music_server.exception;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
public class NotFoundException extends Exception{
    public NotFoundException() {
    }
    public NotFoundException(String msg) {
        super(msg);
    }
}
