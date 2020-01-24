<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:set var="logined" value="<%=session.getAttribute(\"loginedUser\") %>" />
<div class="bs">
	<nav class="navbar navbar-expand-md navbar-dark bg-dark">
		<a href="${pageContext.request.contextPath}/" class="navbar-brand">My application</a>
		<button type="button" class="navbar-toggler" data-toggle="collapse"
			data-target="#navbarCollapse">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarCollapse">
			<div class="navbar-nav">
				<a href="${pageContext.request.contextPath}/"
					class="nav-item nav-link active">Home</a> 
					<a href="${pageContext.request.contextPath}/login" class="nav-item nav-link">Login</a> 
					<a href="${pageContext.request.contextPath}/" class="nav-item nav-link">Something new</a>
			</div>
			<c:if test="${logined !=null }">
			<form action="${pageContext.request.contextPath}/logout"
					method="post">
					<input type="hidden" name="action" value="logout"> <input
						type="submit" value="Logout" class="btn btn-primary  btn-md">
				</form>
			</c:if>

			<form action="${pageContext.request.contextPath}/search" class="form-inline ml-auto">
				<input type="hidden" name="search" value="search" /> 
					<input class="form-control py-2" name="firstname" id="firstname" type="search" 
						placeholder="Type the Name or lastname" required="required"> 
					<span class="input-group-append">
						<button class="btn btn-outline-light" type="submit">Search</button>
					</span>
			</form>
		</div>
	</nav>

	<!--%
		String userName = null;
		if (session.getAttribute("loginedUser") != null) {
			UserAccount userAccount = (UserAccount) session.getAttribute("loginedUser");
			userName = userAccount.getUsername();
		}
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginedUser"))
					userName = cookie.getValue();
			}
		}
	%-->

	<c:if test="${not empty message}">                
    	<div class="alert alert-success">
        	${message}
    	</div>
	</c:if> 
</div>