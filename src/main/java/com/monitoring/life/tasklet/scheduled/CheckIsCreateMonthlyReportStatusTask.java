package com.monitoring.life.tasklet.scheduled;

import com.monitoring.life.infrastructure.repository.flag.MonthlyReportFlagEntity;
import com.monitoring.life.infrastructure.repository.flag.MonthlyReportFlagRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckIsCreateMonthlyReportStatusTask implements Tasklet, StepExecutionListener {

  private static final String ERROR_INVALID_STATE = "データベースのレコードが不正な状態です．";
  private static final ZoneId ZONE_TOKYO = ZoneId.of("Asia/Tokyo");

  private final MonthlyReportFlagRepository monthlyReportFlagRepository;
  private Optional<MonthlyReportFlagEntity> entityOptional;

  @Override
  public void beforeStep(StepExecution stepExecution) {}

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    this.entityOptional =
        StreamSupport.stream(monthlyReportFlagRepository.findAll().spliterator(), false)
            .max(Comparator.comparing(MonthlyReportFlagEntity::month));
    return RepeatStatus.FINISHED;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    if (entityOptional.isEmpty()) {
      log.error(ERROR_INVALID_STATE);
      return ExitStatus.FAILED;
    }

    MonthlyReportFlagEntity monthlyReportFlagEntity = entityOptional.get();
    LocalDate recent = monthlyReportFlagEntity.month().toInstant().atZone(ZONE_TOKYO).toLocalDate();

    if (LocalDate.now(ZONE_TOKYO).getMonthValue() - 1 == recent.getMonthValue()) {
      if (monthlyReportFlagEntity.jcb() == 0
          || monthlyReportFlagEntity.mufg() == 0
          || monthlyReportFlagEntity.report() == 0) {
        stepExecution
            .getJobExecution()
            .getExecutionContext()
            .put("lastStatus", monthlyReportFlagEntity);
        return ExitStatus.COMPLETED;
      }
    }

    return ExitStatus.NOOP;
  }
}
