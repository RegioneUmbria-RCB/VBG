<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<spring-form:form commandName="cartInfoDizionarioHelper" name="innerForm" id="innerForm_ID">
	
	<div class="titoloSezione">
   		 <b><fmt:message key="stp.label.configurazione_tipologie_endo"/></b> <br />
    </div>
    <fieldset style="border-color: black;">
    <div>
   		<fmt:message key="stp.label.legenda_configurazione_tipologie_endo" />
    </div>
    </fieldset>
	<div>
		<table cellpadding="2" cellspacing="2" width="100%">
			<thead class="header">
				<tr>
					<td style="background-color: #DEDDCC"><b><fmt:message key="label.codice_amministrazione_cart" /></b></td>
				    <td style="background-color: #DEDDCC"><b><fmt:message key="label.amministrazione" /></b></td>
				    <td style="background-color: #DEDDCC"><b><fmt:message key="label.movimento" /></b></td>
				</tr>
			</thead>
			<tbody class="tbody">
			<%int i=1;%>
			<c:forEach items="${cartInfoDizionarioHelper.endoTipo1Helpers}" var="endo_tipo1_var" varStatus="a">
				<tr valign="top" >
					<td style="border-bottom: thin solid;">${endo_tipo1_var.codiceAmministrazioneCart}</td>
					<td style="border-bottom: thin solid;">
					
						<c:if test="${endo_tipo1_var.amministrazioni.id.codice!=null }">
							<b>${endo_tipo1_var.amministrazioni.amministrazione}</b>
						</c:if>
						<c:if test="${endo_tipo1_var.amministrazioni.id.codice ==null }">
							<b style="color: red;"><fmt:message key="label.non_configurato" /></b>
						</c:if>	
						<br /><br />			  
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioni${a.index}"/>		
							<jsp:param name="propertyPath" value="amministrazioniTransient" />				
							<jsp:param name="pathPropertyDescription" value="amministrazioniTransient.amministrazione" />
							<jsp:param name="pathPropertyCode" value="codiciAmministrazioni" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=true" />
							<jsp:param name="titleKey" value="label.ricerca_amministrazioni" />
							<jsp:param name="id_help" value="help_amministrazione" />
						</jsp:include>
						<input type="hidden" name=codiciAmministrazioniCart value="${endo_tipo1_var.codiceAmministrazioneCart}">
					</td>
					<td style="border-bottom: thin solid;">
						
						<c:if test="${endo_tipo1_var.amministrazioni.id.codice!=null && endo_tipo1_var.amministrazioni.tipimovimento!=null &&
						endo_tipo1_var.amministrazioni.tipimovimento.id.tipomovimento!='' }">
							<b>${endo_tipo1_var.amministrazioni.tipimovimento.descrizioneEstesa}</b>
						</c:if>
						<c:if test="${endo_tipo1_var.amministrazioni.id.codice ==null || (endo_tipo1_var.amministrazioni.id.codice!=null && endo_tipo1_var.amministrazioni.tipimovimento==null)}">
							<b style="color: red;"><fmt:message key="label.non_configurato" /></b>
						</c:if>
						<br /><br />
								
						<jsp:include page="../includes/autocompletergenericoTT.jsp" >
								<jsp:param name="idElemento" value="tipimovimento${a.index}"/>		
								<jsp:param name="propertyPath" value="tipimovimentoTransient" />				
								<jsp:param name="pathPropertyDescription" value="tipimovimentoTransient.descrizioneEstesa" />
								<jsp:param name="pathPropertyCode" value="codiciTipimovimento" />
								<jsp:param name="autocompleterAjax" value="findTipiMovimentoForSoftware.htm?codice=" />
								<jsp:param name="titleKey" value="label.ricerca_tipi_movimento" />
								<jsp:param name="id_help" value="help_tipi_mov" />
						</jsp:include>
				</tr>
				<%-- 
				<input type="hidden" value="${cartInfoDizionarioHelper_var.codiceAmministrazioneCart}" name="codiceAmministrazioneCart">
				--%>
			<%i++; %>
			
	   		</c:forEach>
	   		
	   		</tbody>
	   
	</table>
	
	<div style="padding-top: 10px;">
   		<fmt:message key="stp.label.legenda_comportamento_configurazione_tipologie_endo" />
    </div><br/>
    <div>
        <spring-form:checkbox path="isSovrascriviConfigurazioneEndo" value="1"/>
   		<fmt:message key="stp.label.help_configurazione_tipologie_endo" />
    </div>
	</div>
	
	<%--TABELLA PER LA CONFIGURAZIONE DELLA AZIONI NELLA TIPOLOGIA ENDO 2 --%>
	<div class="jmesa">
		<div class="titoloSezione">
	   		<b><fmt:message key="stp.label.configurazione_azioni_tipologie_endo_2" /><br /></b>
	   </div>
	    <fieldset style="border-color: black;">
	    	<fmt:message key="stp.label.help_configurazione_azioni_tipologie_endo_2" />
  		</fieldset>
		<table border="0"  cellpadding="2" cellspacing="0" class="table" width="100%">
			<thead class="header">
				<tr>
					<td><fmt:message key="label.descrizione" /></td>
					<td><fmt:message key="label.azione" /></td>
					
				</tr>
			</thead>
			<tbody class="tbody">
			<%int j=1;%>
			<c:forEach items="${cartInfoDizionarioHelper.stpTipologieEndo2s}" var="stp_tipologieendo2_var" varStatus="b">
				<tr class="<%=(j%2)==0?"odd":"even"%>" valign="top">
					<td>${stp_tipologieendo2_var.descrizione}</td>
					<td>
					<spring-form:select id="lista_azioni_id" path="codiciAzioni" >
						<option value="#" label="Seleziona">Seleziona</option>
					    <c:forEach  items="${azionis}" var="azioni">
					        <c:choose>
								<c:when test="${stp_tipologieendo2_var.azioni!=null && azioni.azId ==  stp_tipologieendo2_var.azioni.azId}">
									<option id="option_id" value="${azioni.azId}" label="${azioni.azDescrizione}" selected="selected">${azioni.azDescrizione} (${azioni.azAzione})</option>
								</c:when>
							<c:otherwise>
									<option id="option_id" value="${azioni.azId}" label="${azioni.azDescrizione}">${azioni.azDescrizione} (${azioni.azAzione})</option>
							</c:otherwise>
						</c:choose>
					    </c:forEach>
					</spring-form:select>
					<input type="hidden" value="${stp_tipologieendo2_var.id.codice}" name="codiceTipologieEndo2">
					<input type="hidden" value="${stp_tipologieendo2_var.descrizione}" name="descrizioneTipologieEndo2">
					</td>
			<%j++; %>
	   		</c:forEach>
	   		
	   		</tbody>
	   
	</table>
	<div style="padding-top: 10px;">
        <spring-form:checkbox path="isSovrascriviConfigurazioneAlberoproc" value="1"/>
   		<fmt:message key="stp.label.help_configurazione_alberoproc" />
    </div>
	</div>
	
	
	
</spring-form:form>
<div id="functions">
<ul>
	 <%-- 
	<li><a href="javascript:doSubmit('importaInterventi.htm','',document.innerForm)"><fmt:message key="button.importa" /></a></li>
	--%>
	<li><a href="javascript:aggiornaDizionario(document.innerForm,'innerForm_ID');"><fmt:message key="button.prosegui" /></a></li>
	<li><a href="javascript:void(0)" onClick="dijit.byId('dialogDivPreElaborazione').hide()"><fmt:message key="button.annulla" /></a></li>
	
</ul>
</div>
