<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>



<div>

<%-- SEZIONE MERCATI PER CUI RICHIEDE LA SPUNTA CON L'ISTANZA IN ESAME --%>
<%-- 
<div class="titoloSezione">
	<td><fmt:message key="label.mercati_per_cui_richiede_la_spunta"/></td>
</div>
--%>
   <%-- 
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../autorizzazioni/viewAutorizzazione" />
	</jsp:include>
	--%>
	<div class="header_dato">
		<span class="header_dato_etichetta"><fmt:message key="label.istanza_richiesta_spunta" />:</span>
		<span class="header_dato_valore">
			${istanza.numeroistanza}
		</span>
	</div>
	<div class="header_dato">
		<span class="header_dato_etichetta"><fmt:message key="label.richiedente" />:</span>
		<span class="header_dato_valore">
			${istanza.richiedente.descrizioneRichiedente}
		</span>
	</div>
	<div class="header_dato">
		<span class="header_dato_etichetta"><fmt:message key="label.istanza_autorizzazione" />:</span>
		<span class="header_dato_valore">
			${autorizzazioni.istanza.numeroistanza}
		</span>
	</div>
	<div class="header_dato">
		<span class="header_dato_etichetta"><fmt:message key="label.autorizzazione" />:</span>
		<span class="header_dato_valore">
		${autorizzazioni.autoriznumero} - <fmt:formatDate value="${autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
		- ${autorizzazioni.autorizcomune.descrizioneEstesa} - ${autorizzazioni.tipologiaregistro.trDescrizione}
		</span>
	</div>
	<div class="header_dato">
		<span class="header_dato_etichetta"><fmt:message key="label.intestatario" />:</span>
		<span class="header_dato_valore">
		${autorizzazioni.anagrafe.descrizioneRichiedente}
		</span>
	</div>
	<c:if test="${not empty  mercatiSpuntistiHelpers}">
	<br /><br />
    <div class="titoloTabella">
		<td><fmt:message key="label.mercati_per_cui_richiede_la_spunta"/></td>
	</div>
	<div class="jmesa">
		<table class="table">
			<thead>
				<tr class="header">	
					<td width="30%"><fmt:message key="label.mercato"/></td>
					<td width="30%"><fmt:message key="label.giorno" /></td>
					<td width="15%"><fmt:message key="label.alert" /></td>
				</tr>
			</thead>
			<tbody>	
			<c:forEach items="${mercatiSpuntistiHelpers}" var="mercatiSpuntisti" varStatus="idx">
			<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">
	   	 	   	<td>${mercatiSpuntisti.mercato}</td>
	   	 		<td>${mercatiSpuntisti.uso}</td>
	   	 		<c:if test="${mercatiSpuntisti.segnalazione}">
	   	 			<td><img src="${pageContext.request.contextPath}/images/warning.gif" title="${mercatiSpuntisti.messSegnalazione}"/></td>
	   	 		</c:if>
	   	 		<c:if test="${!mercatiSpuntisti.segnalazione}">
	   	 			<td>&nbsp;</td>
	   	 		</c:if>
			</tr>    	 
			</c:forEach>				
			
			</tbody>
		</table>
			
	</div>
	<div id="functions">
		<li><a href="javascript:doHref('../spuntistimercati/insertSpuntistiMercati.htm?codiceIstanza=${istanza.id.codice}&idautorizzazione=${autorizzazioni.id.codice}','')"><fmt:message key="label.trasforma_in_spuntista" /></a></li>
	    <c:if test="${codiceAnagrafe!=null}">
			<%-- <li><a href="javascript:doHref('../spuntistimercati/create.htm?idautorizzazione=${autorizzazioni.id.codice}','')"><fmt:message key="label.aggiungi_mercato_per_spunta" /></a></li> --%>
		    <li><a href="javascript:historySet('../autorizzazioni/viewAutorizzazione.htm?codice=${autorizzazioni.id.codice}&codiceAnagrafe=${autorizzazioni.anagrafe.id.codice }','../spuntistimercati/create.htm?codice=${autorizzazioni.id.codice}','')"><fmt:message key="label.aggiungi_mercato_per_spunta" /></a></li>				
		</c:if>
	</div>
