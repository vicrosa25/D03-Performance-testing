<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="coach" id="row" requestURI="coach/display.do" class="displaytag">

	<!-- Title -->
	<spring:message code="coach.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<!-- Description -->
	<spring:message code="coach.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

</display:table>

<jstl:if test="${not empty coach.pictures}">
<display:table name="coach.pictures"  id="row" >
	<spring:message code="brotherhood.picture" var="pictureNameHeader" />
	<display:column title="${pictureNameHeader}" sortable="false" >
		<img src="${row.link}" width="50%" height="200"/>
	</display:column>
			
	<display:caption><spring:message code="brotherhood.pictures"/></display:caption>
</display:table>
</jstl:if>

<security:authorize access="isAnonymous()">
	<acme:back code="member.goback"/>
</security:authorize>

<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:cancel code="member.goback" url="/brotherhood/list.do" />
</security:authorize>