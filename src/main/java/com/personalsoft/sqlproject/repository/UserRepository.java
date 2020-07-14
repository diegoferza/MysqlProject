package com.personalsoft.sqlproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.personalsoft.sqlproject.model.db.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer>{

}
