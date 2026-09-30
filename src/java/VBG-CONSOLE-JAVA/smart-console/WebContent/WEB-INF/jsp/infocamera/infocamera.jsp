<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="form.infocamera.title" /></title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.infocamera.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list"/>
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../infocamera/infocamera" />
</jsp:include>
<c:if test="${ESITO_OK != '' && ESITO_OK != null}">
<div id="status_msg" class="success_header" >
      	${ESITO_OK}
</div>
<script type="text/javascript" >
     	$('status_msg').pulsate({ pulses: 2, duration: 1.0 });
</script>
</c:if>
<c:if test="${ESITO_KO != '' && ESITO_KO != null}">
<div id="error_msg" class="error_header" >
      ${ESITO_KO}	
</div>
<script type="text/javascript">
            	$('error_msg').pulsate({ pulses: 2, duration: 1.0 });
 </script>
</c:if>
<c:if test="${error!='' && error != null}">
<div id="error_msg" class="error_header" >
      ${error}	
</div>
<script type="text/javascript">
            	$('error_msg').pulsate({ pulses: 2, duration: 1.0 });
</script>
</c:if>

<br /><br /><br />
<div id="functions">
	<ul>
        <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>