<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jmesa.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.js"></script>
	<title>Pratiche</title>
</head>
<body>
<!-- §§§BEGIN§§§ -->
	<form name="praticheForm" action="${pageContext.request.contextPath}/pratiche/list.htm">
		${pratiche}
	</form>
	<br />
	<input type="button" name="Chiudi" onclick="location.href='${pageContext.request.contextPath}/'" value="Chiudi" />
	<script type="text/javascript">
		function onInvokeAction(id){
			createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function deletePratica(id){
			if(confirm("Vuoi cancellare la pratica id="+id+" ?\nQuesto comporterà la cancellazione di tutti i record collegati.")){
				document.location.href="${pageContext.request.contextPath}/pratiche/delete.htm?codicePratica="+id;
			}
		}
	</script>
<!-- §§§END§§§ -->
</body>
</html>