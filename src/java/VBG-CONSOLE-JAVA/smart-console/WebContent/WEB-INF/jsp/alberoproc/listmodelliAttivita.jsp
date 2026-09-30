<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_modelli_attivita" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_modelli_attivita" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	 	<div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="label.procedimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${alberoproc.scDescrizione}" /></div>
			</div>
	    </div>
	    <div class="clear"></div>	
		<form name="alberoprocD2modtattForm" action="listmodelliAttivita.htm">
			<jmesa:springTableFacade
				id="modelliAlberoproc_id" 
				items="${alberoprocD2modtatts}" 
				var="modelliAttivita_var"
				stateAttr="restore">
				<jmesa:htmlTable>
					<jmesa:htmlRow>		
						<jmesa:htmlColumn property="id.fkD2mtId" titleKey="label.codice" width="2%">
                           	<a href="viewModelliAttivita.htm?codiceprocedimento=${alberoproc.id.codice}&codicemodello=${modelliAttivita_var.id.fkD2mtId}">${modelliAttivita_var.id.fkD2mtId}</a>
                        </jmesa:htmlColumn>			
						<jmesa:htmlColumn property="dyn2Modellit.descrizione" titleKey="label.modello"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="viewModelliAttivita.htm?codiceprocedimento=${alberoproc.id.codice}&codicemodello=${modelliAttivita_var.id.fkD2mtId}" title="<fmt:message key="label.edit.record" />${modelliAttivita_var.id.fkD2mtId}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${alberoproc.id.codice}" name="codiceprocedimento"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listmodelliAttivita.htm?codiceprocedimento=${alberoproc.id.codice}&';
			var _captionTab='<fmt:message key="lista_modelli_attivita" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createmodelliAttivita.htm?codiceprocedimento=${alberoproc.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>