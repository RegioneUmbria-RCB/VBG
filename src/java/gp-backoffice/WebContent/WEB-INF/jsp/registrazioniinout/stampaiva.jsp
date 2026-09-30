<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.registrazioniFilter.title.riepilogoincassi" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.registrazioniFilter.title.riepilogoincassi" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
             
		<div id="subcontent">

        <div class="intestazione">
			<fmt:message key="label.filtri"></fmt:message>
		</div>
		<br />
		<c:if  test="${datainizio != null || datafine != null}" >
       	<span class="parametri">
       		<fmt:message key="form.registrazioniFilter.datadistinta" />:
			<c:if  test="${datainizio != null }" >
				<fmt:message key="form.registrazioniFilter.data.inizio" /><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${datainizio}"/></label>
			</c:if>
			<c:if  test="${datafine != null }" >
				<fmt:message key="form.registrazioniFilter.data.fine"/><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${datafine}"/></label>
			</c:if>
       	</span>
        </c:if>
		<c:if test="${registrazioniFilter.anagrafe.id.codice!=null}">
        	<span class="parametri">
            <fmt:message key="form.registrazioniFilter.anagrafe" />:<label>${registrazioniFilter.anagrafe.descrizioneRichiedente}</label>
     		</span>
        </c:if>
        <c:if test="${registrazioniFilter.mercati.id.codice!=null}">
     		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.mercati" />: <label>${registrazioniFilter.mercati.descrizione}</label>
             </span>
        </c:if>
        <c:if test="${registrazioniFilter.mercatiUso.id.codice!=null}">
     		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.mercatouso" />: <label>${registrazioniFilter.mercatiUso.descrizione}</label>
             </span>
        </c:if>
        <c:if test="${registrazioniFilter.raggruppamentoRiepiloghiIncassi eq 'ANAGRAFE_MERCATO_POSTEGGIO'}">
     		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.raggruppamento" />: <label><fmt:message key="form.registrazioniFilter.raggrRiepIncassi.anagrMercPost" /></label>
             </span>
        </c:if>
         <c:if test="${registrazioniFilter.raggruppamentoRiepiloghiIncassi eq 'CONTI'}">
     		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.raggruppamento" />: <label><fmt:message key="form.registrazioniFilter.raggrRiepIncassi.conto" /></label>
             </span>
        </c:if>
         <c:if test="${registrazioniFilter.raggruppamentoRiepiloghiIncassi eq 'DATADISTINTA_CONTO'}">
     		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.raggruppamento" />: <label><fmt:message key="form.registrazioniFilter.raggrRiepIncassi.datadistintaconto" /></label>
             </span>
        </c:if>
        <c:if test="${registrazioniFilter.raggruppamentoRiepiloghiIncassi eq 'NESSUN_RAGGRUPPAMENTO'}">
     		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.raggruppamento" />: <label><fmt:message key="form.registrazioniFilter.raggrRiepIncassi.nessuno" /></label>
             </span>
        </c:if>
      <br/>
        <c:if test="${registrazioniFilter.raggruppamentoRiepiloghiIncassi eq 'NESSUN_RAGGRUPPAMENTO'}">	
       <form name="registrazioniFilterForm" action="stampaiva.htm">
				<jmesa:springTableFacade
					id="stampaivanessunraggruppamento_id" 
					items="${registrazioniFilterList}" 
					var="registrazioniFilter_var"
					exportTypes="pdfp,excel" 
					stateAttr="restore">
					<jmesa:htmlTable>
						<jmesa:htmlRow>	
						    <jmesa:htmlColumn property="dataDistinta"  titleKey="form.registrazioniFilter.datadistinta"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor"/>
							<jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="form.registrazioniFilter.nominativo" />
                            <jmesa:htmlColumn property="mercati.descrizione" titleKey="form.registrazioniFilter.mercati" />
                            <jmesa:htmlColumn property="mercatiUso.descrizione" titleKey="form.registrazioniFilter.mercatiUso" />
                            <jmesa:htmlColumn property="posteggio.codiceposteggio" titleKey="form.registrazioniFilter.posteggio" style="text-align:right;"/>
                            <jmesa:htmlColumn property="importo" titleKey="form.registrazioniFilter.importo"  style="text-align:right;" pattern="###,##0.00" cellEditor="org.jmesa.view.editor.NumberCellEditor" /> 			
                            <jmesa:htmlColumn property="conti.descrizione" titleKey="form.registrazioniFilter.conto" /> 			
                            <jmesa:htmlColumn property="iva" titleKey="form.registrazioniFilter.iva" style="text-align:right;"/>
                            <jmesa:htmlColumn property="imponibile" titleKey="form.registrazioniFilter.imponibile" style="text-align:right;" pattern="###,##0.00" cellEditor="org.jmesa.view.editor.NumberCellEditor" />
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			</c:if>
       <c:if test="${registrazioniFilter.raggruppamentoRiepiloghiIncassi eq 'ANAGRAFE_MERCATO_POSTEGGIO'}">	
       <form name="registrazioniFilterForm" action="stampaiva.htm">
				<jmesa:springTableFacade
					id="stampaiva_id" 
					items="${registrazioniFilterList}" 
					var="registrazioniFilter_var"
					exportTypes="pdfp,excel" 
					stateAttr="restore" view="org.jmesa.custom.GroupRegistrazioniInOutView">
					<jmesa:htmlTable>
						<jmesa:htmlRow>	
							<jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="form.registrazioniFilter.nominativo" />
                            <jmesa:htmlColumn property="mercati.descrizione" titleKey="form.registrazioniFilter.mercati" />
                            <jmesa:htmlColumn property="mercatiUso.descrizione" titleKey="form.registrazioniFilter.mercatiUso" />
                            <jmesa:htmlColumn property="posteggio.codiceposteggio" titleKey="form.registrazioniFilter.posteggio" style="text-align:right;"/>
                            <jmesa:htmlColumn property="importo" titleKey="form.registrazioniFilter.importo"  style="text-align:right;" pattern="###,##0.00" cellEditor="org.jmesa.view.editor.NumberCellEditor" /> 			
                            <jmesa:htmlColumn property="conti.descrizione" titleKey="form.registrazioniFilter.conto" /> 			
                            
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			</c:if>
            <c:if test="${registrazioniFilter.raggruppamentoRiepiloghiIncassi eq 'CONTI'}">	
            <form name="registrazioniFilterForm" action="stampaiva.htm">
				<jmesa:springTableFacade
					id="stampaiva_id" 
					items="${registrazioniFilterList}" 
					var="registrazioniFilter_var"
					exportTypes="pdfp,excel" 
					stateAttr="restore" view="org.jmesa.custom.GroupRegistrazioniInOutView">
					<jmesa:htmlTable>
						<jmesa:htmlRow>	
						    <jmesa:htmlColumn property="conti.descrizione" titleKey="form.registrazioniFilter.conto" /> 	
                            <jmesa:htmlColumn property="importo" titleKey="form.registrazioniFilter.importo"  style="text-align:right;" pattern="###,##0.00" cellEditor="org.jmesa.view.editor.NumberCellEditor" /> 			
                        </jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			</c:if>
            <c:if test="${registrazioniFilter.raggruppamentoRiepiloghiIncassi eq 'DATADISTINTA_CONTO'}">	
            <form name="registrazioniFilterForm" action="stampaiva.htm">
				<jmesa:springTableFacade
					id="stampaiva_id" 
					items="${registrazioniFilterList}" 
					var="registrazioniFilter_var"
					exportTypes="pdfp,excel" 
					stateAttr="restore" view="org.jmesa.custom.GroupRegistrazioniInOutView">
					<jmesa:htmlTable>
						<jmesa:htmlRow>	
                            <jmesa:htmlColumn property="dataDistinta"  titleKey="form.registrazioniFilter.datadistinta"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor"/> 
						    <jmesa:htmlColumn property="conti.descrizione" titleKey="form.registrazioniFilter.conto" /> 	
                            <jmesa:htmlColumn property="importo" titleKey="form.registrazioniFilter.importo"  style="text-align:right;" pattern="###,##0.00" cellEditor="org.jmesa.view.editor.NumberCellEditor" /> 			
                        </jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			</c:if>
			

            <script type="text/javascript">
				var _jmesaUrl='stampaiva.htm?';
				var _captionTab="<fmt:message key='form.registrazioneFilter.title.stampaiva' />";
			</script>
		</div>
		<div id="functions">
			<ul>
				<c:if test="${registrazioniFilter.raggruppamentoRiepiloghiIncassi eq 'NESSUN_RAGGRUPPAMENTO'}">
					<li><a href="exportStampaIVA.htm"><fmt:message key="button.stampaiva.exportpivot" /></a></li>
				</c:if>
				<li><a href="javascript:doHref('createStampaIVA.htm','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>