<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="proclaims" id="row" requestURI="${ requestUri }" pagesize="5" class="displaytag">

	<!-- Moment -->
	<spring:message code="proclaim.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="{0,date,dd/MM/yyyy}" />
	
	<!-- text -->
	<spring:message code="proclaim.text" var="textHeader" />
	<display:column property="text" title="${textHeader}"/>
     
</display:table>

<acme:back code="chapter.back"/>


















