<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<spring-form:form commandName="movimentiCommand" name="inner_inviodati">	
	<table width="100%">
	  
	   <%-- Visualizza la lista dei documenti associati al tipo movimento --%>
	   <%
			String displayTabellaDocTipo = "";
			String displaySearchDocTipo = "display:none";
	   %>
	   
	   <tr id="id_table_doc_tipo" style="<%=displayTabellaDocTipo%>;">
	   		<td>
	   			<fieldset><legend><b><fmt:message key="movimenti.label.lista_documenti_stampabili" /></b></legend>
	   			<div class="jmesa">
					<table border="0"  cellpadding="2" cellspacing="0" class="table" width="100%">
						<thead>
							<tr class="header">
								<td ><fmt:message key="label.documento" /> </td>
								<td width="5%" align="center" ><fmt:message key="label.azione" /></td>
				            </tr>
						</thead>
						<tbody class="tbody">
						<%int l=1;%>
						<c:if test="${not empty tipimovimento.tipimovimentodoctipos}">
						<c:forEach items="${tipimovimento.tipimovimentodoctipos}" var="tipidoc_var">
							<c:if test="${tipidoc_var.letteretipo.file.id.codice !=null}">
							<tr class="<%=(l%2)==0?"odd":"even"%>">
								<td>${tipidoc_var.letteretipo.descrizione}</td>
								<td>
								    <span id="id_${tipidoc_var.letteretipo.id.codice}"></span>
									<c:if test="${isSalvaFileInFileSystem eq false}">
										<a class="vbg-btn btn-salva"  href="javascript:void(0);" onclick="creaAllegato(${tipidoc_var.letteretipo.id.codice},${movimentiCommand.entity.istanza.id.codice},${movimentiCommand.entity.id.codice},'${tipimovimento.id.tipomovimento}');" title="<fmt:message key="label.crea_allegato"/>" >
			   								<label><fmt:message key="label.visualizza.image" /></label>
										</a>
									</c:if>
									<c:if test="${isSalvaFileInFileSystem eq true}">
										<a class="vbg-btn btn-salva"  href="javascript:void(0);" onclick="creaAllegatoVisualizzaAllegatoDallaLista(${tipidoc_var.letteretipo.id.codice},${movimentiCommand.entity.istanza.id.codice},${movimentiCommand.entity.id.codice},'${tipimovimento.id.tipomovimento}');" title="<fmt:message key="label.crea_allegato"/>" >
			   								<label><fmt:message key="label.visualizza.image" /></label>
										</a>
									</c:if>
								</td>
							</tr>
							</c:if>
						<%l++; %>
						</c:forEach>
						 </c:if>
						 <c:if test="${empty tipimovimento.tipimovimentodoctipos}">
						 	<tr class="even">
								<td colspan="2"><fmt:message key="movimenti.label.lettere_tipo_non_configurate"/></td>
							</tr>
						 </c:if>
						</tbody>
					</table>
				</div>
				</fieldset>		
	   		</td>
	   </tr>
	   </fieldset> 
	   <!-- Visualizza un campo di ricerca ajax che permette di ricercate i documenti tipo stampabili -->
	   <tr id="id_campo_ricerca_doc_tipo" style="<%=displaySearchDocTipo%>;">	
	   		<td>
	   		   <jsp:include page="../includes/letteretipoSearch.jsp" >
					<jsp:param name="idElemento" value="letteretipo" />		
					<jsp:param name="propertyPath" value="letteretipo" />				
					<jsp:param name="pathPropertyDescription" value="letteretipo.descrizione" />
					<jsp:param name="pathPropertyCode" value="letteretipo.id.codice" />
					<jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />
					<jsp:param name="afterUpdateElement" value="setHiddenFieldLettereTipo" />
					<jsp:param name="titleKey" value="label.ricerca_documenti_tipo" />
					<jsp:param name="id_help" value="help_doc_tipo" />
				</jsp:include>
				<fmt:message key="help.ricerca_per_software_TT" />
				<%-- 
				 <input id="letteretipo_id" name="letteretipo.descrizione" class="searchbox" onchange="checkValue(this,'letteretipo_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67">
				 <init:autocompleter methodAjax="findLettereTipo.htm?codicesoftware=" idHidden="letteretipo_hidden" idInput="letteretipo_id" inputTitleKey="label.ricerca_lettere_tipo"/>
				 
				 <spring-form:errors path="letteretipo" cssClass="error"/> 
				 <spring-form:hidden id="letteretipo_hidden" path="letteretipo.id.codice"  />
				 --%>
	   		</td>
	   </tr>
	   <tr>	
	   		<td>&nbsp;<td>
	   </tr>
	   <tr>
			 <td>
			 	<input type="checkbox" id="id_checkbox_documenti" onclick="mostraRicercaDocTotali('id_checkbox_documenti')" >
				<fmt:message key="movimenti.label.mostra_tutti_documenti_tipo" />
			</td>
	   </tr>	

	</table>
	<div id="functions">
		<ul>
			<li><a href="javascript:void(0)" onClick="dijit.byId('ricercaDocTipoDiv').hide()"><fmt:message key="button.annulla" /></a></li>
			
			<c:if test="${isAttiva}">
				<li><a href="javascript:doHref('../movimenti/prepareCreateDocumentoConLink.htm?codiceIstanza=${movimentiCommand.entity.istanza.id.codice}&codiceMovimento=${movimentiCommand.entity.id.codice}','')"><fmt:message key="button.crea_documenti_con_link" /></a></li>
        	</c:if>
        </ul>
  </div>

</spring-form:form>	

