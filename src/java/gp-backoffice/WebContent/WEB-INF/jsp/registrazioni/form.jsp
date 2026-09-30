<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${registrazioni.id.codice==null}">
			<fmt:message key="form.registrazioni.title.create" />
		</c:if> 
		<c:if test="${registrazioni.id.codice!=null}">
			<fmt:message key="form.registrazioni.title.view" />
		</c:if>
	</title>
	
</head>
<body>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../registrazioni/view" />
</jsp:include>
<%
String  mercati="display:none;";
String  mercatiUso="display:none;";
String  mercatiPosteggio="display:none;";
String inventProc="display:none;";
%>
<c:if test="${registrazioni.mercatiD.id.codice!=null}">
<%
mercati="";
mercatiPosteggio="";
mercatiUso="";
%>
</c:if>
<c:if test="${registrazioni.mercatiD.id.codice==null}">
<%
mercati="display:none;";
mercatiPosteggio="display:none;";
mercatiUso="display:none;";
%>
</c:if>
<c:if test="${registrazioni.inventarioprocedimenti.id.codice!=null}">
<%
inventProc="";
%>
</c:if>
<c:if test="${registrazioni.inventarioprocedimenti.id.codice==null}">
<%
inventProc="display:none;";
%>
</c:if>
<span class="titoloPagina">
<c:if test="${registrazioni.id.codice==null}">
	<fmt:message key="form.registrazioni.title.create" />
