package src.main.java.com.macro.mall.tiny.dao;



import src.main.java.com.macro.mall.tiny.nosql.elasticsearch.document.EsProduct;
import src.main.java.com.macro.mall.tiny.nosql.elasticsearch.document.EsProducts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 搜索系统中的商品管理自定义Dao
 * Created by macro on 2018/6/19.
 */
public interface EsProductDao {
    List<EsProduct> getAllEsProductList(@Param("id") Long id);

    List<EsProducts> getAll();
}