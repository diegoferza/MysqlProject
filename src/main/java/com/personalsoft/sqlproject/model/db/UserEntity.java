package com.personalsoft.sqlproject.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
@Builder
@Entity
@Table(name = "USER")
public class UserEntity {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String name;
	@NotNull
	private String email;
	@NotNull
	private Integer age;	
	
}
