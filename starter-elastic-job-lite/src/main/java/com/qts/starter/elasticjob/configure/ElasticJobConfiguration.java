package com.qts.starter.elasticjob.configure;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.qts.starter.elasticjob.annotation.ElasticSimpleJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;

/**
 * job 配置中心
 *
 * @author 阿导
 * @CopyRight 杭州弧途科技有限公司(青团社)
 * @created 2018/7/30 19:13
 * @Modified_By 阿导 2018/7/30 19:13
 */
@Configuration
@ConditionalOnExpression("'${elaticjob.zookeeper.server-lists}'.length() > 0")
public class ElasticJobConfiguration {

	/**
	 * 注册中心
	 */
	@Value("${elaticjob.zookeeper.server-lists}")
	private String serverList;
	
	/**
	 * 任务空间
	 */
	@Value("${elaticjob.zookeeper.namespace}")
	private String namespace;
	
	/**
	 * 上下文
	 */
	@Autowired
	private ApplicationContext applicationContext;
  
  /**
   * 等其他依赖注入完成，进行初始化操作
   *
   * @author 阿导
   * @time 2018/7/30 19:39
   * @CopyRight 万物皆导
   * @param
   * @return
   */
	@PostConstruct
	public void initElasticJob(){
	  // 调度中心
		ZookeeperRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
		// 调度中心初始化
		regCenter.init();
		// 获取所有继承 SImpleJob 的调度任务
		Map<String, SimpleJob> map = applicationContext.getBeansOfType(SimpleJob.class);
		// 防止空指针
    if(CollectionUtils.isEmpty(map)){
      return;
    }
    // 遍历任务
    map.forEach((key,simpleJob)->{
      // 获取注解
      ElasticSimpleJob elasticSimpleJobAnnotation=simpleJob.getClass().getAnnotation(ElasticSimpleJob.class);
      
      // 这是定时任务间隔时间
      String cron=StringUtils.defaultIfBlank(elasticSimpleJobAnnotation.cron(), elasticSimpleJobAnnotation.value());
      // 任务名称
      String jobName=StringUtils.defaultIfBlank(elasticSimpleJobAnnotation.jobName(),simpleJob.getClass().getName());
      
      // 任务调度配置
      SimpleJobConfiguration simpleJobConfiguration=new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(jobName, cron, elasticSimpleJobAnnotation.shardingTotalCount()).shardingItemParameters(elasticSimpleJobAnnotation.shardingItemParameters()).build(), simpleJob.getClass().getCanonicalName());
     
      // 配置转换
      LiteJobConfiguration liteJobConfiguration=LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();
      
      // 调度任务
      String dataSourceRef=elasticSimpleJobAnnotation.dataSource();
      
      // 调度任务不为空，则调度任务
      if(StringUtils.isNotBlank(dataSourceRef)){
        // 上下文不包含调度任务
        if(!applicationContext.containsBean(dataSourceRef)){
          throw new RuntimeException("not exist datasource ["+dataSourceRef+"] !");
        }
        // 从上下文获取调度任务
        DataSource dataSource=(DataSource)applicationContext.getBean(dataSourceRef);
        JobEventRdbConfiguration jobEventRdbConfiguration=new JobEventRdbConfiguration(dataSource);
        SpringJobScheduler jobScheduler=new SpringJobScheduler(simpleJob, regCenter, liteJobConfiguration,jobEventRdbConfiguration);
        jobScheduler.init();
      }else{
        SpringJobScheduler jobScheduler=new SpringJobScheduler(simpleJob, regCenter, liteJobConfiguration);
        jobScheduler.init();
      }
    });
	}
}
