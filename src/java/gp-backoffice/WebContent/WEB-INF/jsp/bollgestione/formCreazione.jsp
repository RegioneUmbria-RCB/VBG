<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
		<fmt:message key="bollgestione.label.nuova_bollettazione.title" />		
	</title>
	<script type="text/javascript">
	function cercaProcedimentoAjax(codiceAlberoproc, descrizioneAlberoproc){
		if(isNaN(codiceAlberoproc)){
			alert("Ricerca per codice. Inserire un valore numerico");
			return;
		}
		new Ajax.Request('../json/getAlberoprocHelper.htm', {
			  method: 'post',
			  parameters: {id: codiceAlberoproc},
			  onSuccess: function(transport){ 
				var response = transport.responseText;
				//alert(response);
				var json = response.evalJSON();
				if(json.id){
					assegnaIntervento(json.scCodice, descrizioneAlberoproc);
				}else{
					alert("Procedimento non trovato o disattivato.");
			    }
			  },
			  onFailure: function(transport){ 
				var response = transport.responseText; 
			    alert("Errore nella ricerca del procedimento!");
			  }						    		 
		} );
	}
	
	function assegnaIntervento(codiceAlberoproc, descrizioneAlberoproc){
		
		var elementId = 'scCodice_' + codiceAlberoproc +'_'+ new Date().getTime();
		var htmlDaScrivere = '<div id="'+elementId+'_id"><span><a class="eliminaRiga" style="float: none;" href="javascript:eliminaIntervento(\''+ elementId +'\')" title="<fmt:message key="label.elimina" />"><label><fmt:message key="label.elimina.image" /></label></a></span>'+
							 '&nbsp;<input type="hidden" name="'+elementId+'" id="'+elementId+
							 '" value="'+codiceAlberoproc+'" />'+descrizioneAlberoproc+'&nbsp;('+codiceAlberoproc+
							 ')&nbsp;</div>' ;
		if(document.getElementById('lista_interventi')){
			document.getElementById('lista_interventi').innerHTML = document.getElementById('lista_interventi').innerHTML + htmlDaScrivere;
			applyStyle();
		}
	}

	function eliminaIntervento(elementId){		
		//if(confirm('<fmt:message key="javascript.confirm.delete" />')){
			$(elementId).value='';
			$(elementId+'_id').style.display='none';
		//}
	}	
	
	async function checkTipoBollettazioneImplementazione(obj){
		
		var tipo = jQuery(obj).val();
		disableFunctions();
		var jhqrPr = jQuery.ajax({
			  url: '../bollgestione/ajaxVerificaTipoBollettazione.htm',
			  data: "id="+tipo,
			  context: document.body,
			  cache: false,					  
			  dataType: "json",
			  success: function(result){
				  enableFunctions();
				  mostraNascondi(result.implementazione);
				  aggiornaLabelPeriodo();
				  enableFunctions();
			  },
		      error: function (xhr, ajaxOptions, thrownError) {
		    	enableFunctions()
		        alert(xhr.status);
		        alert(thrownError);
		      } 
		});
	}
	
	function checkTipoBollettazione(obj){

		var tipo = jQuery(obj).val();
		disableFunctions();
		var jhqrPr = jQuery.ajax({
			  url: '../bollgestione/ajaxVerificaTipoBollettazione.htm',
			  data: "id="+tipo,
			  context: document.body,
			  cache: false,					  
			  dataType: "json",
			  success: function(result){
				  mostraNascondi(result.implementazione);
				  jQuery('#data_inizio').val(result.data_inizio);
				  jQuery('#data_fine').val(result.data_fine);
				  aggiornaLabelPeriodo();
				  enableFunctions();
			  },
		      error: function (xhr, ajaxOptions, thrownError) {
		    	  enableFunctions()
		        alert(xhr.status);
		        alert(thrownError);
		      } 
		});
				
	}
	
	
	function mostraNascondi(tipo){
		if(tipo==='Mercati'){
			jQuery('.filtriIstanze').hide();			
			jQuery('.filtriMercati').show();
		}else if(tipo==='Istanze'){
			jQuery('.filtriIstanze').show();			
			jQuery('.filtriMercati').hide();
		}else{
			jQuery('.filtriIstanze').hide();			
			jQuery('.filtriMercati').hide();
			alert('Tipo '+tipo+' non valido');
		}
		
	}
	
	function aggiornaLabelPeriodo(){
		
		var descrizione_periodo = "da "+jQuery('#data_inizio').val()+ " a "+jQuery('#data_fine').val();
		jQuery('#periodo_label').html(descrizione_periodo); 
		
	}
	
	
	function ajaxAggiornaPeriodo(){
		
			var jhqrPr = jQuery.ajax({
				  url: '../bollgestione/ajaxAggiornaPeriodo.htm',
				  data: "anno=" +jQuery('#periodo_anno option:selected').text()+"&chiave="+jQuery('input[name="elementi"]:checked').val(),
				  context: document.body,
				  cache: false,					  
				  dataType: "json",
				  success: function(result){
					
					 jQuery('#data_inizio').val(result.data_inizio);
					 jQuery('#data_fine').val(result.data_fine);
					 console.log(result);
					 aggiornaLabelPeriodo();
					 jQuery('#dialog_modifica_calcolo_periodo').dialog('close')
					 enableFunctions();
				  },
			      error: function (xhr, ajaxOptions, thrownError) {
			    	enableFunctions()
			        alert(xhr.status);
			        alert(thrownError);
			      } 
			});
		
	}
	
	vbg.ready(() => {
		<c:choose>
		<c:when test="${empty IS_ERRORE}">
			checkTipoBollettazione(document.getElementById('tipologiaBoll_id'));
		</c:when>
		<c:otherwise>
				checkTipoBollettazioneImplementazione(document.getElementById('tipologiaBoll_id'));
		</c:otherwise>		
		</c:choose>
		
		jQuery('#modifica_calcolo_periodo').click(function(){
			
			var jhqrPr = jQuery.ajax({
				  url: '../bollgestione/ajaxCalcolaPeriodo.htm',
				  data: "bollCfgTipoId=" +jQuery('#tipologiaBoll_id').val(),
				  context: document.body,
				  cache: false,					  
				  dataType: "text",
				  success: function(result){
					
					  jQuery('#dialog_modifica_calcolo_periodo').dialog({
						  title:"Modifica periodo",
						  show: true,
						  closeX:true,
						  modal: true
					  }).html(result);
					  enableFunctions();
				  },
			      error: function (xhr, ajaxOptions, thrownError) {
			    	enableFunctions()
			        alert(xhr.status);
			        alert(thrownError);
			      } 
			});
		});
		
		
	});
	
	</script>
	
