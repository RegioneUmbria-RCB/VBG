<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<div slot="body">
	<h1>Mercati spuntisti</h1>
	
	<div class="vbg-form">
		<fieldset>
			<legend>Dati autorizzazione</legend>
			<c:if test="${istanza.id.codice!=null}">
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
					<span class="header_dato_etichetta"><fmt:message key="label.numero_istanza_autorizzazione" />:</span>
					<span class="header_dato_valore">
						${autorizzazioni.istanza.numeroistanza}
					</span>
				</div>
			</c:if>
			<div class="header_dato">
				<span class="header_dato_etichetta"><fmt:message key="label.autorizzazione" />:</span>
				<span class="header_dato_valore">
				${autorizzazioni.autoriznumero} - <fmt:formatDate value="${autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
				- ${autorizzazioni.autorizcomune.descrizioneEstesa} - ${autorizzazioni.tipologiaregistro.trDescrizione}
				</span>
			</div>
			<div class="header_dato">
				<span class="header_dato_etichetta"><fmt:message key="label.titolare" />:</span>
				<span class="header_dato_valore">
				${autorizzazioni.anagrafe.descrizioneRichiedente}
				</span>
			</div>					
		</fieldset>
		<c:choose>
			<c:when test="${isAttivaConfigurazioneSpuntisti}">					
				<c:if test="${not empty  mercatiSpuntistiHelpers}">
					<br /><br />
				    <div class="titoloTabella">
						<td><fmt:message key="label.mercati_per_cui_richiede_la_spunta"/></td>
					</div>							
					<table class="vbg-table">
						<thead>									
							<th><fmt:message key="label.mercato"/></th>
							<th><fmt:message key="label.giorno" /></th>
							<th><fmt:message key="label.alert" /></th>									
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
					<div class="form-button">
						<a href="javascript:doHref('../spuntistimercati/insertSpuntistiMercati.htm?codiceIstanza=${istanza.id.codice}&idautorizzazione=${autorizzazioni.id.codice}','')"><fmt:message key="label.trasforma_in_spuntista" /></a>
					    <c:if test="${codiceAnagrafe!=null}">
							<a href="javascript:doHref('../spuntistimercati/create.htm?idautorizzazione=${autorizzazioni.id.codice}','')"><fmt:message key="label.aggiungi_mercato_per_spunta" /></a>
						    <a href="javascript:historySet('../autorizzazioni/viewAutorizzazione.htm?codice=${autorizzazioni.id.codice}&codiceAnagrafe=${autorizzazioni.anagrafe.id.codice }','../spuntistimercati/create.htm?codice=${autorizzazioni.id.codice}','')"><fmt:message key="label.aggiungi_mercato_per_spunta" /></a>				
						</c:if>
					</div>
				</c:if>	
				<br /> <br /><br /> <br />
				<div>			
				    <jsp:include page="../spuntistimercati/includeListaSpunte.jsp">
				       <jsp:param  name="isCreate" value="false"/>
				    </jsp:include>
				</div>
			</c:when>
			<c:otherwise>
			    <br /> <br /><br /> <br />
			    <table class="vbg-table">
				    <tr align="center">
						<td colspan="3" align="center" class="warning_header"><b></b><fmt:message key="label.spuntistimercati.configurazione_non_attiva"/></td><td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</div>