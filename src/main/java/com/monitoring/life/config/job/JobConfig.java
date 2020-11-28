package com.monitoring.life.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

  private final JobBuilderFactory jobBuilderFactory;

  @Bean
  Job recoverMonthlyReportJob(@Qualifier("downloadJcbStatements") Step downloadJcbStatementsStep) {
    return this.jobBuilderFactory
        .get("recoverMonthlyReportJob")
        .start(downloadJcbStatementsStep)
        .build();
  }

  @Bean
  Job createMonthlyReportJob(
      @Qualifier("checkIsCreate") Step checkIsCreateStep,
      @Qualifier("downloadJcbStatements") Step downloadJcbStatementsStep,
      @Qualifier("downloadMufgStatements") Step downloadMufgStatementsStep,
      @Qualifier("checkIsReadyToCreate") Step checkIsReadyToCreateStep,
      @Qualifier("createMonthlyReport") Step createMonthlyReportStep,
      @Qualifier("recoverMonthlyReport") Step recoverMonthlyReportStep) {
    return this.jobBuilderFactory
        .get("createMonthlyReportJob")
        .start(checkIsCreateStep)
        .on("NOOP")
        .end()
        .from(checkIsCreateStep)
        .on("FAILED")
        .to(recoverMonthlyReportStep)
        .from(checkIsCreateStep)
        .on("COMPLETED")
        .to(downloadJcbStatementsStep)
        .from(downloadJcbStatementsStep)
        .on("*")
        .to(downloadMufgStatementsStep)
        .end()
        .build();
  }
}
