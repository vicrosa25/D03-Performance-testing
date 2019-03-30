<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>





<style type="text/css">
.tableColumnGreen {
	background-color: green;
	color: white;
}

.tableColumnOrange {
	background-color: orange;
	color: white;
}

.tableColumnGrey {
	background-color: grey;
	color: white;
}

.tableColumnBlack {
	background-color: white;
	color: black;
}
</style>



<!-- Listing Grid -->
<display:table name="requests" id="row" requestURI="request/list.do" pagesize="5" class="displaytag">

	<!-- Row color -->
	<jstl:choose>
		<jstl:when test="${row.status == 'APPROVED'}">
			<jstl:set var="css" value="tableColumnGreen" />
		</jstl:when>

		<jstl:when test="${row.status == 'REJECTED'}">
			<jstl:set var="css" value="tableColumnOrange" />
		</jstl:when>

		<jstl:otherwise>
			<jstl:set var="css" value="tableColumnGrey" />
		</jstl:otherwise>
	</jstl:choose>

	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column class="${css}">
			<jstl:if test="${row.status eq 'PENDING'}">
				<a href="request/brotherhood/edit.do?requestId=${row.id}"> <spring:message code="request.edit" />
				</a>
			</jstl:if>
		</display:column>
	
		<!-- Member -->
		<spring:message code="member.name" var="memberHeader" />
		<display:column property="member.name" title="${memberHeader}" class="${css}" />
	</security:authorize>

	<!-- Status -->
	<spring:message code="request.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}" class="${css}" />

	<!-- AssignedRow -->
	<spring:message code="request.assignedRow" var="assignedRowHeader" />
	<display:column property="assignedRow" title="${assignedRowHeader}" class="${css}" />

	<!-- AssignedColumn -->
	<spring:message code="request.assignedColumn" var="assignedColumnHeader" />
	<display:column property="assignedColumn" title="${assignedColumnHeader}" class="${css}" />

	<!-- Reason -->
	<spring:message code="request.reason" var="reasonHeader" />
	<display:column property="reason" title="${reasonHeader}" class="${css}" />

	<!-- Procession -->
	<spring:message code="request.procession" var="processionHeader" />
	<display:column property="procession.title" title="${processionHeader}" class="${css}" />

	<!-- MEMBER Delete -->
	<security:authorize access="hasRole('MEMBER')">
		<spring:message code="request.delete" var="deleteHeader" />
		<display:column title="${ deleteHeader }" class="${css}">
			<jstl:if test="${row.status eq 'PENDING'}">
				<a href="request/member/delete.do?requestId=${row.id}"><spring:message code="request.delete" /></a>
			</jstl:if>
			<jstl:if test="${row.status eq 'REJECTED' or row.status eq 'APPROVED'}">
				<spring:message code="request.not.delete" />
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('MEMBER')">
	<a href=request/member/create.do><spring:message code="request.create" /></a>
</security:authorize>