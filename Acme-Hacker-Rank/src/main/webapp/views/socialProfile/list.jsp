<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing Grid -->
<display:table name="profiles" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<!-- Edit -->
	<spring:message code="profile.edit" var="editHeader" />
	<display:column title="${editHeader}">
		<a href="socialProfile/edit.do?socialProfileId=${row.id}"> <spring:message code="profile.edit" /></a>
	</display:column>


	<!-- Nick -->
	<spring:message code="profile.nick" var="nickHeader" />
	<display:column property="nick" title="${titleHeader}" />

	<!-- Social Network -->
	<spring:message code="profile.socialNetwork" var="netHeader" />
	<display:column property="socialNetwork" title="${netHeader}" />

	<!-- Link -->
	<spring:message code="profile.link" var="linkHeader" />
	<display:column property="link" title="${linkHeader}" />
	
	<!-- Remove -->
	<spring:message code="profile.remove.profile" var="removeHeader" />
	<display:column title="${removeHeader}">
		<a href="socialProfile/remove.do?socialProfileId=${row.id}"> <spring:message code="profile.remove.profile" /></a>
	</display:column>

</display:table>


<!-- Add Social Profile -->
<a href=socialProfile/create.do><spring:message code="profile.create" /></a>



