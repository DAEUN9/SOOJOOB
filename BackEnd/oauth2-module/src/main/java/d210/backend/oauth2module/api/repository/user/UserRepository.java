package d210.backend.oauth2module.api.repository.user;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}
