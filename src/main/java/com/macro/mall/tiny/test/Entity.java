package src.main.java.com.macro.mall.tiny.test;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class Entity {
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal totalCost;

    @JsonSerialize(using = MySerializerUtils.class)
    private int status;
}
