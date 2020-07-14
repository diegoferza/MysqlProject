package com.personalsoft.sqlproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personalsoft.sqlproject.controller.UserController;
import com.personalsoft.sqlproject.model.db.UserEntity;
import com.personalsoft.sqlproject.model.dto.UserDto;
import com.personalsoft.sqlproject.repository.UserRepository;
import com.personalsoft.sqlproject.service.UserService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=MysqlProjectApplication.class)
@WebMvcTest({UserController.class, UserService.class})
class MysqlProjectApplicationTests {
    
	private static final Logger logger = LoggerFactory.getLogger(MysqlProjectApplicationTests.class);	
	private ObjectMapper mapper = new ObjectMapper();
	private static final Integer IdUser= 3;
	
	@Autowired
	UserController controller;
	
	@Autowired
	MockMvc mock;
	
	@MockBean
	UserRepository userRepository;
	
	UserDto userDto;
	
	@BeforeEach
	void contextLoads() {
		userDto = UserDto.builder().name("Diego Fernandez").email("diegoferza183@gmail.com").age(25).build();
	}
	
	@Test
	void user_UT01_CreateUserSuccess_ReturnOkAndAnUser() throws Exception{
		logger.info("user_UT01_CreateUserSuccess_ReturnOkAndAnUser");
	   //GiVEN
		userDto.setAge(20);
		//UserDto userDto = UserDto.builder().name("Diego Fernandez").email("diegoferza183@gmail.com").age(37).build();
	    UserEntity userRepositoryResponse = UserEntity.builder().id(IdUser).name("Diego Fernandez").email("diegoferza183@gmail.com").build();
	    when(userRepository.save(any(UserEntity.class))).thenReturn(userRepositoryResponse);
	    //WHEN
		//UserEntity userResponse = controller.createUser(userDto);
	    MvcResult mvcResult = getResult(userDto);
	    String userEntityJson = mvcResult.getResponse().getContentAsString();
	    UserEntity userResponse = mapper.readValue(userEntityJson, UserEntity.class);
		//THEN
		 assertEquals(userDto.getName(), userResponse.getName());
		 assertEquals(userDto.getEmail(), userResponse.getEmail());
		 assertNotNull(userResponse.getId());
	     assertTrue(userDto.getAge()>=18);
	}
	
	@Test
	void user_UT02_EditUserSuccess_ReturnOkAndEditedUser() throws Exception {
		logger.info("user_UT02_EditUserSuccess_ReturnOkAndEditedUser()");
		// GIVEN
			
		userDto.setAge(25);
		UserEntity userRepoResponse = UserEntity.builder().id(IdUser).name("Diego Fernandez").email("diegoferza183@gmail.com").age(25).build();
		Optional<UserEntity> userRepoResponseOptional = Optional.of(userRepoResponse);
		
		UserEntity userRepoResponseEdited = UserEntity.builder().id(IdUser).name("Nelson Fernandez").email("diegoferza2016@gmail.com").age(28).build();
		when(userRepository.findById(any(Integer.class))).thenReturn(userRepoResponseOptional);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userRepoResponseEdited);
		
		// WHEN
		
		MvcResult mvcRes = getResultPut(userDto, IdUser);
		String userEntityJson = mvcRes.getResponse().getContentAsString();
		UserEntity userEntity = mapper.readValue(userEntityJson, UserEntity.class);
		
		// THEN
		assertNotNull(userRepoResponseOptional);
		assertEquals("Diego Fernandez", userEntity.getName());
		assertTrue(userEntity.getAge()>=25);
		
	}
	
	private MvcResult getResult(UserDto requestObject) throws Exception {
		String json = mapper.writeValueAsString(requestObject);

		return this.mock.perform(post("/")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andReturn();
	}
	
	private MvcResult getResultPut(UserDto requestObject, Integer id) throws Exception {
		String json = mapper.writeValueAsString(requestObject);
		
		StringBuilder url = new StringBuilder("/");
		url.append(id);		
		return this.mock.perform(put(url.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andReturn();
	}

}
