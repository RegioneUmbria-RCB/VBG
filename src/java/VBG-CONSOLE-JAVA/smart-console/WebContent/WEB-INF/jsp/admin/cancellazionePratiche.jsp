<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.pannello_di_amministrazione" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.pannello_di_amministrazione" />	
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../admin/view" />
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="administration" />
	</jsp:include>
	<div id="subcontent">
	<div id="error_msg" class="error_header" >
		Attenzione! Si stanno per cancellare <b>${istanzeDaCancellare}</b> pratiche per il software <b>${descrizioneSoftware}</b>.
	</div>
		<br class="clear" />
		<select name="codicesoftware" id="codicesoftwareId" onchange="cambiaSoftware(this);">
			<option value="">...</option>
			<c:forEach items="${listaSoftwareAttivi}" var="software">
				<option value="${software.codice }">${software.descrizione}</option>
			</c:forEach>
		</select>		
		<br />
		<br class="clear"/>
     
             <fieldset>
	             <legend><fmt:message key="label.interventi_selezionati" /></legend>
	             <form action="" id="cancella_p" name="cancella_p" method="post">
	             
		             <div id="lista_interventi">
		
		             </div>
		             
	             </form>
	             <br class="break" />
             </fieldset>
<fieldset>
		<legend>Selezionare uno o più interventi da utilizzare per eliminare le pratiche</legend>
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
		<%--
		<input type="hidden" name="codiceIntervento" id="codiceInterventoId" value="" />
		<input type="hidden" name="descrizioneIntervento" id="descrizioneInterventoId" value="" />
		--%>
		<div id="functions">
			<ul>
				<li><a href="javascript:void 0;" onclick="cancellaIstanzaConfirm();">Elimina le istanze</a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
		
		
		
								<div dojoType="dijit.Dialog" id="cancellaIstanzeDialogDiv" title="<fmt:message key="label.conferma_cancellazione" />"  style="display: none;">
									<input type="checkbox" id="cancellazioneistanzachk_id" onclick="showHideDiv('doDeleteId')"/>
									<label for="cancellazioneistanzachk_id">
										<fmt:message key="label.messaggio_cancellazione_istanza_per_operatore">
											<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
											<fmt:param>Tutte le istanze corrispondenti ai filtri</fmt:param>
										</fmt:message>
									</label>
									<div id="functions">
										<ul>
											<li style="display: none;" id="doDeleteId"><a href="javascript:eliminaPratiche()"><fmt:message key="button.delete" /></a></li>
											<li><a href="javascript:void 0" onclick="dijit.byId('cancellaIstanzeDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
										</ul>
									</div>
									<br class="clear" />	
								</div>
		
		
	</div>
<script type="text/javascript">

function cancellaIstanzaConfirm(){
	dijit.byId('cancellaIstanzeDialogDiv').show();
}

function eliminaPratiche(){

	doSubmit('../admin/cancellazionePraticheExec.htm','<fmt:message key="javascript.confirm.delete" />', $('cancella_p'));	
}

function cambiaSoftware(selectObj){
	var valori = getSelectTextAndValue(selectObj);
	if(valori[0]!=''){
		doHref('../admin/cancellazionePraticheConfirm.htm?software='+valori[0],'');
	}
}

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
				assegnaIntervento(codiceAlberoproc, descrizioneAlberoproc);
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

/*
function assegnaIntervento(codiceAlberoproc, descrizioneAlberoproc){
	alert("selezionato il procedimento: "+descrizioneAlberoproc);	
	var elementId = 'scId_' + codiceAlberoproc +'_'+ new Date().getTime();
	/*
	if(document.getElementById('codiceInterventoId')){
		document.getElementById('codiceInterventoId').value = codiceAlberoproc;
	}
	if(document.getElementById('descrizioneInterventoId')){
		document.getElementById('descrizioneInterventoId').value = descrizioneAlberoproc;
	}	
	var htmlDaScrivere = '&nbsp;<b>'+descrizioneAlberoproc+'&nbsp;('+codiceAlberoproc+ ')</b>' +
						 '<input type="hidden" name="'+elementId+'" id="'+elementId+
		 				 '" value="'+codiceAlberoproc+'" />'+descrizioneAlberoproc+'&nbsp;('+codiceAlberoproc+
		 				')&nbsp;';
	* /
	var htmlDaScrivere = '<div id="'+elementId+'_id"><span><a class="eliminaRiga" style="float: none;" href="javascript:eliminaIntervento(\''+ elementId +'\')" title="<fmt:message key="label.elimina" />"><label><fmt:message key="label.elimina.image" /></label></a></span>'+
	 					 '&nbsp;<input type="hidden" name="'+elementId+'" id="'+elementId+
	 					 '" value="'+codiceAlberoproc+'" />'+descrizioneAlberoproc+'&nbsp;('+codiceAlberoproc+
	 					 ')&nbsp;</div>';
	 					 alert(htmlDaScrivere);
	if(document.getElementById('lista_interventi')){
		document.getElementById('lista_interventi').innerHTML = document.getElementById('lista_interventi').innerHTML + htmlDaScrivere;
	}
	
}
*/
function assegnaIntervento(codiceAlberoproc, descrizioneAlberoproc){
	
	var elementId = 'scId_' + codiceAlberoproc +'_'+ new Date().getTime();
	var htmlDaScrivere = '<div id="'+elementId+'_id"><span><a class="eliminaRiga" style="float: none;" href="javascript:eliminaIntervento(\''+ elementId +'\')" title="<fmt:message key="label.elimina" />"><label><fmt:message key="label.elimina.image" /></label></a></span>'+
						 '&nbsp;<input type="hidden" name="'+elementId+'" id="'+elementId+
						 '" value="'+codiceAlberoproc+'" />'+descrizioneAlberoproc+'&nbsp;('+codiceAlberoproc+
						 ')&nbsp;</div>' ;
	if(document.getElementById('lista_interventi')){
		document.getElementById('lista_interventi').innerHTML = document.getElementById('lista_interventi').innerHTML + htmlDaScrivere;
	}
}

function eliminaIntervento(elementId){		
	if(confirm('<fmt:message key="javascript.confirm.delete" />')){
		$(elementId).value='';
		$(elementId+'_id').style.display='none';
	}
}		

</script>	
</body>
</html>