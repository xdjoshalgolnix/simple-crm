package com.sample.crm.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity(name = "client")
public class Client
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "company_id", nullable = false)
  private Long companyId;
  
  @Column(nullable = false)
  private String name;
  
  private String email;
  
  private String phone;
  
  @Column(name = "created_by", nullable = false)
  private String createdBy;
  
  @Column(name = "created_at", nullable = false)
  private Date createdAt;
  
  @Column(name = "updated_by", nullable = false)
  private String updatedBy;
  
  @Column(name = "updated_at", nullable = false)
  private Date updatedAt;
  
  @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  @JoinColumn(name = "id", referencedColumnName = "company_id")
  private Company company;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Long getCompanyId()
  {
    return companyId;
  }

  public void setCompanyId(Long companyId)
  {
    this.companyId = companyId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getCreatedBy()
  {
    return createdBy;
  }

  public void setCreatedBy(String createdBy)
  {
    this.createdBy = createdBy;
  }

  public Date getCreatedAt()
  {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt)
  {
    this.createdAt = createdAt;
  }

  public String getUpdatedBy()
  {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy)
  {
    this.updatedBy = updatedBy;
  }

  public Date getUpdatedAt()
  {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt)
  {
    this.updatedAt = updatedAt;
  }

  public Company getCompany()
  {
    return company;
  }

  public void setCompany(Company company)
  {
    this.company = company;
  }
}
