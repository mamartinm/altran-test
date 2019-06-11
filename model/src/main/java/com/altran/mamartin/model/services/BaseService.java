package com.altran.mamartin.model.services;

import java.util.List;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public abstract class BaseService<T> {

  protected Page<T> getPagination(Pageable pageable, List<T> alls) {
    PagedListHolder<T> pagedListHolder = new PagedListHolder<>(alls);
    pagedListHolder.setPageSize(pageable.getPageSize());
    pagedListHolder.setPage(pageable.getPageNumber());
    return new PageImpl<>(pagedListHolder.getPageList(), pageable, alls.size());
  }

}
