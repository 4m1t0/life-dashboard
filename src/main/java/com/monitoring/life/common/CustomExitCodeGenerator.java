package com.monitoring.life.common;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomExitCodeGenerator
    implements ApplicationListener<JobExecutionEvent>, ExitCodeGenerator {

  private JobExecution jobExecution;

  @Override
  public void onApplicationEvent(JobExecutionEvent jobExecutionEvent) {
    this.jobExecution = jobExecutionEvent.getJobExecution();
  }

  /**
   * バッチが完了しなかった場合は異常ステータスでバッチを終了する．
   *
   * @return ステータスコード 0: 正常，1: 異常
   */
  @Override
  public int getExitCode() {
    ExitStatus exitStatus = this.jobExecution.getExitStatus();
    return ExitStatus.COMPLETED.getExitCode().equals(exitStatus.getExitCode()) ? 0 : 1;
  }
}
