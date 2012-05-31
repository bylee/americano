<jsp:include page="top.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div id="crudContent">

		<div id="crudBlank" class="Users">

			<h2 id="crudBlankTitle">Add User</h2>
			<form action="${pageContext.request.contextPath}/${model.path}" method="post" accept-charset="utf-8" >
			<c:forEach var="field" items="${fields}">
				<div class="crudField crud_text">
					<label for="object_name">${field.name}</label>
					<c:if test="${field.type=='string' }">
					<input id="object_name" class="" type="text" name="${field.name}" value="" size="50" />
					</c:if>
					<c:if test="${field.type=='binary' }">
					<input id="object_name" class="" type="file" name="${field.name}" value="" size="50" />
					</c:if>
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
	</div>
<jsp:include page="bottom.jsp" />