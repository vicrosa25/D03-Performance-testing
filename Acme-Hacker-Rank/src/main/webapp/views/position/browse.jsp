<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="position/browse.do" modelAttribute="browser">
	
	<%-- Keyword--%>
	<acme:textbox code="finder.keyword" path="keyword" />
	<br>
	
	<input type="submit" name="search" value="<spring:message code="position.search"/>" />
	<acme:cancel code="company.cancel" url="/" />
</form:form>
