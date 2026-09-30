<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="procedimarche.label.lista_procedimarche.title" /></title>
	<style type="text/css">
	.link-adjust{
		top: 6px;
	    position: relative;
	}
	</style>
</head>
<body>
<div style="width: 85%; height: 70px;">
	<div style="float: left;">
		<img src="../images/procedimarche/procedimarche-logo5.png" style="width: 260px;"/>
	</div>
	<div style="float: right;">
		<img src="../images/procedimarche/regione_marche.png" style="width: 128px; height: 64px;"/>
	</div>
</div>
	<span class="titoloPagina"><fmt:message key="procedimarche.label.lista_procedimarche.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<form name="pmCommandForm" action="list.htm">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="pmCommand" />
	    </jsp:include>
			<jmesa:springTableFacade
				id="pmCommand_id" 
				items="${pmCommand.elencoProcedimenti}" 
				var="elencoProcedimenti_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="datiProcedimento.id" titleKey="label.codice" sortable="false" filterable="false"  width="6%">                           
                           		${elencoProcedimenti_var.datiProcedimento.id}                           		                        		
                           		<c:if test="${elencoProcedimenti_var.datiCollegamento.inventarioprocedimenti.id.codice!=null}">
                           		<a href="javascript:historySet('../procedimarche/list.htm?refresh=true','../inventarioprocedimenti/view.htm?codice=${elencoProcedimenti_var.datiCollegamento.inventarioprocedimenti.id.codice}','');">
									<img src="${pageContext.request.contextPath}/images/chain.png" class="link-adjust" 
									title="<fmt:message key="procedimarche.label.collegatoa"/> ${elencoProcedimenti_var.datiCollegamento.inventarioprocedimenti.procedimento}"/>
									${elencoProcedimenti_var.datiCollegamento.inventarioprocedimenti.id.codice}
								</a>
	   							</c:if>	                           	
                        </jmesa:htmlColumn>	 					
                        <jmesa:htmlColumn property="datiProcedimento.nome" titleKey="procedimarche.label.procedimento" sortable="true"/>
						<jmesa:htmlColumn property="datiProcedimento.descrizione" titleKey="label.descrizione" sortable="true"/>
						<jmesa:htmlColumn property="datiProcedimento.categoria" titleKey="label.categoria" sortable="true"/>						
						<jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="8%">
							<div id="functions">
								<ul>
									<c:if test="${elencoProcedimenti_var.datiCollegamento.inventarioprocedimenti.id.codice!=null && pmCommand.canEdit}">
										<li><a href="javascript:historySet('../procedimarche/list.htm?refresh=true','../procedimarche/view.htm?idProc=${elencoProcedimenti_var.datiProcedimento.id}','');"><fmt:message key="button.gestisci_scheda" /></a></li>
			   						</c:if>	
			   						<c:if test="${elencoProcedimenti_var.datiCollegamento.inventarioprocedimenti.id.codice==null || !pmCommand.canEdit}">
			   							<li><a href="javascript:historySet('../procedimarche/list.htm?refresh=true','../procedimarche/view.htm?idProc=${elencoProcedimenti_var.datiProcedimento.id}','');"><fmt:message key="button.visualizza_scheda" /></a></li>
			   						</c:if>	
			   					</ul>
			   				</div>
						</jmesa:htmlColumn>								
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="pmCommand.label.lista_pmCommand.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>						
			<li><a href="javascript:doHref('../procedimarche/list.htm?refresh=true','');"><fmt:message key="button.aggiorna_lista" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>