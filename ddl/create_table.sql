CREATE TABLE IF NOT EXISTS monthly_report(
  date DATE NOT NULL,
  type VARCHAR(15) NOT NULL,
  amount INT NOT NULL,
  PRIMARY KEY (date, type)
);

CREATE TABLE IF NOT EXISTS monthly_report_flag(
  month DATE NOT NULL,
  jcb TINYINT NOT NULL,
  mufg TINYINT NOT NULL,
  report TINYINT NOT NULL,
  PRIMARY KEY (month)
);