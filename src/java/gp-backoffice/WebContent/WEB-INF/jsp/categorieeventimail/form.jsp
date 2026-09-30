<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.CategorieEventiMail"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<fmt:message key="label.categorieeventimail" />
</title>
</head>
<body>
<span class="titoloPagina"> 
	<fmt:message key="label.categorieeventimail" />
</span>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../categorieeventimail/view" />
</jsp:include>

<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent">
<spring-form:form commandName="categorieEventiMail" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="aree" />
	</jsp:include>
	<table>
		<tr>
			<td><fmt:message key="label.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="70" tabindex="0" /><spring-form:errors path="descrizione" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.categorieeventibase" /></td>
			<td>
				<spring-form:select tabindex="4" path="categorieeventibase.id" >
				    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${categorieeventibase}" itemValue="id"	itemLabel="descrizione" />
				</spring-form:select> 
				<spring-form:errors path="categorieeventibase" cssClass="error" />			
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="label.software" /></td>
			<td>
			   <jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="software" />
							<jsp:param name="propertyPath" value="software" />		
							<jsp:param name="pathPropertyDescription" value="software.descrizione" />
							<jsp:param name="pathPropertyCode" value="software.codice" />
							<jsp:param name="autocompleterAjax" value="findSoftware.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_software" />							
			   </jsp:include>
			   <spring-form:errors path="software.descrizione" cssClass="error"/>
			   <i style="margin-left:3px;" class="fas fa-times-circle" onclick="azzeraSoftware()"></i>
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="label.mailtipo" /></td>
			<td><jsp:include page="../includes/autocompletergenericoTT.jsp">
				<jsp:param name="idElemento" value="mailtipo" />
				<jsp:param name="propertyPath" value="mailtipo" />
				<jsp:param name="pathPropertyDescription" value="mailtipo.descrizione" />
				<jsp:param name="pathPropertyCode" value="mailtipo.id.codice" />
				<jsp:param name="autocompleterAjax" value="findMailtipo2.htm?ambito=M&codicesoftware=${not empty categorieEventiMail.software ? categorieEventiMail.software.codice : ''}" />
				<jsp:param name="titleKey" value="label.ricerca_mailtipo" />
				<jsp:param name="id_help" value="help_mailtipo" />
			</jsp:include>
			<spring-form:errors path="mailtipo.descrizione" cssClass="error"/>
			</td>
		</tr>
		<tr>
					<td class="">
						<fmt:message key="tipimovimentocomunicazioni.label.accountmail" />
					</td>
					<td>
						<spring-form:input id="accountmail_descrizione" path="mailConfig.descrizione" size="100" readonly="true"/>
						<spring-form:errors path="mailConfig" cssClass="error"/>
						<img src="${pageContext.request.contextPath}/images/book_open.png" style="cursor: pointer;" title=" <fmt:message key="movimentimail.label.rubrica_help" />" alt="" onclick="javascript:openSearch('ricerca_accountmail');" />
						<br />
						<span id="ricerca_accountmail" style="display: none;">
						    <fmt:message key="movimentimail.label.seleziona_account_email" />
						    <br />
						    <spring-form:select id="select_accountmail_id" path="mailConfig.id.codice" onchange="setMittente(this.options[this.selectedIndex])">
								<%-- <spring-form:options items="${listMailConfig}" itemLabel="descrizioneLunga" itemValue="id.codice" />--%>
								<spring-form:option value="">-- Seleziona --</spring-form:option>
								<c:forEach items="${listMailConfig}" var="current" >
									<spring-form:option value="${current.id.codice}" >${current.descrizioneLunga}</spring-form:option>
								</c:forEach>							
							</spring-form:select>
						</span>
					</td>
				
		</tr>		
		<tr>
			<td><fmt:message key="label.destinatari" /></td>
			<td><spring-form:input id="destinatarimail_id" path="destinatarimail" size="70" tabindex="0" /><spring-form:errors path="destinatarimail" cssClass="error" /></td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.destinatari.aggiuntivi" />
			</td>
			<td> 
			<%
			Set<String> destinataris = (Set<String>)request.getAttribute("destinataris");
				CategorieEventiMail entity = (CategorieEventiMail)request.getAttribute("categorieEventiMail");
				String destinatariAggiuntivi = StringUtils.defaultString(entity.getDestinatariAggiuntivi());
				String selected = "";
				// out.print(destinatariAggiuntivi);
				
				 %>
			<select multiple="multiple" name="destinatariAggiuntivi" size="4">
			<%
				
				for(String d : destinataris){
				    selected = "";
				    // System.out.println(d);
				    if(destinatariAggiuntivi.indexOf(d)>=0){
						selected = "selected=\"selected\"";										    
				    }
				    // System.out.println(selected);
				    out.print("<option value=\""+d+"\"  "+selected+">"+d+"</option>");
				}
			%>

			</select>
			<fmt:message key="label.select_multiplo" />
			</td>
		</tr>		
		<tr>
			<td>
				<fmt:message key="label.categorieeventimail.trigger.tipo" />
			</td>
			<td> 
			<select name="triggerType" >
			<%
				Map<String, String> triggerTypes = (Map<String, String>)request.getAttribute("triggerTypes");				
				String triggerType = StringUtils.defaultString(entity.getTriggerType());
				 selected = "";
				for(Map.Entry<String, String> d : triggerTypes.entrySet()){
				    selected = "";
				    if(d.getKey().indexOf(triggerType)>=0){
						selected = "selected=\"selected\"";										    
				    }
				    out.print("<option value=\""+d.getKey()+"\"  "+selected+" >"+d.getValue()+"</option>");
				}
			%>

			</select>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.categorieeventimail.trigger.testo" /></td>
			<td><spring-form:input id="triggerText_id" path="triggerText" size="70" tabindex="0" /><spring-form:errors path="triggerText" cssClass="error" /></td>
		</tr>
        <tr>
			<td><fmt:message key="label.attivo" /></td>
			<td><spring-form:checkbox path="flagAttiva" value="1" /></td>
		</tr>		
		
	</table>

</spring-form:form></div>
<div id="functions">
<ul>
	<c:if test="${categorieEventiMail.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${categorieEventiMail.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
<script type='text/javascript'>

const divSoftCh = document.getElementById('software_id_choices');
const softwareredirect = "${categorieEventiMail.id.codice==null ? 'createCategorieEventi.htm?cemsftw=' : ('view.htm?codice='.concat(categorieEventiMail.id.codice).concat('&cemsftw='))}";

divSoftCh.addEventListener('click', function(event) {
  if (event.target.tagName.toLowerCase() === 'li') {	  	  	  
    if(event.target.id){
    	doSubmit(softwareredirect + event.target.id,'',document.inviodati);
    }
  }
});

function openSearch(id){
	jQuery( "#"+id ).toggle();
	
}
function setMittente(option){
	if(option.value){
		jQuery("#accountmail_descrizione").val(option.text);
	}else{
		jQuery("#accountmail_descrizione").val("");
	}	
	jQuery("#ricerca_accountmail").hide();	
}

function azzeraSoftware(){
	doSubmit(softwareredirect + 'azzera');
}

</script>
</body>
</html>