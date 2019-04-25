<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="welcome" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PROTECCIÓN DE DATOS</title>
</head>
<body>
	<p><strong><spring:message code="welcome.title" /></strong></p>
	<p><spring:message code="welcome.p1" /></p>
	<p><spring:message code="welcome.p2" /></p>
	<p><spring:message code="welcome.p3" /></p>
	<p><spring:message code="welcome.p4" /></p>
	<p><strong><spring:message code="welcome.services" /></strong></p>
	<p><spring:message code="welcome.p5" /></p>
	<p><strong><spring:message code="welcome.cookies" /></strong></p>
	<p><spring:message code="welcome.p6" /></p>
	<p><strong><spring:message code="welcome.general" /></strong></p>
	<p><spring:message code="welcome.p7" /></p>
	<p><strong><spring:message code="welcome.how.function" /></strong></p>
	<p><spring:message code="welcome.p8" /></p>
	<p><spring:message code="welcome.p9" /></p>
	<p><strong><spring:message code="welcome.type" /></strong></p>
	<p><strong><spring:message code="welcome.session" /></strong></p>
	<p><spring:message code="welcome.p10" /></p>
	<p><strong><spring:message code="welcome.language" /></strong></p>
	<p><spring:message code="welcome.p11" /></p>
	<p><strong><spring:message code="welcome.manage.cookie" /></strong></p>
	<p><spring:message code="welcome.p12" /></p>
	<p><spring:message code="welcome.p13" /></p>
	<p><spring:message code="welcome.p14" /></p>
	<p><spring:message code="welcome.p15" /></p>
	<p><strong><spring:message code="welcome.consent" /></strong></p>
	<p><spring:message code="welcome.p16" /></p>
	<br>
	<input type="button" name="cancel" value="<spring:message code="welcome.goback" />"
			onClick="javascript: window.location.replace('welcome/index.do')" />
</body>
</html>