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
	    <jsp:param name="path" value="../bollgestione/list" />
	</jsp:include>
   <spring-form:form commandName="esportazioniPentahoCommand" name="inviodati" method="post">  
   <jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="esportazioniPentahoCommand" />
	</jsp:include>
	<input type="hidden" value="${idBollettazione}" name="idBollettazione"/>
	<div id="subcontent">	
	
	<div class="vbg-form">
		<fieldset>
			<legend><fmt:message key="label.esportazione"/></legend>
			<div class="form-group">
				<fmt:message key="iattivita.label.descrizione_export"/>
			</div>
			<div class="form-group">
				<label><fmt:message key="label.operatore"/>:</label>
				<b>${esportazioniPentahoCommand.responsabili.responsabile}</b>
			</div>
			<div class="form-group">
				<label><fmt:message key="label.account_mail_cfg"/>:</label>
				<select id="account_id" disabled="disabled">
					<c:forEach items="${listMailConfig}" var="current">
					 <option value="${current.id.codice}">${current.descrizione}</option>
					</c:forEach>
				</select>
				<input id="chk_selezione_account_id" type="checkbox" onchange="attivaSelectAccount()"/> (<fmt:message key="label.help.seleziona_account"/>)
			</div>
			<div class="form-group">
				<label><fmt:message key="label.indirizzo_email"/>:</label>
				<input id="responsabile_email_id" type="text"  value="${esportazioniPentahoCommand.responsabili.email}" size="40"/>
				<c:if test="${esportazioniPentahoCommand.responsabili.email eq null}">
					<input id="invio_email_id" type="checkbox"/>
				</c:if>
				<c:if test="${esportazioniPentahoCommand.responsabili.email ne null}">
					<input id="invio_email_id" type="checkbox" checked="checked"/> (<fmt:message key="label.help.esportazione_invio_mail"/>)
			    </c:if>
			</div>
			<div class="form-group">
				<label><fmt:message key="label.esportazione"/></label>
				<select onchange="javascript:changeEsportazione(this)"  id="esportazioni_id">
					<c:forEach items="${listaEsportazioni}" var="esportazioni" varStatus="a">
						<option  value="${esportazioni.id.codice}#${esportazioni.id.idcomune}">${esportazioni.descrizione}</option>
					</c:forEach>
				</select>
			</div>
		</fieldset>
		<c:if test="${not empty esportazioniPentahoCommand.esportazioni.parametriesportaziones}">
			<fieldset>
			<legend><fmt:message key="label.lista_parametri" /></legend>
		   <table class="vbg-table">
		    	<td class="titoloSezione" colspan="2">
		    		<fmt:message key="label.lista_parametri" />
		    	</td>		  
	   </c:if>
	   <c:forEach items="${esportazioniPentahoCommand.esportazioni.parametriesportaziones}" var="current" varStatus="a">
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
		</fieldset>	
	</div>
	
	</div>
	<div class="form-button">
		<a class="btn btn-primary" href="javascript:esportaPentaho()"><fmt:message key="button.esporta" /></a>	
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>		
	</div>

	<script type="text/javascript">	
			
		 function attivaSelectAccount(id){
	     	
		   if (jQuery("#chk_selezione_account_id").is(":checked")) {
			   jQuery('#account_id').prop('disabled', false);
		    }
		    else {
		    	jQuery('#account_id').prop('disabled', 'disabled');
		    }
		
		}	

        function changeEsportazione(id){
        	disableFunctions();
			var codici=id.value.split("#");
			document.inviodati.action='createExportModalitaPentaho.htm?codiceEsportazione='+codici[0]+'&_comune='+codici[1];
			setTimeout("document.inviodati.submit()",1000);
			
		}
		
		function esportaPentaho(){

			var email = document.getElementById("responsabile_email_id").value;
			var idAccount='';
			if (jQuery("#chk_selezione_account_id").is(":checked")) {
				 idAccount = document.getElementById("account_id").value;
			}
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
			var cod = jQuery("#esportazioni_id").val();	
			let target = document.inviodati.target;
			document.inviodati.target= "_blank";
			document.inviodati.action='ajaxExportModalitaPentaho.htm?email='+email+'&isInviaMail='+checkInvioMail+'&codiceEsportazione='+cod+'&idAccount='+idAccount;
			setTimeout("document.inviodati.submit()",10);
			setTimeout("settaTarget('"+target+"')",20);
		}
		
		function settaTarget(target){
			
			document.inviodati.target = target;
		}
						
	</script>
	
	
</spring-form:form>
</body>
</html>