package com.ticket.calculator;

import com.ticket.calculator.client.TicketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients(basePackageClasses = {TicketClient.class})
public class CalculatorApplicationBoot
{

   public static void main(String[] args)
   {
      SpringApplication.run(CalculatorApplicationBoot.class, args);
   }

   @Bean
   public Docket api(){
      return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
              .paths(PathSelectors.any()).build();
   }

   @Bean
   public RestTemplate rest(RestTemplateBuilder builder) {
      return builder.build();
   }
}
