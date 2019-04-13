<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="problem/company/edit.do" modelAttribute="problem">
	
	<%-- Hidden properties--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="company" />
	<form:hidden path="attachments" />
	
	<%-- title--%>
	<acme:textbox code="problem.title" path="title" />
	<br>
	
	<%-- statement--%>
	<acme:textbox code="problem.statement" path="statement" />
	<br>
	
	<%-- hint--%>
	<acme:textbox code="problem.hint" path="hint" />
	<br>	

	<%-- finalMode --%>
	<form:label path="finalMode"><spring:message code="problem.finalMode" /></form:label>
	<form:select id="modeDropdown" path="finalMode">
		<form:option value="">--</form:option>
		<form:option value="0"><spring:message code="position.false" /></form:option>
		<form:option value="1"><spring:message code="position.true" /></form:option>
	</form:select>
	<form:errors class="error" path="finalMode" />
	<br>
	<br>
	
	<input type="submit" name="save" value="<spring:message code="problem.save"/>" />
	<acme:cancel code="company.cancel" url="/problem/company/list.do" />
</form:form>
<br>
<jstl:if test="${not empty problem.attachments}">
<display:table name="problem.attachments"  id="row" >
	<spring:message code="problem.attachment" var="attachmentNameHeader" />
	<display:column title="${attachmentNameHeader}" property="link" sortable="false" />
		
	<spring:message code="problem.deleteAttachment" var="deleteHeader" />
	<display:column title="${deleteHeader}">
		<a href="problem/company/deleteAttachment.do?link=${row.link}&problemId=${problem.id}"><spring:message code="problem.deleteAttachment"/></a>
	</display:column>
	
<display:caption><spring:message code="problem.attachments"/></display:caption>
</display:table>
</jstl:if>
<br>
<a href="problem/company/addAttachment.do?problemId=${problem.id}">
		<spring:message code="problem.addAttachment"/>
</a>