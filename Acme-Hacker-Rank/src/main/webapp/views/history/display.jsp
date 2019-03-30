<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<spring:message code="history.title" var="titleHeader" />
<spring:message code="history.description" var="descriptionHeader" />
<spring:message code="brotherhood.picture" var="pictureNameHeader" />
<spring:message code="history.pictures.view" var="viewPicturesHeader" />
<spring:message code="history.edit" var="editHeader" />
<spring:message code="coach.pictures.delete" var="deleteHeader" />
<spring:message code="procession.delete" var="deleteHeader" />

<!-- Inception Record -->
	<display:table name="history.inceptionRecord"  id="row" >
		<display:column property="title" title="${titleHeader}" sortable="false" />
		
		<display:column property="description" title="${desscriptionHeader}" sortable="false" />
		
	<display:caption><spring:message code="history.inceptionRecord"/></display:caption>
	</display:table>
	
<!-- Inception Pictures -->
	<jstl:if test="${not empty history.inceptionRecord.pictures}">
	<display:table name="history.inceptionRecord.pictures"  id="row" >
		
		<display:column title="${pictureNameHeader}" sortable="false" >
			<img src="${row.link}" width="25%" height="100"/>
		</display:column>

		<jstl:if test="${not empty bro}">	
		<display:column title="${deleteHeader}">
			<a href="history/inceptionRecord/brotherhood/deletePicture.do?link=${row.link}"><spring:message code="coach.picture.delete"/></a>
		</display:column>
		</jstl:if>
			
	<display:caption><spring:message code="brotherhood.pictures"/></display:caption>
	</display:table>
	</jstl:if>
	
<jstl:if test="${not empty bro}">
	<a href="history/inceptionRecord/brotherhood/edit.do">
		<spring:message code="history.inceptionRecord.edit"/>
	</a>
	|
	<%-- Añadir Foto --%>
	<a href="history/inceptionRecord/brotherhood/addPicture.do">
			<spring:message code="brotherhood.picture.create"/>
	</a>
	<br>
</jstl:if>
	
<!-- Period Records -->	
<br>
<jstl:if test="${not empty history.periodRecords}">
<display:table name="history.periodRecords"  id="row" >
	<display:column property="title" title="${titleHeader}" sortable="false" />
		
	<display:column property="description" title="${desscriptionHeader}" sortable="false" />
	
	<spring:message code="history.startYear" var="startYearHeader" />
	<display:column property="startYear" title="${startYearHeader}" sortable="false" />

	<spring:message code="history.endYear" var="endYearHeader" />
	<display:column property="endYear" title="${endYearHeader}" sortable="false" />
	

	<display:column  title="${viewPicturesHeader}" sortable="false">
			<jstl:if test="${not empty row.pictures}">
			<a href="history/periodRecord/pictures.do?periodId=${row.id}">
				<spring:message code="history.pictures.view" />
			</a>
			</jstl:if>
	</display:column>

	
	<jstl:if test="${not empty bro}">
	<display:column  sortable="false">
			<a href="history/periodRecord/pictures.do?periodId=${row.id}">
				<spring:message code="history.pictures.manage" />
			</a>
	</display:column>
	</jstl:if>
	
	<jstl:if test="${not empty bro}">
	<display:column >
		<a href="history/periodRecord/brotherhood/edit.do?periodId=${row.id}">
			<spring:message code="history.edit"/>
		</a>
	</display:column>
	<display:column title="${deleteHeader}">
		<a href="history/periodRecord/brotherhood/delete.do?periodId=${row.id}"><spring:message code="record.delete"/></a>
	</display:column>
	</jstl:if>
	
<display:caption><spring:message code="history.periodRecords"/></display:caption>
</display:table>
</jstl:if>
	
<jstl:if test="${not empty bro}">
	<a href="history/periodRecord/brotherhood/create.do">
		<spring:message code="history.periodRecord.create"/>
	</a>
	<br>
