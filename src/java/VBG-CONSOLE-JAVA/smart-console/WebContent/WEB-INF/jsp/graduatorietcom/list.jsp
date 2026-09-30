<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_graduatorietcom.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_graduatorietcom.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../graduatorietcom/list" />
    </jsp:include>
    <div id="subcontent">
	<div class="parametriDiv">
		<div class="etichetta">
	      	<div><fmt:message key="label.bando"/>:</div>
	        <div><fmt:message key="label.graduatoria"/>:</div>
	        
	    </div>        
	    <div class="parametro">
	      	<div>${graduatoriet.bandi.descrizione}</div>
	        <div>${graduatoriet.descrizione}</div>
        </div>
	</div>
	<div class="clear" />	
	<div id="subcontent">
	<spring-form:form commandName="graduatorietcom" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="graduatorietcom" />
		    </jsp:include>
    </spring-form:form>
	
	
	<%-- exportTypes="pdfp,excel,csv"  --%>
		<form name="graduatorietcomForm" action="list.htm">
			<jmesa:springTableFacade
				id="graduatorietcom_id" 
				items="${graduatorietcomList}" 
				var="graduatorietcom_var"
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.DataGraduatorietComFilterMatcherMap" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<%-- 
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%" filterable="false" sortable="false">
                           	<a href="view.htm?codice=${graduatorietcom_var.id.codice}">${graduatorietcom_var.id.codice}</a>
                        </jmesa:htmlColumn>	
                       --%>							
						<jmesa:htmlColumn property="data" width="8%" titleKey="label.data" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataCreazioneComunicazioneCustomFilter" filterable="false" sortable="false"/>
						<jmesa:htmlColumn property="descrizione" titleKey="label.comunicazione" filterable="false" sortable="false"/>
						<jmesa:htmlColumn property="numeroDomande" titleKey="label.domande" filterable="false" sortable="false"/>
						<jmesa:htmlColumn property="numeroDomandeConMovimento" titleKey="label.movimenti" filterable="false" sortable="false"/>
						<jmesa:htmlColumn property="numeroDomandeConAllegato" titleKey="label.allegati" filterable="false" sortable="false"/>
						<jmesa:htmlColumn property="numeroDomandeConMailInviate" titleKey="label.mail_inviate" filterable="false" sortable="false"/>
						<jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../graduatorietcom/view.htm?codice=${graduatorietcom_var.codice}', '')" title="<fmt:message key="label.edit.record" />${graduatorietcom_var.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
							<a class="eliminaRiga" href="javascript:doSubmit('delete.htm?codice=${graduatorietcom_var.codice}','<fmt:message key="javascript.confirm.delete_comunicazioni" />',document.inviodati)" title="<fmt:message key="label.azioni"/>  ">
								<label><fmt:message key="label.azioni" /></label>
							</a>
					</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" name="codiceGraduatoria" value="${graduatoriet.id.codice}" />
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="label.lista_graduatorietcom.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codicegraduatoriat=${graduatoriet.id.codice}','');"><fmt:message key="button.new_comunicazione" /></a></li>
			<%-- <li><a href="javascript:doHref('','');"><fmt:message key="button.processa_comunicazioni" /></a></li> --%>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>