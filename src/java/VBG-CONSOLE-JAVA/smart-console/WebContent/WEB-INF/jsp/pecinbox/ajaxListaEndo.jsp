<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
	<fieldset><legend><span class="titoloPagina"><fmt:message key="alberoproc.label.lista_alberoprocEndo.title" /></span></legend>
	    <div class="clear" ></div>
		<div class="jmesa" >
			<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">
				<thead>
					<tr class="header">
						<td colspan="2"><fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" /></td>
					</tr>
				</thead>
				<tbody class="tbody" >
				<%int i = 0; %>
				<c:forEach var="alberoprocEndo_var" items="${alberoprocEndosList}" varStatus="alberoprocEndoStatus">
				<c:if test="${fn:length(alberoprocEndosList)>=0}">
					<tr class="<%=(i%2)==0?"odd":"even"%>">
						<td>${alberoprocEndo_var.inventarioprocedimento.procedimento }</td>
						<td>
						<c:set var="checkedObj"></c:set>
						<c:set var="disableObj"></c:set>
						<c:if test="${alberoprocEndo_var.flagRichiestoBo eq true }">
						    <c:set var="checkedObj">checked="checked"</c:set>	
							<c:set var="disableObj">disabled="disabled"</c:set>
						</c:if>
							<input type="checkbox" 
								id="inventarioprocedimenti_${alberoprocEndo_var.id.codiceinventario}_id"
								name="inventarioprocedimenti_${alberoprocEndo_var.id.codiceinventario}" 
								value="${alberoprocEndo_var.id.codiceinventario}" 
								onclick="endoInSession(this, ${alberoprocEndo_var.id.codiceinventario});"
								${checkedObj} ${disableObj} />												
								</td>
					</tr>
					<%i++; %>
				</c:if>
				</c:forEach>
				</tbody>
			</table>
			<div id="functions">
			<ul>				
				<li><a href="javascript:void(0);" onclick="javascript:endoSplashDiv.hide();"><fmt:message key="button.ok" /></a></li>
			</ul>
			</div>
		</div>
	</fieldset>