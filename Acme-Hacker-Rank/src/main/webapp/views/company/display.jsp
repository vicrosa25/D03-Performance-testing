<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="company" id="row" requestURI="company/display.do" class="displaytag">

	<!-- Name -->
	<spring:message code="company.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />
	
	<!-- Surname -->
	<spring:message code="company.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="false" />
	
	<!-- Email -->
	<spring:message code="company.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="false" />
	
	<!-- Phonenumber -->
	<spring:message code="company.phone" var="phoneNumberHeader" />
	<display:column property="phoneNumber" title="${phoneNumberHeader}" sortable="false" />

	<!-- commercial name -->
	<spring:message code="company.commercialName" var="commercialNameHeader" />
	<display:column property="commercialName" title="${commercialNameHeader}" />
	
</display:table>


<jstl:if test="${not empty company.positions}">
<display:table name="company.positions" id="row" requestURI="company/display.do" class="displaytag">
	<jstl:if test="${row.finalMode}">
	
	<!-- Title -->
	<spring:message code="position.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />
	
	<!-- description -->
	<spring:message code="position.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	</jstl:if>
	<display:caption><spring:message code="company.positions"/></display:caption>
</display:table>
<br>
</jstl:if>
<br>
<acme:back code="hacker.goback"/>