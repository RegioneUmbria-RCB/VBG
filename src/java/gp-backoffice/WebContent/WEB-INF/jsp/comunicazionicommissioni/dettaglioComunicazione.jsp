<%@ include file="../includes/taglibs.jsp" %>
<style>
.elabora-riga{
	display: inline-block;
    background-color: gainsboro;
    padding: var(--half-padding);
    border-radius: var(--half-padding);
}

.elabora-riga>div{
	display: inline-block;
}

.elabora-riga.attivo{
	background-color: var(--color-warning);
}

.elabora-riga.attivo>i{
	animation: fa-spin 2s infinite linear;
}

</style>
<div class="vbg-form">
	<fieldset>
		<legend>Dettaglio della comunicazione</legend>
		<table class="vbg-table">
			<thead>
				<th><fmt:message key="label.comunicazione.commissione.riga.destinatario" /></th>
				<th><fmt:message key="label.comunicazione.commissione.riga.stato" /></th>
				<th><fmt:message key="label.comunicazione.commissione.riga.errore" /></th>
				<th><fmt:message key="label.azioni" /></th>
			</thead>
			<tbody>
				<c:forEach items="${comunicazioneCommissioneDetail.righe}" var="riga">
					<tr>
						<td>
							<div id="destinatario${riga.id}" style="display: inline-block">${riga.destinatario}</div>
								
							<div name="cmdModificaMail" data-id="${riga.id}" 
									data-codiceamministrazione="${riga.codiceAmministrazione}"
									data-codiceresponsabile="${riga.codiceResponsabile}" 
									data-codiceanagrafe="${riga.codiceAnagrafe}" 
									data-email="${riga.email}" 
									data-pec="${riga.pec}" 
									data-titolare="${riga.destinatario}" 
									data-modificamail="${riga.modificaMail}">
								<i id="iconaModificaMail${riga.id}" class="fa fa-envelope" style="font-size: 1.5em; margin-left: var(--half-padding);"></i>
							</div>
							
						</td>
						<td>
							<div class="elabora-riga" name="cmdElaboraRiga" data-id="${riga.id}">
								<div id="descrizioneStatoRiga${riga.id}">${riga.descrizioneStato}</div>
								<i class="fa fa-refresh" style="font-size: 1.5em; margin-left: var(--half-padding);"></i>
							</div>
						</td>
						<td>
							<div id="errore${riga.id}">${riga.errore}</div></td>
						<td>
							<div name="cmdDettaglio" data-id="${riga.id}">
								<i class="fa fa-search-plus" style="font-size: 1.5em; margin-left: var(--half-padding);"></i>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</div>