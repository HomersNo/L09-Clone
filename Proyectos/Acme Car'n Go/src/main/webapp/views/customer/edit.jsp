<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="customer">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" /> 

	
	<acme:textbox code="customer.name" path="name"/>
	
	<acme:textbox code="customer.surname" path="surname"/>
	
	<acme:textbox code="customer.email" path="email"/>
	
	<acme:textbox code="customer.phone" path="phoneNumber"/>
	
	<br>
	<input type="submit" name="save"
		value="<spring:message code="customer.save" />" 
		onclick="location.href = 'customer/display.do';" />&nbsp; 
	
	<jstl:if test="${customer.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="customer.delete" />" />&nbsp; 
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="customer.cancel" />"
		onclick="location.href = 'welcome/index.do';" />&nbsp;
	<br />
	
</form:form>
