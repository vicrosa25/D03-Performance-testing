<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="application" id="row" requestURI="#{ requestUri }" class="displaytag">
	
	<!-- Creation moment -->
	<spring:message code="application.creationMoment" var="creationMomentHeader" />
	<display:column property="creationMoment" title="${creationMomentHeader}" format="{0,date,dd/MM/yyyy}" />
	
	<!-- Status -->
	<spring:message code="application.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}" />

</display:table>


<display:table name="position" id="row" requestURI="${ requestUri }" class="displaytag">

	<!-- Caption -->
	<display:caption><b><spring:message code="application.position"/></b></display:caption>	
	
	<!-- Posistion title -->
	<spring:message code="position.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<!-- Position ticker -->
	<spring:message code="position.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />

	<!-- Position description -->
	<spring:message code="position.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	
	<!-- Position profile -->
	<spring:message code="position.profile" var="profileHeader" />
	<display:column property="profile" title="${descriptionHeader}" />
	
	<!-- Position skills -->
	<spring:message code="position.skills" var="skillsHeader" />
	<display:column property="skills" title="${skillsHeader}" />
	
	<!-- Position technologies -->
	<spring:message code="position.technologies" var="technologiesHeader" />
	<display:column property="technologies" title="${technologiesHeader}" />
	
	<!-- Position salary -->
	<spring:message code="position.salary" var="salaryHeader" />
	<display:column property="salary" title="${salaryHeader}" />
	
</display:table>


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


<display:table name="attachments" id="row" requestURI="${ requestUri }" class="displaytag">
	
	<!-- Caption -->
	<display:caption><b><spring:message code="application.problem.attachments"/></b></display:caption>
	
	<!-- Problem link -->
	<spring:message code="problem.attachment" var="attachmentsHeader" />
	<display:column property="link" title="${attachmentsHeader}" />
	
</display:table>

<jstl:if test="${ application.getAnswer() != null }">
<display:table name="application.answer" id="row" requestURI="${ requestUri }" class="displaytag">
	
	<!-- Caption -->
	<display:caption><b><spring:message code="application.answer"/></b></display:caption>
	
	<!-- Answer text -->
	<spring:message code="application.answer.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" />
	
	<!-- Answer link -->
	<spring:message code="application.answer.link" var="linkHeader" />
	<display:column property="link" title="${linkHeader}" />
	
</display:table>
</jstl:if>
<br>
<br>

<jstl:if test="${ application.answer == null }">
	<acme:cancel url="application/hacker/update.do?appId=${application.id}" code="application.update"/>
</jstl:if>
<acme:back code="application.goBack"/>








