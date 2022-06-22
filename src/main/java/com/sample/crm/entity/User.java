package com.sample.crm.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sample.crm.constant.APIConstant;

@Entity(name = "user")
public class User implements Serializable
{
  private static final long serialVersionUID = 1702146564299004611L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "user_id", nullable = false)
  private String userId;
  
  @Column(nullable = false)
  private String hashpwd;
  
  @Column(nullable = false)
  private String name;
  
  @Column(name = "role_id", nullable = false)
  private long roleId;
  
  @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  @JoinColumn(name = "id", referencedColumnName = "role_id")
  private Role role;
  
  @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id", referencedColumnName = "role_id")
  private List<RoleAccessControl> accessControls;
  
  private int state = APIConstant.STATE_ENABLE;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String getHashpwd()
  {
    return hashpwd;
  }

  public void setHashpwd(String hashpwd)
  {
    this.hashpwd = hashpwd;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public long getRoleId()
  {
    return roleId;
  }

  public void setRoleId(long roleId)
  {
    this.roleId = roleId;
  }

  public int getState()
  {
    return state;
  }

  public void setState(int state)
  {
    this.state = state;
  }

  public Role getRole()
  {
    return role;
  }

  public void setRole(Role role)
  {
    this.role = role;
  }

  public List<RoleAccessControl> getAccessControls()
  {
    return accessControls;
  }

  public void setAccessControls(List<RoleAccessControl> accessControls)
  {
    this.accessControls = accessControls;
  }
}
