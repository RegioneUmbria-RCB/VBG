<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${command.mercatidLetture.id.codice==null}">
			<fmt:message key="form.mercatidLetture.title.create" />
		</c:if> 
		<c:if test="${command.mercatidLetture.id.codice!=null}">
			<fmt:message key="form.mercatidLetture.title.view" />
		</c:if>
	</title>
</head>
<body>

<script type='text/javascript'>
	if($('dataLetturaCerca')){
		$('dataLetturaCerca').focus();
	}

	function changeValue(obj){
		var importo=obj.value;
		if(isNaN(importo.replace(",","."))){
			alert('<fmt:message key="alert.field.numeric" />');
			obj.value = '';
			return;
		}
		obj.value = importo.replace(".",",");
	}
</script>
<span class="titoloPagina">
	<fmt:message key="form.mercatidLetture.title.create" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>
<div id="subcontent">
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatidletture/createLettureContatore" />
	</jsp:include>
    <span class="parametri"><fmt:message key="form.mercatidLetture.mercato" />:<label>${mercato.descrizione}</label></span>
    <span class="parametri"><fmt:message key="form.mercatidLetture.mercatoUso" />:<label>${uso.descrizione}</label></span>
    <br />
    <fieldset><legend><fmt:message key="form.mercatidLetture.filtri"></fmt:message></legend>
	<spring-form:form commandName="command" name="inviodati">
    <table>
		<tr>
			<td width="5%">
				<fmt:message key="form.mercatidLetture.datalettura" />				
				<spring-form:select tabindex="1" id="dataLetturaCerca" path="mercatidLetture.dataLettura"> 
					<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
					<spring-form:options items="${dataLetturaList}" />
				</spring-form:select>
			</td>
			<td width="5%">
				<fmt:message key="form.mercatidLetture.tipicontatore" />
				<spring-form:select tabindex="2" id="tipiContatore" path="mercatidLetture.tipiContatore.id" >
					<spring-form:options items="${tipicontatoreList}" itemLabel="descrizione" itemValue="id"/>
				</spring-form:select>
			</td>
			<td width="5%"><fmt:message key="form.mercatidLetture.dalladata" />
				<spring-form:input tabindex="3" id="datainizio_id" path="mercatidLetture.dataInizio" size="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="datainizio_id" textKey="label.calendar"/>
			</td>
	
			<td width="5%">
				<fmt:message key="form.mercatidLetture.alladata" />
				<spring-form:input tabindex="4" id="datafine_id" path="mercatidLetture.dataFine" size="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="datafine_id" textKey="label.calendar"/>
			</td>
	
			<td width="5%">
				<fmt:message key="form.mercatidLetture.anno" />
				<spring-form:input tabindex="5" id="anno" path="mercatidLetture.anno" size="4" maxlength="4"  onblur="checkNumberValue(this);"/>
			</td>
		</tr>
	</table>
	<div id="functions">
	<ul>
		<li><a href="javascript:doSubmit('searchLettureContatori.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}','',document.inviodati)" tabindex="6"><fmt:message key="button.search" /></a></li>
	</ul>
	</div>
