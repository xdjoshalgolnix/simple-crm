package com.sample.crm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("role_accesscontrol")
public class RoleAccessControl
{
  @TableField(value = "role_id")
  private long roleId;
  
  private String nouns;
}
