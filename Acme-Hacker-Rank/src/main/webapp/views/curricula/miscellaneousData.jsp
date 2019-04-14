<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="curricula/miscellaneousData/hacker/edit.do" modelAttribute="miscellaneousData">
	
	<%-- Hidden properties from miscellaneousData--%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curricula" />

	<%-- text --%>
	<acme:textbox code="curricula.text" path="text" />
	<br>

	<%-- Buttons --%>
	<input type="submit" name="save" value="<spring:message code="curricula.save"/>" />
	<acme:cancel code="curricula.cancel" url="curricula/hacker/display.do?curriculaId=${personalData.curricula.id}" />
</form:form>
<br>
<jstl:if test="${not empty miscellaneousData.attachments}">
<display:table name="miscellaneousData.attachments"  id="row" >
	<spring:message code="miscellaneousData.attachment" var="attachmentNameHeader" />
	<display:column title="${attachmentNameHeader}" property="link" sortable="false" />
		
	<spring:message code="miscellaneousData.deleteAttachment" var="deleteHeader" />
	<display:column title="${deleteHeader}">
		<a href="miscellaneousData/company/deleteAttachment.do?link=${row.link}&miscellaneousDataId=${miscellaneousData.id}"><spring:message code="miscellaneousData.deleteAttachment"/></a>
	</display:column>
	
<display:caption><spring:message code="miscellaneousData.attachments"/></display:caption>
</display:table>
</jstl:if>
<br>
<a href="curricula/miscellaneousData/hacker/addAttachment.do?miscellaneousDataId=${miscellaneousData.id}">
		<spring:message code="miscellaneousData.addAttachment"/>
</a>