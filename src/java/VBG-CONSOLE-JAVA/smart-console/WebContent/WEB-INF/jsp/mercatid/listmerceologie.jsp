<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="mercatid.label.lista_merceologie.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="mercatid.label.lista_merceologie.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../mercatid/listmerceologie" />
</jsp:include>
	<div id="subcontent">
	    <br class="clear" />
	    <span style="color: red;"><fmt:message key="label.alert_mercelogie_dismesse" /></span>
	    <br class="clear" />
	    <div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="label.manifestazione" />:</div>
				<div><fmt:message key="label.codiceposteggio" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${mercati.descrizione}" /></div>
				<div><c:out value="${mercatid.codiceposteggio}" /></div>
			</div>
	    </div>		        
        <div class="clear"></div>	 
	
		<form name="mercatidattivitaistatForm" action="listmerceologie.htm">
			<jmesa:springTableFacade
				id="mercatidattivitaistat_id" 
				items="${mercatidattivitaistatList}" 
				var="mercatidattivitaistat_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.MercatidattivitaistatFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>							
						<jmesa:htmlColumn property="attivita.istat" titleKey="label.attivita" />
						<jmesa:htmlColumn property="flagConsentito" 
                                              cellEditor="org.jmesa.custom.SiNoCellEditor"
                                              filterEditor="org.jmesa.custom.SiNoDroplist"
                                              titleKey="label.consentito" width="5%"/>
						<jmesa:htmlColumn property="" titleKey="label.elimina" sortable="false" filterable="false" width="5%">
							<a class="eliminaRiga" href="deleteMerceologia.htm?codicemerceologia=${mercatidattivitaistat_var.id.fkcodiceattivitaistat}&codiceposteggio=${mercatidattivitaistat_var.id.fkidposteggio}&codicemercato=${mercatidattivitaistat_var.id.fkcodicemercato}" title="<fmt:message key="label.elimina.riga" />${mercatidattivitaistat_var.id.fkcodiceattivitaistat}">
								<label><fmt:message key="label.elimina" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${mercatid.id.codice}" name="codiceposteggio" />
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listmerceologie.htm?codiceposteggio=${mercatid.id.codice}&';
			var _captionTab='<fmt:message key="mercatid.label.lista_merceologie.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createMerceologia.htm?codiceposteggio=${mercatid.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>