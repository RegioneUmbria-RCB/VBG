<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>	
	<fmt:message key="label.cds" />
</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.cds" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<jsp:include page="../includes/history.jsp">
   	<jsp:param name="path" value="../cds/view" />
   	<jsp:param name="qs" value="codiceIstanza%3D${cds.istanze.id.codice}"/>
</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
	<c:param name="codIstanza">${param.codiceIstanza}</c:param>
</c:import>
	
<br class="clear" />
<div id="subcontent">
	<spring-form:form commandName="cds" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="cds" />
    </jsp:include>
	<table width="100%">
		
		<tr>
			<td><fmt:message  key="label.oggetto" /></td>
			<td colspan="3" >
				<spring-form:textarea id="odg_id" path="odg" cols="70" rows="5" />				
				<spring-form:errors path="odg" cssClass="error"/>				
			</td>
		 </tr>

		 <tr>
			<td><fmt:message  key="label.note" /></td>
			<td colspan="3">
				<spring-form:textarea id="note_id" path="note" cols="70" rows="5" />
				<spring-form:errors path="note" cssClass="error"/>
			</td>
		 </tr>

		 <tr>
			<td><label for="invitorichiedente_id"><fmt:message  key="label.cds_invitorichiedente" /></label></td>
			<td colspan="3">
				<spring-form:checkbox id="invitorichiedente_id" path="invitorichiedente" />
				<spring-form:errors path="invitorichiedente" cssClass="error"/>
			</td>
		 </tr>
		 <tr>
			<td><label for="flagvia_id"><fmt:message  key="label.cds_flagvia" /></label></td>
			<td colspan="3">
				<spring-form:checkbox id="flagvia_id" path="flagvia" />
				<spring-form:errors path="flagvia" cssClass="error"/>
			</td>
		 </tr>
		 <tr>
			<td colspan="4">
				<fieldset><legend><fmt:message key="label.convocazioni" /></legend>	
						<span id="lista_convocazioni" style="display: none;"></span>						
				</fieldset>
			</td>
		</tr>
			<% 
					String displayAmministrazioniinvitate = "display: none;";
					String styleAmministrazioniinvitate = "sezioneDatiPiu";
					if(request.getParameter("amministrazioniVisibili")!=null && request.getParameter("amministrazioniVisibili").equals("visibile"))
			        {
			            displayAmministrazioniinvitate="";
			            styleAmministrazioniinvitate = "sezioneDatiMeno";
			        }
			%>
		<tr>
			<td colspan="4">
				<fieldset><legend>
				<a
					class="<%=styleAmministrazioniinvitate%>"
					id="id_link_amministrazioniinvitate"
					href="javascript:showHidePanelBase('id_amministrazioniinvitate_table', 'id_link_amministrazioniinvitate', ' ', '${pageContext.request.contextPath}/images/','div',false);"
					title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.amministrazioni_invitate_alla_cds"/>">
					<label for="id_link_amministrazioniinvitate"><fmt:message key="label.amministrazioni_invitate_alla_cds" /></label> 
				</a></legend>
				    <script type='text/javascript'>
							function insertAmministrazione(inputField,listItem){
								var a = listItem.id;
								location.href='ajaxInsertAmministrazioneInvitataCds.htm?codiceAmministrazione='+a+'&codiceCds='+${cds.id.codice}; 
							}				
					</script>
					<br />
					<div style="<%=displayAmministrazioniinvitate%>" id=id_amministrazioniinvitate_table>
					<label><fmt:message key="label.inserisci_amministrazione_invitata" /></label>   
				   
				   	<div>
				   		<input id="amministrazione_id" type="text" class="searchbox" onchange="checkValue(this,'amministrazione_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
						<init:autocompleter afterUpdateElement="insertAmministrazione" methodAjax="findAmministrazioni.htm?tutteLeAmministrazioni=false" idHidden="amministrazione_hidden" idInput="amministrazione_id" inputTitleKey="label.ricerca_amministrazione"/>
        			</div>
					<div class="jmesa" >
				         <table  border="0"  cellpadding="0"  cellspacing="0"  class="table">
							<thead>
							<tr class="header">
								<td><fmt:message key="label.amministrazione" /></td>
								<td width="5%"><fmt:message key="label.note"/></td>
								<td width="5%"><fmt:message key="label.azioni"/></td>
							</tr>
							</thead>
							<%
					   		 	int i=0;
					    	%>
							<tbody class="tbody">
							    <c:if test="${fn:length(listaAmministrazioniInvitate)>0}">
								<c:forEach items="${listaAmministrazioniInvitate}" var="amministrazioniInvitate" varStatus="indice">
									<tr class="<%=(i%2)==0?"odd":"even"%>">
										<td>${amministrazioniInvitate.amministrazioni.amministrazione}</td>
										<td>
											<a class="dettaglioColumn" href="javascript:addNoteAmministrazione(${amministrazioniInvitate.id.codice})" title="<fmt:message key="label.note" />">
											<label><fmt:message key="label.note" /></label>
											</a>
										</td>
										<td>
											<a class="eliminaRiga" href="javascript:doHref('deleteInvitoAmministrazione.htm?codice=${amministrazioniInvitate.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />">
											<label><fmt:message key="label.azioni" /></label>
											</a>
										</td>
				                    </tr>
				                    <%i++;%>
			                    </c:forEach>
			                    </c:if>		
			                   	<c:if test="${fn:length(listaAmministrazioniInvitate)==0}">
			                   		<tr>
			                   			<td><fmt:message key="label.ricerca_no_dati" /></td>
			                   		</tr>
			                   	</c:if>
			                </tbody>
						</table>
 					</div>
 					</div> 											
			  </fieldset>
			</td>
		</tr>
			<% 
					String displayAnagrafeinvitate = "display: none;";
					String styleAnagrafeinvitate = "sezioneDatiPiu";
					if(request.getParameter("anagrafeVisibili")!=null && request.getParameter("anagrafeVisibili").equals("visibile"))
			        {
					    displayAnagrafeinvitate="";
					    styleAnagrafeinvitate = "sezioneDatiMeno";
			        }
					
			%>
		<tr>
			<td colspan="4">
				<fieldset><legend>
				<a
					class="<%=styleAnagrafeinvitate%>"
					id="id_link_anagrafeinvitate"
					href="javascript:showHidePanelBase('id_anagrafeinvitate_table','id_link_anagrafeinvitate','', '${pageContext.request.contextPath}/images/','div',false);"
					title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.anagrafe_invitate_alla_cds"/>">
					<label for="id_link_anagrafeinvitate"><fmt:message key="label.anagrafe_invitate_alla_cds" /></label> 
				</a></legend>
				    <script type='text/javascript'>
							function insertAnagrafe(inputField,listItem){
								var a = listItem.id;
								location.href='ajaxInsertAnagrafeInvitataCds.htm?codiceAnagrafe='+a+'&codiceCds='+${cds.id.codice}; 
							}				
					</script>
					<br />
					<div style="<%=displayAnagrafeinvitate%>" id=id_anagrafeinvitate_table>
					<label><fmt:message key="label.inserisci_anagarfe_invitata" /></label>   
				   
				   	<div>
				   		<input id="anagrafe_id" type="text" class="searchbox" onchange="checkValue(this,'anagrafe_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
						<init:autocompleter afterUpdateElement="insertAnagrafe" methodAjax="findAnagrafe.htm?tutteLeAmministrazioni=false" idHidden="anagarefe_hidden" idInput="anagrafe_id" inputTitleKey="label.ricerca_richiedente"/>
        			</div>
					<div class="jmesa" >
				         <table  border="0"  cellpadding="0"  cellspacing="0"  class="table">
							<thead>
							<tr class="header">
								<td><fmt:message key="label.richiedente" /></td>
								<td width="5%"><fmt:message key="label.note"/></td>
								<td width="5%"><fmt:message key="label.azioni"/></td>
							</tr>
							</thead>
							<%
					   		 	int j=0;
					    	%>
							<tbody class="tbody">
							    <c:if test="${fn:length(listaResponsabiliInvitati)>0}">
								<c:forEach items="${listaResponsabiliInvitati}" var="anagrafeInvitate" varStatus="indice">
									<tr class="<%=(i%2)==0?"odd":"even"%>">
										<td>${anagrafeInvitate.anagrafe.descrizioneRichiedente}</td>
										<td>
											<a class="dettaglioColumn" href="javascript:addNoteAnagrafe(${anagrafeInvitate.id.codice})" title="<fmt:message key="label.note" />">
											<label><fmt:message key="label.azioni" /></label>
											</a>
										</td>
										<td><a class="eliminaRiga" href="javascript:doHref('deleteInvitoAnagrafe.htm?codice=${anagrafeInvitate.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />">
											<label><fmt:message key="label.azioni" /></label>
											</a>
										</td>
				                    </tr>
				                <%j++;%>
			                    </c:forEach>
			                    </c:if>		
			                   	<c:if test="${fn:length(listaResponsabiliInvitati)==0}">
			                   		<tr>
			                   			<td><fmt:message key="label.ricerca_no_dati" /></td>
			                   		</tr>
			                   	</c:if>
			                </tbody>
						</table>
 					</div>
 					</div> 											
			  </fieldset>
			</td>
		</tr>	
	</table>	
