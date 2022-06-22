package com.sample.crm.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ResponseBean<T>
{
  private String retCode = "S";
  
  private String retInfo = "OK";
  
  private T retData;
  
  public ResponseBean()
  {
    
  }
  
  public ResponseBean(final String retCode, final String retInfo)
  {
    this.retCode = retCode;
    this.retInfo = retInfo;
  }
  
  public ResponseBean(final String retCode, final String retInfo, final T retData)
  {
    this.retCode = retCode;
    this.retInfo = retInfo;
    this.retData = retData;
  }

  public String getRetCode()
  {
    return retCode;
  }

  public void setRetCode(String retCode)
  {
    this.retCode = retCode;
  }

  public String getRetInfo()
  {
    return retInfo;
  }

  public void setRetInfo(String retInfo)
  {
    this.retInfo = retInfo;
  }

  public T getRetData()
  {
    return retData;
  }

  public void setRetData(T retData)
  {
    this.retData = retData;
  }
}
