package com.altran.mamartin.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity implements RestConvertible {

  private String id;
  private String code;
  private Organization organization;
  private String description;
  private String uri;
}
