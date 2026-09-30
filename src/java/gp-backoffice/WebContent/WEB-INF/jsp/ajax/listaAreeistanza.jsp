<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="jmesa">
	<table class="table" style="width: 100%;">
		<thead>
			<tr class="header">
	
				<td><fmt:message key="label.area" /></td>
				<td><fmt:message key="label.inserimento_automatico" /></td>
				<td><fmt:message key="label.primario" /></td>
				<td><fmt:message key="label.azioni" /></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listIstanzearee}" var="istanzaarera_var" varStatus="a">
				<tr>
					<td>${istanzaarera_var.area.denominazione}</td>
					<c:if test="${istanzaarera_var.autoins eq true}">
						<td><fmt:message key="label.si"/></td>
					</c:if>
					
					<c:if test="${istanzaarera_var.autoins eq false}">
						<td><fmt:message key="label.no"/></td>
					</c:if>
					<td>
						<c:if test="${istanzaarera_var.primario eq true}">
							<input id="radio_id${a.index}" type="RADIO" CHECKED/>
						</c:if>
						<c:if test="${istanzaarera_var.primario eq false}">
							<input id="radio_id${a.index}" type="RADIO" onclick="doHref('../istanzearee/changePrimario.htm?codiceIstanza=${istanzaarera_var.istanza.id.codice}&codiceArea=${istanzaarera_var.area.id.codice}','')"/>
						</c:if>
					</td>
					<c:if test="${istanzaarera_var.primario eq false}">
					<td>
						<a class="eliminaRiga"
							href="javascript:doHref('../istanzearee/deleteIstanzaarea.htm?codiceIstanza=${istanzaarera_var.istanza.id.codice}&codiceArea=${istanzaarera_var.area.id.codice}','<fmt:message key="javascript.confirm.delete" />')"
							title="<fmt:message key="label.elimina" />"> <label><fmt:message
							key="label.azioni" /></label> 
						</a>
				    </td>
				    </c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		
</div>