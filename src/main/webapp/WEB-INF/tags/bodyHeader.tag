<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<%@ attribute name="menuName" required="true" rtexprvalue="true"
              description="Nombre del menu activo: inicio, propietarios, veterinarios o error" %>

<petclinic:menu name="${menuName}"/>
