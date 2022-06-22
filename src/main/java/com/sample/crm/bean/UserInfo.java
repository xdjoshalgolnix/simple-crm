package com.sample.crm.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class UserInfo
{
  private Long id;
  
  @JsonProperty("user_id")
  private String userId;
  
  private String name;

  private String token;
  
  private String role;
  
  @JsonProperty("access_control")
  private List<AccessControlResponse> accessControls;

  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String getToken()
  {
    return token;
  }

  public void setToken(String token)
  {
    this.token = token;
  }

  public String getRole()
  {
    return role;
  }

  public void setRole(String role)
  {
    this.role = role;
  }

  public List<AccessControlResponse> getAccessControls()
  {
    return accessControls;
  }

  public void setAccessControls(List<AccessControlResponse> accessControls)
  {
    this.accessControls = accessControls;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }
}
