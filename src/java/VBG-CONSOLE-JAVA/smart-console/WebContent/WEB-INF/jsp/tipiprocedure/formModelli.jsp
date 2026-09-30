<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipiprocedureDyn2modellit.id.fkD2mtId==null}">
			<fmt:message key="tipiprocedureDyn2modellit.label.nuovo_tipiprocedureDyn2modellit.title" />
		</c:if> 
		<c:if test="${tipiprocedureDyn2modellit.id.fkD2mtId!=null}">			
			<fmt:message key="tipiprocedureDyn2modellit.label.dettaglio_tipiprocedureDyn2modellit.title" />
		</c:if>			
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${tipiprocedureDyn2modellit.id.fkD2mtId==null}">
			<fmt:message key="tipiprocedureDyn2modellit.label.nuovo_tipiprocedureDyn2modellit.title" />
		</c:if> 
		<c:if test="${tipiprocedureDyn2modellit.id.fkD2mtId!=null}">
			<fmt:message key="tipiprocedureDyn2modellit.label.dettaglio_tipiprocedureDyn2modellit.title" />
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
			<div><fmt:message key="label.procedura" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${tipiprocedureDyn2modellit.tipiprocedure.procedura}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="tipiprocedureDyn2modellit" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipiprocedureDyn2modellit" />
		    </jsp:include>
			<table>
				<c:if test="${!view}">
					<tr>
						<td>
							<fmt:message key="label.modello" />
						</td>
						<td>
							<div id="id1" style="<%=swSettato%>"><spring-form:input id="modello_id1" path="dyn2Modellit.descrizione" cssClass="searchbox" size="60" onchange="checkValue(this,'modello_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findDyn2ModelliCurretSoftwareOrTT.htm?codicesoftware=TT'  idHidden="modello_hidden"  idInput="modello_id1" inputTitleKey="label.ricerca_modelli"></init:autocompleter></div>
						    <div id="id2" style="<%=swTT%>"><spring-form:input id="modello_id2" path="dyn2Modellit.descrizione" cssClass="searchbox" size="60" onchange="checkValue(this,'modello_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findDyn2ModelliCurretSoftwareOrTT.htm'  idHidden="modello_hidden"  idInput="modello_id2" inputTitleKey="label.ricerca_modelli"></init:autocompleter></div>  
							<spring-form:errors path="dyn2Modellit" cssClass="error"/> 
							<spring-form:hidden id="modello_hidden" path="dyn2Modellit.id.codice"  />
							<input type="checkbox" id="id_flag" onclick="tuttiSw();"/>
		                	<init:help idHelp="help1" textKey="help.modelli_archivi_base"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${view}">
					<tr>
						<td><fmt:message key="label.modello" /></td>
						<td><spring-form:input id="modello_id" path="dyn2Modellit.descrizione" size="70" disabled="true" />
						<spring-form:errors path="dyn2Modellit" cssClass="error"/></td>
					</tr>
				</c:if>				
				<tr>
					<td><fmt:message key="label.pubblica" /></td>
					<td>
						<spring-form:checkbox id="flagPubblica_id" path="flagPubblica"/>						
						<spring-form:errors path="flagPubblica" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.modello_facoltativo" /></td>
					<td>
						<spring-form:checkbox id="flagFacoltativa_id" path="flagFacoltativa"/>						
						<spring-form:errors path="flagFacoltativa" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.tipo_firma" /></td>
					<td>
						<spring-form:select path="flagTipofirma" > 
							<spring-form:option value="0" ><fmt:message key='label.no_firma' /></spring-form:option>
							<spring-form:option value="1"><fmt:message key='label.firma_modello' /></spring-form:option>
							<spring-form:option value="2" ><fmt:message key='label.firma_ogni_blocco' /></spring-form:option>
						</spring-form:select>
						<init:help idHelp="help2" textKey="tipiprocedureDyn2modellit.help.flagTipofirma"/>	
						<spring-form:errors path="flagTipofirma" cssClass="error"/> 
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.ordine" /></td>
					<td><spring-form:input id="ordine_id" path="ordine" size="5" />
					<spring-form:errors path="ordine" cssClass="error"/></td>
				</tr>							
			</table>
			<script type='text/javascript'>			
				mostraerrore(${flagmultiplo});
				function mostraerrore(flagmultiplo){
					if(flagmultiplo==false){
						alert('<fmt:message key="tipiprocedureDyn2modellit.label.errore_flagmultiplo" />');						
					}
				}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${!view}">
				<li><a href="javascript:doSubmit('insertModelli.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${view}">
				<li><a href="javascript:doSubmit('updateModelli.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
			<c:if test="${tipiprocedureDyn2modellit.id.fkD2mtId!=null}">				
				<li><a href="javascript:doSubmit('deleteModelli.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listmodelli.htm?codicetipoprocedura=${tipiprocedureDyn2modellit.tipiprocedure.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>