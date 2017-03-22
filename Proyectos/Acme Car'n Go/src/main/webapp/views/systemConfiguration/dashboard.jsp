<%--
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<table>
<thead>
	<tr>
		<th colspan = "1"><spring:message	code="systemConfiguration.ratio.posts" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.ratio.posts" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${ratioOffersVsRequests}</td>
	</tr>
</tbody>
</table>
<br/>

<table>
<thead>
	<tr>
		<th colspan = "2"><spring:message	code="systemConfiguration.posts.averages" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.average.posts.customer" /></th>
		<th><spring:message	code="systemConfiguration.average.applications.post" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${avgPostsPerCustomer}</td>
		<td>${avgApplicationsPerPost}</td>
	</tr>
</tbody>
</table>
<br/>

<table>
<thead>
	<tr>
		<th colspan = "2"><spring:message	code="systemConfiguration.comments.averages" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.average.comments.commentable" /></th>
		<th><spring:message	code="systemConfiguration.average.comments.actor" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${avgCommentsPerCommentable}</td>
		<td>${avgCommentsPerActor}</td>
	</tr>
</tbody>
</table>
<br/>

<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.messages.sent.stats" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.messages.sent.min" /></th>
		<th><spring:message	code="systemConfiguration.messages.sent.avg" /></th>
		<th><spring:message	code="systemConfiguration.messages.sent.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${minSentMessagesPerActor}</td>
		<td>${avgSentMessagesPerActor}</td>
		<td>${maxSentMessagesPerActor}</td>
	</tr>
</tbody>
</table>
<br/>

<table>
<thead>
	<tr>
		<th colspan = "3"><spring:message	code="systemConfiguration.messages.received.stats" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.messages.received.min" /></th>
		<th><spring:message	code="systemConfiguration.messages.received.avg" /></th>
		<th><spring:message	code="systemConfiguration.messages.received.max" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${minReceivedMessagesPerActor}</td>
		<td>${avgReceivedMessagesPerActor}</td>
		<td>${maxReceivedMessagesPerActor}</td>
	</tr>
</tbody>
</table>
<br/>

<spring:message	code="systemConfiguration.customer.accepted.requests" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="customersWithMoreAccepted" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.customer.denied.requests" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="customersWithMoreDenied" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.actor.tenth.comments" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="actorsWith10PercentAvg" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.actor.more.sent.messages" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="actorWithMoreSentMessages" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.actor.more.received.messages" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="actorWithMoreReceivedMessages" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phoneNumber" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<input type="button" name="back" value="<spring:message code="systemConfiguration.back"/>" 
		onclick="javascript: window.location.replace('welcome/index.do')"/>