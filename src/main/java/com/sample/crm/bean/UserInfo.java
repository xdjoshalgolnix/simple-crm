package com.sample.crm.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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
}
