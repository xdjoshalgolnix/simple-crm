package com.sample.crm.sample;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
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
import com.sample.crm.bean.ClientRequest;
import com.sample.crm.bean.ClientResponse;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.exception.APIException;
import com.sample.crm.services.ClientManageService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AiLabApp.class)
public class TestClientManageService
{
  @Autowired
  private ClientManageService clientManageService;
  
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
    ClientRequest request = new ClientRequest();
    request.setCompanyId(1);
    assertThrows(APIException.class, () -> clientManageService.createClient(request, superUser));
    
    request.setName("client_a");
    request.setEmail("client_a");
    assertThrows(APIException.class, () -> clientManageService.createClient(request, superUser));
    
    request.setEmail("client_a@gmail.com");
    request.setPhone("a0123456789");
    assertThrows(APIException.class, () -> clientManageService.createClient(request, superUser));
    
    request.setPhone("0123456789");
    request.setCompanyId(5);
    assertThrows(APIException.class, () -> clientManageService.createClient(request, superUser));
  }
  
  @Test
  @Order(1)
  public void testList()
  {
    List<ClientResponse> list = clientManageService.listClientData();
    assertNotNull(list);
    assertEquals(2, list.size());
  }
  
  @Test
  @Order(2)
  public void testCreate() throws APIException
  {
    List<ClientResponse> list = clientManageService.listClientData();
    assertNotNull(list);
    int original = list.size();
    
    ClientRequest request = new ClientRequest();
    request.setCompanyId(1);
    request.setName("client_a");
    request.setEmail("client_a@gmail.com");
    request.setPhone("0123456789");
    
    ClientResponse response = clientManageService.createClient(request, superUser);
    assertNotNull(response);
    assertEquals("super_user", response.getCreatedBy());
    
    list = clientManageService.listClientData();
    assertNotNull(list);
    assertEquals(original + 1, list.size());
  }
  
  @Test
  public void testUpdateRequestCheck()
  {
    List<ClientResponse> list = clientManageService.listClientData();
    assertNotNull(list);
    
    ClientResponse response = list.get(0);
    
    ClientRequest request = new ClientRequest();
    request.setCompanyId(1);
    
    assertThrows(APIException.class, () -> clientManageService.updateClient(response.getId(), request, superUser));
    
    request.setName("update_a");
    request.setEmail("update_a");
    assertThrows(APIException.class, () -> clientManageService.updateClient(response.getId(), request, superUser));
    
    request.setEmail("update_a@gmail.com");
    request.setPhone("s1234567890");
    assertThrows(APIException.class, () -> clientManageService.updateClient(response.getId(), request, superUser));
    
    request.setPhone("1234567890");
    request.setCompanyId(5);
    assertThrows(APIException.class, () -> clientManageService.updateClient(response.getId(), request, superUser));
    
    request.setCompanyId(1);
    assertThrows(APIException.class, () -> clientManageService.updateClient(100, request, superUser));
  }
  
  @Test
  @Order(3)
  public void testUpdate() throws APIException
  {
    List<ClientResponse> list = clientManageService.listClientData();
    assertNotNull(list);
    
    ClientResponse response = list.get(0);
    
    ClientRequest request = new ClientRequest();
    request.setCompanyId(response.getCompanyId());
    request.setEmail(response.getEmail());
    request.setName("update_a");
    request.setPhone(response.getPhone());
    
    response = clientManageService.updateClient(response.getId(), request, superUser);
    assertNotNull(response);
    assertEquals("super_user", response.getUpdatedBy());
    assertTrue(response.getUpdatedAt().getTime() > response.getCreatedAt().getTime());
  }
  
  @Test
  public void testDeleteCheck()
  {
    assertThrows(APIException.class, () -> clientManageService.deleteClient(100));
  }
  
  @Test
  @Order(4)
  public void testDelete() throws APIException
  {
    List<ClientResponse> list = clientManageService.listClientData();
    assertNotNull(list);
    int original = list.size();

    ClientResponse response = list.get(list.size() - 1);

    clientManageService.deleteClient(response.getId());

    list = clientManageService.listClientData();
    assertNotNull(list);
    assertEquals(original - 1, list.size());
  }
  
  @Test
  public void testCreateClientsCheck()
  {
    List<ClientResponse> list = clientManageService.listClientData();
    assertNotNull(list);
    int original = list.size();
    
    List<ClientRequest> request = new ArrayList<>();
    
    ClientRequest entity = new ClientRequest();
    entity.setCompanyId(1);
    entity.setName("client_x");
    entity.setEmail("client_x@gmail.com");
    entity.setPhone("0123456789");
    request.add(entity);
    
    entity = new ClientRequest();
    entity.setCompanyId(2);
    entity.setName("client_y");
    entity.setEmail("client_y@gmail.com");
    entity.setPhone("a0123456789");
    request.add(entity);
    
    assertThrows(APIException.class, () -> clientManageService.createClients(request, superUser));
    
    list = clientManageService.listClientData();
    assertEquals(original, list.size());
  }
  
  @Test
  @Order(5)
  public void testCreateClients() throws APIException
  {
    List<ClientResponse> list = clientManageService.listClientData();
    assertNotNull(list);
    int original = list.size();
    
    List<ClientRequest> request = new ArrayList<>();
    
    ClientRequest entity = new ClientRequest();
    entity.setCompanyId(1);
    entity.setName("client_x");
    entity.setEmail("client_x@gmail.com");
    entity.setPhone("0123456789");
    request.add(entity);
    
    entity = new ClientRequest();
    entity.setCompanyId(2);
    entity.setName("client_y");
    entity.setEmail("client_y@gmail.com");
    entity.setPhone("0123456789");
    request.add(entity);
    
    List<ClientResponse> response = clientManageService.createClients(request, superUser);
    assertNotNull(response);
    response.forEach(n -> {
      assertEquals("super_user", n.getCreatedBy());
    });

    list = clientManageService.listClientData();
    assertEquals(original + 2, list.size());
  }
}
