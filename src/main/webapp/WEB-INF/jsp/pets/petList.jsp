<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<petclinic:layout pageName="pets">
    <h2>Mis mascotas</h2>

	<c:if test="${pets.size() == 0}">No existen mascotas registradas.</c:if>

	<c:if test="${pets.size() != 0}">
	    <table id="petsTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th style="width: 150px;">Nombre</th>
	            <th style="width: 250px;">Tipo</th>
	            <th style="width: 250px;">Fecha de nacimiento</th>
	            <th style="width: 200px">Propietario</th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${pets}" var="pet">
	            <tr>
	                <td>
	                    <spring:url value="/owners/pet/{petId}" var="petUrl">
	                        <spring:param name="petId" value="${pet.id}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(petUrl)}"><c:out value="${pet.name}"/></a>
	                </td>
	                <td>
	                    <c:out value="${pet.type}"/>
	                </td>
	                <td>
	                    <c:out value="${pet.birthDate}"/>
	                </td>
	                <td>
	                    <c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/>
	                </td>
	                
	      
	<!--
	                <td> 
	                    <c:out value="${owner.user.username}"/> 
	                </td>
	                <td> 
	                   <c:out value="${owner.user.password}"/> 
	                </td> 
	-->
	                
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </c:if>
</petclinic:layout>
