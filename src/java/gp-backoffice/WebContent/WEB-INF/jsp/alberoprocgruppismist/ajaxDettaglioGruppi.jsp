<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.AlberoprocController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="jmesa">
	<table class="table">
		<thead>
			<tr class="header">	
				<td><fmt:message key="label.gruppi_smistamento.gruppo1" /></td>
				<td><fmt:message key="label.gruppi_smistamento.gruppo2" /></td>
				<td><fmt:message key="label.gruppi_smistamento.gruppo3" /></td>
				<td><fmt:message key="label.gruppi_smistamento.intervento" /></td>
				<td><fmt:message key="label.gruppi_smistamento.procedura_scia" /></td>
				<td><fmt:message key="label.gruppi_smistamento.procedura_ordinario" /></td>
				<td><fmt:message key="label.azioni" /></td>
			</tr>
		</thead>
		<tbody>
			
		<c:forEach items="${gruppis}" var="entry" varStatus="idx">
		<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">   	 
    	 		<td>${entry.gruppo1.descrizione}</td>
    	 		<td>${entry.gruppo2.descrizione}</td>
    	 		<td>${entry.gruppo3.descrizione}</td>
    	 		<td>${entry.alberoproc.descrizioneCompleta}</td>
    	 		<td>${entry.tipiprocedureSCIA.procedura}</td>
    	 		<td>${entry.tipiprocedureOrdinario.procedura}</td>
    	 		<td>
    	 			<a class="eliminaRiga" style="float: none;" href="javascript:eliminaRiga(${entry.id.codice})" title="<fmt:message key="label.elimina" /> ${entry.id.codice}">
				         	 <label><fmt:message key="label.elimina.image" /></label>
					</a>			
				</td>
		</tr>    	 
		</c:forEach>				
		</tbody>
	</table>
			
</div>