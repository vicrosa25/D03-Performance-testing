<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="brotherhoods" id="row" requestURI="${ requestUri }" pagesize="5" class="displaytag">

	<!-- Title -->
	<spring:message code="brotherhood.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<!-- Establishment -->
	<spring:message code="brotherhood.establishment"
		var="establishmentHeader" />
	<display:column property="establishment" title="${establishmentHeader}"
		format="{0,date,dd/MM/yyyy}" />


	<!-- History -->
	<spring:message code="brotherhood.history" var="historyHeader" />
	<display:column title="${historyHeader}">
		<a href="history/display.do?brotherhoodId=${row.id}">
			<spring:message code="brotherhood.history" />
		</a>
	</display:column>
	
	<!-- Spammer -->
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="brotherhood.spammer" var="spammerHeader" />
	<display:column title="${spammerHeader}">
		<jstl:choose>
			<jstl:when test="${row.isSpammer != null}">
				<jstl:if test="${row.isSpammer}">
				<spring:message code="brotherhood.true" var="trueVar" />
					${trueVar}
				</jstl:if>
				<jstl:if test="${!row.isSpammer}">
				<spring:message code="brotherhood.false" var="falseVar" />
					${falseVar}
				</jstl:if>
			</jstl:when>
			<jstl:otherwise>
				N/A
			</jstl:otherwise>
		</jstl:choose>
	</display:column>

	<!-- Polarity Score -->
	<spring:message code="brotherhood.polarity" var="polarityHeader" />
	<display:column title="${polarityHeader}">
		<jstl:choose>
			<jstl:when test="${row.score != null}">
				<jstl:out value="${row.score}" />
			</jstl:when>
			<jstl:otherwise>
				N/A
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	</security:authorize>

	<!-- Members -->
	<spring:message code="brotherhood.members" var="membersHeader" />
	<display:column>
		<a href="member/list.do?brotherhoodId=${row.id}"> <spring:message
				code="brotherhood.members" />
		</a>
	</display:column>

	<!-- Processions -->
	<spring:message code="brotherhood.processions" var="processionsHeader" />
	<display:column>
		<a href="procession/brotherhoodList.do?brotherhoodId=${row.id}"> <spring:message
				code="brotherhood.processions" /></a>
	</display:column>

	<!-- Floats -->
	<spring:message code="brotherhood.coaches" var="coachesHeader" />
	<display:column>
		<a href="coach/list.do?brotherhoodId=${row.id}"> <spring:message
				code="brotherhood.coaches" /></a>
	</display:column>


	<!-- Dropout -->
	<security:authorize access="hasRole('MEMBER')">
		<jstl:if test="${requestUri.contains('member') and not requestUri.contains('dropped')}">
			<display:column>
				<a href="brotherhood/member/dropout.do?brotherhoodId=${row.id}">
					<spring:message code="brotherhood.dropout" />
				</a>
			</display:column>
		</jstl:if>
	</security:authorize>


</display:table>


<acme:back code="chapter.back"/>




















