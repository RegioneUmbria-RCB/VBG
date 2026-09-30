<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.CommedilizieAppello"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commedilizieappello.id.codice==null}">
			<fmt:message key="label.nuovo_convocato" />
		</c:if> 
		<c:if test="${commedilizieappello.id.codice!=null}">
			<fmt:message key="label.modifica_convocato" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commedilizieappello.id.codice==null}">
			<fmt:message key="label.nuovo_convocato" />
		</c:if> 
		<c:if test="${commedilizieappello.id.codice!=null}">
			<fmt:message key="label.modifica_convocato" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.numero_commissione" />:</div>
			<div><fmt:message key="label.descrizione" />:</div>
		</div>		
		<div class="parametro">       		 	
			<div>${commedilizieappello.commissioniedilizieT.numprotocollo}</div>
			<div>${commedilizieappello.commissioniedilizieT.descrizione}</div>
		</div>
	</div>
	<br class="clear" />
		<spring-form:form commandName="commedilizieappello" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizieappello" />
		    </jsp:include>
		    <%
		    	String displayResponsabile="";
		    	String displayAmmnistrazione="";
		    	if((CommedilizieAppello)request.getAttribute("commissione")!=null)
		    	{
			   		CommedilizieAppello commedilizieAppello= (CommedilizieAppello)request.getAttribute("commissione");
			   	    if(commedilizieAppello.getId()==null){
			    		displayResponsabile="";
			    		displayAmmnistrazione="display:none;";
			   	   	}
				   	if(commedilizieAppello.getId()!=null){
				     	if(commedilizieAppello.getResponsabile().getId().getCodice()!=null){
				    		 displayResponsabile="";
				    		 displayAmmnistrazione="display:none;";
				   		 }
				   	 	 if(commedilizieAppello.getAmministrazioni().getId().getCodice()!=null){
				    		displayResponsabile="display:none;";
				    	    displayAmmnistrazione="";
				   	 	 }
				   	}
		    	}
		    
		    %>
			<table>
				<c:if test="${commedilizieappello.id.codice==null}">
				<tr>
					<td>
							<fmt:message key="label.responsabile" />
							<input type="radio" checked="checked" id="radio_responsabile" onclick="javascript:selectTipo('radio_responsabile');"/>
							<fmt:message key="label.amministrazione" />
							<input type="radio" id="radio_amministratore" onclick="javascript:selectTipo('radio_amministratore');"/>
					</td>
				</tr>
				</c:if>
				
				<tr id="responsabili_id" style="<%=displayResponsabile%>">
					<td>
						<fmt:message key="label.responsabile" />
					</td>
					<td>						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="responsabile" />
							<jsp:param name="propertyPath" value="responsabile" />										
							<jsp:param name="pathPropertyDescription" value="responsabile.responsabile" />
							<jsp:param name="pathPropertyCode" value="responsabile.id.codice" />
							<jsp:param name="autocompleterAjax" value="findResponsabili.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_responsabile" />
							<jsp:param name="autocompleterInputSize" value="65" />
						</jsp:include>
					</td>
				</tr>
				<tr id="amministratori_id" style="<%=displayAmmnistrazione%>">
					<td>
						<fmt:message key="label.amministrazione" />
					</td>
					<td>						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioni" />
							<jsp:param name="propertyPath" value="amministrazioni" />										
							<jsp:param name="pathPropertyDescription" value="amministrazioni.amministrazione" />
							<jsp:param name="pathPropertyCode" value="amministrazioni.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false" />							
							<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
							<jsp:param name="autocompleterInputSize" value="66" />
						</jsp:include>
					</td>
				</tr>
				
				<tr id="referente_id" style="<%=displayAmmnistrazione%>">
					<td>
						<fmt:message key="label.referente" />
					</td>
					<td>
						<spring-form:input id="referentefield_id" path="referente" size="69" />
						<spring-form:errors path="referente" cssClass="error"/>						
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.carica" />
					</td>
					<td>						
						<spring-form:select path="commedilizieCarica.id.codice">
							<spring-form:options items="${listCariche}" itemLabel="descrizione" itemValue="id.codice"/>
						</spring-form:select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.presente" />
					</td>
					<td>						
						<spring-form:select path="presente">
						    <c:if test="${commedilizieappello.presente == true}">
								<spring-form:option value="1"><fmt:message key="label.si"/> </spring-form:option>
								<spring-form:option value="0"><fmt:message key="label.no"/> </spring-form:option>
							</c:if>
							<c:if test="${commedilizieappello.presente == false}">
								<spring-form:option value="0"><fmt:message key="label.no"/> </spring-form:option>
								<spring-form:option value="1"><fmt:message key="label.si"/> </spring-form:option>
							</c:if>
						</spring-form:select>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
					function selectTipo(id)
					{
					
						if(id=='radio_responsabile')
						{
							document.getElementById('radio_amministratore').checked=false
							$('amministratori_id').fade();
							$('referentefield_id').value='';
							$('referente_id').fade();
							$('amministrazioni_id').value=''
							$('responsabili_id').appear();
							return true
						}
						if(id='radio_amministratore')
						{
							document.getElementById('radio_responsabile').checked=false
							$('responsabili_id').fade();
							$('responsabile_id').value='';
							$('amministratori_id').appear();
							$('referente_id').appear();
							return true
						}
						
					}
				</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${commedilizieappello.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${commedilizieappello.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceCommissione=${commedilizieappello.commissioniedilizieT.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>