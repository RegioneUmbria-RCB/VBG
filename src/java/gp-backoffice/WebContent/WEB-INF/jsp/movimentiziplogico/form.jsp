<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="java.net.URLEncoder" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${movimentiZipLogicoCommand.displayMode eq movimentiZipLogicoCommand.displayConstants.NEW}">
			<fmt:message key="documentiistanza.label.lista_documenti_istanza.title" />
		</c:if>
		<c:if test="${movimentiZipLogicoCommand.displayMode eq movimentiZipLogicoCommand.displayConstants.VIEW}">
			<fmt:message key="label.movimenti_zip_logico.visualizza_zip_logico" />
		</c:if>
			
	</title>
</head>
<body>
	
	<c:if test="${movimentiZipLogicoCommand.displayMode eq movimentiZipLogicoCommand.displayConstants.NEW}">
		<span class="titoloPagina">
			<fmt:message key="documentiistanza.label.lista_documenti_istanza.title" />
		</span>
	</c:if>
	<c:if test="${movimentiZipLogicoCommand.displayMode eq movimentiZipLogicoCommand.displayConstants.VIEW}">
		<span class="titoloPagina">
			<fmt:message key="label.movimenti_zip_logico.visualizza_zip_logico" />
		</span>
		<c:choose>
				<c:when test="${ not empty movimentiZipLogicoCommand.testata.guidCollegato }">
					<div style="width: 90%;margin-top: 10px; font-weight: bold;border: 2px dotted maroon; padding-left: 10px; padding-top: 4px; padding-bottom: 4px;">	
						<fmt:message key="label.movimenti_zip_logico.visualizza_zip_logico_collegato_msg" />
					</div>
				</c:when>
		</c:choose>					    							
	</c:if>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>

