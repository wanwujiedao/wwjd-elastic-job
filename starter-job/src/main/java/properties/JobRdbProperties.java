package properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author 阿导
 * @CopyRight 青团社
 * @created 2018年08月20日 11:20:00
 * @Modified_By 阿导 2018/8/20 11:20
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "job.rdb")
public class JobRdbProperties {

  /**
   * 数据库驱动
   */
  private String driver="com.mysql.jdbc.Driver";
  /**
   * 连接地址
   */
  private String url;
  
  /**
   * 用户名
   */
  private String  username;
  /**
   * 密码
   */
  private String password;
  
  public String getDriver() {
    return driver;
  }
  
  public void setDriver(String driver) {
    this.driver = driver;
  }
  
  public String getUrl() {
    return url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
}
