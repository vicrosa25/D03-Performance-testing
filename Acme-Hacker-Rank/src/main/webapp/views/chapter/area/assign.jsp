<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="chapter/area/assign.do" modelAttribute="chapter">
	
	<%-- Hidden properties from member--%>
	<form:hidden path="id" />
	
	<!-- Select Area -->
<%-- 	<acme:select items="${areas}" itemLabel="name" code="chapter.area" path="area"/> --%>
<!-- 	<br> -->
	<form:label path="area"><spring:message code="chapter.link.area" /></form:label>
	<form:select id="areaDropdown" path="area">
		<form:option value="">--</form:option>
		<form:options items="${areas}" itemLabel="name" itemValue="id" />
	</form:select>
	<form:errors class="error" path="area" />
	<br>
	<br>
	
	<%-- Buttons --%>
	<input type="submit" name="save" value="<spring:message code="chapter.save"/>" />
	<acme:cancel code="chapter.cancel" url="/" />
</form:form>



