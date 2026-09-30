<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.gestione_subentri.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.gestione_subentri.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<div id="subcontent">
		<script type="text/javascript">
			//<![CDATA[
				 var array=new Array();
		   		 function changeStatus(obj){
		   		 	var i=0;
		   		 	while(i<array.length){
						if(obj.checked){
							$('check'+array[i]).checked=true;
						}else{
							$('check'+array[i]).checked=false;
						}
						i++;
					}
		   		 }
		   		 function enableDisableNumAutField(autNumFieldId, regAuto){
			   		 if(regAuto == 'true'){
			   			 $(autNumFieldId).value = 'Numerazione da registro';
				   		 $(autNumFieldId).disabled = true;
			   		 }else{
				   		if($(autNumFieldId).value == 'Numerazione da registro'){
			   				$(autNumFieldId).value = '';
				   		}
			   			$(autNumFieldId).disabled = false;
			   		 }
		   		 }
		   		 function resetNumAutField(autNumFieldId,autHiddenFieldId,oldRegAutoValue){
					// alert(autNumFieldId+"-"+$(autHiddenFieldId).value+"-"+oldRegAutoValue);
			   		if($(autHiddenFieldId).value == ''){
			   			enableDisableNumAutField(autNumFieldId, oldRegAutoValue);
			   		}
		   		 }
		    //]]> 
		</script>
	 	<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice}</c:param>
	 	</c:import>
	 	<br class="clear"/>
		<spring-form:form commandName="autorizzazioniSubentriCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
	        	<jsp:param name="commandName" value="autorizzazioniSubentriCommand" />
	    	</jsp:include>
			<div>
			<table>			
			<tr>
				<td>
					<fmt:message key="label.causale_cessazione" />
				</td>
				<td>
					<spring-form:input id="causale_cess_id" path="causaleCessazione.descrizione" cssClass="searchbox" onchange="checkValue(this,'causale_cess_hidden')" size="50"  onkeydown="javascript:return searchAll(this,event)" />
					<init:autocompleter methodAjax="findConcessioniCausali.htm?flagStorico=true" idHidden="causale_cess_hidden" idInput="causale_cess_id" inputTitleKey="label.ricerca_causale"/>
					<spring-form:errors path="causaleAcquisizione" cssClass="error" />  
					<spring-form:hidden id="causale_cess_hidden" path="causaleCessazione.id.codice"  />
				</td>
				<td>
					<fmt:message key="label.data_cessazione" />
				</td>
				<td>
					<spring-form:input tabindex="3" id="dataCessazione_id" path="dataCessazione" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
					<init:calendar imagePath="/images/cal.gif" idImage="calDataCessazione" idInput="dataCessazione_id" textKey="label.calendar"/>
		   			<spring-form:errors path="dataCessazione" cssClass="error" />						 
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.causale_acquisizione" />
				</td>
				<td colspan="3">
					<spring-form:input id="causale_acq_id" path="causaleAcquisizione.descrizione" cssClass="searchbox" onchange="checkValue(this,'causale_acq_hidden')" size="50"  onkeydown="javascript:return searchAll(this,event)" />
					<init:autocompleter methodAjax="findConcessioniCausali.htm?flagStorico=false" idHidden="causale_acq_hidden" idInput="causale_acq_id" inputTitleKey="label.ricerca_causale"/>
					<spring-form:errors path="causaleAcquisizione" cssClass="error" />  
					<spring-form:hidden id="causale_acq_hidden" path="causaleAcquisizione.id.codice"  />
				</td>
			</tr>			
			</table>
			</div>
			<br />
			<fieldset><legend><fmt:message key="label.lista_autorizzazioni_concessioni" /></legend>
			<div class="jmesa">
			<table class="table">
				<thead class="header">
				<tr>
					<td><fmt:message key="label.tipo"/></td>
					<td><fmt:message key="label.numero"/></td>
					<td><fmt:message key="label.data"/></td>
					<td><fmt:message key="label.data_scadenza"/></td>
					<td><fmt:message key="label.comune"/></td>
					<td><fmt:message key="label.registro"/></td>
					<td><fmt:message key="label.istanza"/></td>
					<td><fmt:message key="label.anagrafe"/></td>
					<td><fmt:message key="mercatid.label.occupante"/></td>
					<td><fmt:message key="label.manifestazione"/></td>
					<td><fmt:message key="label.stato"/></td>
					<td align="center"><input type="checkbox" onclick="changeStatus(this);" title="<fmt:message key='label.checkbox.selDeselAll' />" checked="checked" /></td>
				</tr>
				</thead>
				<tbody class="tbody">
				<%int x=0; %>
				<c:forEach items="${autorizzazioniSubentriCommand.listAutDaSubentrare}" var="curr_auth" varStatus="authIdx" >
				<tr class="<%=(x%2)==0?"odd":"even"%>">
					<td>
						<c:if test="${curr_auth.concessione.id.codice!=null}"><fmt:message key="label.concessione" /></c:if>
						<c:if test="${curr_auth.concessione.id.codice==null}"><fmt:message key="label.autorizzazione" /></c:if>
					</td>
					<td>${curr_auth.autorizzazione.autoriznumero }<br />
						<c:choose>
						<c:when test="${curr_auth.registroAutomatico eq true}">
							<spring-form:input id="autoriznum_id${authIdx.index}" path="listAutDaSubentrare[${authIdx.index}].autoriznumeroSubentro" disabled="true" />
							<script type="text/javascript">
								$('autoriznum_id${authIdx.index}').value="Numerazione da registro";
							</script>
						</c:when>
						<c:otherwise>
							<spring-form:input id="autoriznum_id${authIdx.index}" path="listAutDaSubentrare[${authIdx.index}].autoriznumeroSubentro" />
						</c:otherwise>
						</c:choose>
					</td>
					<td width="9%">
						<fmt:formatDate value="${curr_auth.autorizzazione.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
						<br />
						<div style="position: relative;">
						<div style="float: left;">
						<spring-form:input id="autdatasub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizdataSubentro" size="10" onblur="isValidDate(this,true);" />
						</div>
						<div style="float: right;">
						<init:calendar imagePath="/images/cal.gif" idImage="cal_image${authIdx.index }" idInput="autdatasub_id${authIdx.index }" textKey="label.calendar" />
						</div>
						</div>
					</td>
					<td width="9%">
						<fmt:formatDate value="${curr_auth.autorizzazione.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
						<br />
						<div style="position: relative;">
						<div style="float: left;">
						<spring-form:input id="autdatascadenzasub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizdatascadenzaSubentro" size="10" onblur="isValidDate(this,true);" />
						</div>
						<div style="float: right;">
						<init:calendar imagePath="/images/cal.gif" idImage="cal_image_s_${authIdx.index }" idInput="autdatascadenzasub_id${authIdx.index }" textKey="label.calendar" />
						</div>
						</div>
					</td>
					<td>${curr_auth.autorizzazione.autorizcomune.comune}<br />
						<spring-form:input id="autcomsub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizcomuneSubentro.descrizioneEstesa" cssClass="searchbox" cssStyle="padding-left:18px;" onchange="checkValue(this,'autcomsub_hidden${authIdx.index }')" size="30" onkeydown="javascript:return searchAll(this,event)" />
						<init:autocompleter methodAjax="findEnteautorizzazione.htm" idHidden="autcomsub_hidden${authIdx.index }" idInput="autcomsub_id${authIdx.index }" inputTitleKey="label.ricerca_comune"/>  
						<spring-form:hidden id="autcomsub_hidden${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizcomuneSubentro.codicecomune"  />
					</td>
					<td>${curr_auth.autorizzazione.tipologiaregistro.trDescrizione}<br />
						<script type="text/javascript">
							function setHiddenFieldregistro${authIdx.index}(inputField,listItem){
								var codReg = listItem.id;
								var regAuto = listItem.name;
								var autNumFieldId = 'autoriznum_id${authIdx.index}';
								$('autregsub_id${authIdx.index}').value = inputField.value;
								$('autregsub_hidden${authIdx.index}').value = codReg;
								$('autregsub_id${authIdx.index}_choices').fade();
								enableDisableNumAutField(autNumFieldId, regAuto);
							}	
						</script>				
						<spring-form:input id="autregsub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizregistroSubentro.trDescrizione" cssClass="searchbox" cssStyle="padding-left:18px;" onchange="checkValue(this,'autregsub_hidden${authIdx.index }');resetNumAutField('autoriznum_id${authIdx.index}','autregsub_hidden${authIdx.index }','${curr_auth.registroAutomatico}');" size="30" onkeydown="javascript:return searchAll(this,event)" />
						<init:autocompleter methodAjax="findTipologiaRegistri.htm" afterUpdateElement="setHiddenFieldregistro${authIdx.index}" idHidden="autregsub_hidden${authIdx.index }" idInput="autregsub_id${authIdx.index }" inputTitleKey="label.ricerca_tipo_registro"/>  
						<spring-form:hidden id="autregsub_hidden${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizregistroSubentro.id.codice"  />
					</td>
					<td><c:out value="${curr_auth.autorizzazione.istanza.numeroistanza}" default="-" /></td>
					<td>${curr_auth.autorizzazione.anagrafe.descrizioneRichiedente}</td>
					<td>
						<c:if test="${not empty curr_auth.autorizzazione.istanza}">						
								${curr_auth.autorizzazione.istanza.titolareLegaleORichiedente.descrizioneRichiedente}
						</c:if>
					</td>
					<td>
						<c:if test="${curr_auth.concessione.id.codice!=null}">
							${curr_auth.concessione.mercati.descrizione } - ${curr_auth.concessione.mercatiUso.descrizione } - ${curr_auth.concessione.mercatiD.codiceposteggio}
						</c:if>
					</td>
					<td>
						<c:if test="${curr_auth.autorizzazione.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
						<c:if test="${curr_auth.autorizzazione.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.autorizzazione.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
					</td>
					<td align="center"><spring-form:checkbox path="listAutDaSubentrare[${authIdx.index}].daSubentrare" id="check${authIdx.index }" title="Segna per subentro" /></td>
				</tr>
				<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.id.codice!=null}">
				<tr class="<%=(x%2)==0?"odd":"even"%>">
					<td><fmt:message key="label.autorizzazione_collegata" /></td>
					<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autoriznumero }<br />
						<c:choose>
						<c:when test="${curr_auth.autorizzazioneCollegataHelper.registroAutomatico eq true}">
							<input id="autoriznumAutColl_id${authIdx.index}" name="" type="text" value="Numerazione da registro" disabled="disabled" />
						</c:when>
						<c:otherwise>
							<spring-form:input id="autoriznumAutColl_id${authIdx.index}" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autoriznumeroSubentro" />
						</c:otherwise>
						</c:choose>
					</td>
					<td>
						<fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
						<br />
						<div style="position: relative;">
						<div style="float: left;">
						<spring-form:input id="autColldatasub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizdataSubentro" size="10" onblur="isValidDate(this,true);" />
						</div>
						<div style="float: right;">
						<init:calendar imagePath="/images/cal.gif" idImage="calColl_image${authIdx.index }" idInput="autColldatasub_id${authIdx.index }" textKey="label.calendar" />
						</div>
						</div>
					</td>
					<td>
						<fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
						<br />
						<div style="position: relative;">
						<div style="float: left;">
						<spring-form:input id="autColldatascadenzasub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizdatascadenzaSubentro" size="10" onblur="isValidDate(this,true);" />
						</div>
						<div style="float: right;">
						<init:calendar imagePath="/images/cal.gif" idImage="calColl_image_s_${authIdx.index }" idInput="autColldatascadenzasub_id${authIdx.index }" textKey="label.calendar" />
						</div>
						</div>
					</td>
					<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autorizcomune.comune}<br />
						<spring-form:input id="autCollcomsub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizcomuneSubentro.descrizioneEstesa" cssClass="searchbox" cssStyle="padding-left:18px;" onchange="checkValue(this,'autCollcomsub_hidden${authIdx.index }')" size="30" onkeydown="javascript:return searchAll(this,event)" />
						<init:autocompleter methodAjax="findEnteautorizzazione.htm" idHidden="autCollcomsub_hidden${authIdx.index }" idInput="autCollcomsub_id${authIdx.index }" inputTitleKey="label.ricerca_comune"/>  
						<spring-form:hidden id="autCollcomsub_hidden${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizcomuneSubentro.codicecomune"  />
					</td>
					<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.trDescrizione}<br />
						<script type="text/javascript">
							function setHiddenFieldregistroAutColl${authIdx.index}(inputField,listItem){
								var codReg = listItem.id;
								var regAuto = listItem.name;
								var autNumFieldId = 'autoriznumAutColl_id${authIdx.index}';
								$('autCollregsub_id${authIdx.index}').value = inputField.value;
								$('autCollregsub_hidden${authIdx.index}').value = codReg;
								$('autCollregsub_id${authIdx.index}_choices').fade();
								enableDisableNumAutField(autNumFieldId, regAuto);
							}	
						</script>
						<spring-form:input id="autCollregsub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizregistroSubentro.trDescrizione" cssClass="searchbox" cssStyle="padding-left:18px;" onchange="checkValue(this,'autCollregsub_hidden${authIdx.index }');resetNumAutField('autoriznumAutColl_id${authIdx.index}','autCollregsub_hidden${authIdx.index }','${curr_auth.autorizzazioneCollegataHelper.registroAutomatico}');" size="30" onkeydown="javascript:return searchAll(this,event)" />
						<init:autocompleter methodAjax="findTipologiaRegistri.htm" afterUpdateElement="setHiddenFieldregistroAutColl${authIdx.index}" idHidden="autCollregsub_hidden${authIdx.index }" idInput="autCollregsub_id${authIdx.index }" inputTitleKey="label.ricerca_tipo_registro"/>  
						<spring-form:hidden id="autCollregsub_hidden${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizregistroSubentro.id.codice"  />
					</td>
					<td><c:out value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.istanza.numeroistanza}" default="-" /></td>
					<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.anagrafe.descrizioneRichiedente}</td>
					<td>&nbsp;</td>
					<td>
						<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
						<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
					</td>
					<td>&nbsp;</td>
				</tr>
				</c:if>
				<script type="text/javascript">
					array.push('${authIdx.index}');
				</script>
				<%x++; %>
				</c:forEach>
				</tbody>
			</table>
			</div>
			<div id="functions">
				<ul>
					<li><a href="javascript:doHref('createSearch.htm?codiceIstanza=${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice }&return_to=addToList','');"><fmt:message key="button.aggiungi" /></a></li>	
					<li><a href="javascript:doSubmit('removeFromList.htm','');"><fmt:message key="button.rimuovi" /></a></li>
				</ul>
			</div>
			</fieldset>
		</spring-form:form>
	</div>
	<br />
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertSubentri.htm','Attenzione! Stai per eseguire il subentro delle Autorizzazioni/Concessioni selezionate. Continuare?');"><fmt:message key="button.subentro" /></a></li>	
			<li><a href="javascript:doHref('newSearch.htm?codiceIstanza=${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice }','');"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>