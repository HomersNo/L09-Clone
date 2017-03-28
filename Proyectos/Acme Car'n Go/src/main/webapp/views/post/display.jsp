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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authentication property="principal" var ="loggedactor"/>

<h1><jstl:out value="${post.type}"/></h1>
<h2><jstl:out value="${post.title}"></jstl:out></h2>

<spring:message code="post.author.text" />: <a href="customer/display.do?customerId=${post.customer.id}" ><jstl:out value="${post.customer.name} ${post.customer.surname}"/> </a>
<p><jstl:out value="${post.description}"></jstl:out> </p>
<p><spring:message code="post.at" /> <jstl:out value="${post.moment}"/></p>

<p> <spring:message code="post.journey.from" />: <jstl:out value="${post.origin.address}" />.<jstl:if test="${not empty post.origin.latitude }"> <spring:message code="post.coordinates" />: <jstl:out value="(${post.origin.latitude},${post.origin.longitude})"/></jstl:if> </p>

<p> <spring:message code="post.journey.to" />: <jstl:out value="${post.destination.address}" />.<jstl:if test="${not empty post.destination.latitude }"> <spring:message code="post.coordinates" />: <jstl:out value="(${post.destination.latitude},${post.destination.longitude})"/></jstl:if> </p>

<security:authorize access="hasRole('CUSTOMER')">
<p><a href="application/customer/apply.do?postId=${post.id}"><b><spring:message code="post.apply"/></b></a></p>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<jstl:if test="${!(post.banned)}">
<p><a href="post/administrator/ban.do?postId=${post.id}"><b><spring:message code="post.ban"/></b></a></p>
</jstl:if>
</security:authorize>

<b><spring:message code="post.comments"/></b><br/>



<security:authorize access="hasRole('CUSTOMER')">


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="customerComments" requestURI="${requestURI }" id="row">

	<!--Attributes -->
	<spring:message code="comment.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />
	
	<spring:message code="comment.stars" var="starsHeader" />
	<display:column property="stars" title="${starsHeader}" sortable="true" />

	<spring:message code="comment.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"  format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="comment.actor" var="authorHeader"/>
	<display:column title="${authorHeader}">
		<a href="actor/actor/display.do?actorId=${row.actor.id}"> ${row.actor.name} ${row.actor.surname}</a>
	</display:column>
	
</display:table>

	
</security:authorize>

<security:authorize access="hasRole('ADMIN')">

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="adminComments" requestURI="${requestURI }" id="row">

	<!--Attributes -->
	<spring:message code="comment.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />
	
	<spring:message code="comment.stars" var="starsHeader" />
	<display:column property="stars" title="${starsHeader}" sortable="true" />

	<spring:message code="comment.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"  format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="comment.actor" var="authorHeader"/>
	<display:column title="${authorHeader}">
		<a href="actor/actor/display.do?actorId=${row.actor.id}"> ${row.actor.name} ${row.actor.surname}</a>
	</display:column>
	
	<spring:message code="comment.ban" var="banHeader"/>
	<display:column title="${banHeader}">
	<jstl:if test="${row.banned eq false}">
		<a href="comment/administrator/ban.do?commentId=${row.id}"> ban</a>
	</jstl:if>
	</display:column>
	
</display:table>
	
</security:authorize>

<br/>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="comment/actor/create.do?commentableId=${post.id}"><spring:message code="comment.new" /></a>
</security:authorize>
