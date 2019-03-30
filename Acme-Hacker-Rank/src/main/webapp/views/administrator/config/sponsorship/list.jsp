<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing Grid -->

<b><spring:message code="config.spamWords"/></b>
<display:table name="sponsorships" id="row" requestURI="sponsorship/sponsor/list.do" pagesize="5" class="displaytag">
	<!-- banner -->
	<spring:message code="sponsorship.banner" var="bannerHeader" />
	<display:column property="banner" title="${bannerHeader}"/>

	<!-- creditCard -->
	<spring:message code="sponsorship.targetPage" var="targetPageHeader" />
	<display:column property="targetPage" title="${targetPageHeader}" />

	<!-- creditCard -->
	<spring:message code="sponsorship.creditCard" var="creditCardHeader" />
	<display:column property="creditCard.number" title="${ creditCardHeader }" />

	<!-- Procession -->
	<spring:message code="sponsorship.procession" var="processionHeader" />
	<display:column property="procession.title" title="${processionHeader}" sortable="false" />

	<!-- Estado -->
	<spring:message code="procession.status" var="statusHeader" />
	<display:column title="${statusHeader}" sortable="false">
	<jstl:if test="${not row.active}">
		<spring:message code="sponsorship.inactive"/>
	</jstl:if>
	<jstl:if test="${row.active}">
		<spring:message code="sponsorship.active"/>
	</jstl:if>
	</display:column>

</display:table>

<!-- Action link -->
<a href=administrator/computeSponsorship.do><spring:message code="administrator.compute.sponsorship" /></a>
<br><br>



