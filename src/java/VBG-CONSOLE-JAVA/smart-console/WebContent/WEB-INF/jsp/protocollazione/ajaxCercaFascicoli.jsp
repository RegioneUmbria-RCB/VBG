<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:choose>
<c:when test="${not empty errore}">
	<div class="error_header">${errore}</div>
</c:when>
<c:otherwise>
<div class="jmesa">
	<table class="table" style="width: 100%;">
		<thead>
			<tr class="header">
				<td><fmt:message key="label.risultato" /></td>
			</tr>
		</thead>		
		<tbody>
			<c:forEach items="${list}" var="doc_var" varStatus="a">
				<tr class="${((a.index%2)==0)?'odd':'even'}">
					<td title="Clicca per scegliere le informazioni di fascicolazione">
						<ul style="cursor: pointer;" id="fascList_${a.index}" onclick="scegliFascicolo('${a.index}');">
							<li><fmt:message key="label.anno" />: <b><span id="anno_fasc_${a.index}">${doc_var.annoFascicolo}</span></b></li>
							<li><fmt:message key="label.numero" />: <b><span id="numero_fasc_${a.index}">${doc_var.numeroFascicolo}</span></b></li>
							<li><fmt:message key="label.classifica" />: <b><span id="classifica_fasc_${a.index}">${doc_var.classificaFascicolo}</span></b></li>
							<li><fmt:message key="label.descrizione" />: <b><span id="oggetto_fasc_${a.index}">${doc_var.oggettoFascicolo}</span></b></li>
							<%--
								<li><fmt:message key="label.data" />: <span id="data_fasc_${a.index}">${doc_var.dataFascicolo}</span></li>
							 --%>
						</ul>
				    </td>				    
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</c:otherwise>
</c:choose>