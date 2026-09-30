<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/upgr-taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../includes/upgr-js-css.jsp"%>
<title><fmt:message key="upgr.upgrade.complete.title" /></title>
</head>
<body>
		<script type="text/javascript">
			function exportErrors(){
				document.forms['upgrErrorListForm'].submit();
			}
		</script>
	<span class="titoloPagina">
		<c:if test="${installed_version != ''}">
			<fmt:message key="upgr.upgrade.complete.title" ></fmt:message>
		</c:if>
		<c:if test="${installed_version == ''}">
			<fmt:message key="upgr.upgrade.failed.title" ></fmt:message>
		</c:if>
	</span>
	<div id="subcontent">
		<c:if test="${installed_version != ''}">
			<fmt:message key="upgr.upgrade.complete.msg" >
				<fmt:param value="${installed_version}"></fmt:param>
			</fmt:message><br/>
			<fmt:message key="upgr.upgrade.complete.detail" >
				<fmt:param value="${tasks_number}"></fmt:param>
				<fmt:param value="${errors_number}"></fmt:param>
			</fmt:message><br/>
		</c:if>
		<c:if test="${installed_version == ''}">
			<fmt:message key="upgr.upgrade.failed.msg" >
			</fmt:message><br/>
			<fmt:message key="upgr.upgrade.complete.detail" >
				<fmt:param value="${tasks_number}"></fmt:param>
				<fmt:param value="${errors_number}"></fmt:param>
			</fmt:message><br/>
		</c:if>
		<c:if test="${errors_number > 0}">
			<br />
			<span class="titoloTabella"><fmt:message key="upgr.upgrade.complete.error.detail" ></fmt:message></span>
			<form name="upgrErrorListForm" action="export.htm">
			<div class="jmesa">
			<table class="table" border="0"  cellpadding="0"  cellspacing="0"  >
				<thead>
					<tr class="toolbar" >
						<td colspan="4" >
						<table border="0"  cellpadding="0"  cellspacing="1" >
							<tr>
								<%--
								<td><img src="/sigepro2/images/table/firstPageDisabled.gif"  alt="Prima pagina " /></td>
								<td><img src="/sigepro2/images/table/prevPageDisabled.gif"  alt="Pagina precedente " /></td>
								<td><img src="/sigepro2/images/table/nextPageDisabled.gif"  alt="Pagina sucessiva " /></td>
								<td><img src="/sigepro2/images/table/lastPageDisabled.gif"  alt="Ultima pagina " /></td>
								<td><img src="/sigepro2/images/table/separator.gif"  alt="Separator" /></td>
								<td><select name="maxRows"  onchange="jQuery.jmesa.setMaxRowsToLimit('upgr_errors_list', this.options[this.selectedIndex].value);onInvokeAction('upgr_errors_list','max_rows')" >
								<option value="10"  selected="selected">10 </option><option value="100" >100 </option><option value="1000" >1000 </option>
								</select></td>
								<td><img src="/sigepro2/images/table/separator.gif"  alt="Separator" /></td>
								 --%>
								<td><a href="javascript:exportErrors();"><img src="${pageContext.request.contextPath}/images/upgr/excel.gif"  title="Estrazione in formato XLS"  alt="excel" /></a></td>
								<%--
								<td><img src="/sigepro2/images/table/separator.gif"  alt="Separator" /></td>
								<td><a href="javascript:onInvokeAction('upgr_errors_list','filter')"><img src="/sigepro2/images/table/filter.gif"  title="Filtra"  alt="Filtra " /></a></td>
								<td><a href="javascript:jQuery.jmesa.removeAllFiltersFromLimit('upgr_errors_list');onInvokeAction('upgr_errors_list','clear')"><img src="/sigepro2/images/table/clear.gif"  title="Resetta Filtro"  alt="Resetta filtro " /></a></td>
								 --%>
							</tr>
						</table>
						</td>
					</tr>
					<tr class="header">
						<td width="25%"><fmt:message key="upgr.upgrade.taskid" /></td>
						<td><fmt:message key="upgr.upgrade.runcontext" /></td>
						<td><fmt:message key="upgr.upgrade.message" /></td>
						<td><fmt:message key="upgr.upgrade.exception" /></td>
					</tr>
				</thead>
				<tbody class="tbody">
					<% int errorCount = 0; %>
					<% String rowClass = ""; %>
					<c:forEach items="${setup_errors}" var="error">
						<% 
							errorCount++; 
							rowClass = errorCount % 2 == 1 ? "odd" : "even";
						%>
						<tr style="${error.fatal ? 'color: red;' : ''}" class="<%= rowClass %>" onmouseover="this.className='highlight'" onmouseout="this.className='<%= rowClass %>'" >
							<td>${error.taskDefinition.id}</td>
							<td>${error.runContext}</td>
							<td>${error.exception.message}</td>
							<td>
								<c:if test="${error.rootException != null}">
									<span>${error.rootException.class.name}</span><br/>
									<span>${error.rootException.message}</span>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tr class="statusBar" >
					<td align="left"  colspan="4" >${errors_number} errori</td>
				</tr>
			</table>
			</div>
			<%-- 
			<jmesa:springTableFacade id="upgr_errors_list"  items="${setup_errors}" var="error" editable="false" exportTypes="excel" stateAttr="restore">
				<jmesa:htmlTable>
					<jmesa:htmlRow style="${error.fatal ? 'color: red;' : ''}">
						<jmesa:htmlColumn property="taskDefinition.id" titleKey="upgr.upgrade.taskid" width="25%" />
						<jmesa:htmlColumn property="runContext" titleKey="upgr.upgrade.runcontext" />
						<jmesa:htmlColumn property="exception.message" titleKey="upgr.upgrade.message" />
						<jmesa:htmlColumn property="rootException" titleKey="upgr.upgrade.exception" >
							<c:if test="${error.rootException != null}">
								<span>${error.rootException.class.name}</span><br/>
								<span>${error.rootException.message}</span>
							</c:if>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			</jmesa:springTableFacade>
			--%>
			</form>
		</c:if>
		</div>
	
	</body>
</html>