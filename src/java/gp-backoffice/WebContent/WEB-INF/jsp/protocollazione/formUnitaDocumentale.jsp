<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ page import="it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl" %>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.riversa_documenti_docer" /></title>
</head>
<body>
<span class="titoloPagina"> <fmt:message
	key="label.riversa_documenti_docer" /> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
	<jsp:include page="../includes/history.jsp">
	   	<jsp:param name="path" value="../protocollazione/createUnitaDocumentale" />
	</jsp:include>
	<c:if test="${not empty protocolloCommand.entity.id.codice}">
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${protocolloCommand.entity.id.codice}</c:param>
		</c:import>
	</c:if>
	<c:if test="${protocolloCommand.provenienza eq 'P'}">
		<fieldset>
			<legend><fmt:message key="pecinbox.label.dati_pec"/></legend> 								
			<table width="100%" border="0">
			<jsp:include page="../pecinbox/datiPEC.jsp" >
		        <jsp:param name="commandName" value="protocolloCommand" />
		        <jsp:param name="labelWidthPercentage" value="15" />
		    </jsp:include>
		    </table>
		</fieldset>
		
		
				<ul id="functions">
					<li><a href="javascript:void(0)" onclick="cercaPraticaSTC()"><fmt:message key="label.ricerca_istanza" /> >></a></li>	
				</ul>
				<script type="text/javascript">
					function cercaPraticaSTC(){
						var ww = window.open("${pageContext.request.contextPath}/stc/popupPannelloRicerca.htm",699,'status=1,menubar=0,scrollbars=1,width=800, height=600');
					}
				</script>		
		
	</c:if>
	<br class="clear" />
