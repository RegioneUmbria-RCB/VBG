<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_lavori_istanza" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_lavori_istanza" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzelavorit/list" />
	</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanza.id.codice}</c:param>
	</c:import>
	<br class="clear" />
	<div id="subcontent">
	<spring-form:form commandName="istanzelavorit" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzelavorit" />
		    </jsp:include>

 		<!-- CICLO TUTTE LE ISTANZE LAVORI T PRESENTI -->
 		<c:forEach items="${istanzelavoriTs}" var="istanzelavoriT"  varStatus="indice_istanzelavoriT">
 		<fieldset><legend><fmt:message key="label.istanze_lavori" /></legend>
 		<br class="clear" />
 		<table width="100%" border="0">
 			<tr>
 				<td width="10%" ><fmt:message key="label.indirizzo"/></td>
 				<td colspan="3">${istanzelavoriT.istanzestradario.stradario.descrizioneCompleta}
		  				<c:if test="${istanzelavoriT.istanzestradario.civico!=null}">
		  					&nbsp;${istanzelavoriT.istanzestradario.civico}
		  				</c:if>
 				</td>
 			</tr>
 			<tr>
 				<td width="10%" ><fmt:message key="label.categoria"/></td>
 				<td colspan="3">${istanzelavoriT.lavoritipi.lavoro}</td>
 			</tr>
 			<tr>
 				<td><fmt:message key="label.lavoro" /></td>
 				<td width="20%">
 					<textarea id="lavoro_text_area_id${indice_istanzelavoriT.index}" cols="70" rows="2" readonly="readonly">${istanzelavoriT.lavoro}</textarea>
 					<!-- DIV PER L'APERTURA DI UNA OVERLAY CHE PERMETTE DI MODIFICARE IL CAMPO LAVORO -->
 					
 				</td>  
 			    <td colspan="2">
 			    	<div id="infoLavoro${indice_istanzelavoriT.index}" dojoType="dijit.Dialog" title="<fmt:message key='label.lavoro' />" style="display: none;">
    					<div style="width: 500px; height: 200px;" id="lavoroContent${indice_istanzelavoriT.index}"></div>
					</div>
 					<a class="dettaglioColumn" href="javascript:showLavoro${indice_istanzelavoriT.index}(${istanzelavoriT.id.codice});" title="<fmt:message key="label.edit.record" /> ${istanzelavoriD.id.codice}">
						<label><fmt:message key="label.edit.record" /></label>
					</a>
					
				</td>
 			</tr>
 			 <!-- TABELLA STILE JMESA CHE VISUALIZA TUTTE LE ISTANZE LAVORI D DI TABELLA TESTATA (ISTANZELAVORIT) -->
 			<tr>
 				<td colspan="4">
 				    <div class="jmesa" >
        			<table border="0"  cellpadding="0"  cellspacing="0"  class="table" width="100%">
 						<thead>
							<tr  class="header">
								<td width="60%"><fmt:message key="label.descrizione"/></td>
								<td width="10%"><fmt:message key="label.unita_misura"/></td>
								<td width="5%"><fmt:message key="label.quantita"/></td>
								<td width="5%"><fmt:message key="label.costo_unitario"/></td>
								<td width="5%"><fmt:message key="label.totale"/></td>
								<td width="5%"><fmt:message key="label.azioni"/></td>
							</tr>
						</thead>
		                <%
					    int i=0;
					    %>
						<tbody class="tbody">
						<c:forEach items="${istanzelavoriT.istanzelavoriDs}" var="istanzelavoriD" varStatus="indice_istanzelavoriD">
						
							<tr class="<%=(i%2)==0?"odd":"even"%>">
								<td><b>${istanzelavoriD.tipicausalioneri.raggruppamentocausalioneri.rcoDescr}</b>&nbsp;${istanzelavoriD.tipicausalioneri.coDescrizione}</td>
								<td>${istanzelavoriD.tipiunitamisura.umDescrbreve}</td>		
								<td><input id="quantita_id${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}" style="text-align: right;" size="6" type="text" value="${istanzelavoriD.quantita}" onchange="changeCostounitarioOrQuantita${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}('costo_id${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}','quantita_id${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}')"></input></td>
		                       	<td><input id="costo_id${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}" style="text-align: right;" size="6" type="text" value="${istanzelavoriD.costoUnitarioUm}" onchange="changeCostounitarioOrQuantita${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}('costo_id${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}','quantita_id${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}')"></input></td>
								<td>
									<input id="id_totale${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}" style="text-align: right;" size="8" type="text" value="${istanzelavoriD.totale}" readonly="readonly"></input>
								    <div id="result${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}" style="display: none;"></div>
								</td>
		                        <td >
									<a class="eliminaRiga" href="javascript:doHref('deleteIstanzelavoriD.htm?codiceIstanzeLavorid=${istanzelavoriD.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />&nbsp;${istanzelavoriD.tipicausalioneri.raggruppamentocausalioneri.rcoDescr}&nbsp;${istanzelavoriD.tipicausalioneri.coDescrizione}">
										<label><fmt:message key="label.azioni" /></label>
									</a> 
								</td>
									
							</tr>
						<%i++;%>
						
						<script type="text/javascript">
							function changeCostounitarioOrQuantita${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}(costounitario,quantita){
								
								var costo=document.getElementById(costounitario).value;
								var qu=document.getElementById(quantita).value;
								new Ajax.Request('${pageContext.request.contextPath}/istanzelavorit/ajaxChangeQuantiaOrCostoUnitario.htm?codice=${istanzelavoriD.id.codice}&costounitario='+costo+'&quantita='+qu, {
									  method: 'post',	
									  onSuccess: function(transport){
										 	$('result${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').pulsate
											$('result${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').innerHTML = transport.responseText;
											$('result${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').className = 'succes_ajax_call'
											$('result${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').style.display = '';
											applyStyle();
											$('result${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').pulsate
											({
												pulses : 2,
												duration : 2.0
											});
											$('result${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').fade(
											{
												delay    : 2.0,
												duration : 1.0
											});
											$('id_totale${indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').value=costo*qu;
											
								    },
									  onFailure: function(transport){
											$('result{indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').innerHTML = transport.responseText;
											$('result{indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').className = 'error_ajax_call'
											$('result{indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').style.display = '';
											applyStyle();
											$('result{indice_istanzelavoriT.index}${indice_istanzelavoriD.index}').pulsate({
												pulses : 2,
												duration : 1.0
											});
										
									  }						    		 
								});
							}
							
							function showLavoro${indice_istanzelavoriT.index}(codice){
								
								 var ts=(new Date()).getTime();
							     var url = '<%=request.getContextPath() %>/istanzelavorit/ajaxShowViewIstanzelavoriT.htm?ts_='+ts+'&codice='+codice;
							     var target = 'lavoroContent${indice_istanzelavoriT.index}';
							     var myAjax = new Ajax.Updater(target, url, {method: 'get'});	          
							     dijit.byId('infoLavoro${indice_istanzelavoriT.index}').show();
							}
							
							function saveLavoro${istanzelavoriT.id.codice}(codice,objForm){
								var qs = getQueryStringFromForm(objForm);
								new Ajax.Request('${pageContext.request.contextPath}/istanzelavorit/ajaxSaveIstanzelavorit.htm?'+qs, {
									  method: 'post',	
									  onSuccess: function(transport){
										 
										  	$('lavoro').pulsate
										  	var result=transport.responseText.split('#')
											$('lavoro_text_area_id${indice_istanzelavoriT.index}').innerHTML = result[0];
										  	$('aggiornato${istanzelavoriT.id.codice}').innerHTML=result[1]
											$('aggiornato${istanzelavoriT.id.codice}').className = 'succes_ajax_call'
											$('aggiornato${istanzelavoriT.id.codice}').style.display = '';
											$('aggiornato${istanzelavoriT.id.codice}').pulsate
											({
												pulses : 2,
												duration : 2.0
											});
											
								    },
									  onFailure: function(transport){
											$('aggiornato${istanzelavoriT.id.codice}').innerHTML = transport.responseText;
											$('aggiornato${istanzelavoriT.id.codice}').className = 'error_ajax_call'
											$('aggiornato${istanzelavoriT.id.codice}').style.display = '';
											$('aggiornato${istanzelavoriT.id.codice}').pulsate({
												pulses : 2,
												duration : 1.0
											});
										
									  }						    		 
								});
							}
						</script >
						</c:forEach>
					  
						<tr align="right">
							<td colspan="5"></td>
							<td width="5%">
								<a class="addColumn" href="javascript:doHref('createIstanzelavoriD.htm?codiceIstanzaLavoriT=${istanzelavoriT.id.codice}','')" title="<fmt:message key="label.aggiungi" /> ${istanzelavoriD.id.codice}">
									<label><fmt:message key="label.azioni" /></label>
								</a> 
						 	</td>
						 	
						</tr>
						
						</tbody>
						
 					</table>
 					
 					</div>
 				</td>
 			</tr>
 		</table>
 		<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('delete.htm?codiceIstanzeLavorit=${istanzelavoriT.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</ul>
		</div>
 		</fieldset>
 		</c:forEach>
 		
 	</spring-form:form>		
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codiceIstanza=${istanza.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<c:if test="${not empty istanzelavoriTs}">
				<li><a href="javascript:doHref('copiaOneri.htm?codiceIstanza=${istanza.id.codice}','');"><fmt:message key="button.copia_oneri" /></a></li>			
				<%-- STAMPA --%>
				<%pageContext.setAttribute("URL_STAMPA",BackofficeNETConstants.getURL_STAMPA_ISTANZELAVORIT());%>
				<c:set var="_URL_STAMPA" value="${URL_STAMPA}?CodiceIstanza=${istanza.id.codice}&Doc_base=ISTANZELAVORI" /><c:set var="_URL_STAMPA" value="${inite:geturlto(pageContext.request, _URL_STAMPA, _urlback, null, true)}" />
				<li><a href="javascript:void 0"	onclick="window.open('${_URL_STAMPA}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message key="button.stampa" /></a></li>
				<%-- END STAMPA --%>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>