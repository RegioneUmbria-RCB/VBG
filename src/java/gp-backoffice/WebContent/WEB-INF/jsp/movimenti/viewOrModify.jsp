<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<spring-form:form commandName="movimentiCommand" name="inner_inviodati">	
	
	<%
		
		Integer codiceAllegato=null;
		
		
		if((Integer)request.getAttribute("codiceAllegato")!=null)
		{
		    codiceAllegato= (Integer)request.getAttribute("codiceAllegato");
		  	pageContext.setAttribute("codiceallegato", codiceAllegato);
		}
	%>
	
	<table width="100%">
	   <tr>
	   		<td>
	   		   <fmt:message key="label.il_documento" />&nbsp;
	   		   <fmt:message key="label.help_creato_correttamente" />
	   		   
			</td>
	   </tr>
	   <tr>	
	   		<td>&nbsp;<td>
	   </tr>
	   <tr>
	   		<td>
	   			 <div style="width:800px;;min-height: 50px; border: thin dotted; padding: 5px;">
					<fmt:message key="movimenti.label.help_operazioni_possibili_file_creato" />
				</div>
	   		</td>
	   </tr>		
	   <tr>	
	   		<td>&nbsp;<td>
	   </tr>	  
	   <tr>
		  	 <td>
		  		<div id="functions">
					<ul>
						<li><a href="../file/ajaxDownload.htm?fileId=${codiceallegato}"><fmt:message key="button.visualizza" /></a></li>
			        </ul>
					<ul>
						<li><a href="javascript:modificaAllegatoCreato(${codiceallegato})"><fmt:message key="button.change" /></a></li>
			        </ul>
					<ul>
						<li><a href="javascript:void(0)" onClick="dijit.byId('ricercaDocTipoDiv').hide()"><fmt:message key="button.back" /></a></li>
			        </ul>
			  </div>			
			</td>
	   </tr>
	</table>
</spring-form:form>	