<div id="subcontent">
<spring-form:form commandName="protocolloCommand" name="inviodati">
     
    <!-- VARIABILI UTILIZZATE NELLA JSP PER FARE DEI CONTROLLI -->    
    <c:set value="${protocolloCommand.movimento.id.codice}" var="codiceMov" scope="page"></c:set>
    
    <!-- --------------------------------------------------------- --> 

	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="protocolloCommand" />
	</jsp:include>
	<c:set var="_NUMDATAPROTMITT"><%=request.getAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NUMDATAPROTMITT)%></c:set>
	<c:set var="_NOALLEGATI"><%=request.getAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NOALLEGATI)%></c:set>
	<c:set var="_GESTISCI_FASCICOLAZIONE"><%=request.getAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE)%></c:set>	
	<table width="60%">
	
	
	<c:if test="${not empty documentiList}">
			<tr>
				<td><fmt:message key="label.tipo_documento" />
				</td>
				<td>
					<spring-form:select id="tipoDocumento_id" path="tipoDocumento" >
						<spring-form:option value=""></spring-form:option>
						<spring-form:options items="${documentiList}" itemLabel="descrizione" itemValue="codice"/>
					</spring-form:select>
					<spring-form:errors path="tipoDocumento" cssClass="error"/>
				</td>
			</tr>
	</c:if>
	
	</table>

    
    <br class="clear" />
       
            <%--Inizializzo le variabili --%>
    		<c:set scope="page" value="0" var="num_doc_ist"></c:set>
			<c:set scope="page" value="0" var="num_doc_mov"></c:set>
			<c:set scope="page" value="0" var="num_doc_altri_mov"></c:set>
			<c:set scope="page" value="0" var="num_doc_endo"></c:set>
    	<c:choose>
			<c:when test="${_NOALLEGATI eq '1' }">
				<c:set scope="page" value="0" var="num_doc_ist"></c:set>
			<c:set scope="page" value="0" var="num_doc_mov"></c:set>
			<c:set scope="page" value="0" var="num_doc_altri_mov"></c:set>
			<c:set scope="page" value="0" var="num_doc_endo"></c:set>
			</c:when>
			<c:otherwise>
		
	
			<script type="text/javascript">
			
			
			function selezionaAndDeselezionaTutti(){
				var checkIt = jQuery('#a_check_allegati').prop('checked');
				jQuery("input[id^='di_']").prop('checked', checkIt);
				jQuery("input[id^='ma_']").not(':disabled').prop('checked', checkIt);
				jQuery("input[id^='altrima_']").not(':disabled').prop('checked', checkIt);
				jQuery("input[id^='ia_']").prop('checked', checkIt);
				jQuery("input[id^='docanagr_']").prop('checked', checkIt);
			}
			</script>
		
        <fieldset><legend><fmt:message	key="label.sezione_documenti" /></legend>
         
	            <div> 
	                <div style="float: right;">
					<input id="a_check_allegati" type="checkbox" onclick="selezionaAndDeselezionaTutti()"></input>
					<label id="message_label"><fmt:message key="label.seleziona_deseleziona_tutti" /></label>
				    </div>
				</div>
				
        <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DEL MOVIMENTO  -->
            
			<div class="jmesa">
			<table border="0" cellpadding="2" cellspacing="0" class="table">
			<c:if test="${not empty protocolloCommand.documentiHelper.documentiMovimentoList}">
       			  <%int i=1;%>
				<%-- <thead> --%>
					<tr class="header" >
						<td colspan="5">
							<fmt:message key="label.documenti_movimento" />
						</td>
					</tr>
					
				<%-- </thead> --%>
				<%--<tbody class="tbody">--%>
				
				<c:forEach items="${protocolloCommand.documentiHelper.documentiMovimentoList}" var="current" varStatus="a">
					<tr>
						<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
						<td width="75%" colspan="4">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="4"><fmt:message key="movimentimail.label.documento" /></td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
								<td width="15%"><fmt:message key="label.principale" /></td>
			            	</tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b">	
							    <tr class= "<%=(i%2)==0?"odd":"even"%>">
								<td width="50%">${var.descrizione}</td>
								<td width="35%">${var.nomeFile}</td>
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
			   						</jsp:include>
   							    </td>
   							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>		
								<td>
									<spring:bind path="documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
									<c:if test="${codiceMov == null }">
										<input id="ma_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
								    <c:if test="${codiceMov != null }">
										<input id="ma_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
									</spring:bind>
								</td>	
							    <td><input id="radio_button_id${var.id.codice}" type="radio" value="${var.codiceOggetto}" name="documentoPrincipale"/></td>
							    
							</tr>
							<%i++;%>								
							</c:forEach>
							
						</table>
						</td>
					</tr>
				</c:forEach>	
		   </c:if>		
		   
		   <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DEGLI ALTRI MOVIMENTI  -->
		   <c:if test="${not empty protocolloCommand.documentiHelper.documentiAltriMovimentiList}">
		   <%int j=1;%>
				<tr class="header" >
						<td colspan="5">
							<fmt:message key="label.documenti_altri_movimenti" />
						</td>
				</tr>
				<c:forEach items="${protocolloCommand.documentiHelper.documentiAltriMovimentiList}" var="current" varStatus="a">
					<tr>
						<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
						<td width="75%" colspan="4">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="4" ><fmt:message key="movimentimail.label.documento" /> </td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
								<td width="15%"><fmt:message key="label.principale" /></td>
				            </tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b"> 							
								<tr class= "<%=(j%2)==0?"odd":"even"%>">
								<td width="50%">${var.descrizione}</td>
								<td width="35%">${var.nomeFile}</td>
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
			   						</jsp:include>
   							    </td>
   							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>		
								<td>
									<spring:bind path="documentiHelper.documentiAltriMovimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
									<c:if test="${codiceMov == null }">
										<input id="altrima_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
								    <c:if test="${codiceMov != null }">
										<input id="altrima_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
									</spring:bind>
								</td>	
								<td><input id="radio_button_id${var.id.codice}" type="radio" value="${var.codiceOggetto}" name="documentoPrincipale"/></td>						
							<%j++; %>	
							</c:forEach>
							
						</table>
						</td>
					</tr>
				</c:forEach>	
        </c:if>
        <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DELL' ISTANZA  -->
        <c:if test="${not empty protocolloCommand.documentiHelper.documentiIstanzaList}">
        <%int k=1;%>
				<tr class="header" >
						<td colspan="5">
							<fmt:message key="label.allegati_istanza" />
						</td>
				</tr>
			<c:forEach items="${protocolloCommand.documentiHelper.documentiIstanzaList}" var="current" varStatus="a">
				<tr>
					<td width="25%"  style="vertical-align: top;" ><b>${current.chiave}</b></td>
					<td width="75%" colspan="4">
					<table width="100%">
						<tr class="header" >
						    <td width="98%" colspan="4"><fmt:message key="movimentimail.label.documento" /></td>
							<td width="2%" ><fmt:message key="label.seleziona" /></td>
							<td width="15%"><fmt:message key="label.principale" /></td>
			            </tr>
						<c:forEach items="${current.valore}" var="var" varStatus="b">	
							<tr class= "<%=(k%2)==0?"odd":"even"%>">
								<td width="50%">${var.documento}</td>
								<td width="35%">${var.nomeFile}</td>
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
			   						</jsp:include>
   							    </td>
   							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>	
								<td>
									<spring:bind path="documentiHelper.documentiIstanzaList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
									<c:if test="${codiceMov == null }">
										<input id="di_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
								    <c:if test="${codiceMov != null }">
										<input id="di_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
									</spring:bind>
								</td>	
								<td><input id="radio_button_id${var.id.codice}" type="radio" value="${var.codiceOggetto}" name="documentoPrincipale" /></td>
							</tr>
							<%k++; %>								
						</c:forEach>
					</table>
					</td>
				</tr>
			    </c:forEach>
			 </c:if>	
     
		     <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DEGLI ENDO  DELL' ISTANZA  -->
		     <c:if test="${not empty protocolloCommand.documentiHelper.documentiEndoprocedimentiList}">
		     <%int b=1;%>
					<tr class="header" >
						<td colspan="5">
							<fmt:message key="label.allegati_endoprocedimenti" />
						</td>
					</tr>
					<c:forEach items="${protocolloCommand.documentiHelper.documentiEndoprocedimentiList}" var="current" varStatus="a">
						<tr>
							<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
							<td width="75%" colspan="4">
							<table width="100%">
								<tr class="header" >
								    <td width="98%" colspan="4"><fmt:message key="movimentimail.label.documento" /></td>
									<td width="2%" ><fmt:message key="label.seleziona" /></td>
									<td width="15%"><fmt:message key="label.principale" /></td>
					            </tr>
								<c:forEach items="${current.valore}" var="var" varStatus="b">	
									<tr class= "<%=(b%2)==0?"odd":"even"%>">
										<td width="50%">${var.allegatoextra}</td>
										<td width="35%">${var.nomeFile}</td>
										<td width="13%">
											<jsp:include page="../includes/visualizzaOggetto.jsp" >
					       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
					       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
					   						</jsp:include>
		   							    </td>
		   							    <td>
		   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
					       						<jsp:param name="controllook" value="${var.controllook}" />
					   						</jsp:include>
   							    		</td>		
										<td>
											<spring:bind path="documentiHelper.documentiEndoprocedimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
											<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
											<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
											<c:if test="${codiceMov == null }">
												<input id="ia_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
										    </c:if>
										    <c:if test="${codiceMov != null }">
												<input id="ia_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
										    </c:if>
											</spring:bind>
										</td>	
										<td><input id="radio_button_id${var.id.codice}" type="radio" value="${var.codiceOggetto}" name="documentoPrincipale" /></td>
									 </tr>
									<%b++; %>								
								</c:forEach>
							</table>
							</td>
						</tr>
					</c:forEach>	
					</c:if> 
				    <!-- SEZIONE DOCUMENTI DELLE ANAGRAFICHE -->
					<c:if test="${not empty protocolloCommand.documentiHelper.documentiAnagrafeList}">
					<%int p=1;%>
					<%-- <div class="jmesa">
					<table border="0" cellpadding="2" cellspacing="0" class="table">
						<thead>
					--%>	
					<tr class="header" >
							<td colspan="5">
								<fmt:message key="label.allegati_anagrafiche" />
							</td>
					</tr>
					<c:forEach items="${protocolloCommand.documentiHelper.documentiAnagrafeList}" var="current" varStatus="a">
					<tr>
						<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
						<td width="75%" colspan="4">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="4"><fmt:message key="movimentimail.label.documento" /></td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
								<td width="15%"><fmt:message key="label.principale" /></td>
				            </tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b">	
								<tr class= "<%=(p%2)==0?"odd":"even"%>">
									<td width="50%">${var.tipidocumento.documento}</td>
									<td width="35%">${var.oggetto.nomefile}</td>
									<td width="13%">
										<jsp:include page="../includes/visualizzaOggetto.jsp" >
				       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
				       						<jsp:param name="fileId" value="${var.oggetto.id.codice}" />
				   						</jsp:include>
		  							 </td>
		  							 <td>
		  							    &nbsp;
		  							    <%-- 
	   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
				       						<jsp:param name="controllook" value="${var.controllook}" />
				   						</jsp:include>
				   						--%>
				   						
   							    	</td>		
									<td>
										<spring:bind path="documentiHelper.documentiAnagrafeList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
											<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
											<input id="docanagr_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
										</spring:bind>
									</td>	
									<td><input id="radio_button_id${var.id.codice}" type="radio" value="${var.oggetto.id.codice}" name="documentoPrincipale" /></td>
								</tr>
								<%p++; %>								
							</c:forEach>
						</table>
						</td>
					</tr>
				</c:forEach>
				</c:if>	
		    
		    
		    <%-- DOCUMENTO PER PROTOCOLLAZIONE PEC --%>
			<c:if test="${protocolloCommand.provenienza eq 'P'}">
					<thead>
						<tr class="header">
							<td width="90%"><fmt:message key="label.allegati" /></td>
							<td width="5%"><fmt:message key="label.seleziona" /></td>

							<td width="5%"><fmt:message key="label.principale" /></td>

						</tr>
					</thead>
					<tbody>
						<tr class="even">
							<td width="90%">Messaggio PEC: ${protocolloCommand.pec.pecSubject} </td>
							<td width="5%"> 
								<input id="pec_doc" type="checkbox" name="pec_doc" value="true" checked="true" 	disabled="disabled"/>
							</td>
							<td width="5%">
								<input id="radio_button_doc_pec" type="radio" value="1" name="documentoPrincipale" checked="true"/> 
							</td>
						</tr>
					</tbody>
			</c:if>
		    </table>
		    </div>
		   </fieldset>
		  </c:otherwise>
		 </c:choose>
         <br class="clear" />
    
    <!-- CODICE TOLTO START -->
    <!-- CODICE TOLTO END -->	
       
		<br class="clear" />
	
