package com.sample.crm.util;

import java.lang.invoke.MethodHandles;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.crm.bean.AccessControlResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTUtil
{
  private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static final String ID = "id";
  private static final String ENV = "env";
  private static final String RANDOM = "random";
  public static final String ROLE = "role";
  public static final String ROLE_ID = "role_id";
  private static final String USERID = "user_id";
  private static final String NAME = "name";
  private static final String ACCESS_CONTROL_LIST = "access_control_list";

  private static final String ACCESS_CONTROL = "access control";
  private static final String SAMPLE_SERVICE = "sample service";
  private final static String KEY = "JwtForSampleService#1qaz@WSX3edc$RFV";
  private final static Key SecretKey = Keys.hmacShaKeyFor(KEY.getBytes());

  public static String genToken(long id, String usserid, String name, long role_id, String role, Date expiration, String env,
      List<AccessControlResponse> accessControls)
  {
    Claims claims = Jwts.claims();
    claims.put(ID, String.valueOf(id));
    claims.put(USERID, usserid);
    claims.put(NAME, name);
    claims.put(ROLE_ID, role_id);
    claims.put(ROLE, role);
    claims.put(RANDOM, new Random().nextLong());
    claims.put(ENV, env);
    claims.put(ACCESS_CONTROL_LIST, accessControls);
    claims.setIssuer(SAMPLE_SERVICE);
    claims.setSubject(ACCESS_CONTROL);
    claims.setIssuedAt(new Date());
    if ( expiration != null )
    {
      claims.setExpiration(expiration);
    }
    String jwt = Jwts.builder().setClaims(claims).signWith(SecretKey).compact();
    return jwt;
  }

  public static Claims parserTokenId(String jwt, long token_ttl_ms, String env)
  {
    Claims claims = null;
    try
    {
      Jws<Claims> cs = getJws(jwt);
      log.info(cs.toString());
      String envInToken = (String)cs.getBody().get(ENV);
      if ( !StringUtils.equalsIgnoreCase(env, envInToken) )
      {
        log.info(StringUtils.join("envInToken:", envInToken, " env:", env, " not match"));
        return null;
      }
      Date issuedAt = cs.getBody().getIssuedAt();
      Date expiration = cs.getBody().getExpiration();
      if ( expiration != null )
      {
        if ( System.currentTimeMillis() <= expiration.getTime() )
        {
          claims = cs.getBody();
        }
      }
      else if ( issuedAt != null )
      {
        long ttl_now = System.currentTimeMillis() - issuedAt.getTime();
        if ( ttl_now <= token_ttl_ms )
        {
          claims = cs.getBody();
        }
      }
    }
    catch ( Exception e )
    {
      log.error(e.getMessage(), e);
    }
    return claims;
  }

  @SuppressWarnings("unchecked")
  public static List<AccessControlResponse> getAccessControls(Claims claims)
  {
    if ( claims != null )
    {
      List<LinkedHashMap<String, String>> ac = (List<LinkedHashMap<String, String>>)claims.get(ACCESS_CONTROL_LIST);
      List<AccessControlResponse> access = new ArrayList<>();
      ac.forEach(e -> {
        AccessControlResponse accessControlResponse = new AccessControlResponse();
        accessControlResponse.setNouns(e.get("nouns"));
        access.add(accessControlResponse);
      });
      return access;
    }
    return null;
  }

  public static Long getRoleId(Claims claims)
  {
    if ( claims != null )
    {
      return Long.parseLong((String)claims.get(ROLE_ID));
    }
    return null;
  }

  public static String getUserId(Claims claims)
  {
    if ( claims != null )
    {
      return (String)claims.get(USERID);
    }
    return null;
  }

  public static String getName(Claims claims)
  {
    if ( claims != null )
    {
      return (String)claims.get(NAME);
    }
    return null;
  }

  public static String getRole(Claims claims)
  {
    if ( claims != null )
    {
      return (String)claims.get(ROLE);
    }
    return null;
  }

  public static Long getID(Claims claims)
  {
    if ( claims != null )
    {
      return Long.parseLong((String)claims.get(ID));
    }
    return null;
  }

  public static Jws<Claims> getJws(String jwt)
  {
    Jws<Claims> cs = Jwts.parserBuilder().setSigningKey(SecretKey).build().parseClaimsJws(jwt);
    return cs;
  }
}
