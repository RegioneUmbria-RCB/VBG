<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_modelli" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_modelli" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	 	<div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="label.tipimovimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${tipimovimento.descrizioneEstesa}" /></div>
			</div>
	    </div>
	    <div class="clear"></div>	
		<form name="alberoprocModelliForm" action="listmodelli.htm">
			<jmesa:springTableFacade
				id="modelliTipimovimento_id" 
				items="${tipimovimentidyn2modellits}" 
				var="modelli_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>							
						<jmesa:htmlColumn property="dyn2Modellit.descrizione" titleKey="label.modello" />
						<jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="5%">
							<a class="eliminaRiga" href="javascript:doHref('deleteModelli.htm?codicemodellot=${modelli_var.id.fkD2mtId}&codicemovimento=${modelli_var.id.tipomovimento}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />&nbsp;${modelli_var.dyn2Modellit.descrizione}">
								<label><fmt:message key="label.azioni" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			  <input type="hidden" value="${tipimovimento.id.tipomovimento}" name="codicemovimento"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listmodelli.htm?codicemovimento=${tipimovimento.id.tipomovimento}&';
			var _captionTab='<fmt:message key="label.lista_modelli" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createmodelli.htm?codicemovimento=${tipimovimento.id.tipomovimento}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>