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
<spring:message code="administrator.brotherhood" 	  var="brotherhoodlHeader" />
<spring:message code="administrator.member" 		  var="memberHeader" />
<spring:message code="administrator.status" 		  var="statusHeader" />
<spring:message code="administrator.procession" 	  var="processionHeader" />
<spring:message code="administrator.moment" 		  var="momentHeader" />
<spring:message code="administrator.position" 		  var="positionHeader" />
<spring:message code="administrator.enrols" 		  var="enrolsHeader" />
<spring:message code="administrator.approved.request" var="aprovedRequestHeader" />
<spring:message code="administrator.total.request"    var="totalRequestHeader" />
<spring:message code="administrator.chapter"    	  var="chapterHeader" />
<spring:message code="administrator.sponsorships"     var="sponsorshipsHeader" />

<spring:message code="administrator.dashboard.query1" var="query1Header" />
<spring:message code="administrator.dashboard.query2" var="query2Header" />
<spring:message code="administrator.dashboard.query3" var="query3Header" />
<spring:message code="administrator.dashboard.query4" var="query4Header" />
<spring:message code="administrator.dashboard.query5" var="query5Header" />
<spring:message code="administrator.dashboard.query7" var="query7Header" />
<spring:message code="administrator.dashboard.query8" var="query8Header" />
<spring:message code="administrator.dashboard.query9" var="query9Header" />
<spring:message code="administrator.dashboard.query10" var="query10Header" />
<spring:message code="administrator.dashboard.query11" var="query11Header" />
<spring:message code="administrator.dashboard.query12" var="query12Header" />
<spring:message code="administrator.dashboard.query13" var="query13Header" />
<spring:message code="administrator.dashboard.query14" var="query14Header" />
<spring:message code="administrator.dashboard.query15" var="query15Header" />
<spring:message code="administrator.dashboard.query16" var="query16Header" />
<spring:message code="administrator.dashboard.query17" var="query17Header" />
<spring:message code="administrator.dashboard.query18" var="query18Header" />
<spring:message code="administrator.dashboard.query19" var="query19Header" />
<spring:message code="administrator.dashboard.query20" var="query20Header" />
<spring:message code="administrator.dashboard.query21" var="query21Header" />
<spring:message code="administrator.dashboard.query22" var="query22Header" />


<!--  Custom table style -->
<head>
	<link rel="stylesheet" href="styles/tablas.css" type="text/css">
	<link rel="stylesheet" href="styles/charts.css" type="text/css">
</head>



<!--  ACME PARADE Queries level C -->

<!-- Query 12 -->
<table>
	<caption>
		<jstl:out value="${query12Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${avgHeader}"></jstl:out></th>
		<th><jstl:out value="${minHeader}"></jstl:out></th>
		<th><jstl:out value="${maxHeader}"></jstl:out></th>
		<th><jstl:out value="${stdHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query12[0]}"></jstl:out></td>
		<td><jstl:out value="${query12[1]}"></jstl:out></td>
		<td><jstl:out value="${query12[2]}"></jstl:out></td>
		<td><jstl:out value="${query12[3]}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 13  -->
<table>
	<caption>
		<jstl:out value="${query13Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${brotherhoodlHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query13.title}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 14  -->
<table>
	<caption>
		<jstl:out value="${query14Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${brotherhoodlHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query14}" var="row">
      <tr>
        	<td>${row.title}</td>
      </tr>
   </jstl:forEach>
</table>
<br />


<!--  ACME PARADE Queries level B -->

<!-- Query 15  -->
<table>
	<caption>
		<jstl:out value="${query15Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${ratioHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query15}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 16 -->
<table>
	<caption>
		<jstl:out value="${query16Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${avgHeader}"></jstl:out></th>
		<th><jstl:out value="${minHeader}"></jstl:out></th>
		<th><jstl:out value="${maxHeader}"></jstl:out></th>
		<th><jstl:out value="${stdHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query16[0]}"></jstl:out></td>
		<td><jstl:out value="${query16[1]}"></jstl:out></td>
		<td><jstl:out value="${query16[2]}"></jstl:out></td>
		<td><jstl:out value="${query16[3]}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 17  -->
<table>
	<caption>
		<jstl:out value="${query17Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${chapterHeader}"></jstl:out></th>
	</tr>
	<jstl:if test="${ empty query17 }">
		<tr>
        	<td>There are not Chapters in this conditions</td>
      </tr>
	</jstl:if>
	<jstl:if test="${ empty query17 }">
		<jstl:forEach items="${query17}" var="row">
      		<tr>
        		<td>${row.title}</td>
      		</tr>
    </jstl:forEach>
	</jstl:if>
