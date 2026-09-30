<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.AlberoprocController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="jmesa">
	<table class="table">
		<thead>
			<tr class="header">	
				<td><fmt:message key="label.comune" /></td>
				<td><fmt:message key="label.amministrazione" /></td>
				<td><fmt:message key="alberoproc.label.scFascclassifica" /></td>
				<td><fmt:message key="alberoproc.label.scProttipodocumento" /></td>
				<td><fmt:message key="alberoproc.label.scProtcodtesto" /></td>
				<td><fmt:message key="alberoproc.label.scProtautomatica" /></td>
				<td><fmt:message key="alberoproc.label.scFascclassifica" /></td>
				<td><fmt:message key="alberoproc.label.scFasccodtesto" /></td>
				<td><fmt:message key="alberoproc.label.scFascautomatica" /></td>
			</tr>
		</thead>
		<tbody>
			
		<c:forEach items="${configAlberoproc}" var="entry" varStatus="idx">
		<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">   	 
    	 		<td>${entry.key}</td>
				<td>${entry.value.amministrazioni.amministrazione}</td>
				<td>${entry.value.scProtclassifica}</td>
				<td>${entry.value.scProttipodocumento}</td>
				<td>${entry.value.testoProtocollo.descrizione}</td>
				<td>
				<% 
				 Map.Entry<String, AlberoprocProtocollo> en = (Map.Entry<String, AlberoprocProtocollo>)pageContext.getAttribute("entry");
				AlberoprocProtocollo p = en.getValue();				
				String dec = AlberoprocController.decodificaTipo(p.getScProtautomatica(), true);
				out.print(dec);
				%>	
				</td>
				<td>${entry.value.scFascclassifica}</td>
				<td>${entry.value.testoFascicolo.descrizione}</td>
				<td><% 
				
				 dec = AlberoprocController.decodificaTipo(p.getScFascautomatica(), false);
				 out.print(dec);
				%>	</td>
		</tr>    	 
		</c:forEach>				
		</tbody>
	</table>
		
</div>