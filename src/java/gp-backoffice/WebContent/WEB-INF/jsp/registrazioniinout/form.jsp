<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${registrazioniInOut.id.codice==null}">
			<fmt:message key="form.registrazioniInOut.title.create" />
		</c:if> 
		<c:if test="${registrazioniInOut.id.codice!=null}">
			<fmt:message key="form.registrazioniInOut.title.view" />
		</c:if>
	</title>
</head>
<body>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../registrazioniinout/view" />
</jsp:include>

<span class="titoloPagina">
		<c:if test="${registrazioniInOut.id.codice==null}">
			<fmt:message key="form.registrazioniInOut.title.create" />
		</c:if> 
		<c:if test="${registrazioniInOut.id.codice!=null}">
			<fmt:message key="form.registrazioniInOut.title.view" />
		</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
</jsp:include>

<c:if test="${param.insertAssegnazioni=='ok'}">
	<span class="success" id="insertAssegnazioni"><fmt:message key="form.registrazioniInOut.insertAssegnazioni.success" /></span>
<script type="text/javascript">
	$('insertAssegnazioni').pulsate( {
		pulses :2,
		duration :1.0
	});
</script>
</c:if>
<c:if test="${param.insertAssegnazioni=='ko'}">
	<span class="error" id="insertAssegnazioni"><fmt:message key="form.registrazioniInOut.insertAssegnazioni.error" /></span>
<script type="text/javascript">
	$('insertAssegnazioni').pulsate( {
		pulses :2,
		duration :1.0
	});
</script>
</c:if>


<c:if test="${param.deleteAssegnazioni=='ok'}">
	<span class="success" id="deleteAssegnazioni"><fmt:message key="form.registrazioniInOut.deleteAssegnazioni.success" /></span>
<script type="text/javascript">
	$('deleteAssegnazioni').pulsate( {
		pulses :2,
		duration :1.0
	});
</script>
</c:if>

<br />


