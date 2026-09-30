<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
			<fmt:message key="label.gruppi_smistamento" />		
	</title>
</head>
<body>
<span class="titoloPagina">
	
		<fmt:message key="label.gruppi_smistamento" />

</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
        <jsp:param name="commandName" value="gruppiSmistamentoCommand" />
    </jsp:include>
	<spring-form:form commandName="gruppiSmistamentoCommand" name="inviodati" id="inviodatiForm">
	
	<div id="dettaglioGruppi" style="padding-bottom:20px;">&nbsp;<img src='${pageContext.request.contextPath}/images/spinner.gif' /></div>
	
	
	<div id="messaggioErrore" class="error_header alert alert-danger" style="padding:20px;display:none;"></div>
	
	
	<div id="nuovaRigaDiv" style="display: none;">
		
	<table>
		<tr>
			<td width="20%"><fmt:message key="label.gruppi_smistamento.gruppo1" /></td>
			<td class="inline-ui-cell">
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="gruppo1_id" />				
					<jsp:param name="propertyPath" value="gruppo1" />			
					<jsp:param name="pathPropertyDescription" value="gruppo1.descrizione" />
					<jsp:param name="pathPropertyCode" value="gruppo1.id.codice" />
					<jsp:param name="autocompleterAjax" value="findGruppiendoprocedimentiT.htm" />
					<jsp:param name="titleKey" value="label.gruppi_smistamento.gruppo1" />
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td width="20%"><fmt:message key="label.gruppi_smistamento.gruppo2" /></td>
			<td class="inline-ui-cell">
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="gruppo2_id" />				
					<jsp:param name="propertyPath" value="gruppo2" />			
					<jsp:param name="pathPropertyDescription" value="gruppo2.descrizione" />
					<jsp:param name="pathPropertyCode" value="gruppo2.id.codice" />
					<jsp:param name="autocompleterAjax" value="findGruppiendoprocedimentiT.htm" />
					<jsp:param name="titleKey" value="label.gruppi_smistamento.gruppo2" />
				</jsp:include>				
			</td>
		</tr>
		<tr>
			<td width="20%"><fmt:message key="label.gruppi_smistamento.gruppo3" /></td>
			<td class="inline-ui-cell">
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="gruppo3_id" />				
					<jsp:param name="propertyPath" value="gruppo3" />			
					<jsp:param name="pathPropertyDescription" value="gruppo3.descrizione" />
					<jsp:param name="pathPropertyCode" value="gruppo3.id.codice" />
					<jsp:param name="autocompleterAjax" value="findGruppiendoprocedimentiT.htm" />
					<jsp:param name="titleKey" value="label.gruppi_smistamento.gruppo3" />
				</jsp:include>								
			</td>
		</tr>
		<tr>
			<td width="20%"><fmt:message key="label.gruppi_smistamento.intervento" /></td>
			<td class="inline-ui-cell">
				<spring-form:input	
					id="alberoproc_hidden" 
					path="alberoproc.id.codice"
					onchange="cercaProcedimento()" size="9"
					cssStyle="text-align: right;" /> 
									<a class="vbg-btn btn-cerca" href="javascript:cercaProcedimento();" title="Cerca procedimento" 
									style="vertical-align: bottom;" id="alberoimg_id">
									</a> 
									<spring-form:input id="alberoproc_descrestesa_hidden" path="alberoproc.descrizioneCompleta" size="100" readonly="true" /> 
									<spring-form:errors path="alberoproc" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			<div dojoType="dojo.data.ItemFileReadStore"
					jsId="alberoprocStore"
					url="${pageContext.request.contextPath}/json/getAlberoproc.htm?_timestamp=<%=String.valueOf(System.currentTimeMillis()) %>">
			</div>
			<div dojoType="dijit.tree.ForestStoreModel"
				jsId="alberoprocModel" store="alberoprocStore"
				query="{root:'1'}"
				rootId="<%=WebConstants.ATECO_CODICE_ROOT%>"
				rootLabel="<fmt:message key="label.albero_dei_procedimenti" />"
				childrenAttrs="children"></div> <br />
				<div id="treeOne"></div>
				<div id="mostraEndoDiv" style="border: 1px;">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td width="20%"><fmt:message key="label.gruppi_smistamento.procedura_scia" /></td>
			<td class="inline-ui-cell">
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="tipiprocedureScia_id" />				
					<jsp:param name="propertyPath" value="proceduraScia" />			
					<jsp:param name="pathPropertyDescription" value="proceduraScia.procedura" />
					<jsp:param name="pathPropertyCode" value="proceduraScia.id.codice" />
					<jsp:param name="autocompleterAjax" value="findTipiprocedure.htm?includiDisabilitate=false" />
					<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td width="20%"><fmt:message key="label.gruppi_smistamento.procedura_ordinario" /></td>
			<td class="inline-ui-cell">
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="tipiprocedureOrdinario_id" />				
					<jsp:param name="propertyPath" value="proceduraOrdinario" />			
					<jsp:param name="pathPropertyDescription" value="proceduraOrdinario.procedura" />
					<jsp:param name="pathPropertyCode" value="proceduraOrdinario.id.codice" />
					<jsp:param name="autocompleterAjax" value="findTipiprocedure.htm?includiDisabilitate=false" />
					<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
				</jsp:include>
			</td>
		</tr>		
	</table>	
	
		
	<div id="functions">
		<ul>
			<li><a href="javascript:salvaRiga();"><fmt:message key="button.ok" /></a></li>
	       <li><a href="javascript:annullaNuovaRiga();"><fmt:message key="button.annulla" /></a></li>
	    </ul>
	</div>
	
	
	</div>
	
	
	</spring-form:form>

	
	
	
	
	<a class="generaallegato vbg-btn btn-aggiungi" id="nuovaRiga_id" title="aggiungi" href="#" onclick="nuovaRiga()"><!-- img src="${pageContext.request.contextPath}/images/add.png"/>  --></a>
	

	


