
package com.sample.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.annotations.DynamicUpdate;

import com.sample.crm.entity.cpk.RoleAccessControlId;



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

  public long getRoleId()
  {
    return roleId;
  }

  public void setRoleId(long roleId)
  {
    this.roleId = roleId;
  }
  
  public String getNouns()
  {
    return nouns;
  }

  public void setNouns(String nouns)
  {
    this.nouns = nouns;
  }
}
