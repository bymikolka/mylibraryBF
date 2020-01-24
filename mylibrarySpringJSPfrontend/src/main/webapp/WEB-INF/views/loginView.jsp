<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<jsp:include page="_header.jsp"/>
	<jsp:include page="_menu.jsp"/>

	<h3>Login Page</h3>


	<form method="POST" class="form-horizontal"
		action="${pageContext.request.contextPath}/login">
		<div class="form-group">
			<label class="control-label col-sm-2" for="email">Email:</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="username" placeholder="Enter username" value="${user.username}">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="pwd">Password:</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" name="password" placeholder="Enter password" value="${user.password}">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-primary btn-md">Submit</button>
			</div>
		</div>
	</form>

	<p style="color: blue;">Username/password: admin/admin</p>

	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>