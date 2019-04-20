<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<display:table name="problem" id="row" requestURI="${ requestUri }" class="displaytag">
	
	<!-- Caption -->
	<display:caption><b><spring:message code="application.problem"/></b></display:caption>
	
	<!-- Problem title -->
	<spring:message code="problem.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<!-- Problem statement -->
	<spring:message code="problem.statement" var="statementHeader" />
	<display:column property="statement" title="${statementHeader}" />

	<!-- Problem hint -->
	<spring:message code="problem.hint" var="hintHeader" />
	<display:column property="hint" title="${hintHeader}" />
	
</display:table>
<br>
<br>



<!-- Answer Caption -->
<spring:message code="application.answer" var="answerCaption"/>
<b><jstl:out value="${ answerCaption }" /></b>
<br>
<br>

<form:form action="application/hacker/update.do" modelAttribute="application">

	<%-- Hidden properties --%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="hacker" />
	<form:hidden path="status" />
	<form:hidden path="position" />
	<form:hidden path="problem" />
	<form:hidden path="creationMoment" />
	<form:hidden path="curricula" />

	<%-- Answer Text --%>
	<acme:textarea code="application.answer.text" path="answer.text"/>
	<br>

	<%-- Answer Link --%>
	<acme:textbox code="application.answer.link" path="answer.link"/>
	<br>
	<br>

	
	<%-- Buttons --%>
	<acme:submit name="update" code="application.update"/>	
	<acme:cancel code="company.cancel" url="application/display.do?appId=${ application.id }" />

</form:form>