package annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * job 配置注解
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018/7/30 19:14
 * @Modified_By 阿导 2018/7/30 19:14
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticSimpleJob {
	
	/**
	 * 定时表达式
	 */
	@AliasFor("cron")
	String value() default "";
	
	/**
	 * 定时表达式
	 */
	@AliasFor("value")
	String cron() default "";
	
	/**
	 * 任务名称
	 */
	String jobName() default "";
	
	int shardingTotalCount() default 1;
	
	String shardingItemParameters() default "0=local";

	String jobParameter() default "";
	
	boolean monitorExecution() default true;
	
	int monitorPort() default 0;

	String dataSource() default "";
	
	String description() default "";

	boolean failover() default true;
	boolean disabled() default false;

	boolean overwrite() default true;
	
}