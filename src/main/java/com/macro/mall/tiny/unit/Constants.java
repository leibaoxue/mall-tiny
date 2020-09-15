package src.main.java.com.macro.mall.tiny.unit;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum Constants {
   SYSTEM_ERROR (1,"系统异常"),
   NOT_SYSTEM_ERROR  (2,"系统异常"),
   NOT_LOG_IN  (3,"系统异常");

    private int code;
    private String str;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
