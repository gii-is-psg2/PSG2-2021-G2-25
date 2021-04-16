<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">

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
				        <a style="text-decoration:none;padding:10px" href="">
				          <span class="glyphicon glyphicon-ok"></span>
				        </a>
				        
				        <a style="text-decoration:none;padding:10px" href="">
				          <span class="glyphicon glyphicon-remove"></span>
				        </a>
					</td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </c:if>
    
    <c:if test="${!inAdoption}">
	<!--     Colocar botón de Dar en adopción -->    
	</c:if>
</petclinic:layout>
