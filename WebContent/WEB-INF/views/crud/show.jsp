<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="top.jsp" />
	<div id="crudContent">

		<div id="crudBlank" class="Users">

			<h2 id="crudBlankTitle">Add User</h2>
			<form action="${pageContext.request.contextPath}/${model.path}" method="post" accept-charset="utf-8" >
			<c:forEach var="field" items="${fields}">
				<div class="crudField crud_text">
					<label for="object_name">${field.name}</label>
					<input id="object_name" class="" type="text" name="${field.name}" value="${field.value}" size="50" />
					<span class="crudHelp"> </span>
				</div>
			</c:forEach>
				<p class="crudButtons">
					<input type="submit" name="save" value="Save" />
					<input type="submit" name="saveAndContinue" value="Save and continue" />
					<input type="submit" name="saveAndAddAnother" value="Save and add another" />
				</p>
			</form>
		</div>

		<form action="/admin/users/1?x-http-method-override=DELETE" method="post" accept-charset="utf-8" enctype="application/x-www-form-urlencoded" >
			<p class="crudDelete">
				<input type="submit" value="Delete User" />
			</p>
		</form>		
	</div>
<jsp:include page="bottom.jsp" />