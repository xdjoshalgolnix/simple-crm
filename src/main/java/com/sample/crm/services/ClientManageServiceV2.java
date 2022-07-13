package com.sample.crm.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sample.crm.bean.ClientRequest;
import com.sample.crm.bean.ClientResponse;
import com.sample.crm.bean.ClientResponse.ClientResponseBuilder;
import com.sample.crm.bean.UserInfo;
import com.sample.crm.exception.APIException;
import com.sample.crm.exception.ErrorCode;
import com.sample.crm.mapper.ClientMapper;
import com.sample.crm.mapper.CompanyMapper;
import com.sample.crm.model.Client;
import com.sample.crm.model.Company;
import com.sample.crm.util.ValidateUtils;

@Service
public class ClientManageServiceV2
{
  @Resource
  private ClientMapper clientMapper;
  
  @Resource
  private CompanyMapper companyMapper;
  
  @Transactional(rollbackOn = APIException.class)
  public List<ClientResponse> createClients(List<ClientRequest> request, UserInfo userInfo) throws APIException
  {
    List<ClientResponse> list = new ArrayList<>();
    for ( ClientRequest n : request )
    {
      list.add(createClient(n, userInfo));
    }
    return list;
  }
  
  public ClientResponse createClient(ClientRequest request, UserInfo userInfo) throws APIException
  {
    vaildClientRequest(request);
    
    Company entry = companyMapper.selectById(request.getCompanyId());
    if ( null == entry )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, StringUtils.join("company_id = ", request.getCompanyId() + " not existed, please check"));
    }
    
    Client entity = Client.builder()
        .name(request.getName())
        .companyId(request.getCompanyId())
        .email(request.getEmail())
        .phone(request.getPhone())
        .createdBy(userInfo.getUserId())
        .createdAt(new Date())
        .updatedBy(userInfo.getUserId())
        .updatedAt(new Date())
        .build();
    
    clientMapper.insert(entity);
    
    ClientResponse response = ClientResponse.builder()
        .id(entity.getId())
        .companyId(entity.getCompanyId())
        .name(entity.getName())
        .email(entity.getEmail())
        .phone(entity.getPhone())
        .createdBy(entity.getCreatedBy())
        .createdAt(entity.getCreatedAt())
        .updatedBy(entity.getUpdatedBy())
        .updatedAt(entity. getUpdatedAt())
        .build();
    
    return response;
  }
  
  private void vaildClientRequest(ClientRequest request) throws APIException
  {
    if ( null == request.getName() || request.getName().isEmpty() )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, ManageMessageHelper.buildErrorStr("name", request.getName()));
    }
    
    if( null != request.getEmail() && !ValidateUtils.validateEmail(request.getEmail()) )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, ManageMessageHelper.buildErrorStr("email", request.getEmail()));
    }
    
    if( null != request.getPhone() && !ValidateUtils.validatePhone(request.getPhone()) )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, ManageMessageHelper.buildErrorStr("phone", request.getPhone()));
    }
  }
  
  public ClientResponse updateClient(long id, ClientRequest request, UserInfo userInfo) throws APIException
  {
    Client entity = clientMapper.selectById(id);
    if ( null == entity )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, StringUtils.join("id = ", id, " not existed, please check"));
    }
    
    vaildClientRequest(request);
    
    Company company_entry = companyMapper.selectById(request.getCompanyId());
    if ( null == company_entry )
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, StringUtils.join("company_id = ", request.getCompanyId(), " not existed, please check"));
    }
    
    entity.setName(request.getName());
    entity.setCompanyId(request.getCompanyId());
    entity.setEmail(request.getEmail());
    entity.setPhone(request.getPhone());
    entity.setUpdatedBy(userInfo.getUserId());
    entity.setUpdatedAt(new Date());
    
    clientMapper.updateById(entity);
    
    ClientResponse response = ClientResponse.builder()
        .id(entity.getId())
        .companyId(entity.getCompanyId())
        .name(entity.getName())
        .email(entity.getEmail())
        .phone(entity.getPhone())
        .createdBy(entity.getCreatedBy())
        .createdAt(entity.getCreatedAt())
        .updatedBy(entity.getUpdatedBy())
        .updatedAt(entity. getUpdatedAt())
        .build();
    
    return response;
  }

  public void deleteClient(long id) throws APIException
  {
    if ( clientMapper.exists(Wrappers.<Client>query().eq("id", id)) )
    {
      clientMapper.deleteById(id);
    }
    else
    {
      throw new APIException(ErrorCode.BAD_REQUEST_SCH1, StringUtils.join("id = ", id, " not existed, please check"));
    }
  }
  
  public List<ClientResponse> listClientData()
  {
    List<Client> list = clientMapper.selectList(Wrappers.query());
    
    List<ClientResponse> response = new ArrayList<>();
    list.forEach( n -> {
      ClientResponseBuilder entry = ClientResponse.builder()
          .id(n.getId())
          .companyId(n.getCompanyId())
          .name(n.getName());
      if ( null != n.getEmail() )
      { 
        entry.email(n.getEmail());
      }
      if ( null != n.getPhone() )
      {
        entry.phone(n.getPhone());
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
