<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<spring:message code="curricula.startDate" var="startDateHeader" />
<spring:message code="curricula.endDate" var="endDateHeader" />
<spring:message code="curricula.edit" var="editHeader" />
<spring:message code="curricula.delete" var="deleteHeader" />

<h1>${curricula.title}</h1>
<br>

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
	
	<jstl:if test="${requestURI == 'curricula/hacker/display.do'}">
		<a href="curricula/personalData/hacker/edit.do?curriculaId=${curricula.id}">
			<spring:message code="curricula.personalData.edit"/>
		</a>
		<br>
	</jstl:if>
</jstl:if>
<jstl:if test="${empty curricula.personalData}">
	<jstl:if test="${requestURI == 'curricula/hacker/display.do'}">
		<a href="curricula/personalData/hacker/create.do?curriculaId=${curricula.id}">
			<spring:message code="curricula.personalData.create"/>
		</a>
		<br>
	</jstl:if>
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

	
	<jstl:if test="${requestURI == 'curricula/hacker/display.do'}">
	<display:column title="${editHeader }">
		<a href="curricula/positionData/hacker/edit.do?positionDataId=${row.id}">
			<spring:message code="curricula.edit"/>
		</a>
	</display:column>
	<display:column title="${deleteHeader}">
		<a href="curricula/positionData/hacker/delete.do?positionDataId=${row.id}"><spring:message code="curricula.delete"/></a>
	</display:column>
	</jstl:if>
	
<display:caption><spring:message code="curricula.positionData"/></display:caption>
</display:table>
</jstl:if>
	
<jstl:if test="${requestURI == 'curricula/hacker/display.do'}">
	<a href="curricula/positionData/hacker/create.do?curriculaId=${curricula.id}">
		<spring:message code="curricula.positionData.create"/>
	</a>
	<br>
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
	
	<jstl:if test="${requestURI == 'curricula/hacker/display.do'}">
	<display:column title="${editHeader}">
		<a href="curricula/educationData/hacker/edit.do?educationDataId=${row.id}">
			<spring:message code="curricula.edit"/>
		</a>
	</display:column>
	<display:column title="${deleteHeader}">
		<a href="curricula/educationData/hacker/delete.do?educationDataId=${row.id}">
		<spring:message code="curricula.delete"/></a>
	</display:column>
	</jstl:if>
	
<display:caption><spring:message code="curricula.educationData"/></display:caption>
</display:table>
</jstl:if>
	
<jstl:if test="${requestURI == 'curricula/hacker/display.do'}">
	<a href="curricula/educationData/hacker/create.do?curriculaId=${curricula.id}">
		<spring:message code="curricula.educationData.create"/>
	</a>
	<br>
</jstl:if>
	
	
<!-- miscellaneousData -->	
<br>
<jstl:if test="${not empty curricula.miscellaneousData}">
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
	
	<jstl:if test="${requestURI == 'curricula/hacker/display.do'}">
	<display:column title="${editHeader}">
		<a href="curricula/miscellaneousData/hacker/edit.do?miscellaneousDataId=${row.id}">
			<spring:message code="curricula.edit"/>
		</a>
	</display:column>
	<display:column title="${deleteHeader}">
		<a href="curricula/miscellaneousData/hacker/delete.do?miscellaneousDataId=${row.id}"><spring:message code="curricula.delete"/></a>
	</display:column>
	</jstl:if>
<display:caption><spring:message code="curricula.miscellaneousData"/></display:caption>
</display:table>
</jstl:if>
	
<jstl:if test="${requestURI == 'curricula/hacker/display.do'}">
	<a href="curricula/miscellaneousData/hacker/create.do?curriculaId=${curricula.id}">
		<spring:message code="curricula.miscellaneousData.create"/>
	</a>
	<br>
</jstl:if>

<acme:back code="hacker.goback"/>