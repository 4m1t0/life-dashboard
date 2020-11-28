package com.monitoring.life.infrastructure.repository.flag;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "monthly_report_flag")
@Getter
@Accessors(fluent = true)
public class MonthlyReportFlagEntity implements Comparable<MonthlyReportFlagEntity> {

  @Id
  @Temporal(TemporalType.DATE)
  private Date month;

  private short jcb;
  private short mufg_normal;
  private short mufg_deposit;
  private short report;

  @Override
  public int compareTo(MonthlyReportFlagEntity other) {
    return this.month.compareTo(other.month);
  }
}
