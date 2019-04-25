<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="curricula/personalData/hacker/edit.do" modelAttribute="personalData">
	
	<%-- Hidden properties from personalData--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curricula" />

	<%-- fullName --%>
	<acme:textbox code="curricula.fullName" path="fullName" />
	<br>

	<%-- statement --%>
	<acme:textbox code="curricula.statement" path="statement" />
	<br>

	<%-- phoneNumber --%>
	<acme:textbox code="curricula.phoneNumber" path="phoneNumber" />
	<br>

	<%-- gitHub --%>
	<acme:textbox code="curricula.gitHub" path="gitHub" />
	<br>

	<%-- linkedIn --%>
	<acme:textbox code="curricula.linkedIn" path="linkedIn" />
	<br>

	<%-- Buttons --%>
	<input type="submit" name="save" value="<spring:message code="curricula.save"/>" />
	<acme:cancel code="curricula.cancel" url="curricula/hacker/display.do?curriculaId=${personalData.curricula.id}" />
</form:form>