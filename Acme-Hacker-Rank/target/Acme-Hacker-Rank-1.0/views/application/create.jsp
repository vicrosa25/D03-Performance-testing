<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="application/hacker/create.do" modelAttribute="application">
	
	<%-- Hidden properties from application --%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="hacker" />
	<form:hidden path="status" />
	<form:hidden path="creationMoment" />
	
	<%-- Position --%>
	<jstl:if test="${not empty positions }">
		<acme:select items="${ positions }" itemLabel="title" code="application.positions" path="position"/>
	</jstl:if>
	<br>
	<br>
	
	<!-- Select Curricula -->
	<acme:select items="${ curriculas }" itemLabel="title" code="curricula" path="curricula"/>
	<br>
	
	<%-- Buttons --%>
	<jstl:if test="${not empty positions }">
		<acme:submit name="save" code="application.save"/>
		<acme:cancel code="application.cancel" url="application/hacker/list.do" />
	</jstl:if>
	
	<jstl:if test="${ empty positions }">
		<acme:back code="application.goBack"/>
	</jstl:if>
	

</form:form>