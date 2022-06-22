
package com.sample.crm.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * DataSource jdbcTemplate
 *
 */
@Configuration
public class DataSourceConfig
{
  @Bean(name = "h2")
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource1() {
   return DataSourceBuilder.create().build();
  }

  @Bean(name = "h2jdbc")
  public JdbcTemplate jdbcTemplate1(@Qualifier("h2") DataSource ds) {
   return new JdbcTemplate(ds);
  }
}
