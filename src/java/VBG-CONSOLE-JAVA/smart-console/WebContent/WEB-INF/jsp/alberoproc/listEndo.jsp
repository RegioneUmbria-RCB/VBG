<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="alberoproc.label.lista_alberoprocEndo.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="alberoproc.label.lista_alberoprocEndo.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../alberoproc/listEndo" />
	</jsp:include>
    
	<div id="subcontent">
	<div class="parametriDiv">
       		<div class="etichetta"> 
	        	<div>
	        		<div><fmt:message key="label.intervento" />:</div>
	        	</div>
			</div>
			<div class="parametro">
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizione}
	        	</div>
	        </div>
    </div>
    <div class="clear" ></div>
	<% 
		String uriBack = "/alberoproc/listEndo.htm?alberoproc.id.codice="+request.getParameter("alberoproc.id.codice");
	%>
		<form name="alberoprocEndoForm" action="listEndo.htm">
			<jmesa:springTableFacade
				id="alberoprocEndo_id" 
				items="${alberoprocEndosList}" 
				var="alberoprocEndo_var"
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codiceinventario" titleKey="label.codice" width="2%"  sortable="false" filterable="false">
	                		<a href="javascript:historySet('${_urlback}','../alberoproc/listincompatibili.htm?codiceendo=${alberoprocEndo_var.id.codiceinventario}&codicealberoproc=${alberoproc.vwAlberoproc.id.codice}','')">${alberoprocEndo_var.inventarioprocedimento.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="inventarioprocedimento.procedimento" titleKey="alberoproc.label.alberoprocEndo_inventarioprocedimento" sortable="false" filterable="false" >
							<a href="javascript:historySet('${_urlback}', '../inventarioprocedimenti/view.htm?codice=${alberoprocEndo_var.id.codiceinventario}', '')">${alberoprocEndo_var.inventarioprocedimento.procedimento}</a>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="azione.azAzione" titleKey="alberoproc.label.alberoprocEndo_azione" sortable="false" filterable="false" />
						<jmesa:htmlColumn property="flagRichiesto" titleKey="alberoproc.label.alberoprocEndo_flagRichiesto" sortable="false" filterable="false" >
							<c:if test="${alberoprocEndo_var.flagRichiesto eq false || alberoprocEndo_var.flagRichiesto == null}"><fmt:message key="label.no" /></c:if>
							<c:if test="${alberoprocEndo_var.flagRichiesto eq true}"><fmt:message key="label.si" /></c:if>
						</jmesa:htmlColumn>
						<%--
						<jmesa:htmlColumn property="flagPrincipale" titleKey="alberoproc.label.alberoprocEndo_flagPrincipale" sortable="false" filterable="false" >
							<c:if test="${alberoprocEndo_var.flagPrincipale eq false || empty alberoprocEndo_var.flagPrincipale}"><fmt:message key="label.no" /></c:if>
							<c:if test="${alberoprocEndo_var.flagPrincipale eq true}"><fmt:message key="label.si" /></c:if>
						</jmesa:htmlColumn>
						 --%>
						<jmesa:htmlColumn property="" titleKey="label.elimina" sortable="false" filterable="false" width="5%">
								<a class="eliminaRiga" style="float: none;" href="javascript:doHref('deleteEndo.htm?alberoproc.id.codice=${alberoproc.id.codice}&codiceinventario=${alberoprocEndo_var.id.codiceinventario}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${alberoprocEndo_var.id.codiceinventario}">
					               	 <label><fmt:message key="label.elimina.image" /></label>
					            </a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			  <input type="hidden" value="${alberoproc.id.codice}" name="alberoproc.id.codice"/> 
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listEndo.htm?alberoproc.id.codice=${alberoproc.id.codice}&';
			var _captionTab='<fmt:message key="alberoproc.label.lista_alberoprocEndo.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createEndo.htm?alberoproc.id.codice='+${alberoproc.id.codice},'');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=' + ${alberoproc.id.codice},'')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>