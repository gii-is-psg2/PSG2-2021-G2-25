<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@	page contentType="text/html" pageEncoding="UTF-8"%>

<petclinic:layout pageName="detailsCause">

    <h2>Detalles de la causa</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${cause.name}"/></b></td>
        </tr>
        <tr>
            <th>Descripción</th>
            <td><c:out value="${cause.description}"/></td>
        </tr>
        <tr>
            <th>Organización beneficiaria</th>
            <td><c:out value="${cause.ong}"/></td>
        </tr>
       <tr>
       		<th>Objetivo recaudación (&euro;)</th>
       		 <td>
                    <c:out value="${cause.budgetTarget}"/>
             </td> 
       </tr>
        <tr>
            <th>Total recaudados (&euro;)</th>
            <td>
                    <c:out value="${cause.totalAmount}"/>

                </td>
               
        </tr>
        <tr>
        	<th>Causa activa</th>
                <td>
                    <c:choose>
                        <c:when test="${cause.targetNotReached eq true}">
                            Si
                        </c:when>
                        <c:otherwise>
                            No
                        </c:otherwise>
                    </c:choose>
                </td>
        </tr>
    </table>

    <c:if test="${cause.targetNotReached eq true}">
        <sec:authorize access="hasAuthority('owner')">
            <spring:url value="/causes/{causeId}/newDonation" var="addUrl">
                <spring:param name="causeId" value="${cause.id}"/>
            </spring:url>
            <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Añadir donación</a>
        </sec:authorize>
    </c:if>

  	<spring:url value="/causes" var="back"/>
    <a href="${fn:escapeXml(back)}" class="btn btn-default">Volver</a>
    <br/>
    <br/>
    <br/>
<%--    <h2>Donaciones</h2>

     <table class="table table-striped">
        <c:forEach var="donation" items="${cause.donations}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                    	<dt>Cliente</dt>
                    	<dd><c:out value="${donation.client}"/></dd>
                        <dt>Cantidad</dt>
                        <dd><c:out value="${donation.quantity}"/></dd>
                        <dt>Fecha de la donaci&oacuten</dt>
                        <dd><c:out value="${donation.dateOfDonation}"/></dd>
                        <td>
                            <dl>                              
                                <dt>
                                    <spring:url value="/causes/{causeId}/donation/{donationId}" var="donationDetailsUrl">
                                        <spring:param name="causeId" value="${cause.id}"/>
                                        <spring:param name="donationId" value="${donation.id}"/>
                                    </spring:url>   
                                    <a href="${fn:escapeXml(donationDetailsUrl)}" class="btn btn-default">Detalles de donacion</a>
                                </dt>
                                <dt>&nbsp;</dt>
                            </dl>    
                        </td>                      
                    </dl>
                <td valign="top">
                    
                </td>
            </tr>

        </c:forEach>
    </table> --%>

</petclinic:layout>