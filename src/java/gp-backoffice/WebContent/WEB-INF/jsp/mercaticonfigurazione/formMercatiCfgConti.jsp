<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.mercati_cfg_conti" />	
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.mercati_cfg_conti" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="mercatiCfgConti" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatiCfgConti" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="label.mercaticategorie" /></td>
			<td>
				<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="mercatiCategorie_id" />
					<jsp:param name="propertyPath" value="mercatiCategorie" />
					<jsp:param name="pathPropertyDescription" value="mercatiCategorie.descrizione" />
					<jsp:param name="pathPropertyCode" value="mercatiCategorie.id.codice" />
					<jsp:param name="autocompleterAjax" value="findCategorieMercato.htm" />							
					<jsp:param name="titleKey" value="label.ricerca_categorie_mercato" />
				</jsp:include>

			</td>
		</tr>
	  <tr>
	       <td><fmt:message key="label.posteggio_settore" /></td>
	       <td class="inline-ui-cell">
		        <jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="posteggiSettori_id" />
					<jsp:param name="propertyPath" value="posteggiSettori" />
					<jsp:param name="pathPropertyDescription" value="posteggiSettori.settore" />
					<jsp:param name="pathPropertyCode" value="posteggiSettori.id.codice" />
					<jsp:param name="autocompleterAjax" value="findPosteggiSettore.htm" />							
					<jsp:param name="titleKey" value="label.posteggio_settore" />
				</jsp:include>
       	 </td>
      </tr>
      <tr>
	       <td><fmt:message key="form.mercatiConti.descrizione" /></td>
	       <td class="inline-ui-cell">
	        <jsp:include page="../includes/autocompletergenerico.jsp" >
				<jsp:param name="idElemento" value="conti_id" />
				<jsp:param name="propertyPath" value="conti" />
				<jsp:param name="pathPropertyDescription" value="conti.descrizione" />
				<jsp:param name="pathPropertyCode" value="conti.id.codice" />
				<jsp:param name="autocompleterAjax" value="findConti.htm" />							
				<jsp:param name="titleKey" value="label.ricerca_conto" />
			</jsp:include>
       	 </td>
      </tr>
     <tr>
		<td><fmt:message key="label.concessione_uso"/></td>
		<td>
			<jsp:include page="../includes/autocompletergenerico.jsp" >
				<jsp:param name="idElemento" value="concessioniuso_id" />
				<jsp:param name="propertyPath" value="concessioniuso" />
				<jsp:param name="pathPropertyDescription" value="concessioniuso.descrizione" />
				<jsp:param name="pathPropertyCode" value="concessioniuso.id.codice" />
				<jsp:param name="autocompleterAjax" value="findconcessioniuso.htm" />							
				<jsp:param name="titleKey" value="label.ricerca_concessioniuso" />
			</jsp:include>
			
		</td>
	</tr>
	<tr>
		<td>
			<fmt:message key="label.attivita" />
		</td>
		<td>
			<jsp:include page="../includes/autocompletergenerico.jsp" >
				<jsp:param name="idElemento" value="attivita_id" />
				<jsp:param name="propertyPath" value="attivita" />
				<jsp:param name="pathPropertyDescription" value="attivita.istat" />
				<jsp:param name="pathPropertyCode" value="attivita.id.codiceistat" />
				<jsp:param name="autocompleterAjax" value="findAttivita.htm?codicesettore=" />							
				<jsp:param name="titleKey" value="label.ricerca_attivita" />
			</jsp:include>
		</td>
	</tr>	
	
	  <tr>
			<td><fmt:message key="label.data_inizio" /></td>
			<td>
				<spring-form:input tabindex="3" id="datainizioval_id" path="datainizioval" size="10" maxlength="10" 
				onblur="isValidDate(this,true);"/> 
				<init:calendar imagePath="/images/cal.gif" idImage="calDatainizioval" 
				idInput="datainizioval_id" textKey="label.calendar"/>
		   		<spring-form:errors path="datainizioval" cssClass="error" />
			</td>
		</tr>    

    	<tr>
			<td><fmt:message key="label.data_fine" /></td>
			<td>
				<spring-form:input tabindex="3" id="datafineval_id" path="datafineval" size="10" maxlength="10" 
				onblur="isValidDate(this,true);"/> 
				<init:calendar imagePath="/images/cal.gif" idImage="calDatafineval" 
				idInput="datafineval_id" textKey="label.calendar"/>
		   		<spring-form:errors path="datafineval" cssClass="error" />
			</td>
		</tr>
		
      	<tr>
			<td><fmt:message key="label.importo" /></td>
			<td><spring-form:input id="importo_id" path="importo" size="10" maxlength="9"/>
			<spring-form:errors path="importo" cssClass="error" delimiter=", "/></td>
		</tr>
		
		<tr>
			<td><fmt:message key="label.calcola_con_mq" /></td>
			<td><spring-form:checkbox id="flagMoltiplicaMq_id" path="flagMoltiplicaMq" /> 
			<spring-form:errors	path="flagMoltiplicaMq" cssClass="error" /></td>
		</tr>
	</table>
	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${mercatiCfgConti.id.codice==null}">
		<li><a href="javascript:doSubmit('insertMercatiCfgConti.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${mercatiCfgConti.id.codice!=null}">
		<li><a href="javascript:doSubmit('updateMercatiCfgConti.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('deleteMercatiCfgConti.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO %>=%2Fmercaticonfigurazione%2FlistMercatiCfgConti.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
