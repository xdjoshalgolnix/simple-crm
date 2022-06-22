package com.sample.crm.services;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.sample.crm.bean.AccessControlResponse;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.constant.APIConstant;
import com.sample.crm.entity.RoleAccessControl;
import com.sample.crm.entity.User;
import com.sample.crm.exception.APIException;
import com.sample.crm.exception.ErrorCode;
import com.sample.crm.repository.UserRepository;
import com.sample.crm.util.JWTUtil;

import io.jsonwebtoken.Claims;

@Service
public class UserService
{
  private Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Autowired
  private UserRepository userRepository;
  
  private String env = "test";
  
  private long token_ttl_hours = 1;
  
  private long token_ttl_ms = 0;
  
  @PostConstruct
  public void init()
  {
    token_ttl_ms = token_ttl_hours * 3600000;
    log.info("token_ttl_ms:" + token_ttl_ms);
    return;
  }

  public UserInfo getUserInfo(String jwt) throws APIException
  {
    if ( StringUtils.isEmpty(jwt) || StringUtils.isEmpty(jwt) )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_AUTH1, "jwt is required");
    }
    Claims claims = JWTUtil.parserTokenId(jwt, token_ttl_ms, env);
    if ( claims == null )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_AUTH2, "auth fail");
    }
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(JWTUtil.getUserId(claims));
    userInfo.setId(JWTUtil.getID(claims));
    userInfo.setName(JWTUtil.getName(claims));
    userInfo.setRole(JWTUtil.getRole(claims));
    userInfo.setAccessControls(JWTUtil.getAccessControls(claims));
    return userInfo;
  }

  public UserInfo login(String userId, String password) throws APIException
  {
    if ( StringUtils.isEmpty(userId) || StringUtils.isEmpty(password) )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_AUTH1, "user_id & password is required");
    }
    Optional<User> entity = userRepository.findByUserIdAndHashpwd(userId, Hashing.md5().hashBytes(password.getBytes()).toString());
    if ( !entity.isPresent() )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_AUTH2, "auth fail");
    }
    User user = entity.get();
    if ( user.getState() != APIConstant.STATE_ENABLE )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_AUTH3, "SC_FORBIDDEN");
    }
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(userId);
    userInfo.setRole(user.getRole().getRole());
    if ( null != user.getAccessControls() && !user.getAccessControls().isEmpty() )
    {
      List<AccessControlResponse> accessControls = new ArrayList<AccessControlResponse>();
      user.getAccessControls().forEach((RoleAccessControl accessControl) -> {
        accessControls.add(new AccessControlResponse(accessControl.getNouns()));
      });
      userInfo.setAccessControls(accessControls);
    }
    userInfo.setToken(JWTUtil.genToken(user.getId(), userId, user.getName(), user.getRoleId(), userInfo.getRole(),
        new Date(System.currentTimeMillis() + token_ttl_ms), env, userInfo.getAccessControls()));
    
    return userInfo;
  }
}
