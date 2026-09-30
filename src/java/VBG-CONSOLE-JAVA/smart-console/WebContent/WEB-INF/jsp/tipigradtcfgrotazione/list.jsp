<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.lista_configurazione_rotazioni.title" /></title>
	</head>
	<body>
	<%-- exportTypes="pdfp,excel,csv" --%>
		<span class="titoloPagina"><fmt:message key="label.lista_configurazione_rotazioni.title" /></span>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../tipigradtcfgrotazione/list" />
		</jsp:include>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="tipigradtcfgrotazioneForm" action="list.htm">
				<jmesa:springTableFacade
					id="tipigradtcfgrotazione_id" 
					items="${tipigradtcfgrotazioneList}" 
					var="tipigradtcfgrotazione_var"
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="javascript:historySet('${_urlback}','../tipigradtcfgrotazione/view.htm?codice=${tipigradtcfgrotazione_var.id.codice}')">${tipigradtcfgrotazione_var.id.codice}</a>
                         	</jmesa:htmlColumn>							
							<jmesa:htmlColumn property="campiMercatoUso.nomecampo" titleKey="label.campo_mercato_uso"/>
							<jmesa:htmlColumn property="campiPosteggio.nomecampo" titleKey="label.campi_posteggio"/>
							<jmesa:htmlColumn property="campiOrdine.nomecampo" titleKey="label.campi_ordine"/>
							<jmesa:htmlColumn property="flagMultiplo" titleKey="label.multiplo"/>
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
									<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../tipigradtcfgrotazione/view.htm?codice=${tipigradtcfgrotazione_var.id.codice}')" title="<fmt:message key="label.edit.record" />">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
		</div>
		<div id="functions">
			<ul>
			    <li><a href="javascript:historySet('${_urlback}','../tipigradtcfgrotazione/create.htm?codiceTipigraduatoriat=${tipigraduatoriet.id.codice}','')"><fmt:message key="button.new" /></a></li>

				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>