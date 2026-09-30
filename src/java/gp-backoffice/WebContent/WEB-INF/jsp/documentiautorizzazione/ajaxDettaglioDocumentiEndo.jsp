<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:if test="${not empty istanzeallegatis}">
	<table class="vbg-table">
		<thead>
			<tr class="header">	
				<th width="30%"><fmt:message key="label.descrizione_file"/></th>
				<th width="20%"><fmt:message key="inventarioprocedimenti.label.procedimento"/></th>
				<th width="45%"><fmt:message key="label.note"/></th>
				<c:if test="${autorizzazione.modificaBloccata eq false}">
					<th width="5%"><fmt:message key="label.azioni"/></th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${istanzeallegatis}" var="endodoc" varStatus="idx">
				<tr>
					<td>
						<div class="descrizione-file">${endodoc.allegatoextra}</div>
						<div class="allegati-tpl" data-codiceoggetto="${endodoc.codiceOggetto}">
				    		<i class="fa fa-spinner fa-spin"></i>
				    	</div>
				    </td>
					<td>${endodoc.note}</td>
					<td>${endodoc.procedimento}</td>
					<c:if test="${autorizzazione.modificaBloccata eq false}">
						<td>
							<i class="fas fa-plus-circle vbg-link fa-lg aggiungi-doc-procedimenti" data-codiceoggetto="${endodoc.codiceOggetto}" data-idautorizzazione="${autorizzazione.id.codice}" data-idendoallegati="${endodoc.id.codice}" data-codiceistanza="${endodoc.codiceIstanza}"></i>
						</td>
					</c:if>
				</tr>    	 
			</c:forEach>				
		</tbody>
	</table>
</c:if>	