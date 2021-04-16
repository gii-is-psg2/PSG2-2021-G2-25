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

</petclinic:layout>
