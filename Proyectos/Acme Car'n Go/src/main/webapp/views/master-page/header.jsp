<%--
 * header.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="welcome/index.do">
	<img src="images/logo.png" alt="Acme Car'n Go Co., Inc." height="180"/> </a><a href="?language=en">en</a> | <a href="?language=es">es</a>
	
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="isAuthenticated()">
		<li><a class="fNiv"><spring:message code="master.page.offers"/></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="post/listOffers.do"><spring:message code="master.page.offer.list"/></a>
				<security:authorize access="hasRole('CUSTOMER')">
					<li><a href="post/customer/postOffer.do"><spring:message code="master.page.offer.create"/></a> </li>
				</security:authorize>
			</ul>
		</li>
		<li><a class="fNiv"><spring:message code="master.page.requests"/></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="post/listRequests.do"><spring:message code="master.page.request.list"/></a>
				<security:authorize access="hasRole('CUSTOMER')">
					<li><a href="post/customer/postRequest.do"><spring:message code="master.page.request.create"/></a> </li>
				</security:authorize>
			</ul>
		</li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="customer/create.do"><spring:message code="master.page.register"/></a></li>	
		</security:authorize>


		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="folder/actor/list.do"><spring:message code="master.page.profile.folder.list" /></a></li>		
					<li><a href="message/actor/create.do"><spring:message code="master.page.profile.message.create" /></a></li>
					<li><a href="actor/display.do"><spring:message code="master.page.actor.display" /></a></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a><spring:message	code="master.page.administrator" /></a>
							<ul>
								<li class="arrow"></li>
								<li><a href="systemConfiguration/administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
								<li><a href="systemConfiguration/administrator/edit.do"><spring:message code="master.page.administrator.system" /></a></li>
							</ul>
						</li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>


