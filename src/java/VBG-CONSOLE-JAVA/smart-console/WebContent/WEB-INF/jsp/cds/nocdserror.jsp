<%@page import="java.net.URLEncoder"%>
<%@page
	import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title><fmt:message key="label.cds" /></title>
</head>
<body>
<span class="titoloPagina"> <fmt:message key="label.cds" /> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
   	<jsp:param name="path" value="../cds/view" />
   	<jsp:param name="qs" value="codiceIstanza%3D${cds.istanze.id.codice}"/>
</jsp:include>
<c:import url="/ajax/dettaglioIstanza.htm">
	<c:param name="codIstanza">${param.codiceIstanza}</c:param>
</c:import>

<br class="clear" />
<div id="subcontent">
	<div class="global_messages">
		<div id="error_msg" class="error_header">
			<div><fmt:message key="03" />.</div>
			<div><fmt:message key="service_error.nessuna_cds_per_l_istanza" /></div>
		</div>
	</div>
</div>
<div id="functions">
	<ul>
		<%-- CHIUDI --%>
		<li><a href="javascript:historyBack('')"><fmt:message
			key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>