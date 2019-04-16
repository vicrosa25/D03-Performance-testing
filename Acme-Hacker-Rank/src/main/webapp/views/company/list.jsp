<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="companies" id="row" requestURI="${ requestUri }" pagesize="5" class="displaytag">

	<!-- commercialName -->
	<spring:message code="company.commercialName" var="commercialNameHeader" />
	<display:column property="commercialName" title="${commercialNameHeader}" />
	
	<!-- Spammer -->
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="company.spammer" var="spammerHeader" />
	<display:column title="${spammerHeader}">
		<jstl:choose>
			<jstl:when test="${row.isSpammer != null}">
				<jstl:if test="${row.isSpammer}">
				<spring:message code="company.true" var="trueVar" />
					${trueVar}
				</jstl:if>
				<jstl:if test="${!row.isSpammer}">
				<spring:message code="company.false" var="falseVar" />
					${falseVar}
				</jstl:if>
			</jstl:when>
			<jstl:otherwise>
				N/A
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	</security:authorize>

	<!-- name -->
	<spring:message code="company.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />


	<!-- positions -->
	<display:column>
		<a href="position/company.do?companyId=${row.id}">
			<spring:message code="company.positions" />
		</a>
	</display:column>

</display:table>