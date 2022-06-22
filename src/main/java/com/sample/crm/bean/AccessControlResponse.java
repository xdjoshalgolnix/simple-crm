package com.sample.crm.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class AccessControlResponse
{
  private String nouns;
  
  public AccessControlResponse()
  {
    
  }

  public AccessControlResponse(String nouns)
  {
    this.nouns = nouns;
  }
  
  public String getNouns()
  {
    return nouns;
  }

  public void setNouns(String nouns)
  {
    this.nouns = nouns;
  }
}
