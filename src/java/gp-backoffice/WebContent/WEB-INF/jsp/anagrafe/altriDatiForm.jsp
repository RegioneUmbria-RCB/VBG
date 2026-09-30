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
				
					String displayAltriDati = "display:none;";
					String styleAltri = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ANAGRAFE_ALTRI_DATI)).equals("1")) {
					    displayAltriDati = "";
					    styleAltri="sezioneDatiMeno";
					} else {
					    displayAltriDati = "display:none;";
					    styleAltri="sezioneDatiPiu";
					}
				%>
			    <tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleAltri%>" id="id_link_altridati" href="javascript:showHidePanel('id_altridati_table', 'id_link_altridati', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_ALTRI_DATI %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione"/> <fmt:message key="label.altri_dati"/>">
							<label for="id_link_altridati"><fmt:message key="label.altri_dati"/></label>
						</a>
					</td>
				</tr>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td><fmt:message key="label.telefono"/></td>
					<td class="inline-ui-cell" class="inline-ui-cell">
						<c:choose>
							<c:when test="${anagrafe.entity.telefono eq anagrafe.oldAnagrafe.telefono || (empty anagrafe.entity.telefono and empty  anagrafe.oldAnagrafe.telefono)}">
								<spring-form:input id="telefono_id" path="entity.telefono" size="25"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssClass="bgred" id="telefono_id" path="entity.telefono" size="25" onclick="gda('telefono_corrispondenza_modificatoOverlay_id')"/>
								<input id="id_nuovo_telefono" type="hidden" value="${anagrafe.oldAnagrafe.telefono}" name="oldAnagrafe.telefono"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.telefono}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.telefono}"/>
								   <jsp:param value="telefono_corrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="telefono_corrispondenza_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="telefono_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_telefono" name="oldfieldId"/>
								</jsp:include>	
						  	</c:otherwise>
						 </c:choose> 	 
						<spring-form:errors path="entity.telefono" cssClass="error"/>
					</td>
					<td class="inline-ui-cell"><fmt:message key="label.cellulare"/></td>
					<td colspan="3">
					    <c:choose>
							<c:when test="${anagrafe.entity.telefonocellulare eq anagrafe.oldAnagrafe.telefonocellulare || (empty anagrafe.entity.telefonocellulare and empty  anagrafe.oldAnagrafe.telefonocellulare)}">
								<spring-form:input id="telefonocellulare_id" path="entity.telefonocellulare" size="25"/>
						    </c:when>
							<c:otherwise>
								<spring-form:input cssClass="bgred" id="telefonocellulare_id" path="entity.telefonocellulare" size="25" onclick="gda('telefonocellulare_modificatoOverlay_id')"/>
								<input id="id_nuovo_telefonocellulare" type="hidden" value="${anagrafe.oldAnagrafe.telefonocellulare}" name="oldAnagrafe.telefonocellulare"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.telefonocellulare}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.telefonocellulare}"/>
								   <jsp:param value="telefonocellulare_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="telefonocellulare_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="telefonocellulare_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_telefonocellulare" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.telefonocellulare" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td><fmt:message key="label.fax"/></td>
					<td class="inline-ui-cell">
						<c:choose>
							<c:when test="${anagrafe.entity.fax eq anagrafe.oldAnagrafe.fax || (empty anagrafe.entity.fax and empty  anagrafe.oldAnagrafe.fax)}">
								<spring-form:input id="fax_id" path="entity.fax" size="25"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssClass="bgred" id="fax_id" path="entity.fax" size="25" onclick="gda('fax_modificatoOverlay_id')"/>
								<input id="id_nuovo_fax" type="hidden" value="${anagrafe.oldAnagrafe.fax}" name="oldAnagrafe.fax"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.fax}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.fax}"/>
								   <jsp:param value="fax_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="fax_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="fax_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_fax" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
				        </c:choose>			
						<spring-form:errors path="entity.fax" cssClass="error"/>
					</td>
					<td class="inline-ui-cell"><fmt:message key="label.email"/></td>
					<td colspan="3">
		              	<c:choose>
							<c:when test="${anagrafe.entity.email eq anagrafe.oldAnagrafe.email || (empty anagrafe.entity.email and empty  anagrafe.oldAnagrafe.email)}">		
								<spring-form:input id="email_id" path="entity.email" size="40"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssClass="bgred" id="email_id" path="entity.email" size="40" onclick="gda('email_modificatoOverlay_id')"/>
								<input id="id_nuovo_email" type="hidden" value="${anagrafe.oldAnagrafe.email}" name="oldAnagrafe.email"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.email}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.email}"/>
								   <jsp:param value="email_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="email_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="email_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_email" name="oldfieldId"/>
								</jsp:include>	
						   </c:otherwise>
						</c:choose>   
						<spring-form:errors path="entity.email" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.pec"/></td>
					<td colspan="5">
						<c:choose>
							<c:when test="${anagrafe.entity.pec eq anagrafe.oldAnagrafe.pec || (empty anagrafe.entity.pec and empty  anagrafe.oldAnagrafe.pec)}">		
								<spring-form:input id="pec_id" path="entity.pec" size="35" maxlength="320"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssClass="bgred" id="pec_id" path="entity.pec" size="40" onclick="gda('pec_modificatoOverlay_id')"/>
								<input id="id_nuovo_pec" type="hidden" value="${anagrafe.oldAnagrafe.pec}" name="oldAnagrafe.pec"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.pec}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.pec}"/>
								   <jsp:param value="pec_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="pec_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="pec_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_pec" name="oldfieldId"/>
								</jsp:include>	
						   </c:otherwise>
						 </c:choose>
					</td>
				</tr>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td><fmt:message key="label.referente"/></td>
					<td colspan="5">
						<c:choose>
							<c:when test="${anagrafe.entity.referente eq anagrafe.oldAnagrafe.referente || (empty anagrafe.entity.referente and empty  anagrafe.oldAnagrafe.referente)}">		
								<spring-form:input id="referente_id" path="entity.referente" size="35"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssClass="bgred" id="referente_id" path="entity.referente" size="35" onclick="gda('referente_modificatoOverlay_id')"/>
								<input id="id_nuovo_referente" type="hidden" value="${anagrafe.oldAnagrafe.referente}" name="oldAnagrafe.referente"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.referente}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.referente}"/>
								   <jsp:param value="referente_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="referente_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="referente_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_referente" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>	
						<spring-form:errors path="entity.referente" cssClass="error"/>
					</td>
				</tr>
				<%	
				    // SERVE PER GESTIRE IL CHECKBOX,
				    // SE IL CAMPO INVIOMAIL SARà UGUALE A 1 DOVRà MOSTRALO SELEZIONATO
				    // SE IL CAMPO INVIOMAIL SARà UGUALE A 0 DOVRà MOSTRALO NON SELSZIONATO
				    String showCheckedInviomail="";
					if(((Boolean) request.getAttribute("inviomail")!=null))
					{
						if ((Boolean) request.getAttribute("inviomail")==true) {
					    	showCheckedInviomail = "checked='checked'";
	    				} else {
	    					showCheckedInviomail = "";
						}
					}
				%>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td><fmt:message key="label.flag_invio_mail"/></td>
					<td colspan="5">
						<c:if test="${anagrafe.entity.invioemail == anagrafe.oldAnagrafe.invioemail}">
						<spring-form:checkbox id="flag_invio_mail_id" path="entity.invioemail" value="true"/>
						</c:if>
						<c:if test="${anagrafe.entity.invioemail != anagrafe.oldAnagrafe.invioemail}">
					    <input type="checkbox" id="flag_invio_mail_id" <%=showCheckedInviomail%> style="outline-color:red;outline-style:solid;outline-width:thin;"  name="entity.invioemail"  value="true" onmouseover="javascript:gda('inviomail_dialog');"/>
					    <input type="hidden" id="id_nuovo_invio_mail"  name=""  value="${anagrafe.oldAnagrafe.invioemail}"/> 
						<div id="inviomail_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="inviomail_dialogInner" class="dialog">
									<table width="100%">
									<tr><td><fmt:message key="label.nuovo_valore_associato"/></td></tr>
									<tr>
									    <c:if test="${anagrafe.entity.invioemail==true}">
								    		<td  class="parametri"><fmt:message key="label.si"/></td>
								    	</c:if>
								    	<c:if test="${anagrafe.entity.invioemail==false}">
								    		<td  class="parametri"><fmt:message key="label.no"/></td>
								    	</c:if>
									</tr>
									<tr><td><fmt:message key="label.vecchio_valore_associato"/></td></tr>
									<tr>
										<c:if test="${anagrafe.oldAnagrafe.invioemail==true}">
									       <td  class="parametri"><fmt:message key="label.si"/></td>
									    </c:if>
									    <c:if test="${anagrafe.oldAnagrafe.invioemail==false}">
									       <td  class="parametri"><fmt:message key="label.no"/></td>
									    </c:if>
									</tr>
									<tr><td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe"/></td></tr>
					   				</table>
					   				<div id="functions">
										<ul>
											<li><a href="#" onclick="riprChkbox('flag_invio_mail_id','id_nuovo_invio_mail','inviomail_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accChkbox('flag_invio_mail_id','inviomail_dialog')"><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
						</c:if>	
						<spring-form:errors  path="entity.invioemail" cssClass="error"/>	
						<fmt:message key="anagrafe.label.descrizione_flag_invio_mail"/>
					</td>
				</tr>
				<%	
				    // SERVE PER GESTIRE IL CHECKBOX,
				    // SE IL CAMPO INVIOMAIL SARà UGUALE A true DOVRà MOSTRALO SELEZIONATO
				    // SE IL CAMPO INVIOMAIL SARà UGUALE A false DOVRà MOSTRALO NON SELSZIONATO
				    String showCheckedInviomailtecnico="";
					if(((Boolean) request.getAttribute("inviomailtec")!=null))
					{
						if ((Boolean) request.getAttribute("inviomailtec")==true) {
						    showCheckedInviomailtecnico = "checked='checked'";
	    				} else {
	    				showCheckedInviomailtecnico = "";
						}
					}
				%>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td><fmt:message key="label.flag_invio_mail_tecnico"/></td>
					<td colspan="5">
						<c:if test="${anagrafe.entity.invioemailtec == anagrafe.oldAnagrafe.invioemailtec}">
						<spring-form:checkbox id="flag_invio_mailtec_id" path="entity.invioemailtec" value="true"/>
						</c:if>
						<c:if test="${anagrafe.entity.invioemailtec != anagrafe.oldAnagrafe.invioemailtec}">
					    <input type="checkbox" id="flag_invio_mailtec_id" <%=showCheckedInviomailtecnico%> style="outline-color:red;outline-style:solid;outline-width:thin;"  name="entity.invioemailtec"  value="true" onmouseover="javascript:gda('inviomailtec_dialog');"/>
					    <input type="hidden" id="id_nuovo_invio_mailtec"  name=""  value="${anagrafe.oldAnagrafe.invioemailtec}"/>
						<div id="inviomailtec_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="inviomailtec_dialogInner" class="dialog">
									<table width="100%">
									<tr><td><fmt:message key="label.nuovo_valore_associato"/></td></tr>
									<tr>
									    <c:if test="${anagrafe.entity.invioemailtec==true}">
								    		<td  class="parametri"><fmt:message key="label.si"/></td>
								    	</c:if>
								    	<c:if test="${anagrafe.entity.invioemailtec==false}">
								    		<td  class="parametri"><fmt:message key="label.no"/></td>
								    	</c:if>
									</tr>
									<tr><td><fmt:message key="label.vecchio_valore_associato"/></td></tr>
									<tr>
										<c:if test="${anagrafe.oldAnagrafe.invioemailtec==true}">
									       <td  class="parametri"><fmt:message key="label.si"/></td>
									    </c:if>
									    <c:if test="${anagrafe.oldAnagrafe.invioemailtec==false}">
									       <td  class="parametri"><fmt:message key="label.no"/></td>
									    </c:if>
									</tr>
									<tr><td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe"/></td></tr>
					   				</table>
					   				<div id="functions">
										<ul>
											<li><a href="#" onclick="riprChkbox('flag_invio_mailtec_id','id_nuovo_invio_mailtec','inviomailtec_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accChkbox('flag_invio_mailtec_id','inviomailtec_dialog')"><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
						</c:if>	
						<spring-form:errors path="entity.invioemailtec" cssClass="error"/>
						<fmt:message key="anagrafe.label.descrizione_flag_invio_mail_tecnico"/>
					</td>
				</tr>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td><fmt:message key="label.note"/></td>
					<td colspan="5">
						<spring-form:textarea id="note_id" path="entity.note" cols="60" rows="5"/>
						<spring-form:errors path="entity.note" cssClass="error"/>
					</td>
				</tr>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">			
					<%
						String displayParametriFrontoffice = "display:none;";
						String styleParametriFrontoffice = "";
						//gestisce la visualizzazione della tabella Parametri Frontoffice
						if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ANAGRAFE_PARAMETRI_FRONTOFFICE)).equals("1")) {
						    displayParametriFrontoffice = "";
						    styleParametriFrontoffice="sezioneDatiMeno";
						} else {
						    displayParametriFrontoffice = "display:none;";
						    styleParametriFrontoffice="sezioneDatiPiu";
						}
					%>			
				    <tr class="titoloSezione">
						<td colspan="6">
							<a class="<%=styleParametriFrontoffice%>" id="id_link_parametrifrontoffice" href="javascript:showHidePanel('id_parametrifrontoffice_table', 'id_link_parametrifrontoffice', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_PARAMETRI_FRONTOFFICE %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione"/> <fmt:message key="label.parametri_frontoffice"/>">
								<label for="id_link_parametrifrontoffice"><fmt:message key="label.parametri_frontoffice"/></label>
							</a>
						</td>
					</tr>		
					<tr id="id_parametrifrontoffice_table" style="<%=displayParametriFrontoffice%>;">
						<td><fmt:message key="label.utente_tester"/></td>
						<td colspan="5">
							<spring-form:checkbox id="foUtentetester_id" path="entity.foUtentetester"/>
							<spring-form:errors path="entity.foUtentetester" cssClass="error"/>
							<fmt:message key="anagrafe.label.descrizione_foUtentetester"/>
						</td>
					</tr>
