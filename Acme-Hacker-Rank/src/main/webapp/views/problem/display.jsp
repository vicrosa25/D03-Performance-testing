<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="problem" id="row" requestURI="problem/display.do" class="displaytag">

	<!-- ticker -->
	<spring:message code="problem.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" sortable="false" />
	
	<!-- Title -->
	<spring:message code="problem.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />
	
	<!-- description -->
	<spring:message code="problem.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<!-- deadline -->
	<spring:message code="problem.deadline" var="deadlineHeader" />
	<display:column property="deadline" title="${deadlineHeader}" sortable="false" format="{0,date,dd/MM/yyyy}" />
	
	<!-- profile -->
	<spring:message code="problem.profile" var="profileHeader" />
	<display:column property="profile" title="${profileHeader}" sortable="false"/>

	<!-- skills -->
	<spring:message code="problem.skills" var="skillsHeader" />
	<display:column property="skills" title="${skillsHeader}" sortable="false"/>
	
	<!-- technologies -->
	<spring:message code="problem.technologies" var="technologiesHeader" />
	<display:column property="technologies" title="${technologiesHeader}" sortable="false"/>

	<!-- salary -->
	<spring:message code="problem.salary" var="salaryHeader" />
	<display:column property="salary" title="${salaryHeader}" sortable="false"/>

	<!-- Company -->
	<spring:message code="problem.company" var="companyHeader" />
	<display:column title="${ companyHeader }">
		<a href="company/display.do?companyId=${row.company.id}">${row.company.commercialName}</a>
	</display:column>
	
</display:table>


<jstl:if test="${not empty problem.attachments}">
<display:table name="problem.attachments" id="row" requestURI="problem/display.do" class="displaytag">
	
	<spring:message code="problem.attachment" var="attachmentNameHeader" />
	<display:column title="${attachmentNameHeader}" property="link" sortable="false" />
	
	<display:caption><spring:message code="problem.problems"/></display:caption>
</display:table>
<br>
</jstl:if>
<br>
<acme:back code="hacker.goback"/>