package src.main.java.com.macro.mall.tiny.unit;

/**
 * @Author: boris
 * @Data: Created on 2019/9/16
 * @Description: 业务异常
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
