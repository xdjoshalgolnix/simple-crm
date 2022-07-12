package com.sample.crm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@DynamicUpdate
@Entity(name = "company")
public class Company
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String name;
  
  private String address;
  
  @Column(name = "created_by", nullable = false)
  private String createdBy;
  
  @Column(name = "created_at", nullable = false)
  private Date createdAt;
  
  @Column(name = "updated_by", nullable = false)
  private String updatedBy;
  
  @Column(name = "updated_at", nullable = false)
  private Date updatedAt;

}
