package com.monitoring.life.config.common;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.driverClassName}")
  private String driverClassName;

  @Value("${spring.datasource.sql-script-encoding}")
  private String sqlScriptEncoding;

  /** メタデータテーブルを利用しないように設定 */
  @Override
  public void setDataSource(DataSource dataSource) {}

  @Bean
  DataSource dataSource() {
    final Properties dataSourceProperties = new Properties();
    dataSourceProperties.setProperty("sql-script-encoding", this.sqlScriptEncoding);

    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(this.driverClassName);
    dataSource.setUrl(this.url);
    dataSource.setUsername(this.username);
    dataSource.setPassword(this.password);
    dataSource.setConnectionProperties(dataSourceProperties);

    return dataSource;
  }
}
