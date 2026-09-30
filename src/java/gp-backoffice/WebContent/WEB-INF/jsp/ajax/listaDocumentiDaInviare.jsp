<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<spring-form:form commandName="protocolloCommand" name="innerForm" id="innerForm_id">

	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="protocolloCommand" />
	</jsp:include>
	<div>
	
		<!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DEL MOVIMENTO  -->
			<div class="jmesa">
			<!-- La variabile mi permette di capire se ci sono documenti, se entra all'interno
			di una delle liste di documenti viene messa true -->
			<c:set value="false" var="isDocumentiPresenti" scope="page"></c:set>
			<table border="0" cellpadding="2" cellspacing="0" class="table">
			<c:if test="${not empty protocolloCommand.documentiHelper.documentiMovimentoList}">
			<c:set value="true" var="isDocumentiPresenti" scope="page"></c:set>
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
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  movimento.id.codice == null i documenti saranno tutti spuntati -->
									<c:if test="${codiceMov == null }">
										<input id="ma_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
								    <c:if test="${codiceMov != null }">
										<input id="ma_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
									</spring:bind>
								</td>	   
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
		   <c:set value="true" var="isDocumentiPresenti" scope="page"></c:set>
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
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  movimento.id.codice == null i documenti saranno tutti spuntati -->
									<c:if test="${codiceMov == null }">
										<input id="altrima_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
								    <c:if test="${codiceMov != null }">
										<input id="altrima_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
									</spring:bind>
								</td>
							<%j++; %>	
							</c:forEach>
							
						</table>
						</td>
					</tr>
				</c:forEach>	
        </c:if>
		   
		   
		   
		   
		   
		   
		   
		   
        <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DELL' ISTANZA  -->
        <c:if test="${not empty protocolloCommand.documentiHelper.documentiIstanzaList}">
        <c:set value="true" var="isDocumentiPresenti" scope="page"></c:set>
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
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  movimento.id.codice == null i documenti saranno tutti spuntati -->
									<c:if test="${codiceMov == null }">
										<input id="di_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
								    <c:if test="${codiceMov != null }">
										<input id="di_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
								    </c:if>
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
     
     	     <!-- GESTIONE DELLA  VISUALIZZAZIONE DELLE PROCURE  -->
	        <c:if test="${not empty protocolloCommand.documentiHelper.istanzeprocureList}">
	        <c:set value="true" var="isDocumentiPresenti" scope="page"></c:set>
	        <%int k=1;%>
				<tr class="header" >
					<td colspan="4">
						<fmt:message key="label.documenti_procure" />
					</td>
				</tr>
				<c:forEach items="${protocolloCommand.documentiHelper.istanzeprocureList}" var="current" varStatus="a">				
					<tr>
						<td width="25%"  style="vertical-align: top;" ><b>${current.chiave}</b></td>
						<td width="75%" colspan="4">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="4"><fmt:message key="movimentimail.label.documento" /></td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
				            </tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b">	
								<tr class= "<%=(k%2)==0?"odd":"even"%>">
									<td width="50%">Documento della procura di ${var.anagrafeProcuratore.descrizioneRichiedente}</td>
									<td width="35%">${var.nomeFile}</td>
									<td colspan="2" width="13%">
										<jsp:include page="../includes/visualizzaOggetto.jsp" >
				       						<jsp:param name="idElemento" value="docProc${var.id.codice }" />
				       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
				   						</jsp:include>
	   							    </td>
									<td>
										<spring:bind path="documentiHelper.istanzeprocureList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
										<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
										<!-- Controllo se è zione da instanza o movimento, nel caso sia da istanza  movimento.id.codice == null i documenti saranno tutti spuntati -->
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
     			
     
		     <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DEGLI ENDO  DELL' ISTANZA  -->
		     <c:if test="${not empty protocolloCommand.documentiHelper.documentiEndoprocedimentiList}">
		     <c:set value="true" var="isDocumentiPresenti" scope="page"></c:set>
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
											<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  movimento.id.codice == null i documenti saranno tutti spuntati -->
											<c:if test="${codiceMov == null }">
												<input id="ia_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" checked="true" onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
										    </c:if>
										    <c:if test="${codiceMov != null }">
												<input id="ia_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> onchange="checkIsPrincipale('radio_button_doc_istanza_id${b.index}')" />
										    </c:if>
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
					<c:set value="true" var="isDocumentiPresenti" scope="page"></c:set>
					<tr>
						<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
						<td width="75%" colspan="4">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="4"><fmt:message key="movimentimail.label.documento" /></td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
				            </tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b">	
								<tr class= "<%=(p%2)==0?"odd":"even"%>">
									<td width="50%">${var.documento}</td>
									<td width="35%">${var.nomeFile}</td>
									<td width="13%">
										<jsp:include page="../includes/visualizzaOggetto.jsp" >
				       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
				       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
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
								</tr>
								<%p++; %>								
							</c:forEach>
						</table>
						</td>
					</tr>
				</c:forEach>
				</c:if>
				
				<c:if test="${!isDocumentiPresenti}">
				<tr class="odd">
				<td>
					<b><fmt:message key="label.documenti_non_presenti" /></b>
				</td>
				</tr>
			    </c:if>
		    </table>
		    </div>
		   
		    
		    
		    <div id="functions">
				<ul>
					<c:if test="${isDocumentiPresenti}">
						<li><a href="javascript:doSubmit('inviaDocumenti.htm','',document.innerForm)"><fmt:message key="button.update" /></a></li>
					</c:if>
					<li><a href="javascript:closeTabAddDocumenti();"><fmt:message key="button.back" /></a></li>
				
				
				
				</ul>
			</div>	
		
	</div>
	 </spring-form:form>