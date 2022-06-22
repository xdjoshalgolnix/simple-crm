package com.sample.crm.exception;

public final class APIException extends Exception
{
  private static final long serialVersionUID = -5530082929340907827L;
  private ErrorCode code;
  private String retData;

  public APIException(ErrorCode code, String message)
  {
    super(message);
    this.code = code;
  }

  public APIException(ErrorCode code, String message, Throwable cause)
  {
    super(message, cause);
    this.code = code;
  }
  
  public APIException(ErrorCode code, String message, String retData)
  {
    super(message);
    this.code = code;
    this.retData = retData;
  }

  public APIException(ErrorCode code, String message, String retData, Throwable cause)
  {
    super(message, cause);
    this.code = code;
    this.retData = retData;
  }
  
  public ErrorCode getCode()
  {
    return code;
  }

  public void setCode(ErrorCode code)
  {
    this.code = code;
  }
  
  public String getRetData()
  {
    return retData;
  }

  public void setRetData(String retData)
  {
    this.retData = retData;
  }
}
