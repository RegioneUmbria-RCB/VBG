<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test di attivazione della sessione di pagamento</title>
</head>
<body>
	<h2>Test di attivazione della sessione di pagamento</h2>
	<form action="${ datiSessione.payUrl}" method="POST">
		<c:forEach items="${datiSessione.formParams.param}" var="sesParam">
			<div>
				<label for="${sesParam.paramName}">${sesParam.paramName}</label> <input
					type="text" paramName="${sesParam.paramName}"
					value="${sesParam.value}" />
			</div>
		</c:forEach>
		<div>
			<span style="color: red">${ message }</span>
		</div>
		<div>
			<button type="submit">POST</button>
			<button type="button" onclick="javascript: pagamentiGet();">GET</button>
		</div>
	</form>
	<script type="text/javascript">
		function pagamentiGet() {

			document.location = "${urlget}";
		}
	</script>
</body>
</html>