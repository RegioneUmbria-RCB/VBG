<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.min.js"></script>
<meta charset="UTF-8">
<title>Download file</title>
</head>
<body>
	<div id="container">
	
		<label id="msg">Il tuo download inizierà a breve, se così non fosse clicca al link diretto</label>
	
		<form name='inviodati' id='form1' action="${pageContext.request.contextPath}/downloadallegatimov/mostraFile.htm" method="post">	
			<a href="javascript:scarica()" id="lnk">scarica</a>
			<input type='hidden' value='${magic}' name='magic' />
		</form>
	
	</div>
	
<script type="text/javascript">

	function scarica(){
		$('#form1').submit();
			
	}
	function messaggio(){
		$('#msg').html('Documento scaricato');
		$('#lnk').html('');
	}
	$( document ).ready(function() {
		setTimeout('messaggio()',2000);
	    scarica();

	});
</script>
</body>
</html>