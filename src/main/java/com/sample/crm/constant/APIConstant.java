package com.sample.crm.constant;

/**
 * 
 * API status code & retCode
 *
 */
public interface APIConstant
{
  public int SC_SUCCESS = 200;
  public int SC_BAD_REQUEST = 400;
  public int SC_UNAUTHORIZED = 401;
  public int SC_FORBIDDEN = 403;
  public int SC_INTERNAL_SERVER_ERROR = 500;
  
  // retCode
  public String RETCODE_S = "S";
  public String RETCODE_W1 = "W-1";
  public String RETCODE_W2 = "W-2";
  public String RETCODE_SCH1 = "Sch-1";
  public String RETCODE_DOC1 = "Doc-1";
  public String RETCODE_DB1 = "DB-1";
  public String RETCODE_EXT1 = "Ext-1";
  public String RETCODE_PGRM1 = "Pgrm-1";
  
  public String RETCODE_AUTH1 = "Auth-1";
  public String RETCODE_AUTH2 = "Auth-2";
  public String RETCODE_AUTH3 = "Auth-3";
  
  public String MSG_SUCCESS = "Successfully";
  public String MSG_BAD_REQUEST = "Bad Request";
  public String MSG_INTERNAL_SERVER_ERROR = "Internal Server Error";
  
  
  public String Header_VARY = "Vary";
  public String Header_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
  public String Header_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
  public String Header_ORIGIN = "Origin";
  
  public String HEADER_AUTHORIZATION = "Authorization";
  public String PARAM_ACCESSTOKEN = "accesstoken";

  public String BearerPrefix = "Bearer ";
  public String Description_Authorization = "Authorization: Bearer $access_token";
  public String TYPE_STRING = "string";
  public String ParamType_HEADER = "header";
  
  public String CURRENT_USER = "current_user";
  
  public int STATE_ENABLE = 1;
  public int STATE_DISABLE = 0;
}
