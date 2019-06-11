package com.altran.mamartin.model.services;

import static org.springframework.security.config.Elements.HTTP;

import com.altran.mamartin.beans.utils.Constants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class BaseService<T> {

  @Autowired
  protected ApplicationContext applicationContext;
  @Value("${app.server.hostname}")
  private String hostname;

  @Value("${server.port}")
  private String port;

  @Value("${server.servlet.context-path}")
  private String path;

  protected Page<T> getPagination(Pageable pageable, List<T> alls) {
    PagedListHolder<T> pagedListHolder = new PagedListHolder<>(alls);
    pagedListHolder.setPageSize(pageable.getPageSize());
    pagedListHolder.setPage(pageable.getPageNumber());
    return new PageImpl<>(pagedListHolder.getPageList(), pageable, alls.size());
  }

  protected UriComponentsBuilder getUriBuilder(String pathResource) {
    return UriComponentsBuilder.newInstance().scheme(HTTP).host(hostname).port(port).path(path).path(pathResource).path(Constants.SLASH);
  }

}
