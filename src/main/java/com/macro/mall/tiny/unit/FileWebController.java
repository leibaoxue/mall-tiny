package src.main.java.com.macro.mall.tiny.unit;




import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * @Author: boris
 * @Data: Created on 2019/11/25
 * @Description: 文件上传
 */
@RestController
@Log4j2
public class FileWebController {
    @Autowired
    private ObsFileUploadUtil obsFileUploadUtil;


    @Value("${prop.obs.config.domain}")
    private  String OBS_DOMAIN;

    @Value("${prop.obs.config.endPoint}")
    private String OBS_ENDPOINT;

    @PostMapping("/api/user/fileUpload")
    public HttpJsonResult<String> fileUpload(@RequestParam("imageFile") MultipartFile file) {
        String filePath = null;
        try {
            if(Objects.isNull(file)){
                throw  new BusinessException("file不能为空");
            }
            filePath = obsFileUploadUtil.uploadImage(file,10*1024 * 1024L,"10M");
            filePath =filePath.replace(OBS_ENDPOINT,OBS_DOMAIN);
        } catch (BusinessException be) {
            log.error("[medical-design][FileWebController][fileUpload]出现异常：",be.getMessage());
             return  HttpJsonResult.fail(be.getMessage());
        } catch (IOException e) {
            log.error("[medical-design][FileWebController][fileUpload]出现系统异常：",e);
            return HttpJsonResult.fail(Constants.SYSTEM_ERROR.getStr());
        }
        return HttpJsonResult.ok(filePath);
    }

}
