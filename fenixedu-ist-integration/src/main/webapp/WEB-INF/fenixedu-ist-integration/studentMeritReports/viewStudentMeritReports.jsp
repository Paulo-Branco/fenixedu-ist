<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="page-header">
    <div class="pull-right btn-group">
        <button class="btn btn-default" onClick="location.replace(location.href)"><i class="glyphicon glyphicon-refresh"></i></button>
    </div>
    <h2>
        <spring:message code="student.merit.reports.title"/>
    </h2>
</div>

<b><spring:message code="student.merit.reports.select.execution.year"/></b>
<form:form method="POST" modelAttribute="studentMeritReportsBean" cssclass="tstyle5 thlight thright">
    <div class="form-group">
        <label for="executionYear" class="col-md-1 control-label"><spring:message code="student.merit.reports.execution.year"/></label>
        <div class="col-md-11">
            <form:select path="executionYearId" cssClass="form-control" id="executionYear">
                <form:options items="${availableExecutionYears}" itemLabel="qualifiedName" itemValue="externalId"/>
            </form:select>
        </div>
    </div>
    <input type="submit" value=<spring:message code="student.merit.reports.generate"/> />
</form:form>
<br>

<c:if test="${newGenerationRequest}">
    <div class="success0 mtop0" style="width:600px;">
        <p class="mvert05">
            <spring:message code="student.merit.reports.created.success.message"/>
        </p>
    </div>
</c:if>

<c:if test="${!availableReports.isEmpty()}">
    <b><spring:message code="student.merit.reports.generate"/></b><p>
    <table class="table table-bordered">
        <th><spring:message code="student.merit.reports.execution.year"/></th>
        <th><spring:message code="student.merit.reports.request.date"/></th>
        <th><spring:message code="student.merit.reports.requester"/></th>
        <th><spring:message code="student.merit.reports.state"/></th>
        <th><spring:message code="student.merit.reports.conclusion.date"/></th>
        <th><spring:message code="student.merit.reports.actions"/></th>
        <c:forEach var="reportItem" items="${availableReports}">
            <tr>
                <td> ${reportItem.executionYear.qualifiedName} </td>
                <td> <fmt:formatDate value="${reportItem.requestDate.toDate()}" pattern="dd/MM/yyyy HH:mm:ss"/> </td>
                <td> ${reportItem.person.name} </td>
                <td>
                    <c:choose>
                        <c:when test="${reportItem.done}">
                            <spring:message code="student.merit.reports.state.processed"/>
                        </c:when>
                        <c:when test="${reportItem.isNotDoneAndNotCancelled}">
                            <spring:message code="student.merit.reports.state.awaiting.processing"/>
                        </c:when>
                        <c:when test="${reportItem.isNotDoneAndCancelled}">
                            <spring:message code="student.merit.reports.state.canceled"/>
                        </c:when>
                        <c:when test="${reportItem.failedCounter > 0}">
                            <spring:message code="student.merit.reports.state.failed"/>
                        </c:when>
                        <c:otherwise>
                            <spring:message code="student.merit.reports.state.unknown"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                    <td> <c:if test="${reportItem.done}">
                            <fmt:formatDate value="${reportItem.jobEndTime.toDate()}" pattern="dd/MM/yyyy HH:mm:ss"/>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${reportItem.done}">
                            <button class="btn btn-default" onclick="window.location.href='${reportItem.downloadUrl}'">
                                <i class="glyphicon glyphicon-download-alt"></i>
                            </button>
                        </c:if>
                    </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
