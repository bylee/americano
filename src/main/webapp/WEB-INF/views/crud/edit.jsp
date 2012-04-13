<jsp:include page="top.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div id="crudContent">

		<div id="crudBlank" class="Users">

			<h2 id="crudBlankTitle">Add User</h2>
			<form action="${pageContext.request.contextPath}/${model.path}/${id}" method="post" accept-charset="utf-8" >
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
		<p class="crudDelete">
			<input id="delete" type="button" value="Delete User" />
		</p>
	</div>
	<script>
	$("#delete").click( function() {
		$.ajax({
			url :'${pageContext.request.contextPath}/${model.path}',
			type : 'delete',
			success : function( xml ) {
				alert( "hello" );
				$(location).attr('href','${pageContext.request.contextPath}/${model.path}/${id}');
			}
		});
	} );
		
	</script>
<jsp:include page="bottom.jsp" />