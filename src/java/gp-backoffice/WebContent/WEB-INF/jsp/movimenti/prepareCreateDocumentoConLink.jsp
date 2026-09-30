<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
		<fmt:message key="label.crea_documento.title" />
</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.crea_documento.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../movimenti/prepareCreateDocumentoConLink" />
	</jsp:include>
	<spring-form:form commandName="movimentiCommand" name="inviodati">
	
		<table>
			 <tr>
				<td>
					<fmt:message key="tipimovimento.label.letteratipo_linkdoc" />
				</td>
				<td>
				<jsp:include page="../includes/autocompletergenericoTT.jsp" >
					<jsp:param name="idElemento" value="letteretipo" />		
					<jsp:param name="propertyPath" value="letteretipo" />				
					<jsp:param name="pathPropertyDescription" value="letteretipo.descrizione" />
					<jsp:param name="pathPropertyCode" value="letteretipo.id.codice" />
					<jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />	
					<jsp:param name="titleKey" value="label.ricerca_tipo_lettera" />
					<jsp:param name="id_help" value="help_letteraTipoAllegati" />
					<jsp:param name="help" value="help.search_archivi_base" />
				</jsp:include>
				<%-- <fmt:message key="help.letteratipo_linkdoc"/>--%>
				</td>
			</tr>
		</table>
		<c:if test="${not empty docConfiguratiPerMov}">
		<br />
			
			<fieldset><legend>Documenti configurati</legend>
				<table>
	
					<c:forEach items="${docConfiguratiPerMov}" var="tipidoc_var">
						<c:if test="${tipidoc_var.letteretipo.file.id.codice !=null && tipidoc_var.flgGeneraAut eq false}">
							<tr >
								<td><b>${tipidoc_var.letteretipo.descrizione}</b></td>
								<td>
									<input type="radio" name="documento_scelto" data-codice="${tipidoc_var.letteretipo.id.codice}" data-descrizione="${tipidoc_var.letteretipo.descrizione}" value="${tipidoc_var.letteretipo.id.codice}" onclick="impostaDocumento(this)"/>								   
								</td>
							</tr>
						</c:if>
					</c:forEach>		
				</table>
			</fieldset>
		</c:if>	
		
		<fieldset><legend><fmt:message	key="label.sezione_documenti" /></legend>
		
		<table>
			<jsp:include page="../includes/ziplogicosection.jsp">
				<jsp:param name="movimento" value="${movimentiCommand.entity.movimento}" />
				<jsp:param name="codicemovimento" value="${movimentiCommand.entity.id.codice }"/>
				<jsp:param name="isZipLogico" value="${ifZipLogicoExist }"/>
				<jsp:param name="displayNone" value="<%=false %>"/>
				<jsp:param name="labelForFlgZipLogicoChbx" value="label.movimenti_zip_logico.zip_logico"/>
				<jsp:param name="commandPathProperty" value="flgZipLogico"/>
				<jsp:param name="help" value="label.movimenti_zip_logico.help_crea_documento_link"/>
				<jsp:param name="hideDocAltrimov" value=".hide_doc_altrimovimenti"/>
				<jsp:param name="inputMaChbx" value="input[id^='altrima_']" />
				<jsp:param name="hideDocist" value=".hide_docistanza"/>
				<jsp:param name="inputIstChbx" value="input[id^='di_']" />
				<jsp:param name="hideDocendo" value=".hide_docendo"/>
				<jsp:param name="inputEndoChbx" value="input[id^='ia_']" />
				<jsp:param name="hideDocanag" value=".hide_docanagrafe"/>
				<jsp:param name="inputAnagChbx" value="input[id^='docanagr_']" />
				<jsp:param name="isRadioBtn" value="<%=false %>" />
			</jsp:include>
		</table>
		
		<div class="jmesa">
		<table border="0" cellpadding="2" cellspacing="0" class="table">
		
		   
			<c:if test="${not empty movimentiCommand.documentiHelper.documentiMovimentoList}">
      			  <%int i=1;%>
				<tr class="header" >
					<td colspan="5">
						<fmt:message key="label.documenti_movimento" />
					</td>
				</tr>	
			<!-- SEZIONE DOCUMENTI DEL MOVIMENTO -->
			<c:forEach items="${movimentiCommand.documentiHelper.documentiMovimentoList}" var="current" varStatus="a">
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
								<input id="ma_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>  />
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
	   <!-- SEZIONE DOCUMENTI DEL ALTRI MOVIMENT -->
	   
	    
	   <c:if test="${not empty movimentiCommand.documentiHelper.documentiAltriMovimentiList}">
	   <%int j=1;%>
			<tr class="header hide_doc_altrimovimenti" >
				<td colspan="5">
					<fmt:message key="label.documenti_altri_movimenti" />
				</td>
			</tr>
			<c:forEach items="${movimentiCommand.documentiHelper.documentiAltriMovimentiList}" var="current" varStatus="a">
				<tr class="hide_doc_altrimovimenti">
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
								<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
									<input id="altrima_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if> />
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
        
         
        <c:if test="${not empty movimentiCommand.documentiHelper.documentiIstanzaList}">
        <%int k=1;%>
			<tr class="header hide_docistanza" >
				<td colspan="5">
					<fmt:message key="label.allegati_istanza" />
				</td>
			</tr>
			<c:forEach items="${movimentiCommand.documentiHelper.documentiIstanzaList}" var="current" varStatus="a">
				<tr class="hide_docistanza">
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
   
   
     <c:if test="${not empty movimentiCommand.documentiHelper.documentiEndoprocedimentiList}">
           <%int b=1;%>
		<tr class="header hide_docendo" >
			<td colspan="5">
				<fmt:message key="label.allegati_endoprocedimenti" />
			</td>
		</tr>
		<c:forEach items="${movimentiCommand.documentiHelper.documentiEndoprocedimentiList}" var="current" varStatus="a">
			<tr class="hide_docendo">
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
		
		
		<!-- SEZIONE DOCUMENTI DELLE ANAGRAFICHE -->
		 
		<c:if test="${not empty movimentiCommand.documentiHelper.documentiAnagrafeList}">
		<%int p=1;%>
		<tr class="header hide_docanagrafe" >
				<td colspan="5">
					<fmt:message key="label.allegati_anagrafiche" />
				</td>
		</tr>
		<c:forEach items="${movimentiCommand.documentiHelper.documentiAnagrafeList}" var="current" varStatus="a">
		<tr class="hide_docanagrafe">
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
 							</td>
					   
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
     
	</table>
