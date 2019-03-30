<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="brotherhood/addPicture.do" modelAttribute="url">	
		<%-- Hidden properties--%>
		<form:hidden path="targetId" />

		<%-- Link --%>
		<acme:textbox code="brotherhood.picture.link" path="link" />
		<br>

		<%-- Buttons --%>

		<input type="submit" name="save" value="<spring:message code="brotherhood.save"/>" />
		<acme:cancel code="brotherhood.cancel" url="brotherhood/edit.do" />
	<br><br>
</form:form>