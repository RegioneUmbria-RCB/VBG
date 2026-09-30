<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.AlberoprocController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
	<table class="vbg-table">
	<thead>
		<tr>	
			<th width="2%" title="<fmt:message key="label.autorizzazioni.documenti.principale.help"/>"><fmt:message key="label.autorizzazioni.documenti.principale.sigla"/></th>
			<th width="28%"><fmt:message key="label.descrizione_file"/></th>
			<th width="5%"><fmt:message key="label.documenti_autorizzazioni.istanza"/></th>
			<th width="15%"><fmt:message key="label.movimento"/></th>
			<th width="15%"><fmt:message key="label.documenti_autorizzazioni.endo"/></th>
			<th width="15%"><fmt:message key="label.anagrafe"/></th>
			<th width="15%"><fmt:message key="label.documenti_autorizzazioni.procure"/></th>
			<c:if test="${autorizzazione.modificaBloccata eq false}">
				<th width="5%"><fmt:message key="label.azioni"/></th>
			</c:if>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${not empty documentiautorizzaziones}">
				<c:forEach items="${documentiautorizzaziones}" var="documentoautorizzazione" varStatus="idx">
				<tr id="doc_autorizzazione_row_id">
					<c:choose>
						<c:when test="${codiceoggettoinserito ==  documentoautorizzazione.id.codiceoggetto }">
							<c:set value="#aaf981" var="color" scope="page"></c:set>
						</c:when>
						<c:otherwise>
							<c:set value="" var="color" scope="page"></c:set>
						</c:otherwise>
					</c:choose>
					<td>
						<c:choose>
							<c:when test="${autorizzazione.modificaBloccata eq false && mostraCheckPrincipale eq true}">
								<input type="checkbox" value="${documentoautorizzazione.id.codiceoggetto}" name="principale" ${documentoautorizzazione.principale?'checked':''}  data-idautorizzazione="${documentoautorizzazione.id.idautorizzazione}"  />
							</c:when>
							<c:otherwise>
								<c:if test="${documentoautorizzazione.principale}">
									<i class="fas fa-sharp fa-solid fa-star" title="<fmt:message key="label.autorizzazioni.documenti.principale.help"/>"></i>
								</c:if>								
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<div class="descrizione-file">
							<c:choose>
								<c:when test="${not empty documentoautorizzazione.descFileDocumentiistanza}">
									${documentoautorizzazione.descFileDocumentiistanza}
								</c:when>
								<c:when test="${not empty documentoautorizzazione.descFileMovimentiallegati}">
									${documentoautorizzazione.descFileMovimentiallegati}
								</c:when>
								<c:when test="${not empty documentoautorizzazione.descFileIstanzeallegati}">
									${documentoautorizzazione.descFileIstanzeallegati}
								</c:when>
								<c:when test="${not empty documentoautorizzazione.descFileDocumentianagrafe}">
									${documentoautorizzazione.descFileDocumentianagrafe}
								</c:when>
								<c:when test="${not empty documentoautorizzazione.descFileIstanzeprocure}">
									${documentoautorizzazione.descFileIstanzeprocure}
								</c:when>
							</c:choose>
						</div>
						<div class="allegati-tpl" data-codiceoggetto="${documentoautorizzazione.id.codiceoggetto}">
				    		<i class="fa fa-spinner fa-spin"></i>
				    	</div>
					</td>
					<td>${autorizzazione.istanza.numeroistanza}</td>
					<td>${documentoautorizzazione.movimento}</td>
				    <td>${documentoautorizzazione.procedimento}</td>	
				    <td>${documentoautorizzazione.nominativo} ${documentoautorizzazione.nome}</td>
				    <td>${documentoautorizzazione.procure}</td>
					<c:if test="${autorizzazione.modificaBloccata eq false}">
						<td>
							<i class="fas fa-times vbg-link fa-lg togli-doc-autorizzazione" data-codiceoggetto="${documentoautorizzazione.id.codiceoggetto}" data-idautorizzazione="${documentoautorizzazione.id.idautorizzazione}"></i>		
						</td>
					</c:if>
				</tr>    	 
			</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="8" align="center"><fmt:message key="label.documenti_autorizzazioni.empty_list"/></td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
