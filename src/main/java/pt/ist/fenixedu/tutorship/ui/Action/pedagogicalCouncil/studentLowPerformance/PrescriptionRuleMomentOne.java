/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Core.
 *
 * FenixEdu Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Core.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.fenixedu.tutorship.ui.Action.pedagogicalCouncil.studentLowPerformance;

import java.math.BigDecimal;

import org.fenixedu.academic.domain.ExecutionYear;

import pt.ist.fenixedu.tutorship.domain.PrescriptionEnum;

class PrescriptionRuleMomentOne extends PrescriptionRuleGenericMoment {

    public PrescriptionRuleMomentOne() {
        super();
    }

    @Override
    public BigDecimal getMinimumEcts() {
        return new BigDecimal(15);
    }

    @Override
    public PrescriptionEnum getPrescriptionEnum() {
        return PrescriptionEnum.MOMENT1;
    }

    @Override
    public int getNumberOfEntriesStudentInSecretary() {
        return 1;
    }

    @Override
    public ExecutionYear getRegistrationStart(ExecutionYear executionYear) {
        return executionYear;
    }

}