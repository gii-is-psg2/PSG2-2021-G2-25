<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@	page contentType="text/html" pageEncoding="UTF-8"%>


<petclinic:layout pageName="causes">
    <h2>Lista de causas</h2>
    
    <spring:url value="/causes" var="activedUrl">
    </spring:url>
	<a class="btn btn-default" href="${fn:escapeXml(activedUrl)}">En activo</a>
	
    <spring:url value="/causes/inactive" var="completedUrl">
    </spring:url>
    <a class="btn btn-default" href="${fn:escapeXml(completedUrl)}">Completadas</a>
    
    <table id="causesTable" class="table table-striped">
        <thead>
        
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 120px">Total recaudado (&euro;)</th>
            <th style="width: 120px">Objetivo recaudación (&euro;)</th>
            <th style="width: 120px">Acciones</th>  
              
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes}" var="cause">
                <tr>
                    <td>
                        <c:out value="${cause.name}"/>
                    </td>
                    <td>
                        <c:out value="${cause.totalAmount}"/>

                    </td>
                    <td>
                        <c:out value="${cause.budgetTarget}"/>
                    </td>
                    
                    <td>
                        <spring:url value="/causes/{causeId}" var="detailsCause">
                            <spring:param name="causeId" value="${cause.id}"/>
                        </spring:url>
                        <a class="btn btn-default"  href="${fn:escapeXml(detailsCause)}">Detalles</a>
                        <c:if test="${cause.targetNotReached eq true}">
                            
                                <spring:url value="/donation/{causeId}/new" var="donationUrl">
                                    <spring:param name="causeId" value="${cause.id}"/>
                                </spring:url>
                                
                                <a class="btn btn-default"  href="${fn:escapeXml(donationUrl)}">Donar</a>
                               <sec:authorize access="hasAuthority('owner')">
                               <spring:url value="/causes/{causeId}/delete" var="causeUrl">
                                    <spring:param name="causeId" value="${cause.id}"/>
                                </spring:url>
                                
                                <c:if test="${cause.owner.user.username eq username}">
                                
                                
                                	<a class="btn btn-default"  href="${fn:escapeXml(causeUrl)}">Eliminar</a>
                                
                                	<spring:url value="/causes/{causeId}/edit" var="causeUrl">
                                    	<spring:param name="causeId" value="${cause.id}"/>
                                	</spring:url>
                                
                                	<a class="btn btn-default"  href="${fn:escapeXml(causeUrl)}">Editar</a>
                                	
                                 </c:if>  
                                 
                            </sec:authorize>
                        </c:if>

                    </td>
                </tr>
        </c:forEach>
        </tbody>
    </table>
    <sec:authorize access="hasAuthority('owner')">
    <spring:url value="/causes/new" var="newCause">
    	</spring:url>
	<a class="btn btn-default" href="${fn:escapeXml(newCause)}">Añadir causa</a>
	</sec:authorize>
</petclinic:layout>