</jstl:if>
	
	
<!-- Legal Records -->	
<br>
<jstl:if test="${not empty history.legalRecords}">
<display:table name="history.legalRecords"  id="row" >
	<display:column property="title" title="${titleHeader}" sortable="false" />
		
	<display:column property="description" title="${desscriptionHeader}" sortable="false" />
	
	<spring:message code="history.legalName" var="legalNameHeader" />
	<display:column property="legalName" title="${legalNameHeader}" sortable="false" />

	<spring:message code="history.vat" var="VatHeader" />
	<display:column property="vat" title="${VatHeader}" sortable="false" />
	
	<spring:message code="history.laws" var="lawsHeader" />
	<display:column property="laws" title="${lawsHeader}" sortable="false" />
	
	<jstl:if test="${not empty bro}">
	<display:column title="${editHeader}">
		<a href="history/legalRecord/brotherhood/edit.do?legalId=${row.id}">
			<spring:message code="history.edit"/>
		</a>
	</display:column>
	<display:column title="${deleteHeader}">
		<a href="history/legalRecord/brotherhood/delete.do?legalId=${row.id}"><spring:message code="record.delete"/></a>
	</display:column>
	</jstl:if>
	
<display:caption><spring:message code="history.legalRecords"/></display:caption>
</display:table>
</jstl:if>
	
<jstl:if test="${not empty bro}">
	<a href="history/legalRecord/brotherhood/create.do">
		<spring:message code="history.legalRecord.create"/>
	</a>
	<br>
</jstl:if>
	
	
<!-- Link Records -->	
<br>
<jstl:if test="${not empty history.linkRecords}">
<display:table name="history.linkRecords"  id="row" >
	<display:column property="title" title="${titleHeader}" sortable="false" />
		
	<display:column property="description" title="${desscriptionHeader}" sortable="false" />
	
	<spring:message code="brotherhood.title" var="titleLinkHeader" />
	<display:column property="link.title" title="${titleLinkHeader}" sortable="false" />

	<display:column>
		<a href="brotherhood/history/display.do?brotherhoodId=${row.link.id}">
			<spring:message code="brotherhood.history" />
		</a>
	</display:column>
	
	<jstl:if test="${not empty bro}">
	<display:column title="${editHeader}">
		<a href="history/linkRecord/brotherhood/edit.do?linkId=${row.id}">
			<spring:message code="history.edit"/>
		</a>
	</display:column>
	<display:column title="${deleteHeader}">
		<a href="history/linkRecord/brotherhood/delete.do?linkId=${row.id}"><spring:message code="record.delete"/></a>
	</display:column>
	</jstl:if>
<display:caption><spring:message code="history.linkRecords"/></display:caption>
</display:table>
</jstl:if>
	
<jstl:if test="${not empty bro}">
	<a href="history/linkRecord/brotherhood/create.do">
		<spring:message code="history.linkRecord.create"/>
	</a>
	<br>
</jstl:if>
	
	
<!-- Misc Records -->	
<br>
<jstl:if test="${not empty history.miscellaneousRecords}">
<display:table name="history.miscellaneousRecords"  id="row" >
	<display:column property="title" title="${titleHeader}" sortable="false" />
		
	<display:column property="description" title="${desscriptionHeader}" sortable="false" />
	
	<jstl:if test="${not empty bro}">
	<display:column title="${editHeader}">
		<a href="history/miscellaneousRecord/brotherhood/edit.do?miscellaneousId=${row.id}">
			<spring:message code="history.edit"/>
		</a>
	</display:column>
	<display:column title="${deleteHeader}">
		<a href="history/miscellaneousRecord/brotherhood/delete.do?miscellaneousId=${row.id}"><spring:message code="record.delete"/></a>
	</display:column>
	</jstl:if>
		
<display:caption><spring:message code="history.miscellaneousRecords"/></display:caption>
</display:table>
</jstl:if>
	
<jstl:if test="${not empty bro}">
	<a href="history/miscellaneousRecord/brotherhood/create.do">
		<spring:message code="history.miscRecord.create"/>
	</a>
	<br>
</jstl:if>


<security:authorize access="isAnonymous()">
	<acme:back code="member.goback"/>
</security:authorize>