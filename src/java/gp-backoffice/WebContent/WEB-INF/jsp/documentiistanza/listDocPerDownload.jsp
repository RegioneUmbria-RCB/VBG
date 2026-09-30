<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="documentiistanza.label.lista_documenti_istanza.title" />
	</title>
</head>
<body>
	
	<span class="titoloPagina">
		<fmt:message key="documentiistanza.label.lista_documenti_istanza.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
<div id="subcontent">

<spring-form:form commandName="documentiistanza" name="inviodati" id="formDaInviare">
			
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="documentiistanza" />
		    </jsp:include>

		<script type="text/javascript">
			
			
			function selezionaAndDeselezionaTutti(){
				var checkIt = jQuery('#a_check_allegati').prop('checked');
				jQuery("input[id^='di_']").prop('checked', checkIt);
				jQuery("input[id^='ma_']").not(':disabled').prop('checked', checkIt);
				jQuery("input[id^='altrima_']").not(':disabled').prop('checked', checkIt);
				jQuery("input[id^='ia_']").prop('checked', checkIt);
				jQuery("input[id^='docanagr_']").prop('checked', checkIt);
				jQuery("input[id^='dp_']").prop('checked', checkIt);
				jQuery("input[id^='cds_']").prop('checked', checkIt);
				if(checkIt){
					setPanelVisible('id_documenti_mov_table', 'id_link_documenti_mov',true);
					setPanelVisible('id_documenti_endo_table', 'id_link_endo_anagrafe',true);
					setPanelVisible('id_documenti_procure_table', 'id_link_procure_anagrafe',true);
					setPanelVisible('id_documenti_anagrafe_table', 'id_link_documenti_anagrafe',true);
					setPanelVisible('id_documenti_cds_table', 'id_link_documenti_cds',true);
				}				
			}
			</script>
			<style type="text/css">
				.message {
				display: none;
				color: green;
				}
			</style>
			
			<table style="display: none;" id="message_id" border="0" cellpadding="2" cellspacing="0" class="table">
			<tr><td style="color: green;">Archivio zip creato attendere la maschera per effettuare il download, premere chiudi per tornare ai documenti dell'istanza</td></tr>
			</table>
			
			
			<fieldset id="fieldset_documenti_id"><legend><fmt:message	key="label.sezione_documenti" /></legend>
			
			 <div> 
	                <div style="float: right;">
					<input id="a_check_allegati" type="checkbox" onclick="selezionaAndDeselezionaTutti()"></input>
					<label id="message_label"><fmt:message key="label.seleziona_deseleziona_tutti" /></label>
				    </div>
				</div>
			
			<div class="jmesa">
			<table border="0" cellpadding="2" cellspacing="0" class="table">
			    <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DELL' ISTANZA  -->
		        <%
					
					String displayDocumentiIstanza= "";
					String styleDocumentiIstanza = "sezioneDatiMeno";
					
				%>
		        <c:if test="${not empty documentiistanza.documentiHelper.documentiIstanzaList}">
		        <%int k=1;%>
					<tr class="header" >
						<td colspan="5">
							<a class="<%=styleDocumentiIstanza%>" id="id_link_docuemnti_istanza" href="javascript:showHidePanelBase('id_documenti_istanza_table', 'id_link_docuemnti_istanza','', '${pageContext.request.contextPath}/images/',false);"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.allegati_istanza" />">
								<label for="id_link_docuemnti_istanza"> <fmt:message key="label.allegati_istanza" /></label>
							</a>
						</td>
					</tr>
					<c:forEach items="${documentiistanza.documentiHelper.documentiIstanzaList}" var="current" varStatus="a">
						<tr id="id_documenti_istanza_table" style="<%=displayDocumentiIstanza%>;">
							<td width="25%"  style="vertical-align: top;" ><b>${current.chiave}</b></td>
							<td width="75%" colspan="4">
							<table width="100%">
								<tr class="header" >
								    <td width="98%" colspan="4"><fmt:message key="label.documento" /></td>
									<td width="2%" ><fmt:message key="label.seleziona" /></td>
									
					            </tr>
								<c:forEach items="${current.valore}" var="var" varStatus="b">	
									<tr class= "<%=(k%2)==0?"odd":"even"%>">
										<td width="50%">${var.documento}</td>
										<td colspan="2" width="35%">${var.nomeFile}</td>
										<%-- 
										<td width="13%">
											<jsp:include page="../includes/visualizzaOggetto.jsp" >
					       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
					       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
					   						</jsp:include>
		   							    </td>
		   							   --%>
		   							    <td>
		   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
					       						<jsp:param name="controllook" value="${var.controllook}" />
					   						</jsp:include>
	   							    	</td>
	   							    		
										<td>
											<spring:bind path="documentiHelper.documentiIstanzaList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
											<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
											<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
												<input id="di_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
											</spring:bind>
										</td>	
										
									</tr>
									<%k++; %>								
								</c:forEach>
							</table>
							</td>
						</tr>
					</c:forEach>	
			 </c:if>
			 <!-- GESTIONE DEI DOCUMENTI DEGLI ENDO PROCEDIMENTI -->
	     	 <%
					
					String displayDocumentiEndo= "display:none;";
					String styleDocumentiEndo = "sezioneDatiPiu";
					
			%>
	     	<c:if test="${not empty documentiistanza.documentiHelper.documentiEndoprocedimentiList}">
            <%int b=1;%>
			<tr class="header" >
				<td colspan="5">
				    <a class="<%=styleDocumentiEndo%>" id="id_link_documenti_endo" href="javascript:showHidePanelBase('id_documenti_endo_table', 'id_link_documenti_endo','', '${pageContext.request.contextPath}/images/',false);"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.allegati_endoprocedimenti" />">
							<label for="id_link_documenti_endo"> <fmt:message key="label.allegati_endoprocedimenti" /></label>
					</a>
					
				</td>
			</tr>
			<c:forEach items="${documentiistanza.documentiHelper.documentiEndoprocedimentiList}" var="current" varStatus="a">
				<tr id="id_documenti_endo_table" style="<%=displayDocumentiEndo%>;">
					<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
					<td width="75%" colspan="4">
					<table width="100%">
						<tr class="header" >
						    <td width="98%" colspan="4"><fmt:message key="label.documento" /></td>
							<td width="2%" ><fmt:message key="label.seleziona" /></td>
			            </tr>
						<c:forEach items="${current.valore}" var="var" varStatus="b">	
							<tr class= "<%=(b%2)==0?"odd":"even"%>">
								<td width="50%">${var.allegatoextra}</td>
								<td colspan="2" width="35%">${var.nomeFile}</td>
								<%--
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
			   						</jsp:include>
   							    </td>
   							   --%>
 							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>	
								<td>
									<spring:bind path="documentiHelper.documentiEndoprocedimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
										<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
										<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
										<input id="ia_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
									</spring:bind>
								</td>	
							 </tr>
							<%b++; %>								
						</c:forEach>
					</table>
					</td>
				</tr>
			</c:forEach>	
			</c:if>	
		   <!-- SEZIONE DOCUMENTI DEL ALTRI MOVIMENT -->
		    <%
					
					String displayDocumentiMov= "display:none;";
					String styleDocumentiMov = "sezioneDatiPiu";
					
			%>
		   <c:if test="${not empty documentiistanza.documentiHelper.documentiAltriMovimentiList}">
		   <%int j=1;%>
				<tr class="header" >
					<td colspan="5">
					     <a class="<%=styleDocumentiMov%>" id="id_link_documenti_mov" href="javascript:showHidePanelBase('id_documenti_mov_table', 'id_link_documenti_mov','', '${pageContext.request.contextPath}/images/',false);"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.documenti_movimenti" />">
							<label for="id_link_documenti_mov"> <fmt:message key="label.documenti_movimenti" /></label>
						</a>
						
					</td>
				</tr>
				<c:forEach items="${documentiistanza.documentiHelper.documentiAltriMovimentiList}" var="current" varStatus="a">
					<tr id="id_documenti_mov_table" style="<%=displayDocumentiMov%>;">
						<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
						<td width="75%" colspan="4">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="4" ><fmt:message key="label.documento" /> </td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
				            </tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b"> 							
								<tr class= "<%=(j%2)==0?"odd":"even"%>">
								<td width="50%">${var.descrizione}</td>
								<td colspan="2" width="35%">${var.nomeFile}</td>
								<%-- 
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
			   						</jsp:include>
   							    </td>
   							     --%>
   							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>	
								<td>
									<spring:bind path="documentiHelper.documentiAltriMovimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
										<input id="altrima_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if> />
									</spring:bind>
								</td>	
								</tr>
							<%j++; %>	
							</c:forEach>
							
						</table>
						</td>
					</tr>
				</c:forEach>	
        	</c:if>
		    
		    <!-- GESTIONE DELLA  VISUALIZZAZIONE DELLE PROCURE  -->
		     <%
					
					String displayDocumentiprocure= "display:none;";
					String styleDocumentiprocure = "sezioneDatiPiu";
					
			%>
	        <c:if test="${not empty documentiistanza.documentiHelper.istanzeprocureList}">
	        <%int k=1;%>
				<tr class="header" >
					<td colspan="5">
					      <a class="<%=styleDocumentiprocure%>" id="id_link_documenti_procure" href="javascript:showHidePanelBase('id_documenti_procure_table', 'id_link_documenti_procure','', '${pageContext.request.contextPath}/images/',false);"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.documenti_procure" />">
							<label for="id_link_documenti_procure"> <fmt:message key="label.documenti_procure" /></label>
						</a>
						
					</td>
				</tr>
				<c:forEach items="${documentiistanza.documentiHelper.istanzeprocureList}" var="current" varStatus="a">
					<tr id="id_documenti_procure_table" style="<%=displayDocumentiprocure%>;">
						<td width="25%"  style="vertical-align: top;" ><b>${current.chiave}</b></td>
						<td width="75%" colspan="4">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="4"><fmt:message key="label.documento" /></td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
								
				            </tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b">	
								<tr class= "<%=(k%2)==0?"odd":"even"%>">
									<td width="50%">Documento della procura di ${var.anagrafeProcuratore.descrizioneRichiedente}</td>
									<td colspan="3" width="35%">${var.nomeFile}</td>
									<%-- 
									<td colspan="2" width="13%">
										<jsp:include page="../includes/visualizzaOggetto.jsp" >
				       						<jsp:param name="idElemento" value="docProc${var.id.codice }" />
				       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
				   						</jsp:include>
	   							    </td>
	   							    --%>
									<td>
										<spring:bind path="documentiHelper.istanzeprocureList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
										<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
										<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
											<input id="dp_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
										</spring:bind>
									</td>	
									
								</tr>
								<%k++; %>								
							</c:forEach>
						</table>
						</td>
					</tr>
				</c:forEach>	
		  </c:if>
		 
			 
			<!-- SEZIONE DOCUMENTI DELLE ANAGRAFICHE -->
			 <%
					
					String displayDocumentiAnagrafe= "display:none;";
					String styleDocumentiAnagrafe = "sezioneDatiPiu";
					
			%>
			<c:if test="${not empty documentiistanza.documentiHelper.documentiAnagrafeList}">
			<%int p=1;%>
			<tr class="header" >
					<td colspan="5">
					 <a class="<%=styleDocumentiAnagrafe%>" id="id_link_documenti_anagrafe" href="javascript:showHidePanelBase('id_documenti_anagrafe_table', 'id_link_documenti_anagrafe','', '${pageContext.request.contextPath}/images/',false);"	title="<fmt:message key="label.mostra_nasconde_sezione" /><fmt:message key="label.allegati_anagrafiche" />">
							<label for="id_link_documenti_anagrafe"> <fmt:message key="label.allegati_anagrafiche" /></label>
						</a>
						
					</td>
			</tr>
			<c:forEach items="${documentiistanza.documentiHelper.documentiAnagrafeList}" var="current" varStatus="a">
			<tr id="id_documenti_anagrafe_table" style="<%=displayDocumentiAnagrafe%>;">
				<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
				<td width="75%" colspan="4">
				<table width="100%">
					<tr class="header" >
					    <td width="98%" colspan="4"><fmt:message key="label.documento" /></td>
						<td width="2%" ><fmt:message key="label.seleziona" /></td>
		            </tr>
					<c:forEach items="${current.valore}" var="var" varStatus="b">	
						<tr class= "<%=(p%2)==0?"odd":"even"%>">
							<td width="50%">${var.documento}</td>
							<td colspan="2" width="35%">${var.nomeFile}</td>
							<%--
							<td width="13%">							
								<jsp:include page="../includes/visualizzaOggetto.jsp" >
		       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
		       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
		   						</jsp:include>
  							</td>
  							--%>
  							<td>
  								&nbsp;
  							</td>
						    <%-- 
						    <td>
  							    <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
		       						<jsp:param name="controllook" value="${var.controllook}" />
		   						</jsp:include>
   							</td>
   							--%>	
							<td>
								<spring:bind path="documentiHelper.documentiAnagrafeList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
								<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
						        <input id="docanagr_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if>" />
							    </spring:bind>
							</td>	
						</tr>
						<%p++; %>								
					</c:forEach>
				</table>
				</td>
			</tr>
		</c:forEach>
		</c:if>


				<!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DELLE CDS  -->
		        <%
					
					String displayDocumentiCds= "display:none;";
					String styleDocumentiCds = "sezioneDatiPiu";
					
				%>
		        <c:if test="${not empty documentiistanza.documentiHelper.cdsattiList}">
		        <%int k=1;%>
					<tr class="header" >
						<td colspan="5">
							<a class="<%=styleDocumentiCds%>" id="id_link_documenti_cds" href="javascript:showHidePanelBase('id_documenti_cds_table', 'id_link_documenti_cds','', '${pageContext.request.contextPath}/images/',false);"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.allegati_istanza" />">
								<label for="id_link_documenti_cds"> <fmt:message key="label.verbale_cds" /></label>
							</a>
						</td>
					</tr>
					<c:forEach items="${documentiistanza.documentiHelper.cdsattiList}" var="current" varStatus="a">
						<tr id="id_documenti_cds_table" style="<%=displayDocumentiCds%>;">
							<td width="25%"  style="vertical-align: top;" ><b>CDS codice: ${current.chiave}</b></td>
							<td width="75%" colspan="4">
							<table width="100%">
								<tr class="header" >
								    <td width="98%" colspan="4"><fmt:message key="label.documento" /></td>
									<td width="2%" ><fmt:message key="label.seleziona" /></td>									
					            </tr>
								<c:forEach items="${current.valore}" var="var" varStatus="b">	
									<tr class= "<%=(k%2)==0?"odd":"even"%>">
										<td width="50%">
										${var.data}:
    									${var.ora} - 
										${var.note}
										
										</td>
										<td colspan="3" width="35%">${var.nomefile}</td>
										
										<td>
											<spring:bind path="documentiHelper.cdsattiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
											<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
											<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
												<input id="cds_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
											</spring:bind>
										</td>	
										
									</tr>
									<%k++; %>								
								</c:forEach>
							</table>
							</td>
						</tr>
					</c:forEach>	
			 </c:if>

	      
		</table>
	</fieldset>
	</div>
	
	<div id="functions">
		<ul>
		    <script type="text/javascript">
		    function download(hrefFormAction, confirmMessage, objForm) {

		    	
		    	document.getElementById("bnt_download_id").style.display = "none";
		    	document.getElementById("fieldset_documenti_id").style.display = "none";
		    	document.getElementById("message_id").style.display = "";
		    	
		    	var queryString; 
		    	if (buttonSubmitted) {
		    		return;
		    	}
		    	if (checkConfirmMessage(confirmMessage)) {
		    		if (objForm) {
		    			//disableFunctions();
		    			objForm.action = hrefFormAction;
		    			objForm.submit();
		    			return;
		    		} else {
		    			//disableFunctions();
		    			document.forms[0].action = hrefFormAction;
		    			document.forms[0].submit();
		    			return
		    		}
		    	}
		    	
		    }
	
		    
		    
	
	
		</script>
			<li><a id="bnt_download_id" href="javascript:download('../documentiistanza/ajaxDownloadDocumentiZip.htm?codiceIstanza=${codiceIstanza}','',document.inviodati)" ><fmt:message key="button.download" /></a></li>
			<li><a href="javascript:doHref('list.htm?codiceIstanza=${codiceIstanza}','');"><fmt:message key="button.back" /></a></li>
	</spring-form:form>
	</body>
</html>