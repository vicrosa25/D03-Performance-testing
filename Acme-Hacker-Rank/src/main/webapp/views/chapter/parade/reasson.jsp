<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="chapter/parade/reject/reasson.do" modelAttribute="procession">

	<%-- Hidden properties --%>
	<form:hidden path="id" />
	
	<%-- Reason --%>
	<acme:textarea code="chapter.parade.reasson" path="reasson"/>
	<br>
	<br>
	
	<%-- Buttons --%>
	<input type="submit" name="save" value="<spring:message code="chapter.save"/>" />
	<acme:cancel code="chapter.cancel" url="/chapter/parade/list.do" />
</form:form>








