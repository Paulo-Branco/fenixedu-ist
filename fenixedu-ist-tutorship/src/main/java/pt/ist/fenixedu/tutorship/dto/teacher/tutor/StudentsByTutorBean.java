/**
 * Copyright © 2013 Instituto Superior Técnico
 *
 * This file is part of FenixEdu IST Tutorship.
 *
 * FenixEdu IST Tutorship is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu IST Tutorship is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu IST Tutorship.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.fenixedu.tutorship.dto.teacher.tutor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.Teacher;

import pt.ist.fenixedu.tutorship.domain.Tutorship;

public class StudentsByTutorBean implements Serializable {
    private Teacher teacher;
    private ExecutionYear studentsEntryYear = null;
    private List<TutorshipBean> studentsList = new ArrayList<TutorshipBean>();

    public StudentsByTutorBean(Teacher teacher) {
        setTeacher(teacher);
    }

    public StudentsByTutorBean(Teacher teacher, ExecutionYear studentsEntryYear, List<Tutorship> studentsList) {
        setTeacher(teacher);
        setStudentsEntryYear(studentsEntryYear);
        setStudentsList(studentsList);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public ExecutionYear getStudentsEntryYear() {
        return studentsEntryYear;
    }

    public void setStudentsEntryYear(ExecutionYear studentsEntryYear) {
        this.studentsEntryYear = studentsEntryYear;
    }

    public List<TutorshipBean> getStudentsList() {
        return studentsList;
    }

    public List<Tutorship> getActiveTutorshipsMatchingEntryYear() {
        List<Tutorship> matchingTutorships = new ArrayList<Tutorship>();
        for (Tutorship tutorship : Tutorship.getActiveTutorships(getTeacher())) {
            if (getStudentsEntryYear() == null
                    || (tutorship.getStudentCurricularPlan().getRegistration().getIngressionYear() == getStudentsEntryYear())) {
                matchingTutorships.add(tutorship);
            }
        }

        return matchingTutorships;
    }

    public void setStudentsList(List<Tutorship> students) {
        studentsList = (students.stream().map(x -> new TutorshipBean(x)).collect(Collectors.toList()));
    }
}