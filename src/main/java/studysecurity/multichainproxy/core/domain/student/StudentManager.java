package studysecurity.multichainproxy.core.domain.student;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentManager implements AuthenticationProvider, InitializingBean {

    private HashMap<String, Student> studentDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        StudentAuthenticationToken token = (StudentAuthenticationToken) authentication;
        if(studentDB.containsKey(token.getCredentials())){
            Student student = studentDB.get(token.getCredentials());
            return StudentAuthenticationToken.builder()
                    .principal(student)
                    .details(student.getUsername())
                    .authenticated(true)
                    .authorities(student.getRole())
                    .build();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == StudentAuthenticationToken.class;
    }

    public List<Student> myStudents(String teacherId) {
        return studentDB.values().stream().filter(student -> student.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Student("kang", "강백호", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "anh"),
                new Student("chae", "채치수", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "anh"),
                new Student("seo", "서태웅", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "anh"),
                new Student("song", "송태섭", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "jang"),
                new Student("jeong", "정대만", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "jang")
        ).forEach(student ->
                studentDB.put(student.getId(), student)
        );
    }
}
