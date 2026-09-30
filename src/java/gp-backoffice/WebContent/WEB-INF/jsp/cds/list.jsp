<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="java.net.URLEncoder" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.cds" />
	</title>
</head>
<body>	
	<span class="titoloPagina">
		<fmt:message key="label.cds" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../cds/list" />
	</jsp:include>
	<div id="subcontent">
	
		<form name="list" action="list.htm">
			<jmesa:springTableFacade
				id="cdsFilter_id" 
				items="${cdsList}" 
				var="list_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
		            	<jmesa:htmlColumn property="istanze.numeroistanza" titleKey="label.istanza">	            		
		                	<a href ="javascript:historySet('${_urlback}','../cds/view.htm?codiceIstanza=${list_var.istanze.id.codice }&software=${list_var.istanze.software.codice }');" >${list_var.istanze.numeroistanza }</a >		
		            	</jmesa:htmlColumn>	            
		            	<jmesa:htmlColumn property="istanze.transientRichiedenteQualitaAzienda" titleKey="label.anagrafe" />
		            	<jmesa:htmlColumn property="movimento.movimento" titleKey="label.movimento" />
						<jmesa:htmlColumn property="id.codice" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../cds/view.htm?codiceIstanza=${list_var.istanze.id.codice }&software=${list_var.istanze.software.codice }');" title="<fmt:message key="label.edit.record" /> ${list_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
		            </jmesa:htmlRow>
				</jmesa:htmlTable>
			</jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="label.cds" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createSearch.htm','');"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>