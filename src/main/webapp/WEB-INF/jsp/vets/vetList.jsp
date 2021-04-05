<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="vets">
    <h2>Veterinarios/as</h2>


    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Especialidad</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <c:out value="${vet.firstName} ${vet.lastName}"/>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                </td>
                    <sec:authorize access="hasAuthority('admin')">
                    
                <td>
                	<spring:url value="/vet/{vetId}/edit" var="editUrl">
        				<spring:param name="vetId" value="${vet.id}"/>
    				</spring:url>
                	<a href="${editUrl}" class="btn btn-default">Editar</a>
            	</td>
              		<td><spring:url value="vets/{vetId}/delete" var="deleteUrl">
							<spring:param name="vetId" value="${vet.id}" />
						</spring:url> <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Borrar</a>
					</td>
						</sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">Ver como XML</a>
            </td>            
        </tr>
    </table>
    
    <br/>  
    <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/vets/new" htmlEscape="true"/>'>Añadir veterinario</a>
	</sec:authorize>

</petclinic:layout>
