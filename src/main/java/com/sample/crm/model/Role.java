package com.sample.crm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("role")
public class Role
{
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  
  private String role;
}
