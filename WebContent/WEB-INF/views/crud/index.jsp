<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="top.jsp" />
	<div id="crudContent">

		<div id="crudIndex">
			<h2>Choose the object to edit</h2>

			<table>
				<thead>
					<tr>
						<th>Object type</th>
						<th width="20%"></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="model" items="${models }">
					<tr>
						<td><a href="${pageContext.request.contextPath}/${model.path}"><c:out value="${model.name}" /></a></td>
						<td class="crudNew"><a href="${pageContext.request.contextPath}/${model.path }/new">Add</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>

		</div>
	</div>
<jsp:include page="bottom.jsp" />