<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="adoptions">
    <h2>Mascotas en adopción</h2>


    <table id="adoptionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre mascota</th>
            <th>Dueño</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pets}" var="pet">
            <tr>
                <td>
                    <c:out value="${pet.pet.name}"/>
                </td>
                <td>
                    <c:out value="${pet.pet.owner.firstName}  ${pet.pet.owner.lastName}"/>
                </td>
                    
                    <sec:authorize access="hasAuthority('owner')">
                    
              		<td><spring:url value="/adoptions/{petId}/sendRequest" var="requestUrl">
							<spring:param name="petId" value="${pet.pet.id}" />
						</spring:url> <a href="${fn:escapeXml(requestUrl)}" class="btn btn-default">Enviar solicitud de adopción</a>
					</td>
						</sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <br/>  

</petclinic:layout>