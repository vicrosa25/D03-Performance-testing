<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="position" id="row" requestURI="position/display.do" class="displaytag">

	<!-- ticker -->
	<spring:message code="position.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" sortable="false" />
	
	<!-- Title -->
	<spring:message code="position.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />
	
	<!-- description -->
	<spring:message code="position.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<!-- deadline -->
	<spring:message code="position.deadline" var="deadlineHeader" />
	<display:column property="deadline" title="${deadlineHeader}" sortable="false" format="{0,date,dd/MM/yyyy}" />
	
	<!-- profile -->
	<spring:message code="position.profile" var="profileHeader" />
	<display:column property="profile" title="${profileHeader}" sortable="false"/>

	<!-- skills -->
	<spring:message code="position.skills" var="skillsHeader" />
	<display:column property="skills" title="${skillsHeader}" sortable="false"/>
	
	<!-- technologies -->
	<spring:message code="position.technologies" var="technologiesHeader" />
	<display:column property="technologies" title="${technologiesHeader}" sortable="false"/>

	<!-- salary -->
	<spring:message code="position.salary" var="salaryHeader" />
	<display:column property="salary" title="${salaryHeader}" sortable="false"/>

	<!-- Company -->
	<spring:message code="position.company" var="companyHeader" />
	<display:column title="${ companyHeader }">
		<a href="company/display.do?companyId=${row.company.id}">${row.company.commercialName}</a>
	</display:column>
	
</display:table>


<jstl:if test="${not empty position.problems}">
<display:table name="position.problems" id="row" requestURI="position/display.do" class="displaytag">
	<jstl:if test="${row.finalMode}">
	
	<!-- Title -->
	<spring:message code="problem.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />
	
	<!-- description -->
	<spring:message code="problem.statement" var="statementHeader" />
	<display:column property="statement" title="${statementHeader}" sortable="false" />
	
	</jstl:if>
	<display:caption><spring:message code="position.problems"/></display:caption>
</display:table>
<br>
</jstl:if>
<br>
<acme:back code="hacker.goback"/>