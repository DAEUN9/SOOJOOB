package me.silvernine.tutorial.repository;

import me.silvernine.tutorial.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//user 엔티티에 매핑
//jpaRepository 상속하면서 findAll, save 등의 메소드 기본적으로 사용가능
public interface UserRepository extends JpaRepository<User, Long> {

//   쿼리가 수행될때 Lazy조회가 아니고 eager조회로 authorities정보 같이 가져옴
   @EntityGraph(attributePaths = "authorities")
//   user 정보를 가져올 때 권한정보도 같이 가져옴
   Optional<User> findOneWithAuthoritiesByUsername(String username);
}