<div id="subcontent">
		
	<c:if test="${movimentiZipLogicoCommand.displayMode eq movimentiZipLogicoCommand.displayConstants.NEW}">
	
		<spring-form:form commandName="movimentiZipLogicoCommand" name="inviodati">
		
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="movimentiZipLogicoCommand" />
		    </jsp:include>
		    
		    <script type="text/javascript">
		    	
			    function selezionaAndDeselezionaTutti() {
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
		    		    
		    <fieldset id="fieldset_documenti_id">
		    
			    <legend><fmt:message key="label.sezione_documenti"/></legend>
			    	
			    <div>
					<div style="float: right;">
						<input id="a_check_allegati" type="checkbox" onclick="selezionaAndDeselezionaTutti()"/>
						<label id="message_label"><fmt:message key="label.seleziona_deseleziona_tutti"/></label>
					</div>		    
			    </div>
			    <div class="jmesa">
			    	
			    	<table border="0" cellpadding="2" cellspacing="0" class="table">
			    		
			    		<!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DELL' ISTANZA  -->
			    		<%
			    			String displayDocumentiIstanza = "";
			    			String styleDocumentiIstanza = "sezioneDatiMeno";
			    		%>
			    		<c:if test="${not empty  movimentiZipLogicoCommand.documentiHelper.documentiIstanzaList}">
			    			<% int k = 1; %>
			    			<tr class="header">
			    				<td colspan="5">
				    				<a class="<%=styleDocumentiIstanza%>" 
				    				   id="id_link_documenti_istanza" 
				    				   href="javascript:showHidePanelBase('id_documenti_istanza_table', 'id_link_documenti_istanza','', '${pageContext.request.contextPath}/images/',false);"	
				    				   title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.allegati_istanza" />">
				    					
										<label for="id_link_documenti_istanza"> <fmt:message key="label.allegati_istanza" /></label>
										
									</a>
								</td>
			    			</tr>
			    			<c:forEach items="${ movimentiZipLogicoCommand.documentiHelper.documentiIstanzaList }" var="current" varStatus="a">
			    				<tr id="id_documenti_istanza_table" style="<%= displayDocumentiIstanza %>;">
			    					<td width="25%" style="vertical-align: top;"><b>${current.chiave}</b></td>
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
			    			String displayDocumentiEndo = "";
			    			String styleDocumentiEndo = "sezioneDatiMeno";
			    		%>
			    		<c:if test="${not empty movimentiZipLogicoCommand.documentiHelper.documentiEndoprocedimentiList}">
			    			<%int b=1;%>
			    			<tr class="header">
			    				<td colspan="5">
								    <a class="<%=styleDocumentiEndo%>" 
								       id="id_link_documenti_endo" 
								       href="javascript:showHidePanelBase('id_documenti_endo_table', 'id_link_documenti_endo','', '${pageContext.request.contextPath}/images/',false);"	
								       title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.allegati_endoprocedimenti" />">
											<label for="id_link_documenti_endo"> <fmt:message key="label.allegati_endoprocedimenti" /></label>
									</a>
								</td>
			    			</tr>
			    			<c:forEach items="${movimentiZipLogicoCommand.documentiHelper.documentiEndoprocedimentiList}" var="current" varStatus="a">
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
			    		
			    		<!-- SEZIONE DOCUMENTI DEL ALTRI MOVIMENTI -->
					    <%
							String displayDocumentiMov= "";
							String styleDocumentiMov = "sezioneDatiMeno";
						%>
			    		<c:if test="${not empty movimentiZipLogicoCommand.documentiHelper.documentiAltriMovimentiList}">
			    			<%int j=1;%>
			    			<tr class="header" >
								<td colspan="5">
								     <a class="<%=styleDocumentiMov%>" 
								        id="id_link_documenti_mov" 
								        href="javascript:showHidePanelBase('id_documenti_mov_table', 'id_link_documenti_mov','', '${pageContext.request.contextPath}/images/',false);"	
								        title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.documenti_movimenti" />">
										<label for="id_link_documenti_mov"> <fmt:message key="label.documenti_movimenti" /></label>
									</a>
								</td>
							</tr>
							<c:forEach items="${movimentiZipLogicoCommand.documentiHelper.documentiAltriMovimentiList}" var="current" varStatus="a">
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
							String displayDocumentiprocure= "";
							String styleDocumentiprocure = "sezioneDatiMeno";		
						%>
			    		<c:if test="${not empty movimentiZipLogicoCommand.documentiHelper.istanzeprocureList}">
			    			<%int k=1;%>
			    			<tr class="header">
			    				<td colspan="5">
								    <a class="<%=styleDocumentiprocure%>" 
								       id="id_link_documenti_procure" 
								       href="javascript:showHidePanelBase('id_documenti_procure_table', 'id_link_documenti_procure','', '${pageContext.request.contextPath}/images/',false);"	
								       title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.documenti_procure" />">
										<label for="id_link_documenti_procure"> <fmt:message key="label.documenti_procure" /></label>
									</a>
								</td>
			    			</tr>
			    			<c:forEach items="${movimentiZipLogicoCommand.documentiHelper.istanzeprocureList}" var="current" varStatus="a">
			    				<tr id="id_documenti_procure_table" style="<%=displayDocumentiprocure%>;">
			    					<td width="25%"  style="vertical-align: top;" ><b>${current.chiave}</b></td>
									<td width="75%" colspan="4">
										<table width="100%">
											<tr class="header">
												<td width="98%" colspan="4"><fmt:message key="label.documento" /></td>
												<td width="2%" ><fmt:message key="label.seleziona" /></td>
											</tr>
											<c:forEach items="${current.valore}" var="var" varStatus="b">
												<tr class= "<%=(k%2)==0?"odd":"even"%>">
													<td width="50%">Documento della procura di ${var.anagrafeProcuratore.descrizioneRichiedente}</td>
													<td colspan="2" width="35%">${var.nomeFile}</td>
					   							    <td>
					   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
								       						<jsp:param name="controllook" value="${var.controllook}" />
								   						</jsp:include>
					   							    </td>
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
							String displayDocumentiAnagrafe= "";
							String styleDocumentiAnagrafe = "sezioneDatiMeno";	
						%>
						<c:if test="${not empty movimentiZipLogicoCommand.documentiHelper.documentiAnagrafeList}">
							<%int p=1;%>
							<tr class="header">
								<td colspan="5">
									<a class="<%=styleDocumentiAnagrafe%>" 
									   id="id_link_documenti_anagrafe" 
									   href="javascript:showHidePanelBase('id_documenti_anagrafe_table', 'id_link_documenti_anagrafe','', '${pageContext.request.contextPath}/images/',false);"	
									   title="<fmt:message key="label.mostra_nasconde_sezione" /><fmt:message key="label.allegati_anagrafiche" />">
										<label for="id_link_documenti_anagrafe"> <fmt:message key="label.allegati_anagrafiche" /></label>
									</a>
								</td>
							</tr>
							<c:forEach items="${movimentiZipLogicoCommand.documentiHelper.documentiAnagrafeList}" var="current" varStatus="a">
								<tr id="id_documenti_anagrafe_table" style="<%=displayDocumentiAnagrafe%>;">
									<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
									<td width="75%" colspan="4">
										<table width="100%">
											<tr class="header">
												<td width="98%" colspan="4"><fmt:message key="label.documento" /></td>
												<td width="2%" ><fmt:message key="label.seleziona" /></td>
											</tr>
											<c:forEach items="${current.valore}" var="var" varStatus="b">
												<tr class="<%=(p%2)==0?"odd":"even"%>">
													<td width="50%">${var.documento}</td>
													<td colspan="3" width="35%">${var.nomeFile}</td>
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
							String displayDocumentiCds= "";
							String styleDocumentiCds = "sezioneDatiMeno";
						%>
						<c:if test="${not empty movimentiZipLogicoCommand.documentiHelper.cdsattiList}">
							<%int k=1;%>
							<tr class="header" >
								<td colspan="5">
									<a class="<%=styleDocumentiCds%>" 
									   id="id_link_documenti_cds" 
									   href="javascript:showHidePanelBase('id_documenti_cds_table', 'id_link_documenti_cds','', '${pageContext.request.contextPath}/images/',false);"	
									   title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.allegati_istanza" />">
										<label for="id_link_documenti_cds"> <fmt:message key="label.verbale_cds" /></label>
									</a>
								</td>
							</tr>
							<c:forEach items="${movimentiZipLogicoCommand.documentiHelper.cdsattiList}" var="current" varStatus="a">
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
								            		<td width="50%"> ${var.data}: ${var.ora} - ${var.note} </td>
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
			    </div>
		    </fieldset>
		    
		</spring-form:form>
		
	</c:if>
	
	<!-- >>>>>>>>> SEZIONE VIEW <<<<<<<<< -->
	
	<c:if test="${movimentiZipLogicoCommand.displayMode eq movimentiZipLogicoCommand.displayConstants.VIEW}">
	
		<spring-form:form commandName="movimentiZipLogicoCommand" name="inviodatiViewForm">
		
			<!-- FIX: RENDERE IL GLOBAL MESSAGES UNICO SIA PER LO STATO VIEW CHE PER QUELLO NEW -->
			
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="movimentiZipLogicoCommand" />
		    </jsp:include>
		    

		    
		    <script type="text/javascript">
		    	
			    function selezionaAndDeselezionaTuttiView() {
					var checkIt = jQuery('#a_check_allegati_view').prop('checked');
					jQuery("input[id^='view_di_']").prop('checked', checkIt);
					jQuery("input[id^='view_ma_']").not(':disabled').prop('checked', checkIt);
					jQuery("input[id^='view_altrima_']").not(':disabled').prop('checked', checkIt);
					jQuery("input[id^='view_ia_']").prop('checked', checkIt);
					jQuery("input[id^='view_docanagr_']").prop('checked', checkIt);
					jQuery("input[id^='view_dp_']").prop('checked', checkIt);
					jQuery("input[id^='view_cds_']").prop('checked', checkIt);
					if(checkIt){
						setPanelVisible('id_documenti_mov_table_view', 'id_link_documenti_mov_view',true);
						setPanelVisible('id_documenti_endo_table_view', 'id_link_endo_anagrafe_view',true);
						setPanelVisible('id_documenti_procure_table_view', 'id_link_procure_anagrafe_view',true);
						setPanelVisible('id_documenti_anagrafe_table_view', 'id_link_documenti_anagrafe_view',true);
						setPanelVisible('id_documenti_cds_table_view', 'id_link_documenti_cds_view',true);
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
					<tr>
						<td style="color: green;">Archivio Zip Logico Creato</td>
					</tr>
				</table> 
		    
		    <fieldset id="view_fieldset_documenti_id">
		    
		    	<legend><fmt:message key="label.movimenti_zip_logico.sezione_documenti"/> <div style="display: none" class="zip_logico_link"></div></legend>		    	
		    		<div>
						<div >
							<div style="display: none">${movimentiZipLogicoCommand.testata.guid }</div>
							
							<c:if test="${not empty movimentiZipLogicoCommand.testata.codiceOggettoDocumentoAllegato}">
							<div><fmt:message key="label.movimenti_zip_logico.oggetto_doc_allegato"/> 
								<jsp:include page="../includes/visualizzaOggetto.jsp" >
		       						<jsp:param name="idElemento" value="docallegato${movimentiZipLogicoCommand.testata.codiceOggettoDocumentoAllegato }" />
		       						<jsp:param name="fileId" value="${movimentiZipLogicoCommand.testata.codiceOggettoDocumentoAllegato }" />
		       						<jsp:param name="mostralabel" value="true" />	
		       						<jsp:param name="mostraNomeFile" value="false" />
		       						<jsp:param name="readonly" value="true" />
		   						</jsp:include>			
		   						 <c:if test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato }">
									<a href="javascript:disassocia()">
                                         <i class="fa fa-trash" aria-hidden="true"></i> 
                                         <fmt:message key="label.movimenti_zip_logico.oggetto_doc_allegato.elimina"/>
                                    </a>
		   						 </c:if>					
							</div>		    
						</c:if>
				    </div>
		    	
		    	 <c:if test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato }">
			    	<div>
						<div style="float: right;">
							<input id="a_check_allegati_view" type="checkbox" onclick="selezionaAndDeselezionaTuttiView()"/>
							<label id="message_label"><fmt:message key="label.seleziona_deseleziona_tutti"/></label>
						</div>		    
				    </div>
		     	</c:if>
		     	
			    <div class="jmesa">
			    	<table border="0" cellpadding="2" cellspacing="0" class="table">
			    	
			    		<!-- GESTIONE DELLA VIEW VISUALIZZAZIONE DEI DOCUMENTI DELL' ISTANZA PRESENTI NELLO ZIP LOGICO  -->
			    		
			    		<%
			    			String displayDocumentiIstanzaZipLogico = "";
			    			String styleDocumentiIstanzaZipLogico = "sezioneDatiMeno";
				    	%>
				    	<c:if test="${not empty  movimentiZipLogicoCommand.documentiHelper.documentiIstanzaList}">
				    		<% int k = 1; %>
				    		<tr class="header">
				    			<td colspan="5">
				    				<a class="<%=styleDocumentiIstanzaZipLogico%>" 
				    				   id="id_link_documenti_istanza_view" 
				    				   href="javascript:showHidePanelBase('id_documenti_istanza_table_view', 'id_link_documenti_istanza_view','', '${pageContext.request.contextPath}/images/',false);"	
				    				   title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.allegati_istanza" />">
				    					
										<label for="id_link_documenti_istanza_view"> <fmt:message key="label.allegati_istanza" /></label>
										
									</a>
				    			</td>
				    		</tr>
				    		<c:forEach items="${ movimentiZipLogicoCommand.documentiHelper.documentiIstanzaList }" var="current" varStatus="a">
				    			<tr id="id_documenti_istanza_table_view" style="<%= displayDocumentiIstanzaZipLogico %>;">
				    				<td width="25%" style="vertical-align: top;"><b>${current.chiave}</b></td>
				    				<td width="75%" colspan="4">
				    					<table width="100%">
				    						<tr class="header" >
					    						<c:choose>
					    							<c:when test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato}">
					    								<td widt="98%" colspan="5"><fmt:message key="label.documento" /></td>
					    								<td width="2%" ><fmt:message key="label.seleziona" /></td>
					    							</c:when>
					    							<c:otherwise>
					    								<td widt="100%" colspan="5"><fmt:message key="label.documento" /></td>
					    							</c:otherwise>
					    						</c:choose>
				    						</tr>
				    						 <c:forEach items="${current.valore}" var="var" varStatus="b">
				    						 	<tr class= "<%=(k%2)==0?"odd":"even"%>">
				    						 		<td width="50%">${var.documento}</td>
								            		<td colspan="2" width="30%">${var.nomeFile}</td>
								            		<td width="20%">
														<jsp:include page="../includes/visualizzaOggetto.jsp" >
								       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
								       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
								       						<jsp:param name="mostralabel" value="true" />													       						
								   						</jsp:include>
					   							    </td>
					   							   	<td>
					   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
								       						<jsp:param name="controllook" value="${var.controllook}" />
								   						</jsp:include>
			   							    		</td>
			   							    		<c:if test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato}">
			   							    			<td>
															<spring:bind path="documentiHelper.documentiIstanzaList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
															<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
															<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																<input id="view_di_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
															</spring:bind>
														</td>
			   							    		</c:if>
				    						 	</tr>
				    						 	<%k++; %>
				    						 </c:forEach>
				    					</table>
				    				</td>
				    			</tr>
				    		</c:forEach>
				    	</c:if>
				    	
				    	<!-- GESTIONE DEI DOCUMENTI DEGLI ENDO PROCEDIMENTI NELLO ZIP LOGICO -->
				    	
				    	<%
			    			String displayDocumentiEndoZipLogico = "";
			    			String styleDocumentiEndoZipLogico = "sezioneDatiMeno";
			    		%>
			    		
			    		<c:if test="${not empty movimentiZipLogicoCommand.documentiHelper.documentiEndoprocedimentiList}">
			    			<%int b=1;%>
			    			<tr class="header">
			    				<td colspan="5">
								    <a class="<%=styleDocumentiEndoZipLogico%>" 
								       id="id_link_documenti_endo_view" 
								       href="javascript:showHidePanelBase('id_documenti_endo_table_view', 'id_link_documenti_endo_view','', '${pageContext.request.contextPath}/images/',false);"	
								       title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.allegati_endoprocedimenti" />">
											<label for="id_link_documenti_endo_view"> <fmt:message key="label.allegati_endoprocedimenti" /></label>
									</a>
								</td>
			    			</tr>
			    			<c:forEach items="${movimentiZipLogicoCommand.documentiHelper.documentiEndoprocedimentiList}" var="current" varStatus="a">
			    				<tr id="id_documenti_endo_table_view" style="<%=displayDocumentiEndoZipLogico%>;">
			    					<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
			    					<td width="75%" colspan="4">
			    						<table width="100%">
			    							<tr class="header" >
				    							<c:choose>
					    							<c:when test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato}">
					    								<td widt="98%" colspan="5"><fmt:message key="label.documento" /></td>
					    								<td width="2%" ><fmt:message key="label.seleziona" /></td>
					    							</c:when>
					    							<c:otherwise>
					    								<td widt="100%" colspan="5"><fmt:message key="label.documento" /></td>
					    							</c:otherwise>
					    						</c:choose>
				    						</tr>
				    						<c:forEach items="${current.valore}" var="var" varStatus="b">
				    							<tr class= "<%=(b%2)==0?"odd":"even"%>">
				    								<td width="50%">${var.allegatoextra}</td>
								            		<td colspan="2" width="30%">${var.nomeFile}</td>
								            		<td width="20%">
														<jsp:include page="../includes/visualizzaOggetto.jsp" >
								       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
								       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
								       						<jsp:param name="mostralabel" value="true" />													       						
								   						</jsp:include>
					   							    </td>
					   							   	<td>
					   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
								       						<jsp:param name="controllook" value="${var.controllook}" />
								   						</jsp:include>
					   							    </td>
					   							    <c:if test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato}">
					    								<td>
															<spring:bind path="documentiHelper.documentiEndoprocedimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
																<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
																<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																<input id="view_ia_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
															</spring:bind>
														</td>
					    							</c:if>
				    							</tr>			    										
				    							<%b++; %>
				    						</c:forEach>
			    						</table>
			    					</td>
			    				</tr>
			    			</c:forEach>
			    		</c:if>
			    		
			    		<!-- SEZIONE DOCUMENTI DEL ALTRI MOVIMENTI DELLO ZIP LOGICO -->
					    <%
							String displayDocumentiMovZipLogico= "";
							String styleDocumentiMovZipLogico = "sezioneDatiMeno";
						%>
			    		<c:if test="${not empty movimentiZipLogicoCommand.documentiHelper.documentiAltriMovimentiList}">
			    			<%int j=1;%>
			    			<tr class="header" >
			    				<td colspan="5">
								     <a class="<%=styleDocumentiMovZipLogico%>" 
								        id="id_link_documenti_mov_view" 
								        href="javascript:showHidePanelBase('id_documenti_mov_table_view', 'id_link_documenti_mov_view','', '${pageContext.request.contextPath}/images/',false);"	
								        title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.documenti_movimenti" />">
										<label for="id_link_documenti_mov_view"> <fmt:message key="label.documenti_movimenti" /></label>
									</a>
								</td>
			    			</tr>
			    			<c:forEach items="${movimentiZipLogicoCommand.documentiHelper.documentiAltriMovimentiList}" var="current" varStatus="a">
			    				<tr id="id_documenti_mov_table_view" style="<%=displayDocumentiMovZipLogico%>;">
			    					<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
			    					<td width="75%" colspan="3">
			    						<table width="100%">
			    							<tr class="header" >
				    							<c:choose>
					    							<c:when test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato}">
					    								<td widt="98%" colspan="5"><fmt:message key="label.documento" /></td>
					    								<td width="2%" ><fmt:message key="label.seleziona" /></td>
					    							</c:when>
					    							<c:otherwise>
					    								<td widt="100%" colspan="5"><fmt:message key="label.documento" /></td>
					    							</c:otherwise>
					    						</c:choose>
				    						</tr>
			    							<c:forEach items="${current.valore}" var="var" varStatus="b">
			    								<tr class= "<%=(j%2)==0?"odd":"even"%>">
			    									<td width="50%">${var.descrizione}</td>
			    									<td colspan="2" width="30%">${var.nomeFile}</td>
			    									<td width="20%">
														<jsp:include page="../includes/visualizzaOggetto.jsp" >
								       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
								       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
								       						<jsp:param name="mostralabel" value="true" />													       						
								   						</jsp:include>
					   							    </td>
			    									<td>
					   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
								       						<jsp:param name="controllook" value="${var.controllook}" />
								   						</jsp:include>
					   							    </td>
					   							    <c:if test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato }">
						   							    <td>
															<spring:bind path="documentiHelper.documentiAltriMovimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
															<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
															<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																<input id="view_altrima_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if> />
															</spring:bind>
														</td>
													</c:if>
			    								</tr>
			    								<%j++; %>
			    							</c:forEach>
			    						</table>
			    					</td>
			    				</tr>
			    			</c:forEach>
			    		</c:if>
			    		
			    		<!-- GESTIONE DELLA  VISUALIZZAZIONE DELLE PROCURE ZIP LOGICO -->
			    		<%
							String displayDocumentiprocureZipLogico = "";
							String styleDocumentiprocureZipLogico = "sezioneDatiMeno";		
						%>
			    		<c:if test="${not empty movimentiZipLogicoCommand.documentiHelper.istanzeprocureList}">
			    			<%int k=1;%>
			    			<tr class="header">
			    				<td colspan="5">
				    				<a class="<%=styleDocumentiprocureZipLogico%>" 
									       id="id_link_documenti_procure_view" 
									       href="javascript:showHidePanelBase('id_documenti_procure_table_view', 'id_link_documenti_procure_view','', '${pageContext.request.contextPath}/images/',false);"	
									       title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.documenti_procure" />">
										<label for="id_link_documenti_procure_view"> <fmt:message key="label.documenti_procure" /></label>
									</a>
			    				</td>
			    			</tr>
			    			<c:forEach items="${movimentiZipLogicoCommand.documentiHelper.istanzeprocureList}" var="current" varStatus="a">
			    				<tr id="id_documenti_procure_table_view" style="<%=displayDocumentiprocureZipLogico%>;">
			    					<td width="25%"  style="vertical-align: top;" ><b>${current.chiave}</b></td>
			    					<td width="75%" colspan="4">
			    						<table width="100%">
			    							<tr class="header" >
				    							<c:choose>
					    							<c:when test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato }">
					    								<td widt="98%" colspan="5"><fmt:message key="label.documento" /></td>
					    								<td width="2%" ><fmt:message key="label.seleziona" /></td>
					    							</c:when>
					    							<c:otherwise>
					    								<td widt="100%" colspan="5"><fmt:message key="label.documento" /></td>
					    							</c:otherwise>
					    						</c:choose>
				    						</tr>
			    							<c:forEach items="${current.valore}" var="var" varStatus="b">
			    								<tr class= "<%=(k%2)==0?"odd":"even"%>">
			    									<td width="50%">Documento della procura di ${var.anagrafeProcuratore.descrizioneRichiedente}</td>
			    									<td colspan="2" width="30%">${var.nomeFile}</td>
			    									<td width="20%">
														<jsp:include page="../includes/visualizzaOggetto.jsp" >
								       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
								       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
								       						<jsp:param name="mostralabel" value="true" />													       						
								   						</jsp:include>
					   							    </td>
					   							    <td>
					   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
								       						<jsp:param name="controllook" value="${var.controllook}" />
								   						</jsp:include>
					   							    </td>
			    									<c:if test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato}">
			    										<td>
															<spring:bind path="documentiHelper.istanzeprocureList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
															<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
															<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																<input id="view_dp_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
															</spring:bind>
														</td>
			    									</c:if>
			    								</tr>
			    							</c:forEach>
			    						</table>
			    					</td>
			    				</tr>
			    				<%k++; %>
			    			</c:forEach>
			    		</c:if>
			    		
			    		<!-- SEZIONE DOCUMENTI DELLE ANAGRAFICHE ZIP LOGICO -->
			    		
			    		<%
							String displayDocumentiAnagrafeZipLogico="";
							String styleDocumentiAnagrafeZipLogico="sezioneDatiMeno";	
						%>
				    	<c:if test="${not empty movimentiZipLogicoCommand.documentiHelper.documentiAnagrafeList}">
				    		<%int p=1;%>
				    		<tr class="header">
								<td colspan="5">
									<a class="<%=styleDocumentiAnagrafeZipLogico%>" 
									   id="id_link_documenti_anagrafe_view" 
									   href="javascript:showHidePanelBase('id_documenti_anagrafe_table_view', 'id_link_documenti_anagrafe_view','', '${pageContext.request.contextPath}/images/',false);"	
									   title="<fmt:message key="label.mostra_nasconde_sezione" /><fmt:message key="label.allegati_anagrafiche" />">
										<label for="id_link_documenti_anagrafe_view"> <fmt:message key="label.allegati_anagrafiche" /></label>
									</a>
								</td>
							</tr>
							<c:forEach items="${movimentiZipLogicoCommand.documentiHelper.documentiAnagrafeList}" var="current" varStatus="a">
								<tr id="id_documenti_anagrafe_table_view" style="<%=displayDocumentiAnagrafeZipLogico%>;">
									<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
									<td width="75%" colspan="4">
										<table width="100%">
											<tr class="header" >
				    							<c:choose>
					    							<c:when test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato}">
					    								<td widt="98%" colspan="5"><fmt:message key="label.documento" /></td>
					    								<td width="2%" ><fmt:message key="label.seleziona" /></td>
					    							</c:when>
					    							<c:otherwise>
					    								<td widt="100%" colspan="5"><fmt:message key="label.documento" /></td>
					    							</c:otherwise>
					    						</c:choose>
				    						</tr>
											<c:forEach items="${current.valore}" var="var" varStatus="b">
												<tr class="<%=(p%2)==0?"odd":"even"%>">
													<td width="50%">${var.documento}</td>
													<td colspan="2" width="30%">${var.nomeFile}</td>
													<td width="23%" colspan="2">
														<jsp:include page="../includes/visualizzaOggetto.jsp" >
								       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
								       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
								       						<jsp:param name="mostralabel" value="true" />													       						
								   						</jsp:include>
					   							    </td>
												    <%-- <td>
						  							    <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
								       						<jsp:param name="controllook" value="${var.controllook}" />
								   						</jsp:include>
						   							</td> --%>
						   							<c:if test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato}">
							   							<td>
															<spring:bind path="documentiHelper.documentiAnagrafeList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
															<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
													        <input id="view_docanagr_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if>" />
														    </spring:bind>
														</td>
						   							</c:if>
												</tr>
												<%p++; %>
											</c:forEach>
										</table>
									</td>
								</tr>
							</c:forEach>
				    	</c:if>
				    	
				    	<!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DELLE CDS ZIP LOGICO -->
						<%
							String displayDocumentiCdsZipLogico = "";
							String styleDocumentiCdsZipLogico = "sezioneDatiMeno";
						%>
						<c:if test="${not empty movimentiZipLogicoCommand.documentiHelper.cdsattiList}">
							<%int k=1;%>
							<tr class="header" >
								<td colspan="5">
									<a class="<%=styleDocumentiCdsZipLogico%>" 
									   id="id_link_documenti_cds_view" 
									   href="javascript:showHidePanelBase('id_documenti_cds_table_view', 'id_link_documenti_cds_view','', '${pageContext.request.contextPath}/images/',false);"	
									   title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.allegati_istanza" />">
										<label for="id_link_documenti_cds_view"> <fmt:message key="label.verbale_cds" /></label>
									</a>
								</td>
							</tr>
							<c:forEach items="${movimentiZipLogicoCommand.documentiHelper.cdsattiList}" var="current" varStatus="a">
								<tr id="id_documenti_cds_table_view" style="<%=displayDocumentiCdsZipLogico%>;">
									<td width="25%"  style="vertical-align: top;" ><b>CDS codice: ${current.chiave}</b></td>
									<td width="75%" colspan="4">
										<table width="100%">
											<tr class="header" >
				    							<c:choose>
					    							<c:when test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato}">
					    								<td widt="98%" colspan="5"><fmt:message key="label.documento" /></td>
					    								<td width="2%" ><fmt:message key="label.seleziona" /></td>
					    							</c:when>
					    							<c:otherwise>
					    								<td widt="100%" colspan="5"><fmt:message key="label.documento" /></td>
					    							</c:otherwise>
					    						</c:choose>
				    						</tr>
								            <c:forEach items="${current.valore}" var="var" varStatus="b">
								            	<tr class= "<%=(k%2)==0?"odd":"even"%>">
								            		<td width="50%"> ${var.fileverbale} </td>
								            		<td colspan="3" width="30%">${var.nomefile}</td>
								            		<td width="20%" colspan="2">
														<jsp:include page="../includes/visualizzaOggetto.jsp" >
								       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
								       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
								       						<jsp:param name="mostralabel" value="true" />													       						
								   						</jsp:include>
					   							    </td>
								            		<c:if test="${ isZipLogicoModificabile eq true && empty movimentiZipLogicoCommand.testata.guidCollegato}">
									            		<td>
															<spring:bind path="documentiHelper.cdsattiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
															<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
															<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																<input id="view_cds_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
															</spring:bind>
														</td>
								            		</c:if>
								            	</tr>
								            	<%k++; %>
								            </c:forEach>
										</table>
									</td>
								</tr>
							</c:forEach>
						</c:if> 
				    	
			    	</table>
			    </div>
		    </fieldset>
		</spring-form:form>
		
		
		
	</c:if>
	
	<div id="functions">
		<script type="text/javascript">
		</script>
		<ul>
			<c:if test="${movimentiZipLogicoCommand.displayMode eq movimentiZipLogicoCommand.displayConstants.NEW}">
				<li>
					<a href="javascript:inserisciConfirm('insertZipLogicoDiv')">
						<c:choose>
							<c:when test="${zipLogicoEsistente}">
								<fmt:message key="button.insert"/>	
							</c:when>
							<c:otherwise>
								<fmt:message key="label.movimenti_zip_logico.crea"/>
							</c:otherwise>
						</c:choose>
					</a>
					<div dojoType="dijit.Dialog" id="insertZipLogicoDiv" title="<fmt:message key="label.movimenti_zip_logico.conferma_inserimento" />">
						
						<input type="checkbox" id="inserimentoZipLogicochk_id" onClick="showHideDiv('doInsertDiv')"/>
						<label for="inserimentoZipLogicochk_id">
							<fmt:message key="label.movimenti_zip_logico.messaggio_inserimento_zip_logico_per_operatore">
								<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
								<fmt:param>${movimentiZipLogicoCommand.entity.movimenti} [${movimentiZipLogicoCommand.entity.movimenti.tipomovimento.id.tipomovimento}]</fmt:param>
							</fmt:message>
						</label>
						<br /><br />
						<div id="functions">
							<ul>
								<li style="display: none;" id="doInsertDiv"><a id="insert_btn_id" href="javascript:doSubmit('insert.htm', '', document.inviodati)"><fmt:message key="button.insert" /></a></li>
								<li><a href="javascript:void 0" onclick="dijit.byId('insertZipLogicoDiv').hide();"><fmt:message key="button.annulla" /></a></li>
							</ul>
						</div>
						<br class="clear" />
					</div>
					<script type="text/javascript">
						
						function inserisciConfirm(divId) {
							dijit.byId(divId).show();
						}
						
					</script>
					
				</li>
			</c:if>
			<c:if test="${movimentiZipLogicoCommand.displayMode eq movimentiZipLogicoCommand.displayConstants.VIEW}">
				
				<c:if test="${ isZipLogicoModificabile eq true}">				
					
					<c:if test="${ifThereAreDocumentsToAdd eq true && empty movimentiZipLogicoCommand.testata.guidCollegato }">
						<li><a href="javascript:doHref('list.htm?codicemovimento=${movimentiZipLogicoCommand.entity.movimenti.id.codice}','');"><fmt:message key="label.movimenti_zip_logico.aggiungi" /></a></li>
					</c:if>
					
					<c:if test="${empty movimentiZipLogicoCommand.testata.guidCollegato }">
					
					<li>
						<a href="javascript:cancellaConfirm('cancellaZipLogicoDialogDiv')"><fmt:message key="button.delete" /></a>
						<div dojoType="dijit.Dialog" id="cancellaZipLogicoDialogDiv" title="<fmt:message key="label.conferma_cancellazione" />"  style="display: none;">
							<input type="checkbox" id="cancellazioneziplogicochk_id" onclick="showHideDiv('doDeleteId')"/>
							<label for="cancellazioneziplogicochk_id">
								<fmt:message key="label.movimenti_zip_logico.messaggio_cancellazione_zip_logico_per_operatore">
									<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
									<fmt:param>${movimentiZipLogicoCommand.entity.movimenti} [${movimentiZipLogicoCommand.entity.movimenti.tipomovimento.id.tipomovimento}]</fmt:param>
								</fmt:message>
							</label>
							<br /><br />
							<div id="sezione_pwd_id" style="display: none;">
								<fmt:message key="label.password_richiesta" />
								<input type="text" id="password_cancellazione_id" name="password_cancellazione"  />
								<span id="message_pwd_error"></span>
							</div>
							<div id="functions">
								<ul>
									<%-- <li style="display: none;" id="doDeleteId"><a href="javascript:doSubmit('delete.htm','',document.inviodati)"><fmt:message key="button.delete" /></a></li> --%>
									<li style="display: none;" id="doDeleteId"><a id="delete_btn_id" href="#"><fmt:message key="button.delete" /></a></li>
									<li><a href="javascript:void 0" onclick="dijit.byId('cancellaZipLogicoDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
								</ul>
							</div>
							<br class="clear" />
						</div>
						<script type="text/javascript">

							jQuery(function () {
								jQuery("#delete_btn_id").on('click', deleteRecord);							
							});
							
							
							function disassocia() {

								doHref('<%=request.getContextPath()%>/movimentiziplogico/disassociaAllegato.htm','<fmt:message key="label.movimenti_zip_logico.oggetto_doc_allegato.elimina.confirm" />');
							}
							
							
							function cancellaConfirm(idDiv) {
								
								// check verticalizzazione gestione cancellazione
								var jhqrPr = jQuery.ajax({
									url: '<%=request.getContextPath()%>/movimenti/ajaxControllaAttivaVerticalizzazioneGestCancellazioni.htm',
									content: document.body,
									cache: false,
									data: { nomeParametro: "PWD_MOVIMENTO" },
									dataType: "html",
									success: function(data) {
										if (data == 'true') {
											$('sezione_pwd_id').style.display = '';
										}
									}, 
									error: function (jqXHR, textStatus, errorThrown) {
										alert(jqXHR.responseText);
									}
								});
								//
								dijit.byId(idDiv).show();
							}
							
							function deleteRecord(e) {
								
								e.preventDefault();
								
								// check verticalizzazione gestione cancellazione
								var pass = $('password_cancellazione_id').value;
								var jhqrPr = jQuery.ajax({
								  url: '<%=request.getContextPath()%>/movimenti/ajaxControllaPasswordCancellazioni.htm',
								  method: 'POST',
								  type: 'POST', // For jQuery < 1.9
								  context: document.body,
								  cache: false,
								  data: { nomeParametro: "PWD_MOVIMENTO",password:pass },
								  dataType: "html",
								  success: 
									 function(data) {
									 	if(data=='true' || data=='no_attivo') {
									    	doSubmit('delete.htm','',document.inviodatiViewForm);
									    } else{
									    	jQuery("#message_pwd_error").css("color", "#cd0a0a;");
									    	jQuery("#message_pwd_error").text(data);
									    	jQuery('#message_pwd_error').show();
									    	jQuery('#message_pwd_error').delay(2000).fadeOut();
									    	$('password_cancellazione_id').value='';
									    }
									},
									error: function(jqXHR, textStatus, errorThrown) {
										alert(jqXHR.responseText);														
									}
								});
							}
						
						</script>
					</li>
					
					</c:if>
					
					
					<%-- <li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodatiViewForm)"><fmt:message key="button.delete" /></a></li> --%>
				</c:if>
				
				<c:if test="${ isZipLogicoModificabile eq false }">
					<li><a id="btn_download_zip_id" href="javascript:download('ajaxDownloadDocumentiZipLogico.htm','',document.inviodatiViewForm)" ><fmt:message key="button.download" /></a></li>
					<script type="text/javascript">
					    function download(hrefFormAction, confirmMessage, objForm) {
			
					    	
					    	// document.getElementById("btn_download_zip_id").style.display = "none";
					    	// document.getElementById("view_fieldset_documenti_id").style.display = "none";
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
				</c:if>
				
			</c:if>
			<c:choose>
				<c:when test="${movimentiZipLogicoCommand.displayMode eq movimentiZipLogicoCommand.displayConstants.NEW}">
					<c:choose>
						<c:when test="${zipLogicoEsistente}">
							<li><a href="javascript:doHref('view.htm?codicemovimento=${movimentiZipLogicoCommand.entity.movimenti.id.codice}','');"><fmt:message key="button.back" /></a></li>
						</c:when>
						<c:otherwise>
							<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
				</c:otherwise>
			</c:choose>
			
		</ul>
	</div>
	
<script type="text/javascript">




async function popolaLinkZipLogico() {
	
			let linkRes = {};
		
			const url = "../movimentiziplogico/ajaxLinkZipLogico.htm";
			
			const formData = new FormData();
			formData.append('guid_zip_logico', '${movimentiZipLogicoCommand.testata.guid }');
			formData.append('codiceMovimento', ${movimentiZipLogicoCommand.entity.movimenti.id.codice});
			
			const response = await fetch(url, {
				method: 'POST',
				body: formData
			});
			
			if( await response.status == 200){						
				linkRes = await response.json();
			}
			if(linkRes && linkRes.url != ''){
				const links = document.querySelectorAll('.zip_logico_link');
				let contenuto = '>> <a href=\''+ linkRes.url+'\' target="_blank">'+linkRes.url+'</a>' ;
				if(linkRes.pin){
					contenuto = contenuto += "<br/>>> PIN: <b>" + linkRes.pin +"</b>";				
				}
				links.forEach((link) => {
					
					link.style.display = '';						
					
					link.innerHTML = contenuto; 
				});
				
			}

}


vbg.ready(() => {
	popolaLinkZipLogico() ;
	
});


</script>	
	
</div>
</body>
</html>