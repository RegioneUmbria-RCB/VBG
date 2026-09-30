<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.documenti_autorizzazioni.lista_documenti_da_firmare" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.documenti_autorizzazioni.lista_documenti_da_firmare" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../documentiautorizzazione/listDocumentiMessiAllaFirma" />
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
		<jsp:param name="commandName" value="documentiistanza" />
	</jsp:include>
	<div class="header_dati">
		<div class="header_dato">
			<span class="header_dato_etichetta"><fmt:message key="label.documenti_autorizzazioni.numero_aut"/>:</span>
			<span class="header_dato_valore">${autorizzazione.autoriznumero}</span>
		</div>
		<div class="header_dato">
			<span class="header_dato_etichetta"><fmt:message key="label.documenti_autorizzazioni.data_autorizzazione"/>:</span>			
			<span class="header_dato_valore"><fmt:formatDate value="${autorizzazione.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></span>
		</div>
		<div class="header_dato">
			<span class="header_dato_etichetta"><fmt:message key="label.documenti_autorizzazioni.intestatario"/>:</span>
			<span class="header_dato_valore">${autorizzazione.anagrafe.descrizioneRichiedente}</span>
		</div>
	</div>
	<div id="subcontent">
	
		<div style="border: thin dotted; padding: 30px;margin-top:20px;font-size:1.2em; font-weight:bold" class="warning_header">
			<fmt:message key="label.documenti_autorizzazioni.help"/>
		</div>	
	
	
		<form name="movimentiallegatimessiallafirmaForm" action="listDocumentiMessiAllaFirma.htm">
			<table width="100%">
				<tr>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<jmesa:springTableFacade
							id="movimentiallegati_id"
							items="${movimentiallegatis}"
							var="movimentiallegati_var"
							stateAttr="restore">
							<jmesa:htmlTable>
								<jmesa:htmlRow>
									<jmesa:htmlColumn property="nomeFile" width="20%" titleKey="label.filename" filterable="true"/>
									<jmesa:htmlColumn property="descFileMovimentiallegati" width="20%" titleKey="label.descrizione_file" filterable="true"/>
									<jmesa:htmlColumn property="numeroIstanza" width="8%" titleKey="label.documenti_autorizzazioni.istanza" filterable="false"/>
									<jmesa:htmlColumn property="movimento" width="35%" titleKey="label.movimento" filterable="true"/>
									<jmesa:htmlColumn property="_mettiallafirma" titleKey="label.seleziona" headerEditor="org.jmesa.custom.SelezionaAllegatiMovHeaderEditor" sortable="false" filterable="false">
										<input id="sel_id${movimentiallegati_var.codiceMovimentiallegati}" class="documenti_da_firmare_cls" onclick="addToDocDaFirmare();" value="${movimentiallegati_var.codiceMovimentiallegati}" type="checkbox" />
									</jmesa:htmlColumn>
								</jmesa:htmlRow>
							</jmesa:htmlTable>
						</jmesa:springTableFacade>
						
						<%-- <input type="hidden" name="codiceMovimento" id="codiceMovimento_id" value="${movimento.id.codice}" /> --%>
						<!-- <input type="hidden" name="codiceMovimentoAllegato" id="codiceMovimentoAllegato_id" value="" /> -->
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2">
						<div id="functions">
							<ul>
								<li id="metti_alla_firma_fun" style="display: none;" ><a href="javascript:mettiallafirma()"><fmt:message key="button.metti_alla_firma" /></a></li>
								<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<c:if test="${not empty documentidafirmares}">
					<tr>
						<td colspan="2"><span class="titoloPagina"><fmt:message key="label.lista_documenti_da_firmare.title" /></span></td>
					</tr>
					<!-- <tr>
						<td colspan="2">&nbsp;</td>
					</tr> -->
					<tr id="documenti_da_firmare_table_id">
						<td colspan="2">
							<jmesa:springTableFacade
							id="documentidafirmare_id" 
							items="${documentidafirmares}" 
							var="documentidafirmare_var"
							stateAttr="restore" >
								<jmesa:htmlTable>
									<jmesa:htmlRow>
										<jmesa:htmlColumn property="oggetti.nomefile"  width="20%"  titleKey="documentidafirmare.label.nome_file" filterable="true"/>
										<jmesa:htmlColumn property="movimentiallegati.descrizione"  width="20%"  titleKey="documentidafirmare.label.descrizione_file" filterable="true"/>
										<jmesa:htmlColumn property="istanze.numeroistanza"  width="8%"  titleKey="label.documenti_autorizzazioni.istanza" filterable="false"/>
										<jmesa:htmlColumn property="movimentiallegati.movimento.movimento" width="20%" titleKey="label.movimento" filterable="true"/>
										<jmesa:htmlColumn property="richiedente.responsabile" width="15%" titleKey="documentidafirmare.label.op_richiedente" filterable="true"/>
										<jmesa:htmlColumn property="firmatario.responsabile" width="15%" titleKey="documentidafirmare.label.responsabile_firmatario" filterable="true"/>
										<%-- <jmesa:htmlColumn property="annotazioniRichiedente"  width="15%"  titleKey="documentidafirmare.label.note_op" />
										
										<jmesa:htmlColumn property="dataRichiesta" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor"  width="5%"  titleKey="documentidafirmare.label.data_richiesta" />
										<jmesa:htmlColumn property="firmatario.responsabile"  width="15%"  titleKey="documentidafirmare.label.responsabile_firmatario" />
										
										<jmesa:htmlColumn property="annotazioniFirmatario"  width="15%"  titleKey="documentidafirmare.label.note_resp" />
										
										<jmesa:htmlColumn property="dataFirma" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" width="5%"  titleKey="documentidafirmare.label.data_firma" />
										
										<jmesa:htmlColumn property="flagDaFirmare"  width="12%"  titleKey="documentidafirmare.label.stato_richiesta" />
									
										<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
											<a class="dettaglioColumn" href="popupViewMettiAllaFirma.htm?codice=${documentidafirmare_var.id.codice}"  title="<fmt:message key="label.edit.record" />&nbsp;${documentidafirmare_var.id.codice}">
												<label><fmt:message key="label.edit.record.image" /></label>
											</a>
										</jmesa:htmlColumn> --%>
										<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
											<%-- <a class="dettaglioColumn" href="javascript:dettaglio(${documentidafirmare_var.id.codice});"  title="<fmt:message key="label.edit.record" />&nbsp;${documentidafirmare_var.id.codice}">
												<label><fmt:message key="label.edit.record.image" /></label>
											</a> --%>
											<a class="dettaglioColumn" href="javascript: void 0" onclick="window.open('${pageContext.request.contextPath}/documentidafirmare/popupViewMettiAllaFirma.htm?codice=${documentidafirmare_var.id.codice}&from=1',69,'status=1,menubar=0,scrollbars=1,width=1000, height=500, resizable=1')"  title="<fmt:message key="label.edit.record" />&nbsp;${documentidafirmare_var.id.codice}">
												
											</a>
											
										</jmesa:htmlColumn>
									</jmesa:htmlRow>
								</jmesa:htmlTable>
							</jmesa:springTableFacade>
							
						</td>
					</tr>
				</c:if>
			</table>
			<input type="hidden" name="codiceautorizzazione" id="codiceautorizzazione_id" value="${autorizzazione.id.codice}" />
		</form>
		<form name="DOC_DA_METTERE_ALLA_FIRMA_FRM" method="post" action="${pageContext.request.contextPath}/documentidafirmare/ajaxViewOggettoMultiploList.htm">					
			<input type="hidden" name="lista_doc_da_firmare" id="lista_doc_da_firmare_id"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listDocumentiMessiAllaFirma.htm?codiceautorizzazione=${autorizzazione.id.codice}&';
			var _captionTab='<fmt:message key="label.documenti_autorizzazioni.lista_documenti_da_firmare" />';  
		</script>
		<script type="text/javascript">
			function dettaglio(codice){
				var urlTo = "../documentidafirmare/popupViewMettiAllaFirma.htm?codice="+codice;
				historySet('${_urlback}', urlTo, '');
			}
		</script>
	</div>
	
	<script type="text/javascript">
	
		function selezionaDeselezionaTutti(){
	    	var check = jQuery("#id_check_seleziona_tot").is(':checked');
	    	jQuery(".documenti_da_firmare_cls").each(function()
	    	{
	    		this.checked = check;
	    	});
			addToDocDaFirmare();
	    }
	
		function addToDocDaFirmare(){
			var docDaFirmare = "";
			var almenoUno = false;
			jQuery(".documenti_da_firmare_cls").each(function() {
			    if(this.checked){
			    	docDaFirmare += this.value+",";
			    	almenoUno=true;
			    }
			});
			docDaFirmare = docDaFirmare.replace(/,$/,"");
			if(almenoUno){
				jQuery('#metti_alla_firma_fun').show();
			}else{
				jQuery('#metti_alla_firma_fun').hide();
			}
			jQuery('#lista_doc_da_firmare_id').val(docDaFirmare);
		}
		
		function mettiallafirma(){
			var listaCodici=jQuery('#lista_doc_da_firmare_id').val();
            javascript:historySet('${_urlback}', '../documentidafirmare/createMettiAllaFirmaMultipli.htm?codiciMovAllegato='+listaCodici, '');
		}
		
		/* jQuery(function () {
	      	
	  		jQuery(".delete_id").eliminaConConferma({
	      		dialogSelector: '#dialog-6',
	      		dataId: 'codiceAllegatoMovimento',
	      		callback: function (id) {
	      			//alert(id);
	      			elimina(id)
	      		}
	      	});
	  		
	  	}); */
		
	</script>
</body>
</html>