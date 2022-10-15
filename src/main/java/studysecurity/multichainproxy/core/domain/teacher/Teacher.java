package studysecurity.multichainproxy.core.domain.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {

    private String id;
    private String username;

    @JsonIgnore
    private Set<GrantedAuthority> role;
}
