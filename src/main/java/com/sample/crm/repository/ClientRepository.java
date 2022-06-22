package com.sample.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.crm.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long>
{

}
