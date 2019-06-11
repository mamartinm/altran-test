package com.altran.mamartin.beans.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization {

  private String id;
  private String code;
  @JsonProperty("display_name")
  private String displayName;
  private String description;
  @JsonProperty("description_translated")
  private Map<String, String> descriptionTranslated;
  private Organization parent;
  private String name;
  @JsonProperty("is_organization")
  private Boolean isOrganization;
  private String state;
  @JsonProperty("image_display_url")
  private String imageDisplayUrl;
  @JsonProperty("tematica_nti")
  private String tematicaNti;
  @JsonProperty("image_url")
  private String imageUrl;
  private List<Map<String, String>> groups;
  private String type;
  private String title;
  @JsonProperty("revision_id")
  private String revisionId;
  @JsonProperty("title_translated")
  private Map<String, String> titleTranslated;
  @JsonProperty("num_followers")
  private Long numFollowers;
  private Long packageCount;
  @JsonProperty("approval_status")
  private String approvalStatus;
  private Date created;

}
