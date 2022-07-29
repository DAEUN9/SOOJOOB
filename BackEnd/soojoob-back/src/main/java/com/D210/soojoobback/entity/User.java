package com.D210.soojoobback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM - Object Relation Mapping

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="username", length=50, unique = true)
	private String username;

	private String password;

	private String email;

	private String role; //ROLE_USER, ROLE_ADMIN

	private Integer age;

	private String gender;

	private String region;

	private Integer weight;

	private Integer height;

	private boolean activated;
	// OAuth를 위해 구성한 추가 필드 2개
	private String provider;
	private String providerId;
	@CreationTimestamp
	private Timestamp createDate;
}
