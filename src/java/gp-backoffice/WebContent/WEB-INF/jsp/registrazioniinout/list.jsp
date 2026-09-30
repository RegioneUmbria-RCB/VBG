<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.registrazioniInOut.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.registrazioniInOut.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
    		<jsp:param name="path" value="../registrazioniinout/list" />
		</jsp:include>       
		<div id="subcontent">
			<form name="registrazioniInOutForm" action="list.htm">
				<jmesa:springTableFacade
					id="registrazioniInOut_id" 
					items="${registrazioniInOutList}" 
					var="registrazioniInOut_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.RegistrazioneInOutFilterMatcherMap">
					<jmesa:htmlTable>
						<jmesa:htmlRow>	
							<jmesa:htmlColumn property="dataDistinta" width="4%" titleKey="form.registrazioniInOut.datadistinta" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataIncassoCustomFilter"/>	
							<jmesa:htmlColumn property="tipo" titleKey="form.registrazioniInOut.tipo" cellEditor="org.jmesa.custom.TipoEntrataUscitaCellEditor" filterEditor="org.jmesa.custom.TipoEntrataUscitaDroplist"/>
							<jmesa:htmlColumn property="intestatario" titleKey="form.registrazioniInOut.intestatario" />
							<jmesa:htmlColumn width="5%" property="tipimodalitapagamento.mpDescrestesa" titleKey="form.registrazioniInOut.tipimodalitapagamento"/>
							<jmesa:htmlColumn width="5%" property="riferimentiPagamento" titleKey="form.registrazioniInOut.riferimentipagamento"/>
							<jmesa:htmlColumn property="importo" headerStyle="text-align:right;" titleKey="form.registrazioniInOut.importo" style="text-align:right;">
								<fmt:formatNumber minFractionDigits="2">${registrazioniInOut_var.importo}</fmt:formatNumber>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="incassato" headerStyle="text-align:right;" titleKey="form.registrazioniInOut.assegnato" style="text-align:right;">
								<fmt:formatNumber minFractionDigits="2">${registrazioniInOut_var.incassato}</fmt:formatNumber>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="rimanenza" headerStyle="text-align:right;"  titleKey="form.registrazioniInOut.daAssegnare" style="text-align:right;">
								<fmt:formatNumber minFractionDigits="2">${registrazioniInOut_var.rimanenza}</fmt:formatNumber>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="id.codice" titleKey="label.edit.record" sortable="false" filterable="false">
								<a class="dettaglioColumn" href="view.htm?codice=${registrazioniInOut_var.id.codice}"  title="<fmt:message key="label.edit.record" /> ${registrazioniInOut_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
								<c:if test="${registrazioniInOut_var.rimanenza gt 0}">
								<a class="assegnaColumn" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fregistrazioniinout%2FsearchScadenzeByIncassoOrAnagrafe.htm%3FcodiceIncasso%3D${registrazioniInOut_var.id.codice}','');" title="<fmt:message key="label.assegna" />" >
									<label><fmt:message key="label.assegna.image" /></label>
								</a>

								</c:if>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="form.registrazioniInOut.title.list" />';
			</script>
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				
			</ul>
		</div>
	</body>
</html>