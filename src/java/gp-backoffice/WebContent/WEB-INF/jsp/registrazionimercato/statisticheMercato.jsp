<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.registrazioniStatisticheMercati.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.registrazioniStatisticheMercati.title.list" />: <c:out value="${mercati.descrizione}"/></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="registrazioniStatisticheMercatiForm" action="statisticheMercato.htm">
				<jmesa:springTableFacade
					id="registrazioniStatisticheMercati_id" 
					items="${registrazioniStatisticheMercatiList}" 
					var="registrazioniStatisticheMercati_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>							
							<jmesa:htmlColumn property="anno" titleKey="form.registrazioniStatisticheMercati.anno" />
							<jmesa:htmlColumn property="importoAnno" titleKey="form.registrazioniStatisticheMercati.importoAnno" />
							<jmesa:htmlColumn property="incassatoAnno" titleKey="form.registrazioniStatisticheMercati.incassatoAnno" />
							<jmesa:htmlColumn property="rimanenzaAnno" titleKey="form.registrazioniStatisticheMercati.rimanenzaAnno" />
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${mercati.id.codice}" name="mercati.id.codice"/>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='statisticheMercato.htm?mercati.id.codice=${mercati.id.codice}&';
				var _captionTab='<fmt:message key="form.registrazioniStatisticheMercati.title.list" />';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm?mercati.id.codice=${mercati.id.codice}','')"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>