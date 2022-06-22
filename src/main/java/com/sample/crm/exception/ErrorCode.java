package com.sample.crm.exception;

import org.springframework.http.HttpStatus;

import com.sample.crm.constant.APIConstant;

public enum ErrorCode
{
  // 400
  BAD_REQUEST_SCH1(APIConstant.SC_BAD_REQUEST, APIConstant.RETCODE_SCH1)
  , BAD_REQUEST_DOC1(APIConstant.SC_BAD_REQUEST, APIConstant.RETCODE_DOC1)
  , BAD_REQUEST_AUTH1(APIConstant.SC_BAD_REQUEST, APIConstant.RETCODE_AUTH1)
  , BAD_REQUEST_AUTH2(APIConstant.SC_UNAUTHORIZED, APIConstant.RETCODE_AUTH2)
  , BAD_REQUEST_AUTH3(APIConstant.SC_FORBIDDEN, APIConstant.RETCODE_AUTH3)
  
  // 500
  , Error_DB1(APIConstant.SC_INTERNAL_SERVER_ERROR, APIConstant.RETCODE_DB1)
  , Error_EXT1(APIConstant.SC_INTERNAL_SERVER_ERROR, APIConstant.RETCODE_EXT1)
  , Error_PGRM1(APIConstant.SC_INTERNAL_SERVER_ERROR, APIConstant.RETCODE_PGRM1);

  private final int code;
  private final String retCode;

  public final int getCode()
  {
    return code;
  }

  public final String getRetCode()
  {
    return retCode;
  }

  private ErrorCode(final int code, final String retCode)
  {
    this.code = code;
    this.retCode = retCode;
  }

  public HttpStatus getHttpStatus()
  {
    return HttpStatus.valueOf(getCode());
  }
}
