<%@page import="it.gruppoinit.pal.gp.core.domain.web.AnagrafeCommand"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.ProtocollazioneController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%-- DATI RESIDENZA START --%>
				<%
				
					AnagrafeCommand anagrafe = (AnagrafeCommand)request.getAttribute("anagrafe");
				
					String displayResidenza_sedeLegale= "display:none;";
					String styleResidenza_sedeLegale = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ANAGRAFE_RESIDENZA_SEDE_LEGALE)).equals("1")) {
					    displayResidenza_sedeLegale = "";
					    styleResidenza_sedeLegale="sezioneDatiMeno";
					} else {
					    displayResidenza_sedeLegale = "display:none;";
					    styleResidenza_sedeLegale="sezioneDatiPiu";
					}
				%>
	            <c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
				<tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleResidenza_sedeLegale%>" id="id_link_residenza_sede_legale" href="javascript:showHidePanel('id_res_sede_table', 'id_link_residenza_sede_legale', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_RESIDENZA_SEDE_LEGALE %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione"/> <fmt:message key="label.residenza"/>">
							<label for="id_link_residenza_sede_legale"><fmt:message key="label.residenza"/></label>
						</a>
					</td>
				</tr>
				</c:if>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
				<tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleResidenza_sedeLegale%>" id="id_link_residenza_sede_legale" href="javascript:showHidePanel('id_res_sede_table', 'id_link_residenza_sede_legale', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_RESIDENZA_SEDE_LEGALE %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione"/> <fmt:message key="label.sede_legale"/>">
							<label for="id_link_residenza_sede_legale"><fmt:message key="label.sede_legale"/></label>
						</a>
					</td>
				</tr>
				</c:if>
				<tr id="id_res_sede_table" style="<%=displayResidenza_sedeLegale%>;">
					<td><fmt:message key="label.indirizzo"/></td>
					<td colspan="5">
						<c:choose>
						   <c:when test="${anagrafe.entity.indirizzo eq anagrafe.oldAnagrafe.indirizzo || (empty anagrafe.entity.indirizzo and empty  anagrafe.oldAnagrafe.indirizzo)}">
								<spring-form:input id="indirizzo_id" path="entity.indirizzo" size="60"/>
							</c:when>
							<c:otherwise>
								<spring-form:input cssClass="bgred" id="indirizzo_id" path="entity.indirizzo" size="60" onclick="gda('indirizzo_modificatoOverlay_id')"/>
								<input id="id_nuovo_indirizzo" type="hidden" value="${anagrafe.oldAnagrafe.indirizzo}" name="oldAnagrafe.indirizzo"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.indirizzo}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.indirizzo}"/>
								   <jsp:param value="indirizzo_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="indirizzo_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="indirizzo_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_indirizzo" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>
						<spring-form:errors path="entity.indirizzo" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_res_sede_table" style="<%=displayResidenza_sedeLegale%>;">
					<td><fmt:message key="label.localita"/></td>
					<td >
					  <c:choose>
					  	<c:when test="${anagrafe.entity.citta eq anagrafe.oldAnagrafe.citta || (empty anagrafe.entity.citta and empty  anagrafe.oldAnagrafe.citta)}">
					  		<spring-form:input id="citta_id" path="entity.citta" size="30"/>
					    </c:when>
					  	<c:otherwise>
						  	<spring-form:input cssClass="bgred" id="citta_id" path="entity.citta" size="30" onclick="gda('citta_modificatoOverlay_id')"/>
						    <input id="id_nuovo_citta" type="hidden" value="${anagrafe.oldAnagrafe.citta}" name="oldAnagrafe.citta"></input>
						    <jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.citta}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.citta}"/>
								   <jsp:param value="citta_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="citta_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="citta_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_citta" name="oldfieldId"/>
							</jsp:include>	
					  	</c:otherwise>
					  </c:choose>
						<spring-form:errors path="entity.citta" cssClass="error"/>
					</td>
					<td><fmt:message key="label.cap"/></td>
					<td colspan="3">
						<c:choose>
						   <c:when test="${anagrafe.entity.cap eq anagrafe.oldAnagrafe.cap || (empty anagrafe.entity.cap and empty  anagrafe.oldAnagrafe.cap)}">
								<spring-form:input id="cap_id" path="entity.cap" size="6"/>
						   </c:when>
						   <c:otherwise>							   
							    	<spring-form:input id="cap_id" cssClass="bgred" path="entity.cap" size="6" onclick="gda('cap_modificatoOverlay_id')"/>
							    	<input id="id_nuovo_cap" type="hidden" value="${anagrafe.oldAnagrafe.cap}" name="oldAnagrafe.cap"></input>
							        <jsp:include page="../anagrafe/formModificaCampi.jsp">
									   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.cap}"/>
									   <jsp:param name="nuovocampo" value="${anagrafe.entity.cap}"/>
									   <jsp:param value="cap_modificatoOverlay_id" name="id_div_overlay"/>
									   <jsp:param value="cap_modificatoInner_id" name="id_div_inner"/>
									   <jsp:param value="cap_id" name="fieldId"/>
									   <jsp:param value="id_nuovo_cap" name="oldfieldId"/>
									</jsp:include>								   
						   </c:otherwise>
					   </c:choose>
					<spring-form:errors path="entity.cap" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_res_sede_table" style="<%=displayResidenza_sedeLegale%>;">
					<td><fmt:message key="label.comune"/></td>
					<td>
						<script type="text/javascript">
					   function ricercaComuneresidenzaAfterUpdate(inputField,listItem){
						   		var a = listItem.id;
						   		jQuery('#comune_id').val(inputField.value);
						   		jQuery('#comune_hidden').val(a);								
								altreInformazioniComuneresidenza(a);
						}	
						function altreInformazioniComuneresidenza(codiceComune){
								var call_msg = new Ajax.Request('<%=request.getContextPath()%>/json/getProvincia.htm?codiceComune=' + codiceComune, {
									  method: 'post',	
									  onSuccess: function(transport){ 
										var json = transport.responseText.evalJSON();											
										var comune = json.comune;
										var provElem = $('provincia_id');
										if(comune.siglaprovincia!=null){
											provElem.value= comune.siglaprovincia;
										}
										var capElem = $('cap_id');
										if(comune.cap!=null){
											capElem.value= comune.cap;
										}
							  			},
									  onFailure: function(transport){ 
							  			var responseTexts = transport.responseText;
							  			alert(responseTexts);
								  	 }						    		 
								});								
							}
						</script>
					   <c:if test="${anagrafe.entity.comuneResidenza.codicecomune eq anagrafe.oldAnagrafe.comuneResidenza.codicecomune || anagrafe.entity.comuneResidenza.codicecomune==null }">
							<spring-form:input id="comune_id" path="entity.comuneResidenza.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comune_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="40"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_hidden" idInput="comune_id" afterUpdateElement="ricercaComuneresidenzaAfterUpdate" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comuneResidenza" cssClass="error"/> 
							<spring-form:hidden id="comune_hidden" path="entity.comuneResidenza.codicecomune"  />
						</c:if>
						<c:if test="${(anagrafe.entity.comuneResidenza.codicecomune ne anagrafe.oldAnagrafe.comuneResidenza.codicecomune) && anagrafe.entity.comuneResidenza.codicecomune!=null}">
							<spring-form:input id="comune_id" path="entity.comuneResidenza.descrizioneEstesa" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'comune_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gda('comune_modificatoOverlay_id')"  size="40"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_hidden" idInput="comune_id" afterUpdateElement="ricercaComuneresidenzaAfterUpdate" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comuneResidenza" cssClass="error"/> 
							<spring-form:hidden id="comune_hidden" path="entity.comuneResidenza.codicecomune"  />
							<input type="hidden" id="id_nuovo_comune" name="oldAnagrafe.comuneResidenza.comune"  value="${anagrafe.oldAnagrafe.comuneResidenza.comune}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.comuneResidenza.comune}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.comuneResidenza.comune}"/>
							   <jsp:param value="comune_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="comune_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="comune_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_comune" name="oldfieldId"/>
							</jsp:include>	
						</c:if>
					</td>
					<td><fmt:message key="label.sigla_provincia"/></td>
					<td colspan="3">
					    <c:choose>
						   <c:when test="${anagrafe.entity.provincia eq anagrafe.oldAnagrafe.provincia || (empty anagrafe.entity.provincia and empty  anagrafe.oldAnagrafe.provincia)}">
								<spring-form:input id="provincia_id" path="entity.provincia" size="2"/>
							</c:when>
						    <c:otherwise>
						    	<spring-form:input id="provincia_id" cssClass="bgred" path="entity.provincia" size="2" onclick="gda('provincia_modificatoOverlay_id')"/>
						    	<input id="id_nuovo_provincia" type="hidden" value="${anagrafe.oldAnagrafe.provincia}" name="oldAnagrafe.provincia"></input>
						        <jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.provincia}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.provincia}"/>
								   <jsp:param value="provincia_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="provincia_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="provincia_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_provincia" name="oldfieldId"/>
								</jsp:include>	
					 		</c:otherwise>
					 	</c:choose>	
						<spring-form:errors path="entity.provincia" cssClass="error"/>
					</td>
				</tr>
				<%-- GESTIONE DELL'INDIRIZZO DI CORRISPONDENZA START --%>	
				<%
					String displayIndirizzoCorrispondenza= "display:none;";
					String styleIndirizzoCorrispondenza = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ANAGRAFE_INDIRIZZO_CORRISPONDENZA)).equals("1")) {
					    displayIndirizzoCorrispondenza = "";
					    styleIndirizzoCorrispondenza="sezioneDatiMeno";
					} else {
					    displayIndirizzoCorrispondenza = "display:none;";
					    styleIndirizzoCorrispondenza="sezioneDatiPiu";
					}
				%>
				<tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleIndirizzoCorrispondenza%>" id="id_link_indirizzo_corrispondenza" href="javascript:showHidePanel('id_ind_corr_table', 'id_link_indirizzo_corrispondenza', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_INDIRIZZO_CORRISPONDENZA %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione"/> <fmt:message key="label.corrispondenza"/>">
							<label for="id_link_indirizzo_corrispondenza"><fmt:message key="label.corrispondenza"/></label>
						</a>
					</td>
				</tr>
				<tr id="id_ind_corr_table" style="<%=displayIndirizzoCorrispondenza%>;">
					<td><fmt:message key="label.indirizzo"/></td>
					<td colspan="5">
					    <c:choose>					    
					    <c:when test="${anagrafe.entity.indirizzocorrispondenza eq anagrafe.oldAnagrafe.indirizzocorrispondenza || (empty anagrafe.entity.indirizzocorrispondenza and empty  anagrafe.oldAnagrafe.indirizzocorrispondenza)}">
								<spring-form:input id="indirizzo_corrispondenza_id" path="entity.indirizzocorrispondenza" size="60"/>
						    </c:when>
							<c:otherwise>
								<spring-form:input id="indirizzo_corrispondenza_id"  cssClass="bgred" path="entity.indirizzocorrispondenza" size="60" onclick="gda('indirizzo_corrispondenza_modificatoOverlay_id')"/>
							    <input id="id_nuovo_indirizzo_corrispondenza" type="hidden" value="${anagrafe.oldAnagrafe.indirizzocorrispondenza}" name="oldAnagrafe.indirizzocorrispondenza"></input>
							    <jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.indirizzocorrispondenza}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.indirizzocorrispondenza}"/>
								   <jsp:param value="indirizzo_corrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="indirizzo_corrispondenza_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="indirizzo_corrispondenza_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_indirizzo_corrispondenza" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>	
							<spring-form:errors path="entity.indirizzocorrispondenza" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_ind_corr_table" style="<%=displayIndirizzoCorrispondenza%>;">
					<td><fmt:message key="label.localita"/></td>
					<td>
					    <c:choose>
						    <c:when test="${anagrafe.entity.cittacorrispondenza eq anagrafe.oldAnagrafe.cittacorrispondenza || (empty anagrafe.entity.cittacorrispondenza and empty  anagrafe.oldAnagrafe.cittacorrispondenza)}">
								<spring-form:input id="citta_corrispondenza_id" path="entity.cittacorrispondenza" size="20"/>
						    </c:when>
							<c:otherwise>
								<spring-form:input cssClass="bgred" id="citta_corrispondenza_id" path="entity.cittacorrispondenza" size="20" onclick="gda('citta_corrispondenza_modificatoOverlay_id')"/>
								<input id="id_nuovo_citta_corrispondenza" type="hidden" value="${anagrafe.oldAnagrafe.cittacorrispondenza}" name="oldAnagrafe.cittacorrispondenza"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.cittacorrispondenza}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.cittacorrispondenza}"/>
								   <jsp:param value="citta_corrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="citta_corrispondenza_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="citta_corrispondenza_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_citta_corrispondenza" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>
						<spring-form:errors path="entity.cittacorrispondenza" cssClass="error"/>
					</td>
					<td><fmt:message key="label.cap"/></td>
					<td colspan="3">
						<c:choose>
						    <c:when test="${anagrafe.entity.capcorrispondenza eq anagrafe.oldAnagrafe.capcorrispondenza || (empty anagrafe.entity.capcorrispondenza and empty  anagrafe.oldAnagrafe.capcorrispondenza)}">
								<spring-form:input id="cap_corrispondenza_id" path="entity.capcorrispondenza" size="8"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input  cssClass="bgred" id="cap_corrispondenza_id" path="entity.capcorrispondenza" size="8" onclick="gda('cap_corrispondenza_modificatoOverlay_id')"/>
								<input id="id_nuovo_cap_corrispondenza" type="hidden" value="${anagrafe.oldAnagrafe.capcorrispondenza}" name="oldAnagrafe.capcorrispondenza"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.capcorrispondenza}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.capcorrispondenza}"/>
								   <jsp:param value="cap_corrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="cap_corrispondenza_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="cap_corrispondenza_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_cap_corrispondenza" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.capcorrispondenza" cssClass="error"/>						
					</td>
				</tr>
				<tr id="id_ind_corr_table" style="<%=displayIndirizzoCorrispondenza%>;">
					<td><fmt:message key="label.comune"/></td>
					<td>
					<script type="text/javascript">
					 function ricercaComunecorrispondenzaAfterUpdate(inputField,listItem){
						   		var a = listItem.id;
						   		jQuery('#comunecorrispondenza_id').val(inputField.value);
						   		jQuery('#comunecorrispondenza_hidden').val(a);
								altreInformazioniComunecorrispondenza(a);
						}							
					function altreInformazioniComunecorrispondenza(codiceComune){
								var call_msg = new Ajax.Request('<%=request.getContextPath()%>/json/getProvincia.htm?codiceComune=' + codiceComune, {
									  method: 'post',	
									  onSuccess: function(transport){ 
										var json = transport.responseText.evalJSON();											
										var comune = json.comune;
										var provElem = $('provincia_corrispondenza_id');
										if(comune.siglaprovincia!=null){
											provElem.value = comune.siglaprovincia;
										}
										var capElem = $('cap_corrispondenza_id');
										if(comune.cap!=null){
											capElem.value = comune.cap;
										}
							  			},
									  onFailure: function(transport){ 
							  			var responseTexts = transport.responseText;
							  			alert(responseTexts);
								  	 }						    		 
								});								
							}
					</script>
					<c:if test="${anagrafe.entity.comunecorrispondenza.codicecomune eq anagrafe.oldAnagrafe.comunecorrispondenza.codicecomune || anagrafe.entity.comunecorrispondenza.codicecomune==null }">
						<spring-form:input id="comunecorrispondenza_id" path="entity.comunecorrispondenza.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comunecorrispondenza_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="40"/>
						<init:autocompleter methodAjax="findComuni.htm" idHidden="comunecorrispondenza_hidden" idInput="comunecorrispondenza_id" afterUpdateElement="ricercaComunecorrispondenzaAfterUpdate" inputTitleKey="label.ricerca_comune"></init:autocompleter>
						<spring-form:errors path="entity.comunecorrispondenza" cssClass="error"/> 
						<spring-form:hidden id="comunecorrispondenza_hidden" path="entity.comunecorrispondenza.codicecomune"  />
					</c:if>
					<c:if test="${(anagrafe.entity.comunecorrispondenza.codicecomune ne anagrafe.oldAnagrafe.comunecorrispondenza.codicecomune) && anagrafe.entity.comunecorrispondenza.codicecomune!=null}">
							<spring-form:input id="comunecorrispondenza_id" path="entity.comunecorrispondenza.descrizioneEstesa" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'comunecorrispondenza_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gda('comunecorrispondenza_modificatoOverlay_id')"  size="40"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comunecorrispondenza_hidden" idInput="comunecorrispondenza_id" afterUpdateElement="ricercaComunecorrispondenzaAfterUpdate" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comunecorrispondenza" cssClass="error"/> 
							<spring-form:hidden id="comunecorrispondenza_hidden" path="entity.comunecorrispondenza.codicecomune"  />
							<input type="hidden" id="id_nuovo_comunecorrispondenza" name="oldAnagrafe.comunecorrispondenza.comune"  value="${anagrafe.oldAnagrafe.comunecorrispondenza.comune}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.comunecorrispondenza.comune}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.comunecorrispondenza.comune}"/>
							   <jsp:param value="comunecorrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="comunecorrispondenza_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="comunecorrispondenza_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_comunecorrispondenza" name="oldfieldId"/>
							</jsp:include>
					</c:if>				
					</td>
					<td><fmt:message key="label.sigla_provincia"/></td>
					<td colspan="3">
						<c:choose>
							<c:when test="${anagrafe.entity.provinciacorrispondenza eq anagrafe.oldAnagrafe.provinciacorrispondenza || (empty anagrafe.entity.provinciacorrispondenza and empty  anagrafe.oldAnagrafe.provinciacorrispondenza)}">
								<spring-form:input id="provincia_corrispondenza_id" path="entity.provinciacorrispondenza" size="2"/>
							</c:when>
							<c:otherwise>
								<spring-form:input cssClass="bgred" id="provincia_corrispondenza_id" path="entity.provinciacorrispondenza" size="60" onclick="gda('provinciacorrispondenza_modificatoOverlay_id')"/>
								<input id="id_nuovo_provincia" type="hidden" value="${anagrafe.oldAnagrafe.provinciacorrispondenza}" name="oldAnagrafe.provinciacorrispondenza"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.provinciacorrispondenza}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.provinciacorrispondenza}"/>
								   <jsp:param value="provinciacorrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="provinciacorrispondenza_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="provinciacorrispondenza_corrispondenza_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_provincia" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>	
						<spring-form:errors path="entity.provinciacorrispondenza" cssClass="error"/>	
					</td>
				</tr>