</table>
<br />


<!-- Query 18  -->
<table>
	<caption>
		<jstl:out value="${query18Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${ratioHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query18}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 19  -->

<table>
	<caption>
		<jstl:out value="${query19Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${ratioHeader}"></jstl:out></th>
		<th><jstl:out value="${statusHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query19}" var="row">
      <tr>
        <td><jstl:out value="${row[0]}"></jstl:out></td>
        <td><jstl:out value="${row[1]}"></jstl:out></td>
      </tr>
   </jstl:forEach>
</table>
<br />


<!--  ACME PARADE Queries level A -->


<!-- Query 20  -->
<table>
	<caption>
		<jstl:out value="${query20Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${ratioHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query20}"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 21 -->
<table>
	<caption>
		<jstl:out value="${query21Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${avgHeader}"></jstl:out></th>
		<th><jstl:out value="${minHeader}"></jstl:out></th>
		<th><jstl:out value="${maxHeader}"></jstl:out></th>
		<th><jstl:out value="${stdHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query21[0]}"></jstl:out></td>
		<td><jstl:out value="${query21[1]}"></jstl:out></td>
		<td><jstl:out value="${query21[2]}"></jstl:out></td>
		<td><jstl:out value="${query21[3]}"></jstl:out></td>
	</tr>
</table>
<br />


<!-- Query 22  -->

<table>
	<caption>
		<jstl:out value="${query22Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${nameHeader}"></jstl:out></th>
		<th><jstl:out value="${sponsorshipsHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query22}" var="row">
      <tr>
        <td><jstl:out value="${row[0]}"></jstl:out></td>
        <td><jstl:out value="${row[1]}"></jstl:out></td>
      </tr>
   </jstl:forEach>
</table>
<br />






<!-- A+ Charts -->
<canvas id="chartcanvas-4"></canvas>;
<br>
<br>
<br>
<br>
<canvas id="chartcanvas-1"></canvas>
<br>
<br>
<br>
<br>
<canvas id="chartcanvas-3"></canvas>
<br>
<br>
<br>
<br>
<canvas id="chartcanvas-2"></canvas>



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
		<th><jstl:out value="${brotherhoodlHeader}"></jstl:out></th>
		<th><jstl:out value="${memberHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query2}" var="row">
      <tr>
        	<td>${row.title}</td>
        	<td>${row.enrols.size()}</td>
      </tr>
   </jstl:forEach>
</table>
<br />

<!-- Query 3  -->
<table>
	<caption>
		<jstl:out value="${query3Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${brotherhoodlHeader}"></jstl:out></th>
		<th><jstl:out value="${memberHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query3}" var="row">
      <tr>
        	<td>${row.title}</td>
        	<td>${row.enrols.size()}</td>
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
		<th><jstl:out value="${ratioHeader}"></jstl:out></th>
		<th><jstl:out value="${statusHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query4[0]}"></jstl:out></td>
		<td><jstl:out value="Aproved"></jstl:out></td>
	</tr>
	<tr>
		<td><jstl:out value="${query4[1]}"></jstl:out></td>
		<td><jstl:out value="Pending"></jstl:out></td>
	</tr>
	<tr>
		<td><jstl:out value="${query4[2]}"></jstl:out></td>
		<td><jstl:out value="Rejected"></jstl:out></td>
	</tr>
</table>
<br />

<!-- Query 5  -->
<table>
	<caption>
		<jstl:out value="${query5Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${processionHeader}"></jstl:out></th>
		<th><jstl:out value="${momentHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query5}" var="row">
      <tr>
        	<td>${row.title}</td>
        	<td><fmt:formatDate value="${row.moment}" pattern="dd/MM/yyyy" /></td>
      </tr>
   </jstl:forEach>
</table>
<br />

<!-- Query 7  -->
<table>
	<caption>
		<jstl:out value="${query7Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${memberHeader}"></jstl:out></th>
		<th><jstl:out value="${aprovedRequestHeader}"></jstl:out></th>
		<th><jstl:out value="${totalRequestHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query7}" var="row">
      <tr>
        	<td>${row[0]}</td>
        	<td>${row[1]}</td>
        	<td>${row[2]}</td>
      </tr>
   </jstl:forEach>
</table>


<!-- Query 8  -->
<table>
	<caption>
		<jstl:out value="${query8Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${positionHeader}"></jstl:out></th>
		<th><jstl:out value="${memberHeader}"></jstl:out></th>
	</tr>
	<jstl:forEach items="${query8}" var="row">
      <tr>
        	<td>${row[0]}</td>
        	<td>${row[1]}</td>
      </tr>
   </jstl:forEach>
