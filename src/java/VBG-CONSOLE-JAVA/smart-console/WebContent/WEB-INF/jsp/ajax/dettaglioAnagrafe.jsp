<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:set var="personaGiuridicaval" value="<%= WebConstants.PERSONA_GIURIDICA %>" scope="page"/>
<c:set var="personaFisicaval" value="<%= WebConstants.PERSONA_FISICA %>" scope="page"/>
<c:set var="SESSO_MASCHIO" value="<%= WebConstants.MASCHIO %>" scope="page"/>
<c:set var="SESSO_FEMMINA" value="<%= WebConstants.FEMMINA %>" scope="page"/>
	<div id="subcontent">		

		<table border="0"  cellpadding="2" cellspacing="0" class="table" width="100%">
			<tr class="titoloSezione">
				<td colspan="6">
					<fmt:message key="anagrafe.label.dati_generali" />
				</td>
			</tr>
			<tr>
				<td width="30%" class="parametri">
					<fmt:message key="anagrafe.label.tipo_anagrafe" />&nbsp;:
				</td>
				<td colspan="5">
				   <c:if test="${anagrafe.tipoanagrafe eq personaFisicaval}">
				     <fmt:message key="label.persona_fisica" />
				     
				   </c:if>
				   <c:if test="${anagrafe.tipoanagrafe eq personaGiuridicaval}">
				     <fmt:message key="label.persona_giuridica" />
				   </c:if>
				</td>
			</tr>
			<c:if test="${anagrafe.tipoanagrafe eq personaFisicaval }">
				<tr>
					<td class="parametri"><fmt:message key="label.cognome" />&nbsp;:</td>
					<td>${anagrafe.nominativo}</td>
					<td class="parametri"><fmt:message key="label.nome" />&nbsp;:</td>
					<td colspan="3">${anagrafe.nome}</td>
				</tr>
			</c:if>
			<c:if test="${anagrafe.tipoanagrafe eq personaGiuridicaval}">
				<tr>
					<td class="parametri"><fmt:message key="label.ragione_sociale" />&nbsp;:</td>
					<td>${anagrafe.nominativo}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.forma_giuridica" />&nbsp;:</td>
					<td colspan="5">${anagrafe.formagiuridica.formagiuridica}</td>
				</tr>
			</c:if>
			<c:if test="${anagrafe.tipoanagrafe eq personaFisicaval}">
				<tr>
					<td class="parametri"><fmt:message key="label.titolo" />&nbsp;:</td>
					<td colspan="5" >${anagrafe.titolo.titolo}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.sesso" />&nbsp;:</td>
					<td colspan="5" >
						<c:if test="${anagrafe.sesso eq SESSO_MASCHIO}"><fmt:message key="label.maschio" /></c:if>
						<c:if test="${anagrafe.sesso eq SESSO_FEMMINA}"><fmt:message key="label.femmina" /></c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.cittadinanza" />&nbsp;:</td>
					<td colspan="5" >${anagrafe.cittadinanza.cittadinanza}</td>
				</tr>
			</c:if>
				<tr>
					<td class="parametri"><fmt:message key="label.tipo_soggetto" />&nbsp;:</td>
					 <c:if test="${anagrafe.tipologia==-1}"><td><fmt:message key="label.tecnico" /></td></c:if>
					 <c:if test="${anagrafe.tipologia==0}"><td><fmt:message key="label.non_tecnico" /></td></c:if>
				</tr> 
		    <c:if test="${anagrafe.tipoanagrafe eq personaFisicaval}">
				<tr class="titoloSezione">
					<td colspan="6"><fmt:message key="label.residenza"/></td>
				</tr>
			</c:if>
			<c:if test="${anagrafe.tipoanagrafe eq personaGiuridicaval}">
				<tr class="titoloSezione">
					<td colspan="6"><fmt:message key="label.sede_legale"/></td>
				</tr>
			</c:if>
				<tr>
					<td class="parametri"><fmt:message key="label.indirizzo" />&nbsp;:</td>
					<td colspan="5">${anagrafe.indirizzo}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.localita" />&nbsp;:</td>
					<td>${anagrafe.citta}</td>
					<td class="parametri"><fmt:message key="label.cap" />&nbsp;:</td>
					<td colspan="3">${anagrafe.cap}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.comune" />&nbsp;:</td>
					<td>${anagrafe.comuneResidenza.descrizioneEstesa}</td>
					<td class="parametri"><fmt:message key="label.sigla_provincia" />&nbsp;:</td>
					<td colspan="3">${anagrafe.provincia}</td>
				</tr>
 				<tr class="titoloSezione">
					<td colspan="6"><fmt:message key="label.corrispondenza"/></td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.indirizzo" />&nbsp;:</td>
					<td colspan="5">${anagrafe.indirizzocorrispondenza}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.localita" />&nbsp;:</td>
					<td>${anagrafe.cittacorrispondenza}</td>
					<td class="parametri"><fmt:message key="label.cap" />&nbsp;:</td>
					<td colspan="3">${anagrafe.capcorrispondenza}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.comune" />&nbsp;:</td>
					<td>${anagrafe.comunecorrispondenza.descrizioneEstesa}</td>
					<td class="parametri"><fmt:message key="label.sigla_provincia" />&nbsp;:</td>
					<td colspan="3">${anagrafe.provinciacorrispondenza}</td>
				</tr>
				
		    <c:if test="${anagrafe.tipoanagrafe eq personaFisicaval}">
			    <tr class="titoloSezione">
					<td colspan="6"><fmt:message key="label.dati_nascita_e_codice_fiscale"/></td>
				</tr>
			</c:if>
			<c:if test="${anagrafe.tipoanagrafe eq personaGiuridicaval}">
				<tr class="titoloSezione">
					<td colspan="6"><fmt:message key="label.dati_azienda"/></td>
				</tr>
			</c:if>
			<c:if test="${anagrafe.tipoanagrafe eq personaGiuridicaval}">
				<tr>
			    	<td class="parametri"><fmt:message key="label.data_costituzione" />&nbsp;:</td>
					<td colspan="6"><fmt:formatDate value="${anagrafe.datanominativo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
				</tr>
			</c:if>
			<c:if test="${anagrafe.tipoanagrafe eq personaFisicaval}">
				<tr>	
					<td class="parametri"><fmt:message key="label.comune_nascita" />&nbsp;:</td>
					<td> ${anagrafe.comuneNascita.descrizioneEstesa}</td>
					<td class="parametri"><fmt:message key="label.data_nascita" />&nbsp;:</td>
					<td colspan="3"><fmt:formatDate value="${anagrafe.datanascita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
				</tr>
			</c:if>
				<tr>
					<td class="parametri"><fmt:message key="label.codice_fiscale" />&nbsp;:</td>
					<td>${anagrafe.codicefiscale}</td>
				</tr>
				<tr>		
					<td class="parametri"><fmt:message key="label.partita_iva" />&nbsp;:</td>
					<td>${anagrafe.partitaiva}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="anagrafe.label.reg_ditte" />&nbsp;:</td>
					<td>${anagrafe.regditte}</td>
					<td class="parametri"><fmt:message key="label.data" />&nbsp;:</td>
					<td colspan="3"><fmt:formatDate value="${anagrafe.dataregditte}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.comune_reg_ditte" />&nbsp;:</td>
					<td colspan="5">${anagrafe.comunecomregditte.descrizioneEstesa}</td>
				</tr>
			
		
			<c:if test="${anagrafe.tipoanagrafe eq personaGiuridicaval}">
				<tr>
					<td class="parametri"><fmt:message key="label.reg_trib" />&nbsp;:</td>
					<td>${anagraferegtrib}</td>
					<td class="parametri"><fmt:message key="label.data"/>&nbsp;:</td>
					<td colspan="3"><fmt:formatDate value="${anagrafe.dataregtrib}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.comune_reg_trib" />&nbsp;:</td>
					<td colspan="5">${anagrafe.comuneregtrib.descrizioneEstesa}</td>
				</tr>	
				<tr>
					<td class="parametri"><fmt:message key="label.provincia_area" />&nbsp;:</td>
					<td colspan="5">${anagrafe.provinciarea}</td>
				</tr>	
				<tr>
					<td class="parametri"><fmt:message key="label.numero_iscrizione_rea" />&nbsp;:</td>
					<td colspan="1">${anagrafe.numiscrrea}</td>
					<td class="parametri"><fmt:message key="label.data"/>&nbsp;:</td>
					<td valign="top" colspan="3"><fmt:formatDate value="${anagrafe.dataiscrrea}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
				</tr>
			</c:if>
			
			    <tr class="titoloSezione">
					<td colspan="6">
						<fmt:message key="label.altri_dati"/></td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.telefono" />&nbsp;:</td>
					<td>${anagrafe.telefono}</td>
					<td class="parametri"><fmt:message key="label.cellulare" />&nbsp;:</td>
					<td colspan="3">${anagrafe.telefonocellulare}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.fax" />&nbsp;:</td>
					<td>${anagrafe.fax}</td>
					<td class="parametri"><fmt:message key="label.email" />&nbsp;:</td>
					<td colspan="3">${anagrafe.email}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.pec" />&nbsp;:</td>
					<td colspan="5">${anagrafe.pec}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.referente" />&nbsp;:</td>
					<td colspan="5">${anagrafe.referente}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.flag_invio_mail" />&nbsp;:</td>
					<td colspan="5">
						<c:if test="${anagrafe.invioemail==true}"><fmt:message key="label.si" /></c:if>
				    	<c:if test="${anagrafe.invioemail==false}"><fmt:message key="label.no" /></c:if>
				    </td>
				</tr>
				
				<tr>
					<td class="parametri"><fmt:message key="label.flag_invio_mail_tecnico" />&nbsp;:</td>
					<td colspan="5">
						<c:if test="${anagrafe.invioemailtec==true}"><fmt:message key="label.si" /></c:if>
						<c:if test="${anagrafe.invioemailtec==false}"><fmt:message key="label.no" /></c:if>
					</td>
				</tr>
				<%-- 
				<tr>
					<td class="parametri"><fmt:message key="label.note" /></td>
					<td>${anagrafe.note}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.flagDisabilitato" /></td>
					<td colspan="5">
						<c:if test="${anagrafe.flagDisabilitato==1}"><fmt:message key="label.si" /></c:if>
						<c:if test="${anagrafe.flagDisabilitato==0}"><fmt:message key="label.no" /></c:if>
					</td>
				</tr>
				--%>
		</table>
	</div>
