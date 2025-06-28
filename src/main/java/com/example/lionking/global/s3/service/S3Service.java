package com.example.lionking.global.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.example.lionking.global.config.S3Config;
import com.example.lionking.global.s3.dto.GetPresignedUrlRequest;
import com.example.lionking.global.s3.dto.GetPresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.s3.bucket}")
    private String bucket;

    @Value("${cloud.s3.upload.expTime}")
    private Long uploadExpTime;

    @Value("${cloud.s3.download.expTime}")
    private Long downloadExpTime;

    private final S3Config s3Config;

    public GetPresignedUrlResponse getPresignedUrlForPut(GetPresignedUrlRequest getPresignedUrlRequest) {
        String filePath = createPath(getPresignedUrlRequest.prefix(), getPresignedUrlRequest.fileName());
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(bucket, filePath, HttpMethod.PUT);
        URL url = s3Config.amazonS3().generatePresignedUrl(generatePresignedUrlRequest);
        return GetPresignedUrlResponse.of(url.toString(), filePath);
    }

/*    public String updatePresignedUrl(String filePath) {
        if(filePath!=null&&!filePath.isEmpty()){
            GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(bucket, filePath, HttpMethod.PUT);
            URL url = s3Config.amazonS3().generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        }
        return filePath; //다시 반환하게 하자
    }
    */

    public String getPresignedUrlForGet(String filePath) {
        if (filePath != null) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(bucket, filePath, HttpMethod.GET);
            URL url = s3Config.amazonS3().generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        }
        return filePath;
    }

    public void deleteFile(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            s3Config.amazonS3().deleteObject(bucket, filePath);
        }
    }

    public void deleteFiles(List<String> filePaths) {
        if (filePaths == null || filePaths.isEmpty()) {
            return;
        }
        for (String filePath : filePaths) {
            deleteFile(filePath);
        }
    }

    private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String bucket, String fileName, HttpMethod method) {
        if(method == HttpMethod.PUT) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                    .withMethod(method)
                    .withExpiration(putPresignedUrlExpiration());

            return generatePresignedUrlRequest;
        } else if (method == HttpMethod.GET) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                    .withMethod(method)
                    .withExpiration(getPresignedUrlExpiration());

            return generatePresignedUrlRequest;
        } else {
            return null;
        }
    }

    private Date putPresignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += uploadExpTime;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    private Date getPresignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += downloadExpTime;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    private String createPath(String prefix, String fileName) {
        String fileUniqueId = UUID.randomUUID().toString();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return String.format("%s/%s-%s-%s", prefix, timestamp, fileUniqueId, fileName);
    }
}
