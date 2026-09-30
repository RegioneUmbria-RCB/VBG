<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="form.mercatiPresenzeT.title.search" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.mercatipresenzeT.title.search" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="mercatipresenzeT" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatipresenzeT" />
    </jsp:include>
    <table>
		<tr>
			<td><fmt:message key="form.mercatipresenzeT.anno" /></td>
			<td class="inline-ui-cell" valign="middle" style="vertical-align: middle;"><spring-form:input id="anno_id" path="anno" size="6"/>
			    <button type="button" class="functionsPlus btn btn-primary btn-xs" onclick="javascript:annoplus();" title="<fmt:message key="form.calendariomercatoParametri.anno.plus" />">+</button>
          		<button type="button" class="functionsMinus btn btn-primary btn-xs" onclick="javascript:annominus();" title="<fmt:message key="form.calendariomercatoParametri.anno.minus" />">-</button>			
				<spring-form:errors path="anno" cssClass="error" delimiter=","/></td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatipresenzeT.mercati" />
			</td>
			<td>
				<spring-form:input id="mercati_id" path="mercato.descrizione" cssClass="searchbox" onchange="checkValue(this,'mercati_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67"/>
				<init:autocompleter methodAjax="findMercati.htm" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
				<spring-form:errors path="mercato" cssClass="error"/> 
				<spring-form:hidden id="mercati_hidden" path="mercato.id.codice" />
			</td>
		</tr>
	</table>
	<script type='text/javascript'>
		$('mercati_id').focus();	
	</script>	
</spring-form:form>
</div>
<script type="text/javascript">

     function annoplus()
     {
         var year=parseFloat($('anno_id').value)+parseFloat(1);
         $('anno_id').value = year;
     }
     function annominus()
     {
    	 var year=parseFloat($('anno_id').value)-parseFloat(1);
    	 $('anno_id').value = year;        
     }
	
</script>

<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('search.htm','',document.inviodati)"><fmt:message key="button.search" /></a></li>
	
	<c:if test="${ GESTIONE_POSIZIONI_DEBITORIE eq true}">
		<li><a href="javascript:doSubmit('../gestionepresenze/sistemaPosizioniDebitorieConcessionariDelGiorno.htm','',document.inviodati)"><fmt:message key="button.sistema_posizioni_concessionari" /></a></li>
	</c:if>
	
	
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
