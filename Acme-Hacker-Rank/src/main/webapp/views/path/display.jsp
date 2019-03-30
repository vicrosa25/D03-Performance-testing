<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="path.segments" id="row" requestURI="path/brotherhood/display.do" class="displaytag">

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
	
	<spring:message code="segment.update" var="updateHeader" />
	<display:column title="${ updateHeader }">
			<a href="segment/brotherhood/edit.do?segmentId=${row.id}"><spring:message code="segment.update"/></a>
	</display:column>
	
	<spring:message code="segment.delete" var="deleteHeader" />
	<display:column title="${deleteHeader}">
		<a href="segment/brotherhood/delete.do?segmentId=${row.id}"><spring:message code="segment.delete"/></a>
	</display:column>
</display:table>

<%-- Buttons --%>
<acme:cancel code="member.goback" url="procession/list.do" />
<a href="segment/brotherhood/create.do?pathId=${path.id}"><spring:message code="segment.create" /></a>