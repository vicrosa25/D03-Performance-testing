<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="procession" id="row" requestURI="procession/display.do" class="displaytag">>
	<!-- Ticker -->
	<spring:message code="procession.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" sortable="false" />
	
	<!-- Title -->
	<spring:message code="procession.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />
	
	<!-- Title -->
	<spring:message code="procession.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
	<!-- Moment -->
	<spring:message code="procession.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />

	<!-- brotherhood -->
	<spring:message code="procession.brotherhood" var="brotherhoodHeader" />
	<display:column property="brotherhood.title" title="${brotherhoodHeader}" />
	
</display:table>

<jstl:if test="${not empty sponsorship}"><div>
	<a href="${sponsorship.targetPage}"><img src="${sponsorship.banner}" alt="${sponsorship.targetPage}"
		width="500" height="120" /></a>
</div></jstl:if>


<jstl:if test="${not empty procession.path}">
<display:table name="procession.path.segments" id="row" requestURI="procession/display.do" class="displaytag">

	<spring:message code="segment.origin.coordinates" var="originHeader" />
	<display:column title="${originHeader}" sortable="false" >
		${row.originLatitude} : ${row.originLongitude}
	</display:column>
	
	<spring:message code="segment.originTime" var="originTimeHeader" />
	<display:column property="originTime" title="${originTimeHeader}" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	
	<spring:message code="segment.destination.coordinates" var="destinationHeader" />
	<display:column title="${destinationHeader}" sortable="false" >
		${row.destinationLatitude} : ${row.destinationLongitude}
	</display:column>
	
	<spring:message code="segment.destinationTime" var="destinationTimeHeader" />
	<display:column property="destinationTime" title="${destinationTimeHeader}" sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<display:caption><spring:message code="path.segments"/></display:caption>
</display:table>
<br>
</jstl:if>
<br>
<acme:back code="member.goback"/>






