<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class='vbg-modal-body'>

<style>

	.disabilitata-zip-logico > td{
	
		color: red;	
		text-decoration: line-through;
		background-image: linear-gradient(45deg, #f0f0f0 5.56%, #ffffff 5.56%, #ffffff 50%, #f0f0f0 50%, #f0f0f0 55.56%, #ffffff 55.56%, #ffffff 100%);
		background-size: 12.73px 12.73px; 
		min-width: 10px; 
		border: 2px solid maroon; 
		cursor: help;
	}
	
	

</style>

<h1>
  <fmt:message key="label.movimenti_zip_logico.visualizza_zip_logico" /> 
</h1>
	<c:if test="${not empty ziplogico}">
		<table class="vbg-table" id="zip-logico-content-table" data-codiceoggetto-doc-allegato="${ codiceoggettoDocAll }">
			<thead>
				<tr>
					<th><fmt:message key="label.filename"/></th>
					<th><fmt:message key="label.descrizione_file"/></th>
					<th><fmt:message key="label.azioni"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ziplogico}" var="ziplogico_var" varStatus="idx">
					<tr id="zip_row_id">
						<td>${ziplogico_var.nomeFile}</td>
						<c:choose>
							<c:when test="${not empty ziplogico_var.descFileDocumentiistanza }">
								<td>${ziplogico_var.descFileDocumentiistanza}</td>
							</c:when>
							<c:when test="${not empty ziplogico_var.descFileMovimentiallegati }">
								<td>${ziplogico_var.descFileMovimentiallegati}</td>
							</c:when>
							<c:when test="${not empty ziplogico_var.descFileIstanzeallegati }">
								<td>${ziplogico_var.descFileIstanzeallegati}</td>
							</c:when>
							<c:when test="${not empty ziplogico_var.descFileIstanzeprocure }">
								<td>${ziplogico_var.descFileIstanzeprocure}</td>
							</c:when>
							<c:when test="${not empty ziplogico_var.descFileDocumentianagrafe }">
								<td>${ziplogico_var.descFileDocumentianagrafe}</td>
							</c:when>
							<c:when test="${not empty ziplogico_var.descFileCdsatti }">
								<td>${ziplogico_var.descFileCdsatti}</td>
							</c:when>
							<c:otherwise>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
						<td class="riferimenti-zip-logico" data-codiceoggetto="${ziplogico_var.codiceOggetto}" data-id="${ziplogico_var.id.codice }">
							<jsp:include page="../includes/visualizzaOggetto.jsp" >
	       						<jsp:param name="idElemento" value="docZipLogico_${ziplogico_var.id.codice }" />
	       						<jsp:param name="fileId" value="${ziplogico_var.codiceOggetto}" />
	       						<jsp:param name="mostralabel" value="true"/>
	       						<jsp:param name="mostraNomeFile" value="true"/>
								<jsp:param name="readonly" value="true"/>
	   						</jsp:include>
   						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	
	 <div class="vbg-modal-footer">
         <a href="#" data-role='toggle-popup' class="btn btn-primary">
             <fmt:message key="button.back" />
         </a>
     </div>
	
</div>