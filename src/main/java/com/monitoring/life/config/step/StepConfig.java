package com.monitoring.life.config.step;

import com.monitoring.life.tasklet.scheduled.CheckIsCreateMonthlyReportStatusTask;
import com.monitoring.life.tasklet.scheduled.CheckIsReadyToCreateMonthlyReportTask;
import com.monitoring.life.tasklet.scheduled.CreateMonthlyReportTask;
import com.monitoring.life.tasklet.scheduled.DownloadJcbStatementsTask;
import com.monitoring.life.tasklet.scheduled.DownloadMufgStatementsTask;
import com.monitoring.life.tasklet.recovery.RecoverMonthlyReportTask;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StepConfig {

  private final StepBuilderFactory stepBuilderFactory;
  private final CheckIsCreateMonthlyReportStatusTask checkIsCreateMonthlyReportStatusTask;
  private final DownloadJcbStatementsTask downloadJcbStatementsTask;
  private final DownloadMufgStatementsTask downloadMufgStatementsTask;
  private final CheckIsReadyToCreateMonthlyReportTask checkIsReadyToCreateMonthlyReportTask;
  private final CreateMonthlyReportTask createMonthlyReportTask;
  private final RecoverMonthlyReportTask recoverMonthlyReportTask;

  @Bean("checkIsCreate")
  Step checkIsCreateStep() {
    return this.stepBuilderFactory
        .get("isCreateMonthlyReportStep")
        .tasklet(this.checkIsCreateMonthlyReportStatusTask)
        .build();
  }

  @Bean("downloadJcbStatements")
  Step downloadJcbStatementsStep() {
    return this.stepBuilderFactory
        .get("downloadJcbStatementsStep")
        .tasklet(this.downloadJcbStatementsTask)
        .build();
  }

  @Bean("downloadMufgStatements")
  Step downloadMufgStatementsStep() {
    return this.stepBuilderFactory
        .get("downloadMufgStatementsStep")
        .tasklet(this.downloadMufgStatementsTask)
        .build();
  }

  @Bean("checkIsReadyToCreate")
  Step checkIsReadyToCreateStep() {
    return this.stepBuilderFactory
        .get("isReadyToCreateMonthlyReportStep")
        .tasklet(this.checkIsReadyToCreateMonthlyReportTask)
        .build();
  }

  @Bean("createMonthlyReport")
  Step createMonthlyReportStep() {
    return this.stepBuilderFactory
        .get("createMonthlyReportStep")
        .tasklet(this.createMonthlyReportTask)
        .build();
  }

  @Bean("recoverMonthlyReport")
  Step recoverMonthlyReportStep() {
    return this.stepBuilderFactory
        .get("recoverMonthlyReportStep")
        .tasklet(this.recoverMonthlyReportTask)
        .build();
  }
}
