<%--
 * action-1.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="administrator/config/aliveConfig/edit.do" modelAttribute="configurations">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="cacheTime" />
	<form:hidden path="finderMaxResult" />
	<form:hidden path="SpamWords" />


	<%-- Title --%>
	<form:label path="title">
		<spring:message code="config.title" />
	</form:label>
	<form:input path="title" />
	<form:errors class="error" path="title" />
	<br><br>
	
	<%-- SpanishMessage --%>
	<form:label path="spanishMessage">
		<spring:message code="config.spanishMessage" />
	</form:label>
	<form:input path="spanishMessage" />
	<form:errors class="error" path="spanishMessage" />
	<br><br>
	
	<%-- EnglishMessage --%>
	<form:label path="englishMessage">
		<spring:message code="config.englishMessage" />
	</form:label>
	<form:input path="englishMessage" />
	<form:errors class="error" path="englishMessage" />
	<br><br>
	
	<%-- Logo --%>
	<form:label path="logo">
		<spring:message code="config.logo" />
	</form:label>
	<form:input path="logo" />
	<form:errors class="error" path="logo" />
	<br><br>
	
	<%-- countryCode --%>
	<form:label path="countryCode">
		<spring:message code="config.countryCode" />
	</form:label>
	<form:input path="countryCode" />
	<form:errors class="error" path="countryCode" />
	<br><br>
		


	<acme:submit name="update" code="administrator.save" />
	<acme:cancel url="/" code="administrator.cancel"/>

</form:form>