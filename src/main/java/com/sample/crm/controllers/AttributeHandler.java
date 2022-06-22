package com.sample.crm.controllers;

import javax.servlet.http.HttpServletRequest;

import com.sample.crm.bean.UserInfo;
import com.sample.crm.constant.APIConstant;


public class AttributeHandler
{
  public static UserInfo getCurrntUser(final HttpServletRequest httpServletRequest)
  {
    return (UserInfo)httpServletRequest.getAttribute(APIConstant.CURRENT_USER);
  }
}
