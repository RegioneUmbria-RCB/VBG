<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.autorizzazioni.title" />
	</title>
</head>
<body>
	<script type="text/javascript">
		var catMerc='';
		function segnaPresenza(idAut){
			
			
			if(checkCatMerc()){
			<c:if test="${fn:contains(qString,'concessionario')}">
				doHref("${pageContext.request.contextPath}/gestionepresenze/segnaPresenzaConcessionario.htm?${qString}&idAut="+idAut+catMerc);
			</c:if>
			<c:if test="${fn:contains(qString,'spuntista')}">
				doHref("${pageContext.request.contextPath}/gestionepresenze/segnaPresenzaSpuntista.htm?${qString}&idAut="+idAut+catMerc);
			</c:if>
			<c:if test="${fn:contains(qString,'spuntnop')}">
				doHref("${pageContext.request.contextPath}/gestionepresenze/segnaPresenzaSpuntistaNoPosteggio.htm?${qString}&idAut="+idAut+catMerc);
			</c:if>
			<c:if test="${fn:contains(qString,'assenza')}">
				doHref("${pageContext.request.contextPath}/gestionepresenze/aggiornaFlagGiustificazione.htm?${qString}&idAut="+idAut+catMerc);
			</c:if>
			}
		}
		function gestionePresenze(){
			doHref("${pageContext.request.contextPath}/gestionepresenze/list.htm?${qString}");
		}
		function checkCatMerc(){

			
			if ( jQuery( "#catMerc_id" ).length>0 ) {
				catMerc="&catMerc="+jQuery('#catMerc_id').val()
				if(catMerc == ''){
					alert("Selezionare una categoria merceologica.");
					jQuery('#catMerc_id').focus();
					return false;
				}
			}
			return true;
		}
			
	</script>
	<span class="titoloPagina">
		<fmt:message key="label.autorizzazioni.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../autorizzazioni/listDaGestionePresenze" />
	</jsp:include>
	<div id="subcontent">
	
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="autorizzazioni" />
		</jsp:include>
	
		<span class="parametri"><fmt:message key="label.anagrafe" />:<label> ${autorizzazioni.anagrafe.descrizioneRichiedente}</label></span>
		<fieldset>
			<legend><fmt:message key="label.lista_aut_conc" /></legend>
			<form name="autorizzazioniFilter" action="listDaGestionePresenze.htm">
				<jmesa:springTableFacade
					id="autorizzazioniFilter_id" 
					items="${listAutorizzazioni}" 
					var="list_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
			                <jmesa:htmlColumn property="autoriznumero" titleKey="label.numero" filterable="false" >
			                	<a href="javascript:segnaPresenza('${list_var.id.codice }');" title="Segna la presenza">${list_var.autoriznumero }</a>
			                </jmesa:htmlColumn>
			                <jmesa:htmlColumn property="autorizdata" titleKey="label.data" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterable="false" />
							<jmesa:htmlColumn property="autorizcomune.comune" titleKey="label.comune" filterable="false" />
			            	<jmesa:htmlColumn property="tipologiaregistro.trDescrizione" titleKey="label.registro" filterable="false" />
			            	<jmesa:htmlColumn property="istanza.numeroistanza" titleKey="label.istanza" filterable="false" />
			            	<jmesa:htmlColumn property="flagAttiva" titleKey="label.stato" filterable="false">
			            		<c:if test="${list_var.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
			            		<c:if test="${list_var.flagAttiva ne true}"><fmt:message key="label.cessata" /> <c:if test="${list_var.dataCessazione != null}">(<fmt:formatDate value="${list_var.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>)</c:if></c:if>
			            	</jmesa:htmlColumn>
			            	<jmesa:htmlColumn property="id.codice" titleKey="label.posteggio" filterable="false">
			            		<c:if test="${not empty list_var.autorizzazioniConcessionisForFkAutconcAutatt}"> 
			            			<c:forEach items="${list_var.autorizzazioniConcessionisForFkAutconcAutatt}" var="conc">
										<span>
											<fmt:message key="label.mercati" />: ${conc.mercati.descrizione}<br />
											<fmt:message key="label.giorno" />: ${conc.mercatiUso.descrizione}<br />
											<fmt:message key="label.posteggio" />: ${conc.mercatiD.codiceposteggio}<br />
										</span>			            			
			            			</c:forEach>
								</c:if>
			            	</jmesa:htmlColumn>
			            	<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%" >
			            		<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../autorizzazioni/viewAutorizzazione.htm?codice=${list_var.id.codice}&codiceAnagrafe=${list_var.anagrafe.id.codice}', '')" title="<fmt:message key="label.edit.record" /> ${list_var.id.codice}" >
			            			<label><fmt:message key="label.edit.record.image" /></label>
			            		</a>
			            	</jmesa:htmlColumn>
			            </jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${param.codiceAnagrafe}" name="codiceAnagrafe" />
				<input type="hidden" value="${param.codiceMercato}" name="codiceMercato" />
				<input type="hidden" value="${param.usoMercato}" name="usoMercato" />
				<input type="hidden" value="${param.giornoMercato}" name="giornoMercato" />
				<input type="hidden" value="${param.idPosteggio}" name="idPosteggio" />
				<input type="hidden" value="${param.codice}" name="codice" />
				<input type="hidden" value="${param.occupante}" name="occupante" />
			</form>
			<script type="text/javascript">
				var _jmesaUrl='listDaGestionePresenze.htm?codiceAnagrafe=${param.codiceAnagrafe}&codiceMercato=${param.codiceMercato}&usoMercato=${param.usoMercato}&giornoMercato=${param.giornoMercato}&idPosteggio=${param.idPosteggio}&codice=${param.codice}&occupante=${param.occupante}';
				var _captionTab='<fmt:message key="label.autorizzazioni" />';
			</script>
		</fieldset>
		
		<c:if test="${not empty listaCategorieMerceologicheGiornataMercatoAmmesse}">		
			<fieldset><legend>Categoria merceologica</legend>
			<select name="catMerc" id="catMerc_id">
				<option value="">...</option>
				<c:forEach items="${listaCategorieMerceologicheGiornataMercatoAmmesse}" var="currCatMerc">
					<option value="${currCatMerc.codice}" label="${currCatMerc.descrizione}"/>
				</c:forEach>
			</select>
		</fieldset>		
		</c:if>
		
		
		<c:if test="${not empty useCatMerc}">
		<fieldset><legend>Categoria merceologica</legend>
			<select name="catMerc" id="catMerc_id">
				<option value="">...</option>
				<c:forEach items="${catMercList}" var="currCatMerc">
					<option value="${currCatMerc}" label="${currCatMerc}"/>
				</c:forEach>
			</select>
		</fieldset>
		</c:if>
	</div>
	<br />
	<div id="functions">
		<ul>
			<li><a href="javascript:historySet('${_urlback}','../autorizzazioni/createAutorizzazione.htm?codiceAnagrafe=${autorizzazioni.anagrafe.id.codice }','')"><fmt:message key="button.nuova_autorizzazione" /></a></li>
			<li><a href="javascript:gestionePresenze();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>