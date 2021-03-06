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
package pt.ist.fenixedu.tutorship.ui.Action.pedagogicalCouncil.studentLowPerformance;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.student.Registration;

import pt.ist.fenixedu.tutorship.domain.PrescriptionEnum;

public abstract class AbstractPrescriptionRule {

    public boolean appliesFor(PrescriptionEnum prescriptionEnum) {
        return prescriptionEnum.equals(getPrescriptionEnum());
    }

    public abstract boolean isPrescript(Registration registration, BigDecimal ects, int numberOfEntriesStudentInSecretary,
            ExecutionYear executionYear);

    public static List<AbstractPrescriptionRule> readPrescriptionRules(PrescriptionEnum prescriptionEnum) {
        List<AbstractPrescriptionRule> abstractPrescriptionRules = new LinkedList<AbstractPrescriptionRule>();
        for (AbstractPrescriptionRule abstractPrescriptionRule : getPrescriptionRules()) {
            if (abstractPrescriptionRule.appliesFor(prescriptionEnum)) {
                abstractPrescriptionRules.add(abstractPrescriptionRule);
            }
        }
        return abstractPrescriptionRules;
    }

    private static AbstractPrescriptionRule[] getPrescriptionRules() {
        return new AbstractPrescriptionRule[] { new PrescriptionRuleMomentOne(), new PrescriptionRuleMomentTwo(),
                new PrescriptionRuleMomentThree(), new PrescriptionRuleMomentFour(), new PrescriptionRuleMomentFive(),
                new PrescriptionRuleThreeEntries(), new PrescriptionRuleFourEntries(), new PrescriptionRuleFiveEntries() };
    }

    public static List<AbstractPrescriptionRule> readProviderPrescriptionRules() {
        List<AbstractPrescriptionRule> abstractPrescriptionRules = new LinkedList<AbstractPrescriptionRule>();
        abstractPrescriptionRules.add(new PrescriptionRuleMomentOne());
        abstractPrescriptionRules.add(new PrescriptionRuleMomentTwo());
        abstractPrescriptionRules.add(new PrescriptionRuleMomentThree());
        abstractPrescriptionRules.add(new PrescriptionRuleMomentFour());
        abstractPrescriptionRules.add(new PrescriptionRuleMomentFive());
        abstractPrescriptionRules.add(new PrescriptionRuleGeneric());
        return abstractPrescriptionRules;
    }

    public abstract PrescriptionEnum getPrescriptionEnum();

    public abstract ExecutionYear getRegistrationStart(ExecutionYear executionYear);

    public abstract BigDecimal getMinimumEcts();

    protected abstract int getNumberOfEntriesStudentInSecretary();
}
