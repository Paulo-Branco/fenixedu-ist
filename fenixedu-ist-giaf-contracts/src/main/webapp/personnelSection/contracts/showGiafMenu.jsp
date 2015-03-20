<%--

    Copyright © 2013 Instituto Superior Técnico

    This file is part of FenixEdu IST GIAF Contracts.

    FenixEdu IST GIAF Contracts is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu IST GIAF Contracts is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu IST GIAF Contracts.  If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<h2><bean:message key="title.giaf.interface" bundle="APPLICATION_RESOURCES"/></h2>

<ul>

	<li><html:link action="/giafParametrization.do?method=showContractSituations">Situações Profissionais</html:link></li>
	<li><html:link action="/giafParametrization.do?method=showProfessionalCategories">Categorias Profissionais</html:link></li>
	<li><html:link action="/giafParametrization.do?method=showGrantOwnerEquivalences">Equiparações a Bolseiro</html:link></li>
	<li><html:link action="/giafParametrization.do?method=showServiceExemptions">Dispensas de Serviço</html:link></li>
	<li><html:link action="/giafParametrization.do?method=showAbsences">Faltas</html:link></li>

</ul>