<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="manifestazione.label.mercati_spunte" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="manifestazione.label.mercati_spunte" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	    <jsp:include page="../includes/linkmercato.jsp">
			<jsp:param name="codiceMercato" value="${mercati.id.codice}" />
			<jsp:param name="descrizioneMercato" value="${mercati.descrizione}" />											
		</jsp:include>
	
	
	<form name="mercatispunteForm" action="listmercatispunte.htm">
		<jmesa:springTableFacade
			id="mercatispunte_id" 
			items="${mercatispunteList}" 
			var="mercatispunte_var"
			exportTypes="pdfp,excel,csv" 
			stateAttr="restore" >
			<jmesa:htmlTable>
				<jmesa:htmlRow>
					<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                          	<a href="viewMercatiSpunte.htm?codice=${mercatispunte_var.id.codice}">${mercatispunte_var.id.codice}</a>
                       </jmesa:htmlColumn>	                       							
					<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione" />		
					<%--
					<jmesa:htmlColumn property="flagSegnaPres" cellEditor="org.jmesa.custom.SiNoCellEditor"
                                              filterable="no"
                                              width="5%"
                                              titleKey="manifestazione.label.flag_segna_presenza" />
                                               --%>
					<jmesa:htmlColumn property="flagFiltroCatmerc" cellEditor="org.jmesa.custom.SiNoCellEditor"
                                              filterable="no"
                                              width="5%"
                                              titleKey="manifestazione.label.flag_filtro_cat_merceologiche" />
					<jmesa:htmlColumn  filterable="no" property="ordine" titleKey="label.ordine" />
					<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
						<a class="dettaglioColumn" href="viewMercatiSpunte.htm?codice=${mercatispunte_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${mercatispunte_var.descrizione}">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>
					</jmesa:htmlColumn>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		 </jmesa:springTableFacade>
		 <input type="hidden" value="${mercati.id.codice}" name="codicemercato" />
	</form>
	</div>
	<script type="text/javascript">
		var _jmesaUrl='listmercatispunte.htm?codicemercato=${mercati.id.codice}&';
		var _captionTab='<fmt:message key="manifestazione.label.mercati_spunte" />';
	</script>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createMercatiSpunte.htm?codicemercato=${mercati.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>