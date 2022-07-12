package com.sample.crm.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonInclude(value = Include.NON_NULL)
public class ClientRequest
{
  @JsonProperty("company_id")
  private long companyId;
  
  private String name;
  
  private String email;
  
  private String phone;
}
