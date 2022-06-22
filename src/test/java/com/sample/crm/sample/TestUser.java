package com.sample.crm.sample;

import org.junit.Test;

import com.google.common.hash.Hashing;


public class TestUser
{

  @Test
  public void genHashpwd()
  {
    String password = "simple";
    System.out.println(Hashing.md5().hashBytes(password.getBytes()).toString());
  }

}
