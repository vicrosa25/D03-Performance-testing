<%--
 * index.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<link rel="stylesheet" href="styles/cookies.css" type="text/css">

<!-- SCRIPT CONTROL DE COOKIES -->
<script type="text/javascript">

    function controlcookies() {
        // si variable no existe se crea (al clicar en Aceptar)
        localStorage.controlcookie = (localStorage.controlcookie || 0);
        localStorage.controlcookie++; // incrementamos cuenta de la cookie
        cookie1.style.display='none'; // Esconde la política de cookies
    }
</script>
</head>

<body>
    <!--Código HTML de la política de cookies -->
    <div class="cookiesms" id="cookie1">
    	<spring:message code="welcome.cookies.web" /> <a href="welcome/legal.do"><spring:message code="welcome.link"/> </a> 
   		<spring:message code="welcome.acept" />
    	<button name= "aceptar" id="aceptar" onclick="controlcookies()"><spring:message code="welcome.accept"/></button>
    	<div  class="cookies2" onmouseover="document.getElementById('cookie1').style.bottom = '0px';"><spring:message code="welcome.cookies.policies"/></div>
    </div>
    <script type="text/javascript">
    	if (localStorage.controlcookie>0){ 
    		document.getElementById('cookie1').style.bottom = '-50px';
    		document.getElementById('aceptar').style.display= 'none';
    	}
    </script>
	<!-- Fin del código de cookies --->
			
		<p><spring:message code="welcome.greeting.prefix" /> 
		<security:authorize access="isAuthenticated()">
			&nbsp${name}&nbsp
		</security:authorize>
		<spring:message code="welcome.greeting.suffix" /></p>
		
		<jstl:if test="${language == 'en'}">
			<p>${englishMessage} ${moment}</p> 
		</jstl:if>
		<jstl:if test="${language == 'es'}">
			<p>${spanishMessage} ${moment}</p> 
		</jstl:if>
		
	<a href="welcome/legal.do"><spring:message code="welcome.message"/></a>

	</body>

</html>