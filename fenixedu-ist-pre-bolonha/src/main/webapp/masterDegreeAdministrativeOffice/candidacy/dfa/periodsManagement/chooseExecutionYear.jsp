<%--

    Copyright © 2013 Instituto Superior Técnico

    This file is part of FenixEdu IST Pre Bolonha.

    FenixEdu IST Pre Bolonha is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu IST Pre Bolonha is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu IST Pre Bolonha.  If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html:xhtml />
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<h2><strong><bean:message key="label.candidacy.dfa.periodsManagement" bundle="ADMIN_OFFICE_RESOURCES" /></strong></h2>
<html:messages id="message" message="true" bundle="ADMIN_OFFICE_RESOURCES">
	<span class="error">
		<bean:write name="message" />
	</span>
	<br />
</html:messages>
<br />
<html:form action="/dfaPeriodsManagement.do?method=showExecutionDegrees">
	<table>
		<tr>
			<td><bean:message key="label.executionYear" bundle="ADMIN_OFFICE_RESOURCES" /></td>
			<td><html:select property="executionYear">
				<html:options collection="executionYears" property="externalId" labelProperty="year"  />
			</html:select></td>
		</tr>
	</table>


	<html:submit>
		<bean:message key="button.proceed" bundle="ADMIN_OFFICE_RESOURCES" />
	</html:submit>

</html:form>
