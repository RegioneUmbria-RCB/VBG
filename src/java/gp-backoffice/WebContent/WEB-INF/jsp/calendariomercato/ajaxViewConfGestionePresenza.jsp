<%@page import="java.math.BigDecimal"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.MercatipresenzeD"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../calendariomercato/view" />
</jsp:include>

<c:set var="function_goto">javascript:vaiAGiornata('${codicemercato}','${codiceuso}','${dataMercato}')</c:set>
<c:choose>
	<c:when test="${blocco_accesso_futuro eq true}">
		<c:set var="function_goto">javascript:alertGiornoFuturo();</c:set>
	</c:when>
</c:choose>

		<div id="functions" style="margin:4px;">
			<ul>
				<li><a href="${ function_goto }"><fmt:message key="button.vai_a_giornata" /></a></li>
			</ul>
		</div>	
		<br class="clear">&nbsp;</br>
<c:if test="${ GESTIONE_ABILITATA eq true}">
	<form name="giornoForm" id="giornoFormId">
	
		<fmt:message key="label.messaggio.modifica_giornata_mercato.help" />	
	
		<div id="giornoFormIdResult"></div>
			<input type="hidden" name="idGiornata" value="${giorno.id.codice}"/>
	    <table>
			<tr>
	            <td><fmt:message key="label.concessione_uso"/>	</td>
	            <td>
					<select name="conc_uso" id="concessioni_uso_id" onchange="modificaUsoGiornata(this,${giorno.id.codice})">
					<option value=""></option>
						<c:forEach items="${concessioniUsos }" var="concessioniUso">			
							<c:set var="selected" />
							<c:if test="${concessioniUso.id.codice eq concessioniUsoSelezionato }">
								<c:set var="selected" value=" selected='selected' "/>
							</c:if>
							<option value="${concessioniUso.id.codice}" ${ selected }>${concessioniUso.descrizione}</option>
						</c:forEach>
					</select>            
				</td>
	     	</tr>
			<tr>
	            <td><fmt:message key="label.flag_popola_concessionari" /></td>
	            <td>
					<select name="flagPopolaConcessionari" id="flagPopolaConcessionari_id" onchange="modificaFlagPopolaConcessionari(this, ${giorno.id.codice})">
							<option value="1" <c:if test="${giorno.flagPopolaConcessionari eq true}"> selected </c:if> ><fmt:message key="label.si" /></option>
							<option value="0" <c:if test="${giorno.flagPopolaConcessionari ne true}"> selected </c:if> ><fmt:message key="label.no" /></option>					
					</select>            
				</td>
	     	</tr>     	     	
			<tr>
	            <td><fmt:message key="label.flag_conteggia_presenze_assenze" /></td>
	            <td>
					<select name="flagConteggiaPresAss" id="flagConteggiaPresAss_id" onchange="modificaFlagConteggiaPresenzeAssenze(this, ${giorno.id.codice})">
							<option value="1" <c:if test="${giorno.flagConteggiaPresAss eq true}"> selected </c:if> ><fmt:message key="label.si" /></option>
							<option value="0" <c:if test="${giorno.flagConteggiaPresAss ne true}"> selected </c:if> ><fmt:message key="label.no" /></option>					
					</select>            
				</td>
	     	</tr>
	     	
		</table>
	</form>
</c:if>
</div>