</fieldset>
</spring-form:form>
<script type="text/javascript">

	function creaDocumento(){
		
		if(document.getElementById('letteretipo_hidden').value != '')
		{
		  document.inviodati.action='createDocumentoConLink.htm';
		  vbg.mostraModalCaricamento();
          setTimeout("document.inviodati.submit()",10);
		}else
		{
			alert('Selezionare un documento tipo valido');
			return false;
		}
	}
	
	function impostaDocumento(obj){
		
		let codice = obj.dataset.codice;
		let descrizione = obj.dataset.descrizione
		document.getElementById('letteretipo_hidden').value = codice;
		document.getElementById('letteretipo_id1').value = descrizione;
		document.getElementById('letteretipo_id2').value = descrizione;
	} 
	
	jQuery(document).ready(function () {
		
		hideOtherDocumentSectionsIfFlgZipLogicoChecked(false);
	});
	
</script>
<div id="functions">
	<ul>
	
	
	    <%-- <li><a href="javascript:doSubmit('createDocumentoConLink.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li> --%>
		<li><a href="javascript:creaDocumento()"><fmt:message key="button.insert" /></a></li>
		<li><a href="javascript:doHref('../movimenti/view.htm?codice=${movimentiCommand.entity.id.codice}')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>

</body>
</html>