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
            <td><fmt:message key="tipimodalitapagamento.label.dettaglio_tipimodalitapagamento.title" /></td>
            <td>
            <c:set var="modalitaPagamento" value="${mercpresd.tipimodalitapagamento.id.codice}" scope="page"></c:set>
            	
               	<c:if test="${fn:length(tmpags) == 1 }">
               		<c:forEach items="${tmpags }" var="mpg">
               			<input type="hidden" name="modalitapagamento" value="${mpg.id.codice}"/>
               			<b>${mpg.mpDescrestesa}</b>
               		</c:forEach>
               	</c:if>
               	<c:if test="${fn:length(tmpags) > 1 }">
               		<select id="modalitapagamento_id" name="modalitapagamento">
		           		<option value=""><fmt:message key="label.select.default"/></option>
			            <c:forEach items="${tmpags }" var="mpg">
			           	<c:choose>
			           		<c:when test="${mpg.id.codice eq modalitaPagamento}">
						    	<option value="${mpg.id.codice}" selected="selected">${mpg.mpDescrestesa}</option>
						    </c:when>
						    <c:otherwise>
						    	<option value="${mpg.id.codice}">${mpg.mpDescrestesa}</option>
						    </c:otherwise>
						</c:choose>
						</c:forEach>
					</select>	
               	</c:if>
			</td>
     	</tr>
     	<tr>
			<td>
				Importo pagato
			</td>
			<td>	
				<c:if test="${mercpresd.flagPagato eq true}">
					<input type="checkbox" id="flag_pagato_id" checked="checked" name="flag_pagato_id" value="on"/>
				</c:if>
				<c:if test="${mercpresd.flagPagato eq false}">
				<input type="checkbox" id="flag_pagato_id" name="flag_pagato_id" value="on"/>
				</c:if>
			</td>
		</tr>    
		<tr>
			<td>
				<fmt:message key="label.importo" />
			</td>
			<td>
			<% 
				MercatipresenzeD mercpresd = (MercatipresenzeD)request.getAttribute("mercpresd");
				if(mercpresd.getImporto()==null){
				    mercpresd.setImporto(BigDecimal.ZERO);
				}
				String format = NumberFormat.getInstance(Locale.ITALY).format(mercpresd.getImporto());
				
			%>
			
				<input type="text" id="importo_id" name="importo_id" value="<%=format %>" size="10" maxlength="10"/> &euro;
			</td>
		</tr>
		<%-- AL MOMENTO NON GESTITO      
		<tr>
			<td>
				<fmt:message key="label.riferimenti_pagamento" />
			</td>
			<td>
				<input type="text" id="riferimenti_pagamento_id" value="${mercpresd.riferimentiPagamento}"  name="riferimenti_pagamento" size="50" />
			</td>
		</tr>    
		 --%>    
	</table>
</form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:salvaPagamentoPresenza('pagamentiFormId')"><fmt:message key="button.ok" /></a></li> 
	<li><a href="javascript:chiudiFormPagamento('${param.idelemento }', jQuery('#flag_pagato_id'))"><fmt:message key="button.back" /></a></li> 
</ul>