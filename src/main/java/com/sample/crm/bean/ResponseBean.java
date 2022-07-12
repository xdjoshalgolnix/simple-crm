package com.sample.crm.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ResponseBean<T>
{
  private String retCode = "S";
  
  private String retInfo = "OK";
  
  private T retData;
  
  public ResponseBean(final String retCode, final String retInfo)
  {
    this.retCode = retCode;
    this.retInfo = retInfo;
  }
}
