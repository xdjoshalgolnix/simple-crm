
package com.sample.crm.entity.cpk;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAccessControlId implements Serializable
{
  private static final long serialVersionUID = -6880729378934346936L;

  private long roleId;

  private String nouns;
}
