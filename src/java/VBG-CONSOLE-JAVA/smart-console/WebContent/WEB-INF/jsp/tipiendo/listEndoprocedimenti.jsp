<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="tipiendo.label.lista_inventarioprocedimenti" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="tipiendo.label.lista_inventarioprocedimenti" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
  		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../tipiendo/listEndoprocedimenti" />
		</jsp:include>
        <div id="subcontent">
	    	<div class="parametriDiv">
				<div class="etichetta">
					<div><fmt:message key="tipiendo.label.tipo" />:</div>
				</div>		
				<div class="parametro">       		 	
					<div>${tipiendo.tipo}</div>
				</div>
			</div>
    		<%
				String urlBack = "/tipiendo/listEndoprocedimenti.htm?codicecategoriaendo=" + request.getParameter("codicecategoriaendo");
    		 	urlBack = URLEncoder.encode(urlBack, "UTF-8");
    			pageContext.setAttribute("URL_BACK", urlBack);
			%>
            
			<form name="inventarioprocedimentiForm" action="listEndoprocedimenti.htm">
				<jmesa:springTableFacade
					id="inventarioprocedimenti_id" 
					items="${inventarioprocedimentiList}" 
					var="inventarioprocedimenti_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>	
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <c:set var="_URL_INVENTARIO" value="../inventarioprocedimenti/view.htm?codice=${inventarioprocedimenti_var.id.codice}&codicecomune=${inventarioprocedimenti_var.id.idcomune}"></c:set>
                                  <a href="javascript:historySet('${URL_BACK}', '${_URL_INVENTARIO}' ,'')">${inventarioprocedimenti_var.id.codice}</a>
                            </jmesa:htmlColumn>						
							<jmesa:htmlColumn property="procedimento" titleKey="tipiendo.label.procedimento" />
                            <jmesa:htmlColumn property="tipoendo.tipo" titleKey="tipiendo.label.tipo_endo" />
                            <jmesa:htmlColumn property="flagPubblica" titleKey="label.pubblica"  cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist" />
                            <jmesa:htmlColumn property="disabilitato" titleKey="label.disabilitato"  cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="javascript:historySet('${URL_BACK}', '${_URL_INVENTARIO}' ,'')" title="<fmt:message key="label.edit.record" />&nbsp;${inventarioprocedimenti_var.procedimento}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
                <input id="codiceendo" type="hidden" value="${codicecategoriaendo}" name="codicecategoriaendo"/> 
			</form>
			<script type="text/javascript">
				var _jmesaUrl='listEndoprocedimenti.htm?codicecategoriaendo=+${codicecategoriaendo}&';
				var _captionTab='<fmt:message key="tipiendo.label.lista_inventarioprocedimenti" />';
			</script>
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:historySet('${_urlback}','../inventarioprocedimenti/create.htm?codiceTipiendo=${codicecategoriaendo}','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('view.htm?codice=${codicecategoriaendo}','');"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>