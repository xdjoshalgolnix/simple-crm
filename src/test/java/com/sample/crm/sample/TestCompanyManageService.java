package com.sample.crm.sample;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.crm.AiLabApp;
import com.sample.crm.bean.AccessControlResponse;
import com.sample.crm.bean.CompanyRequest;
import com.sample.crm.bean.CompanyResponse;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.exception.APIException;
import com.sample.crm.services.CompanyManageService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AiLabApp.class)
public class TestCompanyManageService
{
  @Autowired
  private CompanyManageService companyManageService;
  
  public UserInfo superUser;
  
  @Before
  public void before()
  {
    superUser = new UserInfo();
    superUser.setId(1L);
    superUser.setUserId("super_user");
    superUser.setName("superUser");
    superUser.setRole("super_user");
    superUser.setAccessControls(Arrays.asList(new AccessControlResponse("*")));
  }
  
  @Test
  public void testCreateRequestCheck()
  {
    CompanyRequest request = new CompanyRequest();;
    assertThrows(APIException.class, () -> companyManageService.createCompany(request, superUser));
  }
  
  @Test
  @Order(1)
  public void testList()
  {
    List<CompanyResponse> list = companyManageService.listCompanyData();
    assertNotNull(list);
    assertEquals(2, list.size());
  }
  
  @Test
  @Order(2)
  public void testCreate() throws APIException
  {
    List<CompanyResponse> list = companyManageService.listCompanyData();
    assertNotNull(list);
    int original = list.size();
    
    CompanyRequest request = new CompanyRequest();
    request.setName("company_x");
    request.setAddress("address_x");
    
    CompanyResponse response = companyManageService.createCompany(request, superUser);
    assertNotNull(response);
    assertEquals("super_user", response.getCreatedBy());
    
    list = companyManageService.listCompanyData();
    assertNotNull(list);
    assertEquals(original + 1, list.size());
  }
  
  @Test
  public void testUpdateRequestCheck()
  {
    List<CompanyResponse> list = companyManageService.listCompanyData();
    assertNotNull(list);
    
    CompanyResponse response = list.get(0);
    
    CompanyRequest request = new CompanyRequest();
    assertThrows(APIException.class, () -> companyManageService.updateCompany(response.getId(), request, superUser));
    
    assertThrows(APIException.class, () -> companyManageService.updateCompany(100, request, superUser));
  }
  
  @Test
  @Order(3)
  public void testUpdate() throws APIException
  {
    List<CompanyResponse> list = companyManageService.listCompanyData();
    assertNotNull(list);
    
    CompanyResponse response = list.get(0);
    
    CompanyRequest request = new CompanyRequest();
    request.setName("update_a");
    request.setAddress("address_x");
    
    response = companyManageService.updateCompany(response.getId(), request, superUser);
    assertNotNull(response);
    assertEquals("super_user", response.getUpdatedBy());
    assertTrue(response.getUpdatedAt().getTime() > response.getCreatedAt().getTime());
  }
  
  @Test
  public void testDeleteCheck()
  {
    assertThrows(APIException.class, () -> companyManageService.deleteCompany(100));
  }
  
  @Test
  @Order(4)
  public void testDelete() throws APIException
  {
    List<CompanyResponse> list = companyManageService.listCompanyData();
    assertNotNull(list);
    int original = list.size();

    CompanyResponse response = list.get(list.size() - 1);

    companyManageService.deleteCompany(response.getId());

    list = companyManageService.listCompanyData();
    assertNotNull(list);
    assertEquals(original - 1, list.size());
  }

}