</div>
<br class="clear" /> 
<div id="functions">
	<ul>
       <li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
    </ul>
</div>

<script type="text/javascript">


function azzeraErrori(){
	jQuery('#messaggioErrore').html('');
	jQuery('#messaggioErrore').hide();
	
}
function salvaRiga(){
	
	azzeraErrori();
	
	
	var gruppo1=jQuery('#gruppo1_id_hidden').val();
	var gruppo2=jQuery('#gruppo2_id_hidden').val();
	var gruppo3=jQuery('#gruppo3_id_hidden').val();	
	var alberoproc_hidden=jQuery('#alberoproc_hidden').val();
	var procedura_scia=jQuery('#tipiprocedureScia_id_hidden').val();
	var procedura_ordinario=jQuery('#tipiprocedureOrdinario_id_hidden').val();
	if(checkValoreObbligatorio(alberoproc_hidden, "Intervento") 
			&& (checkValoreObbligatorio(gruppo1, "Gruppo 1") ||checkValoreObbligatorio(gruppo2, "Gruppo 2") ||checkValoreObbligatorio(gruppo3, "Gruppo 3")) 
			/* && (checkValoreObbligatorio(procedura_scia, "Procedura scia") ||checkValoreObbligatorio(procedura_ordinario, "Procedura Ordinaria") )*/ ){
		disableFunctions();
		
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/alberoprocgruppismist/ajaxSalvaRiga.htm?'+jQuery('#inviodatiForm').serialize(),
			  method: "POST",
			  context: document.body,			  
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
				  enableFunctions();	
				  verificaErroreoSuccesso(data);
			  },
			  error: function(jqXHR, textStatus, errorThrown){
				  	enableFunctions();
					alert(jqXHR.responseText);
			  }
				  
		});
	}	
}


function checkValoreObbligatorio(val, nomeCampo){
	if(!val){
		alert("Attenzione! E' necessario impostare un valore per "+nomeCampo);
		return false;
	}
	return true;	
}

function annullaNuovaRiga(){
	jQuery('#nuovaRiga_id').show();
	jQuery('#nuovaRigaDiv').hide();
}


function nuovaRiga(){
	jQuery('#nuovaRigaDiv').show();
	jQuery('#nuovaRiga_id').hide();
}

