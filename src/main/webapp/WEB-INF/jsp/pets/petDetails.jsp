<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<petclinic:layout pageName="owners">
    <jsp:attribute name="customScript">
        <script>
	        function acceptAlert() {
	        	var opcion = confirm('¿Seguro que desea aceptar esta solicitud de adopción?');
	        	return opcion;
	        }        
	        function denyAlert() {
	        	var opcion = confirm('¿Seguro que desea rechazar esta solicitud de adopción?');
	        	return opcion;
	        }
	        function putInAdoptionAlert() {
	        	var opcion = confirm('¿Seguro que desea poner en adopción a esta mascota?');
	        	return opcion;
	        }
        </script>
    </jsp:attribute>
    <jsp:body>
	    <h2>Información de <c:out value="${pet.name}"></c:out></h2>
	
	    <table class="table table-striped">
	        <tr>
	            <th>Nombre</th>
	            <td><c:out value="${pet.name}"/></td>
	        </tr>
	        <tr>
	            <th>Tipo</th>
	            <td><c:out value="${pet.type}"/></td>
	        </tr>
	        <tr>
	            <th>Fecha de nacimiento</th>
	            <td><c:out value="${pet.birthDate}"/></td>
	        </tr>
	        <tr>
	            <th>Propietario</th>
	            <td><c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/></td>
	        </tr>
	    </table>
	    
	    <c:if test="${inAdoption}">
	    	<hr>	
	    	<h2>Solicitantes para la adopción</h2>
	    	
		    <table id="applicantsTable" class="table table-striped">
		        <thead>
		        <tr>
		            <th style="width: 100px;">Nombre</th>
		            <th style="width: 300px;">Descripción</th>
		            <th style="width: 50px"></th>
		        </tr>
		        </thead>
		        <tbody>
		        <c:forEach items="${applicants}" var="applicant">
		            <tr>
		                <td>
		                    <c:out value="${applicant.owner.firstName} ${applicant.owner.lastName}"/>
		                </td>
		                <td>
		                    <c:out value="${applicant.description}"/>
		                </td>
		                <td>
		                	<c:if test="${applicant.status == 'DENIED'}">
		                		Rechazado
		                	</c:if>
		                	<c:if test="${applicant.status != 'DENIED'}">
			                	<spring:url value="/adoptions/{petId}/{decision}/{applicantId}" var="acceptUrl">
									<spring:param name="petId" value="${applicant.pet.id}" />
									<spring:param name="applicantId" value="${applicant.owner.id}" />
									<spring:param name="decision" value="accept" />
								</spring:url> 
						        <a onclick="return acceptAlert()" style="text-decoration:none;padding:10px" href="${fn:escapeXml(acceptUrl)}">
						          <span  class="glyphicon glyphicon-ok"></span>
						        </a>
						        
			                	<spring:url value="/adoptions/{petId}/{decision}/{applicantId}" var="denyUrl">
									<spring:param name="petId" value="${applicant.pet.id}" />
									<spring:param name="applicantId" value="${applicant.owner.id}" />
									<spring:param name="decision" value="deny" />
								</spring:url> 
						        <a onclick="return denyAlert()" style="text-decoration:none;padding:10px" href="${fn:escapeXml(denyUrl)}">
						          <span class="glyphicon glyphicon-remove"></span>
						        </a>
					        </c:if>
						</td>
		            </tr>
		        </c:forEach>
		        </tbody>
		    </table>
	    </c:if>
	    
	    <c:if test="${!inAdoption}">
		<!--     Colocar botón de Dar en adopción -->    
			<spring:url value="/adoptions/{petId}" var="putInAdoptionUrl">
				<spring:param name="petId" value="${pet.id}" />
			</spring:url> 
			<a onclick="return putInAdoptionAlert()" class="btn btn-default" href="${fn:escapeXml(putInAdoptionUrl)}">
				Dar en adopción
			</a>
		</c:if>
	</jsp:body>
</petclinic:layout>