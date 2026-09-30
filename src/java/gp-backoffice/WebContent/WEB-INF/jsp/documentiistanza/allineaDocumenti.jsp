<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento"%>
<%@ page import="java.util.Date" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.schermata_allineamento_documenti" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.schermata_allineamento_documenti" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../documentiistanza/list" />
	</jsp:include>	
<style>

.attuale{
	background-color: #e0e0e0;
}
.nuovo{
	background-color: #B3FFB4;
}

</style>


	<div id="subcontent">	
		<spring-form:form commandName="cambioInterventoCommand" name="inviodati">
		<%-- <input type="hidden" name="codiceIstanza" value="${cambioInterventoCommand.entity.id.codice}"/>--%>
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="cambioInterventoCommand" />
		    </jsp:include>
  
		   <fieldset style="width: 90%;min-height: 50px; border: thin dotted; font-weight: bolder;">
				<fmt:message key="label.help_allinea_documenti" />
			</fieldset>
			
	         <br class="clear" />
				<div class="jmesa">
				<table width="98%" border="0">
						<tr id="id_progetto_table">
							<td style="width: 50%" valign="top"></td>
							<td class="attuale">
							<b style="text-transform: uppercase;"><fmt:message key="label.documenti_istanza" /></b>
								<div id="functions">
									<ul><li><a href="javascript: void(0)" onclick="selezionaTuttiOld();"><fmt:message key="label.seleziona_tutto" /></a></li>
										<li><a href="javascript: void(0)" onclick="deselezionaTuttiOld();"><fmt:message key="label.deseleziona_tutto" /></a></li>
									</ul>
								</div>
							</td>
							<td  class="nuovo"><b  style="text-transform: uppercase;"><fmt:message key="label.documenti_intervento" /></b>									
								<div id="functions">
									<ul>
										<li><a href="javascript: void(0)" onclick="selezionaTuttiNew();"><fmt:message key="label.seleziona_tutto" /></a></li>
										<li><a href="javascript: void(0)" onclick="deselezionaTuttiNew();"><fmt:message key="label.deseleziona_tutto" /></a></li>
									</ul>
								</div>
							</td>
						</tr>
						
						<%-- INIZIO DOCUMENTI --%>
						<tr id="id_progetto_table" class="titoloSezione">
							<td colspan="3"></td>
						</tr>
						<c:forEach items="${cambioInterventoCommand.docs}" var="current" varStatus="a">
							<tr id="id_progetto_table" class="<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>" onmouseover="this.className='highlight'"  onmouseout="this.className='<c:choose><c:when test="${a.index % 2 eq 0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose>'">
								<td>${current.descrizione}</td>
								<td class="attuale">
								<c:if test="${current.attuale.presente eq true}">
									<c:if test="${current.attuale.readonly eq false}">
										<spring:bind path="docs[${a.index}].attuale.checked">							
											<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
											<input id="doc_attuale_${a.index}" type="checkbox" class="attualesel"
												name="<c:out value="${status.expression}"/>" 
												value="true" 
												<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> 
												/>
										</spring:bind>
									</c:if>
									<c:if test="${current.attuale.readonly eq true}">				
										<input id="doc_attuale_${a.index}" type="checkbox" name="doc_null_${a.index}" 
											disabled="disabled"
											<c:if test="${current.attuale.checked eq true}">checked="true"</c:if> />
									
									</c:if>
								</c:if>						
								</td>
								<td class="nuovo">
									<c:if test="${current.nuovo.presente eq true}">
										<c:if test="${current.nuovo.readonly eq false}">
											<spring:bind path="docs[${a.index}].nuovo.checked">							
												<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
												<input id="doc_nuovo_${a.index}" type="checkbox"  class="nuovosel"
													name="<c:out value="${status.expression}"/>" 
													value="true" 
													<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> 
													/>
											</spring:bind>
										</c:if>
										<c:if test="${current.nuovo.readonly eq true}">
											<input id="doc_nuovo_${a.index}" type="checkbox" name="doc_null_${a.index}" 
												disabled="disabled"
												<c:if test="${current.nuovo.checked eq true}">checked="true"</c:if> />
										</c:if>																
									</c:if>
								</td>
							</tr>
						</c:forEach>
						<%-- FINE DOCUMENTI --%>

				</table>							
				</div>	
				<input type="hidden" value="" name=""></input>
			</spring-form:form>	
		</div>
	
	<div id="functions">
		<ul><li><a href="javascript:void(0);" onclick="doSubmit('allineaDocumenti.htm?codiceIstanza=${cambioInterventoCommand.entity.id.codice}','<fmt:message key="javascript.confirm.procedere_con_l_operazione" />')"><fmt:message key="button.save" /></a></li></ul>
		<ul><li><a href="javascript:historyBack();"><fmt:message key="label.chiudi" /></a></li></ul>
	</div>
	

	<script type="text/javascript">
	function ajaxHistorySet(url){
		new Ajax.Request('<%=request.getContextPath()%>/history/ajaxSet.htm', {
			  method: 'get',
			  parameters: {ReturnTo: url, limit: 12},
			  onSuccess: function(transport){},
			  onFailure: function(){}			  
		});
	}
	
	function selezionaTuttiOld(){
		selezionaTutti('attuale',true);
	}
	function selezionaTuttiNew(){
		selezionaTutti('nuovo',true);
	}
	function deselezionaTuttiOld(){
		selezionaTutti('attuale',false);
	}
	function deselezionaTuttiNew(){
		selezionaTutti('nuovo',false);
	}
	function selezionaTutti(quale, isChecked){
		jQuery( "."+quale+"sel" ).attr('checked', isChecked);
	}
	
	
	function usaConfigurazioniOld(){
		selezionaTuttiOld();
		deselezionaTuttiNew();
		
	}
	function usaConfigurazioniNew(){		
		selezionaTuttiNew();
		deselezionaTuttiOld();
	}
	
	</script>	
</body>
</html>