<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="position/company/edit.do" modelAttribute="position">
	
	<%-- Hidden properties--%>
	<form:hidden path="id" />
	
	<%-- Title--%>
	<acme:textbox code="position.title" path="title" />
	<br>
	
	<%-- description--%>
	<acme:textbox code="position.description" path="description" />
	<br>
	
	<%-- profile--%>
	<acme:textbox code="position.profile" path="profile" />
	<br>
	
	<%-- skills--%>
	<acme:textbox code="position.skills" path="skills" />
	<br>
	
	<%-- technologies--%>
	<acme:textbox code="position.technologies" path="technologies" />
	<br>
	
	<%-- salary--%>
	<acme:numberbox code="position.salary" path="salary" />
	<br>
	
	<%-- deadline--%>
	<acme:dateinput code="position.deadline" format="{0,date,dd/MM/yyyy}" path="deadline" placeholder="dd/mm/yyyy"/>
	<br>
	
	
	<!-- Select Problems -->
	<acme:select2 items="${ problems }" itemLabel="title" code="position.problems" path="problems"/>
	<br>
	
	

	<%-- finalMode --%>
	<form:label path="finalMode"><spring:message code="position.finalMode" /></form:label>
	<form:select id="modeDropdown" path="finalMode">
		<form:option value="">--</form:option>
		<form:option value="0"><spring:message code="position.false" /></form:option>
		<form:option value="1"><spring:message code="position.true" /></form:option>
	</form:select>
	<form:errors class="error" path="finalMode" />
	<br>
	<br>
	
	<input type="submit" name="save" value="<spring:message code="position.save"/>" />
	<acme:cancel code="company.cancel" url="/position/company/list.do" />
</form:form>
