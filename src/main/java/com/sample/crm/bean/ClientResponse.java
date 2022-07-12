package com.sample.crm.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sample.crm.util.DateTimeUtils;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonInclude(value = Include.NON_NULL)
public class ClientResponse
{
  private long id;
  
  @JsonProperty("company_id")
  private long companyId;
  
  private String name;
  
  private String email;
  
  private String phone;
  
  @JsonProperty("created_by")
  private String createdBy;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATE_TIME_FORMAT, timezone = DateTimeUtils.SYSTEM_TZ)
  @JsonProperty("created_at")
  private Date createdAt;
  
  @JsonProperty("updated_by")
  private String updatedBy;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATE_TIME_FORMAT, timezone = DateTimeUtils.SYSTEM_TZ)
  @JsonProperty("updated_at")
  private Date updatedAt;
}

