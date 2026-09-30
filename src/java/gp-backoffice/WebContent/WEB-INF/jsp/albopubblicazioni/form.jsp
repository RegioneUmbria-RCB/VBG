<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<c:if test="${albopubblicazioni.id.codice==null}">
			<fmt:message key="albopubblicazioni.label.nuova_pubblicazione" />
		</c:if> 
		<c:if test="${alboPubblicazioni.id.codice!=null}">
	        <fmt:message key="albopubblicazioni.label.modifica_pubblicazione" />
        </c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${alboPubblicazioni.id.codice==null}">
	<fmt:message key="albopubblicazioni.label.nuova_pubblicazione" />
</c:if> 
<c:if test="${alboPubblicazioni.id.codice!=null}">
	<fmt:message key="albopubblicazioni.label.modifica_pubblicazione" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>

<!-- Servono per non far vedere gli uffici prima di aver scelto l'amministrazione  -->
<%
    String  amministrazioni="display:none;";
	String  uffici="display:none;";
%>
<script type="text/javascript">
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
 </script>

<input  id="albocodice" type="hidden" value="${alboPubblicazioni.id.codice }"/>
<div id="subcontent">
	<spring-form:form commandName="alboPubblicazioni" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="alboPubblicazioni" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="albopubblicazioni.label.numeropubblicazione" /></td>
			<td><spring-form:input tabindex="1" id="numeroPubblicazione_id" path="numeroPubblicazione" size="30" />
			<spring-form:errors path="numeroPubblicazione" cssClass="error"/>

           <fmt:message key="albopubblicazioni.label.data_pubblicazione_atto" />
           <spring-form:input tabindex="2" id="dataPubblicazione_id" path="dataPubblicazione" size="10" maxlength="10" onblur="isValidDate(this,true);" />
				<init:calendar imagePath="/images/cal.gif" idImage="calDataPubblicazione" idInput="dataPubblicazione_id" textKey="label.calendar"/>
            
		   <spring-form:errors path="dataPubblicazione" cssClass="error"/></td>


		</tr>
        <tr>
			<td><fmt:message  key="albopubblicazioni.label.oggetto" /></td>
			<td><spring-form:input tabindex="3" id="descrizione_id" path="descrizione" size="70" />
				<init:help idHelp="help3" textKey="albopubblicazioni.help.oggetto"/> 
			<spring-form:errors path="descrizione" cssClass="error"/></td>
		 </tr>

         <c:if test="${listacategorie!=null}">
         <tr>
			<td><fmt:message  key="albopubblicazioni.label.alboCategorie" /></td>
			<td><spring-form:select tabindex="4" id="alboCategorie_id"  path="alboCategorie.id.codice"  >
                <spring-form:options items="${listacategorie}" itemValue="id.codice" itemLabel="descrizione"></spring-form:options>
                </spring-form:select>				
                <init:help idHelp="help4" textKey="albopubblicazioni.help.alboCategorie"/> 
			<spring-form:errors path="alboCategorie" cssClass="error"/></td>
		 </tr>
		</c:if>
		<c:if test="${listacategorie==null}">
			<tr>
				<td><fmt:message  key="albopubblicazioni.label.alboCategorie" /></td>
				<td><spring-form:input tabindex="4" id="alboCategorie_id" path="alboCategorie.descrizione" disabled="true" size="50"/>                			
                	<init:help idHelp="help4" textKey="albopubblicazioni.help.alboCategorie"/> 
				<spring-form:errors path="alboCategorie" cssClass="error"/></td>
			 </tr>
		</c:if>               
		
        <tr>
        <td><fmt:message key="albopubblicazioni.label.validaDal" /></td>
         <td>  <spring-form:input tabindex="5" id="validaDal_id" path="validaDal" size="10" maxlength="10" onblur="isValidDate(this,true);" />
				<init:calendar imagePath="/images/cal.gif" idImage="calvalidaDal" idInput="validaDal_id" textKey="label.calendar"/>
		   <spring-form:errors path="validaDal" cssClass="error"/>
           
            <fmt:message key="albopubblicazioni.label.validaAl" />
            <spring-form:input tabindex="6" id="validaAl_id" path="validaAl" size="10" maxlength="10" onblur="isValidDate(this,true);" />
				<init:calendar imagePath="/images/cal.gif" idImage="calvalidaAl" idInput="validaAl_id" textKey="label.calendar"/>
		   <spring-form:errors path="validaAl" cssClass="error"/></td>
        </tr>

	
        <tr id="amministrazioni">
			<td><fmt:message key="albopubblicazioni.label.amministrazioni" /></td>
			<td>
				<script type="text/javascript">
				function setHiddenFieldAmministrazioni(inputField,listItem){
					var a = listItem.id;
					document.getElementById('amministrazioni_id').value = inputField.value;
					document.getElementById('amministrazioni_hidden').value = a;
					
					ufficiDisplay();
				}
				</script>
				<spring-form:input tabindex="7" id="amministrazioni_id" path="amministrazioni.amministrazione" cssClass="searchbox" onchange="checkValue(this,'amministrazioni_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67"/>
				<init:autocompleter methodAjax="findAmministrazioni.htm?tutteLeAmministrazioni=false" afterUpdateElement="setHiddenFieldAmministrazioni" idHidden="amministrazioni_hidden"  idInput="amministrazioni_id" inputTitleKey="label.ricerca_amministrazione"/>
				<spring-form:errors path="amministrazioni" cssClass="error"/> 
				<spring-form:hidden id="amministrazioni_hidden" path="amministrazioni.id.codice" />
			</td>
		</tr>

    


