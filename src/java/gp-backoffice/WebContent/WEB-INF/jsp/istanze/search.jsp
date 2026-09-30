<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="form.istanze.title.search" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="form.istanze.title.search" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include> 
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanze/search" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="istanzeCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzeCommand" />
		    </jsp:include>
			<fieldset>
			<legend><fmt:message key="form.istanze.search.legend" /></legend>
	    	<table width="100%">
				<tr>
					<td>
						<fmt:message key="form.istanze.data" />
					</td>
					<td class="inline-ui-cell" style="max-width: 80px;">
						<fmt:message key="form.istanze.data.inizio" />
					</td>
					<td class="inline-ui-cell" >
						<spring-form:input tabindex="1" id="dataInizio_id" path="istanzeFilter.dallaData" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dataInizio_id" textKey="label.calendar"/>
					</td>
					<td class="inline-ui-cell" style="max-width: 80px;">
						<fmt:message key="form.istanze.data.fine" />
					</td>
					<td class="inline-ui-cell" >
						<spring-form:input tabindex="3" id="dataFine_id" path="istanzeFilter.allaData" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="dataFine_id" textKey="label.calendar"/>
						<spring-form:errors path="istanzeFilter.allaData" cssClass="error" delimiter=" :"/>  
					</td>
				</tr>
				<tr>
					<td valign="top"><fmt:message key="form.istanze.alberoproc" /></td>
					<td colspan="4" class="inline-ui-cell">					    
						<jsp:include page="../includes/searchAlberoProc.jsp">
							<jsp:param name="propertyPath" value="istanzeFilter.alberoproc." />								
							<jsp:param name="pathPropertyDescription" value="istanzeFilter.alberoproc.vwAlberoproc.scDescrizione" />
							<jsp:param name="pathPropertyCode" value="istanzeFilter.alberoproc.id.codice" />
							<jsp:param name="isSelectLeafDisable" value="tru" />
							<jsp:param name="isSelectNodoPadre" value="true" />
						</jsp:include>
						
						<%-- 
							Campo nascosto per evitare errore di validazione: siccome visualizziamo alberoproc.vwAlberoproc.scDescrizione
							e l'oggetto di dominio ha il controllo di validazione @Valid allora trovando alberoproc.scDescrizione vuoto o nullo
							dà errore nella validazione (vedi it.gruppoinit.pal.gp.core.domain.Alberoproc.getVwAlberoproc())
						--%>
						<spring-form:hidden	id="alberoproc_hidden_descrizione" path="istanzeFilter.alberoproc.scDescrizione"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.movimento_eseguito" /></td>
					<td colspan="4">
						<jsp:include page="../includes/tipimovimentosearch.jsp" >
							<jsp:param name="idElemento" value="tipoMovimentoInputId" />
							<jsp:param name="pathTipomovimento" value="istanzeFilter.tipoMovimentoFattoPerInserimentoMassivo" />
							<jsp:param name="includiDisabilitate" value="true" />
							<jsp:param name="tipimovimentoInputSize" value="100" />
						</jsp:include>
						<br />
						<fmt:message key="label.movimento_eseguito.inserimento_massivo.help" />				
										
					</td>
				</tr>
				<tr>
					<td><fmt:message key="form.istanze.movimentoDaInserire.legend" /></td>
					<td colspan="4">
					
						<jsp:include page="../includes/tipimovimentosearch.jsp" >
							<jsp:param name="idElemento" value="tipoMovimento" />
							<jsp:param name="pathTipomovimento" value="istanzeFilter.tipoMovimento" />
							<jsp:param name="includiDisabilitate" value="true" />
							<jsp:param name="tipimovimentoInputSize" value="100" />
						</jsp:include>
						<%--
							<spring-form:textarea id="tipoMovimento_id"  tabindex="6" path="istanzeFilter.tipoMovimento.movimento" cssClass="searchbox" onchange="checkValue(this,'tipoMovimento_hidden')" onkeydown="javascript:return searchAll(this,event)" cols="62" rows="2" />
							<init:autocompleter methodAjax="findTipiMovimento.htm" idHidden="tipoMovimento_hidden" idInput="tipoMovimento_id" inputTitleKey="label.ricerca_tipo_movimento"/>
							<spring-form:hidden	id="tipoMovimento_hidden" path="istanzeFilter.tipoMovimento.id.tipomovimento"/>
						--%>
						<br />
						<fmt:message key="form.istanze.movimentoDaInserire.help" />						
						<spring-form:errors path="istanzeFilter.tipoMovimento.movimento" cssClass="error"/> 
					</td>
				</tr>
	    	</table>
			</fieldset>
	    </spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a tabindex="7"  href="javascript:goToIstanzeList();"><fmt:message key="button.search" /></a></li>
			<li><a tabindex="8"  href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				
		</ul>
	</div>
	<script type='text/javascript'>
	//<![CDATA[
	$('dataInizio_id').focus();
	function goToIstanzeList(){
		var goToUrl = "../istanze/list.htm?1=1";
		goToUrl = URLEncode(goToUrl);
		doSubmit('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'',document.inviodati,'GET',true);
					
	}
	//]]>
	</script>
</body>
</html>