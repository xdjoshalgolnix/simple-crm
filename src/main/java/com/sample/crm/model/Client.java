package com.sample.crm.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("client")
public class Client
{
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  
  @TableField(value = "company_id")
  private Long companyId;
  
  private String name;
  
  private String email;
  
  private String phone;
  
  @TableField(value = "created_by")
  private String createdBy;
  
  @TableField(value = "created_at")
  private Date createdAt;
  
  @TableField(value = "updated_by")
  private String updatedBy;
  
  @TableField(value = "updated_at")
  private Date updatedAt;
}
