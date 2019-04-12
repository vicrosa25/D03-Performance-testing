<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<!-- Listing Grid -->
<display:table name="positions" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">


	<!-- Title -->
	<spring:message code="position.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<!-- Ticker -->
	<spring:message code="position.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />
	
	<!-- Description -->
	<spring:message code="position.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	
	<!-- Deadline -->
	<spring:message code="position.deadline" var="deadlineHeader" />
	<display:column property="deadline" title="${deadlineHeader}" format="{0,date,dd/MM/yyyy}" />
	
	<!-- Profile -->
	<spring:message code="position.profile" var="profileHeader" />
	<display:column property="profile" title="${profileHeader}" />
	
	<!-- Skills -->
	<spring:message code="position.skills" var="skillsHeader" />
	<display:column property="skills" title="${skillsHeader}" />
	
	<!-- Technologies -->
	<spring:message code="position.technologies" var="technologiesHeader" />
	<display:column property="technologies" title="${technologiesHeader}" />
	
	<!-- Salary -->
	<spring:message code="position.salary" var="salaryHeader" />
	<display:column property="salary" title="${salaryHeader}" />

		
	<!-- Company -->
	<spring:message code="position.company" var="companyHeader" />
	<display:column title="${ companyHeader }">
		<a href="company/display.do?companyId=${row.company.id}">${row.company.commercialName}</a>
	</display:column>

	

	<jstl:if test="${requestURI == 'position/company/list.do'}">
		
	<!-- Display -->
	<spring:message code="position.display" var="displayHeader" />
	<display:column title="${ displayHeader }">
		<a href="position/company/display.do?positionId=${row.id}">
		<spring:message code="position.display" /></a>
	</display:column>
	
	<!-- Editar -->	
	<spring:message code="position.edit" var="editHeader" />
	<display:column title="${editHeader}">
			<jstl:if test="${not row.finalMode}">
				<a href="position/company/edit.do?positionId=${row.id}">
				<spring:message code="position.edit" /></a>
			</jstl:if>
	</display:column>
	
	
	<!-- Borrar -->	
	<spring:message code="position.delete" var="deleteHeader" />
	<display:column title="${deleteHeader}">
			<jstl:if test="${not row.finalMode}">
				<a href="position/company/delete.do?positionId=${row.id}">
				<spring:message code="position.delete" /></a>
			</jstl:if>
	</display:column>
		
	<!-- Cancelar -->
	<spring:message code="position.cancel" var="cancelHeader" />
	<display:column title="${cancelHeader}">
		<jstl:if test="${row.finalMode}">
		<jstl:if test="${not row.cancelled}">
			<a href="position/company/cancel.do?positionId=${row.id}"> <spring:message code="position.cancel" /></a>
		</jstl:if>
		<jstl:if test="${row.cancelled}">
			<spring:message code="position.cancelled"/>
		</jstl:if>
		</jstl:if>
	</display:column>
	</jstl:if>
</display:table>

<!-- Create Link -->
<security:authorize access="hasRole('COMPANY')">
	<a href=position/company/create.do><spring:message code="position.create" /></a>
</security:authorize>