</c:if> 
<c:if test="${registrazioni.id.codice!=null}">
	<fmt:message key="form.registrazioni.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form  commandName="registrazioni" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="registrazioni" />
    </jsp:include>
	<table>
	    <c:if test="${registrazioni.id.codice!=null}">
		<tr>
			<td><fmt:message key="form.registrazioni.progressivo" /></td>
			<td><spring-form:input id="progressivo_id" path="progressivo" size="12" maxlength="7" readonly="true"/>
			<spring-form:errors path="progressivo" cssClass="error"/></td>
		</tr>
		</c:if>
		<tr>
			<td><fmt:message key="form.registrazioni.descrizione" /></td>
			<td><spring-form:textarea id="descrizione_id" path="descrizione" cols="70" rows="2" />
			<spring-form:errors path="descrizione" cssClass="error"/></td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioni.anagrafe" />
			</td>
			<td>
				<spring-form:input id="anagrafe_id" path="anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden')" onkeydown="javascript:return searchAll(this,event,3)" size="67"/>
				<init:autocompleter methodAjax="findAnagrafe.htm" idHidden="anagrafe_hidden" idInput="anagrafe_id" inputTitleKey="label.ricerca_richiedente" minChars="3"/>
				<spring-form:errors path="anagrafe" cssClass="error"/> 
				<spring-form:hidden id="anagrafe_hidden" path="anagrafe.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioni.operatore" />
			</td>
			<td>
				<spring-form:input id="responsabili_id" path="responsabili.responsabile" cssClass="searchbox" onchange="checkValue(this,'responsabili_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
				<init:autocompleter methodAjax="findResponsabili.htm" idHidden="responsabili_hidden" idInput="responsabili_id" inputTitleKey="label.ricerca_responsabile"/>
				<spring-form:errors path="responsabili.responsabile" cssClass="error"/> 
				<spring-form:hidden id="responsabili_hidden" path="responsabili.id.codice"  />
				<spring-form:hidden id="responsabiliSistema_hidden" path="responsabiliSistema.id.codice"  />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.dataregistrazione" /></td>
			<td>
				<spring-form:input id="dataregistrazione_id" path="dataRegistrazione" size="10" maxlength="10" onblur="isValidDate(this,true);" /> 
				<init:calendar imagePath="/images/cal.gif" idImage="caldataregistrazione" idInput="dataregistrazione_id" textKey="label.calendar"/>
				<spring-form:errors	path="dataRegistrazione" cssClass="error" />
		   		<spring-form:hidden id="datasistema_id" path="dataSistema"/> 
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.registrazioniCausali" /></td>
			<td>
				<spring-form:select id="regCausali" path="registrazioniCausali.id.codice"  onchange="assegna(this);return false;">
				    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${registrazioniCausaliList}" itemValue="id.codice"	itemLabel="descrizione" />
				</spring-form:select> 
				<spring-form:errors path="registrazioniCausali" cssClass="error" />
				
			</td>
		</tr>
		<tr id="mercati" style="<%=mercati%>">
			<td><fmt:message key="form.registrazioni.mercati" /></td>
			<td>
				<script type="text/javascript">
				function setHiddenFieldmercati(inputField,listItem){
					var a = listItem.id;
					document.getElementById('mercati_id').value = inputField.value;
					document.getElementById('mercati_hidden').value = a;
					$('mercati_id_choices').fade();	 
					assegnaPosteggio();
					mercatiUsoDisplay();
				}
				</script>
				<spring-form:input id="mercati_id" path="mercatiD.mercati.descrizione" cssClass="searchbox" onchange="checkValue(this,'mercati_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="67" />
				<init:autocompleter afterUpdateElement="setHiddenFieldmercati" methodAjax="findMercati.htm" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
				<spring-form:errors path="mercatiD" cssClass="error"/> 
				<spring-form:hidden id="mercati_hidden" path="mercatiD.mercati.id.codice" />
			</td>
		</tr>
		
		<tr id="mercatiPosteggio" style="<%=mercatiPosteggio%>">
			<td><fmt:message key="form.registrazioni.mercati.posteggio" /></td>
			<td>
				<spring-form:select id="selectPosteggio" path="mercatiD.id.codice">
				<spring-form:option value="${registrazioni.mercatiD.id.codice}" label="${registrazioni.mercatiD.codiceposteggio}"></spring-form:option>
				</spring-form:select><spring-form:errors path="mercatiD.id.codice" cssClass="error"/> 
			</td>
		</tr>
		<tr id="mercatiUso" style="<%=mercatiUso%>">
			<td><fmt:message key="form.registrazioni.mercatiUso" /></td>
			<td>
				<spring-form:select id="selectMercatiUso" path="mercatiUso.id.codice">
				<spring-form:option value="${registrazioni.mercatiUso.id.codice}" label="${registrazioni.mercatiUso.descrizione}"></spring-form:option>
				</spring-form:select><spring-form:errors path="mercatiUso.id.codice" cssClass="error"/> 
			</td>
		</tr>
		<tr id="endo">
			<td><fmt:message key="form.registrazioni.istanza" /></td>
			<td>
				<script type="text/javascript">
					function setHiddenFieldistanze(inputField,listItem){
						var a = listItem.id;
						document.getElementById('istanze_id').value = inputField.value;
						document.getElementById('istanze_hidden').value = a;
						$('istanze_id_choices').fade();	 
						assegnaEndo();
					}
				</script>
				<spring-form:input id="istanze_id" path="istanze.numeroistanza" cssClass="searchbox" onchange="checkValue(this,'istanze_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="67" />
				<init:autocompleter afterUpdateElement="setHiddenFieldistanze" methodAjax="findIstanze.htm" idHidden="istanze_hidden" idInput="istanze_id" inputTitleKey="label.ricerca_istanza"/>
				<div id="istanze_id_choices" class="autocomplete" onclick="assegnaEndo(this);"></div>
				<spring-form:errors path="istanze" cssClass="error"/> 
				<spring-form:hidden id="istanze_hidden" path="istanze.id.codice" />
			</td>
		</tr>
		
		<tr id="inventProc" style="<%=inventProc%>">
			<td><fmt:message key="form.registrazioni.inventarioproc" /></td>
			<td> 
				<spring-form:select id="selectProc" path="inventarioprocedimenti.id.codice">
					<spring-form:option value="${registrazioni.inventarioprocedimenti.id.codice}" label="${registrazioni.inventarioprocedimenti.procedimento}"></spring-form:option>
				</spring-form:select>
		 		<spring-form:errors path="inventarioprocedimenti" cssClass="error"/> 	
			</td>
		</tr>
		<c:if test="${registrazioni.id.codice!=null}">
		<tr>
			<td><fmt:message key="form.registrazioni.importo" /></td>
			<td><spring-form:input id="importo_id" path="importo" readonly="true"/>
			<spring-form:errors path="importo" cssClass="error"/></td>
		</tr>
		</c:if>
		<tr>
			<td>
				<fmt:message key="form.registrazioni.note" />
			</td>
			<td>
				<spring-form:textarea id="note_id" path="note" cols="62" rows="4" />
				<spring-form:errors path="note" cssClass="error"/> 
			</td>
		</tr>
	</table>
	
	<script type='text/javascript'>
		$('descrizione_id').focus();
		window.onload=function() {
			  var elSel = document.getElementById("regCausali");
			  if (elSel.options[1]) {
				  new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiEndo.htm', {
					  method: 'get',
					  parameters: {code: elSel.options[elSel.selectedIndex].value, limit: 12},
					  onSuccess: function(transport){
						  var response = transport.responseText;
						  if(response == '0' ){
							$('mercati').fade();
							$('mercatiUso').fade();
							$('inventProc').fade();
							$('mercatiPosteggio').fade();
							$('mercati').value='';
							$('mercatiUso').value='';
							$('inventProc').value='';
							$('mercatiPosteggio').value='';
						}
					      if(response == '1' ){
					    	  $('mercati').appear();
					    	  $('mercatiUso').appear();
					    	  $('mercatiPosteggio').appear();
							  $('inventProc').fade();
							  $('inventProc').value='';
							  $('selectProc')[$('selectProc').selectedIndex].value='';
					      }
						  if(response == '2'){
							  $('mercatiPosteggio').fade();
							  $('mercati').fade();
							  $('mercatiUso').fade();
							  $('inventProc').appear();
							}
					    },
					  onFailure: function(){ 
					    	$('mercati').fade();
							$('inventProc').fade();
							$('mercatiPosteggio').fade();
							$('mercati').value='';
							$('mercatiPosteggio').value='';
							$('mercatiUso').value='';
					   }
									  
					  });
			  }
			};
			
		function assegna(elem){
			var code=elem[elem.selectedIndex].value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiEndo.htm', {
				  method: 'get',
				  parameters: {code: code, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  if(response == '0' ){
						$('mercati').fade();
						$('inventProc').fade();
						$('mercatiUso').fade();
						$('mercatiPosteggio').fade();
					}
				      if(response == '1' ){
				    	  $('mercati').appear();
				    	  $('mercatiPosteggio').appear();
						  $('inventProc').fade();
						  if(document.getElementById("mercati_hidden").value != ''){
						  $('mercatiUso').appear();
						  }
						  
				      }
					  if(response == '2'){
						  $('mercatiPosteggio').fade();
						  $('mercati').fade();
						  $('mercatiUso').fade();
						  $('inventProc').appear();
		  
					  }
				    },
				  onFailure: function(){ 
				    	$('mercati').fade();
						$('inventProc').fade();
						$('mercatiPosteggio').fade();
				   }
								  
				  });
												
			}
		function removeOptionSelected(opt)
		{
		  var elSel = document.getElementById(opt);
		  var i;
		  for (i = elSel.length - 1; i>=0; i--) {
		    if (elSel.options[i]) {
		       elSel.remove(i);
		    }
		  }
		}

		function assegnaPosteggio(){
			removeOptionSelected("selectPosteggio");
			var idMercato=document.getElementById("mercati_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findPosteggioMercato.htm', {
				  method: 'post',
				  parameters: {code: idMercato, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("mercatiPosteggio").appear();
					  var opts=response.split(",");
					  for(var i=0;i<((opts.length)-1); i++ ){
						  var j=i+1;
						  try{
						  	$('selectPosteggio').add(new Option( opts[j], opts[i]),  $('selectPosteggio').options[i]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectPosteggio').add(new Option( opts[j], opts[i]));
						  }
						  i++;
					  }
				    },
				  onFailure: function(){  }
								  
				  });
												
			}
		function mercatiUsoDisplay(){
				removeOptionSelected("selectMercatiUso");
				var idMercato=document.getElementById("mercati_hidden").value;
				new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiUso.htm', {
					  method: 'post',
					  parameters: {code: idMercato, limit: 12},
					  onSuccess: function(transport){
						  var response = transport.responseText;
						  $("mercatiUso").appear();
						  var opts=response.split(",");
						  for(var i=0;i<((opts.length)-1); i++ ){
							  var j=i+1;
							  try{
							  	$('selectMercatiUso').add(new Option( opts[j], opts[i]),  $('selectMercatiUso').options[i]);
							  }
							  catch(e){ //in IE, try the below version instead of add()
								  $('selectMercatiUso').add(new Option( opts[j], opts[i]));
							  }
							  i++;
						  }
					    },
					  onFailure: function(){  }
									  
					  });
			}

        function assegnaEndo(){
        	removeOptionSelected("selectProc");
			var code=$("istanze_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findInventarioEndo.htm', {
				  method: 'post',
				  parameters: {code: code, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("inventProc").appear();
					  var opts=response.split(",");
					  for(var i=0;i<((opts.length)-1); i++ ){
						  var j=i+1;
						  try{
						  	$('selectProc').add(new Option( opts[j], opts[i]),  $('selectProc').options[i]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectProc').add(new Option( opts[j], opts[i]));
						  }
						  i++;
					  }
				    },
				  onFailure: function(){ alert('Something went wrong...'); }
								  
				  });



		}
	</script>
