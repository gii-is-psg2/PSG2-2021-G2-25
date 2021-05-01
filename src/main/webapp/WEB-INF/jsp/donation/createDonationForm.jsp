<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donation">
	<div class = "page-body">
	    <h2 class = "page-title" id = "nuevo-dueno-title">
	        <c:if test="${donation['new']}"> Nueva </c:if> Donación
	    </h2>
	    <form:form modelAttribute="donation" class="form-horizontal" id="add-donation-form" action = "/donation/${causeId}/new">
	    	
	        <div class="form-group has-feedback">
	        	<fmt:message key="createOrUpdateDonationForm.label.client" var = "client"/>
	            <petclinic:inputField label="Cantidad" name="quantity"/>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                <c:choose>
	                    <c:when test="${donation['new']}">
	                        <button class="btn btn-default" type="submit">Añadir Donación</button>
	                    </c:when>
	                </c:choose>
	            </div>
	        </div>
	    </form:form>
    </div>
</petclinic:layout>
