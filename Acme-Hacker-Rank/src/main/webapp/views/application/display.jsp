<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="application" id="row" requestURI="${requestUri}" class="displaytag">
	
	<!-- Creation moment -->
	<spring:message code="application.creationMoment" var="creationMomentHeader" />
	<display:column property="creationMoment" title="${creationMomentHeader}" format="{0,date,dd/MM/yyyy}" />
	
	<!-- Status -->
	<spring:message code="application.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}" />

</display:table>


<display:table name="application.position" id="row" requestURI="${ requestUri }" class="displaytag">

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


<display:table name="application.problem" id="row" requestURI="${ requestUri }" class="displaytag">
	
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



<display:table name="application.problem.attachments" id="row" requestURI="${ requestUri }" class="displaytag">
	
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

<spring:message code="curricula.startDate" var="startDateHeader" />
<spring:message code="curricula.endDate" var="endDateHeader" />
<spring:message code="curricula.edit" var="editHeader" />
<spring:message code="curricula.delete" var="deleteHeader" />

<!-- Personal Data -->
<jstl:if test="${not empty curricula.personalData}">
	<display:table name="curricula.personalData" requestURI="${ requestURI }"   id="row" >
		<spring:message code="curricula.fullName" var="fullNameHeader" />
		<display:column property="fullName" title="${fullNameHeader}" sortable="false" />
		
		<spring:message code="curricula.statement" var="statementHeader" />
		<display:column property="statement" title="${statementHeader}" sortable="false" />

		<display:column sortable="false" >
			<a href="${row.gitHub }">GitHub</a>
		</display:column>

		<display:column sortable="false" >
			<a href="${row.linkedIn }">LinkedIn</a>
		</display:column>
		
		<spring:message code="curricula.phoneNumber" var="phoneNumberHeader" />
		<display:column property="phoneNumber" title="${phoneNumberHeader}" sortable="false" />		
	<display:caption><spring:message code="curricula.personalData"/></display:caption>
	</display:table>
</jstl:if>
<!-- Position Data -->	
<br>
<jstl:if test="${not empty curricula.positionData}">
<display:table name="curricula.positionData"  id="row" >
	<spring:message code="curricula.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />
		
	<spring:message code="curricula.description" var="descriptionHeader" />
	<display:column property="description" title="${desscriptionHeader}" sortable="false" />
	
	<display:column property="startDate" title="${startDateHeader}" sortable="false" format="{0,date,dd/MM/yyyy}" />

	<display:column property="endDate" title="${endDateHeader}" sortable="false" format="{0,date,dd/MM/yyyy}" />
	
<display:caption><spring:message code="curricula.positionData"/></display:caption>
</display:table>
</jstl:if>
	
	
<!-- Education data -->	
<br>
<jstl:if test="${not empty curricula.educationData}">
<display:table name="curricula.educationData"  id="row" >
	<spring:message code="curricula.degree" var="degreeHeader" />
	<display:column property="degree" title="${degreeHeader}" sortable="false" />

	<spring:message code="curricula.institution" var="institutionHeader" />
	<display:column property="institution" title="${institutionHeader}" sortable="false" />
	
	<spring:message code="curricula.mark" var="markHeader" />
	<display:column property="mark" title="${markHeader}" sortable="false" />
	
	<display:column property="startDate" title="${startDateHeader}" sortable="false" format="{0,date,dd/MM/yyyy}" />

	<display:column property="endDate" title="${endDateHeader}" sortable="false" format="{0,date,dd/MM/yyyy}" />
	
<display:caption><spring:message code="curricula.educationData"/></display:caption>
</display:table>
</jstl:if>
	
	
<!-- miscellaneousData -->	
<br>
<jstl:if test="${not empty application.curricula.miscellaneousData}">
<display:table name="curricula.miscellaneousData"  id="row" >
	<spring:message code="curricula.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="false" />

	<!-- Attachments -->
	<spring:message code="problem.attachments" var="attachmentsHeader" />
	<display:column title="${attachmentsHeader}" sortable="false" >
		<jstl:forEach var="text" items="${row.attachments}" varStatus="loop">
				${text.link}${!loop.last ? ',' : ''}&nbsp
		</jstl:forEach>
	</display:column>
<display:caption><spring:message code="curricula.miscellaneousData"/></display:caption>
</display:table>
</jstl:if>
<br>
<br>


<security:authorize access="hasRole('HACKER')">
<jstl:if test="${ application.answer == null }">
	<acme:cancel url="application/hacker/update.do?appId=${application.id}" code="application.update"/>
</jstl:if>
</security:authorize>
<acme:back code="application.goBack"/>








