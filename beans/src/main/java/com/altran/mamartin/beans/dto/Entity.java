package com.altran.mamartin.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity {

  private String code;
  private Organization organization;
  private String description;

}
