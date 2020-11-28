# life-dashboard

一人暮らし用のメトリクスを監視するためのダッシュボード

## Memo

1. 以下に従ってUbuntu 18.04 LTSのイメージでGCPのCompute Engineインスタンスを立ち上げ，MySQLを導入する  
https://cloud.google.com/solutions/mysql-remote-access?hl=ja

2. ユーザを作成する．  
```bash
$ sudo mysql -uroot -p<root_password> -e "CREATE USER '<grafana_account_username>' IDENTIFIED BY '<grafana_account_password>';"
$ sudo mysql -uroot -p<root_password> -e "CREATE USER '<batch_account_username>' IDENTIFIED BY '<batch_account_password>';"
```

3. データベースを作成する．  
```sql
CREATE DATABASE life_monitoring;
USE life_monitoring;
```

4. テーブルを作成する．  
```sql
CREATE TABLE monthly_report(
  date timestamp,
  type VARCHAR(15),
  amount int
);
```

5. 権限を付与する．  
```sql
GRANT SELECT ON <DB name>.monthly_report TO <grafana_account_username>;
GRANT SELECT, INSERT, UPDATE ON <DB name>..monthly_report_flag TO <batch_account_username>;
GRANT SELECT, INSERT, UPDATE ON <DB name>..monthly_report TO <batch_account_username>;
```

6. 日本語を扱えるようにする．

```bash
$ sudo vim /etc/mysql/my.cnf
# 以下を追加
[mysql]
default-character-set=utf8

[mysqldump]
default-character-set=utf8

[mysqld]
character-set-server=utf8

# MySQL再起動と確認
$ sudo service mysql restart
$ sudo mysql -uroot -p<root_password> -e "status";
```
