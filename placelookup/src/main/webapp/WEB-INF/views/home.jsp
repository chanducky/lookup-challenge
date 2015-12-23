<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Place Lookup</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<style type="text/css">
span.stars, span.stars span {
	display: block;
	background: url(http://www.ulmanen.fi/stuff/stars.png) 0 -16px repeat-x;
	width: 80px;
	height: 16px;
}

span.stars span {
	background-position: 0 0;
}
</style>
</head>
<body>

	<div id="main"
		style="padding-left: 10%; padding-right: 10%; padding-top: 5px;">

		<div class="panel panel-default">
			<div class="panel-body" style="height: 160px;">
				<img alt=""
					src="<c:url value="/resources/images/lookup-logo.png" />" />
			</div>
			<h5 align="left">Place Lookup : Searching Made Easy</h5>
			<P align="left">Total Places ${totalPlace}.</P>
		</div>
		<div align="center">
			<form class="form-inline" method="get" action="search">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
				<div class="form-group">
					<div class="input-group">
						<input type="text" class="form-control" id="searchBox"
							name="searchTxt" placeholder="Search by Name or Category">
					</div>
				</div>
				<input type="submit" value="Search Places" class="btn btn-primary" />
			</form>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>Place Name</th>
					<th>Category</th>
					<td>Location on Map</td>
					<th>Rating</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="place" items="${places}" varStatus="counter">
					<tr>
						<td><c:out value="${place.name}" /></td>
						<td><c:out value="${place.category}" /></td>
						<td><a
							href="/placelookup/map?lat=${place.latitude}&lng=${place.longitude}"
							target="_blank"> <span class="glyphicon glyphicon-map-marker"></span>
						</a></td>
						<td><c:out value="${place.rating}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- script below here  -->
	<script src="<c:url value="/resources/js/jquery-2.1.4.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>

</html>
