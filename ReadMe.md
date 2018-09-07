# spring-boot 集成 elastic-job

***

## starter-job 2.0 支持的配置如下

```properties

# 任务调度
job.rdb.driver=com.mysql.jdbc.Driver
job.rdb.url=jdbc:mysql://host:port/database?useSSL=false&useUnicode=true&characterEncoding=utf-8
job.rdb.username=username
job.rdb.password=password
job.center.server-lists=127.0.0.1:2181
job.center.namespace=job-name
job.center.base-sleep-time-milliseconds=1000
job.center.max-sleep-time-milliseconds=3000
job.center.max-retries=3

```

