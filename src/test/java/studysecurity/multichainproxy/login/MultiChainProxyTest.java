package studysecurity.multichainproxy.login;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import studysecurity.multichainproxy.core.domain.student.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultiChainProxyTest {

    @LocalServerPort
    int port;

    TestRestTemplate testClient = new TestRestTemplate("anh", "1");

    @Test
    @DisplayName("로그인해서 학생 리스트를 내려받는다")
    void test_1() {
        ResponseEntity<List<Student>> response = testClient.exchange("http://localhost:" + port + "/api/teacher/students",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
                });
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(3);
    }
}
