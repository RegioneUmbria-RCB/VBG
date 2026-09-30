<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_notifiche_soggetti" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_notifiche_soggetti" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	     <div id="subcontent">
	    	<div class="parametriDiv">
				<div class="etichetta">
   					<div>
             			<fmt:message key="label.soggetto" />:
       				</div>
        		</div>
        		<div class="parametro">
       				<div>
           	 			<c:out value="${anagrafe.descrizioneRichiedente}"/>
       				</div>
		 		</div>
    		</div>
    	</div>  
    	<br class="clear"/>
		<form name="scadenzeForm" action="listscadenze.htm">
			<jmesa:springTableFacade
				id="scadenze_id" 
				items="${scadenzeList}" 
				var="scadenze_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.ScadenzeFilterMatcherMap" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="viewScadenze.htm?codice=${scadenze_var.id.codice}">${scadenze_var.id.codice}</a>
                        </jmesa:htmlColumn>								
                        <jmesa:htmlColumn property="categoria" width="10%" titleKey="label.tipo" cellEditor="org.jmesa.custom.ScadenzaAvvisoCellEditor" filterEditor="org.jmesa.custom.ScadenzaAvvisoDroplist"/>
                        <jmesa:htmlColumn property="datascadenza" titleKey="label.datascadenza" width="10%" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DatascadenzaScadenzeCustomFilter"/>
                        <jmesa:htmlColumn property="dataregistrazione" titleKey="label.dataregistrazione" width="10%" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataRegistrazioneScadenzeCustomFilter"/>
						<jmesa:htmlColumn property="scadenza" titleKey="label.scadenza" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="viewScadenze.htm?codice=${scadenze_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${scadenze_var.scadenza}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" name="codiceanagrafe" value="${anagrafe.id.codice}"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listscadenze.htm?codiceanagrafe=${anagrafe.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_notifiche_soggetti" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createScadenze.htm?codiceanagrafe=${anagrafe.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<!-- GMT REPORT -->
			<li><a href="javascript:doHref('','')"><fmt:message key="button.stampa" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>