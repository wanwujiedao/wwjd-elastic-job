package configure;

import annotations.ElasticSimpleJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 初始化
 *
 * @author 阿导
 * @CopyRight 青团社
 * @created 2018年08月20日 19:00:00
 * @Modified_By 阿导 2018/8/20 19:00
 */
@Configuration
public class InitElasticJob implements InitializingBean {
  
  /**
   * 上下文
   */
  @Autowired
  private ApplicationContext applicationContext;
  
  @Autowired
  ZookeeperRegistryCenter jobCenter;
  @Autowired
  DataSource elasticJobLog;
  
  @Override
  public void afterPropertiesSet() throws Exception {
    
    // 获取所有继承 SImpleJob 的调度任务
    Map<String, SimpleJob> map = applicationContext.getBeansOfType(SimpleJob.class);
    // 防止空指针
    if (CollectionUtils.isEmpty(map)) {
      return;
    }
    // 遍历任务
    map.forEach((key, simpleJob) -> {
      // 获取注解
      ElasticSimpleJob elasticSimpleJobAnnotation = simpleJob.getClass().getAnnotation(ElasticSimpleJob.class);
      
      // 配置
      JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(
          // job 名称
          StringUtils.defaultIfBlank(elasticSimpleJobAnnotation.jobName(), simpleJob.getClass().getName()),
          // 定时配置
          StringUtils.defaultIfBlank(elasticSimpleJobAnnotation.cron(), elasticSimpleJobAnnotation.value()),
          // 分片
          elasticSimpleJobAnnotation.shardingTotalCount()).
          // 参数
              shardingItemParameters(elasticSimpleJobAnnotation.shardingItemParameters()).
          // 描述
              description(elasticSimpleJobAnnotation.description()).
          //
              failover(elasticSimpleJobAnnotation.failover()).
              build();
      
      
      // 任务调度配置
      SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, simpleJob.getClass().getCanonicalName());
      
      
      // 配置转换
      LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).
          overwrite(elasticSimpleJobAnnotation.overwrite()).
          disabled(elasticSimpleJobAnnotation.disabled()).
          monitorExecution(elasticSimpleJobAnnotation.monitorExecution()).
          monitorPort(elasticSimpleJobAnnotation.monitorPort()).build();
      
      // 调度任务
      String dataSourceRef = elasticSimpleJobAnnotation.dataSource();
      
      JobEventRdbConfiguration jobEventRdbConfiguration;
      // 调度任务不为空，则调度任务
      if (StringUtils.isNotBlank(dataSourceRef)) {
        // 上下文不包含调度任务
        if (!applicationContext.containsBean(dataSourceRef)) {
          throw new RuntimeException("not exist datasource [" + dataSourceRef + "] !");
        }
        // 从上下文获取调度任务
        DataSource dataSource = (DataSource) applicationContext.getBean(dataSourceRef);
        jobEventRdbConfiguration = new JobEventRdbConfiguration(dataSource);
        
      } else {
        jobEventRdbConfiguration = new JobEventRdbConfiguration(elasticJobLog);
      }
      SpringJobScheduler jobScheduler = new SpringJobScheduler(simpleJob, jobCenter, liteJobConfiguration, jobEventRdbConfiguration);
       jobScheduler.init();
    });
  }
  
}
