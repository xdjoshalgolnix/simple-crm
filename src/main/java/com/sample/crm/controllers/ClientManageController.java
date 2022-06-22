package com.sample.crm.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.crm.bean.ClientRequest;
import com.sample.crm.bean.ClientResponse;
import com.sample.crm.bean.ResponseBean;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.constant.APIConstant;
import com.sample.crm.exception.APIException;
import com.sample.crm.services.ClientManageService;
import com.sample.crm.util.ResponseEntityUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
public class ClientManageController implements APIConstant
{
  @Autowired
  private ClientManageService clientManageService;
  
  @ApiImplicitParams({
    @ApiImplicitParam(name = HEADER_AUTHORIZATION, value = Description_Authorization, required = true, dataType = TYPE_STRING, paramType = ParamType_HEADER)})
  @RequestMapping(value = "/client", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createClient(@RequestBody ClientRequest request, HttpServletRequest httpServletRequest)
  {
    try
    {
      UserInfo userInfo = AttributeHandler.getCurrntUser(httpServletRequest);

      ClientResponse response = clientManageService.createClient(request, userInfo);

      ResponseBean<ClientResponse> resp = new ResponseBean<>();
      resp.setRetData(response);

      return new ResponseEntity<ResponseBean<?>>(resp, HttpStatus.OK);
    }
    catch ( APIException e )
    {
      return ResponseEntityUtil.fromAPIException(e);
    }
  }
  
  @ApiImplicitParams({
    @ApiImplicitParam(name = HEADER_AUTHORIZATION, value = Description_Authorization, required = true, dataType = TYPE_STRING, paramType = ParamType_HEADER)})
  @RequestMapping(value = "/clients", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createClients(@RequestBody List<ClientRequest> request, HttpServletRequest httpServletRequest)
  {
    try
    {
      UserInfo userInfo = AttributeHandler.getCurrntUser(httpServletRequest);

      List<ClientResponse> response = clientManageService.createClients(request, userInfo);

      ResponseBean<List<ClientResponse>> resp = new ResponseBean<>();
      resp.setRetData(response);

      return new ResponseEntity<ResponseBean<?>>(resp, HttpStatus.OK);
    }
    catch ( APIException e )
    {
      return ResponseEntityUtil.fromAPIException(e);
    }
  }
  
  @ApiImplicitParams({
    @ApiImplicitParam(name = HEADER_AUTHORIZATION, value = Description_Authorization, required = true, dataType = TYPE_STRING, paramType = ParamType_HEADER)})
  @RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deleteClient(@PathVariable long id, HttpServletRequest httpServletRequest)
  {
    try
    {
      clientManageService.deleteClient(id);
  
      ResponseBean<Object> resp = new ResponseBean<>();
  
      return new ResponseEntity<ResponseBean<?>>(resp, HttpStatus.OK);
    }
    catch ( APIException e )
    {
      return ResponseEntityUtil.fromAPIException(e);
    }
  }
  
  @ApiImplicitParams({
    @ApiImplicitParam(name = HEADER_AUTHORIZATION, value = Description_Authorization, required = true, dataType = TYPE_STRING, paramType = ParamType_HEADER)})
  @RequestMapping(value = "/client/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateClient(@PathVariable long id, @RequestBody ClientRequest request, HttpServletRequest httpServletRequest)
  {
    try
    {
      UserInfo userInfo = AttributeHandler.getCurrntUser(httpServletRequest);

      ClientResponse response = clientManageService.updateClient(id, request, userInfo);

      ResponseBean<ClientResponse> resp = new ResponseBean<>();
      resp.setRetData(response);

      return new ResponseEntity<ResponseBean<?>>(resp, HttpStatus.OK);
    }
    catch ( APIException e )
    {
      return ResponseEntityUtil.fromAPIException(e);
    }
  }
  
  @ApiImplicitParams({ 
    @ApiImplicitParam(name = HEADER_AUTHORIZATION, value = Description_Authorization, required = true, dataType = TYPE_STRING, paramType = ParamType_HEADER)})
  @RequestMapping(value = "/client", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> listClientData()
  {
    List<ClientResponse> list = clientManageService.listClientData();
    return new ResponseEntity<List<ClientResponse>>(list, HttpStatus.OK);
  }
}
