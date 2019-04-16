<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="enrols" id="row" requestURI="${uri}" pagesize="5" class="displaytag">
	<!-- Name -->
	<spring:message code="member.name" var="nameHeader" />
	<display:column property="member.name" title="${nameHeader}" />
	
	<!-- Description -->
	<spring:message code="enrol.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
	format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<!-- Spammer -->
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="member.spammer" var="spammerHeader" />
	<display:column title="${spammerHeader}">
		<jstl:choose>
			<jstl:when test="${row.member.isSpammer != null}">
				<jstl:if test="${row.member.isSpammer}">
				<spring:message code="brotherhood.true" var="trueVar" />
					${trueVar}
				</jstl:if>
				<jstl:if test="${!row.member.isSpammer}">
				<spring:message code="brotherhood.false" var="falseVar" />
					${falseVar}
				</jstl:if>
			</jstl:when>
			<jstl:otherwise>
				N/A
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	</security:authorize>
	
	<!-- Positions -->
	<spring:message code="enrol.position" var="positionsHeader" />
	
		<jstl:if test="${language=='es'}">
		<!-- Spanish Name -->
		<display:column title="${positionsHeader}" sortable="false">
			<jstl:forEach var="position" items="${row.positions}" varStatus="loop">
					${position.spanishName}${!loop.last ? ',' : ''}&nbsp
			</jstl:forEach>
		</display:column>
		</jstl:if>
		
		<jstl:if test="${language=='en'}">
		<!-- English Name -->
		<display:column title="${positionsHeader}" sortable="false">
			<jstl:forEach var="position" items="${row.positions}" varStatus="loop">
					${position.englishName}${!loop.last ? ',' : ''}&nbsp
			</jstl:forEach>
		</display:column>
		</jstl:if>
	
		<jstl:if test="${empty language}">
			<!-- Spanish Name -->
			<display:column title="${positionsHeader}" sortable="false">
				<jstl:forEach var="position" items="${row.positions}" varStatus="loop">
						${position.englishName}${!loop.last ? ',' : ''}&nbsp
				</jstl:forEach>
			</display:column>
			
			<!-- English Name -->
			<display:column title="${positionsHeader}" sortable="false">
				<jstl:forEach var="position" items="${row.positions}" varStatus="loop">
						${position.spanishName}${!loop.last ? ',' : ''}&nbsp
				</jstl:forEach>
			</display:column>
		</jstl:if>
		
	<jstl:choose>
	<jstl:when test="${not empty row.positions}">

		
		<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${not empty bro}">		
			<display:column>
        		<a href="member/brotherhood/selectPosition.do?enrolId=${row.id}">
        			<spring:message code="brotherhood.changePosition"/>
        		</a>
        	</display:column>
	   </jstl:if>
	   </security:authorize>
	</jstl:when>
	
	<jstl:otherwise>
		<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${not empty bro}">
			<display:column>
        		<a href="member/brotherhood/selectPosition.do?enrolId=${row.id}">
        			<spring:message code="brotherhood.selectPosition"/>
	        	</a>
	        </display:column>
		  </jstl:if>
	   </security:authorize>
	</jstl:otherwise>
	</jstl:choose>
	
	
	<security:authorize access="hasRole('BROTHERHOOD')">
	<jstl:if test="${not empty bro}">
	
	<!-- Display -->
		<display:column>
        	<a href="member/brotherhood/display.do?memberId=${row.member.id}">
        		<spring:message code="member.display"/>
        	</a>
        </display:column>
        
        
	<!-- Disenroll -->
		<display:column>
        	<a href="member/brotherhood/disenroll.do?enrolId=${row.id}">
        		<spring:message code="member.disenroll"/>
        	</a>
        </display:column>
   </jstl:if>
   </security:authorize>

</display:table>

<security:authorize access="isAnonymous()">
	<acme:back code="member.goback"/>
</security:authorize>