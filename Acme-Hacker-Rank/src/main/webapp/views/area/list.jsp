<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="areas" id="row" requestURI="${ requestUri }" pagesize="5" class="displaytag">

	<!-- Edit -->
   	<security:authorize access="hasRole('ADMIN')">
   		<spring:message code="area.edit" var="editHeader" />
        <display:column title="${ editHeader }">
          <a href="area/administrator/edit.do?areaId=${row.id}"><spring:message code="area.edit"/></a>
        </display:column>
   	</security:authorize>
	
	<!-- Title -->
	<spring:message code="area.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />
	
	<!-- Brotherhoods -->
	<spring:message code="area.bros" var="brosHeader" />
	<display:column title="${brosHeader}">
		<jstl:forEach var="bro" items="${row.brotherhoods}" varStatus="loop">
			${bro.title}${!loop.last ? ',' : ''}&nbsp
		</jstl:forEach>
	</display:column>
	
	
	<!-- Delete -->
	<security:authorize access="hasRole('ADMIN')">
	 <spring:message code="area.delete" var="deleteHeader" />
	 <display:column title="${ deleteHeader }">
	 	<jstl:if test="${empty row.brotherhoods}">
          <a href="area/administrator/delete.do?areaId=${row.id}"><spring:message code="area.delete"/></a>
        </jstl:if>
        <jstl:if test="${not empty row.brotherhoods}">
       	   <spring:message code="area.steady" var="steadyHeader" />
           <jstl:out value="${steadyHeader}" />
        </jstl:if>
     </display:column>
    </security:authorize>
     
</display:table>

<!-- Create Link -->
<security:authorize access="hasRole('ADMIN')">
	<a href=area/administrator/create.do><spring:message code="area.create" /></a>
</security:authorize>
