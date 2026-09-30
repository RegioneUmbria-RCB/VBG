<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.registrazioni.mercato.created" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.registrazioni.mercato.created" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>

        <div id="subcontent">
			<form name="registrazioniForm" action="registrazionipresenzeout.htm">
				<jmesa:springTableFacade
					id="registrazioni_id" 
					items="${registrazioniList}" 
					var="registrazioni_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.DateRegistrazioniFilterMatcherMap">
					<jmesa:htmlTable>
						<jmesa:htmlRow>							
							<jmesa:htmlColumn property="progressivo" titleKey="form.registrazioni.progressivo" />
							<jmesa:htmlColumn property="descrizione" titleKey="form.registrazioni.descrizione" />
							<jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="form.registrazioni.anagrafe" />
							<jmesa:htmlColumn property="dataRegistrazione" titleKey="form.registrazioni.dataRegistrazione" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataRegRegistrazioniCustomFilter"/>
							<jmesa:htmlColumn property="registrazioniCausali.descrizione" titleKey="form.registrazioni.registrazioniCausali" />
							<jmesa:htmlColumn property="importo" titleKey="form.registrazioni.importo" />
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='registrazionipresenzeout.htm?';
				var _captionTab='<fmt:message key="form.registrazioni.mercato.created" />';
			</script>
		</div>

<br />
<br />
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>