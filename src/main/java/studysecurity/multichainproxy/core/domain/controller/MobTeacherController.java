package studysecurity.multichainproxy.core.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studysecurity.multichainproxy.core.domain.student.Student;
import studysecurity.multichainproxy.core.domain.student.StudentManager;
import studysecurity.multichainproxy.core.domain.teacher.Teacher;

import java.util.List;

@RestController
@RequestMapping(value = "/api/teacher")
@RequiredArgsConstructor
public class MobTeacherController {

    private final StudentManager studentManager;

    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping("/students")
    public List<Student> students(@AuthenticationPrincipal Teacher teacher) {
        return studentManager.myStudents(teacher.getId());
    }
}
