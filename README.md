# study-security-multi-chain-proxy

## 프로젝트 설정
- 프로젝트 선택
    - Project : Gradle Project
    - Language : Java
    - Spring Boot : 2.6.x
- Project Metadata
    - Group : study.security
    - Artifact : multiChainProxy
    - Package name : study.security.multichainproxy
    - Packaging : Jar
    - Java : 11

## 내용

### CustomLoginFilter
- SecurityConfiguration 설정(springboot version = 2.6.x)
```java

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAt(new CustomLoginFilter(authenticationManager(), UsernamePasswordAuthenticationFilter.class)
        ;
    }
}
```
- CustomLoginFilter 설정
```java
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    public CustomLoginFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        username = (username != null) ? username : "";
        username = username.trim();
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        String type = request.getParameter("type");
        if(type == null || !type.equals("teacher")){
            // student
            StudentAuthenticationToken token = StudentAuthenticationToken.builder()
                    .credentials(username).build();
            return this.getAuthenticationManager().authenticate(token);
        }else{
            // teacher
            TeacherAuthenticationToken token = TeacherAuthenticationToken.builder()
                    .credentials(username).build();
            return this.getAuthenticationManager().authenticate(token);
        }
    }
}
```

[[이전으로]](https://github.com/heechul90/study-security-basic-authentication) [[다음으로]](https://github.com/heechul90/study-security-user-details)


[[복습하기//적용하기]](https://github.com/heechul90/heech-member-server)