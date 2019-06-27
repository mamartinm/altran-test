package com.altran.mamartin.model.services;

import com.altran.mamartin.beans.dto.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public abstract class BaseService<T, R extends Serializable> {

  protected R repository;

  @Autowired
  protected ApplicationContext applicationContext;

  @Autowired
  protected CacheManager cacheManager;

  public BaseService(R repository) {
    this.repository = repository;
  }

  Page<T> getPagination(Pageable pageable, List<T> alls) {
    PagedListHolder<T> pagedListHolder = new PagedListHolder<>(alls);
    pagedListHolder.setPageSize(pageable.getPageSize());
    pagedListHolder.setPage(pageable.getPageNumber());
    return new PageImpl<>(pagedListHolder.getPageList(), pageable, alls.size());
  }

  protected abstract CompletableFuture<List<T>> findAll();

  protected abstract CompletableFuture<Page<Entity>> findAllPaginated(Pageable pageable);

  protected abstract CompletableFuture<Optional<Entity>> findById(String id);

}
