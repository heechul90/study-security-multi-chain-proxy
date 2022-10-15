package studysecurity.multichainproxy.core.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studysecurity.multichainproxy.core.domain.student.StudentManager;
import studysecurity.multichainproxy.core.domain.teacher.Teacher;

@Controller
@RequestMapping(value = "/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final StudentManager studentManager;

    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping(value = "/main")
    public String main(@AuthenticationPrincipal Teacher teacher, Model model) {
        model.addAttribute("studentList", studentManager.myStudents(teacher.getId()));
        return "teacherMain";
    }
}
