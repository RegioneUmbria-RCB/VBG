<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="fodomande.domande_in_sospeso" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="fodomande.domande_in_sospeso" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
		</jsp:include>
		<div id="subcontent">
			<form name="fodomandeForm" action="list.htm">
				<jmesa:springTableFacade
					id="fodomande_id" 
					items="${fodomandes}" 
					var="fodomande_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="identificativodomanda" titleKey="label.codice" width="15%" />
							<jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="label.richiedente" />
							<jmesa:htmlColumn property="datainvio" titleKey="label.data_invio" filterable="false"   cellEditor="org.jmesa.view.editor.DateWithTimeCellEditor" width="10%"/>							
							<jmesa:htmlColumn property="dataUltimaModifica" titleKey="label.data_modifica" filterable="false"    cellEditor="org.jmesa.view.editor.DateWithTimeCellEditor" width="10%"/>							
							
							<%-- 
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a href="javascript:doHref('invalidaDomanda.htm?codice=${foarjdomande_var.id.codice}','<fmt:message key="javascript.confirm.invalida_foarjdomande" />');"  title="<fmt:message key="label.invalida" /> ${foarjdomande_var.id.codice}">
									<fmt:message key="label.invalida" /></label>
								</a>
							</jmesa:htmlColumn>
							--%>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="fodomande.domande_in_sospeso" />';
			</script>
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
		</body>
</html>