<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="bookings">
    <jsp:attribute name="customScript">
        <script type="text/javascript">
        	$(function() {
        		$("#datepicker").datepicker( {
        		    format: "dd/mm/yy",
        		    minViewMode: "days"
        		});
        		$("#datepicker1").datepicker( {
        		    format: "dd/mm/yy",
        		    minViewMode: "days"
        		});
            });
        </script>
		
	</jsp:attribute>
	<jsp:body>

		<c:if test="${!booking['new']}">
			<div style="margin-top:3%;text-align:center">
				<h2 style="padding: 30px">Selecciona una mascota para reservar una habitación</h2>
				
				<form:form modelAttribute="pets" action="/bookings/new" class="form-horizontal">
					<select style="margin-bottom:30px" name="id" title="Select a pet">
						<c:forEach items="${pets}" var="pet">
							<option value="${pet.id}"><c:out value="${pet} (${pet.type})"></c:out></option>
						</c:forEach>
					</select>
				    <div class="form-group">
				    	<button class="btn btn-default" type="submit">Continuar</button>
					</div>
				</form:form>
			</div>
		</c:if>
	
		<c:if test="${booking['new']}">
			<div style="margin-top:3%;text-align:center">
			    <h2 style="padding: 30px">Crear una reserva</h2>
			
			    <form:form modelAttribute="booking" action="/bookings/new/${petId}" class="form-horizontal">
			        <div class="form-group has-feedback" style="margin-right: 3%;margin-top:-2%">
			            <label for="room">Room:&nbsp;&nbsp;&nbsp;&nbsp;</label>
						<select name="room" title="Select a room">
				        	<c:forEach begin="1" end="99" varStatus="room">
				    			<option value="${room.count}"><c:out value="${room.count}"/></option>
				    		</c:forEach>
						</select>
						<br>
			            <label style="margin-top:10px" for="initDate">Fecha de inicio:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
						<input style="width:12%;margin-left:46%" type="text" class="form-control"  id="datepicker" name="initDate" value="${booking.initDate}" />
			            <label style="margin-top:10px" for="endDate">Fecha de fin:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
						<input style="width:12%;margin-left:46%" type="text" class="form-control"  id="datepicker1" name="endDate" value="${booking.endDate}" />
			        </div>
			        <div class="form-group">
			        	<button class="btn btn-default" type="submit">Crear</button>
			        </div>
			    </form:form>
		    </div>
	    </c:if>
	</jsp:body>    
</petclinic:layout>
