/**
 * Project Name:gridapp-gateway1
 * File Name:FeignConfig.java
 * Package Name:com.softcloud.grid.gateway.config
 * Date:2019年10月10日上午10:05:29
 * Copyright (c) 2019, softcloud All Rights Reserved.
*/

package com.softcloud.grid.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: FeignConfig <br/>
 * Function: feign请求、响应编码处理 <br/>
 * @version 
 * @since JDK 1.8
 */
@Configuration
public class FeignConfig {
    
    /**
     * feignEncoder:feign编码 <br/>
     * @return 解码对象
     * @since JDK 1.8
     */
    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(feignHttpMessageConverter());
    }
    
    /**
     * feignDecoder:feign解码 <br/>
     * @return
     * @since JDK 1.8
     */
    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }

    /**
     * feignHttpMessageConverter:feignHttp消息转换 <br/>
     * @return ObjectFactory<HttpMessageConverters>
     * @since JDK 1.8
     */
    private ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(getFastjsonConverter());
        return () -> httpMessageConverters;
    }
    
    /**
     * getFastjsonConverter:Fastjson转换器 <br/>
     * @return FastJsonHttpMessageConverter
     * @since JDK 1.8
     */
    private FastJsonHttpMessageConverter getFastjsonConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        MediaType mediaTypeJson = MediaType.valueOf(MediaType.TEXT_PLAIN + ";charset=UTF-8");
        supportedMediaTypes.add(mediaTypeJson);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        FastJsonConfig config = new FastJsonConfig();
        config.getSerializeConfig().put(JSON.class, new SwaggerJsonSerializer());
        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        converter.setFastJsonConfig(config);
        return converter;
    }
}

