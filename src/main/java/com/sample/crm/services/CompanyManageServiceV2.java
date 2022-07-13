package com.sample.crm.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sample.crm.bean.CompanyRequest;
import com.sample.crm.bean.CompanyResponse;
import com.sample.crm.bean.CompanyResponse.CompanyResponseBuilder;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.exception.APIException;
import com.sample.crm.exception.ErrorCode;
import com.sample.crm.mapper.CompanyMapper;
import com.sample.crm.model.Company;

@Service
public class CompanyManageServiceV2
{
  @Resource
  private CompanyMapper companyMapper;
  
  public CompanyResponse createCompany(CompanyRequest request, UserInfo userInfo) throws APIException
  {
    vaildCompanyRequest(request);
    
    Company entity = Company.builder()
        .name(request.getName())
        .address(request.getAddress())
        .createdBy(userInfo.getUserId())
        .createdAt(new Date())
        .updatedBy(userInfo.getUserId())
        .updatedAt(new Date())
        .build();
    
    companyMapper.insert(entity);
    
    CompanyResponse response = CompanyResponse.builder()
        .id(entity.getId())
        .name(entity.getName())
        .address(entity.getAddress())
        .createdBy(entity.getCreatedBy())
        .createdAt(entity.getCreatedAt())
        .updatedBy(entity.getUpdatedBy())
        .updatedAt(entity. getUpdatedAt())
        .build();
    
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
    Company entity = companyMapper.selectById(id);
    if ( null == entity )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, StringUtils.join("id = ", id, " not existed, please check"));
    }
    
    vaildCompanyRequest(request);
    
    entity.setName(request.getName());
    entity.setAddress(request.getAddress());
    entity.setUpdatedBy(userInfo.getUserId());
    entity.setUpdatedAt(new Date());
    
    companyMapper.updateById(entity);
    
    CompanyResponse response = CompanyResponse.builder()
        .id(entity.getId())
        .name(entity.getName())
        .address(entity.getAddress())
        .createdBy(entity.getCreatedBy())
        .createdAt(entity.getCreatedAt())
        .updatedBy(entity.getUpdatedBy())
        .updatedAt(entity. getUpdatedAt())
        .build();
    
    return response;
  }
  
  public void deleteCompany(long id) throws APIException
  {
    if ( companyMapper.exists(Wrappers.<Company>query().eq("id", id)) )
    {
      companyMapper.deleteById(id);
    }
    else
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, StringUtils.join("id = ", id, " not existed, please check"));
    }
  }
  
  public List<CompanyResponse> listCompanyData()
  {
    List<Company> list = companyMapper.selectList(Wrappers.query());
    
    List<CompanyResponse> response = new ArrayList<>();
    list.forEach( n -> {
      CompanyResponseBuilder entry = CompanyResponse.builder()
          .id(n.getId())
          .name(n.getName());
      if ( null != n.getAddress() )
      {
        entry.address(n.getAddress());
      }
      entry.createdBy(n.getCreatedBy()).createdAt(n.getCreatedAt());
      if ( null != n.getUpdatedBy() )
      {
        entry.updatedBy(n.getUpdatedBy());
      }
      if ( null != n.getUpdatedAt() )
      {
        entry.updatedAt(n.getUpdatedAt());
      }
      response.add(entry.build());
    });
    
    return response;
  }
}
