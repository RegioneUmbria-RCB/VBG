<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI --%>
<c:set var="codiceIstanza" value="${param.codiceIstanza }"/>
<c:set var="_returnTo" value="${param.returnTo }"/>
<c:set var="codiceMovimento" value="${param.codiceMovimento }"/>
<c:set var="funzioneRichiesta" value="richiestaPraticaIstanza"/>
<c:set var="flagStc" value="${param.flagStc}"/>
<c:set var="inviatoConStc" value="${param.inviatoConStc}"/>
<c:set var="creatoDaStc" value="${param.creatoDaStc}"/>
<%-- 
funzioni possibili:
	richiestaPraticaIstanza: 
		visualizza la richiesta pratica a partire da una pratica creata da STC
	richiestaPraticaMovimento: 
		visualizza la richiesta pratica a partire da un movimento che ha creato pratiche o è stato creato tramite STC
		se il movimento è da notificare allora compare lo warning con title da notificare
		se il movimento è inviato allora compare l'icona dei computer out e link a collegamento pratica
		se il movimento è stato creato da STC allora compare l'icona dei computer in e link a collegamento pratica
		parametri obbligatori:
			codiceIstanza
			codiceMovimento
			funzioneRichiesta=richiestaPraticaMovimento
			flagStc 'true' o 'false' preso dal tipomovimento
			inviatoConStc  1 (Inviato) o 0 (non inviato) o 2 (disattivato d operatore) preso dal movimento
			creatoDaStc 'true' o 'false' preso dal movimento
 --%>
<c:if test="${not empty param.funzioneRichiesta}">
	<c:set var="funzioneRichiesta" value="${param.funzioneRichiesta}"/>
</c:if>
<%-- END RECUPERO PARAMETRI--%>
<%-- BEGIN SEZIONE FUNZIONI --%>
<c:choose>
<c:when test="${funzioneRichiesta eq 'richiestaPraticaIstanza' }">
	<a href="javascript:historySet('${_returnTo }', '../stc/gotoPraticaCollegata.htm?software=<%= ORMHelper.getSoftware() %>&codiceIstanza=${codiceIstanza}', '');" title="<fmt:message key="label.pratica_creata_da_stc"/>">
		<img src="${pageContext.request.contextPath}/images/retestc_in.gif" align="middle"/>
	</a>
</c:when>
<c:when test="${funzioneRichiesta eq 'richiestaPraticaMovimento' }">
	<c:if test="${flagStc eq true}">
		<c:if test="${inviatoConStc eq 1}">
			<a 
			href="javascript:historySet('${_returnTo }', '../stc/praticaCollegata.htm?software=<%= ORMHelper.getSoftware() %>&codiceIstanza=${codiceIstanza}&codiceMovimento=${codiceMovimento}', '');" 
			title="<fmt:message key="label.movimento_notificato_con_stc"/>">
				<img src="${pageContext.request.contextPath}/images/retestc_out.gif" align="middle"/>
			</a>
		</c:if>				
		<c:if test="${inviatoConStc eq 0 or empty inviatoConStc }">
			<a href="javascript:historySet('${_returnTo}','../movimenti/associaEnteDestinatario.htm?codiceMovimento=${codiceMovimento}', '');">		
			<img title="<fmt:message key="label.movimento_da_notificare_con_stc"/>" src="${pageContext.request.contextPath}/images/warning.gif" align="middle"/></a>
		</c:if>
		<c:if test="${inviatoConStc eq 2}">
			<img title="<fmt:message key="label.movimento_da_notificare_con_stc_disattivato_da_operatore"/>" src="${pageContext.request.contextPath}/images/warning.gif" align="middle"/>
		</c:if>				
	</c:if>
	<c:if test="${creatoDaStc eq true}">
		<a 
		href="javascript:historySet('${_returnTo }', '../stc/praticaCollegata.htm?software=<%= ORMHelper.getSoftware() %>&codiceIstanza=${codiceIstanza}&codiceMovimento=${codiceMovimento}', '');" 
		title="<fmt:message key="label.movimento_creato_da_stc"/>">
			<img src="${pageContext.request.contextPath}/images/retestc_in.gif" align="middle"/>
		</a>
	</c:if>
</c:when>
</c:choose>
<%-- END SEZIONE FUNZIONI --%>
