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
        User user= null;
        try {
            Subject subject = SecurityUtils.getSubject();
            user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        } catch (Exception e) {
            return Optional.ofNullable(user);
        }
        return Optional.ofNullable(user);
    }
}