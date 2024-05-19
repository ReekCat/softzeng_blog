package cn.softzeng.handler.exception;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.enums.AppHttpCodeEnum;
import cn.softzeng.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
          log.error("出现了异常!{}",e);//e的异常信息生效 e.toString()
        //从异常对象中获取提示信息，封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常!{}",e);//e的异常信息生效 e.toString()
        //从异常对象中获取提示信息，封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }

}
