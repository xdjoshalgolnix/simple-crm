package com.sample.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.crm.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>
{

}
