package pt.ist.fenixedu.integration.ui.spring.controller;

import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.QueueJob;
import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.spring.portal.SpringApplication;
import org.fenixedu.bennu.spring.portal.SpringFunctionality;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ist.fenixedu.integration.domain.StudentMeritReportQueueJob;
import pt.ist.fenixframework.FenixFramework;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringApplication(group = "#pedagogicalCouncil", path = "student.merit.reports.title", title = "student.merit.reports.title")
@SpringFunctionality(app = StudentMeritReportsController.class, title = "student.merit.reports.title")
@Controller
@RequestMapping("/studentMeritReports")
public class StudentMeritReportsController {

    @RequestMapping(method = RequestMethod.GET)
    public String showReports(Model model, @ModelAttribute("studentMeritReportsBean") StudentMeritReportsBean bean) {
        model.addAttribute("studentMeritReportsBean", bean);

        List<ExecutionYear> availableExecutionYears = Bennu.getInstance().getExecutionYearsSet().stream()
                .sorted(Comparator.comparing(ExecutionYear::getBeginCivilYear).reversed())
                .collect(Collectors.toList());
        model.addAttribute("availableExecutionYears", availableExecutionYears);

        return "fenixedu-ist-integration/studentMeritReports/viewStudentMeritReports";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String generateReport(Model model, @ModelAttribute("studentMeritReportsBean") StudentMeritReportsBean bean,
            RedirectAttributes redirectAttributes) {

        ExecutionYear executionYear = FenixFramework.getDomainObject(bean.getExecutionYearId());
        StudentMeritReportQueueJob.createRequest(executionYear);

        redirectAttributes.addFlashAttribute("studentMeritReportsBean", bean);
        redirectAttributes.addFlashAttribute("newGenerationRequest", true);

        return "redirect:/studentMeritReports";
    }

    @ModelAttribute("availableReports")
    public List<QueueJob> getAvailableStudentMeritReportJobs() {
        return StudentMeritReportQueueJob.getAllJobsForClassOrSubClass(StudentMeritReportQueueJob.class, 100,
                (q1,q2) -> q2.getRequestDate().compareTo(q1.getRequestDate()));
    }
}
