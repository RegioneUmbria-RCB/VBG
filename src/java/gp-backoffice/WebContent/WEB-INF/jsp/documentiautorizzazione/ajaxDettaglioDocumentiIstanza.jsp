<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:if test="${not empty documentiistanzas}">
	<table class="vbg-table">
		<thead>
			<tr>	
				<th width="30%"><fmt:message key="label.descrizione_file"/></th>
				<th width="20%"><fmt:message key="label.documenti_autorizzazioni.istanza"/></th>
				<th width="45%"><fmt:message key="label.note"/></th>
				<c:if test="${autorizzazione.modificaBloccata eq false}">
					<th width="5%"><fmt:message key="label.azioni"/></th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${documentiistanzas}" var="documentiistanza" varStatus="idx">
				<tr id="documentiistanza_tr_id${documentiistanza.id.codice}">
					<td>
						<div class="descrizione-file">${documentiistanza.documento}</div>
						<div class="allegati-tpl" data-codiceoggetto="${documentiistanza.codiceOggetto}">
				    		<i class="fa fa-spinner fa-spin"></i>
				    	</div>
				    </td>
					<td>${istanza.numeroistanza}</td>
					<td>${documentiistanza.note}</td>
					<c:if test="${autorizzazione.modificaBloccata eq false}">
						<td>
							<i class="fas fa-plus-circle vbg-link fa-lg aggiungi-doc-istanza" data-codiceoggetto="${documentiistanza.codiceOggetto}" data-idautorizzazione="${autorizzazione.id.codice}" data-iddocumentiistanza="${documentiistanza.id.codice}" data-codiceistanza="${istanza.id.codice}"></i>
						</td>
					</c:if>
				</tr>    	 
			</c:forEach>				
		</tbody>
	</table>
</c:if>