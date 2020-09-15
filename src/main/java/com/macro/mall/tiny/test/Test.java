package src.main.java.com.macro.mall.tiny.test;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        Entity entity = new Entity();
        entity.setTotalCost(new BigDecimal("23.789232"));
        System.out.println(entity.toString());
        System.out.println(entity.getTotalCost());
        AnnotationProcessor.getStudentInfo(Student.class);
    }
}
