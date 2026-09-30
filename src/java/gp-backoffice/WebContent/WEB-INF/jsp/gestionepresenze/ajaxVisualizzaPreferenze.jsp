<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
		<div class="rTable" id="panelSpuntistiManifestazione">
				
			<div class="rTableHeading">
				<div class="rTableHead">Richiedente</div>
				<div class="rTableHead">Data richiesta</div>
				<div class="rTableHead">Annotazioni</div>
			</div>			
			<c:forEach items="${prenots}" var="prenot">
				<div class="rTableRow">
					<div class="rTableCell">${prenot.anagrafe.descrizioneRichiedente}</div>
					<div class="rTableCell"><fmt:formatDate value="${prenot.dataInserimento}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" /></div>
					<div class="rTableCell">${prenot.note}</div>
				</div>			
			</c:forEach>
		</div>
