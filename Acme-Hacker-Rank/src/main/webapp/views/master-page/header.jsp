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
					<li><a href="message/admin/broadcast.do"><spring:message code="master.page.administrator.broadcast" /></a></li>
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
			<li><a class="fNiv"><spring:message	code="master.page.problems" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="problem/company/list.do"><spring:message code="master.page.problems.list" /></a></li>
					<li><a href="problem/company/create.do"><spring:message code="master.page.problems.create" /></a></li>			
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.hacker.applications" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/company/list.do"><spring:message code="master.page.hacker.applications.list" /></a></li>
				</ul>
		</li>
		
		</security:authorize>
		
		
		<!-- An actor who is authenticated as a HACKER -->
		<security:authorize access="hasRole('HACKER')">
		<li><a class="fNiv"><spring:message code="master.page.hacker.applications" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/hacker/list.do"><spring:message code="master.page.hacker.applications.list" /></a></li>
				</ul>
		</li>
		<li><a class="fNiv"><spring:message	code="master.page.finder" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/hacker/edit.do"><spring:message code="master.page.finder.edit" /></a></li>
					<li><a href="finder/hacker/result.do"><spring:message code="master.page.finder.result" /></a></li>
				</ul>
		</li>
		<li><a class="fNiv"><spring:message code="master.page.curricula" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curricula/hacker/list.do"><spring:message code="master.page.curricula.list" /></a></li>
				</ul>
		</li>
		</security:authorize>
		
		
		
		<!-- An actor who is NOT authenticated -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message	code="master.page.positions" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/list.do"><spring:message code="master.page.position.list" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.companies" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="company/list.do"><spring:message code="master.page.company.list" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="hacker/create.do"><spring:message code="master.page.hacker.register" /></a></li>
					<li><a href="company/create.do"><spring:message code="master.page.company.register" /></a></li>			
				</ul>
			</li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		
		<!-- An actor who is authenticated -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"><spring:message	code="master.page.companies" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="company/list.do"><spring:message code="master.page.company.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.positions" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/list.do"><spring:message code="master.page.position.list" /></a></li>	
					<security:authorize access="hasRole('COMPANY')">
						<li><a href="position/company/list.do"><spring:message code="master.page.position.company.list" /></a></li>
					</security:authorize>			
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
						<li><a href="hacker/edit.do"><spring:message code="master.page.hacker.edit" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('COMPANY')">
						<li><a href="company/edit.do"><spring:message code="master.page.hacker.edit" /></a></li>
					</security:authorize>
					
					<!-- Message -->
					<li><a href="message/list.do"><spring:message code="master.page.message.list" /></a></li>
					
					<!-- Social Profile -->
					<li><a href="socialProfile/list.do"><spring:message code="master.page.socialProfile" /></a></li>
					
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

