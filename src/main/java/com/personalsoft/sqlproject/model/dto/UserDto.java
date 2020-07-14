package com.personalsoft.sqlproject.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class UserDto {
		
	private Integer id;
	@Size(min = 10)
	private String name;
	private String email;	
	@Min(18)
	private Integer age;
}
