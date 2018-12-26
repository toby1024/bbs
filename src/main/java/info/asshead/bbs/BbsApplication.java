package info.asshead.bbs;

import info.asshead.bbs.web.AuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@SpringBootApplication
public class BbsApplication {

  public static void main(String[] args) {
    SpringApplication.run(BbsApplication.class, args);
  }

}

