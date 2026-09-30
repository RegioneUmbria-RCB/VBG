<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${inventarioprocedimenti.inventarioprocedimentiincomp.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_endo_incompatibili.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.inventarioprocedimentiincomp.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_endo_incompatibili.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${inventarioprocedimenti.inventarioprocedimentiincomp.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_endo_incompatibili.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.inventarioprocedimentiincomp.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_endo_incompatibili.title" />
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
			<table>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.endo_incompatibile" />
					</td>
					<td class="inline-ui-cell">
						<div id="id1" style="<%=swSettato%>"><spring-form:input id="endo_incompatibile_id1" path="inventarioprocedimentiincomp.inventarioprocedimentoincompatibile.procedimento" cssClass="searchbox" size="60" onchange="checkValue(this,'endo_incompatibile_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findInventarioprocedimentoAndSoftware.htm?codicesoftware=TT'  idHidden="endo_incompatibile_hidden"  idInput="endo_incompatibile_id1" inputTitleKey="label.ricerca_endoprocedimento"></init:autocompleter></div>
						<div id="id2" style="<%=swTT%>"><spring-form:input id="endo_incompatibile_id2" path="inventarioprocedimentiincomp.inventarioprocedimentoincompatibile.procedimento" cssClass="searchbox" size="60" onchange="checkValue(this,'endo_incompatibile_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findInventarioprocedimentoAndSoftware.htm'  idHidden="endo_incompatibile_hidden"  idInput="endo_incompatibile_id2" inputTitleKey="label.ricerca_endoprocedimento"></init:autocompleter></div>
						<spring-form:errors path="inventarioprocedimentiincomp.inventarioprocedimentoincompatibile" cssClass="error"/> 
						<spring-form:hidden id="endo_incompatibile_hidden" path="inventarioprocedimentiincomp.inventarioprocedimentoincompatibile.id.codice"  />
						<input type="checkbox" id="id_flag" onclick="tuttiSw();"/>
	                	<init:help idHelp="help1" textKey="help.endo_procedimenti_archivi_base"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertEndoincompatibili.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('listendoincompatibili.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>