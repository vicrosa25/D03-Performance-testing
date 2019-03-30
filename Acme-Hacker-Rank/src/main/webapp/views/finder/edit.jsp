<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="finder/member/edit.do" modelAttribute="finder">
	
	<%-- Hidden properties from finder--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="lastUpdate" />
	
	<%-- keyword--%>
	<acme:textbox code="finder.keyword" path="keyword" />
	<br>
	
	<%-- max date --%>
	<acme:dateinput code="finder.maxDate" path="maxDate" placeholder="dd/mm/yyyy" format="{0,date,dd/MM/yyyy HH:mm}" />
	<br>
	
	<%-- min date --%>
	<acme:dateinput code="finder.minDate" path="minDate" placeholder="dd/mm/yyyy" format="{0,date,dd/MM/yyyy HH:mm}" />
	<br>
	
	<%-- Area --%>
	<acme:select code="finder.area" path="area" items="${areas}" itemLabel="name" />
	<br>
	
	
	<%-- Buttons --%>
	<input type="submit" name="save" 
		value="<spring:message code="finder.save"/>"/>
	
	<acme:cancel code="finder.clear" url="/finder/member/clear.do" />
	<acme:cancel code="finder.cancel" url="/" />
		
</form:form>