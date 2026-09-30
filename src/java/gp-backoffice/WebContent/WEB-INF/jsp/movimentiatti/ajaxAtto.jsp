<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="jmesa">
	<table class="table">
		<thead>
			
		</thead>
		<tbody>
		
			<c:if test="${attoPresente}">
			            <b>${messaggio}</b><br />
						<div class="parametriDiv">
						<div class="etichetta">
							<div><fmt:message key="label.data_richiesta" />:</div>
							<div><fmt:message key="label.data_ricezione" />:</div>
							<div><fmt:message key="label.id_documento" />:</div>
							<div><fmt:message key="label.numero" />:</div>
							<div><fmt:message key="label.anno" />:</div>
							<div><fmt:message key="label.tipo_documento" />:</div>
						</div>		
						<div class="parametro">    
							<div><fmt:formatDate value="${movimentiAtti.dataRichiestaAtto}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
							<div><fmt:formatDate value="${movimentiAtti.dataRicezioneAtto}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>
				            <div>${movimentiAtti.idDocumento} </div>
				            <div>${movimentiAtti.numero}</div>
				            <div>${movimentiAtti.anno}</div>
				            <div>${movimentiAtti.tipoDocumento}</div>
						</div>
					</div>
			</c:if>
			<c:if test="${!attoPresente}">
				${messaggio}
			</c:if>
		</tbody>
	</table>
</div>
<div id="functions">
	<ul>
		<li><a href="javascript:void 0" onclick="dijit.byId('ricercaAttoDiv').hide();"><fmt:message key="button.back" /></a></li>
	</ul>
</div>