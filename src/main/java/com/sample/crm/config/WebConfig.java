package com.sample.crm.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sample.crm.filter.AccessLogFilter;
import com.sample.crm.filter.ValidateTokenFilter;



@Configuration
public class WebConfig 
{
  @Autowired
  private ApplicationContext context;

  /**
   * 
   * cross domain
   * 
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedHeaders("*").exposedHeaders("X-UNDONE");
      }
    };
  }
  
  /**
   * 
   * access log filter
   * 
   */
  @Bean
  public FilterRegistrationBean<AccessLogFilter> getAccessLogFilter()
  {
    AccessLogFilter filter = context.getBean(AccessLogFilter.class);
    FilterRegistrationBean<AccessLogFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(filter);
    List<String> urlPatterns = new ArrayList<String>();
    urlPatterns.add("/*");
    registrationBean.setUrlPatterns(urlPatterns);
    registrationBean.setOrder(0);
    return registrationBean;
  }
  
  
  @Bean
  public FilterRegistrationBean<ValidateTokenFilter> getValidateTokenFilter()
  {
    ValidateTokenFilter filter = context.getBean(ValidateTokenFilter.class);
    FilterRegistrationBean<ValidateTokenFilter> registrationBean = new FilterRegistrationBean<ValidateTokenFilter>();
    registrationBean.setFilter(filter);
    List<String> urlPatterns = new ArrayList<>();
    urlPatterns.add("/client/*");
    urlPatterns.add("/company/*");
    urlPatterns.add("/clients/*");
    registrationBean.setUrlPatterns(urlPatterns);
    registrationBean.setOrder(1);
    return registrationBean;
  }
}
