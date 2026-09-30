<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="vbg-form">
	<table width="100%">       
		<tr>
			<td>		     
				<select id="metadato_id">

		    	</select>   	
		 	</td>
		</tr>
		<tr>
			<td><div id="messaggioErrore" class="error_header" style="display:none;"></div></td>
		</tr>
	</table>
	<table class="vbg-table">
		<thead>
			<tr class="header">	
				<th><fmt:message key="label.metadato" /></th>
				<th><fmt:message key="label.valore" /></th>
				<th><fmt:message key="label.azioni" /></th>
			</tr>
		</thead>
		<tbody>			
			<c:forEach items="${metadati}" var="metadato">
				<tr>
+	    	 	    <td>${metadato.chiave}</td>
+	    	 	    <td>${metadato.valore}</td>
	    	 		<td>
	    	 			<a class="azione" style="float: none;" href="javascript:eliminaMetadato(${metadato.chiave})" title="<fmt:message key="label.elimina" /> ${metadato.chiave}">
							<i class="fa fa-trash-o"></i><fmt:message key="label.elimina" />
						</a>
					</td>
				</tr>    	 
			</c:forEach>				
		</tbody>
	</table>		
</div>