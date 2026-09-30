<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:if test="${not empty anagrafedocumentis}">
	<table class="vbg-table">
		<thead>
			<tr>	
				<th width="30%"><fmt:message key="label.descrizione_file"/></th>
				<th width="65%"><fmt:message key="label.anagrafe"/></th>
				<c:if test="${autorizzazione.modificaBloccata eq false}">
					<th width="5%"><fmt:message key="label.azioni"/></th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${anagrafedocumentis}" var="anagrefedocumento" varStatus="idx">
				<tr>
					<td>
						<div class="descrizione-file">${anagrefedocumento.documento}</div>
						<div class="allegati-tpl" data-codiceoggetto="${anagrefedocumento.codiceOggetto}">
				    		<i class="fa fa-spinner fa-spin"></i>
				    	</div>
				    </td>
					<td>${anagrefedocumento.nominativo}</td>
					<c:if test="${autorizzazione.modificaBloccata eq false}">
						<td>
							<i class="fas fa-plus-circle vbg-link fa-lg aggiungi-doc-procure" data-codiceoggetto="${anagrefedocumento.codiceOggetto}" data-idautorizzazione="${autorizzazione.id.codice}" data-iddocanagrafe="${anagrefedocumento.id.codice}" data-codiceistanza="${anagrafedocumento.codiceIstanza}"></i>
						</td>
					</c:if>
				</tr>    	 
			</c:forEach>				
		</tbody>
	</table>
</c:if>