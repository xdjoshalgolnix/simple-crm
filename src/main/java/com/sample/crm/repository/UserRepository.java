package com.sample.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.crm.entity.User;

public interface UserRepository extends JpaRepository<User, Long>
{
  public Optional<User> findByUserIdAndHashpwd(String userId, String hashpwd);
}
