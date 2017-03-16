<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="applications" requestURI="${requestURI}" id="row">
	
	
	<!-- Attributes -->
	<spring:message code="application.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}" sortable="true" />

	<spring:message code="application.post" var="postHeader" />
	<display:column  title="${postHeader}" sortable="false" >
		<a href="post/customer/display.do?postId=${row.post.id}" ><jstl:out value="${row.post.title}" /></a>
	</display:column>

	<spring:message code="application.customer" var="customerHeader" />
	<display:column title="${customerHeader}" sortable="false" >
		<a href="customer/display.do?customerId=${row.customer.id}"> <jstl:out value="${row.customer.name} ${row.customer.surname}" /></a>
	</display:column>
	
	<jstl:if test="${requestURI eq 'application/customer/listReceived.do' }"></jstl:if>

</display:table>

