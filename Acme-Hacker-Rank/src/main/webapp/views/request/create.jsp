<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" 	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" 	uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" 	tagdir="/WEB-INF/tags" %>

<form:form action="request/member/create.do" modelAttribute="request">

	<%-- Hidden properties --%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="status" />
	<form:hidden path="reason" />
	<form:hidden path="assignedRow" />
	<form:hidden path="assignedColumn" />
	<form:hidden path="member" />
	
		
	<!-- Select Procession -->
	<acme:select items="${ processions }" itemLabel="title" code="request.procession" path="procession"/>
	<br>
	
	<jstl:if test="${empty processions}">
		<b><spring:message code="procession.empty.list"/></b>
		<br>
	</jstl:if>
	<br>
	<%-- Buttons --%>
	<input type="submit" name="save" value="<spring:message code="request.save"/>"/>
	<acme:cancel code="request.cancel" url="request/list.do" />

</form:form>









