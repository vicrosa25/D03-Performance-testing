<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<style type="text/css">
.tableColumnSubmitted {
	background-color: grey;
	color: Black;
}

.tableColumnAccepted {
	background-color: LimeGreen;
	color: Black;
}

.tableColumnRejected {
	background-color: FireBrick;
	color: White;
}

.tableColumnBlack {
	background-color: white;
	color: black;
}
</style>

<display:table name="parades" id="row" requestURI="${uri}" pagesize="5" class="displaytag">

	<!-- Row color -->
	<jstl:choose>
		<jstl:when test="${row.status == 'SUBMITTED'}">
			<jstl:set var="css" value="tableColumnSubmitted" />
		</jstl:when>

		<jstl:when test="${row.status == 'APPROVED'}">
			<jstl:set var="css" value="tableColumnAccepted" />
		</jstl:when>

		<jstl:when test="${row.status == 'REJECTED'}">
			<jstl:set var="css" value="tableColumnRejected" />
		</jstl:when>

		<jstl:otherwise>
			<jstl:set var="css" value="tableColumnBlack" />
		</jstl:otherwise>
	</jstl:choose>

	<!-- title -->
	<spring:message code="procession.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"  class="${css}"/>

	<!-- ticker -->
	<spring:message code="procession.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"  class="${css}"/>

	<!-- description -->
	<spring:message code="procession.description" var="descriptionHeader" />
	<display:column property="description" title="${ descriptionHeader }"  class="${css}"/>

	<!-- moment -->
	<spring:message code="procession.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="{0,date,dd/MM/yyyy}"  class="${css}"/>

	<!-- brotherhood -->
	<spring:message code="procession.brotherhood" var="brotherhoodHeader" />
	<display:column property="brotherhood.title" title="${brotherhoodHeader}"  class="${css}"/>
	
	<!-- status -->
	<spring:message code="procession.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}"  class="${css}"/>	
	
	<!-- manage status -->
	<spring:message code="chapter.parade.accept" var="acceptHeader"/>
	<display:column  title="${ acceptHeader }" class="${css}">
		<jstl:if test="${row.status == 'SUBMITTED'}">
			<a href="chapter/parade/aprove.do?processionId=${row.id}"><spring:message code="chapter.parade.accept" /></a>
		</jstl:if>
	</display:column>
	<spring:message code="chapter.parade.reject" var="rejectHeader" />
	<display:column  title="${ rejectHeader }" class="${css}">
		<jstl:if test="${row.status == 'SUBMITTED'}">
			<a href="chapter/parade/reject/reasson.do?processionId=${row.id}"><spring:message code="chapter.parade.reject" /></a>
		</jstl:if>
	</display:column>
	
	<!-- reasson -->	
	<spring:message code="parade.reject.reasson" var="reassonHeader" />
	<display:column title="${reassonHeader}"  class="${css}">
		<jstl:if test="${ row.status == 'REJECTED' }">
			<jstl:out value="${ row.reasson }" />
		</jstl:if>
	</display:column>
	
	
</display:table>