<br class="clear"/>
</spring-form:form>
</fieldset>
<%
String displayriga="display:none;";
%>

 <fieldset><legend><fmt:message key="form.mercatidLetture.dettaglioletture"></fmt:message></legend>
	<!-- START Tabella principale --> 
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="command" />
    </jsp:include>
    <div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
                <td width="10%" ><fmt:message key="form.mercatidLetture.posteggio" /></td>
                <td width="10%" ><fmt:message key="form.mercatidLetture.datalettura" /></td>
                <td width="10%" ><fmt:message key="form.mercatidLetture.dalladata" /></td>
                <td width="10%"><fmt:message key="form.mercatidLetture.alladata" /></td>
                <td width="10%" ><fmt:message key="form.mercatidLetture.presunta" /></td>
                <td width="10%" align="right"><fmt:message key="form.mercatidLetture.letturainiziale" /></td>
                <td width="10%" align="right"><fmt:message key="form.mercatidLetture.letturafinale" /></td>
                <td width="10%" align="right"><fmt:message key="form.mercatidLetture.consumo" /></td>
                <td width="10%" align="right"><fmt:message key="form.mercatidLetture.importo" /></td>
                <td width="5%"></td>
    		</tr>
		</thead>
		<tbody class="tbody">
		<%int i=1;%>
		<c:set value="6" var="tabindex"></c:set>
		<spring-form:form commandName="command" name="inviodatiLetture">
			<c:forEach items="${command.mercatidLettureList}" var="letture_var" varStatus="status" >
				<%
					pageContext.setAttribute("iCount", i);
				%>
				<tr class= "<%=(i%2)==0?"odd":"even"%>">
				   <td>${letture_var.posteggio.codiceposteggio}</td>
				<spring:bind path="mercatidLettureList[${status.index}].dataLettura" >
				<c:set value="${tabindex+1}" var="tabindex"></c:set>
                   <td>
                   		<input type="text" tabindex="${tabindex}" id="idDataLettura${iCount}" name="${status.expression}"  value="<fmt:formatDate value="${letture_var.dataLettura}" pattern="dd/MM/yyyy"/>" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
                   		<init:calendar imagePath="/images/cal.gif" idImage="calDataLettura${iCount}" idInput="idDataLettura${iCount}" textKey="label.calendar"/>
                  </td>
                </spring:bind>
                <spring:bind path="mercatidLettureList[${status.index}].dataInizio" >
                <c:set value="${tabindex+1}" var="tabindex"></c:set>
               	   <td>
               	   		<input type="text" tabindex="${tabindex}" id="idDataInizio${iCount}" name="${status.expression}"  value="<fmt:formatDate value="${letture_var.dataInizio}" pattern="dd/MM/yyyy"/>" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
               	   		<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio${iCount}" idInput="idDataInizio${iCount}" textKey="label.calendar"/>
               	   </td>
                </spring:bind>
                <spring:bind path="mercatidLettureList[${status.index}].dataFine" >
                <c:set value="${tabindex+1}" var="tabindex"></c:set>
                   <td>
                   		<input type="text" tabindex="${tabindex}" id="idDataFine${iCount}" name="${status.expression}"  value="<fmt:formatDate value="${letture_var.dataFine}" pattern="dd/MM/yyyy"/>" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
                   		<init:calendar imagePath="/images/cal.gif" idImage="calDataFine${iCount}" idInput="idDataFine${iCount}" textKey="label.calendar"/>
                   </td>
                </spring:bind>
                <spring:bind path="mercatidLettureList[${status.index}].presunta" >
                <c:set value="${tabindex+1}" var="tabindex"></c:set>
               	   <td>
	               	   <spring-form:select  path="${status.expression}"  tabindex="${tabindex}" >
		               	  <spring-form:option value="<%=WebConstants.MERCATIDLETTURE_PRESUNTA_PRESUNTA%>"><fmt:message key="form.mercatidLetture.label.presunta" /></spring-form:option>
	                   	  <spring-form:option value="<%=WebConstants.MERCATIDLETTURE_PRESUNTA_EFFETTIVA%>"><fmt:message key="form.mercatidLetture.label.effettiva" /></spring-form:option>
	               	   </spring-form:select>
	               </td>     
				</spring:bind>
				<spring:bind path="mercatidLettureList[${status.index}].letturaIniziale" >
				<c:set value="${tabindex+1}" var="tabindex"></c:set>
				   <td align="right"><input type="text" tabindex="${tabindex}" name="${status.expression}"  value="<fmt:formatNumber minFractionDigits="5" value="${letture_var.letturaIniziale}"/>" style="text-align: right;padding: 0 2px 0 0; " maxlength="10" size="10" onblur="checkNumberValue(this);"/></td>
				</spring:bind>
				<spring:bind path="mercatidLettureList[${status.index}].letturaFinale" >
				<c:set value="${tabindex+1}" var="tabindex"></c:set>
                   <td align="right"><input type="text" tabindex="${tabindex}" name="${status.expression}"  value="<fmt:formatNumber minFractionDigits="5" value="${letture_var.letturaFinale}"/>" style="text-align: right;padding: 0 2px 0 0; "  maxlength="10" size="10" onblur="checkNumberValue(this);"/></td>
                </spring:bind>
                <spring:bind path="mercatidLettureList[${status.index}].consumo" >
               <c:set value="${tabindex+1}" var="tabindex"></c:set>
                   <td align="right"><input type="text" tabindex="${tabindex}" name="${status.expression}"  value="<fmt:formatNumber minFractionDigits="5" value="${letture_var.consumo}"/>" style="text-align: right;padding: 0 2px 0 0;"  maxlength="10" size="10" onblur="checkNumberValue(this);" /></td>  
                </spring:bind>
                <spring:bind path="mercatidLettureList[${status.index}].importo" >
                <c:set value="${tabindex+1}" var="tabindex"></c:set>
				   <td align="right"><input type="text" tabindex="${tabindex}" name="${status.expression}"  value="<fmt:formatNumber minFractionDigits="2" value="${letture_var.importo}" />" style="text-align: right;padding: 0 2px 0 0;" maxlength="10" size="10" onblur="checkNumberValue(this);"/></td>
				</spring:bind>
				<c:set value="${tabindex+1}" var="tabindex"></c:set>
                   <td>
                   <a href="deleteLettureContatori.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}&mercatidLetture.id.codice=${letture_var.id.codice }" tabindex="${tabindex}" >
                  	 <img src="${pageContext.request.contextPath}/images/cross.gif" title="<fmt:message key="button.mercatidLetture.elimina" />" />
                   </a>
                   </td>
			   </tr>
			   <%i++; %>
			   </c:forEach>
			   </spring-form:form>
			   <c:if test="${newLettura eq true}">
			   <spring-form:form commandName="mercatidLetture" name="inviodatiRigaLetture" modelAttribute="mercatidLetture">
			   <jsp:include page="../includes/displayGlobalMessages.jsp" >
        			<jsp:param name="commandName" value="mercatidLetture"/>
    			</jsp:include>
    			
			   <tr id="insertRiga" class= "<%=(i%2)==0?"odd":"even"%>" >
			   <c:set value="${tabindex+1}" var="tabindex"></c:set>
				   <td>
				   	  <spring-form:select tabindex="${tabindex}"  path="posteggio.id.codice">
	                   	<spring-form:options items="${posteggi}" itemLabel="codiceposteggio" itemValue="id.codice"/>
	                   </spring-form:select>
	                   <spring-form:errors path="posteggio.id.codice" cssClass="error" />
    			   </td>
				   <td><c:set value="${tabindex+1}" var="tabindex"></c:set>
				   		<spring-form:input tabindex="${tabindex}" id="dataLetturaid" path="dataLettura" size="10" onblur="isValidDate(this,true);"/>
				   		<init:calendar imagePath="/images/cal.gif" idImage="dataLetturaCal" idInput="dataLetturaid" textKey="label.calendar"/>
				   		<spring-form:errors path="dataLettura" cssClass="error"/>
				   </td>
                   <td><c:set value="${tabindex+1}" var="tabindex"></c:set>
                   		<spring-form:input tabindex="${tabindex}" id="dataInizioid"  path="dataInizio" size="10" onblur="isValidDate(this,true);"/>
                   		<init:calendar imagePath="/images/cal.gif" idImage="dataInizioCal" idInput="dataInizioid" textKey="label.calendar"/>
						<spring-form:errors path="dataInizio" cssClass="error"/>     
                   </td>
                   <td><c:set value="${tabindex+1}" var="tabindex"></c:set>
                   		<spring-form:input tabindex="${tabindex}" id="dataFineid" path="dataFine" size="10" onblur="isValidDate(this,true);"/>
                   		<init:calendar imagePath="/images/cal.gif" idImage="dataFineCal" idInput="dataFineid" textKey="label.calendar"/>
						<spring-form:errors path="dataFine" cssClass="error"/>     
                   </td>
                   <td><c:set value="${tabindex+1}" var="tabindex"></c:set>
	                   <spring-form:select tabindex="${tabindex}" path="presunta">
	                   	<spring-form:option value="<%=WebConstants.MERCATIDLETTURE_PRESUNTA_PRESUNTA%>"> <fmt:message key="form.mercatidLetture.label.presunta" /></spring-form:option>
	                   	<spring-form:option value="<%=WebConstants.MERCATIDLETTURE_PRESUNTA_EFFETTIVA%>"> <fmt:message key="form.mercatidLetture.label.effettiva" /></spring-form:option>
	                   </spring-form:select>
                   </td>
				   <td align="right"><c:set value="${tabindex+1}" var="tabindex"></c:set>
				   		<spring-form:input tabindex="${tabindex}" path="letturaIniziale" cssStyle="text-align: right;padding: 0 2px 0 0;" maxlength="10" size="10"  onblur="checkNumberValue(this);" />
				   		<spring-form:errors path="letturaIniziale" cssClass="error" />
				   </td>     
				   <td align="right"><c:set value="${tabindex+1}" var="tabindex"></c:set>
				   		<spring-form:input tabindex="${tabindex}" path="letturaFinale" cssStyle="text-align: right;padding: 0 2px 0 0;" maxlength="10" size="10" onblur="checkNumberValue(this);"  />
				   		<spring-form:errors path="letturaFinale" cssClass="error" />
				   </td>
                   <td align="right"> <c:set value="${tabindex+1}" var="tabindex"></c:set>
                   		<spring-form:input tabindex="${tabindex}"  path="consumo" cssStyle="text-align: right;padding: 0 2px 0 0;" maxlength="10" size="10" onblur="checkNumberValue(this);" />
						<spring-form:errors path="consumo" cssClass="error" />
					</td> 
				   <td align="right"><c:set value="${tabindex+1}" var="tabindex"></c:set>
				   		<spring-form:input tabindex="${tabindex}" path="importo" cssStyle="text-align: right;padding: 0 2px 0 0;" maxlength="10" size="10" onblur="checkNumberValue(this);" />
				   		<spring-form:errors path="importo" cssClass="error" />
				   </td>
                   <td><c:set value="${tabindex+1}" var="tabindex"></c:set>
                   <input type="hidden" value="${command.mercatidLetture.tipiContatore.id}" name="tipicontatoreRiga"/>
                   <a href="javascript:doSubmit('insertLettureContatori.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}','',document.inviodatiRigaLetture)" tabindex="${tabindex}">
                  	 <img src="${pageContext.request.contextPath}/images/save.gif" title="<fmt:message key="button.mercatidLetture.save" />" />
                   </a>
                   </td>
			   </tr>
			   </spring-form:form>
			   </c:if>
		</tbody>
	</table>
	</div>
	<c:set value="${tabindex+1}" var="tabindex"></c:set>
	<a href="javascript:doHref('createRigaLettureContatori.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}','')" tabindex="${tabindex}">
		<img  src="${pageContext.request.contextPath}/images/add.gif" title="<fmt:message key="button.mercatidLetture.aggiungilettura" />" />
	</a>

