<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<spring:message code="administrator.dashboard.avg" 	  var="avgHeader" />
<spring:message code="administrator.dashboard.min" 	  var="minHeader" />
<spring:message code="administrator.dashboard.max" 	  var="maxHeader" />
<spring:message code="administrator.dashboard.std" 	  var="stdHeader" />
<spring:message code="administrator.dashboard.ratio"  var="ratioHeader" />
<spring:message code="administrator.dashboard.count"  var="countHeader" />
<spring:message code="administrator.dashboard.name"   var="nameHeader" />
<spring:message code="administrator.dashboard.email"  var="emailHeader" />
<spring:message code="administrator.company"	 	  var="companyHeader" />
<spring:message code="administrator.hacker" 		  var="hackerHeader" />
<spring:message code="administrator.status" 		  var="statusHeader" />
<spring:message code="administrator.moment" 		  var="momentHeader" />
<spring:message code="administrator.positions" 		  var="positionsHeader" />
<spring:message code="administrator.applications" 	  var="applicationsHeader" />
<spring:message code="administrator.position.salary"  var="salaryHeader" />

<spring:message code="administrator.dashboard.query1" var="query1Header" />
<spring:message code="administrator.dashboard.query2" var="query2Header" />
<spring:message code="administrator.dashboard.query3" var="query3Header" />
<spring:message code="administrator.dashboard.query4" var="query4Header" />
<spring:message code="administrator.dashboard.query5" var="query5Header" />
<spring:message code="administrator.dashboard.query6" var="query6Header" />
<spring:message code="administrator.dashboard.query7" var="query7Header" />
<spring:message code="administrator.dashboard.query8" var="query8Header" />
<spring:message code="administrator.dashboard.query9" var="query9Header" />


<!--  Custom table style -->
<head>
	<link rel="stylesheet" href="styles/tablas.css" type="text/css">
	<link rel="stylesheet" href="styles/charts.css" type="text/css">
</head>



<!-- C level -->

<!-- Query 1 -->
<table>
	<caption>
		<jstl:out value="${query1Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${avgHeader}"></jstl:out></th>
		<th><jstl:out value="${minHeader}"></jstl:out></th>
		<th><jstl:out value="${maxHeader}"></jstl:out></th>
		<th><jstl:out value="${stdHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query1[0]}"></jstl:out></td>
		<td><jstl:out value="${query1[1]}"></jstl:out></td>
		<td><jstl:out value="${query1[2]}"></jstl:out></td>
		<td><jstl:out value="${query1[3]}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 2  -->
<table>
	<caption>
		<jstl:out value="${query2Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${avgHeader}"></jstl:out></th>
		<th><jstl:out value="${minHeader}"></jstl:out></th>
		<th><jstl:out value="${maxHeader}"></jstl:out></th>
		<th><jstl:out value="${stdHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query2[0]}"></jstl:out></td>
		<td><jstl:out value="${query2[1]}"></jstl:out></td>
		<td><jstl:out value="${query2[2]}"></jstl:out></td>
		<td><jstl:out value="${query2[3]}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 3  -->
<table>
	<caption>
		<jstl:out value="${query3Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${companyHeader}"></jstl:out></th>
		<th><jstl:out value="${positionsHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query3}" var="row">
      <tr>
        	<td>${row.commercialName}</td>
        	<td>${row.getPositions().size()}</td>
      </tr>
   </jstl:forEach>
</table>
<br />

<!-- Query 4  -->
<table>
	<caption>
		<jstl:out value="${query4Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${hackerHeader}"></jstl:out></th>
		<th><jstl:out value="${applicationsHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query4}" var="row">
      <tr>
        	<td>${row.name}</td>
        	<td>${row.getApplications().size()}</td>
      </tr>
   </jstl:forEach>
</table>
<br />

<!-- Query 5  -->
<table>
	<caption>
		<jstl:out value="${query5Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${avgHeader}"></jstl:out></th>
		<th><jstl:out value="${minHeader}"></jstl:out></th>
		<th><jstl:out value="${maxHeader}"></jstl:out></th>
		<th><jstl:out value="${stdHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query5[0]}"></jstl:out></td>
		<td><jstl:out value="${query5[1]}"></jstl:out></td>
		<td><jstl:out value="${query5[2]}"></jstl:out></td>
		<td><jstl:out value="${query5[3]}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 6  -->
<table>
	<caption>
		<jstl:out value="${query6Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${hackerHeader}"></jstl:out></th>
		<th><jstl:out value="${salaryHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query6a}" var="row">
      <tr>
        	<td>${row.title}</td>
        	<td>${row.salary}</td>
      </tr>
   </jstl:forEach>
   <tr>
		<th><jstl:out value="${hackerHeader}"></jstl:out></th>
		<th><jstl:out value="${salaryHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query6b}" var="row">
      <tr>
        	<td>${row.title}</td>
        	<td>${row.salary}</td>
      </tr>
   </jstl:forEach>
</table>


<!-- Query 7  -->
<table>
	<caption>
		<jstl:out value="${query7Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${avgHeader}"></jstl:out></th>
		<th><jstl:out value="${minHeader}"></jstl:out></th>
		<th><jstl:out value="${maxHeader}"></jstl:out></th>
		<th><jstl:out value="${stdHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query7[0]}"></jstl:out></td>
		<td><jstl:out value="${query7[1]}"></jstl:out></td>
		<td><jstl:out value="${query7[2]}"></jstl:out></td>
		<td><jstl:out value="${query7[3]}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 8 -->
<table>
	<caption>
		<jstl:out value="${query8Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${avgHeader}"></jstl:out></th>
		<th><jstl:out value="${minHeader}"></jstl:out></th>
		<th><jstl:out value="${maxHeader}"></jstl:out></th>
		<th><jstl:out value="${stdHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query8[0]}"></jstl:out></td>
		<td><jstl:out value="${query8[1]}"></jstl:out></td>
		<td><jstl:out value="${query8[2]}"></jstl:out></td>
		<td><jstl:out value="${query8[3]}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 9 -->
<table>
	<caption>
		<jstl:out value="${query9Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${ratioHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query9}"></jstl:out></td>
	</tr>
</table>
<br />









































