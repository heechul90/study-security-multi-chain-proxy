package studysecurity.multichainproxy.core.domain.student;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            if (studentDB.containsKey(token.getName())) {
                return getAuthenticationToken(token.getName());
            }
            return null;
        }
        StudentAuthenticationToken token = (StudentAuthenticationToken) authentication;
        if(studentDB.containsKey(token.getCredentials())){
            return getAuthenticationToken(token.getCredentials());
        }
        return null;
    }

    private StudentAuthenticationToken getAuthenticationToken(String id) {
        Student student = studentDB.get(id);
        return StudentAuthenticationToken.builder()
                .principal(student)
                .details(student.getUsername())
                .authenticated(true)
                .authorities(student.getRole())
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == StudentAuthenticationToken.class ||
                authentication == UsernamePasswordAuthenticationToken.class;

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
