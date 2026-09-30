<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="tipiunitamisura.label.list_unitamisura.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="tipiunitamisura.label.list_unitamisura.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
		</jsp:include>
		<div id="subcontent">
			<form name="tipiunitamisuraForm" action="list.htm">
				<jmesa:springTableFacade
					id="tipiunitamisura_id" 
					items="${tipiunitamisuraList}" 
					var="tipiunitamisura_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${tipiunitamisura_var.id.codice}">${tipiunitamisura_var.id.codice}</a>
                            </jmesa:htmlColumn>							
							<jmesa:htmlColumn property="umDescrbreve" titleKey="tipiunitamisura.label.umDescrbreve" />
							<jmesa:htmlColumn property="umDescrestesa" titleKey="tipiunitamisura.label.umDescrestesa" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="view.htm?codice=${tipiunitamisura_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${tipiunitamisura_var.id.codice}" >
								<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="tipiunitamisura.label.list_unitamisura.title" />';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
			</ul>
		</div>
	</body>
</html>