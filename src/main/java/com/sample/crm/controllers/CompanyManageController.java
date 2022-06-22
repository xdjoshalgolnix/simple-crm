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

import com.sample.crm.bean.CompanyRequest;
import com.sample.crm.bean.CompanyResponse;
import com.sample.crm.bean.ResponseBean;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.constant.APIConstant;
import com.sample.crm.exception.APIException;
import com.sample.crm.services.CompanyManageService;
import com.sample.crm.util.ResponseEntityUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
public class CompanyManageController implements APIConstant
{
  @Autowired
  private CompanyManageService companyManageService;
  
  @ApiImplicitParams({
    @ApiImplicitParam(name = HEADER_AUTHORIZATION, value = Description_Authorization, required = true, dataType = TYPE_STRING, paramType = ParamType_HEADER)})
  @RequestMapping(value = "/company", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createCompany(@RequestBody CompanyRequest request, HttpServletRequest httpServletRequest)
  {
    try
    {
      UserInfo userInfo = AttributeHandler.getCurrntUser(httpServletRequest);

      CompanyResponse response = companyManageService.createCompany(request, userInfo);

      ResponseBean<CompanyResponse> resp = new ResponseBean<>();
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
  @RequestMapping(value = "/company/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deleteCompany(@PathVariable long id, HttpServletRequest httpServletRequest)
  {
    try
    {
      companyManageService.deleteCompany(id);

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
  @RequestMapping(value = "/company/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateCompany(@PathVariable long id, @RequestBody CompanyRequest request, HttpServletRequest httpServletRequest)
  {
    try
    {
      UserInfo userInfo = AttributeHandler.getCurrntUser(httpServletRequest);

      CompanyResponse response = companyManageService.updateCompany(id, request, userInfo);

      ResponseBean<CompanyResponse> resp = new ResponseBean<>();
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
  @RequestMapping(value = "/company", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> listCompanyData()
  {
    List<CompanyResponse> list = companyManageService.listCompanyData();
    return new ResponseEntity<List<CompanyResponse>>(list, HttpStatus.OK);
  }
}
