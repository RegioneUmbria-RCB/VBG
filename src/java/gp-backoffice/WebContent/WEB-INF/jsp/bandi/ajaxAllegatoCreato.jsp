<%@ include file="../includes/taglibs.jsp"%>
	<%
		String nomeFile="";
		Integer codiceAllegato=null;
		if((String)request.getAttribute("nomeFile")!=null)
		{
		   nomeFile= (String)request.getAttribute("nomeFile");
		   pageContext.setAttribute("nomefile", nomeFile);
		}
		
		if((Integer)request.getAttribute("codiceAllegato")!=null)
		{
		    codiceAllegato= (Integer)request.getAttribute("codiceAllegato");
		  	pageContext.setAttribute("codiceallegato", codiceAllegato);
		}
	%>
	
	<table width="60%">	  
	   <tr>	
	   		<td>&nbsp;<td>
	   </tr>
	   <tr>
	   		<td>
	   			 <div style="width:500px;;min-height: 50px; border: thin dotted; padding: 5px;">
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
						<li><a href="${pageContext.request.contextPath}/file/editDocApplication.htm?fileId=${codiceallegato}"><fmt:message key="button.change" /></a></li>
			        </ul>
			        <ul>
						<li><a href="javascript: apriDialogComunicazioni(${graduatoried.id.codice })"><fmt:message key="button.back" /></a></li>
			        </ul>
			  </div>			
			</td>
	   </tr>
	</table>