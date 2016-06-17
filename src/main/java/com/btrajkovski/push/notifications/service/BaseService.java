package com.btrajkovski.push.notifications.service;


import com.btrajkovski.push.notifications.model.base.BaseEntity;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseEntity<?>, R extends JpaRepository<T, Long>> {

    void setRepository(R jpaRepository);

    void delete(T entity);

    List<T> findAll();

    T findById(Serializable id);

    T save(T entity) throws UserNotAuthenticated;

    T loadAndDelete(Serializable id);

    void deleteAll(T entity);
}