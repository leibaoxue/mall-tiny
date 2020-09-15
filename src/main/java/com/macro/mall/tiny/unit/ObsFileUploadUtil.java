package com.macro.mall.tiny.unit;




import com.obs.services.ObsClient;
import com.obs.services.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

/**
 * @Author: boris
 * @Data: Created on 2019/11/25
 * @Description:
 */
@Component
@Log4j2
public class ObsFileUploadUtil {
    @Autowired
    private ObsClient obsClient;

    @Value("${prop.obs.config.bucketname}")
    private String bucketName;

    @Value("${prop.obs.config.accessKey}")
    private String accessKey;

    @Value("${prop.obs.config.endPoint}")
    private String endPoint;

    private static AuthTypeEnum authType = AuthTypeEnum.V2;

    public String uploadImage(MultipartFile multipartFile,long partSizeMax,String sizeMsg) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }

        String tempFilePath = System.getProperty("java.io.tmpdir") + multipartFile.getOriginalFilename();
        File uploadFile = new File(tempFilePath);
        multipartFile.transferTo(uploadFile);//生成临时文件

        String fileFullName = getFileName(uploadFile);
        validateFile(fileFullName, uploadFile,partSizeMax,sizeMsg);

        //创建桶
        obsClient.createBucket(bucketName);

        PutObjectRequest request = new PutObjectRequest("bucketname", "objectname");
        request.setFile(uploadFile);
        request.setProgressListener(new ProgressListener() {
            @Override
            public void progressChanged(ProgressStatus status) {
                // 获取上传平均速率    获取上传进度百分比
                log.info(fileFullName + "======AverageSpeed:{},TransferPercentage:{}", status.getAverageSpeed(), status.getTransferPercentage());
            }
        });
        // 每上传1MB数据反馈上传进度
        request.setProgressInterval(1024 * 1024L);
        request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
        request.setBucketName(bucketName);
        request.setObjectKey(fileFullName);
        PutObjectResult result = obsClient.putObject(request);
        log.info("**************PutObjectResult**********" + result.toString());

        return result.getObjectUrl();
    }

    private String getFileName(File uploadFile) {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String baseDir = year + "/" + month + "/";
        String objectKey = UUID.randomUUID().toString().replace("-", "");
        objectKey = baseDir + objectKey;
        String fileName = uploadFile.getName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String objectName = objectKey + suffix;
        return objectName;
    }

    private void validateFile(String fileName, File uploadFile,long partSizeMax,String sizeMsg) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String[] array = {".png", ".jpg", ".jpeg", ".pdf", ".gif", ".doc", ".docx", ".mp4", ".rar", ".zip", ".apk", ".xlsx", ".txt", ".pdf",".7z",".tar"};
        boolean enableFileSuffix = Arrays.asList(array).contains(suffix.toLowerCase());
        if (!enableFileSuffix) {
            throw new BusinessException("上传的格式不支持");
        }
        long length = uploadFile.length();
        if (length > partSizeMax) {
            throw new BusinessException("上传文件大小不能超过"+sizeMsg);
        }
    }
}
