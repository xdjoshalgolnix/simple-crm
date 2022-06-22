package com.sample.crm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.crm.bean.LoginRequest;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.exception.APIException;
import com.sample.crm.services.UserService;
import com.sample.crm.util.ResponseEntityUtil;

@RestController
public class LoginController
{
  @Autowired
  private UserService userService;

  @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
  {
    try
    {
      UserInfo loginResponse = userService.login(loginRequest.getUserId(), loginRequest.getPassword());
      return new ResponseEntity<UserInfo>(loginResponse, HttpStatus.OK);
    }
    catch ( APIException e )
    {
      return ResponseEntityUtil.fromAPIException(e);
    }
  }
}
