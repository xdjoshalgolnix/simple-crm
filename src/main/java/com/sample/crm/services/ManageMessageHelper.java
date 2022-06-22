package com.sample.crm.services;

public class ManageMessageHelper
{
  public static String buildErrorStr(String fieldName, String value)
  {
    return new StringBuilder().append("field_name:").append(fieldName).append(" ,value:").append(value).append(" is invalid, please check").toString();
  }
}
