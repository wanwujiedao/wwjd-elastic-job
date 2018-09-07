package annotations;

import com.qts.agent.web.elasticjob.configure.ElasticJobConfiguration;
import com.qts.agent.web.elasticjob.configure.InitElasticJob;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 Elasticjob
 *
 * @author 阿导
 * @CopyRight 杭州弧途科技有限公司(青团社)
 * @created 2018/8/20 15:42
 * @Modified_By 阿导 2018/8/20 15:42
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ ElasticJobConfiguration.class,InitElasticJob.class})
public @interface EnableElasticJob {
}