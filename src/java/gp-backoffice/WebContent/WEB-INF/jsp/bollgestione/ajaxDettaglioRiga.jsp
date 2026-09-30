<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<div  class="popup-form">
	<table>
		<tr>
			<td><fmt:message key="dettaglioBollettazione.label.nominativo" /></td>
			<td>
				<div class="read-only">
					${rigaDettaglioBollettazione.nominativo}
				</div> 
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="dettaglioBollettazione.label.descrizione" /></td>
			<td>
				<div class="read-only">
					${rigaDettaglioBollettazione.descrizione}
				</div>
			</td>
		</tr>
				<tr>
			<td>
				<fmt:message key="dettaglioBollettazione.label.importoSenzaIVA" /></td>
			<td>
				<div class="read-only">
					<fmt:formatNumber type = "number" minFractionDigits = "2" value = "${rigaDettaglioBollettazione.importoSenzaIVA}" />					
				</div>
			</td>
		</tr>
				<tr>
			<td>
				<fmt:message key="dettaglioBollettazione.label.iva" /></td>
			<td>
				<div class="read-only">
					${rigaDettaglioBollettazione.iva}
				</div>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dettaglioBollettazione.label.importo" /></td>
			<td>
				<div class="read-only">				
					<fmt:formatNumber type = "number" minFractionDigits = "2" value = "${rigaDettaglioBollettazione.importoTotale}" />					
				</div>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dettaglioBollettazione.label.validata" /></td>
			<td>
				<div class="read-only">
					<c:set var="validato" >No</c:set>
					<c:if test="${rigaDettaglioBollettazione.validato eq true }">
						<c:set var="validato" >Si</c:set>
					</c:if>
					${validato }
				</div>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dettaglioBollettazione.label.idPosizioneDebitoria" /></td>
			<td>
				<div class="read-only">
					${rigaDettaglioBollettazione.idPosizioneDebitoria}
				</div>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dettaglioBollettazione.label.descrizioneStato" /></td>
			<td>
				<div class="read-only">
					${rigaDettaglioBollettazione.descrizioneStato}
				</div>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dettaglioBollettazione.label.dataUltimoStato" /></td>
			<td>
				<div class="read-only">
					<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${rigaDettaglioBollettazione.dataUltimoStato}"/>
				</div>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dettaglioBollettazione.label.noteUtente" /></td>
			<td>
				<div class="read-only">			
					${rigaDettaglioBollettazione.noteUtente}
				</div>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dettaglioBollettazione.label.noteSistema" /></td>
			<td>
				<div class="read-only">
					<ul>
						<c:forEach items="${rigaDettaglioBollettazione.noteSistema}" var="nota">
							<li>${nota}</li>
						</c:forEach>
					</ul>					
				</div>
			</td>
		</tr>		
	</table>
	
	<c:if test="${!empty rigaDettaglioBollettazione.dettRigaBollOneri}">
	
		<div class="jmesa">
			<h2>Istanze collegate</h2>
		
			<table class="table istanze">
				<colgroup>
					<col style="width:20%" />
					<col style="width:20%" />
					<col style="width:60%" />
				</colgroup>
				<thead>
					<tr class="header">
						<td><fmt:message key="label.istanza" /></td>
						<td><fmt:message key="label.protocollo" /></td>
						<td><fmt:message key="label.intervento" /></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${rigaDettaglioBollettazione.dettRigaBollOneri}" var="rigaDettBollOneri">
						<tr>
							<td>${rigaDettBollOneri.istanza }</td>
							<td>${rigaDettBollOneri.protocollo }</td>
							<td>${rigaDettBollOneri.intervento }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
</div>