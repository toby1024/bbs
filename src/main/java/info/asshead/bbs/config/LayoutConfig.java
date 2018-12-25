package info.asshead.bbs.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jason
 */
@Configuration
public class LayoutConfig {
  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }
}
