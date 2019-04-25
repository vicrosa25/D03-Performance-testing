<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="curricula/hacker/create.do" modelAttribute="curricula">	
		<%-- Hidden properties--%>

		<%-- Title --%>
		<acme:textbox code="curricula.title" path="title" />
		<br>

		<%-- Buttons --%>

		<input type="submit" name="save" value="<spring:message code="problem.save"/>" />
		<acme:cancel code="problem.cancel" url="curricula/hacker/list.do" />
	<br><br>
</form:form>