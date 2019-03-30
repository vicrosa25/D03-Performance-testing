<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="coach/brotherhood/addPicture.do?coachId=${coachId}" modelAttribute="url">
		<%-- Hidden properties--%>
		<form:hidden path="targetId" />

		<%-- Link --%>
		<acme:textbox code="coach.picture.link" path="link" />
		<br>

		<%-- Buttons --%>

		<input type="submit" name="save" value="<spring:message code="coach.save"/>" />
		
		<input type="button" name="cancel"
			value="<spring:message code="coach.cancel" />"
			onClick="javascript: window.location.replace('coach/brotherhood/list.do')" />
	<br><br>
</form:form>