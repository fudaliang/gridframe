package com.softcloud.grid.privadmin.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.softcloud.grid.common.intercepter.AuthIntercepter;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableWebMvc
@EnableSwagger2
public class Swagger2Config  {
//todo 开启 swagger 功能失效
//    @Value("${SWAGGER.ENABLE}")
//    private boolean swaggerEnable;

    @Value("${SWAGGER.TITLE}")
    private String swaggerTitle;

    @Value("${SWAGGER.DESC}")
    private String swaggerDesc;
    
    @Bean
    public AuthIntercepter authIntercepter() {
        return new AuthIntercepter();
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration addInterceptor = registry.addInterceptor(authIntercepter());
//
//        // 排除配置
//        addInterceptor.excludePathPatterns("/error");
//        addInterceptor.excludePathPatterns("/login**");
//        addInterceptor.excludePathPatterns("/test**");
//        addInterceptor.excludePathPatterns("/swagger-resources/**");
//        addInterceptor.excludePathPatterns("/v2/**");
//        addInterceptor.excludePathPatterns("/swagger-ui.html**");
//        addInterceptor.excludePathPatterns("/webjars/springfox-swagger-ui/**");
//        addInterceptor.excludePathPatterns("/csrf/**");
//        addInterceptor.excludePathPatterns("/*/v2/api-docs");
//        
//        
//        // 拦截配置
//        addInterceptor.addPathPatterns("/**");
//    }

    
    @Bean
    public Docket createRestApi() {
    	
    	  ParameterBuilder tokenPar = new ParameterBuilder();
          List<Parameter> pars = new ArrayList<>();
          tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
          pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.softcloud.grid.privadmin"))
                //扫描所有有注解的api，用这种方式更灵活
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                ;
        
    }


    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title(swaggerTitle)
                //描述
                .description(swaggerDesc)
                //服务条款
                .termsOfServiceUrl("http://localhost:8002")
                //版本号
                .version("1.0.0")
                //许可证
                .license("LICENSE")
                //许可证地址
                .licenseUrl("http://localhost:8002")
                .build();
    }


//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if (swaggerEnable) {
//            registry.addResourceHandler("swagger-ui.html")
//                    .addResourceLocations("classpath:/META-INF/resources/");
//
//            registry.addResourceHandler("/webjars/**")
//                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        }
//    }
    
  //通过Swagger2的securitySchemes配置全局参数：如下列代码所示，securitySchemes的ApiKey中增加一个名为“Authorization”，type为“header”的参数。
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList= new ArrayList();
        //apiKeyList.add(new ApiKey("x-auth-token", "x-auth-token", "header"));
      //apiKeyList.add(new ApiKey("apikey", "Authorization", "header"));
        // apikey 的值取决于应用编写时验证的头字段标记，这个应用定义在 com.softcloud.grid.common.constants;
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        
        return apiKeyList;
    	
    }
//在Swagger2的securityContexts中通过正则表达式，设置需要使用参数的接口（或者说，是去除掉不需要使用参数的接口），如下列代码所示，通过PathSelectors.regex("^(?!auth).*$")，所有包含"auth"的接口不需要使用securitySchemes。即不需要使用上文中设置的名为“Authorization”，type为“header”的参数。
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}