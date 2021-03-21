<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="bookings">
    <h2>My bookings</h2>

	<c:if test="${bookings.size() == 0}">
		<p>Oops, there aren't bookings...!</p>
		<br>
	</c:if>
	<c:if test="${bookings.size() != 0}">
	    <table id="bookingsTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th>Pet Name</th>
	            <th>Pet Type</th>
	            <th>Initial Date</th>
	            <th>End Date</th>
	            <th>Room</th>
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
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </c:if>
	<a style="text-decoration:none;" href="/bookings/new">
		<button class="btn btn-default" type="submit">Create a new booking</button>
	</a>
</petclinic:layout>