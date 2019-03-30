<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="history.title" var="titleHeader" />
<spring:message code="history.description" var="descriptionHeader" />
<spring:message code="brotherhood.picture" var="pictureNameHeader" />
<spring:message code="history.pictures.view" var="viewPicturesHeader" />
<spring:message code="history.edit" var="editHeader" />
<spring:message code="coach.pictures.delete" var="deleteHeader" />

<spring:message code="periodRecord.picturesFrom"/>${periodRecord.startYear}
<spring:message code="periodRecord.picturesTo"/>${periodRecord.endYear}

<display:table name="periodRecord.pictures"  id="row" requestURI="history/periodRecord/pictures.do" pagesize="5" class="displaytag">
		
	<display:column title="${pictureNameHeader}" sortable="false" >
		<img src="${row.link}" width="25%" height="100"/>
	</display:column>

	<jstl:if test="${not empty bro}">	
	<display:column title="${deleteHeader}">
		<a href="history/periodRecord/brotherhood/deletePicture.do?link=${row.link}&periodId=${row.targetId}"><spring:message code="coach.picture.delete"/></a>
	</display:column>
	</jstl:if>
			
	<display:caption><spring:message code="brotherhood.pictures"/></display:caption>
</display:table>

<jstl:if test="${not empty bro}">
	<a href="history/periodRecord/brotherhood/addPicture.do?periodId=${periodRecord.id }">
			<spring:message code="brotherhood.picture.create"/>
	</a>
</jstl:if>