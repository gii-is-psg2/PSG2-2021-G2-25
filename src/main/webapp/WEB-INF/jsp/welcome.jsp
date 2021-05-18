<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">


</script>

<div id="content">
<petclinic:layout pageName="home">
    <h1><b><fmt:message key="welcome"/></b></h1>
    <h2><fmt:message key="description"/></h2>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/doge.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
    </div>
</petclinic:layout>
</div>