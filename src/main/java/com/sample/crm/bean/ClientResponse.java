package com.sample.crm.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sample.crm.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

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

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public Long getCompanyId()
  {
    return companyId;
  }

  public void setCompanyId(Long companyId)
  {
    this.companyId = companyId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public String getCreatedBy()
  {
    return createdBy;
  }

  public void setCreatedBy(String createdBy)
  {
    this.createdBy = createdBy;
  }

  public void setCompanyId(long companyId)
  {
    this.companyId = companyId;
  }

  public Date getCreatedAt()
  {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt)
  {
    this.createdAt = createdAt;
  }

  public String getUpdatedBy()
  {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy)
  {
    this.updatedBy = updatedBy;
  }

  public Date getUpdatedAt()
  {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt)
  {
    this.updatedAt = updatedAt;
  }
}

