<%@page import="it.gruppoinit.pal.gp.core.domain.Istanze"%>
<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="jmesa">
	<table class="table">
		<thead>
			<tr class="header">
				<td><fmt:message key="label.azione" /></td>
				<td><fmt:message key="label.denominazione_dell_attivita" /></td>
				<td><fmt:message key="label.tipologia_attivita" /></td>
				<td><fmt:message key="label.numeroistanza" /></td>
				<td><fmt:message key="label.richiedente" /></td>
				<td><fmt:message key="label.localizzazione" /></td>
				<td><fmt:message key="label.alberoproc" /></td>
				<td><fmt:message key="label.attiva" /></td>
				<td><fmt:message key="label.operante" /></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${iAttivitas}" var="iattivita_var">
				<tr>
					<td>
						<div id="functions">
							<ul><li><a href="javascript:viewOrAssign(${iattivita_var.id.codice});" title=""><fmt:message key="label.collega" /></a></li></ul>
						</div>
					</td>
					<td>${iattivita_var.denominazione}</td>
					<td>${iattivita_var.tipologiaAttivita.descrizione}</td>
					<td>${iattivita_var.istanza.numeroistanza}</td>
					<td>${iattivita_var.istanza.transientRichiedenteQualitaAzienda}</td>
					<td>
						${iattivita_var.istanza.transientLocalizzazionePrimario}
					</td>
					<td>
						${iattivita_var.istanza.alberoproc.vwAlberoproc.scDescrizione}
					</td>
					<td>
						<c:choose>
							<c:when test="${iattivita_var.attiva eq true }">
								<fmt:message key="label.si" />
							</c:when>
							<c:otherwise>
								<fmt:message key="label.no" />
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${iattivita_var.operante eq true }">
								<fmt:message key="label.si" />
							</c:when>
							<c:otherwise>
								<fmt:message key="label.no" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td style="border-bottom: #c0c0c0 1px dashed;" colspan="9"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%
	String urlBack = URLEncoder.encode("../istanze/view.htm?codice=");
	%>
	
	<script type="text/javascript">
	function viewOrAssign(idattivita){	
		historySet('<%=urlBack%>${codiceistanza}','../iattivita/updateCollegaIstanza.htm?codiceAttivita='+idattivita+'&codiceIstanza=${codiceistanza}','<fmt:message key="javascript.confirm.associa_attivita" />');				
	}
	</script>
</div>