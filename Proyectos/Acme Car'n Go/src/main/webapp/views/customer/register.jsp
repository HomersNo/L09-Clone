<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI}" modelAttribute="register">

	<acme:textbox code="customer.useraccount.username" path="username"/>
	
    <acme:password code="customer.useraccount.password" path="password"/>
    
	<acme:textbox code="customer.name" path="name"/>
	
	<acme:textbox code="customer.surname" path="surname"/>
	
	<acme:textbox code="customer.email" path="email"/>
	
	<acme:textbox code="customer.phone" path="phoneNumber"/>
	
	<form:label path="accept" >
		<spring:message code="customer.terms" />
	</form:label>
	<form:checkbox path="accept" id="terms" onchange="javascript: toggleSubmit()"/>
	<form:errors path="accept" cssClass="error" />
	<br/>
	
	<button type="submit" name="save" class="btn btn-primary" id="save" disabled onload="javascript: toggleSubmit()">
		<spring:message code="customer.save" />
	</button>

	<acme:cancel url="index.do" code="customer.cancel"/>
	
	<script type="text/javascript">
	function toggleSubmit() {
		var accepted = document.getElementById("terms");
		if(accepted.checked){
			document.getElementById("save").disabled = false;
		} else{
			document.getElementById("save").disabled = true;
		}
	}
	</script>
	
</form:form>