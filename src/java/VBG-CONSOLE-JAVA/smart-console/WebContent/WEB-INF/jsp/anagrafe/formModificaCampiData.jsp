<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<div id="${param.id_div_overlay }" style="display: none;" class="overlay">
	<div id="${param.id_div_inner}" class="dialog">
		<div style="width: 100%" align="right">
			<a title="<fmt:message key="button.back" />" href="javascript:gestDialogAnagrafeAnagrafeAnagrafe('${param.id_div_overlay }');"><img src="${pageContext.request.contextPath }/images/cross.gif" border="0"/></a>
		</div>
		<table width="100%">
		<tr>
	    	<td><fmt:message key="label.nuovo_valore_associato" /></td>
		</tr>
		<tr>
	    	<td  class="parametri"><fmt:formatDate value="${param.nuovocampo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
		</tr>
		<tr>
	    	<td><fmt:message key="label.vecchio_valore_associato" /></td>
		</tr>
		<tr>
		    <td class="parametri"><fmt:formatDate value="${param.vecchiocampo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
		</tr>
		<tr>
		    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe" /></td>
		</tr>
 				</table>
 				<div id="functions">
			<ul>
				<li><a href="#" onclick="ripristina('${param.fieldId}','${param.oldfieldId}','${param.id_div_overlay }')"><fmt:message key="button.rifiuta"/></a></li>
				<li><a href="#" onclick="accetta('${param.fieldId}','${param.id_div_overlay }')" ><fmt:message key="button.accetta"/></a></li>
			</ul>
		</div>
	</div>	
</div>	
</html>