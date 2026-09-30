<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>File upload</title>
	</head>
	<body>
		<form id="uploadForm_id" method="post" action="${pageContext.request.contextPath}/file/ajaxUpload.htm" enctype="multipart/form-data" name="fileUpload">
		<fieldset><legend>File upload</legend>
		    <input type="file" name="fileUpload" size="70"/>
		    <br/><br/>
		    <input type="submit" value="Invia"/>
		    <input type="button" value="Chiudi" onclick="javascript:window.close()" />
		</fieldset>
		</form>
	</body>
</html>