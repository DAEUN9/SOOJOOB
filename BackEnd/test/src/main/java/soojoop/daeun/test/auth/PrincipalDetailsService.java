package soojoop.daeun.test.auth;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import soojoop.daeun.test.user.domain.User;
import soojoop.daeun.test.user.repository.UserRepository;


//시큐리티 설정에서 loginProcessingUrl("/loginProc")
// 로그인 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수 실행
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session = Authentication = UserDetails
    // 즉, 시큐리티 session(내부 (Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            return null;
        }else {
            // 유저네임이 있으면
            return new PrincipalDetails(user);
        }

    }

}