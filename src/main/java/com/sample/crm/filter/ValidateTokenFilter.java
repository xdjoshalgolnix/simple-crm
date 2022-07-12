package com.sample.crm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.sample.crm.bean.UserInfo;
import com.sample.crm.constant.APIConstant;
import com.sample.crm.exception.APIException;
import com.sample.crm.services.AccessControlService;
import com.sample.crm.services.UserService;

//@Slf4j
@Component
public class ValidateTokenFilter implements Filter
{
//  private Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private AccessControlService accessControlService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
  {
    HttpServletRequest httpRequest = (HttpServletRequest)request;
    if ( httpRequest.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString()) )
    {
      filterChain.doFilter(request, response);
      return;
    }
    HttpServletRequest httprequest = (HttpServletRequest)request;
    String method = httprequest.getMethod();

    UserInfo userInfo = null;
    String usertoken = this.getUserToken(httprequest);
    try
    {
      userInfo = userService.getUserInfo(usertoken);
      accessControlService.valid(method, userInfo);
    }
    catch ( APIException e )
    {
      throw new ServletException(e);
    }
    
    request.setAttribute(APIConstant.CURRENT_USER, userInfo);
    filterChain.doFilter(request, response);
    return;
  }

  private String getUserToken(HttpServletRequest httprequest)
  {
    String usertoken = httprequest.getHeader(APIConstant.HEADER_AUTHORIZATION);
    if ( usertoken != null )
    {
      usertoken = usertoken.trim();
      if ( usertoken.startsWith(APIConstant.BearerPrefix) && usertoken.length() > APIConstant.BearerPrefix.length() )
      {
        usertoken = usertoken.substring(APIConstant.BearerPrefix.length());
      }
    }
    if ( usertoken == null )
    {
      usertoken = (String)httprequest.getParameter(APIConstant.PARAM_ACCESSTOKEN);
    }
    return usertoken;
  }
}
