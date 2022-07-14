package com.sample.crm.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sample.crm.util.DateTimeUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CompanyResponse
{
  private long id;
  
  private String name;
  
  private String address;
  
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
