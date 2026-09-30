<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
	<fieldset><legend><span class="titoloPagina">Verifica Stato Istanza in SIEDER</span></legend>
	    	<div class="clear" ></div>
			<c:if test="${not empty statoistanza}">
				Lo stato attuale della pratica SIEDER è <br /><b style="font-size:1.5em;color: maroon">${statoistanza}</b>
			</c:if>
			<c:if test="${empty statoistanza}">
				Non è stato possibile determinare lo stato della pratica in SIEDER
			</c:if>
			
			<div class="clear" ></div>
			<div id="functions">
			<ul>		
			<c:if test="${not empty statoistanza}">		
				<li><a href="${pageContext.request.contextPath }/istanze/infoView.htm?codice=${param.codiceIstanza }">Accedi alla modifica dello stato</a></li>
			</c:if>	
				<li><a href="javascript:chiudiDivSieder()"><fmt:message key="button.annulla" /></a></li>
			</ul>
			</div>
		</div>
	</fieldset>