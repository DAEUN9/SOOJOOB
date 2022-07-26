package soojoop.daeun.test.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soojoop.daeun.test.user.domain.User;


/**
 *
 * @author cos
 * JPA는 기본 CRUD를 JpaRepository가 상속하는 CrudRepository가 가지고 있음.
 * JPA는 method names 전략을 가짐. README.md 사진 참고
 */

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
// CRUD 함수를 JpaRepository가 들고있음
// @Repository라는 어노테이션이 없어도 IoC됨. JpaRepository를 상속했기 때문
public interface UserRepository extends JpaRepository<User, Integer>{
    //findBy규칙 -> Username문법
    // SELECT * FROM user WHERE username = ?1

    User findByUsername(String username);

    // SELECT * FROM user WHERE provider = ?1 and providerId = ?2
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}