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


<h1> ${folder.name}</h1>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="messages" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	
	<display:column>
		<a href="message/actor/delete.do?messageId=${row.id}">
			<spring:message code="message.delete" />
		</a>
	</display:column>
	
	<!-- Attributes -->
	
	<spring:message code="message.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="message.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="message.body" var="bodyHeader" />
	<display:column property="text" title="${bodyHeader}" sortable="false" />
	
	<spring:message code="message.attachment" var="attachmentHeader"/>
	<display:column title="${attachmentHeader}">
		<a href="${row.attachment}">${row.attachment}</a>
	</display:column> 
	
	
	
	<spring:message code="message.sender" var="senderHeader"/>
	<display:column title="${senderHeader}">
		<a href="actor/actor/display.do?actorId=${row.sender.id}"> ${row.sender.name} ${row.sender.surname}</a>
	</display:column>
	
	
	<spring:message code="message.recipient" var="recipientHeader"/>
	<display:column title="${recipientHeader}">
		<a href="actor/actor/display.do?actorId=${row.recipient.id }">${row.recipient.name } ${row.recipient.surname }</a>
	</display:column> 
	

</display:table>

<!-- Action links -->

<div>
	<a href="message/actor/create.do"> <spring:message
			code="message.create" />
	</a>
</div>
