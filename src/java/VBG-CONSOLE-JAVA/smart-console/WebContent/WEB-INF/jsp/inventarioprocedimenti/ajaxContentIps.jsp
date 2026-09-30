<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${empty ips}">		
	<!-- non attivo -->
	</c:when>
	<c:otherwise>
		<c:if test="${empty ips.tipimovimento.id.tipomovimento}">
			<!-- non c'è il movimento [inserisci movimento] --> 
			<a class="dettaglioColumn" style="float: none;" href="javascript:void(0);" onclick="tabmodificaMov('modificaMovDiv', ${ips.id.codiceinventario}, '${ips.id.modulosoftware}')" title="<fmt:message key="label.edit.record.image" /> ${ips.id.codiceinventario}">
				<label><fmt:message key="label.edit.record.image" /></label>
			</a>
			&nbsp;
		</c:if>
		<c:if test="${not empty ips.tipimovimento.id.tipomovimento}">
			<!-- c'è il movimento [inserisci movimento []][cancella movimento - X ] -->
			<a class="dettaglioColumn" style="float: none;" href="javascript:void(0);" onclick="tabmodificaMov('modificaMovDiv', ${ips.id.codiceinventario}, '${ips.id.modulosoftware}')" title="<fmt:message key="label.edit.record.image" /> ${ips.id.codiceinventario}">
				<label><fmt:message key="label.edit.record.image" /></label>
			</a>
			<a class="eliminaRiga" style="float: none;" href="javascript:eliminaMov(${ips.id.codiceinventario},'${ips.id.modulosoftware}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${ips.id.codiceinventario}">
				 <label><fmt:message key="label.elimina.image" /></label>
			</a>
			${ips.tipimovimento.descrizioneEstesa}
		</c:if>
	</c:otherwise>
</c:choose>				
