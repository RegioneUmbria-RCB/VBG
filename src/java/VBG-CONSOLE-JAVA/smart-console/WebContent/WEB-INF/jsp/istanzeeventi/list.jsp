<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title><fmt:message key="label.lista_istanzeeventi" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.lista_istanzeeventi" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<div id="subcontent">
			<form name="istanzeeventiForm" action="list.htm">
				<jmesa:springTableFacade
					id="istanzeeventi_id" 
					items="${istanzeeventiList}" 
					var="istanzeeventi_var" 
					stateAttr="restore">
						<jmesa:htmlTable>
							<jmesa:htmlRow>	
								<jmesa:htmlColumn property="data" titleKey="label.data" pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" />			
								<jmesa:htmlColumn property="categorieeventibase.descrizione" titleKey="label.categoria" />
								<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione" />
								<jmesa:htmlColumn property="flagLetto" titleKey="label.letto" filterable="false">
									<input id="flagLettoId${istanzeeventi_var.id.codice }" type="checkbox" onclick="changeCheckboxValue(flagLettoId${istanzeeventi_var.id.codice },'${pageContext.request.contextPath}/istanzeeventi/ajaxChangeFlagLetto.htm?codice=${istanzeeventi_var.id.codice}')" ${istanzeeventi_var.flagLetto?'checked':''} />
								</jmesa:htmlColumn>
							</jmesa:htmlRow>
						</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" name="codiceIstanza" value="${param.codiceIstanza}" />
				<input type="hidden" name="codicemovimento" value="${param.codicemovimento}" />
			</form>
		</div>		
		<script type="text/javascript">
		
				var _jmesaUrl='list.htm?codiceIstanza=${param.codiceIstanza}&codicemovimento=${param.codicemovimento}&';
				var _captionTab="<fmt:message key="label.lista_istanzeeventi" />";
				
		</script>
		<div id="functions">
			<ul>
				<li><a href="javascript:historyBack('');"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>