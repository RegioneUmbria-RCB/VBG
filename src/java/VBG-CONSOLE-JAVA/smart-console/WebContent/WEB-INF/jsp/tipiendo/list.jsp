<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="tipiendo.label.lista_tipiendo" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="tipiendo.label.lista_tipiendo" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../tipiendo/list" />
		</jsp:include>
		<div id="subcontent">
			<form name="tipiendoForm" action="list.htm">
				<jmesa:springTableFacade
					id="tipiendo_id" 
					items="${tipiendoList}" 
					var="tipiendo_var"  
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable >
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="javascript:historySet('${_urlback}','../tipiendo/view.htm?codice=${tipiendo_var.id.codice}','')">${tipiendo_var.id.codice}</a>
                            </jmesa:htmlColumn>							
							<jmesa:htmlColumn property="tipo" titleKey="tipiendo.label.tipo" />
							<jmesa:htmlColumn property="tipifamiglieendo.tipo" titleKey="tipiendo.label.tipifamiglieendo" />
                            <jmesa:htmlColumn property="ordine" titleKey="tipiendo.label.ordine" headerStyle="text-align:right;" style="text-align:right;" />
							<jmesa:htmlColumn property="flagPubblica" titleKey="label.pubblica"  cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../tipiendo/view.htm?codice=${tipiendo_var.id.codice}','')" title="<fmt:message key="label.edit.record" />&nbsp;${tipiendo_var.tipo}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${codicetipifamiglieendo}" name="codiceTipofamigliaEndo"/>
			</form>
			<script type="text/javascript">
				var _jmesaUrl='list.htm?codiceTipofamigliaEndo=${codicetipifamiglieendo}&';
				var _captionTab='<fmt:message key="tipiendo.label.lista_tipiendo" />';
			</script>
		
		</div>
		<div id="functions">
			<ul>
                <li><a href="javascript:historySet('${_urlback}','../tipiendo/create.htm?codiceTipofamigliaEndo=${codicetipifamiglieendo}','')"><fmt:message key="button.new" /></a></li>
                <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>