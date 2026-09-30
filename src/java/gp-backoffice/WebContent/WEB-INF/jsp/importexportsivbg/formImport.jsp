<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.importexportsivbg.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.importexportsivbg.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="jmesa">
		<spring-form:form commandName="importexportsivbgcommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="importexportsivbgcommand" />
		    </jsp:include>
		    <table width="100%">
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="importexport.label.title.import"/></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="importexport.label.servizio_export" />
					</td>
					<td>
						<spring-form:input id="descrizioneServizioExport_id" path="descrizioneServizioExport" size="30" readonly="true" />
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<c:if test="${importexportsivbgcommand.listaVersioniRegionali!=null}">
				<tr class="header">
					<td colspan="2">
						<fmt:message key="importexport.label.servizio_import_versioni_regionali" />
					</td>
				</tr>
				<tr>
					<td >
						<table class="table" >
							<thead class="header">
							<tr>
								<td align="center" width="10%"></td>
								<td width="30%"><fmt:message key="importexport.label.servizio_import.versione" /></td>
								<td width="60%"><fmt:message key="importexport.label.servizio_import.note" /></td>
							</tr>
							</thead>
							<tbody class="tbody">
								<%int y=0; %>
								<c:forEach items="${importexportsivbgcommand.listaVersioniRegionali}" var="versioneN" varStatus="versioneNIdx">
								
									<tr class="<%=(y%2)==0?"odd":"even"%>">
										<td align="center"><spring-form:radiobutton id="R.${versioneN.remoteVers.value}.${versioneN.localVers.value}" path="sceltaVers" value="R.${versioneN.remoteVers.value}.${versioneN.localVers.value}" ></spring-form:radiobutton></td>
										<td>${versioneN.remoteVers.value}.${versioneN.localVers.value}</td>
										<td>${versioneN.note.value}</td>
									</tr>
									<%y++; %>
								
								</c:forEach>
							</tbody>						
						</table>
					</td>
				</tr>
				</c:if>
				
				<tr><td>&nbsp;</td></tr>
				
				<c:if test="${!(importexportsivbgcommand.listaVersioniLocali==null)}">
				<tr class="header">
					<td colspan="2">
						<fmt:message key="importexport.label.servizio_import_versioni_locali" />
					</td>
				
				</tr>
				<tr>
					<td >
						<table class="table" >
							<thead class="header">
							<tr>
								<td align="center" width="10%"></td>
								<td width="30%"><fmt:message key="importexport.label.servizio_import.versione" /></td>
								<td width="60%"><fmt:message key="importexport.label.servizio_import.note" /></td>
							</tr>
							</thead>
							<tbody class="tbody">
								<%int y=0; %>
								<c:forEach items="${importexportsivbgcommand.listaVersioniLocali}" var="versioneN" varStatus="versioneNIdx">
								<tr class="<%=(y%2)==0?"odd":"even"%>">
									<td align="center"><spring-form:radiobutton id="${versioneN.remoteVers.value}.${versioneN.localVers.value}" path="sceltaVers" value="${versioneN.remoteVers.value}.${versioneN.localVers.value}" ></spring-form:radiobutton></td>
									<td>${versioneN.remoteVers.value}.${versioneN.localVers.value}</td>
									<td>${versioneN.note.value}</td>
								</tr>
								<%y++; %>
								</c:forEach>
							</tbody>						
						</table>
					</td>
				</tr>
				</c:if>	
				<c:if test="${importexportsivbgcommand.listaVersioniRegionali!=null || importexportsivbgcommand.listaVersioniLocali!=null}">
				<tr>
					<td><br /><spring-form:checkbox id="descrizioneServizioExport_id" path="attivaUpdateDati" /><fmt:message key="importexport.label.import.insertOrUpdate" /></td>
				</tr>
				</c:if>
				<c:if test="${importexportsivbgcommand.listaVersioniRegionali==null && importexportsivbgcommand.listaVersioniLocali==null}">
				<tr>
					<td><br /><fmt:message key="importexport.label.noRepertorio" /></td>
				</tr>
				</c:if>
			</table>
		</spring-form:form>
	</div>	
	</div>
	<div id="functions">
		<ul>
			<c:if test="${importexportsivbgcommand.listaVersioniRegionali!=null || importexportsivbgcommand.listaVersioniLocali!=null}">
			<li><a href="javascript:doSubmit('importSIVBG.htm','',document.inviodati)"><fmt:message key="button.import" /></a></li>
			</c:if>
			<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>