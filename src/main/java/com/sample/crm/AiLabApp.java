package com.sample.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
@MapperScan(basePackages = {"com.sample.crm.mapper"})
public class AiLabApp
{
  public static void main(String[] args)
  {
    SpringApplication.run(AiLabApp.class, args);
    return;
  }
}
