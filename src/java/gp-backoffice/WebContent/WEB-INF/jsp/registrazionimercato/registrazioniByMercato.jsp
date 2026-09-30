<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.registrazioniByMercato.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.registrazioniByMercato.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../registrazionimercato/registrazioniByMercato" />
	</jsp:include>
		<div id="subcontent">
        
		<span class="parametri"><fmt:message key="form.registrazioniByMercato.title.mercato" />: <label><c:out value="${mercati.descrizione}"/></label></span>
		<span class="parametri"><fmt:message key="form.registrazioniByMercato.title.mercatoUso" />: <label><c:out value="${mercatoUso.descrizione}"/></label></span>
		<span class="parametri"><fmt:message key="form.registrazioniByMercato.title.anno" />: <label><c:out value="${anno}"/></label></span>
		<br />
		<c:if test="${not empty registrazioniDaMercatoList}">
		<span class="intestazione"><fmt:message key="form.registrazioniByMercato.title.list.intestazione" /></span>
           
           <form name="registrazioniForm" action="registrazioniByMercato.htm">
				<jmesa:springTableFacade
					id="registrazioni_id" 
					items="${registrazioniDaMercatoList}" 
					var="registrazioni_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>							
							<jmesa:htmlColumn property="registrazioniCausali.descrizione" titleKey="form.registrazioniByMercato.registrazioniCausali" />
							<jmesa:htmlColumn property="importo" style="text-align: right;" titleKey="form.registrazioniByMercato.importo"><fmt:formatNumber minFractionDigits="2" value="${registrazioni_var.importo}"/></jmesa:htmlColumn>
							<jmesa:htmlColumn property="incassato" style="text-align: right;" titleKey="form.registrazioniByMercato.incassato"><fmt:formatNumber minFractionDigits="2" value="${registrazioni_var.incassato}"/></jmesa:htmlColumn>
							<jmesa:htmlColumn property="rimanenza" style="text-align: right;" titleKey="form.registrazioniByMercato.rimanenza"><fmt:formatNumber minFractionDigits="2" value="${registrazioni_var.rimanenza}"/></jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${mercati.id.codice}" name="mercati.id.codice"/>
				<input type="hidden" value="${mercatoUso.id.codice}" name="mercatoUso"/>
				<input type="hidden" value="${anno}" name="anno"/>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='registrazioniByMercato.htm?mercati.id.codice=${mercati.id.codice}&mercatoUso=${mercatoUso.id.codice}&anno=${anno}&';
				var _captionTab='<fmt:message key="form.registrazioniByMercato.title.list" />';
			</script>
		</c:if>
		
		</div>
		<script type="text/javascript">
			var goToUrl = "../registrazionimercato/create.htm?mercati.id.codice=${mercati.id.codice}&mercatouso.id.codice=${mercatoUso.id.codice}&anno=${anno}";
			goToUrl = escape(goToUrl);
		</script>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'')"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>