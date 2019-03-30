<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="area" id="row" requestURI="chapter/area/display.do" class="displaytag">	
	
	<!-- Name -->
	<spring:message code="area.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />
	
</display:table>
<br>

<display:table name="brotherhoods" id="row" requestURI="chapter/area/display.do" class="displaytag">	
	
	<!-- Title -->
	<spring:message code="area.brotherhood" var="broHeader" />
	<display:column property="title" title="${broHeader}" />
	
</display:table>
<br>

<jstl:if test="${not empty area.pictures}">
<display:table name="pictures" id="row" requestURI="chapter/area/display.do" class="displaytag">	
	
	<!-- Picture -->
	<spring:message code="area.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}" >
		<img src="${row.link}" width="50%" height="200"/>
	</display:column>
	
</display:table>
</jstl:if>



