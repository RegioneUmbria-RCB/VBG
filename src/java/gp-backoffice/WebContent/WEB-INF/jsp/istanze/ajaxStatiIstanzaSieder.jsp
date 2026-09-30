<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
	<fieldset><legend><span class="titoloPagina">Stati ammissibili per l'Istanza in SIEDER</span></legend>
	    	<div class="clear" ></div>
			<c:if test="${not empty statiIstanzaSieder}">
				Gli stati ammissibili sono <br />
				<ol>
				<c:forEach items="${statiIstanzaSieder}" var="si">
					<li style="font-size:1.2em;color: maroon">${si.descrizione } (${si.codice })</li>
				</c:forEach>
				</ol>
			</c:if>
			<c:if test="${empty statiIstanzaSieder}">
				Non è stato possibile determinare gli stati ammissibili della pratica in SIEDER
			</c:if>
			
			<div class="clear" ></div>
			<div id="functions">
			<ul>		
			<%--
			<c:if test="${not empty statiIstanzaSieder}">		
				<li><a href="${pageContext.request.contextPath }/istanze/infoView.htm?codice=${param.codiceIstanza }">Accedi alla modifica dello stato</a></li>
			</c:if>
			 --%>	
				<li><a href="javascript:chiudiDivSieder()"><fmt:message key="button.annulla" /></a></li>
			</ul>
			</div>
		</div>
	</fieldset>