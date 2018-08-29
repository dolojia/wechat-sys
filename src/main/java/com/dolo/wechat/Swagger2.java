package com.dolo.wechat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: swagger2 API文档生成<br>
 * 作者: dolojia
 * 修改日期: 2018/8/29 14:38
 * E-mail: dolojia@gmail.com
 **/
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> operationParameters = new ArrayList<Parameter>();
        ticketPar.name("appid").description("接口调用APPID")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build(); //header中的appid参数必填
        operationParameters.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                // swagger扫描包
                .apis(RequestHandlerSelectors.basePackage("com.dolo.wechat")).paths(PathSelectors.any())
                .build().globalOperationParameters(operationParameters)
                // swagger请求环境配置host
                .host("dolojia.top");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("wechat-sys").description("微信系统文档").version("1.0")
                .build();
    }
}
