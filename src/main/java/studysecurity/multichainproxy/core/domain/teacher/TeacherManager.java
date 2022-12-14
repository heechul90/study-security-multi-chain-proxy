package studysecurity.multichainproxy.core.domain.teacher;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
public class TeacherManager implements AuthenticationProvider, InitializingBean {

    private HashMap<String, Teacher> teacherDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            if (teacherDB.containsKey(token.getName())) {
                return getAuthenticationToken(token.getName());
            }
            return null;
        }
        TeacherAuthenticationToken token = (TeacherAuthenticationToken) authentication;
        if(teacherDB.containsKey(token.getCredentials())){
            return getAuthenticationToken(token.getCredentials());
        }
        return null;
    }

    private TeacherAuthenticationToken getAuthenticationToken(String id) {
        Teacher teacher = teacherDB.get(id);
        return TeacherAuthenticationToken.builder()
                .principal(teacher)
                .details(teacher.getUsername())
                .authenticated(true)
                .authorities(teacher.getRole())
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == TeacherAuthenticationToken.class ||
                authentication == UsernamePasswordAuthenticationToken.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Teacher("anh", "?????????", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER"))),
                new Teacher("jang", "?????????", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")))
        ).forEach(teacher ->
                teacherDB.put(teacher.getId(), teacher)
        );
    }
}
