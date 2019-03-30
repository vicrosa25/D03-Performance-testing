<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<script type="text/JavaScript">
function check()
{
	var check1=confirm("Confirmar publicación / Confirm publish");
	if (check1){
		var check2=confirm("¿Estás seguro? / Are you sure?");
		
		if (check2)	{return true;}
		else	{return false;}
	}
	else	{return false;}
}
</script>

<form:form action="proclaim/chapter/publish.do" modelAttribute="proclaim">
	<form:hidden path="moment" />
	<%-- Text --%>
	<acme:textarea code="proclaim.text" path="text" />
	<br>
	
	<input type="submit" name="save" value="<spring:message code="path.save"/>"
		onClick="javascript: return check()" />
	<acme:cancel code="path.cancel" url="/" />
</form:form>