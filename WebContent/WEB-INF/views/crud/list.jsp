<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<jsp:include page="top.jsp" />
	<div id="crudContent">

		<div id="crudList" class="Users">

			<h2 id="crudListTitle">Users</h2>

			<div id="crudListSearch">
				<form action="/crud/users" method="get" accept-charset="utf-8" enctype="application/x-www-form-urlencoded">
					<input type="text" name="search" value="" /> <input type="submit" value="Search" />
				</form>
			</div>

			<div id="crudListTable">
				<table>
					<thead>
						<tr>
							<th><a class="crudSortedAsc" href="/crud/users?order=DESC">${model.name }</a></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="item" items="${models }">
						<tr>
							<td><a href="${pageContext.request.contextPath}/${model.path }/${item.key}"><c:out value="${item.value}" /></a></td>
						</tr>
					</c:forEach>
					
					</tbody>
					
				</table>
				
			</div>

			<div id="crudListPagination">
				<p class="crudCount">${fn:length( models )} ${model.name} (s)</p>
			</div>

			<p id="crudListAdd">
				<a href="${pageContext.request.contextPath}/${model.path }/new">Add ${model.name}</a>
			</p>

		</div>
		
	</div>
	
	
	
<jsp:include page="bottom.jsp" />