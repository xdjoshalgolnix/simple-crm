package com.sample.crm.sample;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.crm.AiLabApp;
import com.sample.crm.bean.AccessControlResponse;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.exception.APIException;
import com.sample.crm.services.AccessControlService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AiLabApp.class)
public class TestAccessControlService
{
  @Autowired
  private AccessControlService accessControlService;
  
  public UserInfo superUser;
  
  public UserInfo manager;
  
  public UserInfo operator;
  
  @Before
  public void before()
  {
    superUser = new UserInfo();
    superUser.setId(1L);
    superUser.setUserId("super_user");
    superUser.setName("superUser");
    superUser.setRole("super_user");
    superUser.setAccessControls(Arrays.asList(new AccessControlResponse("*")));
    
    manager = new UserInfo();
    manager.setId(2L);
    manager.setUserId("manager");
    manager.setName("manager");
    manager.setRole("manager");
    manager.setAccessControls(Arrays.asList(new AccessControlResponse("Q"), new AccessControlResponse("U"), new AccessControlResponse("D")));
    
    operator = new UserInfo();
    operator.setId(3L);
    operator.setUserId("operator");
    operator.setName("operator");
    operator.setRole("operator");
    operator.setAccessControls(Arrays.asList(new AccessControlResponse("Q"), new AccessControlResponse("C")));
  }
  
  @Test
  public void testValidSuperUser() throws APIException
  {
    accessControlService.valid("GET", superUser);
    
    accessControlService.valid("POST", superUser);
    
    accessControlService.valid("PUT", superUser);
    
    accessControlService.valid("DELETE", superUser);
  }
  
  @Test
  public void testValidManager() throws APIException
  {
    accessControlService.valid("GET", manager);
    
    assertThrows(APIException.class, () -> accessControlService.valid("POST", manager));
    
    accessControlService.valid("PUT", manager);
    
    accessControlService.valid("DELETE", manager);
  }
  
  @Test
  public void testValidOperator() throws APIException
  {
    accessControlService.valid("GET", operator);
    
    accessControlService.valid("POST", operator);
    
    assertThrows(APIException.class, () -> accessControlService.valid("PUT", operator));
    
    assertThrows(APIException.class, () -> accessControlService.valid("DELETE", operator));
  }
  
}
