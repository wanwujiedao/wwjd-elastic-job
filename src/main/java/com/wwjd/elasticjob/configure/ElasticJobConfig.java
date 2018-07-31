package com.wwjd.elasticjob.configure;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 配置类
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018/7/25 16:52
 * @Modified_By 阿导 2018/7/25 16:52
 */
@Configuration
public class ElasticJobConfig {

  @Bean(initMethod = "init")
  public ZookeeperRegistryCenter regCenter(@Value("${elaticjob.zookeeper.server-lists}") final String serverList, @Value("${elaticjob.zookeeper.namespace}") final String namespace) {
    return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
  }
}