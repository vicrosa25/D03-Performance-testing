<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<style type="text/css">
.tableColumnSubmitted {
	background-color: grey;
	color: Black;
}

.tableColumnAccepted {
	background-color: LimeGreen;
	color: Black;
}

.tableColumnRejected {
	background-color: FireBrick;
	color: White;
}

.tableColumnBlack {
	background-color: white;
	color: black;
}
</style>


<display:table name="processions" id="row" requestURI="${uri}" pagesize="5" class="displaytag">

	<!-- Row color -->
 	<jstl:if test="${not empty bro}">
	<jstl:choose>
		<jstl:when test="${row.status == 'SUBMITTED'}">
			<jstl:set var="css" value="tableColumnSubmitted" />
			<spring:message code="procession.submitted" var="statusValue" />
		</jstl:when>

		<jstl:when test="${row.status == 'APPROVED'}">
			<jstl:set var="css" value="tableColumnAccepted" />
			<spring:message code="procession.approved" var="statusValue" />
		</jstl:when>

		<jstl:when test="${row.status == 'REJECTED'}">
			<jstl:set var="css" value="tableColumnRejected" />
			<spring:message code="procession.rejected" var="statusValue" />
		</jstl:when>

		<jstl:otherwise>
			<jstl:set var="css" value="tableColumnBlack" />
		</jstl:otherwise>
	</jstl:choose>
	</jstl:if>
	<jstl:if test="${row.draftMode}">
		<jstl:set var="css" value="tableColumnBlack" />
	</jstl:if>
	
	

	<!-- title -->
	<spring:message code="procession.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" class="${css}"/>

	<!-- ticker -->
	<spring:message code="procession.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" class="${css}"/>

	<!-- description -->
	<spring:message code="procession.description" var="descriptionHeader" />
	<display:column property="description" title="${ descriptionHeader }" class="${css}"/>

	<!-- moment -->
	<spring:message code="procession.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="{0,date,dd/MM/yyyy}" class="${css}"/>

	<!-- brotherhood -->
	<spring:message code="procession.brotherhood" var="brotherhoodHeader" />
	<display:column property="brotherhood.title" title="${brotherhoodHeader}" class="${css}"/>
		
	<!-- Display -->
	<spring:message code="procession.display" var="displayHeader" />
	<display:column title="${displayHeader}" class="${css}">
		<a href="procession/display.do?processionId=${row.id}"> <spring:message code="procession.display" /></a>
	</display:column>
	
	<!-- draftMode -->
<%-- 	<spring:message code="procession.draftMode" var="draftModeHeader" /> --%>
<%-- 	<display:column property="draftMode" title="${draftModeHeader}" /> --%>

	<security:authorize access="hasRole('BROTHERHOOD')">
	<jstl:if test="${not empty bro}">
		<spring:message code="procession.status" var="statusHeader" />
		<display:column title="${statusHeader}" class="${css}">
			<jstl:if test="${not row.draftMode}">${statusValue}</jstl:if>
			<jstl:if test="${row.draftMode}"><spring:message code="procession.draftMode"/></jstl:if>
		</display:column>
			
		<!-- Hacer copia -->
		<display:column title="${copyHeader}" class="${css}">
			<a href="procession/brotherhood/copy.do?processionId=${row.id}"> <spring:message code="procession.copy" /></a>
		</display:column>
		
		<!-- Borrar -->
		<display:column title="${deleteHeader}" class="${css}">
			<a href="procession/brotherhood/delete.do?processionId=${row.id}"> <spring:message code="procession.delete" /></a>
		</display:column>
		
		<!-- Acciones cuando esta en modo borrador -->
		
			<spring:message code="procession.delete" var="deleteHeader" />
			<spring:message code="path.manage" var="pathHeader" />
			<spring:message code="procession.edit" var="editHeader" />
		
		<!-- Editar -->	
		<display:column title="${editHeader}" class="${css}">
				<jstl:if test="${row.draftMode}">
					<a href="procession/brotherhood/edit.do?processionId=${row.id}">
					<spring:message code="procession.edit" /></a>
				</jstl:if>
		</display:column>
		
		<!-- Gestionar ruta -->
		<display:column title="${pathHeader}" class="${css}">
			<jstl:if test="${row.draftMode}">
				<a href="path/brotherhood/display.do?processionId=${row.id}">
				<spring:message code="path.manage" /></a>
			</jstl:if>
		</display:column>
		
	</jstl:if>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">
<jstl:if test="${not empty bro}">
	<a href=procession/brotherhood/create.do><spring:message code="procession.create" /></a>
</jstl:if>
</security:authorize>

<security:authorize access="isAnonymous()">
	<acme:back code="member.goback"/>
</security:authorize>