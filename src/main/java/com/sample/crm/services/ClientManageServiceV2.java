package com.sample.crm.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sample.crm.bean.ClientRequest;
import com.sample.crm.bean.ClientResponse;
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
  
  @Autowired
  private Mapper mapper;
  
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
    
    Client entity = mapper.map(request, Client.class);
    entity.setCreatedBy(userInfo.getUserId());
    entity.setCreatedAt(new Date());
    entity.setUpdatedBy(userInfo.getUserId());
    entity.setUpdatedAt(new Date());
    
    clientMapper.insert(entity);
    
    ClientResponse response = mapper.map(entity, ClientResponse.class);
    
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
    
    ClientResponse response = mapper.map(entity, ClientResponse.class);
    
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
      ClientResponse entry = mapper.map(n, ClientResponse.class);
      response.add(entry);
    });
    
    return response;
  }
}