<c:if test="${alboPubblicazioni.id.codice==null}">

        <tr id="uffici" style="<%=uffici%>">
			<td><fmt:message key="albopubblicazioni.label.uffici" /></td>
			<td>

				<spring-form:select tabindex="8" id="selectUffici" path="amministrazionireferenti.id.codice"></spring-form:select>
				<spring-form:errors path="amministrazionireferenti.id.codice" cssClass="error"/> 
			</td>
		</tr>
</c:if>  
<c:if test="${alboPubblicazioni.id.codice!=null}">

      <tr id="uffici" >
			<td><fmt:message key="albopubblicazioni.label.uffici" /></td>
            <td>
				<spring-form:select tabindex="8" id="selectUffici" path="amministrazionireferenti.id.codice"></spring-form:select>
				<spring-form:errors path="amministrazionireferenti.id.codice" cssClass="error"/> 
			</td>
		</tr>

<script type="text/javascript">

function ufficioDisplay(){
	removeOptionSelected("selectUffici");
	var idUfficio=document.getElementById("albocodice").value;
	new Ajax.Request('<%=request.getContextPath()%>/ajax/findUfficio.htm', {
		  method: 'post',
		  parameters: {code: idUfficio, limit: 12},
		  onSuccess: function(transport){
			  var response = transport.responseText;
			  $("uffici").appear();
			  var opts=response.split(",");
			  
			  for(var i=0;i<((opts.length)-1); i++ ){
				  var j=i+1;
				  try{  
				  	$('selectUffici').add(new Option( opts[j], opts[i]),  $('selectUffici').options[i]);
				  }
				  catch(e){ //in IE, try the below version instead of add()
					  $('selectUffici').add(new Option( opts[j], opts[i]));
				  }
				  i++;
			  }
			  try{  
				  	$('selectUffici').add(new Option( "Seleziona ufficio", ""),  $('selectUffici').options["Seleziona ufficio"]);
				  }
				  catch(e){ //in IE, try the below version instead of add()
					  $('selectUffici').add(new Option( "Seleziona ufficio", ""));
				  }
		    },
		  onFailure: function(){  }
						  
		  });

	}
ufficioDisplay();
</script>
</c:if>


         <tr>
			<td><fmt:message key="albopubblicazioni.label.numeroProtocollo" /></td>
			<td><spring-form:input tabindex="9" id="numeroProtocollo_id" path="numeroProtocollo" size="30" />
			<spring-form:errors path="numeroProtocollo" cssClass="error"/>

           <fmt:message key="albopubblicazioni.label.dataProtocollo" />
           <spring-form:input tabindex="10" id="dataProtocollo_id" path="dataProtocollo" size="10" maxlength="10" onblur="isValidDate(this,true);" />
				<init:calendar imagePath="/images/cal.gif" idImage="caldataProtocollo" idInput="dataProtocollo_id" textKey="label.calendar"/>
		   <spring-form:errors path="dataProtocollo" cssClass="error"/></td>


		</tr>
        <tr>
			<td><fmt:message  key="albopubblicazioni.label.note" /></td>
			<td><spring-form:textarea tabindex="11" id="note_id" path="note" cols="70" rows="10" />
			<spring-form:errors path="note" cssClass="error"/></td>
		 </tr>
        

	</table>
	
	
