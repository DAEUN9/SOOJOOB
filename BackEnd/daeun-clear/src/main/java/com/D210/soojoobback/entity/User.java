package com.D210.soojoobback.entity;

import com.D210.soojoobback.dto.user.LoginDetailResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

// ORM - Object Relation Mapping

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@OneToMany(mappedBy = "ploggingUser", orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Plogging> ploggings;

	@OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
	@JsonIgnore
	private  List<Article> articleList;

	@OneToOne(mappedBy = "userRecord", cascade = ALL, orphanRemoval = true)
	@JsonIgnore
	private Record record;

	@OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
	@JsonIgnore
	private List<UserBadge> userBadges = new ArrayList<>();

	public void addArticle(Article article){
		articleList.add(article);
	}

	public User(String username, String password, String email, String role, Integer age, String gender, Integer weight, Integer height
			,boolean activated, String region) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.gender = gender;
		this.region = region;
		this.activated = activated;
	}

	public User( String username, String password, String email, String role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public void update(String username, String password, String email) {
		this.email = email;
		this.password = password;
		this.username = username;
	}

	public LoginDetailResponseDto toBuildDetailUser() {
		return LoginDetailResponseDto.builder()
				.id(this.id)
				.email(this.email)
				.username(this.username)
//                .password(this.password)
				.role(this.role)
				.gender(this.gender)
				.age(this.age)
				.weight(this.weight)
				.height(this.height)
				.region(this.region)
				.build();
	}

//	@OneToMany(orphanRemoval = true, targetEntity = Badge.class)
//	@JsonIgnore
//	private List<Badge> badges = new ArrayList<>();
//
//
//	public void addBadge(Badge badge){
//		this.getBadges().add(badge);
//	}
}
