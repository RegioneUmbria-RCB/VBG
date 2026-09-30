<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<c:set var="table_color" scope="page">#f4e541</c:set>
<c:if test="${dbInfoClient.esistentePOD eq true and dbInfoClient.attivoPOD eq true}">
	<c:set var="table_color">#d7ffa3</c:set>
</c:if>
<div class="jmesa" style="background-color: ${table_color}">
	<table class="table" style="background-color: ${table_color}" id="POD_TABLE">
		<thead>

		</thead>
		<tbody>
		<c:if test="${dbInfoClient.esistentePOD eq true}">
			<tr>
				<td>Cliente</td>
				<td><b>${anagrafe.nominativo } ${anagrafe.nome}</b></td>
				<td>Codice fiscale</td>
				<td><b>${anagrafe.codicefiscale }</b></td>
			</tr>
		</c:if>
			<tr>
				<td>Codice POD</td>
				<td><b>${dbInfoClient.codicePOD }</b></td>
				<td>Codice cliente finale</td>
				<td><b>${dbInfoClient.codiceCliente }</b></td>
			</tr>
			<tr>
				<td>POD esistente</td>
				<td><b>
				<c:choose>
					<c:when test="${dbInfoClient.esistentePOD eq true}">
					<font style="color: green; font-size: 1.5em">Si</font>
					</c:when>
					<c:otherwise><font style="color: red; font-size: 1.5em">No</font></c:otherwise>
				</c:choose>
				</b></td>
				<td>POD attivo</td>
				<td><b><c:choose>
					<c:when test="${dbInfoClient.attivoPOD eq true}"><font style="color: green; font-size: 1.5em">Si</font>
					</c:when>
					<c:otherwise><font style="color: red; font-size: 1.5em">No</font></c:otherwise>
				</c:choose></b></td>
			</tr>
			<c:if test="${dbInfoClient.esistentePOD eq true}">
			<tr>
				<td>Indirizzo fornitura</td>
				<td><b>${dbInfoClient.indirizzoFornitura }</b></td>
				<td>Civico</td>
				<td><b>${dbInfoClient.civicoFornitura }</b></td>
			</tr>
			<tr>
				<td>CAP fornitura</td>
				<td><b>${dbInfoClient.capFornitura }</b></td>
				<td>Citta' fornitura</td>
				<td><b>${dbInfoClient.cittaFornitura }</b></td>
			</tr>
			<tr>
				<td>Provincia fornitura</td>
				<td colspan="3"><b>${dbInfoClient.provinciaFornitura }</b></td>
			</tr>
			<tr>
				<td>Tipo tariffa</td>
				<td colspan="3"><b>${dbInfoClient.tipoTariffa }</b></td>
			</tr>
			<tr>
				<td>Potenza contrattuale</td>
				<td><b>${dbInfoClient.potenzaContrattuale }</b></td>
				<td>Potenza pagata</td>
				<td><b>${dbInfoClient.potenzaPagata }</b></td>
			</tr>
			<tr>
				<td>Potenza disponibile</td>
				<td colspan="3"><b>${dbInfoClient.potenzaDisponibile }</b></td>
			</tr>
			<tr>
				<td>Servizio</td>
				<td><b>${dbInfoClient.servizio }</b></td>
				<td>Tipologia utenza</td>
				<td><b>${dbInfoClient.tipologiaUtenza }</b></td>
			</tr>
				<input type="hidden" id="anagrafe_pod_id_hidden" value="${anagrafe.id.codice }"/>
				<input type="hidden" id="anagrafe_pod_desc_hidden" value="${anagrafe.descrizioneRichiedente}"/>
				<c:if test="${not empty distributore}">
				
					<tr>
						<td>Distributore</td>
						<td colspan="2"><b>${distributore.descrizioneRichiedente}</b></td>
					</tr>				
					<input type="hidden" id="distributore_pod_id_hidden" value="${distributore.id.codice }"/>
					<input type="hidden" id="distributore_pod_desc" value="${distributore.descrizioneRichiedente}"/>
				</c:if>
				
			</c:if>
		</tbody>
	</table>
	
	
</div>
<c:if test="${dbInfoClient.esistentePOD eq true}">
	<div id="functions">
		<ul>
		<c:if test="${param.isView eq false}">
			<li><a href="javascript:void 0" onclick="inserisciDatiPOD(true);"><fmt:message key="button.ok" /></a></li>
		</c:if>	
			<li><a href="javascript:void 0" onclick="inserisciDatiPOD(false);"><fmt:message key="button.annulla" /></a></li>
		</ul>
	</div>
</c:if>
<c:if test="${dbInfoClient.esistentePOD eq false}">
	<div id="functions">
		<ul>
			<li><a href="javascript:void 0" onclick="inserisciDatiPOD(false);"><fmt:message key="button.annulla" /></a></li>
		</ul>
	</div>
</c:if>
