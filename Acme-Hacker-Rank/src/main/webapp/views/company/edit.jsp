<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="company/edit.do" modelAttribute="company">

	<%-- Hidden properties from pruned company--%>
	<form:hidden path="id" />

	<%-- Name --%>
	<acme:textbox code="company.name" path="name" />
	<br>

	<%-- Surname --%>
	<acme:textbox code="company.surname" path="surname" />
	<br>

	<%-- Photo --%>
	<acme:textbox code="company.photo" path="photo" />
	<br>

	<%-- Phone --%>
	<acme:textbox code="company.phone" path="phoneNumber" />
	<br>

	<%-- email --%>
	<acme:textbox code="company.email" path="email" />
	<br>

	<%-- Address --%>
	<acme:textbox code="company.address" path="address" />
	<br>

	<%-- Vat --%>
	<acme:textbox code="company.vat" path="vat" />
	<br>

	<%-- Card number --%>
	<acme:textbox code="company.cardNumber" path="cardNumber" />
	<br>

	<%-- Commercial name --%>
	<acme:textbox code="company.commercialName" path="commercialName" />
	<br>
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
				return confirm('<spring:message code="company.confirm"/>');
		}
	</script>
	<%-- Buttons --%>
	<input type="submit" name="save"
		value="<spring:message code="company.save"/>"
		onClick="javascript: return phoneNumberValidator()" />
	
	<acme:cancel code="company.cancel" url="/" />
	<acme:cancel code="company.delete" url="/company/delete.do" />
	<acme:cancel code="company.export" url="/company/generatePDF.do" />
</form:form>