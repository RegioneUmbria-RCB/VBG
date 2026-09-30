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
<span class="titoloPagina">
	<fmt:message key="form.mercatidLetture.nuovalettura.title.create" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="command" />
    </jsp:include>
<div id="subcontent">
	
    <span class="parametri"><fmt:message key="form.mercatidLetture.mercato" />:<label>${mercato.descrizione}</label></span>
    <span class="parametri"><fmt:message key="form.mercatidLetture.mercatoUso" />:<label>${uso.descrizione}</label></span>
    <br /> 
    <spring-form:form commandName="command" name="inviodatiLetture" modelAttribute="command">
    <fieldset><legend><fmt:message key="form.mercatidLetture.nuovalettura.parametri"></fmt:message></legend>
    <div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="10%"><fmt:message key="form.mercatidLetture.tipicontatore" /> </td>
				<td width="10%" ><fmt:message key="form.mercatidLetture.datalettura" /></td>
                <td width="10%" ><fmt:message key="form.mercatidLetture.dalladata" /></td>
                <td width="10%"><fmt:message key="form.mercatidLetture.alladata" /></td>
                <td width="10%" ><fmt:message key="form.mercatidLetture.presunta" /></td>
            </tr>
		</thead>
		<tbody class="tbody">
		<tr>
			      <td width="5%" >						
						<spring-form:select path="mercatidLetture.tipiContatore.id">
							<spring-form:options items="${tipicontatoreList}" itemLabel="descrizione" itemValue="id"/>
						</spring-form:select>
				  </td>

				  <td>
                   		<spring-form:input id="idDataLettura" path="mercatidLetture.dataLettura" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
                   		<init:calendar imagePath="/images/cal.gif" idImage="calDataLettura" idInput="idDataLettura" textKey="label.calendar"/>
						<spring-form:errors path="mercatidLetture.dataLettura" cssClass="error"/>
                   </td>
               	   <td>
               	   		<spring-form:input path="mercatidLetture.dataInizio" id="idDataInizio" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
               	   		<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="idDataInizio" textKey="label.calendar"/>
               	   		<spring-form:errors path="mercatidLetture.dataInizio" cssClass="error"/> 
               	   </td>

                   <td>
                   		<spring-form:input path="mercatidLetture.dataFine" id="idDataFine" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
                   		<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="idDataFine" textKey="label.calendar"/>
                   		<spring-form:errors path="mercatidLetture.dataFine" cssClass="error"/>  
                   	</td>
               	   <td>
	               	   <spring-form:select  path="mercatidLetture.presunta" >
		               	   <spring-form:option value="<%=WebConstants.MERCATIDLETTURE_PRESUNTA_PRESUNTA%>" ><fmt:message key="form.mercatidLetture.label.presunta" /></spring-form:option>
	                       <spring-form:option value="<%=WebConstants.MERCATIDLETTURE_PRESUNTA_EFFETTIVA%>"><fmt:message key="form.mercatidLetture.label.effettiva" /></spring-form:option>
	               	   </spring-form:select>
	               </td>     
		</tr>
		</tbody>
	</table>
	</div>	
    </fieldset>
    <fieldset><legend><fmt:message key="form.mercatidLetture.nuovalettura"></fmt:message></legend>
	<!-- START Tabella principale --> 
    <div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="5%" ><fmt:message key="form.mercatidLetture.posteggio" /></td>
                <td width="10%" style="text-align: right;" ><fmt:message key="form.mercatidLetture.letturainiziale" /></td>
                <td width="10%" style="text-align: right;" ><fmt:message key="form.mercatidLetture.letturafinale" /></td>
                <td width="10%" style="text-align: right;" ><fmt:message key="form.mercatidLetture.consumo" /></td>
                <td width="10%" style="text-align: right;" ><fmt:message key="form.mercatidLetture.importo" /></td>
    		</tr>
		</thead>
		<tbody class="tbody">
		<%int i=1;%>
		
			<c:forEach items="${command.mercatidLettureList}" var="letture_var" varStatus="status" >
			
			<tr class= "<%=(i%2)==0?"odd":"even"%>">
				<td width="5%">${letture_var.posteggio.codiceposteggio}</td>
				<spring:bind path="mercatidLettureList[${status.index}].letturaIniziale" >
				   <td align="right"><input type="text" name="${status.expression}"  value="${letture_var.letturaIniziale}" style="text-align: right;padding: 0 2px 0 0;" maxlength="10" size="10" onblur="checkNumberValue(this);"/></td>
				</spring:bind>
				<spring:bind path="mercatidLettureList[${status.index}].letturaFinale" >
                   <td align="right"><input type="text" name="${status.expression}"  value="${letture_var.letturaFinale}" style="text-align: right;padding: 0 2px 0 0;" maxlength="10" size="10" onblur="checkNumberValue(this);"/></td>
                </spring:bind>
                <spring:bind path="mercatidLettureList[${status.index}].consumo" >
                   <td align="right"><input type="text" name="${status.expression}"  value="${letture_var.consumo}" style="text-align: right;padding: 0 2px 0 0;" maxlength="10" size="10" onblur="checkNumberValue(this);"/></td>  
                </spring:bind>
                <spring:bind path="mercatidLettureList[${status.index}].importo" >
				   <td align="right"><input type="text" name="${status.expression}"  value="${letture_var.importo}" style="text-align: right;padding: 0 2px 0 0;" maxlength="10" size="10" onblur="checkNumberValue(this);"/></td>
				</spring:bind>
			   </tr>
			   <%i++; %>
			   </c:forEach>
			   
		</tbody>
	</table>
</div>
</fieldset>	
</spring-form:form>
</div>
<div id="functions">
	<ul>
		<li><a href="javascript:doSubmit('insertNuovaLettura.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}','',document.inviodatiLetture)"><fmt:message key="button.update" /></a></li>		
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>		
	</ul>
</div>
</body>
</html>