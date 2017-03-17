<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="post/customer/edit.do" modelAttribute="post">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="origin"/>
	<form:hidden path="destination"/>
	
	<fieldset>
		<legend><spring:message code="post.info" /></legend>
		<acme:textbox code="post.type" path="type" readonly="true"/>
		
		<acme:textbox code="post.title" path="title"/>
		
		<acme:textbox code="post.description" path="description"/>
		
		
		<form:label path="moment">
			<spring:message code="post.moment" />:
		</form:label>
		<form:input placeholder="dd/MM/yyyy" path="moment" />
		<form:errors cssClass="error" path="moment" />
	
	</fieldset>
	
	<br/>
	<fieldset>
		<legend><spring:message code="post.origin" /></legend>
		<acme:textbox code="post.place.address" path="origin.address"/>
		<acme:number max="-85" min="85" step="any" code="post.place.latitude" path="origin.latitude"/>
		<acme:number max="-180" min="180" step="any" code="post.place.longitude" path="origin.longitude"/>
  	</fieldset>
  	
  	<fieldset>
		<legend><spring:message code="post.destination" /></legend>
		<acme:textbox code="post.place.address" path="destination.address"/>
		<acme:number max="-85" min="85" step="any" code="post.place.latitude" path="destination.latitude"/>
		<acme:number max="-180" min="180" step="any" code="post.place.longitude" path="destination.longitude"/>
  	</fieldset>
	
	
	
	<acme:submit name="save" code="post.save"/>
	<acme:cancel url="post/customer/list.do" code="post.cancel"/>
	
</form:form>