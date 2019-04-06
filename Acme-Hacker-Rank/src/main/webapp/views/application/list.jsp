<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing Grid -->
<display:table name="apps" id="row" requestURI="${ requestUri }" pagesize="5" class="displaytag">

	<!-- Edit -->
	<security:authorize access="hasRole('HACKER')">
	<spring:message code="application.edit" var="editHeader" />
	<display:column title="${editHeader}">
		<a href="application/hacker/edit.do?appId=${row.id}"><spring:message code="application.edit" /></a>
	</display:column>
	</security:authorize>

	
	
	<!-- FROM POSITION -->
	
	<!-- Posistion title -->
	<spring:message code="position.title" var="titleHeader" />
	<display:column property="position.title" title="${titleHeader}" />

	<!-- Position ticker -->
	<spring:message code="position.ticker" var="tickerHeader" />
	<display:column property="position.ticker" title="${tickerHeader}" />

	<!-- Position description -->
	<spring:message code="position.description" var="descriptionHeader" />
	<display:column property="position.description" title="${descriptionHeader}" />
	
	
	<!-- FROM APPLICATION -->

	<!-- Creation moment -->
	<spring:message code="application.creationMoment" var="creationMomentHeader" />
	<display:column property="creationMoment" title="${creationMomentHeader}" format="{0,date,dd/MM/yyyy}" />
	
	
	<!-- Status -->
	<spring:message code="application.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}" sortable="true"/>
	
	
	
	<!-- Display -->
	<spring:message code="application.display" var="displayHeader" />
	<display:column title="${displayHeader}">
		<a href="application/hacker/display.do?appId=${row.id}"> <spring:message code="application.display" /></a>
	</display:column>

</display:table>


<!-- Create Application -->
<security:authorize access="hasRole('HACKER')">
<a href=application/hacker/create.do><spring:message code="application.create" /></a>
</security:authorize>


