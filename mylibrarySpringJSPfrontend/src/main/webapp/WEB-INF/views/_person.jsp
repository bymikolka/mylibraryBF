<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--script>
function addAction(action)
{
	document.getElementById('action').value=action;
	document.getElementById('personForm').submit();
}

</script-->

        <div class="container">
            <form action="savePerson" id="personForm" role="form" data-toggle="validator" method="post">
                <input type="hidden" id="action" name="action" value="${action}"/>
                <input type="hidden" id="id" name="id" value="${person.id}"/>
                <h2>Person</h2>
                <div class="form-group col-xs-4">
                    <label for="firstname" class="control-label col-xs-4">Name:</label>
                    <input type="text" name="firstname" id="firstname" class="form-control" value="${person.firstname}" 
                        required="required"/>                                   

                    <label for="lastname" class="control-label col-xs-4">Last name:</label>                   
                    <input type="text" name="lastname" id="lastname" class="form-control" value="${person.lastname}" 
                        required="required"/> 

                    <label for="username" class="control-label col-xs-4">Username:</label>                   
                    <input type="text" name="username" id="username" class="form-control" value="${person.username}" 
                        required="required"/> 
                    <label for="role" class="control-label col-xs-4">Role:</label>
					<select id="role" name="role">
				    	<c:forEach items="${mapRoles}" var="mapRole">
        					<option value="${mapRole.key}" ${mapRole.key == person.role? 'selected':''} >${mapRole.value} </option>
    					</c:forEach>
					</select>
					
					                                             
					<br/>
                    <label for="description" class="control-label col-xs-4">Description:</label>
                    <input type="text" name="description" id="description" class="form-control" 
                        value="${person.description}" required="required"/>

                    <label for="email" class="control-label col-xs-4">E-mail:</label>                   
                    <input type="text" name="email" id="email" class="form-control" value="${person.email}" 
                        placeholder="me@google.com" required="required"/>

                    <br></br>
                    
                    <button type="submit" class="btn btn-primary  btn-md" onclick="addAction(${action})">Accept</button> 

                </div>                                                      
            </form>
        </div>
</html>