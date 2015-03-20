<%--

    Copyright © 2013 Instituto Superior Técnico

    This file is part of FenixEdu IST QUC.

    FenixEdu IST QUC is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu IST QUC is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu IST QUC.  If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<html:xhtml />

<h2>QUC - Garantia da Qualidade das UC</h2>

<br/>
<h4>Lista de Docentes com comentários e/ou questões obrigatórias por responder:</h4>
<logic:present name="teacherInquiryOID">	
	<p><html:link action="qucTeachersStatus.do?method=dowloadReport" paramId="teacherInquiryOID" paramName="teacherInquiryOID">Ver ficheiro</html:link></p>
</logic:present>

<logic:notPresent name="teacherInquiryOID">
	<p>O inquérito ao Docente encontra-se fechado.</p>
</logic:notPresent>