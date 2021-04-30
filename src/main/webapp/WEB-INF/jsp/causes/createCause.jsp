<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>


<petclinic:layout pageName="Causes">
    <jsp:body>
        <h2>Nueva Causa</h2>
         <c:choose>
        	<c:when test="${cause['new']}">
            	<c:set var="action" value="/causes/save"/>
            </c:when>
            <c:otherwise>
                <c:set var="action" value="/causes/${cause.id}/save"/>
            </c:otherwise>
       </c:choose> 
        
        <form:form modelAttribute="cause" class="form-horizontal" action="${action}">
			
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Nombre" name="name"/>
            	<petclinic:inputField label="Descripci&oacuten"  name="description"/>
            	<petclinic:inputField label="Organizaci&oacuten"  name="ong"/>
            	<spring:bind path="budgetTarget">
               		<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    				<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    				<div class="${cssGroup}">
        				<label class="col-sm-2 control-label">Objetivo (&euro;)</label>

        				<div class="col-sm-10">
        					<input type="number" name="budgetTarget" value="${cause.budgetTarget}" step=".01" min="0">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
           					<c:if test="${valid}">
               					<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
            				</c:if>
            				<c:if test="${status.error}">
                				<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                				<span class="help-inline">${status.errorMessage}</span>
            				</c:if>
        				</div>
    				</div>
               	</spring:bind>
            	

            </div>
              
              
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                	<input type="hidden" name="id" value="${cause.id}"/>
                <c:choose>
                	<c:when test="${cause['new']}">
                    		<button class="btn btn-default" type="submit">Crear causa</button>
                    </c:when>
                    <c:otherwise>
                    		<button class="btn btn-default" type="submit">Editar causa</button>
                    </c:otherwise>
                 </c:choose> 
                 
                 <spring:url value="/causes" var="causeUrl">
                 </spring:url>
                 <a class="btn btn-default" href="${fn:escapeXml(causeUrl)}">Atr&aacutes</a>
                
                </div>
              </div>
        </form:form>
    </jsp:body>
 
</petclinic:layout>