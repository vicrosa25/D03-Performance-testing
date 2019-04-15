<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Listing Grid -->
<display:table name="curriculas" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<!-- title -->
	<spring:message code="curricula.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<!-- Number position data -->
	<spring:message code="curricula.positionData" var="positionDataHeader" />
	<display:column title="${positionDataHeader}" >
		${fn:length(row.positionData)}
	</display:column>
	
	<!-- Number education data -->
	<spring:message code="curricula.educationData" var="educationDataHeader" />
	<display:column title="${educationDataHeader}" >
		${fn:length(row.educationData)}
	</display:column>
	
	<!-- Number misc data -->
	<spring:message code="curricula.miscellaneousData" var="miscellaneousDataHeader" />
	<display:column title="${miscellaneousDataHeader}" >
		${fn:length(row.miscellaneousData)}
	</display:column>
	
	<!-- Display -->	
	<spring:message code="curricula.display" var="displayHeader" />
	<display:column title="${displayHeader}">
			<a href="curricula/hacker/display.do?curriculaId=${row.id}">
			<spring:message code="curricula.display" /></a>
	</display:column>
	

	<jstl:if test="${requestURI == 'curricula/hacker/list.do'}">
	
	<!-- Borrar -->	
	<spring:message code="curricula.delete" var="deleteHeader" />
	<display:column title="${deleteHeader}">
			<a href="curricula/hacker/delete.do?curriculaId=${row.id}">
			<spring:message code="curricula.delete" /></a>
	</display:column>
	</jstl:if>
	
</display:table>
<!-- Create Link -->
<jstl:if test="${requestURI == 'curricula/hacker/list.do'}">
	<a href=curricula/hacker/create.do><spring:message code="curricula.create" /></a>
</jstl:if>