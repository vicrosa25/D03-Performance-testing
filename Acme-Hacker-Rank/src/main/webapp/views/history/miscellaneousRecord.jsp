<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="history/miscellaneousRecord/brotherhood/edit.do" modelAttribute="miscellaneousRecord">
	
	<%-- Hidden properties from miscellaneousRecord--%>
	<form:hidden path="id" />
	<form:hidden path="version" />

	<%-- Title --%>
	<acme:textbox code="history.title" path="title" />
	<br>

	<%-- Description --%>
	<acme:textbox code="history.description" path="description" />
	<br>

	<%-- Buttons --%>
	<input type="submit" name="save" value="<spring:message code="history.save"/>" />
	<acme:cancel code="history.cancel" url="history/brotherhood/display.do" />
</form:form>