<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${inventarioprocedimenti.inventarioprocdyn2modellit.id.fkD2mtId==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_modello.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.inventarioprocdyn2modellit.id.fkD2mtId!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_modello.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${inventarioprocedimenti.inventarioprocdyn2modellit.id.fkD2mtId==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_modello.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.inventarioprocdyn2modellit.id.fkD2mtId!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_modello.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<script type="text/javascript">
	function tuttiSw(){
		if($('id_flag').checked){
		    $('id1').style.display="inline";
		    $('id2').style.display="none";
		}else
		{
			$('id1').style.display="none";
			$('id2').style.display="inline";
		}
	}	
	</script>
	<%
     String  swSettato="display:none;";
     String  swTT="display:inline;";
	%>
	<div id="subcontent">
	<div class="parametriDiv">
   		<div class="etichetta">
			<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${inventarioprocedimenti.entity.procedimento}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="inventarioprocedimenti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="inventarioprocedimenti" />
		    </jsp:include>
			<table >
				<tr>
					<td width="20%">
						<fmt:message key="inventarioprocedimenti.label.modello" />
					</td>
					<c:if test="${view eq false}">
					<td class="inline-ui-cell">
						<div id="id1" style="<%=swSettato%>"><spring-form:input id="modello_id1" path="inventarioprocdyn2modellit.dyn2Modellit.descrizione" cssClass="searchbox" size="60" onchange="checkValue(this,'modello_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findDyn2ModelliCurretSoftwareOrTT.htm?codicesoftware=TT'  idHidden="modello_hidden"  idInput="modello_id1" inputTitleKey="label.ricerca_modelli"></init:autocompleter></div>
						<div id="id2" style="<%=swTT%>"><spring-form:input id="modello_id2" path="inventarioprocdyn2modellit.dyn2Modellit.descrizione" cssClass="searchbox" size="60" onchange="checkValue(this,'modello_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findDyn2ModelliCurretSoftwareOrTT.htm'  idHidden="modello_hidden"  idInput="modello_id2" inputTitleKey="label.ricerca_modelli"></init:autocompleter></div>
						<spring-form:errors path="inventarioprocdyn2modellit.dyn2Modellit" cssClass="error"/> 
						<spring-form:hidden id="modello_hidden" path="inventarioprocdyn2modellit.dyn2Modellit.id.codice"  />
						<input type="checkbox" id="id_flag" onclick="tuttiSw();"/>
	                	<init:help idHelp="help1" textKey="help.modelli_archivi_base"/>
					</td>
					</c:if>
					<c:if test="${view eq true}">
						<td><spring-form:input id="modello_id2" path="inventarioprocdyn2modellit.dyn2Modellit.descrizione" size="90" readonly="true"/></td>
					</c:if>
					
				</tr>
				<tr>
					<td><fmt:message key="label.pubblica" /></td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flagPubblica_id" path="inventarioprocdyn2modellit.flagPubblica"/>						
						<spring-form:errors path="inventarioprocdyn2modellit.flagPubblica" cssClass="error" />
					</td>
				</tr>
				<c:if test="${isFvgSolAttivaAndConsole}">
				<tr>
					<td><fmt:message key="label.fvg_mostra_nel_back" /></td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flagFvgMostraNelBack_id" path="inventarioprocdyn2modellit.flagFvgMostraNelBack"/>						
						<spring-form:errors path="inventarioprocdyn2modellit.flagFvgMostraNelBack" cssClass="error" />
					</td>
				</tr>
				</c:if>
				<tr>
					<td><fmt:message key="label.modello_facoltativo" /></td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flagFacoltativa_id" path="inventarioprocdyn2modellit.flagFacoltativa"/>						
						<spring-form:errors path="inventarioprocdyn2modellit.flagFacoltativa" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.tipo_firma" /></td>
					<td class="inline-ui-cell">
						<spring-form:select path="inventarioprocdyn2modellit.flagTipofirma" > 
							<spring-form:option value="0" ><fmt:message key='inventarioprocedimenti.label.no_firma' /></spring-form:option>
							<spring-form:option value="1"><fmt:message key='inventarioprocedimenti.label.firma_modello' /></spring-form:option>
							<spring-form:option value="2" ><fmt:message key='inventarioprocedimenti.label.firma_ogni_blocco' /></spring-form:option>
						</spring-form:select>
						<init:help idHelp="help2" textKey="inventarioprocedimenti.help.flagTipofirma"/>	
						<spring-form:errors path="inventarioprocdyn2modellit.flagTipofirma" cssClass="error"/> 
					</td>
				</tr>
				
				<tr>
					<td><fmt:message key="label.mostra_tipo_localizzazione" /></td>
					<td class="inline-ui-cell">
						<spring-form:select path="inventarioprocdyn2modellit.tipiLocalizzazioni.id.codice" > 
								<spring-form:option value="" ><fmt:message key='label.seleziona' /></spring-form:option>
							<c:forEach items="${tipiLocalizzazionis}" var="tipolocalizzazione">
								<spring-form:option value="${tipolocalizzazione.id.codice}">${tipolocalizzazione.descrizione}</spring-form:option>
							</c:forEach>
						</spring-form:select>
						<init:help idHelp="help_tipo_localizzazione" textKey="inventarioprocedimenti.help.tipiLocalizzazioni"/>	
						<spring-form:errors path="inventarioprocdyn2modellit.tipiLocalizzazioni" cssClass="error"/> 
					</td>
				</tr>
								

				
				<tr>
					<td><fmt:message key="label.ordine" /></td>
					<td class="inline-ui-cell">
						<spring-form:input id="ordine_id" path="inventarioprocdyn2modellit.ordine" size="5" />
						<spring-form:errors path="inventarioprocdyn2modellit.ordine" cssClass="error"/>
					</td>
				</tr>					
			</table>
			<script type='text/javascript'>
			mostraerrore(${flagmultiplo});
			function mostraerrore(flagmultiplo){
				if(flagmultiplo==false){
					alert('<fmt:message key="alberoprocDyn2modellit.label.errore_flagmultiplo" />');						
				}
			}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			
			
		    
		    <c:if test="${view eq false}">
				<li><a href="javascript:doSubmit('insertModelli.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${view eq true}">
				<li><a href="javascript:doSubmit('updateModelli.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
			<c:if test="${inventarioprocedimenti.inventarioprocdyn2modellit.id.fkD2mtId!=null}">				
				<li><a href="javascript:doSubmit('deleteModelli.htm?codicemodellot=${alberoprocDyn2modellit.id.fkD2mtId}&codiceprocedimento=${alberoprocDyn2modellit.id.fkScId}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listmodelli.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
		
		</ul>
	</div>
</body>
</html>