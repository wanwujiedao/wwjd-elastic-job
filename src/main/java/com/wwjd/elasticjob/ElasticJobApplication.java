package com.wwjd.elasticjob;

import com.qts.starter.elasticjob.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018/7/31 19:14
 * @Modified_By 阿导 2018/7/31 19:14
 */
@SpringBootApplication
@EnableElasticJob
public class ElasticJobApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(ElasticJobApplication.class, args);
  }
}
