<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.autorizzazioni.title" />
	</title>
</head>
<body>

	<span class="titoloPagina">
		<fmt:message key="label.autorizzazioni.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../autorizzazioni/listDaAnagrafe" />
	</jsp:include>
	<div id="subcontent">
		<span class="parametri"><fmt:message key="label.anagrafe" />:<label> ${autorizzazioni.anagrafe.descrizioneRichiedente}</label></span>
		
		
		<c:forEach var="entry" items="${mList}">
			
		
			<fieldset>
			  
			   <%
			   Map.Entry entryKey = (Map.Entry)pageContext.getAttribute("entry");
			   
			   String chiave = (String) entryKey.getKey();
			   
			   String codiceSoftware=chiave.substring(0,chiave.indexOf("|"));
			   String descrizioneSoftware = chiave.substring((chiave.indexOf("|")+1));
			   %>
			    <legend><b><%=descrizioneSoftware %></b></legend>
			   <form name="autorizzazioniFilter" action="listDaAnagrafe.htm">
				<jmesa:springTableFacade
					id="autorizzazioniFilter_id" 
					items="${entry.value}" 
					var="list_var"
					exportTypes="" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
			                <jmesa:htmlColumn property="autoriznumero" titleKey="label.numero" filterable="false" >
			                	<a href="javascript:historySet('${_urlback}', '../autorizzazioni/viewAutorizzazione.htm?software=${list_var.tipologiaregistro.software.codice }&codice=${list_var.id.codice}&codiceAnagrafe=${list_var.anagrafe.id.codice}', '')"  title="dettaglio">${list_var.autoriznumero }</a>
			                </jmesa:htmlColumn>
			                <jmesa:htmlColumn property="autorizdata" titleKey="label.data" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterable="false" />
							<jmesa:htmlColumn property="autorizcomune.comune" titleKey="label.comune" filterable="false" />
			            	<jmesa:htmlColumn property="tipologiaregistro.trDescrizione" titleKey="label.registro" filterable="false" />
			            	<jmesa:htmlColumn property="istanza.numeroistanza" titleKey="label.istanza" filterable="false" />
			            	<jmesa:htmlColumn property="flagAttiva" titleKey="label.stato" filterable="false">
			            		<c:if test="${list_var.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
			            		<c:if test="${list_var.flagAttiva ne true}"><fmt:message key="label.cessata" /> <c:if test="${list_var.dataCessazione != null}">(<fmt:formatDate value="${list_var.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>)</c:if></c:if>
			            	</jmesa:htmlColumn>
			            	<jmesa:htmlColumn property="id.codice" titleKey="label.posteggio" filterable="false">
			            		<c:if test="${not empty list_var.autorizzazioniConcessionisForFkAutconcAutatt}"> 
			            			<c:forEach items="${list_var.autorizzazioniConcessionisForFkAutconcAutatt}" var="conc">
										<span>
											<fmt:message key="label.mercati" />: ${conc.mercati.descrizione}<br />
											<fmt:message key="label.giorno" />: ${conc.mercatiUso.descrizione}<br />
											<fmt:message key="label.posteggio" />: ${conc.mercatiD.codiceposteggio}<br />
										</span>			            			
			            			</c:forEach>
								</c:if>
			            	</jmesa:htmlColumn>
			            	<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%" >
			            		<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../autorizzazioni/viewAutorizzazione.htm?software=${list_var.tipologiaregistro.software.codice }&codice=${list_var.id.codice}&codiceAnagrafe=${list_var.anagrafe.id.codice}', '')" title="<fmt:message key="label.edit.record" /> ${list_var.id.codice}" >
			            			<label><fmt:message key="label.edit.record.image" /></label>
			            		</a>
			            	</jmesa:htmlColumn>
			            </jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${param.codiceAnagrafe}" name="codiceAnagrafe" />
			</form>
				<script type="text/javascript">
					var _jmesaUrl='listDaAnagrafe.htm?codiceAnagrafe=${param.codiceAnagrafe}';
					var _captionTab='<fmt:message key="label.autorizzazioni" />';
				</script>
				<%--
				<div id="functions">
					<ul>
						<li><a href="javascript:historySet('${_urlback}','../autorizzazioni/createAutorizzazione.htm?codiceAnagrafe=${autorizzazioni.anagrafe.id.codice }&software=<%= codiceSoftware%>','')"><fmt:message key="button.nuova_autorizzazione" /></a></li>
					</ul>				
				</div>		
				 --%>				
			</fieldset>   
			
		</c:forEach>
		
		
		
	</div>
	<br />
	<div id="functions">
		<ul>
		    <%-- 
		    <li><a href="javascript:historySet('${_urlback}','../autorizzazioni/createAutorizzazione.htm?codiceAnagrafe=${autorizzazioni.anagrafe.id.codice }','')"><fmt:message key="button.nuova_autorizzazione" /></a></li>
			--%>
			<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>