<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:if test="${not empty istanzeprocures}">
	<table class="vbg-table">
		<thead>
			<tr>	
				<th width="30%"><fmt:message key="label.filename"/></th>
				<th width="65%"><fmt:message key="label.procura"/></th>
				<c:if test="${autorizzazione.modificaBloccata eq false}">
					<th width="5%"><fmt:message key="label.azioni"/></th>
				</c:if>
			</tr>
		</thead>
		<tbody>	 
			<c:forEach items="${istanzeprocures}" var="istanzaprocure" varStatus="idx">
				<tr>
					<td>
						<div class="allegati-tpl" data-codiceoggetto="${istanzaprocure.codiceOggetto}">
				    		<i class="fa fa-spinner fa-spin"></i>
				    	</div>
					</td>
					<td>${istanzaprocure.anagrafeProcuratore.nominativo}</td>
					<c:if test="${autorizzazione.modificaBloccata eq false}">
						<td>
							<i class="fas fa-plus-circle vbg-link fa-lg aggiungi-doc-procure" data-codiceoggetto="${istanzaprocure.codiceOggetto}" data-idautorizzazione="${autorizzazione.id.codice}" data-idprocura="${istanzaprocure.id.codice}" data-codiceistanza="${istanzaprocure.codiceIstanza}"></i>
						</td>
					</c:if>
				</tr>    	 
			</c:forEach>				
		</tbody>
	</table>
</c:if>	