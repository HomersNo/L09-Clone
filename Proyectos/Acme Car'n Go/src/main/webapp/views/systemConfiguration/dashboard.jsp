<%--
 * action-1.jsp
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
		<th colspan = "2"><spring:message	code="systemConfiguration.requests.accepted" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.per.actor" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${avgAcceptedDeniedPerTenant[0]}</td>
		<td>${avgAcceptedDeniedPerLessor[0]}</td>
	</tr>
</tbody>
</table>
<br/>

<table>
<thead>
	<tr>
		<th colspan = "2"><spring:message	code="systemConfiguration.requests.denied" /></th>
	</tr>
	<tr>
		<th><spring:message	code="systemConfiguration.per.tenant" /></th>
		<th><spring:message	code="systemConfiguration.per.lessor" /></th>
	</tr>
</thead>
<tbody>
	<tr>
		<td>${avgAcceptedDeniedPerTenant[1]}</td>
		<td>${avgAcceptedDeniedPerLessor[1]}</td>
	</tr>
</tbody>
</table>
<br/>

<spring:message	code="systemConfiguration.customer.accepted.requests" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="customerByAcceptedRequest" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.customer.denied.requests" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="customersByDeniedRequest" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.actor.tenth.comments" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="actorsByComments" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.actor.more.sent.messages" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="actorsBySentMessages" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>

<spring:message	code="systemConfiguration.actor.more.received.messages" />
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="actorsByReceivedMessages" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="systemConfiguration.actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="systemConfiguration.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="systemConfiguration.actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true"/>
	
</display:table>
<br/>