</spring-form:form>

<script type="text/javascript">

		var secondDlg = null;
		jQuery(document).ready(function(){
			secondDlg = new dijit.Dialog({
		        title: "<fmt:message key='label.dettaglio_convocazione' />",
		        style: "width: 700px"
		    });
		});
		
		var secondDlgNote = null;
		jQuery(document).ready(function(){
			secondDlgNote = new dijit.Dialog({
		    	title: "<fmt:message key='label.note' />",
		        style: "width: 500px"
		    });
		});
	
		mostraConvocazioni(); 
	
		function mostraConvocazioni(){
			new Ajax.Request('${pageContext.request.contextPath}/cds/ajaxListaconvocazioni.htm?codice=${cds.id.codice}', {
				method: 'post',	
				onSuccess: function(transport){						
				  $("lista_convocazioni").innerHTML = transport.responseText;
				  $("lista_convocazioni").style.display='';     				  
 				},
 				onFailure: function(transport){ 
 				  $("lista_convocazioni").innerHTML= transport.responseText;
 				  $("lista_convocazioni").style.display='';     				      				  
 				 }						    		 
 			});
		}   		
	
		
		function dettaglioConvocazione(codice){	
	    	new Ajax.Request(
					'${pageContext.request.contextPath}/cds/ajaxDettaglioConvocazione.htm?codice='+ codice,
					{
						method : 'post',
						onSuccess : function(transport) {							
							var response = transport.responseText;							
							// $("dettaglio" + inventario.value).innerHTML = parseScript(response);
							result = parseAjaxResponse(response, true, false);
							secondDlg.attr("content", result);
					        secondDlg.show();
							//$("dettaglio" + inventario.value).appear();							
						},
						onFailure : function(transport) {
							var response = transport.responseText;
							alert(response);
							secondDlg.attr("content", response);
					        secondDlg.show();
						}
					});
		}
		
		function setEffettiva(codice){
			new Ajax.Request(
					'${pageContext.request.contextPath}/cds/ajaxSetConvocazioneEffettiva.htm?codice='+ codice,
					{
						method : 'post',
						onSuccess : function(transport) {							
							var response = transport.responseText;
							mostraConvocazioni(); 
						},
						onFailure : function(transport) {
							var response = transport.responseText;
							alert(response);
						}
					});
		}
	
		function eliminaConvocazione(codice){
			if(confirm('<fmt:message key="javascript.confirm.delete" />')){
				new Ajax.Request(
					'${pageContext.request.contextPath}/cds/ajaxDeleteConvocazione.htm?codice='+codice,
					{
						method : 'post',
						onSuccess : function(transport) {							
							var response = transport.responseText;
							mostraConvocazioni(); 
						},
						onFailure : function(transport) {
							var response = transport.responseText;
							alert(response);
						}
				});
			}
		}
		
		
		// Javascrip per mostrare il calendario (Viene richiamto all'interno della jsp ajax/listInventarioprocedimenti.jsp)
		
		function setupCal(inputId, imageId){
			RANGE_CAL_1 = new Calendar({
					inputField: inputId,
					dateFormat: "%d/%m/%Y",
					trigger: imageId,
					bottomBar: false,
					onSelect: function() {
				var date = Calendar.intToDate(this.selection.get());
				this.hide();
			}
			})
		
		}
		

		function addConvocazione(){
			new Ajax.Request(
					'${pageContext.request.contextPath}/cds/ajaxCreateConvocazione.htm?codicecds=${cds.id.codice}',
					{
						method : 'post',
						onSuccess : function(transport) {							
							var response = transport.responseText;							
							result = parseAjaxResponse(response, true, false);
							secondDlg.attr("content", result);
							secondDlg.show();
							//$("dettaglio" + inventario.value).appear();							
						},
						onFailure : function(transport) {
							var response = transport.responseText;
							alert(response);
							secondDlg.attr("content", response);
							secondDlg.show();
						}
					});
		}

		function insertConvocazione(){
			
			new Ajax.Request(
					'${pageContext.request.contextPath}/cds/ajaxInsertConvocazione.htm',
					{
						method : 'post',
						parameters: $('innerForm_id').serialize(true),
						onSuccess : function(transport) {							
							var response = transport.responseText;	
							secondDlg.hide();
							mostraConvocazioni();
						},
						onFailure : function(transport) {
							var response = transport.responseText;
							alert(response);
						}
					});
		}
		
		function updateConvocazione(){
			new Ajax.Request(
					'${pageContext.request.contextPath}/cds/ajaxUpdateConvocazione.htm',
					{
						method : 'post',
						parameters: $('innerForm_id').serialize(true),
						onSuccess : function(transport) {							
							var response = transport.responseText;														
							result = parseAjaxResponse(response, true, false);
							secondDlg.hide();
							mostraConvocazioni();
						},
						onFailure : function(transport) {
							var response = transport.responseText;
							alert(response);
						}
					});
		}
		
		function addNoteAmministrazione(codice){
			
	        // create the dialog:
	    	new Ajax.Request(
					'${pageContext.request.contextPath}/cds/ajaxCreateNoteAmministrazione.htm?codice='+ codice,
					{
						method : 'post',
						onSuccess : function(transport) {							
							var response = transport.responseText;							
							// $("dettaglio" + inventario.value).innerHTML = parseScript(response);
							result = parseAjaxResponse(response, true, false);
							secondDlgNote.attr("content", result);
					        secondDlgNote.show();
							//$("dettaglio" + inventario.value).appear();							
						},
						onFailure : function(transport) {
							var response = transport.responseText;
							alert(response);
							secondDlgNote.attr("content", response);
					        secondDlgNote.show();
						}
					});
		}
		
		function addNoteAnagrafe(codice){

	    	new Ajax.Request(
					'${pageContext.request.contextPath}/cds/ajaxCreateNoteAnagrafe.htm?codice='+ codice,
					{
						method : 'post',
						onSuccess : function(transport) {							
							var response = transport.responseText;							
							// $("dettaglio" + inventario.value).innerHTML = parseScript(response);
							result = parseAjaxResponse(response, true, false);
							secondDlgNote.attr("content", result);
					        secondDlgNote.show();
							//$("dettaglio" + inventario.value).appear();							
						},
						onFailure : function(transport) {
							var response = transport.responseText;
							alert(response);
							secondDlgNote.attr("content", response);
					        secondDlgNote.show();
						}
					});
		 }
</script>	

</div>
<div id="functions">
<ul>
		<%-- SALVA --%>
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>		
		<%-- CANCELLA --%>			
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		<%-- ATTI E VERBALI --%>
		<li><a href="javascript:historySet('${_urlback}','../cdsatti/list.htm?codiceCds=${cds.id.codice}','')"><fmt:message key="button.atti_e_verbali" /></a></li>
		<%-- CHIUDI --%>
		<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>