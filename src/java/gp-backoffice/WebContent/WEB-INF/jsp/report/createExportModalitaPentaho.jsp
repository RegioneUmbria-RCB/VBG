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
	<script type="text/javascript">
		vbg.ready(() => {
			let chkAccount = document.getElementById('chk_selezione_account_id');
			let selAccount = document.getElementById('account_id');
			let chkIndirizzoEmail = document.getElementById('invio_email_id');
			let inpEmail = document.getElementById('responsabile_email_id');
			let selEsportazione = document.querySelector('select.esportazioni');
			let btnEsporta = document.getElementById('btnExport');
			
			chkAccount.addEventListener('change',(e) => {
				e.preventDefault();
				let abilitato = e.target.checked;
				if( !abilitato ) {
					selAccount.setAttribute('disabled','disabled');
					chkIndirizzoEmail.removeAttribute('checked');
					inpEmail.setAttribute('disabled','disabled');
				} else {
					selAccount.removeAttribute('disabled');
					inpEmail.removeAttribute('disabled');
				}
			});
			
			chkIndirizzoEmail.addEventListener('change',(e) => {
				e.preventDefault();
				let abilitato = e.target.checked;
				if( !abilitato ) {
					inpEmail.setAttribute('disabled','disabled');
				} else {
					inpEmail.removeAttribute('disabled');
				}
			});
			
			selEsportazione.addEventListener('change',(e) => {
				e.preventDefault();
				var codici = e.target.value.split("#");
				document.inviodati.action='createExportModalitaPentaho.htm?codiceEsportazione='+codici[0]+'&_comune='+codici[1];
				setTimeout("document.inviodati.submit()",10);
			});
			
			btnEsporta.addEventListener('click',(e) => {
				e.preventDefault();
				
				if(selEsportazione.value === '') {
					alert('Selezionare l\'esportazione');
					return;
				}
				
				let email = inpEmail.value;
				let checkInvioMail = false;
				let idAccount='';
				
				if (chkAccount.checked) {
					 idAccount = selAccount.value;
				}
				
				if(chkIndirizzoEmail.checked)
				{
					if(email == '' || email == null )
					{
						alert('Attenzione, si è deciso di inviare l\'esportazione per e-mail, ma non ne è stata configurata una');
						checkInvioMail = false;
					} else {
						checkInvioMail=true;
					}
				}
				
				let cod = selEsportazione.value;
				let target = document.inviodati.target;
				document.inviodati.target= "_blank";
				document.inviodati.action='ajaxExportModalitaPentaho.htm?email=' + email + '&isInviaMail=' + checkInvioMail + '&codiceEsportazione=' + cod + '&idAccount=' + idAccount;
				setTimeout("document.inviodati.submit()",10);
				setTimeout("settaTarget('" + target + "')",20);
				
			});
		});
		
		function settaTarget(target){
			document.inviodati.target = target;
		}
	</script>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.esportazione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include> 
	<jsp:include page="../includes/history.jsp">
   		<jsp:param name="path" value="../report/createExportModalitaPentaho" />
	</jsp:include>
	<div id="subcontent">	
		<spring-form:form commandName="reportistanze" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="reportistanze" />
		    </jsp:include>
		    <div id="form" class="vbg-form">
		    	<fieldset>
		    		<legend><fmt:message key="label.dati_generali" /></legend>
		    		<div class="form-group">
		    			<span><fmt:message key="iattivita.label.descrizione_export"/></span>
		    		</div>
		    		<div class="form-group">
		    			<label><fmt:message key="label.operatore"/></label>
		    			<span>${reportistanze.responsabili.responsabile}</span>
		    		</div>
		    		<div class="form-group">
		    			<label><fmt:message key="label.account_mail_cfg"/></label>
						<select id="account_id" disabled="disabled">
							<c:forEach items="${listMailConfig}" var="current">
							 <option value="${current.id.codice}">${current.descrizione}</option>
							</c:forEach>
						</select>
						<input id="chk_selezione_account_id" type="checkbox"/> (<fmt:message key="label.help.seleziona_account"/>)
		    		</div>
		    		<div class="form-group">
		    			<label><fmt:message key="label.indirizzo_email"/></label>
						<spring-form:input id="responsabile_email_id" path="responsabili.email" size="40"/>
						<c:if test="${reportistanze.responsabili.email eq null}">
							<input id="invio_email_id" type="checkbox"/>
						</c:if>
						<c:if test="${reportistanze.responsabili.email ne null}">
							<input id="invio_email_id" type="checkbox" checked="checked"/> (<fmt:message key="label.help.esportazione_invio_mail"/>)
			    		</c:if>
		    		</div>
		    		<div class="form-group">
		    			<label><fmt:message key="label.esportazione"/></label>
						<select class="esportazioni">
							<c:forEach items="${esportazionis}" var="esportazioni" varStatus="a">
								<option label="${esportazioni.descrizione}" value="${esportazioni.id.codice}#${esportazioni.id.idcomune}">${esportazioni.descrizione}</option>
							</c:forEach>
						</select>
		    		</div>
		    	</fieldset>
		    	
		    	<c:if test="${not empty reportistanze.esportazioni.parametriesportaziones}">
		    		<fieldset>
		    			<legend><fmt:message key="label.lista_parametri" /></legend>
		    			<c:forEach items="${reportistanze.esportazioni.parametriesportaziones}" var="current" varStatus="a">
			    			<div class="form-group">
			    				<label>${current.parametro}</label>
			    				<spring:bind path="esportazioni.parametriesportaziones[${a.index}].id.codice">
									<input type="hidden" name="${status.expression}" value="${status.value}" />
								</spring:bind> 
								<spring:bind path="esportazioni.parametriesportaziones[${a.index}].value">
									<input  type="text" name="${status.expression}"  value="${status.value}" />					 
								</spring:bind>
								<label>${current.descrizione}</label>
								<spring-form:errors path="esportazioni.parametriesportaziones[${a.index}].parametro" cssClass="error" />
			    			</div>
		    			</c:forEach>
		    		</fieldset>
		    	</c:if>
		    	<div class="form-button">
		    		<a id="btnInsert" class="btn btn-primary"><fmt:message key="button.insert" /></a>
		    		<a id="btnExport" class="btn btn-primary"><fmt:message key="button.esporta" /></a>
					<a id="btnChiudi" class="btn btn-secondary" href="javascript:historyBack()"><fmt:message key="button.back" /></a>
		    	</div>
		    </div>
	    </spring-form:form>
	</div>
</body>
</html>