</head>
<body>
	<span class="titoloPagina">		
		<fmt:message key="bollgestione.label.nuova_bollettazione.title" />		
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<div id="dialog_modifica_calcolo_periodo" ></div>
		<spring-form:form commandName="bollGestTestata" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="bollGestTestata" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="bollgestione.label.tipo.table" />
					</td>
					<td>
					
						<spring-form:select path="bollCfgTipoId" id="tipologiaBoll_id"  items="${bollCfgTipo}" itemValue="bollCfgTipoId" itemLabel="descrizione" onchange="checkTipoBollettazione(this)" />
						<init:help idHelp="help1" textKey="bollettazione.help.tipo"/>
						<spring-form:errors path="bollCfgTipoId" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="bollgestione.label.periodo.table" /> da: 
					</td>
					<td>
						<spring-form:input size="10" path="intervalloDate.dataInizio" id="data_inizio"/>
						a: 
						<spring-form:input size="10" path="intervalloDate.dataFine" id="data_fine"/>
						<label id="periodo_label" style="display: none"></label>
						<a href="#" id = "modifica_calcolo_periodo">modifica</a>
						
					</td>
				
				</tr>
				<tr>
					<td><fmt:message key="bollgestione.label.descrizione.table" /></td>
					<td>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />
						<spring-form:errors path="descrizione" cssClass="error"/>
					
					</td>
				</tr>
				
				
				<tr class="filtriIstanze">
					<td>
						<fmt:message key="label.comune" />
					</td>
					<td>
					
						<spring-form:select path="comuni" id="comuni_id"  items="${comuniassociatiListInRequest}" itemValue="codicecomune" itemLabel="comune" />
						<init:help idHelp="help2" textKey="bollettazione.help.comune"/>
						<spring-form:errors path="comuni" cssClass="error"/>
					</td>
				</tr>			
				
			</table>
			
             <fieldset class="filtriIstanze">
	             <legend><fmt:message key="label.interventi_selezionati" /></legend>
	             <form action="" id="cancella_p" name="cancella_p" method="post">
	             
		             <div id="lista_interventi">
		
		             </div>
		             
	             </form>
	             <br class="break" />
             </fieldset>
			<fieldset class="filtriIstanze">
				<legend><fmt:message key="label.bollettazione.seleziona_interventi" /></legend>
				<div>
						
					<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" url="${pageContext.request.contextPath}/json/getAlberoproc.htm?time=<%=System.currentTimeMillis() %>"></div>
					
					<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore"	query="{root:'1'}" rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>
					
					<div dojoType="dijit.Tree" id="tree2" model="alberoprocModel" />
					
				   	<script type="dojo/method" event="onClick" args="item">
		if(item.id != '0'){
			var itemId = alberoprocStore.getValue(item, "id");
			var descrizioneEstesa = alberoprocStore.getValue(item, "descrizioneEstesa");
			var padre = alberoprocStore.getValue(item, "padre");
			cercaProcedimentoAjax(itemId, descrizioneEstesa)
		}
   		</script>
				
				   <script type="dojo/method" event="getIconClass" args="item, opened">
		if(item.id != '0'){
			var dis = 'false';
			if(item){
				dis = alberoprocStore.getValue(item, "disabilitato");
			}
			if(dis == 'false'){
				return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf"
			}else{
				return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled"
			}
		}else{
			return "dijitFolderOpened"
		}
   		</script>   		
				</div>
			</fieldset>	
	
	
	
	
	
		</spring-form:form>
	</div>
	
	<div id="functions">
		<ul>
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
				<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>