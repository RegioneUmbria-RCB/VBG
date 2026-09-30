<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.nuovo_modello" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.nuovo_modello" />
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
			<div><fmt:message key="label.procedimento" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${tipimovimentidyn2modellit.tipimovimento.descrizioneEstesa}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="tipimovimentidyn2modellit" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipimovimentidyn2modellit" />
		    </jsp:include>
			<table>
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
				
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertModelli.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('listmodelli.htm?codicemovimento=${tipimovimentidyn2modellit.tipimovimento.id.tipomovimento}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>