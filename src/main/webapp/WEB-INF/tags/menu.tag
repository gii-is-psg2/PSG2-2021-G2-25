<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Alternar navegación</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<sec:authorize access="hasAuthority('admin')">
					<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
						title="Encontrar propietarios/as">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						<span>Encontrar propietarios/as</span>
					</petclinic:menuItem>
				</sec:authorize>
				<petclinic:menuItem active="${name eq 'vets'}" url="/vets"
					title="Veterinarios/as">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Veterinarios/as</span>
				</petclinic:menuItem>
				
				<sec:authorize access="hasAuthority('owner')">
					<petclinic:menuItem active="${name eq 'bookings'}" url="/bookings/"
						title="Bookings">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Reservas</span>
					</petclinic:menuItem>
				
					<petclinic:menuItem active="${name eq 'pets'}" url="/owners/pets"
						title="My pets">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Mis mascotas</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'adoptions'}" url="/adoptions/list"
						title="Adopciones">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Adopciones</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<petclinic:menuItem active="${name eq 'causes'}" url="/causes/"
					title="Donar a causas">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Donar a causas</span>
				</petclinic:menuItem>
				
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Acceder</a></li>
					<li><a href="<c:url value="/users/new" />">Registrarse</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Cerrar sesión</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
						</ul></li>
				</sec:authorize>
			</ul>
		</div>
	</div>
</nav>
