<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="chapter/create.do" modelAttribute="chapterForm">
	
	
	<%-- UserAccount--%>
	<%-- username--%>
	<acme:textbox code="chapter.username" path="userAccount.username" />
	<br>

	<%-- password--%>
	<acme:password code="chapter.password" path="userAccount.password" />
	<br>
	
	<%-- Title --%>
	<acme:textbox code="chapter.title" path="title" />
	<br>

	<%-- Name --%>
	<acme:textbox code="chapter.name" path="name" />
	<br>

	<%-- Middlename --%>
	<acme:textbox code="chapter.middleName" path="middlename" />
	<br>

	<%-- Surname --%>
	<acme:textbox code="chapter.surname" path="surname" />
	<br>

	<%-- Photo --%>
	<acme:textbox code="chapter.photo" path="photo" />
	<br>

	<%-- Phone --%>
	<acme:textbox code="chapter.phone" path="phoneNumber" />
	<br>

	<%-- email --%>
	<acme:textbox code="chapter.email" path="email" />
	<br>

	<%-- Address --%>
	<acme:textbox code="chapter.address" path="address" />
	<br>
	
	<%-- Accept Legal term --%>
	<form:label path="accepted">
		<spring:message code="register.terms.accept" />
	</form:label>
	<form:checkbox path="accepted"/>
	<form:errors path="accepted" cssClass="error" />
	<br><br>

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
				return confirm('<spring:message code="chapter.confirm"/>');
		}
	</script>
	<%-- Buttons --%>
	<input type="submit" name="save"
		value="<spring:message code="chapter.save"/>"
		onClick="javascript: return phoneNumberValidator()" />
	
	<acme:cancel code="chapter.cancel" url="/" />
</form:form>



