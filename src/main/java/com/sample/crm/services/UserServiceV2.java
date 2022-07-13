package com.sample.crm.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.hash.Hashing;
import com.sample.crm.bean.AccessControlResponse;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.constant.APIConstant;
import com.sample.crm.exception.APIException;
import com.sample.crm.exception.ErrorCode;
import com.sample.crm.mapper.RoleAccessControlMapper;
import com.sample.crm.mapper.RoleMapper;
import com.sample.crm.mapper.UserMapper;
import com.sample.crm.model.RoleAccessControl;
import com.sample.crm.model.User;
import com.sample.crm.util.JWTUtil;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceV2
{
  @Resource
  private UserMapper userMapper;
  
  @Resource
  private RoleMapper roleMapper;
  
  @Resource
  private RoleAccessControlMapper roleAccessControlMapper;
  
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
    
    User user = userMapper.selectOne(Wrappers.<User>query().eq("user_id", userId).eq("hashpwd", Hashing.md5().hashBytes(password.getBytes()).toString()));
    if ( null == user )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_AUTH2, "auth fail");
    }
    
    if ( user.getState() != APIConstant.STATE_ENABLE )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_AUTH3, "SC_FORBIDDEN");
    }
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(userId);
    userInfo.setRole(roleMapper.selectById(user.getRoleId()).getRole());
    
    List<RoleAccessControl> list = roleAccessControlMapper.selectList(Wrappers.<RoleAccessControl>query().eq("role_id", user.getRoleId()));
    if ( null != list && !list.isEmpty() )
    {
      List<AccessControlResponse> accessControls = new ArrayList<>();
      list.forEach( n -> {
        accessControls.add(new AccessControlResponse(n.getNouns()));
      });
      userInfo.setAccessControls(accessControls);
    }
    userInfo.setToken(JWTUtil.genToken(user.getId(), userId, user.getName(), user.getRoleId(), userInfo.getRole(),
        new Date(System.currentTimeMillis() + token_ttl_ms), env, userInfo.getAccessControls()));
    
    return userInfo;
  }
}
