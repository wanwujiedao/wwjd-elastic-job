package com.wwjd.elasticjob.job;

import com.dangdang.ddframe.job.api.simple.SimpleJob;

import com.qts.starter.elasticjob.annotation.ElasticSimpleJob;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;

import java.io.*;

/**
 * 我的定时器
 *
 * @author 阿导
 * @CopyRight 杭州弧途科技有限公司(青团社)
 * @created 2018/7/31 19:14
 * @Modified_By 阿导 2018/7/31 19:14
 */
@ElasticSimpleJob(cron="*/5 * * * * ?",jobName="wanwujiedao",shardingTotalCount=2,jobParameter="测试参数",shardingItemParameters="0=A,1=B")
@Component
public class MyJob implements SimpleJob {

    @Override
    public void execute(ShardingContext content) {
        OutputStream output = null;
        try {
            output = new FileOutputStream("job.log");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PrintStream printOut = new PrintStream(output);
        System.setOut(printOut);
    
        System.out.println("JobName:"+content.getJobName());
        System.out.println("JobParameter:"+content.getJobParameter());
        System.out.println("ShardingItem:"+content.getShardingItem());
        System.out.println("ShardingParameter:"+content.getShardingParameter());
        System.out.println("ShardingTotalCount:"+content.getShardingTotalCount());
        System.out.println("TaskId:"+content.getTaskId());
        System.out.println("---------------------------------------");
    }
}