/**
 * Copyright © 2013 Instituto Superior Técnico
 *
 * This file is part of FenixEdu IST QUC.
 *
 * FenixEdu IST QUC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu IST QUC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu IST QUC.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package pt.ist.fenixedu.quc.ui.renderers;

import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.util.Bundle;
import org.fenixedu.bennu.core.i18n.BundleUtil;

import pt.ist.fenixWebFramework.renderers.OutputRenderer;
import pt.ist.fenixWebFramework.renderers.components.HtmlComponent;
import pt.ist.fenixWebFramework.renderers.components.HtmlTable;
import pt.ist.fenixWebFramework.renderers.components.HtmlTableCell;
import pt.ist.fenixWebFramework.renderers.components.HtmlTableCell.CellType;
import pt.ist.fenixWebFramework.renderers.components.HtmlTableRow;
import pt.ist.fenixWebFramework.renderers.components.HtmlText;
import pt.ist.fenixWebFramework.renderers.layouts.Layout;
import pt.ist.fenixedu.quc.dto.GroupResultsSummaryBean;
import pt.ist.fenixedu.quc.dto.QuestionResultsSummaryBean;

/**
 * @author - Ricardo Rodrigues (ricardo.rodrigues@ist.utl.pt)
 * 
 */
public class InquiryTeachersResultsResumeRenderer extends OutputRenderer {

    @Override
    protected Layout getLayout(Object object, Class type) {
        return new InquiryGeneralGroupLayout(object);
    }

    private class InquiryGeneralGroupLayout extends Layout {

        public InquiryGeneralGroupLayout(Object object) {
        }

        @Override
        public HtmlComponent createComponent(Object object, Class type) {
            GroupResultsSummaryBean groupResultsSummaryBean = (GroupResultsSummaryBean) object;

            final HtmlTable mainTable = new HtmlTable();
            for (QuestionResultsSummaryBean summaryBean : groupResultsSummaryBean.getQuestionsResults()) {
                HtmlTableRow row = mainTable.createRow();
                HtmlTableCell resultCell = row.createCell();
                String divText =
                        "<div class=\"bar-" + summaryBean.getResultClassification().toString().toLowerCase() + "\">&nbsp;</div>";
                HtmlText divComponent = new HtmlText(divText);
                divComponent.setEscaped(false);
                resultCell.setBody(divComponent);
                HtmlTableCell labelCell = row.createCell(CellType.HEADER);
                Person person = summaryBean.getQuestionResult().getProfessorship().getPerson();
                String teacherId = "";
                if (person.getEmployee() != null) {
                    teacherId = person.getEmployee().getEmployeeNumber().toString();
                } else {
                    teacherId = person.getUsername();
                }
                teacherId = " (" + teacherId + ") - ";

                labelCell.setBody(new HtmlText(BundleUtil.getString(Bundle.ENUMERATION, summaryBean.getQuestionResult()
                        .getShiftType().name())
                        + person.getName() + teacherId));
            }
            return mainTable;
        }

        @Override
        public String getClasses() {
            return "graph teacher-results";
        }
    }
}
