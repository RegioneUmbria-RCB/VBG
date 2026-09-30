<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div style="padding: 40px;">

	<div id="posteggioRinunciaIdMsgs"></div>
	<c:if test="${not empty  mpd.posteggioRinunciato}">
		L'operatore ha rinunciato al posteggio <b>${mpd.posteggioRinunciato.codiceposteggio}</b>
		
		<input type="hidden" id="posteggioRinunciaId" value=""/>		
	</c:if>
	<c:if test="${empty  mpd.posteggioRinunciato}">
		L'operatore intende rinunciare al posteggio
	
	<select id="posteggioRinunciaId">		
				<option value=""></option>
		<c:forEach items="${listaPosteggi}" var="var_presenza" varStatus="varIndex">
			<c:choose>
				<c:when test="${not empty mpd.posteggioRinunciato and var_presenza.posteggio.id.codice eq mpd.posteggioRinunciato.id.codice}">
					<option value="${var_presenza.posteggio.id.codice}" selected="selected">${var_presenza.posteggio.codiceposteggio}</option>
				</c:when>
				<c:otherwise>
					<option value="${var_presenza.posteggio.id.codice}">${var_presenza.posteggio.codiceposteggio}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>		
	</select>
	</c:if>
	<div id="functions">	
		<ul>
			<c:if test="${empty  mpd.posteggioRinunciato}">
				<%-- L'operatore ha rinunciato al posteggio ${mpd.posteggioRinunciato.codiceposteggio} --%>
				<li><a href="javascript:segnaRinunciaSpuntistaNoPosteggio('${mpd.id.codice }','posteggioRinunciaId', true)"><fmt:message key="label.segna_rinuncia_posteggio" /></a></li>					
			</c:if>
			<c:if test="${not empty  mpd.posteggioRinunciato}">
				<%-- L'operatore rinuncia al posteggio --%>
				<li><a href="javascript:segnaRinunciaSpuntistaNoPosteggio('${mpd.id.codice }','posteggioRinunciaId', false)"><fmt:message key="label.elimina_rinuncia_posteggio" /></a></li>		
			</c:if>
	</ul>	
	</div>
	
</div>