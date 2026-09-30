<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
		<table border="1" cellspacing="0" cellpadding="2" style="border: 1px thin dotted; width: 100%; height: 100%">
			<tr>
				<th width="5%">
					<fmt:message key="movimentimail.label.mittente"/>
				</th>
				<th width="5%">
					<fmt:message key="movimentimail.label.destinatario"/>
				</th>
				<th  width="70%">
					<fmt:message key="movimentimail.label.oggetto"/>
				</th>
				<th width="5%">
					<fmt:message key="movimentimail.label.datainvio.table"/>
				</th>
				<th width="5%">
					<fmt:message key="movimentimail.label.messageId"/>
				</th>
			</tr>
			<c:forEach items="${movimentimailList}" var="movimentimail_var" varStatus="a">	
				<tr>
					<td>
						${movimentimail_var.mittente }
					</td>
					<td>
						${movimentimail_var.destinatario }						
					</td>
					<td>
						${movimentimail_var.oggetto }
						<c:if test="${not empty movimentimail_var.movimentimailFigli}">						
						<label>(<a href="#" onclick="showHideDiv('dialog${movimentimail_var.id.codice }');"><fmt:message key="label.movimentimail.risposte" /></a>)</label>
						<div id="dialog${movimentimail_var.id.codice }" title="<fmt:message key='label.movimentimail.risposte' />" style="display: none;">
						    <div style="width: 600px; height: 150px; overflow: auto;">
					            <table border="1" style="border: 1px dotted; width: 100%">
					            	
					            		<tr>
					            			<%-- <th><fmt:message key="movimentimail.label.mittente" /></th>
					            			<th><fmt:message key="movimentimail.label.destinatario" /></th>
					            			<td><fmt:message key="movimentimail.label.oggetto" /></th>
					            			--%>
					            			<th><fmt:message key="movimentimail.label.corpo" /></th>
					            			<%--
					            			<th><fmt:message key="movimentimail.label.data_ricezione" /></th>
					            			 --%>
					            		</tr>
					            		<c:forEach items="${movimentimail_var.movimentimailFigli }" var="emailFiglio">
					            		<tr valign="top">
					            		<%--
					            			<td>${emailFiglio.mittente}</td>
					            			<td>${emailFiglio.destinatario}</td>
					            			<td>${emailFiglio.oggetto}</td>
					            			 --%>
					            			<td><pre><font style="font-size: 12px">${emailFiglio.corpo}</font></pre></td>
					            			<%--
					            			<td><fmt:formatDate value="${emailFiglio.datainvio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>"/></td>
					            			 --%>
					            		</tr>
					            		</c:forEach>
					            	
					            </table>   
						    </div>
						</div>
						</c:if>
					</td>
					<td>
						<fmt:formatDate value="${movimentimail_var.datainvio }" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>"/>
					</td>
					<td>
						${movimentimail_var.messageId }						
					</td>
				</tr>
			</c:forEach>
		</table>		
