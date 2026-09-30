<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:if test="${not empty endoList}">
	<table class="vbg-table">
		<thead>
			<tr class="header">	
				<th><fmt:message key="label.inventarioprocedimenti" /></th>
				<th><fmt:message key="appio.label.ogg_msg" /></th>
				<th><fmt:message key="appio.label.msg" /></th>
				<th><fmt:message key="label.azioni" /></th>
			</tr>
		</thead>
		<tbody>			
		<c:forEach items="${endoList}" var="entry" varStatus="idx">
			<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">
	    	 	    <td>${entry.procedimento}</td>
	    	 	    <td class="anteprima_markdown" data-messaggio="${entry.templateOggetto}" >${entry.templateOggetto}</td>
	    	 	    <td class="anteprima_markdown" data-messaggio="${entry.templateMessaggio}">${entry.templateMessaggio}</td>
	    	 		<td>
	    	 			<a class="azioni-aggiorna-endo" style="float: none;" 
	    	 				data-codiceinventario="${entry.codiceinventario}"
	    	 				data-templateoggetto="${entry.templateOggetto}" 
	    	 				data-templatemessaggio="${entry.templateMessaggio}"
	    	 				href="javascript: void(0)" title="<fmt:message key="label.modifica" /> ${entry.codiceinventario}">
					    	<i class="fa fa-trash-o"></i><fmt:message key="label.modifica" />
						</a>
	    	 			<a class="azioni-elimina-endo" style="float: none;" 
	    	 				data-codiceinventario="${entry.codiceinventario}" 
	    	 				href="javascript: void(0)" title="<fmt:message key="label.elimina" /> ${entry.codiceinventario}">
					    	<i class="fa fa-trash-o"></i><fmt:message key="label.elimina" />
						</a>			
					</td>
			</tr>    	 
		</c:forEach>				
		</tbody>
	</table>
</c:if>		