var visualizzaDettaglioInfo = function(){
	
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/alberoprocgruppismist/ajaxDettaglioGruppi.htm',
			  context: document.body,
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
				  if(data){					  
					  	jQuery('#dettaglioGruppi').html(data);
					  	jQuery('#dettaglioGruppi').show();
				  }					  
			  }
		});

}


	function azzeraAutoCompleter(){
		jQuery('#inventarioprocedimento_id').val('');
		jQuery('#inventarioprocedimento_hidden').val('');
	}

function eliminaRiga(idRiga){		
	if(confirm('<fmt:message key="javascript.confirm.delete" />')){
		disableFunctions();
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/alberoprocgruppismist/ajaxEliminaConfigurazione.htm?idRiga='+idRiga,
			  context: document.body,
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
				  	enableFunctions();
					verificaErroreoSuccesso(data);
			  }
			});
	}
}	

function cancellaErrore(){
	jQuery('#messaggioErrore').hide();
	jQuery('#messaggioErrore').html('');
}

function verificaErroreoSuccesso(data){
	 // verifica errori o altro e aggiorna
	 if(data=='OK'){
	 	visualizzaDettaglioInfo();
	 }else{
		 jQuery('#messaggioErrore').html(data);
		 jQuery('#messaggioErrore').show();
	 }
}
<c:if test="${gruppiSmistamentoCommand.displayMode == gruppiSmistamentoCommand.displayConstants.VIEW}">
	jQuery(document).ready(function(){
		visualizzaDettaglioInfo(); 
	});
</c:if>


								var treeControl = null;
								var treeInitialized = false;
								
								function cercaProcedimento(){									
									var codProc = $('alberoproc_hidden').value;
									rimuoviValori2();
									if(codProc){
										cercaProcedimentoAjax(codProc);	
									}else{
										apriAlbero();
									}
									$('alberoproc_hidden').focus();
								}
								
								function cercaProcedimentoAjax(codiceAlberoproc){
									if(isNaN(codiceAlberoproc)){
										alert("Ricerca per codice. Inserire un valore numerico");
										return;
									}
									new Ajax.Request('../json/getAlberoprocHelper.htm?hideDisabled=true', {
										  method: 'post',
										  parameters: {id: codiceAlberoproc},
										  onSuccess: function(transport){ 
											var response = transport.responseText;
											var json = response.evalJSON();
											if(json.id){
												if(json.padre == 'true'){
													alert("Procedimento non selezionabile.");
												}else{
													assegnaValori2(json);
													$('treeOne').style.display="none";
												}						
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
								
								jQuery($('alberoproc_hidden')).keypress(function(e) {
							  	  	var code = e.keyCode ? e.keyCode : e.which;
									if(code.toString() == 13) {
										cercaProcedimento(); 
									}
							    });
								
								function apriAlbero() {
							        if(!treeControl){
								        treeControl = new dijit.Tree({
								            model: alberoprocModel,
								            showRoot: true,							            
								            onClick: function(item, node){
								            	if(item.id !='0' ){	
									        		var itemId = alberoprocStore.getValue(item, "id");
									        		if(itemId>0){
														cercaProcedimentoAjax(itemId);
														$('alberoproc_hidden').focus();
									        		}
								            	}
								            },
								            getIconClass: function(item,opened){							            	
								            	treeInitialized = true;
								            	if(item.id!='0'){
									        		var dis = 'false';
									        		if(item){
									        			dis = alberoprocStore.getValue(item, "disabilitato");
									        		}
									        		if(dis == 'false'){
									        			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf";
									        		}else{
									        			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled";
									        		}
								            	}else{
								            		return "dijitFolderOpened"
								            	}	
								            }
								        },
								        "treeOne");
							        }else{
							        	document.getElementById('treeOne').style.display="";
							        }
							    }

								
								function assegnaValori2(map){
									$('alberoproc_hidden').value = map.id;
									$('alberoproc_descrestesa_hidden').value = map.desc;
								}
								
								
								function rimuoviValori2(){
									$('alberoproc_hidden').value = '';
									$('alberoproc_descrestesa_hidden').value = '';
								}
								
								</script>


</body>
</html>
