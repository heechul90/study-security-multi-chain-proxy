package studysecurity.multichainproxy.core.domain.teacher;

import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherAuthenticationToken implements Authentication {

    private Teacher principal;
    private String credentials;
    private String details;
    private boolean authenticated;
    private Set<GrantedAuthority> authorities;

    @Override
    public String getName() {
        return principal == null ? "" : principal.getUsername();
    }
}