</table>


<!-- Query 9 -->
<table>
	<caption>
		<jstl:out value="${query9Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${ratioHeader}"></jstl:out></th>
		<th><jstl:out value="${countHeader}"></jstl:out></th>
		<th><jstl:out value="${avgHeader}"></jstl:out></th>
		<th><jstl:out value="${minHeader}"></jstl:out></th>
		<th><jstl:out value="${maxHeader}"></jstl:out></th>
		<th><jstl:out value="${stdHeader}"></jstl:out></th>
	</tr>

	<tr>
		<td><jstl:out value="${query9[0]}"></jstl:out></td>
		<td><jstl:out value="${query9[1]}"></jstl:out></td>
		<td><jstl:out value="${query9[2]}"></jstl:out></td>
		<td><jstl:out value="${query9[3]}"></jstl:out></td>
		<td><jstl:out value="${query9[4]}"></jstl:out></td>
		<td><jstl:out value="${query9[5]}"></jstl:out></td>
	</tr>
</table>
<br />


<!-- Query 10 -->
<table>
	<caption>
		<jstl:out value="${query10Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${minHeader}"></jstl:out></th>
		<th><jstl:out value="${maxHeader}"></jstl:out></th>
		<th><jstl:out value="${avgHeader}"></jstl:out></th>
		<th><jstl:out value="${stdHeader}"></jstl:out></th>
	</tr>

	<tr>
		<td><jstl:out value="${query10[0]}"></jstl:out></td>
		<td><jstl:out value="${query10[1]}"></jstl:out></td>
		<td><jstl:out value="${query10[2]}"></jstl:out></td>
		<td><jstl:out value="${query10[3]}"></jstl:out></td>
	</tr>
</table>
<br />


<!-- Query 11 -->
<table>
	<caption>
		<jstl:out value="${query11Header}"></jstl:out>
	</caption>
	<tr>
		<th><jstl:out value="${ratioHeader}"></jstl:out></th>
	</tr>
	<tr>
		<td><jstl:out value="${query11}"></jstl:out></td>
	</tr>
</table>
<br />


<script>
$(document).ready(function () {
	
	Chart.defaults.scale.ticks.beginAtZero = true;
	
	var ctx1 = $("#chartcanvas-1");
	var ctx2 = $("#chartcanvas-2");
	var ctx3 = $("#chartcanvas-3");
	var ctx4 = $("#chartcanvas-4");
	
	var data1 = {
		labels: ["Spammers", "Not Spammers"],
		datasets: [
			{
				label: "The percentage of spammers/not spammers",
				data: [${spammers}, ${notSpammers}],
				backgroundColor: [
					"#0000FF",
					"#FF0000"
				],
				borderColor: ["#000000"],
				borderWidth: [1]
			}
		]
	};
	
	var data2 = {
			labels: ["Average polarity"],
			datasets: [
				{
					label: "The average polarity of the actors",
					data: [${averagePolarity}],
					backgroundColor: [
						"#0000FF",
						"#FF0000"
					],
					borderColor: ["#000000"],
					borderWidth: [1]
				}
			]
		};
	
	var data3 = {
			labels: ["ratio", "count", "minimum", "maximum", "average", "standard deviation"],
			datasets: [
				{
					label: "Number of brotherhoods per area",
					data: [${query9[0]}, ${query9[1]}, ${query9[2]}, ${query9[3]}, ${query9[4]}, ${query9[5]}],
					backgroundColor: [
						"#0000FF",
						"#FF0000",
						"#ffcc66",
						"#6699ff",
						"#66ff66",
						"#ff66ff"
					],
					borderColor: ["#000000"],
					borderWidth: [1]
				}
			]
		};
	
	var data4 = {
			labels: ["average", "minimum", "maximum", "standard deviation"],
			datasets: [
				{
					label: "Number of members per brotherhood",
					data: [${query1[0]}, ${query1[1]}, ${query1[2]}, ${query1[3]}],
					backgroundColor: [
						"#0000FF",
						"#FF0000",
						"#ffcc66",
						"#6699ff"
					],
					borderColor: ["#000000"],
					borderWidth: [1]
				}
			]
		};
	
	
	var chart1 = new Chart(ctx1, {
		type: "pie",
		data: data1,
		options: {}
	});
	
	var chart2 = new Chart(ctx2, {
		type: "pie",
		data: data2,
		options: {}
	});
	
	var chart3 = new Chart(ctx3, {
		type: "bar",
		data: data3,
		options: {}
	});
	
	var chart4 = new Chart(ctx4, {
		type: "bar",
		data: data4,
		options: {}
	});
});
</script>






































