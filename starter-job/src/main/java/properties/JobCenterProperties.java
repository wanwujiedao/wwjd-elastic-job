package properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author 阿导
 * @CopyRight 青团社
 * @created 2018年08月20日 11:21:00
 * @Modified_By 阿导 2018/8/20 11:21
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "job.center")
public class JobCenterProperties {
  
  /**
   * job 中心地址
   */
  private String serverLists;
  /**
   * job 命名空间
   */
  private String namespace;
  /**
   * 睡眠时间
   */
  private int baseSleepTimeMilliseconds=1000;
  /**
   * 最大休眠时间
   */
  private int maxSleepTimeMilliseconds=3000;
  /**
   * 重试次数
   */
  private int maxRetries=3;
  
  public String getServerLists() {
    return serverLists;
  }
  
  public void setServerLists(String serverLists) {
    this.serverLists = serverLists;
  }
  
  public String getNamespace() {
    return namespace;
  }
  
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }
  
  public int getBaseSleepTimeMilliseconds() {
    return baseSleepTimeMilliseconds;
  }
  
  public void setBaseSleepTimeMilliseconds(int baseSleepTimeMilliseconds) {
    this.baseSleepTimeMilliseconds = baseSleepTimeMilliseconds;
  }
  
  public int getMaxSleepTimeMilliseconds() {
    return maxSleepTimeMilliseconds;
  }
  
  public void setMaxSleepTimeMilliseconds(int maxSleepTimeMilliseconds) {
    this.maxSleepTimeMilliseconds = maxSleepTimeMilliseconds;
  }
  
  public int getMaxRetries() {
    return maxRetries;
  }
  
  public void setMaxRetries(int maxRetries) {
    this.maxRetries = maxRetries;
  }
}
