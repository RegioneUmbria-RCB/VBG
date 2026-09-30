<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:if test="${not empty movimentiallegatis}">
	<table class="vbg-table">
		<thead>
			<tr>	
				<th width="30%"><fmt:message key="label.descrizione_file"/></th>
				<th width="20%"><fmt:message key="label.movimento"/></th>
				<th width="45%"><fmt:message key="label.note"/></th>
				<c:if test="${autorizzazione.modificaBloccata eq false}">
					<th width="5%"><fmt:message key="label.azioni"/></th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${movimentiallegatis}" var="allegatomovimento" varStatus="idx">
				<tr>
					<td>
						<div class="descrizione-file">${allegatomovimento.descrizione}</div>
						<div class="allegati-tpl" data-codiceoggetto="${allegatomovimento.codiceOggetto}">
				    		<i class="fa fa-spinner fa-spin"></i>
				    	</div>
					</td>
					<td>${allegatomovimento.descrizioneMovimento}</td>
					<td>${allegatomovimento.note}</td>
					<c:if test="${autorizzazione.modificaBloccata eq false}">
						<td>
							<i class="fas fa-plus-circle vbg-link fa-lg aggiungi-doc-movimenti" data-codiceoggetto="${allegatomovimento.codiceOggetto}" data-idautorizzazione="${autorizzazione.id.codice}" data-idmovimentiallegati="${allegatomovimento.id.codice}" data-codiceistanza="${allegatomovimento.codiceIstanza}"></i>
						</td>
					</c:if>
				</tr>    	 
			</c:forEach>				
		</tbody>
	</table>
</c:if>	