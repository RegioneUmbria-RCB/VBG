<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_mercatidcritass.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_mercatidcritass.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<form name="mercatidcritassForm" action="list.htm">
			<jmesa:springTableFacade
				id="mercatidcritass_id" 
				items="${mercatidcritassList}" 
				var="mercatidcritass_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.SiNoFilteriMercatiDCritAssMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${mercatidcritass_var.id.codice}">${mercatidcritass_var.id.codice}</a>
                        </jmesa:htmlColumn>
                        <jmesa:htmlColumn property="dyn2Campi.nomecampo" titleKey="label.nome_campo" />								
						<jmesa:htmlColumn property="valoredecodificato" titleKey="label.valore" />
						<jmesa:htmlColumn property="flagConsentito" titleKey="label.consentito"  cellEditor="org.jmesa.custom.SiNoCellEditor"
                                              filterEditor="org.jmesa.custom.SiNoDroplist" width="5%" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${mercatidcritass_var.id.codice}" title="<fmt:message key="label.edit.record" />${mercatidcritass_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${mercatid.id.codice}" name="codicePosteggio" />
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codicePosteggio=${mercatid.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_mercatidcritass.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codicePosteggio=${mercatid.id.codice}&codiceMercato=${mercatid.mercati.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>