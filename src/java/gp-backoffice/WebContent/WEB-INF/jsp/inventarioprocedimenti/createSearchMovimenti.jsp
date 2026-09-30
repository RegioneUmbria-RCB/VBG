<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<spring-form:form commandName="ips" name="inner_inviodati">	
	<table width="100%">		
		<tr id="id_campo_ricerca_movimenti" >	
	   		<td>
	   		   <jsp:include page="../includes/tipimovimentosearch.jsp" >
					<jsp:param name="idElemento" value="tipoMovimentoInputId" />
					<jsp:param name="pathTipomovimento" value="tipimovimento" />
					<jsp:param name="afterUpdateElement" value="setHiddenFieldMovimento" />	 
				</jsp:include>				
				<input type="hidden" name="id.codiceinventario" value="${ips.id.codiceinventario}">							
	   		</td>
	   </tr> 
	   <tr>
	     	<td>
		  		<div id="functions">
					<ul>						
						<li><a href="javascript:updateMov(${ips.id.codiceinventario},'${ips.id.modulosoftware}',document.getElementById('tipoMovimentoInputId_hidden').value)"><fmt:message key="button.update" /></a></li>
						<li><a href="javascript:void(0)" onClick="dijit.byId('modificaMovDiv').hide()"><fmt:message key="button.annulla" /></a></li>
			        </ul>
			    </div>			
			</td>
	   </tr>
	</table>
</spring-form:form>	

