<%@page import="it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioInfoRestBean"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.MercatipresenzeD"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

	<form name="pagamentiForm" id="pagamentiFormId">
	<div id="pagamentiFormIdResult"></div>
	<input type="hidden" name="mercatipresenzedid" value="${mercpresd.id.codice}"/>
    <table>
		<tr>
			<td>
				<fmt:message key="label.importo" />
			</td>
			<td>
			<% 
			PosteggioInfoRestBean mercpresd = (PosteggioInfoRestBean)request.getAttribute("costoPosteggio");
			String format = NumberFormat.getInstance(Locale.ITALY).format(mercpresd.getImporto());
			out.print(format);
			%>
				 &euro;
			</td>
		</tr>
	</table>
</form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:chiudiFormPagamento('${param.idelemento }', jQuery('#flag_pagato_id'))"><fmt:message key="button.back" /></a></li> 
</ul>