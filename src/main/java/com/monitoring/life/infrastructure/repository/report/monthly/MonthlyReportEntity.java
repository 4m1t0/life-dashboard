package com.monitoring.life.infrastructure.repository.report.monthly;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.experimental.Accessors;

@Entity
@IdClass(MonthlyReportId.class)
@Table(name = "monthly_report")
@Getter
@Accessors(fluent = true)
public class MonthlyReportEntity {

  @Id
  @Temporal(TemporalType.DATE)
  private Date date;

  @Id private String type;

  private int amount;
}
