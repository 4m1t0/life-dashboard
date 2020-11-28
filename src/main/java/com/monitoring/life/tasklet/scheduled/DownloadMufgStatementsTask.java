package com.monitoring.life.tasklet.scheduled;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class DownloadMufgStatementsTask implements Tasklet, StepExecutionListener {

  @Override
  public void beforeStep(StepExecution stepExecution) {}

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    return RepeatStatus.FINISHED;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return ExitStatus.COMPLETED;
  }
}
