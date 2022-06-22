package com.sample.crm.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.crm.bean.CompanyRequest;
import com.sample.crm.bean.CompanyResponse;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.entity.Company;
import com.sample.crm.exception.APIException;
import com.sample.crm.exception.ErrorCode;
import com.sample.crm.repository.CompanyRepository;

@Service
public class CompanyManageService
{
  @Autowired
  private CompanyRepository companyRepository;
  
  public CompanyResponse createCompany(CompanyRequest request, UserInfo userInfo) throws APIException
  {
    vaildCompanyRequest(request);
    
    Company entity = new Company();
    entity.setName(request.getName());
    entity.setAddress(request.getAddress());
    entity.setCreatedBy(userInfo.getUserId());
    entity.setCreatedAt(new Date());
    entity.setUpdatedBy(userInfo.getUserId());
    entity.setUpdatedAt(new Date());
    
    entity = companyRepository.save(entity);
    
    CompanyResponse response = new CompanyResponse();
    response.setId(entity.getId());
    response.setName(entity.getName());
    response.setAddress(entity.getAddress());
    response.setCreatedBy(entity.getCreatedBy());
    response.setCreatedAt(entity.getCreatedAt());
    response.setUpdatedBy(entity.getUpdatedBy());
    response.setUpdatedAt(entity. getUpdatedAt());
    
    return response;
  }
  
  private void vaildCompanyRequest(CompanyRequest request) throws APIException
  {
    if ( null == request.getName() || request.getName().isEmpty() )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, ManageMessageHelper.buildErrorStr("name", request.getName()));
    }
  }
  
  public CompanyResponse updateCompany(long id, CompanyRequest request, UserInfo userInfo) throws APIException
  {
    Optional<Company> entry = companyRepository.findById(id);
    if ( !entry.isPresent() )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, StringUtils.join("id = ", id, " not existed, please check"));
    }
    
    vaildCompanyRequest(request);
    
    Company entity = entry.get();
    entity.setName(request.getName());
    entity.setAddress(request.getAddress());
    entity.setUpdatedBy(userInfo.getUserId());
    entity.setUpdatedAt(new Date());
    
    entity = companyRepository.save(entity);
    
    CompanyResponse response = new CompanyResponse();
    response.setId(entity.getId());
    response.setName(entity.getName());
    response.setAddress(entity.getAddress());
    response.setCreatedBy(entity.getCreatedBy());
    response.setCreatedAt(entity.getCreatedAt());
    response.setUpdatedBy(entity.getUpdatedBy());
    response.setUpdatedAt(entity. getUpdatedAt());
    
    return response;
  }
  
  public void deleteCompany(long id) throws APIException
  {
    if ( companyRepository.existsById(id) )
    {
      companyRepository.deleteById(id);
    }
    else
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, StringUtils.join("id = ", id, " not existed, please check"));
    }
  }
  
  public List<CompanyResponse> listCompanyData()
  {
    List<Company> list = companyRepository.findAll();
    List<CompanyResponse> response = new ArrayList<>();
    list.forEach( n -> {
      CompanyResponse entry = new CompanyResponse();
      entry.setId(n.getId());
      entry.setName(n.getName());
      if ( null != n.getAddress() )
      {
        entry.setAddress(n.getAddress());        
      }
      entry.setCreatedBy(n.getCreatedBy());
      entry.setCreatedAt(n.getCreatedAt());
      if ( null != n.getUpdatedBy() )
      {
        entry.setUpdatedBy(n.getUpdatedBy());
      }
      if ( null != n.getUpdatedAt() )
      {
        entry.setUpdatedAt(n. getUpdatedAt());
      }
      response.add(entry);
    });
    
    return response;
  }
}
