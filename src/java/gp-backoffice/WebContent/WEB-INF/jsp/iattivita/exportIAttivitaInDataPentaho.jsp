<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.report.helper.TypeReport"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.esportazione" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.esportazione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include> 
	  
	
	
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../iattivita/createExportModalitaPentaho" />
	</jsp:include>



<spring-form:form commandName="iattivitaCommand" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="iattivitaCommand" />
	</jsp:include>
	<div id="subcontent">		

	<table>
		<tr>
			<td colspan="2"><fmt:message key="iattivita.label.descrizione_export"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="label.operatore"/>:</td>
			<td><b>${iattivitaCommand.responsabile.responsabile}</b></td>
		</tr>
		<tr>
			<td><fmt:message key="label.indirizzo_email"/>:</td>
			<td><input id="responsabile_email_id" type="text"  value="${iattivitaCommand.responsabile.email}" size="40"/>
			<c:if test="${iattivitaCommand.responsabile.email eq null}">
				<input id="invio_email_id" type="checkbox"/>
			</c:if>
			<c:if test="${iattivitaCommand.responsabile.email ne null}">
				<input id="invio_email_id" type="checkbox" checked="checked"/> (<fmt:message key="label.help.esportazione_invio_mail"/>)
		    </c:if>
			</td>
		<%-- 	<td><b>${iattivitaCommand.responsabile.email}</b></td> --%>
		</tr>			
		<tr>
			<td>
				<fmt:message key="label.data" />
			</td>
			<td>
		
				 <spring-form:input tabindex="3" id="dataEsportazione_id" path="dataEsportazione" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				 <init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dataEsportazione_id" textKey="label.calendar"/>
			     <spring-form:errors path="dataEsportazione" cssClass="error"/>
			</td>		
		</tr>
		<tr>
			<td><fmt:message key="label.esportazione"/></td>
			<td>
			<%--
				<spring-form:select path="esportazioni" onchange="changeEsportazione(this)">
				  
				   <c:forEach items="${listaEsportazioni}" var="esportazioni" varStatus="a">
				   		<option label="${esportazioni.descrizione}" value="${esportazioni.id.codice}#${esportazioni.id.idcomune}">${esportazioni.descrizione}</option>
				   	</c:forEach>
				</spring-form:select>
				 --%>
				<select onchange="javascript:changeEsportazione(this)"  id="esportazioni_id">
					<c:forEach items="${listaEsportazioni}" var="esportazioni" varStatus="a">
						<option value="${esportazioni.id.codice}#${esportazioni.id.idcomune}">${esportazioni.descrizione}</option>
					</c:forEach>
				</select>
				
			</td>
		</tr>
		
		<c:if test="${not empty iattivitaCommand.esportazioni.parametriesportaziones}">
		    <tr>
		    	<td class="titoloSezione" colspan="2">
		    		<fmt:message key="label.lista_parametri" />
		    	</td>
		    </tr>
		  </c:if>
		 <c:forEach items="${iattivitaCommand.esportazioni.parametriesportaziones}" var="current" varStatus="a">
				<tr>
					<td><label>${current.parametro}</label></td>
					<td>
					<spring:bind path="esportazioni.parametriesportaziones[${a.index}].id.codice">
						<input type="hidden" name="${status.expression}" value="${status.value}" />
					</spring:bind> 
					<spring:bind path="esportazioni.parametriesportaziones[${a.index}].value">
						<input  type="text" name="${status.expression}"  value="${status.value}" />					 
					</spring:bind>
					<label>${current.descrizione}</label>
					<spring-form:errors path="esportazioni.parametriesportaziones[${a.index}].parametro" cssClass="error" />
					</td>
				</tr>
			</c:forEach>
		
	</table>
	</div>	
	<div id="functions">
		<ul>
			<li><a href="javascript:esportaPentahoInData()"><fmt:message key="button.esporta" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
	<script type="text/javascript">	
			
	        function changeEsportazione(id){
	        	disableFunctions();
				var codici=id.value.split("#");
				document.inviodati.action='createExportModalitaPentaho.htm?contestoExport=ATS&codiceEsportazione='+codici[0]+'&_comune='+codici[1];
				setTimeout("document.inviodati.submit()",1000);
			
			}
			
			function esportaPentahoInData(){
				
				var email = document.getElementById("responsabile_email_id").value;
				var _data = document.getElementById("dataEsportazione_id").value;
				var checkInvioMail = false;
				
				if(document.getElementById("invio_email_id").checked)
				{
					if(email == '' || email == null )
					{
						alert('Attenzione, si è deciso di inviare l\'esportazione per e-mail, ma non ne è stata configurata una');
						checkInvioMail = false;
					}else
					{
					checkInvioMail=true;
					}
				}
				
				let cod = document.getElementById("esportazioni_id").value;	
				let target = document.inviodati.target;
				document.inviodati.target= "_blank";
				document.inviodati.action='ajaxExportModalitaPentaho.htm?contestoExport=${contestoExport}&email=' + email + '&isInviaMail=' + checkInvioMail + '&codiceEsportazione=' + cod + '&dataEsportazione='+_data;
				setTimeout("document.inviodati.submit()",10);
				setTimeout("settaTarget('" + target + "')",20);
				
			}
		
		
		function settaTarget(target){
			document.inviodati.target = target;
		}
			
	</script>
	
</spring-form:form>
</body>
</html>
	

	
	