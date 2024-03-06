package com.sky.config;


import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import io.netty.util.SuppressForbidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {


    /**
     * 阿里oss存储服务配置，通过自定义工具+配置类实现
     * @param aliOssProperties
     * @return
     */

    @Bean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {

        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }

}
