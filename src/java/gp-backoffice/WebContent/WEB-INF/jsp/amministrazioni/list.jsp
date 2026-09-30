<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="amministrazioni.label.lista_amministrazioni.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="amministrazioni.label.lista_amministrazioni.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="amministrazioniForm" action="list.htm">
				<jmesa:springTableFacade
					id="amministrazioni_id" 
					items="${amministrazioniList}" 
					var="amministrazioni_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.AmministrazioniFilterMatcherMap">
					<jmesa:htmlTable>
						<jmesa:htmlRow>						
                            <jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${amministrazioni_var.id.codice}">${amministrazioni_var.id.codice}</a>
                            </jmesa:htmlColumn>
							<jmesa:htmlColumn property="amministrazione" titleKey="label.amministrazione" />
							<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">	
								<jmesa:htmlColumn property="stcIdnodo" titleKey="label.stcIdnodo" width="5%"/>
								<jmesa:htmlColumn property="stcIdente" titleKey="label.stcIdente" width="5%"/>
								<jmesa:htmlColumn property="stcIdsportello" titleKey="label.stcIdsportello"  width="5%"/>
							</spring-security:authorize>
                            <jmesa:htmlColumn property="flagAmministrazioneinterna" 
                                              cellEditor="org.jmesa.custom.SiNoCellEditor"
                                              filterEditor="org.jmesa.custom.SiNoDroplist"
                                              titleKey="amministrazioni.label.flag_amministrazione_interna" width="5%"/>                                              
                            <jmesa:htmlColumn property="flagDisabilitato" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor"
                                              filterEditor="org.jmesa.custom.SiNoDroplist"
                                              titleKey="label.disabilitato" width="5%"/>
                                              
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="view.htm?codice=${amministrazioni_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${amministrazioni_var.amministrazione}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="amministrazioni.label.lista_amministrazioni.title" />';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm','')"><fmt:message key="button.new" /></a></li>
	            <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>