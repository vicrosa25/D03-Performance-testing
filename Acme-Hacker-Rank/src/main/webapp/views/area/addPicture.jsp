<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="area/administrator/addPicture.do?areaId=${areaId}" modelAttribute="url">
		
		<%-- Hidden properties--%>
		<form:hidden path="targetId" />

		<%-- Link--%>		
		<acme:textbox code="area.picture.link" path="link"/>
		<br>
		<br>


		<%-- Buttons --%>
		<input type="submit" name="save" value="<spring:message code="area.save"/>" />
		<acme:cancel code="area.cancel" url="area/administrator/edit.do?areaId=${areaId}" />
</form:form>