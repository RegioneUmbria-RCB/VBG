<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:set var="personaGiuridicaval" value="<%= WebConstants.PERSONA_GIURIDICA %>" scope="page"/>
<c:set var="personaFisicaval" value="<%= WebConstants.PERSONA_FISICA %>" scope="page"/>
<c:set var="SESSO_MASCHIO" value="<%= WebConstants.MASCHIO %>" scope="page"/>
<c:set var="SESSO_FEMMINA" value="<%= WebConstants.FEMMINA %>" scope="page"/>

<div id="subcontent">		
		<fieldset><legend><fmt:message key="label.periodo_validita"/></legend>
		<div class="parametriDiv">
				<c:if test="${anagrafestorico.datafinevalidita ne null && anagrafestorico.datainiziovalidita ne null}">
				<div class="etichetta">
					<div><fmt:message key="label.data_inizio" />:</div>
					<div><fmt:message key="label.data_fine" />:</div>
				</div>
				<div class="parametro">
					<c:if test="${anagrafestorico.datainiziovalidita eq null}">
						<div>&nbsp;</div>
					</c:if>
					<c:if test="${anagrafestorico.datafinevalidita ne null}">
						<div><fmt:formatDate value="${anagrafestorico.datainiziovalidita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></div>
					</c:if>
					<c:if test="${anagrafestorico.datafinevalidita ne null}">
						<div><fmt:formatDate value="${anagrafestorico.datafinevalidita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></div>
				    </c:if>
				    <c:if test="${anagrafestorico.datafinevalidita eq null}">
						<div><fmt:message key="label.in_corso" /></div>
				    </c:if>
				</div>
				</c:if>
				<c:if test="${anagrafestorico.datainiziovalidita eq null && anagrafestorico.datafinevalidita ne null}">
				<div class="etichetta">
					<div><fmt:message key="label.data_inizio" />:</div>
					<div><fmt:message key="label.data_fine" />:</div>
				</div>
				<div class="parametro">
					<div>&nbsp;</div>
					<div><fmt:formatDate value="${anagrafestorico.datafinevalidita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></div>  
				</div>
				</c:if>
				<c:if test="${anagrafestorico.datafinevalidita eq null && anagrafestorico.datafinevalidita eq null}">
					<div class="parametro">
						<div><fmt:message key="label.in_corso" /></div>
					</div>
				</c:if>
				
			</div>		 
		</fieldset>
		<br clear="clear" />
		
		<table border="0"  cellpadding="2" cellspacing="0" class="table" width="100%">
			<tr class="titoloSezione">
				<td colspan="6">
					<fmt:message key="anagrafe.label.dati_generali" />
					<init:help idHelp="helpAnagrafestoricoLegenda" textKey="help.anagrafestorico.legenda"/></td>
				</td>
			</tr>
			<tr>
				<td width="30%" class="parametri">
					<fmt:message key="anagrafe.label.tipo_anagrafe" />&nbsp;:
				</td>
				<td colspan="5">
				   
				   <c:if test="${anagrafestorico.tipoanagrafe eq personaFisicaval}">
				     <fmt:message key="label.persona_fisica" />				     
				   </c:if>
				   <c:if test="${anagrafestorico.tipoanagrafe eq personaGiuridicaval}">
				     <fmt:message key="label.persona_giuridica" />
				   </c:if>
				   
				   <c:if test="${anagrafestorico.tipoanagrafe ne anagrafeattuale.tipoanagrafe}"	>
					   	<span class="anagrafeDiffValue">
					   		<c:if test="${anagrafeattuale.tipoanagrafe eq personaFisicaval}">
						     <fmt:message key="label.persona_fisica" />				     
						   </c:if>
						   <c:if test="${anagrafeattuale.tipoanagrafe eq personaGiuridicaval}">
						     <fmt:message key="label.persona_giuridica" />
						   </c:if>
				   		</span>
				   </c:if>
				</td>
			</tr>
			<c:if test="${anagrafestorico.tipoanagrafe eq personaFisicaval }">
				<tr>
					<td class="parametri"><fmt:message key="label.cognome" />&nbsp;:</td>
						<td>
							${anagrafestorico.nominativo}
							<c:if test="${anagrafestorico.nominativo ne anagrafeattuale.nominativo}">
					   			<span class="anagrafeDiffValue">${anagrafeattuale.nominativo}</span>
					   		</c:if>					   		
						</td>
					<td class="parametri"><fmt:message key="label.nome" />&nbsp;:</td>
						<td colspan="3">
							${anagrafestorico.nome}
							<c:if test="${anagrafestorico.nome ne anagrafeattuale.nome}">
					   			<span class="anagrafeDiffValue">${anagrafeattuale.nome}</span>
					   		</c:if>	
						</td>
				</tr>
			</c:if>
			<c:if test="${anagrafestorico.tipoanagrafe eq personaGiuridicaval}">
				<tr>
					<td class="parametri"><fmt:message key="label.ragione_sociale" />&nbsp;:</td>
					<td>					
						${anagrafestorico.nominativo}
						<c:if test="${anagrafestorico.nominativo ne anagrafeattuale.nominativo}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.nominativo}</span>
				   		</c:if>		
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.forma_giuridica" />&nbsp;:</td>
					<td colspan="5">
						${anagrafestorico.formagiuridica.formagiuridica}
						<c:if test="${anagrafestorico.formagiuridica.formagiuridica ne anagrafeattuale.formagiuridica.formagiuridica}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.formagiuridica.formagiuridica}</span>
				   		</c:if>		
					</td>
				</tr>
			</c:if>
			<c:if test="${anagrafestorico.tipoanagrafe eq personaFisicaval}">
				<tr>
					<td class="parametri"><fmt:message key="label.titolo" />&nbsp;:</td>
					<td colspan="5">
						${anagrafestorico.titolo.titolo}
						<c:if test="${anagrafestorico.titolo.titolo ne anagrafeattuale.titolo.titolo}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.titolo.titolo}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.sesso" />&nbsp;:</td>
					<td colspan="5" >
						
						<c:if test="${anagrafestorico.sesso eq SESSO_MASCHIO}"><fmt:message key="label.maschio" /></c:if>
						<c:if test="${anagrafestorico.sesso eq SESSO_FEMMINA}"><fmt:message key="label.femmina" /></c:if>
						<c:if test="${anagrafestorico.sesso ne anagrafeattuale.sesso}">
				   			<span class="anagrafeDiffValue">
								<c:if test="${anagrafeattuale.sesso eq SESSO_MASCHIO}"><fmt:message key="label.maschio" /></c:if>
								<c:if test="${anagrafeattuale.sesso eq SESSO_FEMMINA}"><fmt:message key="label.femmina" /></c:if>				   			
							</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.cittadinanza" />&nbsp;:</td>
					<td colspan="5" >
						${anagrafestorico.cittadinanza.cittadinanza}
						<c:if test="${anagrafestorico.cittadinanza.cittadinanza ne anagrafeattuale.cittadinanza.cittadinanza}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.cittadinanza.cittadinanza}</span>
				   		</c:if>
					</td>
				</tr>
			</c:if>
				<tr>
					<td class="parametri"><fmt:message key="label.tipo_soggetto" />&nbsp;:</td>
					<td>
					
					 <c:if test="${anagrafestorico.tipologia==-1}"><fmt:message key="label.tecnico" /></c:if>
					 <c:if test="${anagrafestorico.tipologia==0}"><fmt:message key="label.non_tecnico" /></c:if>
					 <c:if test="${anagrafestorico.tipologia ne anagrafeattuale.tipologia}">
				   			<span class="anagrafeDiffValue">
								<c:if test="${anagrafeattuale.tipologia==-1}"><fmt:message key="label.tecnico" /></c:if>
								<c:if test="${anagrafeattuale.tipologia==0}"><fmt:message key="label.non_tecnico" /></c:if>
				   			</span>
				   	</c:if>
					 
					 </td>
				</tr> 
		    <c:if test="${anagrafestorico.tipoanagrafe eq personaFisicaval}">
				<tr class="titoloSezione">
					<td colspan="6"><fmt:message key="label.residenza"/></td>
				</tr>
			</c:if>
			<c:if test="${anagrafestorico.tipoanagrafe eq personaGiuridicaval}">
				<tr class="titoloSezione">
					<td colspan="6"><fmt:message key="label.sede_legale"/>&nbsp;:</td>
				</tr>
			</c:if>
				<tr>
					<td class="parametri"><fmt:message key="label.indirizzo" />&nbsp;:</td>
					<td colspan="5">	
						${anagrafestorico.indirizzo}
						<c:if test="${anagrafestorico.indirizzo ne anagrafeattuale.indirizzo}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.indirizzo}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.localita" />&nbsp;:</td>
					<td>
						${anagrafestorico.citta}
						<c:if test="${anagrafestorico.citta ne anagrafeattuale.citta}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.citta}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.cap" />&nbsp;:</td>
					<td colspan="3">
						${anagrafestorico.cap}
						<c:if test="${anagrafestorico.cap ne anagrafeattuale.cap}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.cap}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.comune" />&nbsp;:</td>
					<td>
						${anagrafestorico.comuneResidenza.descrizioneEstesa}
						<c:if test="${anagrafestorico.comuneResidenza.descrizioneEstesa ne anagrafeattuale.comuneResidenza.descrizioneEstesa}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.comuneResidenza.descrizioneEstesa}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.sigla_provincia" />&nbsp;:</td>
					<td colspan="3">
						${anagrafestorico.provincia}
						<c:if test="${anagrafestorico.provincia ne anagrafeattuale.provincia}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.provincia}</span>
				   		</c:if>
					</td>
				</tr>
 				<tr class="titoloSezione">
					<td colspan="6"><fmt:message key="label.corrispondenza"/></td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.indirizzo" />&nbsp;:</td>
					<td colspan="5">
						${anagrafestorico.indirizzocorrispondenza}
						<c:if test="${anagrafestorico.indirizzocorrispondenza ne anagrafeattuale.indirizzocorrispondenza}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.indirizzocorrispondenza}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.localita" />&nbsp;:</td>
					<td>
						${anagrafestorico.cittacorrispondenza}
						<c:if test="${anagrafestorico.cittacorrispondenza ne anagrafeattuale.cittacorrispondenza}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.cittacorrispondenza}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.cap" />&nbsp;:</td>
					<td colspan="3">
						${anagrafestorico.capcorrispondenza}
						<c:if test="${anagrafestorico.capcorrispondenza ne anagrafeattuale.capcorrispondenza}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.capcorrispondenza}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.comune" />&nbsp;:</td>
					<td>
						${anagrafestorico.comunecorrispondenza.descrizioneEstesa}
						<c:if test="${anagrafestorico.comunecorrispondenza.descrizioneEstesa ne anagrafeattuale.comunecorrispondenza.descrizioneEstesa}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.comunecorrispondenza.descrizioneEstesa}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.sigla_provincia" />&nbsp;:</td>
					<td colspan="3">
						${anagrafestorico.provinciacorrispondenza}
						<c:if test="${anagrafestorico.provinciacorrispondenza ne anagrafeattuale.provinciacorrispondenza}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.provinciacorrispondenza}</span>
				   		</c:if>
					</td>
				</tr>
				
		    <c:if test="${anagrafestorico.tipoanagrafe eq personaFisicaval}">
			    <tr class="titoloSezione">
					<td colspan="6"><fmt:message key="label.dati_nascita_e_codice_fiscale"/></td>
				</tr>
			</c:if>
			<c:if test="${anagrafestorico.tipoanagrafe eq personaGiuridicaval}">
				<tr class="titoloSezione">
					<td colspan="6"><fmt:message key="label.dati_azienda"/></td>
				</tr>
			</c:if>
			<c:if test="${anagrafestorico.tipoanagrafe eq personaGiuridicaval}">
				<tr>
			    	<td class="parametri"><fmt:message key="label.data_costituzione" />&nbsp;:</td>
					<td colspan="6">
						<fmt:formatDate value="${anagrafestorico.datanominativo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
						<c:if test="${anagrafestorico.datanominativo != anagrafeattuale.datanominativo}">
				   			<span class="anagrafeDiffValue"><fmt:formatDate value="${anagrafeattuale.datanominativo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></span>
				   		</c:if>
					</td>
				</tr>
			</c:if>
			<c:if test="${anagrafestorico.tipoanagrafe eq personaFisicaval}">
				<tr>	
					<td class="parametri"><fmt:message key="label.comune_nascita" />&nbsp;:</td>
					<td>
						${anagrafestorico.comuneNascita.descrizioneEstesa}
						<c:if test="${anagrafestorico.comuneNascita.descrizioneEstesa ne anagrafeattuale.comuneNascita.descrizioneEstesa}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.comuneNascita.descrizioneEstesa}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.data_nascita" />&nbsp;:</td>
					<td colspan="3">
						<fmt:formatDate value="${anagrafestorico.datanascita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
						<c:if test="${anagrafestorico.datanascita != anagrafeattuale.datanascita}">
				   			<span class="anagrafeDiffValue"><fmt:formatDate value="${anagrafeattuale.datanascita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></span>
				   		</c:if>
					</td>
				</tr>
			</c:if>
				<tr>
					<td class="parametri"><fmt:message key="label.codice_fiscale" />&nbsp;:</td>
					<td>
						${anagrafestorico.codicefiscale}
						<c:if test="${anagrafestorico.codicefiscale ne anagrafeattuale.codicefiscale}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.codicefiscale}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>		
					<td class="parametri"><fmt:message key="label.partita_iva" />&nbsp;:</td>
					<td>
						${anagrafestorico.partitaiva}
						<c:if test="${anagrafestorico.partitaiva ne anagrafeattuale.partitaiva}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.partitaiva}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="anagrafe.label.reg_ditte" />&nbsp;:</td>
					<td>
						${anagrafestorico.regditte}
						<c:if test="${anagrafestorico.regditte ne anagrafeattuale.regditte}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.regditte}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.data" />&nbsp;:</td>
					<td colspan="3">
						<fmt:formatDate value="${anagrafestorico.dataregditte}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
						<c:if test="${anagrafestorico.dataregditte != anagrafeattuale.dataregditte}">
				   			<span class="anagrafeDiffValue"><fmt:formatDate value="${anagrafeattuale.dataregditte}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.comune_reg_ditte" />&nbsp;:</td>
					<td colspan="5">
						${anagrafestorico.comunecomregditte.descrizioneEstesa}
						<c:if test="${anagrafestorico.comunecomregditte.descrizioneEstesa ne anagrafeattuale.comunecomregditte.descrizioneEstesa}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.comunecomregditte.descrizioneEstesa}</span>
				   		</c:if>
					</td>
				</tr>
			
		
			<c:if test="${anagrafestorico.tipoanagrafe eq personaGiuridicaval}">
				<tr>
					<td class="parametri"><fmt:message key="label.reg_trib" />&nbsp;:</td>
					<td>
						${anagrafestorico.regtrib}
						<c:if test="${anagrafestorico.regtrib ne anagrafeattuale.regtrib}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.regtrib}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.data"/>&nbsp;:</td>
					<td colspan="3">
						<fmt:formatDate value="${anagrafestorico.dataregtrib}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
						<c:if test="${anagrafestorico.dataregtrib != anagrafeattuale.dataregtrib}">
				   			<span class="anagrafeDiffValue"><fmt:formatDate value="${anagrafeattuale.dataregtrib}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.comune_reg_trib" />&nbsp;:</td>
					<td colspan="5">
						${anagrafestorico.comuneregtrib.descrizioneEstesa}
						<c:if test="${anagrafestorico.comuneregtrib.descrizioneEstesa ne anagrafeattuale.comuneregtrib.descrizioneEstesa}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.comuneregtrib.descrizioneEstesa}</span>
				   		</c:if>
					</td>
				</tr>	
				<tr>
					<td class="parametri"><fmt:message key="label.provincia_area" />&nbsp;:</td>
					<td colspan="5">
						${anagrafestorico.provinciarea}
						<c:if test="${anagrafestorico.provinciarea ne anagrafeattuale.provinciarea}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.provinciarea}</span>
				   		</c:if>
					</td>
				</tr>	
				<tr>
					<td class="parametri"><fmt:message key="label.numero_iscrizione_rea" />&nbsp;:</td>
					<td colspan="1">
						${anagrafestorico.numiscrrea}
						<c:if test="${anagrafestorico.numiscrrea ne anagrafeattuale.numiscrrea}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.numiscrrea}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.data"/>&nbsp;:</td>
					<td colspan="3">
						<fmt:formatDate value="${anagrafestorico.dataiscrrea}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
						<c:if test="${anagrafestorico.dataiscrrea != anagrafeattuale.dataiscrrea}">
				   			<span class="anagrafeDiffValue"><fmt:formatDate value="${anagrafeattuale.dataiscrrea}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></span>
				   		</c:if>
					</td>
				</tr>
				
				
				<tr>
					<td class="parametri"><fmt:message key="label.numero_matricola_inail" />&nbsp;:</td>
					<td colspan="1">
						${anagrafestorico.inailMatricola}
						<c:if test="${anagrafestorico.inailMatricola ne anagrafeattuale.inailMatricola}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.inailMatricola}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.sede_iscrizione_inail"/>&nbsp;:</td>
					<td colspan="3">
						${anagrafestorico.sedeInail.descrizioneEstesa}
						<c:if test="${anagrafestorico.sedeInail.descrizioneEstesa != anagrafeattuale.sedeInail.descrizioneEstesa}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.sedeInail.descrizioneEstesa}</span>
				   		</c:if>
					</td>
				</tr>
				
				
				<tr>
					<td class="parametri"><fmt:message key="label.numero_matricola_inps" />&nbsp;:</td>
					<td colspan="1">
						${anagrafestorico.inpsMatricola}
						<c:if test="${anagrafestorico.inpsMatricola ne anagrafeattuale.inpsMatricola}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.inpsMatricola}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.sede_iscrizione_inps"/>&nbsp;:</td>
					<td colspan="3">
						${anagrafestorico.sedeInps.descrizioneEstesa}
						<c:if test="${anagrafestorico.sedeInps.descrizioneEstesa != anagrafeattuale.sedeInps.descrizioneEstesa}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.sedeInps.descrizioneEstesa}</span>
				   		</c:if>
					</td>
				</tr>
			</c:if>
			    <tr class="titoloSezione">
					<td colspan="6">
						<fmt:message key="label.altri_dati"/></td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.telefono" />&nbsp;:</td>
					<td>
						${anagrafestorico.telefono}
						<c:if test="${anagrafestorico.telefono ne anagrafeattuale.telefono}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.telefono}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.cellulare" />&nbsp;:</td>
					<td colspan="3">
						${anagrafestorico.telefonocellulare}
						<c:if test="${anagrafestorico.telefonocellulare ne anagrafeattuale.telefonocellulare}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.telefonocellulare}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.fax" />&nbsp;:</td>
					<td>
						${anagrafestorico.fax}
						<c:if test="${anagrafestorico.fax ne anagrafeattuale.fax}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.fax}</span>
				   		</c:if>
					</td>
					<td class="parametri"><fmt:message key="label.email" />&nbsp;:</td>
					<td colspan="3">
						${anagrafestorico.email}
						<c:if test="${anagrafestorico.email ne anagrafeattuale.email}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.email}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.pec" />&nbsp;:</td>
					<td colspan="5">
						${anagrafestorico.pec}
						<c:if test="${anagrafestorico.pec ne anagrafeattuale.pec}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.pec}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.referente" />&nbsp;:</td>
					<td colspan="5">
						${anagrafestorico.referente}
						<c:if test="${anagrafestorico.referente ne anagrafeattuale.referente}">
				   			<span class="anagrafeDiffValue">${anagrafeattuale.referente}</span>
				   		</c:if>
					</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.flag_invio_mail" />&nbsp;:</td>
					<td colspan="5">
						<c:if test="${anagrafestorico.invioemail==true}"><fmt:message key="label.si" /></c:if>
				    	<c:if test="${anagrafestorico.invioemail==false}"><fmt:message key="label.no" /></c:if>
				    	<c:if test="${anagrafestorico.invioemail != anagrafeattuale.invioemail}">
				   			<span class="anagrafeDiffValue">
						   		<c:if test="${anagrafeattuale.invioemail==true}"><fmt:message key="label.si" /></c:if>
						    	<c:if test="${anagrafeattuale.invioemail==false}"><fmt:message key="label.no" /></c:if>
							</span>
				   		</c:if>
				    </td>
				</tr>
				
				<tr>
					<td class="parametri"><fmt:message key="label.flag_invio_mail_tecnico" />&nbsp;:</td>
					<td colspan="5">
						<c:if test="${anagrafestorico.invioemailtec==true}"><fmt:message key="label.si" /></c:if>
						<c:if test="${anagrafestorico.invioemailtec==false}"><fmt:message key="label.no" /></c:if>
						<c:if test="${anagrafestorico.invioemailtec ne anagrafeattuale.invioemailtec}">
				   			<span class="anagrafeDiffValue">
				   				<c:if test="${anagrafestorico.invioemailtec==true}"><fmt:message key="label.si" /></c:if>
								<c:if test="${anagrafestorico.invioemailtec==false}"><fmt:message key="label.no" /></c:if>
							</span>
				   		</c:if>
					</td>
				</tr>
				<%-- 
				<tr>
					<td class="parametri"><fmt:message key="label.note" /></td>
					<td>${anagrafestorico.note}</td>
				</tr>
				<tr>
					<td class="parametri"><fmt:message key="label.flagDisabilitato" /></td>
					<td colspan="5">
						<c:if test="${anagrafestorico.flagDisabilitato==1}"><fmt:message key="label.si" /></c:if>
						<c:if test="${anagrafestorico.flagDisabilitato==0}"><fmt:message key="label.no" /></c:if>
					</td>
				</tr>
				--%>
		</table>
	</div>


