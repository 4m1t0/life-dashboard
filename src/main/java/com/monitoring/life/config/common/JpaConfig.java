package com.monitoring.life.config.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = {
      "com.monitoring.life.infrastructure.repository.flag",
      "com.monitoring.life.infrastructure.repository.report.monthly"
    })
public class JpaConfig {}
