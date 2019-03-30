<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="coaches" id="row" requestURI="coaches/list.do" pagesize="5" class="displaytag">

	<!-- Title -->
	<spring:message code="coach.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<!-- Description -->
	<spring:message code="coach.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	
	<!-- Brotherhood -->
	<spring:message code="procession.brotherhood" var="brotherhoodHeader" />
	<display:column title="${brotherhoodHeader}" >${brotherhood.title}</display:column>

	<!-- Display -->
	<spring:message code="coach.display" var="displayHeader" />
	<display:column title="${displayHeader}">
		<a href="coach/display.do?coachId=${row.id}">
		<spring:message code="coach.display"/>
		</a>
	</display:column>

   <security:authorize access="hasRole('BROTHERHOOD')">
   <jstl:if test="${not empty bro}">
        <display:column>
          <a href="coach/brotherhood/delete.do?coachId=${row.id}">
            <spring:message code="coach.delete"/>
          </a>
        </display:column>
        <display:column>
          <a href="coach/brotherhood/edit.do?coachId=${row.id}">
            <spring:message code="coach.edit"/>
          </a>
        </display:column>
   </jstl:if>
   </security:authorize>

</display:table>

<security:authorize access="isAnonymous()">
	<acme:back code="member.goback"/>
</security:authorize>