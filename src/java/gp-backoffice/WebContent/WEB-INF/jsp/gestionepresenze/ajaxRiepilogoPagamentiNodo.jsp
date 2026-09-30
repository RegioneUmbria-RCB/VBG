<%@page import="java.math.BigDecimal"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.MercatipresenzeD"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<% 
	MercatipresenzeD mercpresd = (MercatipresenzeD)request.getAttribute("mercpresd");
	if(mercpresd.getImporto()==null){
	    mercpresd.setImporto(BigDecimal.ZERO);
	}
	String format = NumberFormat.getInstance(Locale.ITALY).format(mercpresd.getImporto());
			
%>
<div class="layer_nodo_pagamenti">
<c:choose>
	<c:when test="${helper.attivato eq true }">
		<div id="pagamento_nodo_div_${ mercpresd.id.codice }" class="elemento_nodo_pagamento ${helper.posizioneDebitoria.statoAttuale.stato}"
			 data-mpd-id="${ mercpresd.id.codice }" 
			 data-pos-deb-id="${helper.posizioneDebitoria.id}">
				Stato pagamento: ${ helper.posizioneDebitoria.statoAttuale.descrizione }
				<%--
				<div><a class="eliminaRiga" href="javascript:deletePosizioneDebitoria(${mercpresd.id.codice})" title="<fmt:message key="label.azioni"/>  ">
					<label><fmt:message key="label.azioni" /></label></a></div>
				--%>
		</div>
	</c:when>
	<c:otherwise>		
			<a class="vbg-btn btn-euro btn-euro-aggiungi registra-pagamento" 
				data-idmercatipresenza="${mercpresd.id.codice}" 
				href="javascript: void 0" 
				onclick="registraPagamento(this,'pagamento-id-${mercpresd.id.codice}');"
				id="pagamento-id-${mercpresd.id.codice}"></a>	
	</c:otherwise>
</c:choose>
</div>