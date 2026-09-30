<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${mailtipo.id.codice==null}">
			<fmt:message key="mailtipo.label.nuovo_mailtipo.title" />
		</c:if> 
		<c:if test="${mailtipo.id.codice!=null}">
			<fmt:message key="mailtipo.label.dettaglio_mailtipo.title" />
		</c:if>
	</title>
</head>
<body>


<script type="text/javascript">
	
	if('${mailtipo.ambito}'=='M' || '${mailtipo.ambito}'=='F')
	{
		initTextEditors();
		/*
		tinyMCE.init({
  			mode: "exact",   			
			elements: "corpo_id", 
  			theme: "advanced",
  			theme_advanced_toolbar_location: "top",
  			theme_advanced_toolbar_align: "left",
  			plugins: "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
  		  	theme_advanced_buttons1: "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
  			theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
  			theme_advanced_buttons3: "link,unlink,anchor,image,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,media,advhr",
  			forced_root_block : false,
  	        force_br_newlines : true,
  	        force_p_newlines : false
		});
		*/
	}if('${mailtipo.ambito}'=='F'){
		jQuery(document).ready(function(){
			
			showFrontendVars();
						
		});	
	}
	
	function cambiaAmbito()
	{
		var ambito = document.getElementById('ambito_id').options[ document.getElementById('ambito_id').selectedIndex ].value;
		var d;
		if( ambito == 'P' )
		{
			document.getElementById("TR_OGGETTO").style.display='';
			document.getElementById("TR_CORPO").style.display='none';
			showALLVars();
		}
		else if( ambito == 'C' )
		{
			document.getElementById("TR_OGGETTO").style.display='none';
			document.getElementById("TR_CORPO").style.display='';
		    tinyMCE.execCommand('mceFocus', false, 'corpo_id');                    
			tinyMCE.execCommand('mceRemoveControl', false, 'corpo_id');
			tinyMCE.triggerSave();
			showALLVars();
		}
		else if( ambito == 'M' )
		{
			 tinyMCE.init({
	  			mode: "exact",   			
				elements: "corpo_id", 
	  			theme: "advanced",
	  			theme_advanced_toolbar_location: "top",
	  			theme_advanced_toolbar_align: "left",
	  			plugins: "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
	  		  	theme_advanced_buttons1: "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
	  			theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
	  			theme_advanced_buttons3: "link,unlink,anchor,image,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,media,advhr",
	  			forced_root_block : false,
	  	        force_br_newlines : true,
	  	        force_p_newlines : false
			});
			 
			document.getElementById("TR_OGGETTO").style.display='';
			document.getElementById("TR_CORPO").style.display='';
			showALLVars();
		}
		else if( ambito == 'F' )
		{
			 tinyMCE.init({
	  			mode: "exact",   			
				elements: "corpo_id", 
	  			theme: "advanced",
	  			theme_advanced_toolbar_location: "top",
	  			theme_advanced_toolbar_align: "left",
	  			plugins: "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
	  		  	theme_advanced_buttons1: "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
	  			theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
	  			theme_advanced_buttons3: "link,unlink,anchor,image,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,media,advhr",
	  			forced_root_block : false,
	  	        force_br_newlines : true,
	  	        force_p_newlines : false
			});
		
			document.getElementById("TR_OGGETTO").style.display='';
			document.getElementById("TR_CORPO").style.display='';
			showFrontendVars();
			
		}
		
		/*
		else
		{
			
			document.getElementById("TR_OGGETTO").style.display='';
			document.getElementById("TR_CORPO").style.display='';
			
		}
		*/
	}	
	
	
	
	function showALLVars(){
		jQuery('*[ambito]').show();
	}
	
	function showFrontendVars(){
		jQuery('*[ambito]').hide();
		jQuery('*[ambito=frontend]').show();
	}
	
