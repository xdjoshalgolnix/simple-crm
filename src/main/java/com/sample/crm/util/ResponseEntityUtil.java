package com.sample.crm.util;

import org.springframework.http.ResponseEntity;

import com.sample.crm.bean.ResponseBean;
import com.sample.crm.exception.APIException;

public class ResponseEntityUtil
{
  public static ResponseEntity<ResponseBean> fromAPIException(final APIException e)
  {
    if ( e == null )
    {
      return null;
    }
    ResponseBean responseBean = new ResponseBean(e.getCode().getRetCode(), e.getMessage(), e.getRetData());
    return new ResponseEntity<ResponseBean>(responseBean, e.getCode().getHttpStatus());
  }
}
