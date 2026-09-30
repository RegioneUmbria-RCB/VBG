<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="alberoproc.label.lista_alberoprocOneri.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="alberoproc.label.lista_alberoprocOneri.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
       		<div class="etichetta"> 
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionepadre}
	        	</div>
	        	<br />
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionebreve}
	        	</div>
	        </div>
    </div>
    <div class="clear"></div>
		<form name="alberoprocForm" action="listOneri.htm">
			<jmesa:springTableFacade
				id="alberoproc_id" 
				items="${alberoprocOneriList}" 
				var="alberoprocOneri_var"
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%" sortable="false"  filterable="false">
                           	<a href="viewOneri.htm?alberoprocOneri.id.codice=${alberoprocOneri_var.id.codice}">${alberoprocOneri_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="tipicausalioneri.coDescrizione" titleKey="alberoproc.label.alberoprocOneri_causali.table" sortable="false"  filterable="false"  />
						<jmesa:htmlColumn property="aoImportocausale" titleKey="alberoproc.label.alberoprocOneri_aoImportocausale" sortable="false"  filterable="false" style="text-align:right;" headerStyle="text-align:right;">
							<fmt:formatNumber minFractionDigits="2">${alberoprocOneri_var.aoImportocausale}</fmt:formatNumber>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="aoImportoistruttoria" titleKey="alberoproc.label.alberoprocOneri_aoImportoistruttoria" sortable="false"  filterable="false" style="text-align:right;" headerStyle="text-align:right;">
							<fmt:formatNumber minFractionDigits="2">${alberoprocOneri_var.aoImportoistruttoria}</fmt:formatNumber>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="note" titleKey="label.note" width="20%"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="viewOneri.htm?alberoprocOneri.id.codice=${alberoprocOneri_var.id.codice}" title="<fmt:message key="label.edit.record" />${alberoproc_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${alberoproc.id.codice}" name="alberoproc.id.codice"/> 
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listOneri.htm?alberoproc.id.codice=${alberoproc.id.codice}&';
			var _captionTab='<fmt:message key="alberoproc.label.lista_alberoprocOneri.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createOneri.htm?alberoproc.id.codice='+${alberoproc.id.codice},'');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=' + ${alberoproc.id.codice},'')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>