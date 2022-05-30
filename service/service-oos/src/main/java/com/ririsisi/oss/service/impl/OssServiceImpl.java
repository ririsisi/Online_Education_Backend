package com.ririsisi.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.ririsisi.oss.service.OssService;
import com.ririsisi.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @Author ririsisi
 * @Version 1.0
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 获取文件名称
        String fileName = file.getOriginalFilename();

        // 1. 在文件名称里面拼上随机字符串
        String uuid = UUID.randomUUID().toString().replace("-","");
        fileName = fileName + uuid;

        // 2. 把文件按照日期进行分类
        // 2022/5/21/02.jpg
        // 获取当前日期
        String datePath = new DateTime().toString("yyyy/MM/dd");

        // 拼接文件名
        fileName = datePath + "/" + fileName;

        try {
            // 获取上传文件文件输入流
            InputStream inputStream = file.getInputStream();

            // 调用oos方法实现上传
            ossClient.putObject(bucketName, fileName, inputStream);

            // 管理ossClient
            ossClient.shutdown();

            // 返回上传路径手动拼接上传到阿里云oos的文件路径  https://edu-guli-417.oss-cn-hangzhou.aliyuncs.com/2.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
