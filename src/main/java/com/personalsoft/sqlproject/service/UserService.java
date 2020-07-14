package com.personalsoft.sqlproject.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.personalsoft.sqlproject.repository.UserRepository;
import com.personalsoft.sqlproject.model.db.UserEntity;
import com.personalsoft.sqlproject.model.dto.UserDto;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);	
	
	@Autowired
	UserRepository userRepository;
	
	public List<UserEntity> list(){
		return (List<UserEntity>)userRepository.findAll();
	}
	
	public UserEntity create(UserDto user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(user.getEmail());
		userEntity.setName(user.getName());
		userEntity.setAge(user.getAge());
		return userRepository.save(userEntity);
	}
	
	public UserEntity update(UserDto user, Integer id) {
		UserEntity userEntity = userRepository.findById(id).orElse(null);
		if (userEntity != null && userEntity.getAge() >= 25) {			
			if (userEntity.getEmail().equals(user.getEmail())) return null;	
			if (userEntity.getName().equals(user.getName())) return null;	
			if (userEntity.getAge().equals(user.getAge())) return null;	
			
			userEntity.setName(user.getName());
			userEntity.setAge(user.getAge());
			userRepository.save(userEntity);						
		}else {
			logger.error("El usuario no se puede actualizar");
		}
		return userEntity;
	}
	
	public String delete(Integer id) {
		UserEntity userEntity = userRepository.findById(id).orElse(null);		
		if (userEntity != null) {
			userRepository.deleteById(userEntity.getId());
			return "Usuario Eliminado con exito";
		}else {
			return "Usuario no existe";
		}		
	}
}
