 <?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${mercatid.id.codice==null}">
			<fmt:message key="mercatid.label.nuovo_posteggio.title" />
		</c:if> 
		<c:if test="${mercatid.id.codice!=null}">
			<fmt:message key="mercatid.label.dettaglio_posteggio.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${mercatid.id.codice==null}">
			<fmt:message key="mercatid.label.nuovo_posteggio.title" />
		</c:if> 
		<c:if test="${mercatid.id.codice!=null}">
			<fmt:message key="mercatid.label.dettaglio_posteggio.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatid/view" />
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.manifestazione" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${mercati.descrizione}" /></div>
		</div>
	</div>		        
    <br class="clear"/>
	<spring-form:form commandName="mercatid" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="mercatid" />
	    </jsp:include>
		     
				
		<table>
			<tr>
				<td>
					<fmt:message key="label.codiceposteggio" />
				</td>
				<c:if test="${mercatid.id.codice==null}">
				<td>
					<spring-form:input id="codiceposteggio_id" path="codiceposteggio" size="20" />
					<spring-form:errors path="codiceposteggio" cssClass="error"/>
				</td>
				</c:if>
				<c:if test="${mercatid.id.codice!=null}">
				<td>
				   <span id="label_codiceposteggio"><input type="text" id="codiceposteggio_id" readonly="readonly" name="_codiceposteggio" size="20" value="${mercatid.codiceposteggio}"/></span>
				   <span id="input_codiceposteggio" style="display: none;"><spring-form:input id="_codiceposteggio_id" path="codiceposteggio" size="20" /></span>
				   <input id="id_posteggio" type="hidden" name="id.codice" value="${mercatid.id.codice}" />
				   <a id="link_modifica_id" href="javascript:modificaCodicePoteggio();" title="<fmt:message key="label.modifica_codice_posteggio"/>"><fmt:message key="label.modifica"/>&#x00BB;</a>
				   <a id="mod_cod_post_id" style="display: none;" href="javascript:salvaCodicePosteggio()" title="<fmt:message key="label.salva_codice_posteggio" />"><img src="${pageContext.request.contextPath }/images/save.gif"/></a>
				   <a id="annulla_mod_id" style="display: none;" href="javascript:annullaModifica()" title="<fmt:message key="label.annulla_modifica" />"><img src="${pageContext.request.contextPath }/images/cross.gif"/></a>  
				</td>
				
					<script type="text/javascript">
						var oldCodicePosteggio = '';
						function modificaCodicePoteggio(){
							var visibile = showHideElement(document.getElementById('mod_cod_post_id'));
							showHideElement(document.getElementById('annulla_mod_id'));
							$("id_salva").style.display='none';
							if(visibile){
								oldCodicePosteggio = document.getElementById("_codiceposteggio_id").value;
								showHideElement(document.getElementById('link_modifica_id'));
								$("label_codiceposteggio").style.display='none';
								$("input_codiceposteggio").style.display='';
								$("_codiceposteggio_id").focus();
							}else{
								showHideElement(document.getElementById('link_modifica_id'));
								$("label_codiceposteggio").style.display='';
								$("input_codiceposteggio").style.display='none';
								// document.getElementById("entity_numeroistanza_id").disabled = true;
							}
						}
						
						function salvaCodicePosteggio(){
							// chiamata ajax
							var valore = document.getElementById("_codiceposteggio_id").value;
							var idPosteggio=document.getElementById("id_posteggio").value;
							new Ajax.Request('ajaxUpdateProprieta.htm', {
								  method: 'post',
								  parameters: {codicePosteggio: "${mercatid.codiceposteggio}", valore: valore,idPosteggio: idPosteggio },
								  onSuccess: function(transport){
									  var response = transport.responseText;
									  if(response!=''){
									   	alert(response);
									   	document.getElementById("_codiceposteggio_id").value=oldCodicePosteggio;
									  }else{
									  	alert("<fmt:message key="label.operazione_corretta" />");
									  	$('codiceposteggio_id').value=$("_codiceposteggio_id").value;
									  	modificaCodicePoteggio();
									  	$("id_salva").style.display='';
									  }
								  },
								  onFailure: function(transport){ 
									var response = transport.responseText;
								    alert(response); }						    		 
							} );											
						}
						
						
						function annullaModifica(){
							
							$('codiceposteggio_id').value=oldCodicePosteggio;
							$('_codiceposteggio_id').value=oldCodicePosteggio;
							$("mod_cod_post_id").style.display='none';
							$("annulla_mod_id").style.display='none';
							$("input_codiceposteggio").style.display='none';
							showHideElement(document.getElementById('label_codiceposteggio'));
							showHideElement(document.getElementById('link_modifica_id'));
							$("larghezza_id").focus();
							$("id_salva").style.display='';
						}
						
						
					</script>					
				
				</c:if>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.larghezza" />
				</td>
				<td>
				
					<spring-form:input id="larghezza_id" path="larghezza" size="8" cssStyle="text-align:right;"  onchange="checkNumberValue(this);calcolaSuperficie();" />
					<fmt:message key="label.metri" />
					<spring-form:errors path="larghezza" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.lunghezza" />
				</td>
				<td>
					<spring-form:input id="lunghezza_id" path="lunghezza" size="8" cssStyle="text-align:right;"  onchange="checkNumberValue(this);calcolaSuperficie();"/>
					<fmt:message key="label.metri" />
					<spring-form:errors path="lunghezza" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.superficie" />
				</td>
				<td>
					<spring-form:input id="superficie_id" path="superficie" cssStyle="text-align:right;" size="8" onchange="checkNumberValue(this);" />
					<fmt:message key="label.metri_quadri" />
					<spring-form:errors path="superficie" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.peso_importanza" />
				</td>
				<td>
					<spring-form:input id="peso_id" path="peso" cssStyle="text-align:right;" size="8" onchange="checkNumberInt(this);" />
					<init:help idHelp="help_peso" textKey="mercatid.help.peso" />
					<spring-form:errors path="peso" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.tipo_spazio" />
				</td>
				<td>
					<spring-form:input id="tipoSpazio_id" path="tipoSpazio.tipospazio" cssClass="searchbox" onchange="checkValue(this,'tipoSpazio_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
					<init:autocompleter methodAjax="findtipospazio.htm" idHidden="tipoSpazio_hidden" idInput="tipoSpazio_id" inputTitleKey="label.ricerca_tipo_spazio"></init:autocompleter>
					<spring-form:errors path="tipoSpazio.tipospazio" cssClass="error"/> 
					<spring-form:hidden id="tipoSpazio_hidden" path="tipoSpazio.id.codice"  />
				</td>
			</tr>
			
			<tr>
				<td>
					<fmt:message key="label.indirizzo" />
				</td>
				<td>
					<spring-form:input id="stradario_id" path="stradario.descrizioneCompleta" cssClass="searchbox" onchange="checkValue(this,'stradario_hidden')" onkeydown="javascript:return searchAll(this,event) " size="60"/>
					<init:autocompleter methodAjax="findStradarioMercato.htm?codicemercato=${mercati.id.codice}&searchDisabilitati=false" idHidden="stradario_hidden" idInput="stradario_id" inputTitleKey="label.ricerca_stradario"></init:autocompleter>
					<spring-form:errors path="stradario" cssClass="error"/> 
					<spring-form:hidden id="stradario_hidden" path="stradario.id.codice"  />
				</td>			
			</tr>
	        <tr>
		       <td><fmt:message key="label.disabilitato" /></td>
		       <td><spring-form:checkbox id="disabilitato_id" path="disabilitato"/>
		       <init:help idHelp="help1" textKey="mercatid.help.disabilitato"/>
		       <spring-form:errors path="disabilitato" cssClass="error"/></td>
	        </tr>
	        <tr>
		      <td><fmt:message key="label.note" /></td>
		      <td><spring-form:textarea id="note_id" path="note" cols="48" rows="8" /></td>
		      <td><spring-form:errors path="note" cssClass="error"/></td>
	        </tr>
		</table>
			<script type='text/javascript'>
				$('larghezza_id').focus();

				function calcolaSuperficie()
				{
					var lunghezza= $("lunghezza_id").value;
					var larghezza=$("larghezza_id").value;
					if(larghezza.indexOf(",",0)>0){
					larghezza=larghezza.replace(",",".");
					
					}
					if(lunghezza.indexOf(",",0)>0){
					lunghezza=lunghezza.replace(",",".");
					
					}
					var superficie=0;
					
					if(lunghezza>0 && larghezza>0)
					{
						superficie=lunghezza*larghezza;
						superficie=superficie.toString();
						if(superficie.indexOf(".",0)>0){
						superficie=superficie.replace(".",",");
						}
					    $("superficie_id").value=superficie;
					}else
					{
						$("superficie_id").value='';
					}
						
				}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${mercatid.id.codice==null}">
				<li ><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mercatid.id.codice!=null}">
				<li id="id_salva" ><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			    <!-- §§§BEGIN§§§ -->
			    <c:if test="${inite:isEnterprise()}">
				    <li><a
					href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatidconti/list.htm?posteggio.id.codice%3D${mercatid.id.codice}','')"><fmt:message
					key="button.conti" /></a></li>
				</c:if>
				<!-- §§§END§§§ -->
				<!-- La funzionalità è stata dismessa, verrà mostrata solo per i posteggi con ancora questa configurazione --> 
				<c:if test="${fn:length(mercatid.mercatiDattivitaistats)> 0}">
					<li><a
					href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid/listmerceologie.htm?codiceposteggio%3D${mercatid.id.codice}','')"><fmt:message
					key="button.merceologie" /></a></li>
				</c:if>
				<li><a
				href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatidcritass/list.htm?codicePosteggio%3D${mercatid.id.codice}','')"><fmt:message
				key="button.criteri_assegnazione" /></a></li>	
			    </c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>