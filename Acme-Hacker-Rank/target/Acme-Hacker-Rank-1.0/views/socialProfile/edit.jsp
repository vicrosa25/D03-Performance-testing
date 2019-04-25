<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="socialProfile/edit.do" modelAttribute="socialProfile">

	<%-- Hidden properties--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actor" />
	
	
	
	<%-- socialProfile --%>

	<%-- Nick --%>
	<acme:textbox code="profile.nick" path="nick" />
	<br>

	<%-- Social Network --%>
	<acme:textbox code="profile.socialNetwork" path="socialNetwork" />
	<br>

	<%-- link --%>
	<acme:textbox code="profile.link" path="link" />
	<br>


	<%-- Buttons --%>

	<input type="submit" name="save" value="<spring:message code="profile.save"/>" />

<%-- 	<jstl:if test="${socialProfile.id != 0 }"> --%>
<%-- 		<input type="submit" name="delete" value="<spring:message code="tutorial.delete"/>" /> --%>
<%-- 	</jstl:if> --%>

	<input type="button" name="cancel"
		value="<spring:message code="profile.cancel" />"
		onClick="javascript: window.location.replace('socialProfile/list.do')" />
	<br>
	<br>

</form:form>

