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
					<li><a href="position/admin/list.do"><spring:message code="master.page.position.list" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a href="message/broadcast.do"><spring:message code="master.page.administrator.broadcast" /></a></li>
					<li><a href="area/administrator/list.do"><spring:message code="master.page.administrator.area" /></a></li>
					<li><a href="administrator/spammers.do"><spring:message code="master.page.administrator.spammers" /></a></li>					
					<li><a href="administrator/config/polarityWords/list.do"><spring:message code="master.page.administrator.polarity.words" /></a></li>
					<li><a href="administrator/score.do"><spring:message code="master.page.administrator.score" /></a></li>
					<li><a href="administrator/sponsorship/list.do"><spring:message code="master.page.administrator.sponsorship" /></a></li>
					<li><a href="administrator/securityBreach.do"><spring:message code="master.page.administrator.securityBreach" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.configurations" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/config/cache/edit.do"><spring:message code="master.page.cache" /></a></li>
					<li><a href="administrator/config/aliveConfig/edit.do"><spring:message code="master.page.settings" /></a></li>
					<li><a href="administrator/config/spam/list.do"><spring:message code="master.page.spam.words" /></a></li>
					<li><a href="administrator/config/brand/list.do"><spring:message code="master.page.config.brand" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<!-- An actor who is authenticated as a BROTHERHOOD -->
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message	code="master.page.processions" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="procession/list.do"><spring:message code="master.page.procession.list" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.coach.brotherhood" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="coach/brotherhood/list.do"><spring:message code="master.page.coach.list" /></a></li>
					<li><a href="coach/brotherhood/create.do"><spring:message code="master.page.coach.create" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.request" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/brotherhood/list.do"><spring:message code="master.page.request.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood.member" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="member/brotherhood/list.do"><spring:message code="master.page.member.list" /></a></li>				
				</ul>
			</li>
			<li><a href="history/brotherhood/display.do"><spring:message	code="master.page.history.brotherhood" /></a></li>
		</security:authorize>
		
		
		<!-- An actor who is authenticated as a MEMBER -->
		<security:authorize access="hasRole('MEMBER')">
			<li><a class="fNiv"><spring:message	code="master.page.request" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/list.do"><spring:message code="master.page.request.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.finder" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/member/edit.do"><spring:message code="master.page.finder.edit" /></a></li>
					<li><a href="finder/member/result.do"><spring:message code="master.page.finder.result" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<!-- An actor who is authenticated as a CHAPTER -->
		<security:authorize access="hasRole('CHAPTER')">
			<li><a class="fNiv"><spring:message	code="master.page.area" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chapter/area/assign.do"><spring:message code="master.page.area.assign" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.chapter.parades" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chapter/parade/list.do"><spring:message code="master.page.chapter.myParades" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.proclaims" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="proclaim/chapter/publish.do"><spring:message	code="master.page.proclaim.publish" /></a></li>
					<li><a href="proclaim/chapter/list.do"><spring:message	code="master.page.proclaim.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<!-- An actor who is authenticated as a SPONSOR -->
		<security:authorize access="hasRole('SPONSOR')">
			<li><a href="sponsorship/sponsor/list.do" class="fNiv"><spring:message	code="master.page.sponsorships" /></a></li>
		</security:authorize>
		
		
		
		<!-- An actor who is NOT authenticated -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/list.do"><spring:message code="master.page.brotherhood.list" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.chapter" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chapter/list.do"><spring:message code="master.page.chapter.list" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/create.do"><spring:message code="master.page.brotherhood.register" /></a></li>	
					<li><a href="member/create.do"><spring:message code="master.page.member.register" /></a></li>
					<li><a href="chapter/create.do"><spring:message code="master.page.chapter.register" /></a></li>
					<li><a href="sponsor/create.do"><spring:message code="master.page.sponsor.register" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		
		<!-- An actor who is authenticated -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/list.do"><spring:message code="master.page.brotherhood.list" /></a></li>
					<security:authorize access="hasRole('MEMBER')">
						<li><a href="brotherhood/member/list.do"><spring:message code="master.page.brotherhood.member.list" /></a></li>
						<li><a href="brotherhood/member/dropped.do	"><spring:message code="master.page.brotherhood.member.list.dropped" /></a></li>
						<li><a href="enrol/member/create.do"><spring:message code="master.page.member.enrol" /></a></li>		
					</security:authorize>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.chapter" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chapter/list.do"><spring:message code="master.page.chapter.list" /></a></li>				
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
					<security:authorize access="hasRole('BROTHERHOOD')">
						<li><a href="brotherhood/edit.do"><spring:message code="master.page.brotherhood.edit" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="administrator/update.do"><spring:message code="master.page.brotherhood.edit" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('MEMBER')">
						<li><a href="member/edit.do"><spring:message code="master.page.member.edit" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('CHAPTER')">
						<li><a href="chapter/edit.do"><spring:message code="master.page.chapter.edit" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('SPONSOR')">
						<li><a href="sponsor/edit.do"><spring:message code="master.page.sponsor.edit" /></a></li>
					</security:authorize>
					
					<!-- MessageBox -->
					<li><a href="messageBox/list.do"><spring:message code="master.page.messageBox.list" /></a></li>
					
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

