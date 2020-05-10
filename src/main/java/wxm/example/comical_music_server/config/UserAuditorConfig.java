package wxm.example.comical_music_server.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import wxm.example.comical_music_server.entity.bbs.User;

import java.util.Optional;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@Configuration
public class UserAuditorConfig implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        Subject subject = SecurityUtils.getSubject();
        User user= (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        return Optional.ofNullable(user);
    }
}