<div id="subcontent">
	<spring-form:form commandName="registrazioniInOut" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="registrazioniInOut" />
    </jsp:include>
	<spring-form:hidden path="id.codice"/>
	<table>
		<tr>
			<td><fmt:message key="form.registrazioniInOut.dataincasso" /></td>
			<td>
				<spring-form:input id="dataIncasso_id" path="dataIncasso" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="calDataIncasso" idInput="dataIncasso_id" textKey="label.calendar"/> 
				<spring-form:errors	path="dataIncasso" cssClass="error" />
			</td>
		</tr>
        <tr>
			<td><fmt:message key="form.registrazioniInOut.datadistinta" /></td>
			<td>
				<spring-form:input id="dataDistinta_id" path="dataDistinta" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
				<init:calendar imagePath="/images/cal.gif" idImage="calDataDistinta" idInput="dataDistinta_id" textKey="label.calendar"/>
				<spring-form:errors	path="dataDistinta" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniInOut.tipo" /></td>
			<td>
				<spring-form:select path="tipo">
					<spring-form:option value="<%=WebConstants.REGISTRAZIONIINOUT_TIPO_E%>"><fmt:message key="form.registrazioniInOut.tipo.e" /></spring-form:option>
					<spring-form:option value="<%=WebConstants.REGISTRAZIONIINOUT_TIPO_U%>"><fmt:message key="form.registrazioniInOut.tipo.u" /></spring-form:option>
				</spring-form:select>
				<spring-form:errors path="tipo" cssClass="error"/>	
			</td>
		</tr>
		<tr id="richiedente">
			<td><fmt:message key="form.registrazioniInOut.intestatario" /></td>
			<td><select name="richiedente" onchange="mostra(this);return false;" id="richiedente_id">
					<option value="<%=WebConstants.REGISTRAZIONIINOUT_RICHIEDENTE_ANAGRAFE %>"><fmt:message key="form.registrazioniInOut.anagrafe" /></option>
					<option value="<%=WebConstants.REGISTRAZIONIINOUT_RICHIEDENTE_AMMINISTRAZIONI %>"><fmt:message key="form.registrazioniInOut.amministrazioni" /></option>
				</select>
			</td>
		</tr>		
		<tr id="anagrafe">
			<td>
				<fmt:message key="form.registrazioniInOut.anagrafe" />
			</td>
			<td class="inline-ui-cell">
				<spring-form:input id="anagrafe_id" path="anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden')" size="67" onkeydown="javascript:return searchAll(this,event,3)"/>
				<init:autocompleter methodAjax="findAnagrafe.htm" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente"/>
				<init:help idHelp="help_anagrafe_id" textKey="ajax.search.minchars"/>
				<spring-form:errors path="anagrafe" cssClass="error"/> 
				<spring-form:hidden id="anagrafe_hidden" path="anagrafe.id.codice"  />
			</td>
		</tr>
		<tr id="amministrazioni" style="display: none;">
			<td>
				<fmt:message key="form.registrazioniInOut.amministrazioni" />
			</td>
			<td>
				<spring-form:input id="amministrazioni_id" path="amministrazioni.amministrazione" cssClass="searchbox" onchange="checkValue(this,'amministrazioni_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67" />
				<init:autocompleter methodAjax="findAmministrazioni.htm?tutteLeAmministrazioni=false" idHidden="amministrazioni_hidden" idInput="amministrazioni_id" inputTitleKey="label.ricerca_amministrazione"/>
				<spring-form:errors path="amministrazioni" cssClass="error"/> 
				<spring-form:hidden id="amministrazioni_hidden" path="amministrazioni.id.codice"  />
			</td>
		</tr>			
		<tr>
			<td><fmt:message key="form.registrazioniInOut.importo" /></td>
			<td><spring-form:input id="importo_id" path="importo" size="10" cssStyle="text-align:right;"/>
			<spring-form:errors path="importo" cssClass="error"/></td>
		</tr>
		<c:if test="${registrazioniInOut.id.codice!=null}">
		<tr>
			<td><fmt:message key="form.registrazioniInOut.rimanenza" /></td>
			<td>
			<c:if test="${registrazioniInOut.rimanenza gt 0}">
				<spring-form:input id="rimanenza_id" cssClass="inputRed" path="rimanenza" size="10" readonly="true" cssStyle="text-align:right;"/>
			</c:if>
			<c:if test="${registrazioniInOut.rimanenza le 0}">
				<spring-form:input id="rimanenza_id" cssClass="inputGreen" path="rimanenza" size="10" readonly="true" cssStyle="text-align:right;"/>
			</c:if>

			<spring-form:errors path="rimanenza" cssClass="error"/></td>
		</tr>	
		</c:if>	
		<tr>
			<td><fmt:message key="form.registrazioniInOut.tipimodalitapagamento" /></td>
			
				<td>
				<spring-form:select path="tipimodalitapagamento.id.codice" >
					
					<c:if test="${tipimodalitapagamento == null }">
				    	<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					</c:if>
					
					<c:if test="${tipimodalitapagamento != null }">
						<spring-form:input id="tipimodalitapagamento_id" path="tipimodalitapagamento" size="10"/>
						<spring-form:errors path="importo" cssClass="error"/>
					</c:if>
					
					<spring-form:options items="${tipimodalitapagamentoList}" itemValue="id.codice"	itemLabel="mpDescrestesa" />
				</spring-form:select> 
				<spring-form:errors path="tipimodalitapagamento" cssClass="error" />
				</td>			
		</tr>		
		<tr>
			<td><fmt:message key="form.registrazioniInOut.riferimentipagamento" /></td>
			<td><spring-form:textarea id="riferimentiPagamento_id" path="riferimentiPagamento"
				cols="70" rows="5"/> <spring-form:errors path="riferimentiPagamento" cssClass="error" tabindex="0" /></td>
		</tr>
		
		<tr>
			<td><fmt:message key="form.registrazioniInOut.note" /></td>
			<td><spring-form:textarea id="note_id" path="note"
				cols="70" rows="5"/> <spring-form:errors path="note" cssClass="error" tabindex="0" /></td>
		</tr>	
		<tr>
			<td><fmt:message key="form.registrazioniInOut.visualizzadettagli" /></td>
			<td>
				<spring-form:checkbox id="visualizzaDettagliId" path="visualizzaDettagliTransient" onclick="visualizzaDettagliScadenze();"/>
				<init:help idHelp="help1" textKey="form.registrazioniInOut.visualizzadettagli.help"/>
			</td>
		</tr>		
	</table>	
	<script type='text/javascript'>
	function visualizzaDettagliScadenze(){
		var dettagliDisplay=document.getElementById("visualizzaDettagliId");
		var id=dettagliDisplay.checked;

		saveUserPreference('<%= WebConstants.CONF_UTENTE_REG_IO_VISUALIZZA_DETTAGLI %>',id);
	}
	function saveUserPreference(nomeparametro, valore){
		new Ajax.Request('/sigepro2dev/registrazioniinout/salvaPreferenza.htm', {
			  method: 'post',
			  parameters: {nomeparametro: nomeparametro,valore: valore},
			  onSuccess: function(transport){ },
			  onFailure: function(transport){ 
				var response = transport.responseText;
			    alert(response); }						    		 
			  });			
	}
	//<![CDATA[ 					
	
		$('anagrafe_id').focus();
		function searchAll(inputField,evt){
			 var charCode = (evt.which) ? evt.which : event.keyCode;
			 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
			   inputField.value='%';
			 }
		}
	function mostra(obj){
		var pos=obj.selectedIndex;
		var valore = ''
		
		if (pos>-1) {
			valore=obj.options[pos].value;
		}
		if (valore == '<%=WebConstants.REGISTRAZIONIINOUT_RICHIEDENTE_ANAGRAFE %>'){
			$('anagrafe').appear();
			$('amministrazioni').fade();	 
		}else if (valore=='<%=WebConstants.REGISTRAZIONIINOUT_RICHIEDENTE_AMMINISTRAZIONI %>'){
			$('anagrafe').fade();
			$('amministrazioni').appear();	 
		}  else {
			$('anagrafe').fade();
			$('amministrazioni').fade();	 
		}
		return false;
	}
	
	function mostradiv(id){
		$(id).appear();
	}
	
	function nascondidiv(id){
		$(id).fade();
	}

	function changeValue(link,id){
		var idImporto='importo'+id;
		var importo=$(idImporto).value;		
		var linkHref=link+"&importo="+importo;
		location.href=linkHref;
	}

	    function dettaglioRegistrazione(codice){
				var goToUrl = "../registrazioni/view.htm?codice="+codice;
				goToUrl = escape(goToUrl);
				doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'');
			}
	//]]> 
	</script>

