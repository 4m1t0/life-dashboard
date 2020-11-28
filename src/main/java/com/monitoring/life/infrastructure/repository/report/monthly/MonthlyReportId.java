package com.monitoring.life.infrastructure.repository.report.monthly;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class MonthlyReportId implements Serializable {

  private Date date;
  private String type;
}
