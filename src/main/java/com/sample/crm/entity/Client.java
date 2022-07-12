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

import lombok.Data;

@Data
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
}
