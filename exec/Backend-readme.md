# Backend



### application.properties

```java
jwt.token.key = skldjficmnwl123kclknmcnfklsdkskmxxfofohue;
```



### application.yml

```java
#server:
#  port: 8080
#  servlet:
#    context-path: /
#    encoding:
#      charset: UTF-8
#      enabled: true
#      force: true

# jdbc:mysql://i7d210.p.ssafy.io:3306/d210?serverTimezone=Asia/Seoul
# jdbc:mysql://localhost:3306/d210?serverTimezone=Asia/Seoul
# 로컬주소
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://i7d210.p.ssafy.io:3306/d210?serverTimezone=Asia/Seoul
    username: d210
    password: tjrgus777!

  #  mvc:
  #    view:
  #      prefix: /templates/
  #      suffix: .mustache

  jpa:
    hibernate:
      ddl-auto: none #create update none
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: true
```



### application.yml

```java
plugins {
	id 'org.springframework.boot' version '2.6.10'
	id 'io.spring.dependency-management' version '1.0.12.RELEASE'
	id 'java'
}

group = 'com.D210'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
	all {
		//logback과의 충돌 방지
		exclude group: 'org.springframework.boot', module: 'spring-boot-starter-logging'
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-log4j2'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'mysql:mysql-connector-java'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'

	implementation(group: 'io.jsonwebtoken', name: 'jjwt-api', version: '0.11.2')
	runtimeOnly(group: 'io.jsonwebtoken', name: 'jjwt-impl', version: '0.11.2')
	runtimeOnly(group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.11.2')
	implementation 'io.jsonwebtoken:jjwt:0.9.1'
}

tasks.named('test') {
	useJUnitPlatform()
}

```



### build.gradle

```java
plugins {
	id 'org.springframework.boot' version '2.6.10'
	id 'io.spring.dependency-management' version '1.0.12.RELEASE'
	id 'java'
}

group = 'com.D210'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
	all {
		//logback과의 충돌 방지
		exclude group: 'org.springframework.boot', module: 'spring-boot-starter-logging'
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-log4j2'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'mysql:mysql-connector-java'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'

	implementation(group: 'io.jsonwebtoken', name: 'jjwt-api', version: '0.11.2')
	runtimeOnly(group: 'io.jsonwebtoken', name: 'jjwt-impl', version: '0.11.2')
	runtimeOnly(group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.11.2')
	implementation 'io.jsonwebtoken:jjwt:0.9.1'
}

tasks.named('test') {
	useJUnitPlatform()
}

```

