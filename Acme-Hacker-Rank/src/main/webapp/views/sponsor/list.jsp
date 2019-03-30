<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="sponsors" id="row" requestURI="sponsor/list.do"
	pagesize="5" class="displaytag">


	<spring:message code="sponsor.username" var="usernameHeader" />
	<display:column property="userAccount.username"
		title="${usernameHeader}" />

	<spring:message code="sponsor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="sponsor.middleName" var="middlenNameHeader" />
	<display:column property="middleName" title="${middleNameHeader}"
		sortable="true" />

	<spring:message code="sponsor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}"
		sortable="true" />

	<spring:message code="sponsor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" />

	<spring:message code="sponsor.phoneNumber" var="phoneNumberHeader" />
	<display:column property="phoneNumber" title="${phoneNumberHeader}" />

	<display:column>
		<a href="socialIdentity/list.do?actorId=${row.id}"> <spring:message
				code="sponsor.socialIdentities" />
		</a>
	</display:column>

	<display:column>
		<a href="complaint/list.do?sponsorId=${row.id}"> <spring:message
				code="sponsor.complaints" />
		</a>
	</display:column>

	<display:column>
		<a href="complaint/list.do?sponsorId=${row.id}"> <spring:message
				code="sponsor.reports" />
		</a>
	</display:column>

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="sponsor/edit.do?sponsorId=${row.id}"> <spring:message
					code="sponsor.edit" />
			</a>
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('ADMIN')">
	<a href=sponsor/create.do><spring:message
			code="sponsor.create" /></a>
</security:authorize>