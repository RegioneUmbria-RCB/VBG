<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.configurazione_parametri_mail" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.configurazione_parametri_mail" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="mailconfig" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mailconfig" />
		    </jsp:include>
		<table border="0" width="100%"><tr><td valign="top">
			<table width="100%">
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.dati_mail_out"/></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.utente" />
					</td>
					<td>
						<spring-form:input id="loginname_id" path="entity.loginname" size="30" />
						<spring-form:errors path="entity.loginname" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.password" />
					</td>
					<td>
						<spring-form:password id="loginpass_id" path="entity.loginpass" size="32" showPassword="true" />
						<spring-form:errors path="entity.loginpass" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.mailserver" />
					</td>
					<td>
						<spring-form:input id="mailserver_id" path="entity.mailserver" size="30" />
						<spring-form:errors path="entity.mailserver" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.port" />
					</td>
					<td>
						<spring-form:input id="loginname_id" path="entity.port" size="6" cssStyle="text-align:right;"/>
						<spring-form:errors path="entity.port" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.usa_autenticazione" />
					</td>
					<td>
						<spring-form:checkbox id="useauthentication_id" path="entity.useauthentication" value="true" />
						<spring-form:errors path="entity.useauthentication" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.protocollo_invio_mail" />
					</td>
					<td>
						<spring-form:select path="entity.usessl">
							<spring-form:option value="0"><fmt:message key="label.smtp" /></spring-form:option>
							<spring-form:option value="1"><fmt:message key="label.legacy_smtps" /></spring-form:option>
							<spring-form:option value="2"><fmt:message key="label.smtp_with_ssl" /></spring-form:option>
						</spring-form:select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.email_mittente" />
					</td>
					<td>
						<spring-form:input id="senderaddress_id" path="entity.senderaddress" size="30" />
						<spring-form:errors path="entity.senderaddress" cssClass="error"/>
					</td>
				</tr>
			</table>
			</td><td valign="top">
			<table width="100%">
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.dati_mail_in"/></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.utente" />
					</td>
					<td>
						<spring-form:input id="loginnamein_id" path="entity.inLoginname" size="30" />
						<spring-form:errors path="entity.inLoginname" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.password" />
					</td>
					<td>
						<spring-form:password id="loginpassin_id" path="entity.inLoginpass" size="32" showPassword="true" />
						<spring-form:errors path="entity.inLoginpass" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.mailserver" />
					</td>
					<td>
						<spring-form:input id="mailserverin_id" path="entity.inMailserver" size="30" />
						<spring-form:errors path="entity.inMailserver" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.port" />
					</td>
					<td>
						<spring-form:input id="loginnamein_id" path="entity.inPort" size="6" cssStyle="text-align:right;"/>
						<spring-form:errors path="entity.inPort" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.usa_autenticazione" />
					</td>
					<td>
						<spring-form:checkbox id="useauthenticationin_id" path="entity.inUseauthentication" value="true" />
						<spring-form:errors path="entity.inUseauthentication" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.protocollo_lettura_mail" />
					</td>
					<td>
						<spring-form:select path="entity.inUsessl">
							<spring-form:option value="0"><fmt:message key="label.pop3" /></spring-form:option>
							<spring-form:option value="1"><fmt:message key="label.imap" /></spring-form:option>
							<spring-form:option value="2"><fmt:message key="label.ssl_pop3" /></spring-form:option>
							<spring-form:option value="3"><fmt:message key="label.ssl_imap" /></spring-form:option>
						</spring-form:select>
					</td>
				</tr>
			</table>
			</td></tr></table>
			<script type='text/javascript'>
				$('loginname_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
		    <c:if test="${mailconfig.displayMode == mailconfig.displayConstants.NEW }">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mailconfig.displayMode == mailconfig.displayConstants.VIEW }">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
			<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>