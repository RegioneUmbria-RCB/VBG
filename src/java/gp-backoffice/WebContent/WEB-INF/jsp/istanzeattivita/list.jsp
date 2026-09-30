<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="istanzeattivita.label.lista_istanzeattivita.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="istanzeattivita.label.lista_istanzeattivita.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
     <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzeattivita/list" />
	</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanzeattivita.istanza.id.codice}</c:param>
	</c:import>
	<br class="break" />
	<div id="subcontent">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
			<jsp:param name="commandName" value="istanzeattivita" />
		</jsp:include>
		
		
			<%-- Start Preferenze Utente --%>

			<%
				String displayPreferenze = "display:none;";
				String stylePreferenze = "";
				//gestisce la visualizzazione della tabella altri dati
				if (((String) request.getAttribute(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA)).equals("1")) {
				    displayPreferenze = "";
		   			stylePreferenze="sezioneDatiMeno";
				} else {
				    displayPreferenze = "display:none;";
		    		stylePreferenze="sezioneDatiPiu";
				}
				
				String styleCodiceIstat = "display:none;";				
				//gestisce la visualizzazione della colonna codiceistat
				if (((String) request.getAttribute(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT)).equals("1")) {
				  	styleCodiceIstat="";				  	
				} else {		    
		    		styleCodiceIstat="display:none;";
				}
				String styleIstat = "display:none;";
				//gestisce la visualizzazione della colonna istat
				if (((String) request.getAttribute(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT)).equals("1")) {
				  	styleIstat="";
				} else {		    
		    		styleIstat="display:none;";
				}
				String colspanTotale = "2";
				// gestisce la visualizzazione del totale metriq				
				if (((String) request.getAttribute(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT)).equals("1") && ((String) request.getAttribute(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT)).equals("1")) {
				    colspanTotale = "3";
				}
			%>
			<a class="<%=stylePreferenze %>" 
				id="id_link_preferenze" 
				href="javascript:showHidePanel('preferenzeColonne_id', 'id_link_preferenze', '<%= WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA %>', '${pageContext.request.contextPath}/images/','div');"	
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.modifica_colonne_da_visualizzare"/>">
				<label for="id_link_preferenze"><fmt:message key="label.modifica_colonne_da_visualizzare_e_ordine"/></label>
			</a>
			<div id="preferenzeColonne_id" style="<%= displayPreferenze%>">
				<fieldset>
					<div><fmt:message key="label.spiegazione_salvataggio_preferenze" />
						<div>				
							<input type="checkbox" id="CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT_id" 
								onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT%>',this)" ${CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT_CHECKED}/>
								<label for="CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT_id"><fmt:message key="istanzeattivita.label.codiceistat" /></label>
						</div>
						<div>
							<input type="checkbox" id="CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT_id" 
								onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT%>',this)" ${CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT_CHECKED} />
								<label for="CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT_id"><fmt:message key="attivita.label.istat" /></label>
						</div>	
						<br class="break" />
						<div>
							<fmt:message key="istanzeattivita.label.ordina_codiceistat"/>						
						</div>
						<div>
							<input type="checkbox" id="CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT_id" 
								onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT%>',this)" ${CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT_CHECKED} />
								<label for="CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT_id"><fmt:message key="istanzeattivita.label.codiceistat" /></label>
						</div>			
						<div id="functions">
							<ul>
								<li><a href="javascript:document.location.reload();"><fmt:message key="button.save" /></a></li>
							</ul>
						</div>
						&nbsp;
					</div>		
				</fieldset>
			</div>	
		 	<%-- End Preferenze Utente --%>
		 		
		<form name="istanzeattivitaForm" action="list.htm" id="myForm">
			<br class="break" />
				<%-- Creazione di una tabella distinta delle istanzeattivita di ogni settore --%>
				<c:forEach items="${istanzeattivita.settoreAttivitaMap}" var="current" varStatus="a">						
					<%
						String displaySettore = "";
						String styleSettore = "sezioneDatiMeno";						
					%>		 
					<c:set var="widthIstat" value="55%"></c:set>
					<c:set var="widthCodiceIstat" value="20%"></c:set>
					<%-- visualizzazione allineata campo note --%>
					<c:if test="${current.key.tipiunitamisura!=null}">
						<c:set var="widthIstat" value="45%"></c:set>
						<c:set var="widthCodiceIstat" value="10%"></c:set>
						<c:if test="${CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT_CHECKED==' checked ' and CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT_CHECKED==' checked '}">
							<c:set var="widthCodiceIstat" value="20%"></c:set>
						</c:if>
					</c:if>					
					<a class="<%= styleSettore %>" 
						id="id_link_settore${current.key.id.codicesettore}" 
						href="javascript:showHidePanelBase('visSettoreId${current.key.id.codicesettore}', 'id_link_settore${current.key.id.codicesettore}', '', '${pageContext.request.contextPath}/images/','div',false);"	
						title="<fmt:message key="label.mostra_nasconde_attivita_settore" />: ${current.key.settore}">
						<label for="id_link_settore${current.key.id.codicesettore}" ><fmt:message key="istanzeattivita.label.lista_attivita_settore" />${current.key.settore}</label>
					</a>					
					<div id="visSettoreId${current.key.id.codicesettore}" style="<%= displaySettore%>">					
					<fieldset id="_${current.key.id.codicesettore}">						
						<legend>${current.key.settore}</legend>
						<div class="jmesa">
						<table border="0" cellpadding="2" cellspacing="0" class="table">
							<thead>
								<tr class="header">
									<td width="${widthCodiceIstat}%" style="<%= styleCodiceIstat%>"><fmt:message key="istanzeattivita.label.codiceistat" /> </td>
									<td width="${widthIstat}" style="<%= styleIstat%>"><fmt:message key="attivita.label.istat" /> </td>
									<c:if test="${current.key.tipiunitamisura!=null}">										
										<td width="10%">${current.key.tipiunitamisura.umDescrbreve}</td>		
									</c:if>									
									<td width="16%"><fmt:message key="label.note" /> </td>									
									<td width="2%" align="center"><fmt:message key="label.edit.record" /></td>	
									<td width="2%" align="center"><fmt:message key="label.elimina" /></td>					
								</tr>
							</thead>
							<tbody class="tbody">
								<%int j=1;%>								
								<c:forEach items="${current.value}" var="istanzeattivitaTemp">
									<tr class="<%=(j%2)==0?"odd":"even"%>">
										<td style="<%= styleCodiceIstat%>">
											<a href="view.htm?codice=${istanzeattivitaTemp.id.codice}">${istanzeattivitaTemp.attivita.id.codiceistat}</a>
										</td>
										<td style="<%= styleIstat%>">
											${istanzeattivitaTemp.attivita.istat}
										</td>										
										<c:if test="${current.key.tipiunitamisura!=null}">
											<td align="right">
												<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${istanzeattivitaTemp.metriq}"/>												
											</td>
										</c:if>										
										<td>
											${istanzeattivitaTemp.note}
										</td>
										<td>
					                 		<a class="dettaglioColumn" href="view.htm?codice=${istanzeattivitaTemp.id.codice}" title="<fmt:message key="label.edit.record" />${istanzeattivitaTemp.id.codice}">
												<label><fmt:message key="label.edit.record.image" /></label>
											</a>	
					                 	</td>
										<td>					                  	 
					                		<a class="eliminaRiga" style="float: none;" href="javascript:doHref('eliminaIstanzeattivita.htm?codice=${istanzeattivitaTemp.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${istanzeattivitaTemp.id.codice}">
							               		<label><fmt:message key="label.elimina.image" /></label>
							            	</a>							            				            
					                 	</td>					                 	
									</tr>
									<%j++; %>	
								</c:forEach>
								<c:if test="${current.key.tipiunitamisura!=null}">
									<c:forEach items="${istanzeattivita.settoreTotMQ}" var="totale" varStatus="b">
										<c:if test="${totale.key==current.key}">
											<tr>
												<td colspan="<%= colspanTotale%>" align="right">
													<b><fmt:message key="label.totale" /> ${current.key.settore}:</b>
												</td>
												<td align="right">
													<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${totale.value}"/>																									
												</td>
											</tr>											
										</c:if>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						</div>
					</fieldset>
					</div>
					<br class="break" />
				</c:forEach>								 
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="istanzeattivita.label.lista_istanzeattivita.title" />';
			
			function salvaPreferenza(nomeparametro, objchk){
				var valore = "0";	
				if(objchk.checked==true){
					valore="1";	
				}
				saveUserPreference(nomeparametro, valore);
			}
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codiceIstanza=${istanzeattivita.istanza.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>