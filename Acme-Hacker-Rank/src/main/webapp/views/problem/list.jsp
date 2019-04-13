<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<!-- Listing Grid -->
<display:table name="problems" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">


	<!-- Title -->
	<spring:message code="problem.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<!-- statement -->
	<spring:message code="problem.statement" var="statementHeader" />
	<display:column property="statement" title="${statementHeader}" />
	
	<!-- hint -->
	<spring:message code="problem.hint" var="hintHeader" />
	<display:column property="hint" title="${hintHeader}" />
		
	<!-- Company -->
	<spring:message code="problem.company" var="companyHeader" />
	<display:column title="${ companyHeader }">
		<a href="company/display.do?companyId=${row.company.id}">${row.company.commercialName}</a>
	</display:column>
	

	<jstl:if test="${requestURI == 'problem/company/list.do'}">
	
	<!-- status -->
	<spring:message code="problem.status" var="statusHeader" />
	<display:column title="${statusHeader}">
		<jstl:if test="${row.finalMode}">
			<spring:message code="problem.finalMode" />
		</jstl:if>
		<jstl:if test="${not row.finalMode}">
			<spring:message code="problem.draftMode" />
		</jstl:if>
	</display:column>
	
	<!-- Display -->	
	<spring:message code="problem.display" var="displayHeader" />
	<display:column title="${displayHeader}">
			<a href="problem/company/display.do?problemId=${row.id}">
			<spring:message code="problem.display" /></a>
	</display:column>
	
	<!-- Editar -->	
	<spring:message code="problem.edit" var="editHeader" />
	<display:column title="${editHeader}">
			<jstl:if test="${not row.finalMode}">
				<a href="problem/company/edit.do?problemId=${row.id}">
				<spring:message code="problem.edit" /></a>
			</jstl:if>
			<jstl:if test="${row.finalMode}">
				-
			</jstl:if>
	</display:column>
	
	
	<!-- Borrar -->	
	<spring:message code="problem.delete" var="deleteHeader" />
	<display:column title="${deleteHeader}">
			<a href="problem/company/delete.do?problemId=${row.id}">
			<spring:message code="problem.delete" /></a>
	</display:column>
	</jstl:if>
</display:table>
<!-- Create Link -->
<security:authorize access="hasRole('COMPANY')">
	<a href=problem/company/create.do><spring:message code="problem.create" /></a>
</security:authorize>
<jstl:if test="${not empty application}">
<br>
<p class="error"> <spring:message code="problem.delete.application"/></p>
</jstl:if>