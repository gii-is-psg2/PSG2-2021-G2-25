<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="adoptions">
    <h2>
        Envío de solicitud de adopción
    </h2>
                    <form:form modelAttribute="adoptionsApplication" class="form-horizontal" id="adoptions-form">
        				<div class="form-group has-feedback">
            				<petclinic:inputField label="Descripción" name="description"/>
        				</div>
    					<div class="form-group">
            				<div class="col-sm-offset-2 col-sm-10">
                        		<button class="btn btn-default" type="submit">Enviar solicitud</button>
            				</div>
       					 </div>
        			</form:form>
</petclinic:layout>