</spring-form:form>


	<script type='text/javascript'>
	$('numeroPubblicazione_id').focus();
	
	function ufficiDisplay(){
		
		removeOptionSelected("selectUffici");
		var idAmministrazione=document.getElementById("amministrazioni_hidden").value;
		new Ajax.Request('<%=request.getContextPath()%>/ajax/findUffici.htm', {
			  method: 'post',
			  parameters: {codiceAmministrazione: idAmministrazione, limit: 12},
			  onSuccess: function(transport){
				  var response = transport.responseText;
				  $("uffici").appear();
				  var opts=response.split(",");
				  try{  
					  	$('selectUffici').add(new Option( "Seleziona ufficio", ""),  $('selectUffici').options["Seleziona ufficio"]);
					  }
					  catch(e){ //in IE, try the below version instead of add()
						  $('selectUffici').add(new Option( "Seleziona ufficio", ""));
					  }
				  for(var i=0;i<((opts.length)-1); i++ ){
					  var j=i+1;
					  try{  
					  	$('selectUffici').add(new Option( opts[j], opts[i]),  $('selectUffici').options[i]);
					  }
					  catch(e){ //in IE, try the below version instead of add()
						  $('selectUffici').add(new Option( opts[j], opts[i]));
					  }
					  i++;
				  }
			    },
			  onFailure: function(){  }
							  
			  });
		
	}
	</script>






</div>
<div id="functions">
<ul>
	<c:if test="${alboPubblicazioni.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${alboPubblicazioni.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.deleteAlbopubblicazioni" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	 
	</c:if>
   <li> <a href="javascript:doHref('../history/back.htm?GoTo=../default_urlback','')"><fmt:message key="button.back" /></a></li>
	
</ul>
</div>
<c:if test="${alboPubblicazioni.id.codice!=null}">
<br></br><br></br>
<div id="subcontent">
<fieldset>
<legend><fmt:message key="albopubblicazioni.label.lista_allegati" /></legend>
<div class="jmesa">

							<table class="table" width="30%">
								<thead>
									<tr class="header">
										<td><fmt:message key="albopubblicazioni.label.descrizione_allegato" /></td>
                                        <td><fmt:message key="albopubblicazioni.label.nomefile_allegato" /></td>
										<td><fmt:message key="albopubblicazioni.label.ordine_allegato" /></td>
                                        <td><fmt:message key="label.azioni" /></td>
									</tr>
								</thead>
						    	<tbody class="tbody">
									<%int i=0; %>
								    <c:forEach items="${listpubblicazioniallegate}" var="pubblicazioneallegate">
						                 <tr class="<%=(i%2)==0?"odd":"even"%>">
											<td>
						                    	${pubblicazioneallegate.descrizione}                                
											</td>                    
						                    <td >                    
							                	${pubblicazioneallegate.oggetti.nomefile}
												<c:if test="${not empty pubblicazioneallegate.oggetti.dimensioneFileLeggibile}">												
													( ${pubblicazioneallegate.oggetti.dimensioneFileLeggibile})
												</c:if>
							                </td>  
                                            <td >                    
							                	${pubblicazioneallegate.ordine}
							                </td>                   
						                    <td >
                                            <a class="dettaglioColumn" href="viewAllegato.htm?codice=${pubblicazioneallegate.id.codice}"  title="<fmt:message key="label.edit.record" /> ${pubblicazioneallegate.id.codice}">
									            <label><fmt:message key="label.edit.record.image" /></label>
								            </a>
                                            <a class="eliminaRiga" href="deleteAlbopubblicazioneallegatoFromCodice.htm?codice=${pubblicazioneallegate.id.codice}"  title="<fmt:message key="label.elimina" /> ${pubblicazioneallegate.id.codice}">
									            <label><fmt:message key="label.elimina.image" /></label>
								            </a>
                                            <a class="visualizzaDocColumn" href="../file/ajaxDownload.htm?fileId=${pubblicazioneallegate.oggetti.id.codice}"  title="<fmt:message key="label.visualizza" /> ${pubblicazioneallegate.id.codice}">
									            <label><fmt:message key="label.visualizza.image" /></label>
								            </a>
                                            </td>
										</tr>
										<%i++; %>
							 		</c:forEach>
						 		</tbody>
					  		</table>

					  	</div>	
<div id="functions">
<ul>
<li><a href="javascript:doHref('createAllegato.htm?albopubblicazioni.codice.id=${alboPubblicazioni.id.codice}','')"><fmt:message key="button.nuovoallegato" /></a></li>
</ul>
</div>
</fieldset>
</div>
</c:if>
</body>
</html>
