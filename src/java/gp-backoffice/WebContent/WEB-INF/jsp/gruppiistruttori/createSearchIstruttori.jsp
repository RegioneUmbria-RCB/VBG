<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%-- <spring-form:form commandName="movimentiCommand" name="inner_inviodati">	 --%>

<c:set var="modifica_da_lista" value="false" scope="page" />	
<c:if test="${isModifica && modifica_istr_da_lista}"> 
<%-- <c:if test="${modifica_istr_da_lista}"> --%>
	<c:set var="modifica_da_lista" value="true" scope="page" />	
</c:if>
<c:set var="istr_gruppo_assenti" value="false" scope="page" />
	
<style>
   	#istruttore_id{
 		width:250px;   
	}
</style>


<%
	String displayTable = "";
	
	//gestisce la visualizzazione della tabella normative
	if (((Boolean) request.getAttribute("isModifica").equals(true))) {
	    displayTable="display:none;";
	}
%>

	
	<table width="100%"> 
	   <tr>
	   		  <td>
	   		    <c:if test="${isModifica}">
	   		    
	   			<input type="checkbox" id="chk_attiva_informativa"/>
				<label for="cancellazionedocistanzachk_id">
					<fmt:message key="label.messaggio_modifica_resp_istruttore_per_operatore">
						<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
					</fmt:message>
				</label>
				<!-- 
				<div id="functions">
					<ul>
						<li style="display: none;" id="doDeleteId"><a href="javascript:elimina()"><fmt:message key="button.delete" /></a></li>
						<li><a href="javascript:void 0" onclick="$('codiceMovimentoAllegato_id').value='';dijit.byId('cancellaDocumentiIstanzeDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
					</ul>
				</div>
				 -->
				 
				</c:if>
				</td>
			</tr>
			<tr>	
				<td style="<%=displayTable%>"  class="sezioneModifica">
	   			<div  class="jmesa">
					<table border="0"  cellpadding="2" cellspacing="0" class="table" width="100%">
						<thead>
							<tr class="header">
								<td ><fmt:message key="label.lista_istruttori_del_gruppo" /> </td>
				            </tr>
						</thead>
						<tbody class="tbody">
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<c:if test="${isModifica}">
								<td><b>Permette di modificare l'istruttore scegliendolo all'interno del gruppo associato all'istanza</b></td>
							</c:if>
							<c:if test="${!isModifica}">
								<td><b>Permette di scegliere l'istruttore scegliendolo all'interno del gruppo associato all'istanza</b></td>
							</c:if>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<select id="istruttore_id" name="istruttore" multiple="multiple" size="" >
								  	<c:forEach items="${listIstruttori}" var="istruttoreResp">
									    <c:if test="${!istruttoreResp.responsabili.isAssenteOra && istruttoreResp.isAttivoPerSoftwareCorrente}">
									    	<option  value="${istruttoreResp.responsabili.id.codice}">${istruttoreResp.responsabili.responsabile}</option>
	    							    </c:if>
	    							</c:forEach>
								</select>
							</td>
						</tr>
						<tr class="header">
							<td><fmt:message key="label.lista_istr_grup_assenti_o_non_attivi_sw" /></td>
				        </tr>
				        	
				        	<c:forEach items="${listIstruttori}" var="_istruttoreResp">
						    <tr>
						    	<td style="color: red;">
							    <c:if test="${_istruttoreResp.responsabili.isAssenteOra || !_istruttoreResp.isAttivoPerSoftwareCorrente}">
							        <c:set value="true" var="istr_gruppo_assenti" scope="page"/> 
							   		${_istruttoreResp.responsabili.responsabile}
							   	</c:if>
  							   	</td>
  							 </tr>
    						</c:forEach>
    						<c:if test="${!istr_gruppo_assenti}">
    						<tr>
						    	<td>Non sono presenti nel gruppo istruttori non selezionabili</td>
  							</tr>
  							</c:if> 
						</tbody>
					</table>
				</div>
	   		</td>
	   </tr>
	   </fieldset> 
	   <tr>
	  	 <td>
	  		<div id="functions">
				<ul>
				<c:if test="${!isModifica}">
				    <li><a href="javascript:assegna(${codiceIstanza})"><fmt:message key="button.assegna_da_gruppo" /></a></li>
				</c:if>
				    <li style="display: none;" class="sezioneModifica"><a href="javascript:modifica(${codiceIstanza})"><fmt:message key="button.assegna_da_gruppo" /></a></li>
					<c:if test="${!modifica_da_lista}">
					<li><a href="javascript:void(0)" onClick="dijit.byId('ricercaIstruttoriDiv').hide()"><fmt:message key="button.annulla" /></a></li>
					</c:if>
					<%-- <li><a href="javascript:void(0)" onClick="dijit.byId('ricercaIstruttoriDiv').hide()"><fmt:message key="button.annulla" /></a></li> --%>
		        </ul>
		  	</div>			
		</td>
	   </tr>
	   <%-- <c:if test="${isModifica && modifica_istr_da_lista}"> --%>
	   
	   <c:if test="${modifica_da_lista}">
	   <spring-form:form commandName="istanza" name="innerInviodatiIstr" id="innerIstanzeForm">
	   <tr>	
		   	<td style="<%=displayTable%>"  class="sezioneModifica">
   			<div  class="jmesa">
				<table border="0"  cellpadding="2" cellspacing="0" class="table" width="100%">
					<thead>
						<tr class="header">
							<td ><fmt:message key="label.lista_istruttori_completa" /> </td>
			            </tr>
					</thead>
					<tbody class="tbody">
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<c:if test="${isModifica}">
							<td><b>Permette di modificare l'istruttore scegliendolo tra tutti gli istruttori censiti</b></td>
						</c:if>
						<c:if test="${!isModifica}">
							<td><b>Permette di scegliere l'istruttore scegliendolo tra tutti gli istruttori censiti</b></td>
						</c:if>
					</tr>
					<tr>
							<td>&nbsp;</td>
						</tr>
					<tr>
						<td>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="istruttore_id" />
							<jsp:param name="propertyPath" value="istruttore" />
							<jsp:param name="pathPropertyDescription" value="istruttore.responsabile" />
							<jsp:param name="pathPropertyCode" value="istruttore.id.codice" />
							<jsp:param name="autocompleterAjax" value="findResponsabiliIstruttoria.htm" />
							<jsp:param name="titleKey" value="label.ricerca_responsabile" />
						</jsp:include>
						</td>
					</tr>	
					</tbody>
				</table>
			</div>
	   		</td>
	   </tr>
	   <tr>
	  	 <td>
	  		<div id="functions">
				<ul>
				<%-- 
				<c:if test="${!isModifica}">
				    <li><a href="javascript:assegnaDaInteraLista(${codiceIstanza},'istruttore_id_hidden')"><fmt:message key="button.assegna" /></a></li>
				</c:if>
				--%>
				    <li style="display: none;" class="sezioneModifica"><a href="javascript:modificaDaInteraLista(${codiceIstanza},'istruttore_id_hidden')"><fmt:message key="button.assegna" /></a></li>
					<li><a href="javascript:void(0)" onClick="dijit.byId('ricercaIstruttoriDiv').hide()"><fmt:message key="button.annulla" /></a></li>
		        </ul>
		  	</div>			
		</td>
	   </tr>
	   </spring-form:form>
	   </c:if>
	   
	</table>
	<script type="text/javascript">
			jQuery("#chk_attiva_informativa").click(function(){
				if(jQuery('#chk_attiva_informativa').is(':checked'))
				{ jQuery(".sezioneModifica").show();}else
				{jQuery(".sezioneModifica").hide();}
			})
		
	</script>
	

<%--  </spring-form:form>	--%>

