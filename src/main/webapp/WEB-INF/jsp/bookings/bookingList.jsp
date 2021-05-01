<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="bookings">
    <h2>Mis reservas</h2>

	<c:if test="${bookings.size() == 0}">
		<p>Oops, ¡no hay reservas!</p>
		<br>
	</c:if>
	<c:if test="${bookings.size() != 0}">
	    <table id="bookingsTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th>Nombre mascota</th>
	            <th>Tipo mascota</th>
	            <th>Fecha de inicio</th>
	            <th>Fecha de fin</th>
	            <th>Habitación</th>
	            <th></th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${bookings}" var="booking">
	            <tr>
	                <td>
	                    <c:out value="${booking.pet}"/>
	                </td>
	                <td>
	                    <c:out value="${booking.pet.type}"/>
	                </td>
	                <td>
	                    <c:out value="${booking.initDate}"/>
	                </td>
	                <td>
	                    <c:out value="${booking.endDate}"/>
	                </td>
	                <td>
	                    <c:out value="${booking.room}"/>
	                </td>
	                <td>
	                	<spring:url value="/bookings/delete/{bookingId}" var="deleteUrl">
							<spring:param name="bookingId" value="${booking.id}" />
						</spring:url> 
						<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Borrar reserva</a>
	                </td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </c:if>
	<a style="text-decoration:none;" href="/bookings/new">
		<button class="btn btn-default" type="submit">Realizar una nueva reserva</button>
	</a>
</petclinic:layout>