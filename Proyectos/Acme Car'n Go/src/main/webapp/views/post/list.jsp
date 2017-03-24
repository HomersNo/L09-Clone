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
<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="post/filter.do" modelAttribute="filterString">
	<acme:textbox code="post.filter" path="filter"/>
	
	<acme:submit name="filterButton" code="post.search"/>
</form:form>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="posts" requestURI="${requestURI}" id="row">
	
	
	<!-- Attributes -->
	<acme:column code="post.title" path="title"/>

	<acme:column code="post.description" path="description"/>

	<spring:message code="post.moment" var="momentHeader" />
	<display:column title="${momentHeader}" sortable="false" property ="moment" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<acme:column code="post.origin" path="origin.address"/>
	
	<acme:column code="post.destination" path="destination.address"/>
	
	<display:column >
		<a href="post/display.do?postId=${row.id}"><spring:message code="post.display" /></a>
	</display:column>
	
	<display:column>
		<a href="customer/display.do?customerId=${row.customer.id}">${row.customer.name} ${row.customer.surname}</a>
	</display:column>

</display:table>