</spring-form:form>

</div>

<script type="text/javascript">

	function riversa()
	{
		
		if(verifica()){
			doSubmit('insertUnitaDocumentale.htm', '',document.inviodati);
		}
	}
	
    function verifica(){    	
    		if(jQuery('#tipoDocumento_id').val()==''){
    			alert('Tipo documento obbligatorio');
    			return false;
    		}
	    	var rb_scelto = false;	    	
	        if(document.inviodati.documentoPrincipale){ //ce ne è più di uno
	        		if(document.inviodati.documentoPrincipale.length){
	                 for (var counter = 0; counter <= document.inviodati.documentoPrincipale.length; counter++) {
	                 	if (document.inviodati.documentoPrincipale[counter].checked){
	                         rb_scelto = true;
	                         break;
	                 	}
	                 }
	        }else{// uno o nessuno
	        	if (document.inviodati.documentoPrincipale){
		        	if (document.inviodati.documentoPrincipale.checked){
	                    rb_scelto = true;
	            	}
	        	}
	        }
	                 if (!rb_scelto) {
	                	 alert('\nAttenzione,allegato principale non selezionato. E\' necessario, selezionarne uno prima di procedere');
	                     return (false);
	                 }
	         }
	         return (true);

    }
   
	
</script>
<div id="functions">
<ul>
	<li><a href="javascript:riversa('');"><fmt:message key="label.riversa_documenti_docer" /></a></li>
	<li><a href="javascript:historyBack('');"><fmt:message	key="button.back" /></a></li>
</ul>
</div>
</body>
</html>