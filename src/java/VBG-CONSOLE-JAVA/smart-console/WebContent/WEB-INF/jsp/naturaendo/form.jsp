<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.service.NatureProcedureService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${naturaendo.id==null}">
			<fmt:message key="label.nuovo_naturaendo.title" />
		</c:if> 
		<c:if test="${naturaendo.id!=null}">
			<fmt:message key="label.dettaglio_naturaendo.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${naturaendo.id==null}">
			<fmt:message key="label.nuovo_naturaendo.title" />
		</c:if> 
		<c:if test="${naturaendo.id!=null}">
			<fmt:message key="label.dettaglio_naturaendo.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="naturaendo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="naturaendo" />
		    </jsp:include>
			<table width="100%" >
				<c:if test="${naturaendo.id!=null}">
				<tr>
					<td>
						<fmt:message key="label.codice" />
					</td>
					<td>
						<spring-form:input id="codice_id" path="id" size="6" readonly="true"/>
					</td>
				</tr>
				</c:if>
				<tr>
					<td>
						<fmt:message key="label.natura" />
					</td>
					<td>
						<spring-form:input id="natura_id" path="natura" size="70" />
						<spring-form:errors path="natura" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						Tipo di avvio
					</td>
					<td>
						<spring-form:select id="modalitaApertura_id" path="modalitaApertura">
							<spring-form:option value="" label=""></spring-form:option>
							<spring-form:option value="R-COMUNICAZIONE" label="R-COMUNICAZIONE"></spring-form:option>
							<spring-form:option value="AUTOMATICO" label="AUTOMATICO"></spring-form:option>
							<spring-form:option value="ORDINARIO" label="ORDINARIO"></spring-form:option>
						</spring-form:select>
					</td>
				</tr>				
				<tr>
					<td valign="top">
						<fmt:message key="label.nature_compatibili" />
					</td>
			    	<td>
						<select  name="naturecompatitibili" multiple="multiple" style="width: 150px" size="${fn:length(naturaendoList)}">						
		            		<c:forEach var="nature" items="${naturaendoList}" varStatus="counter">	
		            			<c:if test="${nature.transietFlagBinariodipendenze eq true}">
		            			<option value="${nature.id}" selected="selected" >
										${nature.natura}
								</option>
								</c:if>
								<c:if test="${nature.transietFlagBinariodipendenze eq false}">
		            			<option value="${nature.id}">
										${nature.natura}
								</option>
								</c:if>
		            		</c:forEach>
		            	</select>
		            	<fmt:message key="label.select_multiplo" />
		            </td>
		        </tr>
				<tr>
					<td>
						Codice natura base
					</td>
					<td>
						<spring-form:select  path="naturabase">
						 	<spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
							<spring-form:option value="<%=NatureProcedureService.NATURA_BASE_ENUM.comunicazione%>"><%=NatureProcedureService.NATURA_BASE_ENUM.comunicazione%></spring-form:option>
							<spring-form:option value="<%=NatureProcedureService.NATURA_BASE_ENUM.scia%>"><%=NatureProcedureService.NATURA_BASE_ENUM.scia%></spring-form:option>
							<spring-form:option value="<%=NatureProcedureService.NATURA_BASE_ENUM.ordinario%>"><%=NatureProcedureService.NATURA_BASE_ENUM.ordinario%></spring-form:option>		                   
						</spring-form:select>
						<spring-form:errors path="naturabase" cssClass="error"/>
					</td>
				</tr>
		        
			</table>
			<script type='text/javascript'>
				$('natura_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${naturaendo.id==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${naturaendo.id!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<%--
					<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			 	--%>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>