</spring-form:form>
</div>
<br class="clear" />
<div id="functions">
<ul>
	<c:if test="${registrazioni.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${registrazioni.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>

<c:if test="${(registrazioni.id.codice!= null)}">
<br/>
<span class="titoloTabella"><fmt:message key="form.registrazioniimporti.title" /></span>
		<form name="registrazioniimportiForm" action="view.htm">
				<jmesa:springTableFacade
					id="registrazioniImporti_id" 
					items="${registrazioni.registrazioniImportis}" 
					var="registrazioniimporti_var"
					maxRows="10" 
					exportTypes="" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.DateRegImportiFilterMatcherMap" view="org.jmesa.custom.GroupRegistrazioniimporti">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="nrRata" titleKey="form.registrazioniimporti.nrRata" />
							<jmesa:htmlColumn property="scadenza" titleKey="form.registrazioniimporti.scadenza" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.ScadenzaRegImportoCustomFilter"/>
							<jmesa:htmlColumn property="conti.descrizione" titleKey="form.registrazioniimporti.conti" width="500px"/>
							<jmesa:htmlColumn property="importo" titleKey="form.registrazioniimporti.importo" width="500px"/>
							<jmesa:htmlColumn property="iva" titleKey="form.registrazioniimporti.iva" width="500px"/>
							<jmesa:htmlColumn property="id.codice" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fregistrazioni%2FviewRegistrazioneimporti.htm?codice=${registrazioniimporti_var.id.codice}" 
									title="<fmt:message key="label.edit.record" /> ${registrazioniimporti_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label></a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${registrazioni.id.codice}" name="codice"/>
			</form>
			   <div id="functions">
			    <ul>
			    	<li>
			    		<a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fregistrazioni%2FcreateImporto.htm?registrazionecodice=${registrazioni.id.codice}','')"><fmt:message key="button.nuovoImporto" /></a>
			    	</li>
			    </ul>
				</div>
				
</c:if>

</body>
</html>
