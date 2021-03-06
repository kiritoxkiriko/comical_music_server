package wxm.example.comical_music_server.controller;

import org.apache.shiro.ShiroException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.exception.NotFoundException;
import wxm.example.comical_music_server.exception.TokenExpiredException;
import wxm.example.comical_music_server.exception.UnauthorizedException;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController {

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseData handle401(ShiroException e) {
        //System.out.println(22);
        return new ResponseData(401, e.getMessage(), null);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseData handle401(UnauthorizedException e) {
        //System.out.println(11);
        return new ResponseData(401, e.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseData handle401(AuthenticationException exception){
        return new ResponseData(401, exception.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseData tokenExpire(TokenExpiredException exception){
        return new ResponseData(StatusCode.TOKEN_EXPIRE,null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    public ResponseData handleMultipartException(){
        return new ResponseData(HttpStatus.BAD_REQUEST.value(),"文件大小超出限制",null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ResponseData handle404(){
        return new ResponseData(StatusCode.NOT_FOUND,null);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData globalException(HttpServletRequest request, Throwable ex) {
        //ex.printStackTrace();
        return new ResponseData(getStatus(request).value(), ex.getMessage(), null);
    }

    //捕捉404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseData handleSystem404(NoHandlerFoundException e){
        return new ResponseData(404,e.getMessage(),null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}

