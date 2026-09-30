<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:if test="${not empty interventiList}">
		<style>
		.sezione_software{
			padding-top: 10px;
			
		}
		.sezione_software > label {
			font-size: 1.8em;
			font-style: bold;	
			color: maroon;		
			text-align: center;
		}
		
		
		</style>
	
		
		<c:forEach items="${interventiList}" var="radice" varStatus="idx2">
			<div class="sezione_software">
				<label>Archivi ${radice.descrizione }</label>
			<div>
			<table class="vbg-table">
			<thead>
				<tr class="header">	
					<th><fmt:message key="label.intervento" /></th>
					<th><fmt:message key="appio.label.ogg_msg" /></th>
					<th><fmt:message key="appio.label.msg" /></th>
					<th><fmt:message key="label.azioni" /></th>
				</tr>
			</thead>
			<tbody>			
				<c:forEach items="${radice.interventi}" var="entry" varStatus="idx">
				<tr>
		    	 	    <td>${entry.intervento}</td>
		    	 	    <td class="anteprima_markdown" data-messaggio="${entry.templateOggetto}" >${entry.templateOggetto}</td>
		    	 	    <td class="anteprima_markdown" data-messaggio="${entry.templateMessaggio}">${entry.templateMessaggio}</td>
		    	 		<td>
		    	 			<a class="azioni-aggiorna-intervento" style="float: none;" 
		    	 				data-codiceintervento="${entry.codiceintervento}"
		    	 				data-templateoggetto="${entry.templateOggetto}" 
		    	 				data-templatemessaggio="${entry.templateMessaggio}"
		    	 				href="javascript: void(0)" title="<fmt:message key="label.modifica" /> ${entry.codiceintervento}">
						    	<i class="fa fa-trash-o"></i><fmt:message key="label.modifica" />
							</a>
		    	 			<a class="azioni-elimina-intervento" style="float: none;" 
		    	 				data-codiceintervento="${entry.codiceintervento}" 
		    	 				href="javascript: void(0)" title="<fmt:message key="label.elimina" /> ${entry.codiceintervento}">
						    	<i class="fa fa-trash-o"></i><fmt:message key="label.elimina" />
							</a>			
						</td>
				</tr>    	 
				</c:forEach>
			</tbody>
			</table>
			
		</c:forEach>				
		
</c:if>		