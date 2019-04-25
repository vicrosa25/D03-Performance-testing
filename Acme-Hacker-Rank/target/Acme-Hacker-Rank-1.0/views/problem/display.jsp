<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="problem" id="row" requestURI="problem/display.do" class="displaytag">

	<!-- Title -->
	<spring:message code="problem.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />
	
	<!-- statement -->
	<spring:message code="problem.statement" var="statementHeader" />
	<display:column property="statement" title="${statementHeader}" sortable="false" />
	
	<!-- hint -->
	<spring:message code="problem.hint" var="hintHeader" />
	<display:column property="hint" title="${hintHeader}" sortable="false" format="{0,date,dd/MM/yyyy}" />
	
	<!-- Company -->
	<spring:message code="problem.company" var="companyHeader" />
	<display:column title="${ companyHeader }">
		<a href="company/display.do?companyId=${row.company.id}">${row.company.commercialName}</a>
	</display:column>
	
	<!-- Attachments -->
	<spring:message code="problem.attachments" var="attachmentsHeader" />
	<display:column title="${attachmentsHeader}" sortable="false" >
		<jstl:forEach var="text" items="${row.attachments}" varStatus="loop">
				${text.link}${!loop.last ? ',' : ''}&nbsp
		</jstl:forEach>
	</display:column>
	
</display:table>
<br>
<acme:back code="hacker.goback"/>