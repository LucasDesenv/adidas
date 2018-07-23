package com.travel.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalTime;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
public class TravelApplicationBoot
{


   public static void main(String[] args)
   {
      SpringApplication.run(TravelApplicationBoot.class, args);
   }

   @Bean
   public Docket api(){
      return new Docket(DocumentationType.SWAGGER_2).directModelSubstitute(LocalTime.class, String.class).select().apis(RequestHandlerSelectors.any())
              .paths(PathSelectors.any()).build();
   }
}
