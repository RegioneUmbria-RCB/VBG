<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="letteretipo.label.lista_lettere.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="letteretipo.label.lista_lettere.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="letteretipoForm" action="list.htm">
				<jmesa:springTableFacade
					id="letteretipo_id" 
					items="${letteretipoList}" 
					var="letteretipo_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore"
					filterMatcherMap="org.jmesa.custom.SiNoFilterMatcherMap" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>		
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${letteretipo_var.id.codice}">${letteretipo_var.id.codice}</a>
                            </jmesa:htmlColumn>						
							<jmesa:htmlColumn property="descrizione" titleKey="letteretipo.label.descrizione" />
							
							<jmesa:htmlColumn property="flagDisabilitato"
								cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor"
								filterEditor="org.jmesa.custom.SiNoDroplist"
								titleKey="label.disabilitato" width="5%" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="view.htm?codice=${letteretipo_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${letteretipo_var.descrizione}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>							
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="letteretipo.label.lista_lettere.title" />';
			</script>
		
		</div>
		
		<fieldset><legend><fmt:message key="letteretipo.label.templates"/></legend>
		<div class="parametriDiv">
			<div class="etichetta">
				<c:if test="${not empty codiceoggettomoddoctipo}">
					<div><fmt:message key="letteretipo.label.modello.comune" />:</div>
				</c:if>
				<div><fmt:message key="letteretipo.label.modello.sistema" />:</div>				
			</div>
			<div class="parametro">		
				<c:if test="${not empty codiceoggettomoddoctipo}">
					<div>
						<a href="../file/ajaxDownload.htm?fileId=${codiceoggettomoddoctipo}" target="blank" title="<fmt:message key="letteretipo.label.modello.comune.download"/>"><fmt:message key="letteretipo.label.modello.comune.download"/></a>
					</div>				
				</c:if>
				<div>
					<a href="<%=BackofficeNETConstants.getUrlTo(request, BackofficeNETConstants.getURL_RTF_BASE_DOC(), null, null, true) %>" target="blank" title='<fmt:message key="letteretipo.label.modello.sistema.download"/>'>base.doc</a>
				</div>
			</div>
		</div>		
		<br />
	</fieldset>
		<br />
		
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>