</div>
</c:if>
<br /> <br /><br /> <br />
<div>	

    <jsp:include page="../spuntistimercati/includeListaSpunte.jsp">
       <jsp:param  name="isCreate" value="false"/>
    </jsp:include>
    
    

	<%-- 
    <div class="titoloTabella">
		<td><fmt:message key="label.mercati_per_cui_e_spuntista"/></td>
	</div> 
	 
	

	<div class="jmesa">
		<table class="table">
			<thead>
				<tr class="header">	
					<td width="20%"><fmt:message key="label.mercato"/></td>
					<td width="10%"><fmt:message key="label.giorno" /></td>
					<td width="20%"><fmt:message key="label.autorizzazione" /></td>
					<td width="10%"><fmt:message key="label.numero_istanza" />(<fmt:message key="label.registrazione_spuntista_istanza" />)</td>
					<td width="10%"><fmt:message key="label.numero_istanza" />(<fmt:message key="label.legata_autorizzazione" />)</td>
					<td width="10%"><fmt:message key="label.data_registrazione" /></td>
					<td width="10%"><fmt:message key="label.attivo" /></td>
					<td width="10%"><fmt:message key="label.data_disattivazione" /></td>
				</tr>
			</thead>
			<tbody>	
			<c:if test="${not empty  mercatiInCuiESpuntista}">
			<c:forEach items="${mercatiInCuiESpuntista}" var="mercatiInCuiSpunstita" varStatus="idx">
			<c:if test="${mercatiInCuiSpunstita.flgAttivo }">
			<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">
	   	 		<td>${mercatiInCuiSpunstita.mercati.descrizione}</td>
	   	 		<td>${mercatiInCuiSpunstita.mercatiUso.descrizione}</td>
	   	 		<td>${mercatiInCuiSpunstita.autorizzazioni.autoriznumero}</td>
	   	 		<td>${mercatiInCuiSpunstita.istanze.numeroistanza}</td>
	   	 		<td>${mercatiInCuiSpunstita.autorizzazioni.istanza.numeroistanza}</td>
	   	 		<td><fmt:formatDate value="${mercatiInCuiSpunstita.dataRegistrazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
	   	 		<c:if test="${mercatiInCuiSpunstita.flgAttivo}">
	   	 			<td><fmt:message key="label.si" /></td>
	   	 		</c:if>
	   	 		<c:if test="${!mercatiInCuiSpunstita.flgAttivo}">
	   	 			<td><fmt:message key="label.no" /></td>
	   	 		</c:if>
	   	 		<td> - </td>   	
			</tr> 
			</c:if>   	 
			</c:forEach>				
			</c:if>
			<c:if test="${empty  mercatiInCuiESpuntista}">
				<tr align="center">
	   	 	   		<td colspan="3"><b><fmt:message key="label.nessun_mercato_per_cui_e_spuntista"/></b></td>
				</tr>   
			</c:if>
			</tbody>
		</table>
		
		
		
		<div class="jmesa">
		<table class="table">
			<thead>
				<tr class="header">	
					<td width="20%"><fmt:message key="label.mercato"/></td>
					<td width="10%"><fmt:message key="label.giorno" /></td>
					<td width="20%"><fmt:message key="label.autorizzazione" /></td>
					<td width="10%"><fmt:message key="label.numero_istanza" />(<fmt:message key="label.registrazione_spuntista_istanza" />)</td>
					<td width="10%"><fmt:message key="label.numero_istanza" />(<fmt:message key="label.legata_autorizzazione" />)</td>
					<td width="10%"><fmt:message key="label.data_registrazione" /></td>
					<td width="10%"><fmt:message key="label.attivo" /></td>
					<td width="10%"><fmt:message key="label.data_disattivazione" /></td>
				</tr>
			</thead>
			<tbody>	
			<c:if test="${not empty  mercatiInCuiESpuntista}">
			<c:forEach items="${mercatiInCuiESpuntista}" var="mercatiInCuiSpunstita" varStatus="idx">
			<c:if test="${!mercatiInCuiSpunstita.flgAttivo }">
			<tr style="background-color: red; color:#FFFFFF; font: bold;" class="${(idx.index % 2 == 0)? 'odd':'even' }">
	   	 		<td>${mercatiInCuiSpunstita.mercati.descrizione}</td>
	   	 		<td>${mercatiInCuiSpunstita.mercatiUso.descrizione}</td>
	   	 		<td>${mercatiInCuiSpunstita.autorizzazioni.autoriznumero}</td>
	   	 		<td>${mercatiInCuiSpunstita.istanze.numeroistanza}</td>
	   	 		<td>${mercatiInCuiSpunstita.autorizzazioni.istanza.numeroistanza}</td>
	   	 		<td><fmt:formatDate value="${mercatiInCuiSpunstita.dataRegistrazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
	   	 		<c:if test="${mercatiInCuiSpunstita.flgAttivo}">
	   	 			<td><fmt:message key="label.si" /></td>
	   	 		</c:if>
	   	 		<c:if test="${!mercatiInCuiSpunstita.flgAttivo}">
	   	 			<td><fmt:message key="label.no" /></td>
	   	 		</c:if>
	   	 		<c:if test="${mercatiInCuiSpunstita.dataDisattivazione != null}">
	   	 			<td> <fmt:formatDate value="${mercatiInCuiSpunstita.dataDisattivazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
	   	 		</c:if>
	   	 		<c:if test="${mercatiInCuiSpunstita.dataDisattivazione == null}">
	   	 			<td> - </td>   	
	   	 		</c:if>
			</tr>
			</c:if>    	 
			</c:forEach>				
			</c:if>
			<c:if test="${empty  mercatiInCuiESpuntista}">
				<tr align="center">
	   	 	   		<td colspan="3"><b><fmt:message key="label.nessun_mercato_per_cui_e_spuntista"/></b></td>
				</tr>   
			</c:if>
			</tbody>
		</table>
		--%>
		
		
		
		
			
	</div>
</div>


