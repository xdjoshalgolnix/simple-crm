
package com.sample.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.annotations.DynamicUpdate;

import com.sample.crm.entity.cpk.RoleAccessControlId;

import lombok.Data;


@Data
@Entity(name = "role_accesscontrol")
@DynamicUpdate
@IdClass(value = RoleAccessControlId.class)
public class RoleAccessControl
{
  @Id
  @Column(name = "role_id")
  private long roleId;
  
  @Id
  @Column(nullable = false)
  private String nouns;
}
