package com.sample.crm.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sample.crm.bean.ResponseBean;
import com.sample.crm.constant.APIConstant;
import com.sample.crm.constant.CommonConstant;
import com.sample.crm.exception.APIException;


@Component
public class AccessLogFilter implements Filter, APIConstant, CommonConstant
{
  private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  private static ObjectMapper mapper = new ObjectMapper();
  static
  {
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    if( httpRequest.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString()) )
    {
      chain.doFilter(request, response);
      return;
    }
    
    long processed = 0L;
    boolean hasError = false;
    Throwable error = new Throwable();
    try
    {
      long start = System.currentTimeMillis();
      chain.doFilter(request, response);
      processed = System.currentTimeMillis() - start;
    }
    catch( Throwable t )
    {
      hasError = true;
      error = t;
      if ( t instanceof APIException )
      {
        APIException apie = (APIException)t;
        response(httpRequest, response, apie.getCode().getCode(), new ResponseBean<String>(apie.getCode().getRetCode(), apie.getMessage(), apie.getRetData()));
      }
      else if ( t.getCause() != null && t.getCause() instanceof APIException )
      {
        APIException apie = (APIException)t.getCause();
        error = t.getCause();
        response(httpRequest, response, apie.getCode().getCode(), new ResponseBean<String>(apie.getCode().getRetCode(), apie.getMessage(), apie.getRetData()));
      }
      else
      {
        response(httpRequest, response, HttpStatus.INTERNAL_SERVER_ERROR.value(), new ResponseBean<String>(APIConstant.RETCODE_PGRM1, t.getMessage()));
      }
    }
    finally
    {
      if ( hasError )
      {
        if( error instanceof APIException )
        {
          APIException apie = (APIException)error;
          log.error(String.format("%s | %s | %s | %s | %s | %d | %d ms | %s", httpRequest.getServerName(), httpRequest.getRemoteAddr(), httpRequest.getMethod(),
                                  httpRequest.getRequestURI(), httpRequest.getHeader("user-agent"), apie.getCode().getCode(), processed, apie.getMessage()));
        }
        else
        {
          log.error(String.format("%s | %s | %s | %s | %s | %d | %d ms | %s", httpRequest.getServerName(), httpRequest.getRemoteAddr(), httpRequest.getMethod(),
                                  httpRequest.getRequestURI(), httpRequest.getHeader("user-agent"), HttpStatus.INTERNAL_SERVER_ERROR.value(), processed,
                                  error.getMessage()));
        }
      }
      else
      {
        // hostname, remote_address, HTTP Method, Endpoint name(path), User agent, Status code, Request time in microseconds, message, curl
        log.info(String.format("%s | %s | %s | %s | %s | %d | %d ms | %s", httpRequest.getServerName(), httpRequest.getRemoteAddr(), httpRequest.getMethod(),
                               httpRequest.getRequestURI(), httpRequest.getHeader("user-agent"), HttpStatus.OK.value(), processed, "")); 
      }
    }
  }

  @Override
  public void destroy()
  {
    return;
  }
  
  private void response(HttpServletRequest httpRequest, ServletResponse response, int rc, ResponseBean<String> result) throws IOException
  {
    HttpServletResponse httpesponse = (HttpServletResponse)response;
    httpesponse.setStatus(rc);
    httpesponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
    if( httpRequest.getHeader(Header_ORIGIN) != null )
    {
      httpesponse.setHeader(Header_ACCESS_CONTROL_ALLOW_CREDENTIALS, TRUE);
      httpesponse.setHeader(Header_ACCESS_CONTROL_ALLOW_ORIGIN, ALLOW_ALL);
      httpesponse.setHeader(Header_VARY, Header_ORIGIN);
    }
    ServletOutputStream os = httpesponse.getOutputStream();
    mapper.writeValue(os, result);
    os.flush();
    os.close();
    httpesponse.flushBuffer();
    return;
  }
}
