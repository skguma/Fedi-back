package com.fedi.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.fedi.domain.entity.Image;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class S3Service {
    private AmazonS3 s3Client;
    public static final String CLOUD_FRONT_DOMAIN_NAME = "d22asp7h1gwd58.cloudfront.net";

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }


    public String upload(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String contentType = file.getContentType();
            String originalFileExtension;

            //확장자명 없을 경우
            if (ObjectUtils.isEmpty(contentType)) {
                return null;
            } else {
                if (contentType.contains("image/jpg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else if(contentType.contains("image/jpeg")){
                    originalFileExtension = ".jpeg";
                }
                // 다른 파일 명이면 아무 일 하지 않는다
                else {
                    return null;
                }
            }
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = FilenameUtils.getBaseName(file.getOriginalFilename()) + "-" + date.format(new Date()) + "." + FilenameUtils.getExtension(file.getOriginalFilename());


            s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null));

            return "http://" + CLOUD_FRONT_DOMAIN_NAME + "/" + fileName;
        }
        else return null;
    }
}


