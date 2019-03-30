<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="paths" id="row" requestURI="path/brotherhood/list.do" pagesize="5" class="displaytag">

	<!-- Procession -->
	<spring:message code="path.parade" var="processionHeader" />
	<display:column property="procession.title" title="${processionHeader}" sortable="false" />
	
	<spring:message code="path.moment" var="momentHeader" />
	<display:column property="procession.moment" title="${tickerHeader}" sortable="false"  format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<!-- Display -->
	<spring:message code="path.display" var="displayHeader" />
	<display:column title="${displayHeader}"><a href="path/brotherhood/display.do?pathId=${row.id}">
	<spring:message code="path.display"/></a></display:column>
	
	<!-- Display -->
	<spring:message code="path.delete" var="deleteHeader" />
	<display:column title="${deleteHeader}"><a href="path/brotherhood/delete.do?pathId=${row.id}">
	<spring:message code="path.delete"/></a></display:column>
</display:table>

<a href="path/brotherhood/create.do"><spring:message code="path.create" /></a>