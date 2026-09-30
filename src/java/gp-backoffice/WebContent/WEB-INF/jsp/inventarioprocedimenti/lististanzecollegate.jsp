<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="inventarioprocedimenti.label.lista_istanze_collegate.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="inventarioprocedimenti.label.lista_istanze_collegate.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	     <div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${inventarioprocedimenti.procedimento}" /></div>
			</div>
	    </div>
	    <div class="clear"></div>	
		<form name="istanzecollegateForm" action="lististanzecollegate.htm">
			<jmesa:springTableFacade
				id="istanzecollegate_id" 
				items="${istanzecollegateList}" 
				var="istanzecollegate_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.DateIstanzecollegateFilterMatcherMap" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>			
						<jmesa:htmlColumn property="istanza.id.codice" titleKey="inventarioprocedimenti.label.codice_istanza" />
						<jmesa:htmlColumn property="istanza.richiedente.descrizioneRichiedente" titleKey="inventarioprocedimenti.label.richiedente" />
						<jmesa:htmlColumn width="10%" property="istanza.data" titleKey="inventarioprocedimenti.label.data" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataistanzecollegateCustomFilter"/>
						<jmesa:htmlColumn property="istanza.software.descrizione" titleKey="inventarioprocedimenti.label.modulo_software" />
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${inventarioprocedimenti.id.codice}" name="codiceendo"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='lististanzecollegate.htm?codiceendo=${inventarioprocedimenti.id.codice}&';
			var _captionTab='<fmt:message key="inventarioprocedimenti.label.lista_istanze_collegate.title" />';
		</script>
		</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>