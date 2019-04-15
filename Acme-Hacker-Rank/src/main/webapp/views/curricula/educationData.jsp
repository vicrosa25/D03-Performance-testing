<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="curricula/educationData/hacker/edit.do" modelAttribute="educationData">
	
	<%-- Hidden properties from educationData--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curricula" />

	<%-- degree --%>
	<acme:textbox code="curricula.degree" path="degree" />
	<br>

	<%-- institution --%>
	<acme:textbox code="curricula.institution" path="institution" />
	<br>
	
	<%-- mark --%>
	<acme:numberbox code="curricula.mark" path="mark" />
	<br>
	
	<%-- startDate --%>
	<acme:dateinput code="curricula.startDate" path="startDate" placeholder="dd/mm/yyyy" format="{0,date,dd/MM/yyyy}" />
	<br>
	
	<%-- endDate --%>
	<acme:dateinput code="curricula.endDate" path="endDate" placeholder="dd/mm/yyyy" format="{0,date,dd/MM/yyyy}" />
	<br>

	<%-- Buttons --%>
	<input type="submit" name="save" value="<spring:message code="curricula.save"/>" />
	<acme:cancel code="curricula.cancel" url="curricula/hacker/display.do?curriculaId=${educationData.curricula.id}" />
</form:form>