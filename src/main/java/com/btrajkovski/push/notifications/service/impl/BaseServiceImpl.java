package com.btrajkovski.push.notifications.service.impl;

import com.btrajkovski.push.notifications.model.base.BaseEntity;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import com.btrajkovski.push.notifications.service.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bojan on 2.4.16.
 */
@Service
public abstract class BaseServiceImpl implements BaseService {

    private JpaRepository repository;

    @Transactional
    @Override
    public void delete(BaseEntity entity) {
        repository.delete(entity);
    }

    @PostConstruct
    public abstract void init();

    @Transactional(readOnly = true)
    @Override
    public List findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public BaseEntity<?> findById(Serializable id) {
        return (BaseEntity<?>) repository.findOne(id);
    }

    @Transactional
    @Override
    public BaseEntity loadAndDelete(Serializable id) {
        BaseEntity entity = findById(id);
        repository.delete(id);
        return entity;
    }

    @Transactional
    @Override
    public void deleteAll(BaseEntity entity) {
        repository.deleteAll();
    }

    @Transactional
    @Override
    public BaseEntity save(BaseEntity entity) throws UserNotAuthenticated {
        BaseEntity savedEntity = (BaseEntity) repository.save(entity);
        return savedEntity;
    }

    @Override
    public void setRepository(JpaRepository repository) {
        this.repository = repository;
    }
}