<c:if test="${CAN_UPDATE eq true }">
					<tr>
						<td><fmt:message key="label.utente_identificato"/></td>
						<td colspan="5">
							<c:choose>
								<c:when test="${anagrafe.entity.flagIdentificato eq true}">
									<b><fmt:message key="label.si"/></b>
									<c:set var="infoCittadinoId">Identificato dall'operatore ${anagrafe.entity.operatoreIdentificazione.responsabile} in data <fmt:formatDate value="${ anagrafe.entity.dataIdentificazione }" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>"/>.</c:set>
									<init:help idHelp="IdIdentificazione" text="${infoCittadinoId}"/>
								</c:when>
							<c:otherwise>
								<b><fmt:message key="label.no"/></b>
								<div id="functions" style="display:inline; float: right">
									<ul>
										<li><a href="javascript:javascript:historySet('${_urlback}','../anagrafe/${anagrafe.prefixPopup}identificazioneutente.htm','',document.inviodati)"><fmt:message key="button.identificazione_utente"/></a></li>
									</ul>
								</div>	
							</c:otherwise>
							</c:choose>											
						</td>
					</tr>					 
</c:if>
					<tr id="id_parametrifrontoffice_table" >
						<td><fmt:message key="label.flag_mail_verificata"/></td>
						<td colspan="5">
							<spring-form:checkbox id="flagMailVerificata_id" path="entity.flagMailVerificata"/>
							<spring-form:errors path="entity.flagMailVerificata" cssClass="error"/>
							<fmt:message key="label.flag_mail_verificata.help"/>
						</td>
					</tr>		
							
				</c:if>