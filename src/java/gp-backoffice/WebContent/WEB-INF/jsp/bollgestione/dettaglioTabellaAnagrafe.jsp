<%@ include file="../includes/taglibs.jsp" %>
<table class="table tabella-dettaglio">
	<colgroup>
		<col style="width:50%" />
		<col style="width:10%" />
		<col style="width:5%" />
		<col style="width:10%" />
		<col style="width:5%" />
		<col style="width:20%" />
	</colgroup>
	<thead>
		<tr class="header">
			<td><fmt:message key="dettaglioBollettazione.label.descrizione" /></td>						
			<td style="text-align: center"><fmt:message key="dettaglioBollettazione.label.importoSenzaIVA" /> (&euro;)</td>
			<td style="text-align: center"><fmt:message key="dettaglioBollettazione.label.iva" /> </td>
			<td style="text-align: center"><fmt:message key="dettaglioBollettazione.label.importo" /> (&euro;)</td>
			<td style="text-align: center"><fmt:message key="dettaglioBollettazione.label.validata" /></td>
			<td><fmt:message key="dettaglioBollettazione.label.funzioni" /></td>
		</tr>
	</thead>
	<tbody class="tbody">	
		<c:forEach items="${righeBollettazioneList}" var="riga" varStatus="loop"> 
		
			<tr class="riga-bollettazione ${(loop.index % 2 == 0) ? "odd" : "even"} ${riga.isRettificato == true ? " rettificato" : ""} " data-id-riga="${riga.id}" id="rigaBollettazione${riga.id}"  >				
				<td class="descrizione-riga">
				${riga.descrizione}
				</td>
				<td style="text-align: right" class="importo-senza-iva-riga">
					<fmt:formatNumber type = "number" minFractionDigits = "2" value = "${riga.importoSenzaIVA}" />
				</td>
				<td style="text-align: right" class="iva-riga">${riga.iva}</td>
				<td style="text-align: right" class="importo-riga">
					<fmt:formatNumber type = "number" minFractionDigits = "2"  value = "${riga.importoTotale}" />
				</td>
				<td style="text-align: center">
					<c:if test="${riga.supportaValidazione}">
						<c:set var="valido" />  
						<c:if test="${riga.validato eq true }">
							<c:set var="valido" >  checked="checked" </c:set>
						</c:if>
						<input type="checkbox" name="" class="cmd-validato" data-id-riga="${riga.id}" ${valido}/>
					</c:if>
				</td>
				<td>
					
					<ul class="funzioni-dettaglio">
						<li class="azione cmd-dettaglio">
							<i class="fa fa-search"></i>
							<fmt:message key="label.dettaglio" />
						</li>
						<c:if test="${riga.supportaRettifica && riga.bollettazioneChiusa eq false}">
							<li class="azione cmd-rettifica">
								<i class="fa fa-edit"></i>
								<fmt:message key="label.rettifica" />
							</li>
						</c:if>
						<c:if test="${riga.supportaCancellazione && riga.bollettazioneChiusa eq false}">
							<li class="azione cmd-cancella">
								<i class="fa fa-trash-o"></i>
								<fmt:message key="label.elimina" />
							</li>
						</c:if>
						<c:if test="${riga.daRateizzare eq true}">
						<li class="azione cmd-elencorate">
							<i class="fa fa-file-invoice-dollar"></i>
							<fmt:message key="bollgestione.rata.elencorate" />
						</li>
						</c:if>				
					</ul>
					<c:if test="${riga.posizioniDebitorieRaggruppate eq false }">
						<c:forEach items="${riga.idDettPosizioniDebitorie}" var="posizione">
                        	<vbg-dettaglio-posizione-debitoria 
                        		id-posizione="${posizione}" 
                        		mostra-testo="false"
                        		fetch-ref='data-by-id'>
                    		</vbg-dettaglio-posizione-debitoria>
                    	</c:forEach>
                    	<span class="cmd-anteprima-avvisatura" data-id-posizione="${posizione}"></span>
					</c:if>	
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

