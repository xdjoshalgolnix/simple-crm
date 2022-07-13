package com.sample.crm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sample.crm.constant.APIConstant;

import lombok.Data;

@Data
@TableName("user")
public class User
{
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  
  @TableField(value = "user_id")
  private String userId;
  
  private String hashpwd;
  
  private String name;
  
  @TableField(value = "role_id")
  private long roleId;
  
  private int state = APIConstant.STATE_ENABLE;
}
