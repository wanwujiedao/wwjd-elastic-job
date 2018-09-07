package configure;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import properties.JobCenterProperties;
import properties.JobRdbProperties;

import java.sql.SQLException;

/**
 * @author 阿导
 * @CopyRight 青团社
 * @created 2018年08月20日 11:18:00
 * @Modified_By 阿导 2018/8/20 11:18
 */
@Configuration
@Import({JobCenterProperties.class, JobRdbProperties.class})
public class ElasticJobConfiguration  {
  

  @Autowired
  private JobCenterProperties jobCenterProperties;
  @Autowired
  private JobRdbProperties jobRdbProperties;
  

 
  @Bean(initMethod = "init")
  public ZookeeperRegistryCenter jobCenter() {
    ZookeeperConfiguration config = new ZookeeperConfiguration(jobCenterProperties.getServerLists(), jobCenterProperties.getNamespace());
    config.setBaseSleepTimeMilliseconds(jobCenterProperties.getBaseSleepTimeMilliseconds());
    config.setMaxSleepTimeMilliseconds(jobCenterProperties.getMaxSleepTimeMilliseconds());
    config.setMaxRetries(jobCenterProperties.getMaxRetries());
    ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(config);
   try {
     zookeeperRegistryCenter.init();
   }catch (Exception e){
     throw new RuntimeException(e);
   }
    return zookeeperRegistryCenter;
  }
  
  @Bean(initMethod = "init")
  public DruidDataSource elasticJobLog() {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setDriverClassName(jobRdbProperties.getDriver());
    dataSource.setUrl(jobRdbProperties.getUrl());
    dataSource.setUsername(jobRdbProperties.getUsername());
    dataSource.setPassword(jobRdbProperties.getPassword());
    try {
      dataSource.init();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return dataSource;
  }
  
  
}
