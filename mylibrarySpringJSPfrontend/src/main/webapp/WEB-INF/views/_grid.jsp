<!-- %@page import="by.javaeecources.entities.UserAccount"%-->
<!--%@page import="by.javaeecources.repository.PersonFactory"%-->


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- div class="container-fluid">
	<div class="row">
		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<span class="input-group-text" id="basic-addon3">Select a Role:</span>
			</div>
			<form action="${pageContext.request.contextPath}/" method="get">
				<select class="btn btn-outline-dark" name="role">
					<c:forEach items="${personRoles}" var="option">
						<option value="${option.role}"
							${param.role == option.role? 'selected' : ''}>${option.name}</option>
					</c:forEach>
				</select>
				<button type="submit" class="btn btn-primary btn-md">Select</button>
				<div class="text-right"></div>
			</form>
		</div>
	</div>
</div-->

	<!--%
		if (session.getAttribute("loginedUser") != null) {
			UserAccount userAccount = (UserAccount) session.getAttribute("loginedUser");
			if (userAccount.isEditorMode()) {
				session.setAttribute("editor", true);
			} else {
				session.setAttribute("editor", false);
			}
		}
	%-->
	<c:set var="editor" value="<%=session.getAttribute(\"editor\")%>" />
	<c:set var="role" value="<%=getServletContext().getAttribute(\"role\")%>" />

<div class="text-left">
	<!-- c:if test="${editor == true}"-->
		<form action="${pageContext.request.contextPath}/create" method="post">
			<button type="submit" class="btn btn-primary btn-md">New person</button>
		</form>
	<!--/c:if-->
</div>
<form action="/" method="post" id="personForm" role="form">
	<input type="hidden" id="action" name="action" value="${action}" />
	<input type="hidden" id="idPerson" name="idPerson" value="${person.id}" />
	<c:choose>
		<c:when test="${not empty personsList}">
			<table class="table" aria-describedby="personsTable">
				<thead class="thead-dark">
					<tr>
						<th scope="col">Role</th>
						<th scope="col">Full name</th>
						<th scope="col">Username</th>
						<th scope="col">E-mail</th>
						<th scope="col">Description</th>
						<th scope="col"></th>
						<th scope="col"></th>
					</tr>
				</thead>
				<c:forEach var="person" items="${personsList}">
				
				<c:url var="editLink" value="/edit">
        			<c:param name="id" value="${person.id}" />
       			</c:url>

				
					<c:set var="classSucess" value="" />
					<c:if test="${idPerson == person.id}">
						<c:set var="classSucess" value="info" />
					</c:if>
					<tr class="${classSucess}">
						<c:set var="tRole" value="${person.role}" />
						<td>${mapRoles[person.role]}</td>
							<!--%
								out.print(PersonFactory.getPersonTypeByRole((Long) pageContext.getAttribute("tRole"))
													.getShortName());
							%-->
						<td>${person.firstname}&nbsp;${person.lastname}</td>
						<td>${person.username}</td>
						<td>${person.email}</td>
						<td>${person.description}</td>
								<td><a href="${editLink}">edit</a></td>
								<td><a href="/delete/${person.id}">delete</a></td>
			
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<br>
			<div class="alert alert-info">No people found matching your	search criteria</div>
		</c:otherwise>
	</c:choose>
</form>
<nav aria-label="Navigation">
				<div class="form-group col-md-1">
					<select class="form-control pagination" id="recordsPerPage">
						<c:forEach items="${pageSizes}" var="pageSize">
							<option value="${pageSize}" ${pageSize == recordsPerPage? 'selected':''} >${pageSize} </option>
						</c:forEach>
					
					</select>
				</div>


    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li class="page-item">
            	<a class="page-link" href="${pageContext.request.contextPath}/?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">Previous</a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i -1}">
                    <li class="page-item active">
                    	<a class="page-link"> ${i} <span class="sr-only">(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                    	<a class="page-link" href="?recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage lt noOfPages}">
            <li class="page-item">
            	<a class="page-link" href="?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">Next</a>
            </li>
        </c:if>              
    </ul>
</nav>
<script>
		/*<![CDATA[*/
		$(document).ready(function () {
			changePageAndSize();
		});

		function changePageAndSize() {
			$('#recordsPerPage').change(function (evt) {
				window.location.replace("/?recordsPerPage=" + this.value + "&currentPage=1");
			});
		}
	        /*]]>*/
	</script>




