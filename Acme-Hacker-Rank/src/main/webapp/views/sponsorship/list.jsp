<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

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
	
								<!-- ACTIONS -->
	<!-- Edit -->
	<spring:message code="sponsorship.edit" var="editHeader" />
	<display:column title="${editHeader}" >
		<a href="sponsorship/sponsor/edit.do?sponsorshipId=${row.id}">
			<spring:message code="sponsorship.edit" /></a>
	</display:column>
	
	<!-- de/activate -->
	<jstl:if test="${not row.active}">
		<display:column>
			<a href="sponsorship/sponsor/activate.do?sponsorshipId=${row.id}">
				<spring:message code="sponsorship.activate" /></a>
		</display:column>
	</jstl:if>
	
	<jstl:if test="${row.active}">
		<display:column>
			<a href="sponsorship/sponsor/deactivate.do?sponsorshipId=${row.id}">
				<spring:message code="sponsorship.deactivate" /></a>
		</display:column>
	</jstl:if>
</display:table>

<a href=sponsorship/sponsor/create.do><spring:message code="sponsorship.create" /></a>