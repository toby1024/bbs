package info.asshead.bbs.web;

import info.asshead.bbs.annotation.Auth;
import info.asshead.bbs.common.CommonConstant;
import info.asshead.bbs.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
      return true;
    }
    final HandlerMethod handlerMethod = (HandlerMethod) handler;
    final Method method = handlerMethod.getMethod();
    final Class<?> clazz = method.getDeclaringClass();
    if (clazz.isAnnotationPresent(Auth.class) ||
        method.isAnnotationPresent(Auth.class)) {
      if (request.getSession().getAttribute(CommonConstant.SESSION_USER) == null) {

        log.info("未登录用户非法访问");
        throw  new AuthException("未登录用户非法访问");

      } else {
        return true;
      }
    }

    return true;
  }
}
