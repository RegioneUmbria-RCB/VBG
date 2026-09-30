<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html>
	<head>
		<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
		<title><fmt:message key="leggi.label.lista_leggi.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="leggi.label.lista_leggi.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<div id="subcontent">
			<form name="leggiForm" action="list.htm">
				<jmesa:springTableFacade
					id="legge_id" 
					items="${leggi}" 
					var="legge_var" 
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
						<jmesa:htmlTable>
							<jmesa:htmlRow>
								<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${legge_var.id.codice}">${legge_var.id.codice}</a>
                         		</jmesa:htmlColumn>							
								<jmesa:htmlColumn property="leDescrizione" titleKey="label.descrizione" />
								<jmesa:htmlColumn property="normative.normativa" titleKey="label.normativa" />
								<jmesa:htmlColumn property="leggitipi.ltDescrizione" titleKey="leggi.label.tipolegge" />
								<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
									<a class="dettaglioColumn" href="view.htm?codice=${legge_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${legge_var.leDescrizione}">
									<label><fmt:message key="label.edit.record.image" /></label>
									</a>
								</jmesa:htmlColumn>
							</jmesa:htmlRow>
						</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="leggi.label.lista_leggi.title" />';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>