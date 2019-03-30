<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="area/administrator/create.do" modelAttribute="area">

	<%-- Hidden properties --%>
	<form:hidden path="id" />
	<form:hidden path="version" />


    <%-- title--%>
    <acme:textbox code="area.name" path="name" />
    <br>

	<%-- Buttons --%>
	<input type="submit" name="save" value="<spring:message code="area.save"/>"/>
	<acme:cancel code="area.cancel" url="area/administrator/list.do" />
</form:form>