</script>


	<span class="titoloPagina">
		<c:if test="${mailtipo.id.codice==null}">
			<fmt:message key="mailtipo.label.nuovo_mailtipo.title" />
		</c:if> 
		<c:if test="${mailtipo.id.codice!=null}">
			<fmt:message key="mailtipo.label.dettaglio_mailtipo.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="mailtipo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mailtipo" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="mailtipo.label.ambito" />
					</td>
					<td>
						<spring-form:select id="ambito_id" path="ambito" onchange="cambiaAmbito()">
						    <spring-form:option value=""><fmt:message key="label.seleziona"/></spring-form:option>
						    <spring-form:option value="F"><fmt:message key="mailtipo.label.ambito.item_frontend" /></spring-form:option>
							<spring-form:option value="M"><fmt:message key="mailtipo.label.ambito.item_mail" /></spring-form:option>
							<spring-form:option value="P"><fmt:message key="mailtipo.label.ambito.item_protocollo" /></spring-form:option>
							<spring-form:option value="C"><fmt:message key="mailtipo.label.ambito.item_commissioni_e_conferenze" /></spring-form:option>
						</spring-form:select>
						<spring-form:errors path="ambito" cssClass="error"/>												
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="descrizione" size="67"/>
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
				<c:set var="displayOggetto" value="" scope="page"/>
				<c:set var="displayCorpo" value="" scope="page"/>
				<c:if test="${mailtipo.ambito eq 'C'}">
					<c:set var="displayOggetto" value="none;" scope="page"/>
				</c:if>				
				<tr id="TR_OGGETTO" style="display:${displayOggetto}">
					<td>
						<fmt:message key="label.oggetto_email" />
					</td>
					<td>
						<spring-form:textarea id="oggetto_id" path="oggetto" cols="70" rows="4" />
						<spring-form:errors path="oggetto" cssClass="error"/>
					</td>
				</tr>	
				<c:if test="${mailtipo.ambito eq 'P'}">
					<c:set var="displayCorpo" value="none;" scope="page"/>
				</c:if>				
				<tr id="TR_CORPO" style="display:${displayCorpo}">
					<td>
						<fmt:message key="label.corpo_mail" />
					</td>
					<td>
						<spring-form:textarea id="corpo_id" path="corpo" cols="70" rows="8" />
						<spring-form:errors path="corpo" cssClass="error"/>
					</td>
				</tr>				
			</table>			
			<br />
			<fieldset><legend><fmt:message key="mailtipo.label.legenda" /></legend>
			<br />
			
			<table border="1" style="width: 100%;">
				<tr  class="intestazionetabella" ambito="generico">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_generali" /></b></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[DATIGEN_DEN]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.denominazione_comune" /></span></td>
					<td><span ambito="generico">[DATIGEN_COD_ACCR]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.cod_accr" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr class="intestazionetabella" ambito="generico">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_sportello" /></b></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[DATISPO_DEN]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.denominazione_sportello" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr class="intestazionetabella">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_richiedente" /></b></td>
				</tr>
				<tr>
					<td><span ambito="frontend">[1]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.richiedente" /></span></td>
					<td><span ambito="frontend">[RIC_CF]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.richiedente_cf" /></span></td>
					<td><span ambito="generico">[RIC_PIVA]</span></td>
					<td ><span ambito="generico"><fmt:message key="mailtipo.label.legenda.richiedente_pi" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><span ambito="frontend">[2]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.indirizzo" /></span></td>
					<td><span ambito="frontend">[3]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.citta" /></span></td>
					<td><span ambito="frontend">[4]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.cap" /></span></td>
					<td><span ambito="frontend">[5]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.provincia" /></span></td>
				</tr>
				<tr>
					<td><span ambito="frontend">[RIC_CN]</span></td>
					<td><span ambito="frontend"><fmt:message key="label.comune_nascita" /></span></td>
					<td><span ambito="frontend">[RIC_DN]</span></td>
					<td><span ambito="frontend"><fmt:message key="label.data_nascita" /></span></td>
					<td></td>
					<td></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>				
				<tr class="intestazionetabella">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_azienda_richiedente" /></b></td>
				</tr>
				<tr>
					<td><span ambito="frontend">[AZRIC_CF]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.cf_azienda" /></span></td>
					<td><span ambito="frontend">[AZRIC_DEN]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.denominazione_azienda" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr class="intestazionetabella">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_istanza" /></b></td>
				</tr>
				<tr>
					<td><span ambito="generico">[20]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.codice_istanza" /></span></td>
					<td><span ambito="frontend">[6]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.data_istanza" /></span></td>
					<td><span ambito="generico">[7]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.numero_protocollo_istanza" /></span></td>
					<td><span ambito="generico">[8]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.data_protocollo_istanza" /></span></td>
				</tr>
				<tr>
					<td><span ambito="frontend">[13]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.descrizione_lavori" /></span></td>
					<td><span ambito="generico">[24]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.password" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[17]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.operatore" /></span></td>
					<td><span ambito="generico">[28]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.tecnico" /></span></td>
					<td><span ambito="generico">[18]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.responsabile_procedimento" /></span></td>
					<td><span ambito="generico">[29]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.cointestatari" /></span></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[12]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.lotto" /></span></td>
					<td><span ambito="generico">[16]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.sub" /></span></td>
					<td><span ambito="generico">[15]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.particella" /></span></td>
					<td><span ambito="generico">[14]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.foglio" /></span></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[9]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.intervento" /></span></td>
					<td><span ambito="generico">[10]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.tipoprocedura" /></span></td>
					<td><span ambito="generico">[21]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.impianto" /></span></td>
					<td><span ambito="generico">[11]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.area_industriale" /></span></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[26]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.variante_prg" /></span></td>
					<td><span ambito="generico">[25]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.valutazione_impatto_ambientale" /></span></td>
					<td><span ambito="generico">[19]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.lista_endo_attivati" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[30]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.settore_istat" /></span></td>
					<td><span ambito="generico">[31]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.attivita_istat" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><span ambito="generico">[40]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.cod_istanza_people" /></span></td>
					<td><span ambito="generico">[COMUNE_ISTANZA]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.comune_istanza" /></span></td>
					<td><span ambito="generico">[INTERVENTODAALBEROPRIMAVOCE]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.prima_voce_albero" /></span></td>
					<td><span ambito="frontend">[INQUALITADI]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.in_qualita_di" /></span></td>
					
					
					
				</tr>
				<%--Dati sulle informazioni di localizzazione di un istanza --%>
				<tr class="intestazionetabella" ambito="generico">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_localizzazione_istanza" /></b></td>
				</tr>
				<tr  ambito="generico">
					<td><span ambito="generico">[23]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.localizzazione" /></span></td>
					<%-- Segna posto che riporta la localizzazione estesa "Es. Via Manzoni 45/A rosso" o Via Manzoni Km 13/ rosso --%>
					<td><span ambito="generico">[LOC_ESTESA]</span></td>
					<td colspan="2">
						<span ambito="generico"><fmt:message key="mailtipo.label.legenda.localita_estesa" />
						<init:help idHelp="helpMailtipolocalitaEstesa" textKey="help.mailtipo.legenda_localita_estesa"/></span></td>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<%-- Segna posto che riportano valori della localizzazione (istanzestradario) Es.esponente,colore,scala... --%>
				<tr  ambito="generico">
				    <td><span ambito="generico">[22]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.civico" /></span></td>
					<td><span ambito="generico">[ESPONENTE]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.esponente" /></span></td>
					<td><span ambito="generico">[COLORE]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.colore" /></span></td>
					<td><span ambito="generico">[SCALA]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.scala" /></span></td>
				</tr>
				<tr  ambito="generico">
					<td><span ambito="generico">[PIANO]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.piano" /></span></td>
					<td><span ambito="generico">[INTERNO]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.interno" /></span></td>
					<td><span ambito="generico">[ESPONENTE_INTERNO]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.esponente_interno" /></span></td>
					<td><span ambito="generico">[FABBRICATO]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.fabbricato" /></span></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[KM]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.km" /></span></td>
					<td><span ambito="generico">[CAP]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.cap" /></span></td>
					<td><span ambito="generico">[FRAZIONE]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.frazione" /></span></td>
					<td><span ambito="generico">[CIRCOSCRIZIONE]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.circoscrizione" /></span></td>
				
				<tr ambito="generico">
					<td><span ambito="generico">[QUARTIERE]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.quartiere" /></span></td>
					<td><span ambito="generico">[NOTE]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.note" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				
				</tr>
				<tr class="intestazionetabella" ambito="generico">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_movimento" /></b></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[32]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.tipomovimento" /></span></td>
					<td><span ambito="generico">[33]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.endoprocedimento" /></span></td>
					<td><span ambito="generico">[34]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.nr_data_protocollo" /></span></td>
					<td><span ambito="generico">[35]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.esito" /></span></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[36]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.parere" /></span></td>
					<td><span ambito="generico">[37]</span></td>
					<td ><span ambito="generico"><fmt:message key="mailtipo.label.legenda.movimento_data" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[MOVIMENTI_FKIDPROTOCOLLO]</span></td>
					<td><span ambito="generico"><fmt:message key="label.identificativo_protocollo" /></span></td>				
					<td><span ambito="generico">[MOVIMENTI_NUMPROT]</span></td>
					<td><span ambito="generico"><fmt:message key="label.numero_protocollo_no_anno" /></span></td>
					<td><span ambito="generico">[MOVIMENTI_ANNOPROT]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.data_movimento_protocollo" /></span></td>
					<td><span ambito="generico">[MOV_DATAPROT]</span></td>
					<td>&nbsp;</td>					
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[LINKALLEGATI]</span></td>
					<td><span ambito="generico"><fmt:message key="label.link_allegati_mail" /></span></td>	
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<%-- DATI DEL MOVIMENTO SEZIONE ENDOPROCEDIMENTI --%>
				<tr class="intestazionetabella" ambito="generico">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_movimento_endoprocedimenti"/></b></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[MOV_ENDO_ATTONUM]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.movimento_endo_numeroatto" /></span></td>				
					<td><span ambito="generico">[MOV_ENDO_ATTODATA]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.movimento_endo_dataatto" /></span></td>
					<td><span ambito="generico">[MOV_ENDO_ATTOTIPO]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.movimento_endo_tipoatto" /></span></td>
					<td><span ambito="generico">[MOV_ENDO_ATTOENTE]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.movimento_endo_noteatto" /></span></td>
				</tr>
					<td><span ambito="generico">[MOV_ENDO_ATTONOTE]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.movimento_endo_noteatto" /></span></td>				
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				
				<tr class="intestazionetabella" ambito="generico">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_autorizzazione" /></b></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[38(<b>CodReg</b>)]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.nr_autorizzazione" /></span></td>
					<td><span ambito="generico">[39(<b>CodReg</b>)]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.data_autorizzazione" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr class="intestazionetabella" ambito="generico">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_sorteggi" /></b></td>
				</tr>
				<tr ambito="generico">
					<td><span ambito="generico">[41]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.data_sorteggio" /></span></td>
					<td><span ambito="generico">[42]</span></td>
					<td><span ambito="generico"><fmt:message key="mailtipo.label.legenda.descrizione" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr class="intestazionetabella">
					<td colspan="8"><b><fmt:message key="mailtipo.label.legenda.dati_altri_dati" /></b></td>
				</tr>
				<tr>
					<td><span ambito="frontend">[27]</span></td>
					<td><span ambito="frontend"><fmt:message key="mailtipo.label.legenda.data_odierna" /></span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			</fieldset>
			<script type='text/javascript'>
			
				$('descrizione_id').focus();

				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${mailtipo.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mailtipo.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>