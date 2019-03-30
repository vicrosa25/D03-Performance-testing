<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="sponsor/edit.do" modelAttribute="sponsor">

	<%-- Hidden properties from member--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<%-- Name --%>
	<acme:textbox code="sponsor.name" path="name" />
	<br>

	<%-- Middlename --%>
	<acme:textbox code="sponsor.middleName" path="middleName" />
	<br>

	<%-- Surname --%>
	<acme:textbox code="sponsor.surname" path="surname" />
	<br>

	<%-- Photo --%>
	<acme:textbox code="sponsor.photo" path="photo" />
	<br>

	<%-- Phone --%>
	<acme:textbox code="sponsor.phone" path="phoneNumber" />
	<br>

	<%-- email --%>
	<acme:textbox code="sponsor.email" path="email" />
	<br>

	<%-- Address --%>
	<acme:textbox code="sponsor.address" path="address" />
	<br>

	<script type="text/javascript">
		function phoneNumberValidator() {

			var phoneNumber = document.getElementById("phoneNumber").value;

			var patternCCACPN = /^(\+[1-9][0-9]{0,2}) (\([1-9][0-9]{0,2}\)) (\d{3}\d+)/
			$;
			var patternCCPN = /^(\+[1-9][0-9]{0,2}) (\d{3}\d+)/
			$;
			var patternPN = /^(\d{3}\d+)/
			$;

			if (patternCCACPN.test(phoneNumber))
				return true;
			else if (patternCCPN.test(phoneNumber))
				return true;
			else if (patternPN.test(phoneNumber))
				return true;
			else
				return confirm('<spring:message code="brotherhood.confirm"/>');
		}
	</script>
	<%-- Buttons --%>
	<input type="submit" name="save"
		value="<spring:message code="sponsor.save"/>"
		onClick="javascript: return phoneNumberValidator()" />
	
	<acme:cancel code="sponsor.cancel" url="/" />
	<acme:cancel code="brotherhood.delete" url="/sponsor/delete.do" />
	<acme:cancel code="brotherhood.export" url="/sponsor/generatePDF.do" />
</form:form>