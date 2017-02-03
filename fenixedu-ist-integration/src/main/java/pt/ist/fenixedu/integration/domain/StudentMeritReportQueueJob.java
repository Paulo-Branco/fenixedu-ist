package pt.ist.fenixedu.integration.domain;

import org.fenixedu.academic.domain.Degree;
import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.Enrolment;
import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.Grade;
import org.fenixedu.academic.domain.IEnrolment;
import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.domain.QueueJobResult;
import org.fenixedu.academic.domain.StudentCurricularPlan;
import org.fenixedu.academic.domain.degree.DegreeType;
import org.fenixedu.academic.domain.student.Registration;
import org.fenixedu.academic.domain.student.Student;
import org.fenixedu.academic.domain.studentCurriculum.Credits;
import org.fenixedu.academic.domain.studentCurriculum.CurriculumGroup;
import org.fenixedu.academic.domain.studentCurriculum.CurriculumLine;
import org.fenixedu.academic.domain.studentCurriculum.CurriculumModule;
import org.fenixedu.academic.domain.studentCurriculum.Dismissal;
import org.fenixedu.academic.domain.studentCurriculum.EnrolmentWrapper;
import org.fenixedu.academic.domain.studentCurriculum.RootCurriculumGroup;
import org.fenixedu.academic.domain.studentCurriculum.Substitution;
import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.domain.exceptions.DomainException;
import org.fenixedu.bennu.io.servlets.FileDownloadServlet;
import org.fenixedu.commons.spreadsheet.Spreadsheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ist.fenixframework.Atomic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentMeritReportQueueJob extends StudentMeritReportQueueJob_Base {
    private static final Logger logger = LoggerFactory.getLogger(StudentMeritReportQueueJob.class);

    private StudentMeritReportQueueJob(ExecutionYear executionYear) {
        super();
        setExecutionYear(executionYear);
    }

    @Override public String getFilename() {
        return "StudentMeritReport" + getExecutionYear().getQualifiedName().replace("/","-");
    }

    @Atomic
    public static StudentMeritReportQueueJob createRequest(ExecutionYear executionYear) {
        return new StudentMeritReportQueueJob(executionYear);
    }

    @Override public QueueJobResult execute() throws Exception {
        List<Spreadsheet> spreadsheets =  DegreeType.all().filter(dt -> dt.isBolonhaDegree() || dt.isBolonhaMasterDegree() || dt.isIntegratedMasterDegree())
                .map(dt -> generate(dt, getExecutionYear()))
                .collect(Collectors.toList());

        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Spreadsheet.exportToXLSSheets(output, spreadsheets);
            output.close();
        } catch (IOException e) {
            throw new Error(e);
        }

        QueueJobResult result = new QueueJobResult();
        result.setContent(output.toByteArray());
        result.setContentType("application/excel");
        return result;
    }

    private Spreadsheet generate(final DegreeType degreeType, ExecutionYear executionYear) {
        final Spreadsheet spreadsheet = createHeader(degreeType.getName().getContent(), executionYear);
        for (final Degree degree : Bennu.getInstance().getDegreesSet()) {
            if (degreeType != degree.getDegreeType()) {
                continue;
            }

            for (final DegreeCurricularPlan degreeCurricularPlan : degree.getDegreeCurricularPlansSet()) {
                logger.info("Processing: " + degree.getPresentationName() + " - " + degreeCurricularPlan.getName());

                for (final StudentCurricularPlan studentCurricularPlan : degreeCurricularPlan.getStudentCurricularPlansSet()) {
                    final Registration registration = studentCurricularPlan.getRegistration();
                    final Student student = registration.getStudent();
                    if (registration.hasAnyActiveState(executionYear)) {
                        final double approvedCredits =
                                getCredits(executionYear, studentCurricularPlan.getRegistration().getStudent(), true);
                        final double enrolledCredits =
                                getCredits(executionYear, studentCurricularPlan.getRegistration().getStudent(), false);
                        final Person person = student.getPerson();
                        final int curricularYear = registration.getCurricularYear(executionYear);

                        final Spreadsheet.Row row = spreadsheet.addRow();
                        row.setCell(degree.getSigla());
                        row.setCell(student.getNumber());
                        row.setCell(person.getName());
                        row.setCell(enrolledCredits);
                        row.setCell(approvedCredits);
                        row.setCell(curricularYear);

                        final BigDecimal average = calculateAverage(registration, executionYear);
                        row.setCell(average.toPlainString());
                    }
                }
            }
        }
        return spreadsheet;
    }

    private Spreadsheet createHeader(String sheetName, ExecutionYear executionYear) {
        Spreadsheet spreadsheet = new Spreadsheet(sheetName);

        spreadsheet.setHeader("Degree");
        spreadsheet.setHeader("Number");
        spreadsheet.setHeader("Name");
        spreadsheet.setHeader("Credits Enrolled in " + executionYear.getQualifiedName());
        spreadsheet.setHeader("Credits Approved During Year " + executionYear.getQualifiedName());
        spreadsheet.setHeader("Curricular Year During " + executionYear.getQualifiedName());
        spreadsheet.setHeader("Average in " + executionYear.getQualifiedName());

        return spreadsheet;
    }

    private double getCredits(final ExecutionYear executionYear, final Student student, final boolean approvedCredits) {
        double creditsCount = 0.0;
        for (final Registration registration : student.getRegistrationsSet()) {
            for (final StudentCurricularPlan studentCurricularPlan : registration.getStudentCurricularPlansSet()) {
                final RootCurriculumGroup root = studentCurricularPlan.getRoot();
                final Set<CurriculumModule> modules =
                        root == null ? (Set) studentCurricularPlan.getEnrolmentsSet() : root.getCurriculumModulesSet();
                creditsCount += countCredits(executionYear, modules, approvedCredits);
            }
        }
        return creditsCount;
    }

    private double countCredits(final ExecutionYear executionYear, final Set<CurriculumModule> modules, final boolean approvedCredits) {
        double creditsCount = 0.0;
        for (final CurriculumModule module : modules) {
            if (module instanceof CurriculumGroup) {
                final CurriculumGroup courseGroup = (CurriculumGroup) module;
                creditsCount += countCredits(executionYear, courseGroup.getCurriculumModulesSet(), approvedCredits);
            } else if (module instanceof CurriculumLine) {
                final CurriculumLine curriculumLine = (CurriculumLine) module;
                if (curriculumLine.getExecutionYear() == executionYear) {
                    if (approvedCredits) {
                        creditsCount += curriculumLine.getAprovedEctsCredits().doubleValue();
                    } else {
                        creditsCount += curriculumLine.getEctsCredits().doubleValue();
                    }
                }
            }
        }
        return creditsCount;
    }

    private BigDecimal calculateAverage(final Registration registration, final ExecutionYear executionYear) {
        MathContext mathContext = new MathContext(3, RoundingMode.HALF_EVEN);
        BigDecimal[] result = new BigDecimal[] { new BigDecimal(0.000, mathContext), new BigDecimal(0.000, mathContext) };
        for (final StudentCurricularPlan studentCurricularPlan : registration.getStudentCurricularPlansSet()) {
            final RootCurriculumGroup root = studentCurricularPlan.getRoot();
            final Set<CurriculumModule> modules =
                    root == null ? (Set) studentCurricularPlan.getEnrolmentsSet() : root.getCurriculumModulesSet();
            calculateAverage(result, modules, executionYear);
        }
        return result[1].equals(BigDecimal.ZERO) ? result[1] : result[0].divide(result[1], mathContext);
    }

    private void calculateAverage(final BigDecimal[] result, final Set<CurriculumModule> modules, final ExecutionYear executionYear) {
        for (final CurriculumModule module : modules) {
            if (module instanceof CurriculumGroup) {
                final CurriculumGroup courseGroup = (CurriculumGroup) module;
                calculateAverage(result, courseGroup.getCurriculumModulesSet(), executionYear);
            } else if (module instanceof Enrolment) {
                final Enrolment enrolment = (Enrolment) module;
                if (enrolment.isApproved()) {
                    if (enrolment.getExecutionYear() == executionYear) {
                        final Grade grade = enrolment.getGrade();
                        if (grade.isNumeric()) {
                            // TODO: This shouldn't be needed, but it is
                            try {
                                final BigDecimal ectsCredits = new BigDecimal(enrolment.getEctsCredits());
                                final BigDecimal value = grade.getNumericValue().multiply(ectsCredits);
                                result[0] = result[0].add(value);
                                result[1] = result[1].add(ectsCredits);
                            } catch (DomainException ignored) {}
                        }
                    }
                }
            } else if (module instanceof Dismissal) {
                final Dismissal dismissal = (Dismissal) module;
                if (dismissal.getExecutionYear() == executionYear && dismissal.getCurricularCourse() != null
                        && !dismissal.getCurricularCourse().isOptionalCurricularCourse()) {

                    final Credits credits = dismissal.getCredits();
                    if (credits instanceof Substitution) {
                        final Substitution substitution = (Substitution) credits;
                        for (final EnrolmentWrapper enrolmentWrapper : substitution.getEnrolmentsSet()) {
                            final IEnrolment iEnrolment = enrolmentWrapper.getIEnrolment();

                            final Grade grade = iEnrolment.getGrade();
                            if (grade.isNumeric()) {
                                // TODO: This shouldn't be needed, but it is
                                try {
                                    final BigDecimal ectsCredits = new BigDecimal(iEnrolment.getEctsCredits());
                                    final BigDecimal value = grade.getNumericValue().multiply(ectsCredits);
                                    result[0] = result[0].add(value);
                                    result[1] = result[1].add(ectsCredits);
                                } catch (DomainException ignored) {}
                            }
                        }
                    } else {
                        final Grade grade = dismissal.getGrade();
                        if (grade.isNumeric()) {
                            final BigDecimal ectsCredits = new BigDecimal(dismissal.getEctsCredits());
                            final BigDecimal value = grade.getNumericValue().multiply(ectsCredits);
                            result[0] = result[0].add(value);
                            result[1] = result[1].add(ectsCredits);
                        }
                    }
                }
            }
        }
    }

    public String getDownloadUrl() {
        return FileDownloadServlet.getDownloadUrl(this.getFile());
    }
}
