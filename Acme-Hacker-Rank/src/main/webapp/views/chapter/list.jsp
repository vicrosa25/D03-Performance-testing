<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="chapters" id="row" requestURI="${ requestUri }" pagesize="5" class="displaytag">

	<!-- Title -->
	<spring:message code="chapter.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	
	<!-- Area -->
	<display:column>
		<jstl:if test="${ row.area != null }">
			<a href="chapter/area/list.do?areaId=${row.area.id}"> <spring:message code="chapter.link.area" /></a>
		</jstl:if>
		<jstl:if test="${ row.area == null }">
			Not Area
		</jstl:if>
	</display:column>
	
	<!-- Proclaims -->
	<display:column>
		<jstl:if test="${ not empty row.proclaims }">
			<a href="chapter/proclaim/list.do?chapterId=${row.id}"> <spring:message code="chapter.link.proclaim" /></a>
		</jstl:if>
		<jstl:if test="${ empty row.proclaims }">
			Not Proclaims
		</jstl:if>
	</display:column>
	
	

</display:table>




