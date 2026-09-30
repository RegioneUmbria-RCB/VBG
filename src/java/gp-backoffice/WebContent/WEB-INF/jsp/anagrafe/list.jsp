<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="anagrafe.label.lista_anagrafe.title"/></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="anagrafe.label.lista_anagrafe.title"/></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../anagrafe/list" />
		</jsp:include>
		<div id="subcontent">
			<form name="inviodati" action="list.htm">
				${htmltable}
			</form>			
			<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="anagrafe.label.lista_anagrafe.title" />';
			</script>
			
		</div>
		<% String idTemp=String.valueOf(System.currentTimeMillis()); %>	      
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
			    <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			    <c:if test="${anagrafeParixAttivo eq true }">	
			    	 	
			    	<li><a href="javascript:visuraParixFN<%=idTemp%>()"><fmt:message key="button.visura_infocamere" /></a></li>
			    </c:if>
			</ul>
		</div>
	<c:if test="${anagrafeParixAttivo eq true }">
		<jsp:include page="../includes/ricercaimpreseparix.jsp">
			<jsp:param name="identificativoTemporaneoParix" value="<%= idTemp%>" />
		</jsp:include>
	</c:if>		
	</body>
</html>