package com.sample.crm.util;

public class ValidateUtils
{
  public static boolean validateEmail(final String email)
  {
    String PATTERN = "^(.+)@(.+)$";

    return email.matches(PATTERN);
  }
  
  public static boolean validatePhone(final String phone)
  {
    String PATTERN = "^[0-9]*$";

    return phone.matches(PATTERN);
  }
}