</spring-form:form>
</div>
	
<div id="functions">
<ul>
	<c:if test="${registrazioniInOut.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${registrazioniInOut.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
<br/><br/>
<c:if test="${registrazioniInOut.id.codice!=null}">
<br/>
	<span class="titoloTabella"><fmt:message key="form.registrazioniInOut.assegnazioni" /></span>
		<form name="assegnazioniForm" action="view.htm">
				<jmesa:springTableFacade
					id="assegnazioni_id" 
					items="${registrazioniInOut.regIoAssegnazionis}" 
					var="assegnazioni_var"
					stateAttr="restore" view="org.jmesa.custom.AssegnazioniIncassiGroupView">
					<jmesa:htmlTable>
						<jmesa:htmlRow>		
							<jmesa:htmlColumn property="registrazioniImporti.registrazioni.id.codice" titleKey="label.edit.record" sortable="false" filterable="false" width="8%">
								<a class="dettaglioColumn" href="javascript:dettaglioRegistrazione('${assegnazioni_var.registrazioniImporti.registrazioni.id.codice}');"  title="<fmt:message key="label.edit.record" /> ${assegnazioni_var.registrazioniImporti.registrazioni.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>	
							</jmesa:htmlColumn>	
							<jmesa:htmlColumn width="5%" property="registrazioniImporti.registrazioni.progressivo" titleKey="form.registrazioni.progressivo" />
							<jmesa:htmlColumn property="registrazioniImporti.registrazioni.registrazioniCausali.descrizione" titleKey="form.registrazioni.registrazioniCausali" />
							<jmesa:htmlColumn property="registrazioniImporti.registrazioni.mercatiD.mercati.descrizione" titleKey="form.registrazioni.mercati" />
							<jmesa:htmlColumn property="registrazioniImporti.registrazioni.mercatiUso.descrizione" titleKey="form.registrazioni.mercatiUso" />
							<jmesa:htmlColumn width="5%"  property="registrazioniImporti.registrazioni.mercatiD.codiceposteggio" titleKey="form.registrazioni.mercati.posteggio" />

							<jmesa:htmlColumn property="registrazioniImporti.conti.descrizione" titleKey="form.mercatiConti.conti" />	
							<jmesa:htmlColumn  width="5%" property="registrazioniImporti.importo" titleKey="form.registrazioniInOut.importo" style="text-align:right;" headerStyle="text-align:right;">
								<fmt:formatNumber minFractionDigits="2">${assegnazioni_var.registrazioniImporti.importo}</fmt:formatNumber>
							</jmesa:htmlColumn>	
							<jmesa:htmlColumn  width="5%" property="importo" titleKey="form.registrazioniInOut.incassato" style="text-align:right;" headerStyle="text-align:right;">	
								<fmt:formatNumber minFractionDigits="2">${assegnazioni_var.importo}</fmt:formatNumber>
							</jmesa:htmlColumn>		
																
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${registrazioniInOut.id.codice}" name="codice" />
			</form>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('deleteAssegnazioni.htm?codice=${registrazioniInOut.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.regioassegnazioni.reset" /></a></li>
</ul>
</div>
</c:if>

</body>
</html>