package com.sample.crm.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.sample.crm.bean.AccessControlResponse;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.exception.APIException;
import com.sample.crm.exception.ErrorCode;

@Service
public class AccessControlService
{
  public final static String HTTP_GET = "GET";
  public final static String HTTP_POST = "POST";
  public final static String HTTP_PUT = "PUT";
  public final static String HTTP_DELETE = "DELETE";
  
  public final static String ALL = "*";

  private final static ConcurrentHashMap<String, String> _method = new ConcurrentHashMap<String, String>();

  @PostConstruct
  public void init()
  {
    _method.put(HTTP_GET, "Q");
    _method.put(HTTP_POST, "C");
    _method.put(HTTP_PUT, "U");
    _method.put(HTTP_DELETE, "D");
    return;
  }

  private static class PathInfo
  {
    private String method;
    private String nouns;
  }

  private static class AccessControls
  {
    private boolean allowAll;
    private Set<String> nouns = new HashSet<>();

    public boolean isAllowAll()
    {
      return allowAll;
    }

    public PathInfo extract(String method)
    {
      PathInfo pathInfo = new PathInfo();
      pathInfo.method = method;
      pathInfo.nouns = _method.get(method);
      
      return pathInfo;
    }

    public boolean isAllow(PathInfo pathInfo)
    {
      if ( allowAll )
      {
        return true;
      }
      
      if ( nouns.contains(pathInfo.nouns) )
      {
        return true;
      }
      
      return false;
    }

    public static AccessControls get(List<AccessControlResponse> accessControls)
    {
      AccessControls acs = new AccessControls();
      for ( AccessControlResponse acp : accessControls )
      {
        if ( acp.getNouns() == null )
        {
          continue;
        }
        
        if ( acp.getNouns().equals(ALL) )
        {
          acs.allowAll = true;
          break;
        }
        
        acs.nouns.add(acp.getNouns());
      }
      return acs;
    }
  }
  
  public void valid(String method, UserInfo userInfo) throws APIException
  {
    if ( null == method || null == userInfo || null == userInfo.getAccessControls() || userInfo.getAccessControls().isEmpty() )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_AUTH3, "invalid input");
    }
    AccessControls accessControls = AccessControls.get(userInfo.getAccessControls());
    PathInfo pathInfo = accessControls.extract(method);
    if ( pathInfo == null )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_AUTH3, "invalid url");
    }
    if ( !accessControls.isAllowAll() )
    {
      if ( !accessControls.isAllow(pathInfo) )
      {
        throw new APIException(ErrorCode.BAD_REQUEST_AUTH3, "SC_FORBIDDEN");
      }
      return;
    }
  }
}
