<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${logo}" alt="${title}" width="1000" height="240" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
	
		<!-- An actor who is authenticated as an ADMIN -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/list.do"><spring:message code="master.page.administrator.list" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a href="message/broadcast.do"><spring:message code="master.page.administrator.broadcast" /></a></li>
					<li><a href="administrator/spammers.do"><spring:message code="master.page.administrator.spammers" /></a></li>					
					<li><a href="administrator/securityBreach.do"><spring:message code="master.page.administrator.securityBreach" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.configurations" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/config/cache/edit.do"><spring:message code="master.page.cache" /></a></li>
					<li><a href="administrator/config/aliveConfig/edit.do"><spring:message code="master.page.settings" /></a></li>
					<li><a href="administrator/config/spam/list.do"><spring:message code="master.page.spam.words" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<!-- An actor who is authenticated as a COMPANY -->
		<security:authorize access="hasRole('COMPANY')">
		
		</security:authorize>
		
		
		<!-- An actor who is authenticated as a HACKER -->
		<security:authorize access="hasRole('HACKER')">
			
		</security:authorize>
		
		
		
		<!-- An actor who is NOT authenticated -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message	code="master.page.position" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chapter/list.do"><spring:message code="master.page.position.list" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="hacker/create.do"><spring:message code="master.page.member.register" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		
		<!-- An actor who is authenticated -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"><spring:message	code="master.page.companies" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/list.do"><spring:message code="master.page.brotherhood.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.position.list" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/list.do"><spring:message code="master.page.position.list" /></a></li>				
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>					
					
					<!-- PROFILE -->
					<security:authorize access="hasRole('HACKER')">
						<li><a href="hacker/edit.do"><spring:message code="master.page.brotherhood.edit" /></a></li>
					</security:authorize>
					
					<!-- Social Profile -->
					<li><a href="socialIdentity/list.do"><spring:message code="master.page.socialProfile" /></a></li>
					
					<!-- LOGOUT -->
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>	
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

