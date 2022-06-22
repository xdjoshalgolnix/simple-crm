
package com.sample.crm.entity.cpk;

import java.io.Serializable;

public class RoleAccessControlId implements Serializable
{
  private static final long serialVersionUID = -6880729378934346936L;

  public RoleAccessControlId()
  {}
  
  public RoleAccessControlId(long roleId, String nouns)
  {
    this.roleId = roleId;
    this.nouns = nouns;
  }

  private long roleId;

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
