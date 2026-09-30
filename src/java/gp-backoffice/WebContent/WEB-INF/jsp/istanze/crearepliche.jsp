<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<fmt:message key="label.replica_della_istanza" />
</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.replica_della_istanza" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
	<spring-form:form commandName="istanze" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="istanze" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanze/creaReplicheView" />
		<jsp:param name="qs" value="codiceIstanza%3D${param.codiceIstanza}" />
	</jsp:include>	
	
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${param.codiceIstanza}</c:param>
		</c:import>
 		<br class="clear" />
	
	
			<fieldset>
				<legend><fmt:message key="label.istanza_di_origine" /></legend>
		 		<div class="jmesa" >
					<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td><fmt:message key="label.codice_istanza" /></td>
								<td><fmt:message key="label.data" /></td>
								<td><fmt:message key="label.numero_protocollo" /></td>
								<td><fmt:message key="label.data_protocollo" /></td>
								<td><fmt:message key="label.richiedente" /></td>
								<td><fmt:message key="label.intervento" /></td>
							</tr>
						</thead>
						<tbody class="tbody" >
						<tr class="even">
							<td>${istanzaPadre.numeroistanza}</td>
							<td><fmt:formatDate value="${istanzaPadre.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
							<td>${istanzaPadre.numeroprotocollo}</td>
							<td><fmt:formatDate value="${istanzaPadre.dataprotocollo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
							<td>${istanzaPadre.richiedente.descrizioneRichiedente}</td>
							<td>${istanzaPadre.alberoproc.vwAlberoproc.scDescrizione }</td>
						</tr>
						</tbody>
					</table>
				</div>
		 	</fieldset>
		 	<c:if test="${istanzaPadre.attivita!=null}">
		 	<br class="clear"/>
		 	<%-- <fmt:message key="label.gestisci_attivita_collegata" /> 
		 	<init:help idHelp="help_gestisci_attivita_collegata" textKey="help.crea_repliche_gestisci_attivita_collegata"/>--%>
		 	<div>
			 	<input id="gestisci_attivita_collegata_id" type="checkbox" value="1"/>
			 	<label>Se selezionato, l'istanza creata verrà associata all'attività <b>"${istanzaPadre.attivita.denominazione}"</b> collegata 
			 	       all' Istanza d'origine. Inoltre verranno ricalcolate le caratteristiche dell'attività: "Operante", "Attiva", "Ultima istanza". 
			 	</label>	
		 	</div>
		 	</c:if>
		 	<br class="clear"/>
			<fieldset>
			<legend><fmt:message key="label.interventi_selezionati" /></legend>
			<div id="lista_interventi">
			
			</div>
			<br class="break" />			
			</fieldset>
			<br class="clear"/>
	<fieldset>
	<legend><fmt:message key="label.crea_repliche_seleziona_interventi" /></legend>
	<div>
			
		<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" url="${pageContext.request.contextPath}/json/getAlberoproc.htm?time=<%=System.currentTimeMillis() %>"></div>
		
		<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore"	query="{root:'1'}" rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>
		
		<div dojoType="dijit.Tree" id="tree2" model="alberoprocModel" />
		
	   	<script type="dojo/method" event="onClick" args="item">
		

    

		if(item.id != '0'){
			<%--
			//Determine the attributes we need to process.
    		var attributes = alberoprocStore.getAttributes(item);
    		if (attributes && attributes.length > 0) {
      		var i;
      			for (i = 0; i < attributes.length; i++) {
					alert(attributes[i]);
					var values = alberoprocStore.getValues(item, attributes[i]);
					alert(values);	
      			}
    		}
			*/
			--%>
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

	<br class="break" />
	
	</spring-form:form>
</div>


<script type="text/javascript">
	

	function assegnaIntervento(codiceAlberoproc, descrizioneAlberoproc){
		
		var elementId = 'scId_' + codiceAlberoproc +'_'+ new Date().getTime();
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
		if(confirm('<fmt:message key="javascript.confirm.delete" />')){
			$(elementId).value='';
			$(elementId+'_id').style.display='none';
		}
	}			
	
	
	function cercaProcedimentoAjax(codiceAlberoproc, descrizioneAlberoproc){
		if(isNaN(codiceAlberoproc)){
			alert("Ricerca per codice. Inserire un valore numerico");
			return;
		}
		new Ajax.Request('../json/getAlberoprocHelper.htm?hideDisabled=true', {
			  method: 'post',
			  parameters: {id: codiceAlberoproc},
			  onSuccess: function(transport){ 
				var response = transport.responseText;
				//alert(response);
				var json = response.evalJSON();
				if(json.id){
					if(json.padre == 'true'){
						alert("Procedimento non selezionabile.");
					}else{
						assegnaIntervento(codiceAlberoproc, descrizioneAlberoproc);
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
	
	function creaRepliche()
	{
		
		
		if((document.getElementById("gestisci_attivita_collegata_id")!=null && document.getElementById("gestisci_attivita_collegata_id").checked==true))
		{
			doSubmit('creaRepliche.htm?codiceIstanza=${param.codiceIstanza}&isGestisciAttivita=true','<fmt:message key="label.messaggio_conferma_crea_repliche" />',document.inviodati);
		}else
		{
			doSubmit('creaRepliche.htm?codiceIstanza=${param.codiceIstanza}&isGestisciAttivita=false','<fmt:message key="label.messaggio_conferma_crea_repliche" />',document.inviodati);
		}
		
	}
	
	
</script>
<div id="functions">
	<ul>
	<%-- 	<li><a href="javascript:doSubmit('creaRepliche.htm?codiceIstanza=${param.codiceIstanza}&isGestisciAttivita=document','<fmt:message key="label.messaggio_conferma_crea_repliche" />',document.inviodati);"><fmt:message key="button.crea_repliche" /></a></li> --%>
		<li><a href="javascript:creaRepliche()"><fmt:message key="button.crea_repliche" /></a></li>
		<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>