</fieldset>
<script type="text/javascript">
			var goToUrl = "../mercatidletture/createNuovaLettura.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}";
			goToUrl = escape(goToUrl);
			var goToUrlImport = "../mercatidletture/createImport.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}";
			goToUrlImport = escape(goToUrlImport);
			var goToUrlReg = "../mercatidletture/createRegistrazioni.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}";
			goToUrlReg = escape(goToUrlReg);
		</script>
<div id="functions">
	<ul><c:set value="${tabindex+1}" var="tabindex"></c:set>
		<li><a href="javascript:doSubmit('updateLettureContatori.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}','',document.inviodatiLetture)" tabindex="${tabindex}"><fmt:message key="button.update" /></a></li>		
		<c:set value="${tabindex+1}" var="tabindex"></c:set>
		<li><a href="exportLettureContatore.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}" tabindex="${tabindex}"><fmt:message key="button.exportexcel" /></a></li>		
		<c:set value="${tabindex+1}" var="tabindex"></c:set>
		<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlImport,'')" tabindex="${tabindex}"><fmt:message key="button.importexcel" /></a></li>
		<c:set value="${tabindex+1}" var="tabindex"></c:set>
		<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'')" tabindex="${tabindex}"><fmt:message key="button.nuovalettura" /></a></li>			
		<c:set value="${tabindex+1}" var="tabindex"></c:set>
		<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlReg,'')" tabindex="${tabindex}"><fmt:message key="button.registrazioniconatbili" /></a></li>			
		<c:set value="${tabindex+1}" var="tabindex"></c:set>
		<li><a href="javascript:doHref('../contabilitamercati/createSearch.htm','')" tabindex="${tabindex}"><fmt:message key="button.back" /></a></li>			
	</ul>
</div>
</div>
</body>
</html>