<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.istanzestradario" />
	</title>
	<style>
		.row {
			display: flex;
			align-items: stretch;
    		justify-content: flex-start;
    		width: 100%
		}
		.col {
			flex-grow: 1;
		    flex-basis: 0%;
    		flex-shrink: 0;
		}
	</style>
	<script type="text/javascript">
		vbg.ready(() => {
			let buttonCartografico = document.querySelector('.mostra-mappa');
			
			buttonCartografico?.addEventListener('click',async (e) => {
				e.preventDefault();
				window.vbg.mostraModalCaricamento();
				try
				{
					let urlMappa = await recuperaUrlMappa();
					let jsResponse= urlMappa;

					if(jsResponse.esito.esito == "KO"){
		                throw new Error(jsResponse.esito.exceptions.join(' - '));
		            }
					
					if( jsResponse.method === "GET" ){
		                location.replace(jsResponse.url);
		                return;
		            }
					if( jsResponse.method === "POST" ){
		                const formMappa = document.createElement("form");
		                formMappa.method = "POST";
		                formMappa.action = jsResponse.url;
		                console.log(jsResponse.body);
		                jsResponse.body.forEach(item => {
		                    const input = document.createElement("input");
		                    input.type = "hidden";
		                    input.name = item.chiave;
		                    input.value = item.valore;
		                    formMappa.appendChild(input);
		                });
		                
		                document.body.appendChild(formMappa);
		                formMappa.submit();
		                return;
		            }
					
				}
				catch(error) {
					console.log(error);
					alert('Si sono verificati errori durante l\'apertura della mappa: ' + error);
				}
				window.vbg.nascondiModalCaricamento();
			});
		});
		
		async function recuperaUrlMappa(){

			let uuid = '${istanzestradarioCommand.entity.uuid}';
			let codiceIstanza = '${istanzestradarioCommand.entity.istanza.id.codice}';
												
			const response = await fetch('../istanzestradariocartografico/jsonRecuperaUrl.htm?codiceIstanza=' + codiceIstanza + '&uuid=' + uuid, {
        		method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                }
        	});
			
			const jsResponse = await response.json();
			
			if(jsResponse.esito.esito == "KO"){
				throw new Error(jsResponse.esito.exceptions.join(' - '));
			}
			
			return jsResponse;
		}
	</script>
</head>
<body>
    <%
    	// Recupero la mappa dei campi che gestiscono il dettaglio
    	// Value : true lo gestiscono
    	// Value : false non lo gestiscono
    	Map<String,String> campiGestitoDettaglio=new HashMap<String,String>();
    	campiGestitoDettaglio=( Map<String,String>)request.getAttribute("campiGestitoDettaglio");
    %>
	<span class="titoloPagina">
		<fmt:message key="label.istanzestradario" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<c:if test="${istanzestradarioCommand.displayMode eq istanzestradarioCommand.displayConstants.NEW}">
    	<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../istanzestradario/create" />
			<jsp:param name="qs" value="codiceIstanza%3D${istanzestradarioCommand.entity.istanza.id.codice}%26software%3D${istanzestradarioCommand.entity.istanza.software.codice}" />
		</jsp:include>
	</c:if>
	<c:if test="${istanzestradarioCommand.displayMode eq istanzestradarioCommand.displayConstants.EDIT}">
	    <jsp:include page="../includes/history.jsp">
				<jsp:param name="path" value="../istanzestradario/view" />
				<jsp:param name="qs" value="codice%3D${istanzestradarioCommand.entity.id.codice}%26software%3D${istanzestradarioCommand.entity.istanza.software.codice}" />
		</jsp:include>
	</c:if>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanzestradarioCommand.entity.istanza.id.codice}</c:param>
	</c:import>
	<%-- PANNELLO EVENTI --%>
	<jsp:include page="../includes/pannelloEventi.jsp">
		<jsp:param name="codIstanza"
			value="${istanzestradarioCommand.entity.istanza.id.codice}" />
	</jsp:include>
	<br class="clear" />
	<div id="subcontent">
		<c:set var="VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO)%></c:set>
		<c:set var="VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT)%></c:set>
		<spring-form:form commandName="istanzestradarioCommand" name="inviodati">
			<label for="codviario_id" style="display: none;"><fmt:message key="label.codice_viario" /></label>
			<spring-form:hidden id="codviario_id" path="entity.stradario.codviario" />
			<spring-form:hidden id="valido_id" path="entity.valido" />
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzestradarioCommand" />
		    </jsp:include>
			<spring-form:hidden id="codicecomune_id" path="entity.istanza.comune.codicecomune" />
		    <c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
				<c:set var="displayAlert">none;</c:set>
				<c:if test="${istanzestradarioCommand.entity.valido eq false or empty istanzestradarioCommand.entity.valido}">
					<c:if test="${istanzestradarioCommand.displayMode ne istanzestradarioCommand.displayConstants.NEW}">
						<c:set var="displayAlert"></c:set>
					</c:if>	
				</c:if>
				<div id="stradario_validato_id" class="alertLine" style="display: ${displayAlert};">
					<fmt:message key="label.stradario_non_validato_da_sit" />			
				</div>
				<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT_IN_REQUEST eq 'SIT_QUAESTIOFLORENZIA' }">	
					<c:if test="${istanzestradarioCommand.displayMode eq istanzestradarioCommand.displayConstants.EDIT}">
						<c:if test="${empty istanzestradarioCommand.entity.idPuntoSit}">
							<div id="stradario_geolocalizzato_id" class="warningLine">
								<fmt:message key="label.stradario_non_geolocalizzato_da_sit" />			
							</div>
						</c:if>
					</c:if>
				</c:if>
			</c:if>
			<div class="vbg-form">
				<fieldset>
					<legend><fmt:message key="label.dati_generali" /></legend>
					<div class="form-group">
						<label><fmt:message key="label.primario" /></label>
						<spring-form:checkbox id="primario_id" path="entity.primario" />
						<spring-form:errors path="entity.primario" cssClass="error"/>
					</div>
					<div class="form-group">
						<c:choose>
							<c:when test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true && inite:contains(campiGestiti, 'codcivico_id')}">
									<label for="codcivico_id" style="display: none;"><fmt:message key="label.codice_civico" /></label>
									<c:set var="codCivicoTitle" scope="page"><fmt:message key="label.codice_civico.help" /></c:set>					
									<spring-form:input id="codcivico_id" path="entity.codicecivico" readonly="true" title="${codCivicoTitle}" />
							</c:when>
							<c:otherwise>
									<label for="codcivico_id" style="display: none;"><fmt:message key="label.codice_civico" /></label>
									<spring-form:hidden id="codcivico_id" path="entity.codicecivico" />
							</c:otherwise>
						</c:choose>
					</div>
					<div class="form-group">
						<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
							<c:choose>
								<c:when test="${VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT_IN_REQUEST eq 'SIT_QUAESTIOFLORENZIA' }">
									<c:if test="${istanzestradarioCommand.displayMode eq istanzestradarioCommand.displayConstants.EDIT}">
										<label for="idPuntoSit_id" title="<fmt:message key="label.id_punto_sit.help" />"><fmt:message key="label.id_punto_sit" /></label>
										<c:set var="idPuntoSitTitle" scope="page"><fmt:message key="label.id_punto_sit.help" /></c:set>					
										<spring-form:input id="idPuntoSit_id" path="entity.idPuntoSit" readonly="true" title="${idPuntoSitTitle}" />						
									</c:if>
								</c:when>
								<c:otherwise>
									<label for="idPuntoSit_id" style="display: none;"><fmt:message key="label.id_punto_sit" /></label>
									<spring-form:hidden id="idPuntoSit_id" path="entity.idPuntoSit" />
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					<div class="form-group">
						<c:if test="${not empty tipiLocalizzazionis}">
							<label for="tipo_localizzazione_id"><fmt:message key="label.tipo_localizzazione" /></label>
							<spring-form:select id="tipo_localizzazione_id" path="entity.tipiLocalizzazioni.id.codice">
								<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
								<spring-form:options items="${tipiLocalizzazionis}" itemValue="id.codice" itemLabel="descrizione"/>
							</spring-form:select> 
						</c:if>
					</div>
					<div class="form-group">
						<label for="via_id"><fmt:message key="label.indirizzo" /></label>
						<jsp:include page="../includes/javascriptSIT.jsp" >
							<jsp:param name="PROVENIENZA" value="ISTANZESTRADARIO" />
							<jsp:param name="CODICEISTANZA" value="${istanzestradarioCommand.entity.istanza.id.codice}" />
						</jsp:include>
						<jsp:include page="../includes/searchstradario.jsp" >
							<jsp:param name="idElemento" value="stradario_id" />						
							<jsp:param name="pathStradario" value="entity.stradario" />
							<jsp:param name="stradarioAutocompleterAjax" value="findStradario.htm?searchDisabilitati=false" />
							<jsp:param name="ajaxCallBack" value="filterComune" />
							<jsp:param name="stradarioHideFunctions" value="${isAddStradario}" />
							<jsp:param name="afterUpdateElement" value="ricercaStradarioAfterUpdate" />
						</jsp:include>
						<a style="float: none;"
							href="javascript:visualizzaAltreIstanze('altreIstanzeDialogDiv');"	
							title="<fmt:message key="label.altre_istanze_con_la_stessa_localizzazione" />">
							<i class="fa fa-layer-group fa-lg"></i>
						</a>
						<div dojoType="dijit.Dialog" id="altreIstanzeDialogDiv" title="<fmt:message key="label.altre_istanze_con_la_stessa_localizzazione" />">
							<div dojoType="dijit.layout.ContentPane" class="generic_dialog">
								<table>
									<tr>
										<td><label for="_civico"><fmt:message key="label.civico" /></label>
										<input type="checkbox" id="_civico" name="_civico" checked="checked" onclick="visualizzaListaIstanze();"/></td>
										<td><label for="_esponente"><fmt:message key="label.esponente" /></label>
										<input type="checkbox" id="_esponente" name="_esponente" checked="checked" onclick="visualizzaListaIstanze();"/></td>
										<td><label for="_colore"><fmt:message key="label.colore" /></label>
										<input type="checkbox" id="_colore" name="_colore" checked="checked" onclick="visualizzaListaIstanze();"/></td>
										<td><label for="_accessoTipo"><fmt:message key="label.accesso_tipo"/></label>
										<input type="checkbox" id="_accessoTipo" name="_accessoTipo" checked="checked" onclick="visualizzaListaIstanze();"/></td>													
										<td><label for="_accessoNumero"><fmt:message key="label.accesso_numero"/></label>
										<input type="checkbox" id="_accessoNumero" name="_accessoNumero" checked="checked" onclick="visualizzaListaIstanze();"/></td>													
										<%-- <td><label for="_accessoDescrizione"><fmt:message key="label.accesso_descrizione"/></label>
										<input type="checkbox" id="_accessoDescrizione" name="_accessoDescrizione" checked="checked" onclick="visualizzaListaIstanze();"/></td> --%>
									</tr>	
								</table>
								<div id="listaIstanzeAltriIndirizziDiv"></div>
							</div>
						</div>
						<c:if test="${isNotificheASLPresenti eq true && istanzestradarioCommand.displayMode eq istanzestradarioCommand.displayConstants.EDIT }">
							<a style="float: none;"
								href="javascript:historySet('${_urlback }','../notificheausl/list.htm?filterIndirizzo=${istanzestradarioCommand.entity.stradario.descrizione}&filterRagSoc=','') " 
								title="<fmt:message key="label.notifiche_asl" />">
								<i class="fa fa-laptop-medical fa-lg"></i>
							</a>	
						</c:if>
						<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
							<%
							String isViaDettaglio=campiGestitoDettaglio.get("stradario_id");
							pageContext.setAttribute("isViaDettaglio", isViaDettaglio);
							%>
						</c:if>
						<c:if test="${cartograficoAttivo eq true}">
							<a style="float: none;" href="" title="<fmt:message key="label.cartografico.aprimappa" />">
								<i class="fa fa-map-marked-alt fa-lg mostra-mappa"></i>
							</a>
						</c:if>
					</div>
					<div class="row">
						<div class="form-group col">
							<label for="civico_id"><fmt:message key="label.civico" /></label>
							<%
							String isCivicoDettaglio=campiGestitoDettaglio.get("civico_id");
							pageContext.setAttribute("isCivicoDettaglio", isCivicoDettaglio);
							%>
							<jsp:include page="../includes/campo_sit.jsp" >
								<jsp:param name="entityPath" value="entity.civico" />
								<jsp:param name="idCampo" value="civico_id" />
								<jsp:param name="size" value="5" />
								<jsp:param name="isDettaglio" value="${isCivicoDettaglio}"/>
								<jsp:param name="resettaCivico" value="true" />
							</jsp:include>
							<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
								<script type="text/javascript">
									jQuery(document).ready(function(){ 
								    	ajaxSitLink('stradario','civico_sit_id','PuntoDaIndirizzo');
								    });
								</script>
								<span id="civico_sit_id"></span>						    				    
							</c:if>
						</div>
						<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldLocEsponente')}">
							<div class="form-group col">
								<label for="esponente_id"><fmt:message key="label.esponente" /></label>
								<%
								String isEsponenteDettaglio=campiGestitoDettaglio.get("esponente_id");
								pageContext.setAttribute("isEsponenteDettaglio", isEsponenteDettaglio);
								%>
								<jsp:include page="../includes/campo_sit.jsp" >
							        <jsp:param name="entityPath" value="entity.esponente" />
							        <jsp:param name="idCampo" value="esponente_id" />
							        <jsp:param name="size" value="5" />
							        <jsp:param name="isDettaglio" value="${isEsponenteDettaglio}"/>
							        <jsp:param name="resettaCivico" value="true" />
							    </jsp:include>
							</div>
						</c:if>
						<c:if test="${isStradariocoloreVisible eq true }">
							<div class="form-group col">
								<label for="colore_id"><fmt:message key="label.colore" /></label>
								<c:set var="fnColoreSit"></c:set>
								<c:if test="${inite:contains(campiGestiti, 'colore_id')}">
									<c:set var="fnColoreSit">resettaCodiceCivicoID();validaSIT($('colore_id'));</c:set>
								</c:if>	
								<spring-form:select id="colore_id" path="entity.stradariocolore.id.codicecolore" onchange="${fnColoreSit}">
									<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
									<spring-form:options items="${stradariocoloreList }" itemValue="id.codicecolore" itemLabel="colore"/>
								</spring-form:select>
								<spring-form:errors path="entity.stradariocolore" cssClass="error"/>
								<span id="spinner-colore_id" style="display: none;"><img alt="richiesta in corso..." src="<%=request.getContextPath()%>/images/spinner.gif" />richiesta in corso...</span>
								&nbsp;
								<c:if test="${inite:contains(campiGestiti, 'colore_id')}">
									<%
									String isColoreDettaglio=campiGestitoDettaglio.get("colore_id");
									pageContext.setAttribute("isColoreDettaglio", isColoreDettaglio);
									%>
									<jsp:include page="../includes/funzioni_sit.jsp" >
										<jsp:param name="idCampo" value="colore_id" />
									    <jsp:param name="isDettaglioCampo" value="${isColoreDettaglio}" />
									</jsp:include>
								</c:if>
								<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
									<c:choose>
										<c:when test="${VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT_IN_REQUEST eq 'SIT_QUAESTIOFLORENZIA' }">
											<script type="text/javascript">
												jQuery(document).ready(function(){ 
											    	ajaxSitLink('stradario','colore_sit_id','PuntoDaColore');
											    });
											</script>
											<span id="colore_sit_id"></span>
										</c:when>
									</c:choose>
								</c:if>
							</div>
						</c:if>
					</div>
					<div class="row">
						<div class="form-group col">
							<label for="accessotipo_id"><fmt:message key="label.accesso_tipo"/></label>
							<% 
							String isaccessoTipo=campiGestitoDettaglio.get("accessotipo_id");
							pageContext.setAttribute("isaccessoTipo", isaccessoTipo);
							%>
							<jsp:include page="../includes/campo_sit.jsp" >
								<jsp:param name="entityPath" value="entity.accessoTipo" />
								<jsp:param name="idCampo" value="accessotipo_id" />
								<jsp:param name="size" value="5" />
								<jsp:param name="isDettaglio" value="${isaccessoTipo}"/>
								<jsp:param name="resettaCivico" value="false" />
							</jsp:include>
							<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
								<script type="text/javascript">
									jQuery(document).ready(function(){ 
										ajaxSitLink('stradario','accesso_tipo_sit_id','PuntoDaAccessoTipo');
								    });
								</script>
								<span id="accesso_tipo_sit_id"></span>
							</c:if>
						</div>
						<div class="form-group col">
							<label for="accessonumero_id"><fmt:message key="label.accesso_numero"/></label>
							<% 
							String isaccessoNumero=campiGestitoDettaglio.get("accessonumero_id");
							pageContext.setAttribute("isaccessoNumero", isaccessoNumero);
							%>
							<jsp:include page="../includes/campo_sit.jsp" >
								<jsp:param name="entityPath" value="entity.accessoNumero" />
								<jsp:param name="idCampo" value="accessonumero_id" />
								<jsp:param name="size" value="5" />
								<jsp:param name="isDettaglio" value="${isaccessoNumero}"/>
								<jsp:param name="resettaCivico" value="false" />
							</jsp:include>
							<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
								<script type="text/javascript">
									jQuery(document).ready(function(){
										ajaxSitLink('stradario','accesso_numero_sit_id','PuntoDaAccessoNumero');
									});
								</script>
								<span id="accesso_numero_sit_id"></span>						    				    
							</c:if>
						</div>
						<div class="form-group col">
							<label for="accessodescrizione_id"><fmt:message key="label.accesso_descrizione"/></label>
							<% 
							String isaccessoDescrizione=campiGestitoDettaglio.get("accessodescrizione_id");
							pageContext.setAttribute("isaccessoDescrizione", isaccessoDescrizione);
							%>
							<jsp:include page="../includes/campo_sit.jsp" >
								<jsp:param name="entityPath" value="entity.accessoDescrizione" />
								<jsp:param name="idCampo" value="accessodescrizione_id" />
								<jsp:param name="size" value="5" />
								<jsp:param name="isDettaglio" value="${isaccessoDescrizione}"/>
								<jsp:param name="resettaCivico" value="false" />
							</jsp:include>
							<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
								<script type="text/javascript">
									jQuery(document).ready(function(){ 
								    	ajaxSitLink('stradario','accesso_descrizione_sit_id','PuntoDaAccessoDescrizione');
									});
								</script>
								<span id="accesso_descrizione_sit_id"></span>						    				    
							</c:if>
						</div>
					</div>
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldScalaInternoEspInterno')}">
						<div class="row">
							<div class="form-group col">
								<label for="scala_id"><fmt:message key="label.scala" /></label>
								<%
								String isscalaDettaglio=campiGestitoDettaglio.get("scala_id");
							    pageContext.setAttribute("isscalaDettaglio", isscalaDettaglio);
								%>
								<jsp:include page="../includes/campo_sit.jsp" >
							        <jsp:param name="entityPath" value="entity.scala" />
							        <jsp:param name="idCampo" value="scala_id" />
							        <jsp:param name="size" value="5" />
							        <jsp:param name="isDettaglio" value="${isscalaDettaglio}"/>
							        <jsp:param name="resettaCivico" value="false" />
							    </jsp:include>
						    </div>
						    <div class="form-group col">
							    <label for="piano_id"><fmt:message key="label.piano" /></label>
							    <%
								String isPianoDettaglio=campiGestitoDettaglio.get("piano_id");
								pageContext.setAttribute("isPianoDettaglio", isPianoDettaglio);
								%>
								<jsp:include page="../includes/campo_sit.jsp" >
									<jsp:param name="entityPath" value="entity.piano" />
									<jsp:param name="idCampo" value="piano_id" />
									<jsp:param name="size" value="5" />
									<jsp:param name="isDettaglio" value="${isPianoDettaglio}"/>
									<jsp:param name="resettaCivico" value="false" />
								</jsp:include>
							</div>
							<div class="form-group col">
								<label for="interno_id"><fmt:message key="label.interno" /></label>
								<%
								String isInternoDettaglio=campiGestitoDettaglio.get("interno_id");
							    pageContext.setAttribute("isInternoDettaglio", isInternoDettaglio);
								%>				
								<jsp:include page="../includes/campo_sit.jsp" >
							        <jsp:param name="entityPath" value="entity.interno" />
							        <jsp:param name="idCampo" value="interno_id" />
							        <jsp:param name="size" value="5" />
							        <jsp:param name="isDettaglio" value="${isInternoDettaglio}"/>
							        <jsp:param name="resettaCivico" value="false" />
							    </jsp:include>
							</div>
						</div>					
					</c:if>
					<div class="form-group">
						<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldScalaInternoEspInterno')}">
							<label for="esponenteinterno_id"><fmt:message key="label.esponente_interno" /></label>
							<%
							String isEspInternoDettaglio=campiGestitoDettaglio.get("esponenteinterno_id");
						    pageContext.setAttribute("isEspInternoDettaglio", isEspInternoDettaglio);
							%>	
							<jsp:include page="../includes/campo_sit.jsp" >
						        <jsp:param name="entityPath" value="entity.esponenteinterno" />
						        <jsp:param name="idCampo" value="esponenteinterno_id" />
						        <jsp:param name="size" value="5" />
						        <jsp:param name="isDettaglio" value="${isEspInternoDettaglio}"/>
						        <jsp:param name="resettaCivico" value="false" />
						    </jsp:include>
						</c:if>
					</div>
					<div class="form-group">
						<label for="fabbricato_id"><fmt:message key="label.fabbricato" /></label>
						<%
						String isFabbInternoDettaglio=campiGestitoDettaglio.get("fabbricato_id");
						pageContext.setAttribute("isFabbInternoDettaglio", isFabbInternoDettaglio);
						%>	
						<jsp:include page="../includes/campo_sit.jsp" >
					        <jsp:param name="entityPath" value="entity.fabbricato" />
					        <jsp:param name="idCampo" value="fabbricato_id" />
					        <jsp:param name="size" value="30" />
					        <jsp:param name="isDettaglio" value="${isFabbInternoDettaglio}"/>
					        <jsp:param name="resettaCivico" value="false" />
					    </jsp:include>
						<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldDettaglioFabbricato')}">
							<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
								<b></b>
							</c:if>
						</c:if>
					</div>
					<div class="form-group">
						<label for="km_id"><fmt:message key="label.km" /></label>
						<%
						String isKmDettaglio=campiGestitoDettaglio.get("km_id");
						pageContext.setAttribute("isKmDettaglio", isKmDettaglio);
						%>
						<jsp:include page="../includes/campo_sit.jsp" >
					        <jsp:param name="entityPath" value="entity.km" />
					        <jsp:param name="idCampo" value="km_id" />
					        <jsp:param name="size" value="5" />
					        <jsp:param name="isDettaglio" value="${isKmDettaglio}"/>
					        <jsp:param name="resettaCivico" value="false" />
					    </jsp:include>
					</div>
					<div class="form-group">
						<label for="cap_id"><fmt:message key="label.cap" /></label>
						<%
						String isCapDettaglio=campiGestitoDettaglio.get("cap_id");
						pageContext.setAttribute("isCapDettaglio", isCapDettaglio);
						%>				
						<jsp:include page="../includes/campo_sit.jsp" >
					        <jsp:param name="entityPath" value="entity.cap" />
					        <jsp:param name="idCampo" value="cap_id" />
					        <jsp:param name="size" value="5" />
					         <jsp:param name="isDettaglio" value="${isCapDettaglio}"/>
					        <jsp:param name="resettaCivico" value="false" />
					    </jsp:include>
					</div>
					<div class="form-group">
						<label for="notestradario_id"><fmt:message key="label.note" /></label>
						<spring-form:textarea id="notestradario_id" path="entity.note" cols="73" rows="3" />			
						<spring-form:errors path="entity.note" cssClass="error"/>
					</div>
					<div class="row">
						<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldLocFrazioneCircoscrizione')}">
							<div class="form-group col">
								<label for="frazione_id"><fmt:message key="label.frazione" /></label>
								<%
								String isFrazioneDettaglio=campiGestitoDettaglio.get("frazione_id");
							    pageContext.setAttribute("isFrazioneDettaglio", isFrazioneDettaglio);
								%>	
								<jsp:include page="../includes/campo_sit.jsp" >
							        <jsp:param name="entityPath" value="entity.frazione" />
							        <jsp:param name="idCampo" value="frazione_id" />
							        <jsp:param name="size" value="15" />
							        <jsp:param name="isDettaglio" value="${isFrazioneDettaglio}"/>
							        <jsp:param name="resettaCivico" value="false" />
							    </jsp:include>
							</div>
							<div class="form-group col">
							    <label for="quartiere_id"><fmt:message key="label.quartiere" /></label>
							    <%
								String isQuartiereDettaglio=campiGestitoDettaglio.get("quartiere_id");
							    pageContext.setAttribute("isQuartiereDettaglio", isQuartiereDettaglio);
								%>	
								<jsp:include page="../includes/campo_sit.jsp" >
							        <jsp:param name="entityPath" value="entity.quartiere" />
							        <jsp:param name="idCampo" value="quartiere_id" />
							        <jsp:param name="size" value="15" />
							        <jsp:param name="isDettaglio" value="${isQuartiereDettaglio}"/>
							        <jsp:param name="resettaCivico" value="false" />
							    </jsp:include>
							</div>
							<div class="form-group col">
							    <label for="circoscrizione_id"><fmt:message key="label.circoscrizione" /></label>
							    <%
								String isCircoscrDettaglio=campiGestitoDettaglio.get("circoscrizione_id");
							    pageContext.setAttribute("isCircoscrDettaglio", isCircoscrDettaglio);
								%>
								<jsp:include page="../includes/campo_sit.jsp" >
							        <jsp:param name="entityPath" value="entity.circoscrizione" />
							        <jsp:param name="idCampo" value="circoscrizione_id" />
							        <jsp:param name="size" value="20" />
							        <jsp:param name="isDettaglio" value="${isCircoscrDettaglio}"/>
							        <jsp:param name="resettaCivico" value="false" />
							    </jsp:include>
							</div>
						</c:if>
					</div>
					<div class="row">
						<div class="form-group col">
							<label for="longitudine_id"><fmt:message key="label.longitudine" /></label>
							<spring-form:input id="longitudine_id" path="entity.longitudine" size="50" />
							<spring-form:errors path="entity.longitudine" cssClass="error"/>
						</div>
						<div class="form-group col">
							<label for="latitudine_id"><fmt:message key="label.latitudine" /></label>
							<spring-form:input id="latitudine_id" path="entity.latitudine" size="50" />
							<spring-form:errors path="entity.latitudine" cssClass="error"/>
						</div>
						<div class="form-group col">&nbsp;</div>
					</div>
			</fieldset>
			<fieldset>
				<legend><fmt:message key="label.lista_mappali" /></legend>
				<input type="hidden" name="daValidare" id="daValidare" value="0" />
				<table class="vbg-table">
					<thead>
						<tr>
							<th><fmt:message key="label.catasto" /></th>
							<th><fmt:message key="label.sezione" /></th>
							<th><fmt:message key="label.foglio" /></th>
							<th><fmt:message key="label.particella" /></th>
							<th><fmt:message key="label.sub" /></th>
							<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldUnitaImmobiliare')}">
								<th><label for="unitaimmob_id_N"><fmt:message key="label.unita_immobiliare" /></th>
							</c:if>
							<th><fmt:message key="label.azioni" /></th>
						</tr>
					</thead>
					<c:set var="colspan" value="7" />
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldUnitaImmobiliare')}">
						<c:set var="colspan" value="6" />
					</c:if>
					<tbody>
						<c:set var="radioSettato" value="<%=Boolean.FALSE %>"/>
						<c:set var="displaySitFunction" value=""/>
						<c:forEach items="${istanzestradarioCommand.entity.istanzemappalis}" var="istMappale" varStatus="a">
							<%int i=0;%>
							<c:choose>
								<c:when test="${a.index > 0 and  VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
									<tr>
										<td>
											${istMappale.catasto.descrizione}
											<spring-form:hidden id="tipocatasto_id_${a.index}" path="entity.istanzemappalis[${a.index}].catasto.codice" />
										</td>
										<td>
											${istMappale.sezione}
											<spring-form:hidden id="sezione_id_${a.index}" path="entity.istanzemappalis[${a.index}].sezione" />
										</td>
										<td>
											${istMappale.foglio}							
											<spring-form:hidden id="foglio_id_${a.index}" path="entity.istanzemappalis[${a.index}].foglio" />
										</td>
										<td>
											${istMappale.particella}							
											<spring-form:hidden id="particella_id_${a.index}" path="entity.istanzemappalis[${a.index}].particella" />
										</td>
										<td>
											${istMappale.sub}							
											<spring-form:hidden id="sub_id_${a.index}" path="entity.istanzemappalis[${a.index}].sub" />
										</td>
										<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldUnitaImmobiliare')}">
											<td>
												${istMappale.unitaimmob}							
												<spring-form:hidden id="unitaimmob_id_${a.index}" path="entity.istanzemappalis[${a.index}].unitaimmob" />
											</td>
										</c:if>
										<td>
											<a class="copiaRecord" style="clear: right ;" href="javascript:confermaCreazioneNuovoIstanzastradario('message_dialog_confirm_${a.index}');" title="<fmt:message key="label.separastradario.help"/>">
												<label><fmt:message key="label.separastradario" /></label>
											</a>
											<%-- Crea la finestra di dialogo che descrive all'operatore l'operazione che sta per fare e gli dà la 
												possibilità di effettuarla o annullarla
											--%>
											<script type="text/javascript">		
												function confermaCreazioneNuovoIstanzastradario(divId){
													dijit.byId(divId).show();
												}
											</script>
											<div style="display: none;" align="center" dojoType="dijit.Dialog" id="message_dialog_confirm_${a.index}" title="<fmt:message key="label.messaggio_conferma"/>">
										 		<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width:400px;height: 120px">
													<div align="center">
														<div align="center"><fmt:message key="javascript.confirm.crea_nuova_istanza_stradario_da_mappale" /></div>
														<br class="clear" />
														<div style="float: none;" id="functions">
															<ul>
												    			<li><a href="javascript:doHref('separaMappale.htm?codiceMappale=${istMappale.id.codice}&codiceIstanzaStradario=${istanzestradarioCommand.entity.id.codice}','');" ><fmt:message key="button.ok" /></a></li>
																<li><a href="javascript:void(0)" onClick="dijit.byId('message_dialog_confirm_${a.index}').hide()"><fmt:message key="button.annulla" /></a></li>
												   			</ul>
												   		</div>
													</div>		
												</div>	
											</div>
											<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
												<script type="text/javascript">
											    	jQuery(document).ready(function(){
														ajaxSitLink('mappali','sitfx_${a.index}mappali_sit_id${a.index}','PuntoDaMappale');
											    	});
											    </script>
											    <span id="sitfx_${a.index}mappali_sit_id${a.index}"></span>										   
											</c:if>	
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td>
											<c:if test="${istMappale.catasto != null}">
												<c:set var="fnCatastoSit" value=""/>
												<c:if test="${inite:contains(campiGestiti, 'tipocatasto_id')}">
													<c:set var="fnCatastoSit" >validaSIT($('tipocatasto_id_${a.index}'));</c:set>
												</c:if>
												<spring-form:select id="tipocatasto_id_${a.index}" path="entity.istanzemappalis[${a.index}].catasto.codice" onchange="savePreference(this);${fnCatastoSit}" >
											    	<spring-form:options items="${catastoList}" itemValue="codice" itemLabel="descrizione"/> 
												</spring-form:select>
												<span id="spinner-tipocatasto_id_${a.index}" style="display: none;"><img alt="richiesta in corso..." src="<%=request.getContextPath()%>/images/spinner.gif" />richiesta in corso...</span>
					
											</c:if>
											<%
											String isCatastoDettaglio=campiGestitoDettaglio.get("tipocatasto_id");
										    pageContext.setAttribute("isCatastoDettaglio", isCatastoDettaglio);
											%>
											<c:if test="${inite:contains(campiGestiti, 'tipocatasto_id')}">
												<span id="sitfx_${a.index}" style="${displaySitFunction}">
													<jsp:include page="../includes/funzioni_sit.jsp" >
														<jsp:param name="idCampo" value="tipocatasto_id_${a.index}" />
														<jsp:param name="isDettaglioCampo" value="${isCatastoDettaglio}" />
													</jsp:include>									
												</span>
											</c:if>
											<spring-form:errors path="entity.istanzemappalis[${a.index}].catasto" cssClass="error"/>
										</td>
										<td>
											<jsp:include page="../includes/campo_sit.jsp" >
										    	<jsp:param name="entityPath" value="entity.istanzemappalis[${a.index}].sezione" />
										        <jsp:param name="idCampo" value="sezione_id" />
										        <jsp:param name="size" value="10" />
										        <jsp:param name="resettaCivico" value="false" />
										        <jsp:param name="isMappali" value="true" />
										        <jsp:param name="isDettaglio" value="${isCatastoDettaglio}"/>
										        <jsp:param name="displaySitFunction" value="${displaySitFunction}" />
										        <jsp:param name="indiceMappali" value="_${a.index}" />
										    </jsp:include>	
										</td>
										<td>
											<%
											String isFoglioDettaglio=campiGestitoDettaglio.get("foglio_id");
									    	pageContext.setAttribute("isFoglioDettaglio", isFoglioDettaglio);
											%>
											<jsp:include page="../includes/campo_sit.jsp" >
										        <jsp:param name="entityPath" value="entity.istanzemappalis[${a.index}].foglio" />
										        <jsp:param name="idCampo" value="foglio_id" />
										        <jsp:param name="size" value="10" />
										        <jsp:param name="resettaCivico" value="false" />
										        <jsp:param name="isMappali" value="true" />
										        <jsp:param name="isDettaglio" value="${isFoglioDettaglio}"/>
										        <jsp:param name="displaySitFunction" value="${displaySitFunction}" />
										        <jsp:param name="indiceMappali" value="_${a.index}" />
										    </jsp:include>
										</td>
										<td>
										    <%
											String isParticellaDettaglio=campiGestitoDettaglio.get("particella_id");
											pageContext.setAttribute("isParticellaDettaglio", isParticellaDettaglio);
											%>
											<jsp:include page="../includes/campo_sit.jsp" >
												<jsp:param name="entityPath" value="entity.istanzemappalis[${a.index}].particella" />
											    <jsp:param name="idCampo" value="particella_id" />
											    <jsp:param name="size" value="10" />
											    <jsp:param name="resettaCivico" value="false" />
											    <jsp:param name="isMappali" value="true" />
											    <jsp:param name="isDettaglio" value="${isParticellaDettaglio}"/>
											    <jsp:param name="displaySitFunction" value="${displaySitFunction}" />
											    <jsp:param name="indiceMappali" value="_${a.index}" />
											</jsp:include>
										</td>
										<td>
											<%
											String isSubDettaglio=campiGestitoDettaglio.get("sub_id");
										    pageContext.setAttribute("isSubDettaglio", isSubDettaglio);
											%>
											<jsp:include page="../includes/campo_sit.jsp" >
												<jsp:param name="entityPath" value="entity.istanzemappalis[${a.index}].sub" />
											    <jsp:param name="idCampo" value="sub_id" />
											    <jsp:param name="size" value="10" />
											    <jsp:param name="resettaCivico" value="false" />
											    <jsp:param name="isMappali" value="true" />
											    <jsp:param name="isDettaglio" value="${isSubDettaglio}"/>
											    <jsp:param name="displaySitFunction" value="${displaySitFunction}" />
											    <jsp:param name="indiceMappali" value="_${a.index}" />
											</jsp:include>
											
											<c:if test="${inite:contains(oggettiDisabilitatiSetInRequest, 'fldUnitaImmobiliare')}">
												<spring-form:hidden id="unitaimmob_id_${a.index}" path="entity.istanzemappalis[${a.index}].unitaimmob" />
											</c:if>
										</td>
										<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldUnitaImmobiliare')}">
											<td>
												<%
												String isUIDettaglio=campiGestitoDettaglio.get("unitaimmob_id");
												pageContext.setAttribute("isUIDettaglio", isUIDettaglio);
												%>
												<jsp:include page="../includes/campo_sit.jsp" >
													<jsp:param name="entityPath" value="entity.istanzemappalis[${a.index}].unitaimmob" />
												    <jsp:param name="idCampo" value="unitaimmob_id" />
												    <jsp:param name="size" value="10" />
												    <jsp:param name="resettaCivico" value="false" />
												    <jsp:param name="isMappali" value="true" />
												    <jsp:param name="isDettaglio" value="${isUIDettaglio}"/>
												    <jsp:param name="displaySitFunction" value="${displaySitFunction}" />
												    <jsp:param name="indiceMappali" value="_${a.index}" />
												</jsp:include>
											</td>
										</c:if>
										<td>
											<c:if test="${istMappale.id.codice != null}">
												<a class="eliminaRiga" href="javascript:doSubmit('deleteMappale.htm?codice=${istMappale.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" title="<fmt:message key="label.elimina" /> ${istMappale.id.codice}">
													<label><fmt:message key="label.elimina.image" /></label>
												</a>
												<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq false}">	
													<a class="copiaRecord" href="javascript:doHref('copiaMappale.htm?codiceMappale=${istMappale.id.codice}&codiceIstanzaStrd=${istanzestradarioCommand.entity.id.codice}','')" title="<fmt:message key="label.replica"/>">
														<label><fmt:message key="label.replica" /></label>
													</a>
												</c:if>
												<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
													<script type="text/javascript">
												    	jQuery(document).ready(function(){
															ajaxSitLink('mappali','sitfx_${a.index}mappali_sit_id${a.index}','PuntoDaMappale');
												    	});
												    </script>
												    <span id="sitfx_${a.index}mappali_sit_id${a.index}"></span>										   
												</c:if>	
											</c:if>
										</td>
									</tr>
								</c:otherwise>
							</c:choose>
							<%i++;%>
							<c:set var="indice" value="${a.index}" scope="page"/>
						</c:forEach>
						<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq false}">				
							<c:if test="${istanzestradarioCommand.displayMode eq istanzestradarioCommand.displayConstants.EDIT}">
								<c:if test="${mappaleInserito == true}">
									<tr>
										<td colspan="${colspan}" align="left">
											<a class="addColumn"  style="float: none;"
												href="javascript:doHref('addMappale.htm?codice=${istanzestradarioCommand.entity.id.codice}','')" 
												title="<fmt:message key="label.nuovo" />"><label><fmt:message key="label.add.record.image" /></label></a>
										</td>
									</tr>
								</c:if>	
							</c:if>
						</c:if>
					</tbody>
				</table>
				<!-- Pannello che compare al click sul tasto copia localizzazione da istanze collegate collegati -->
				<!-- --------------------------------------------START------------------------------------- -->
				<div dojoType="dijit.Dialog" id="istanze_collegateDiv" title="<fmt:message key="label.istanze_collegate" />: ">
					<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 500px">
						<div id="istanze_collegate_tab"></div>
					</div>
				</div>
			</fieldset>
		</spring-form:form>
	</div>
	<script type="text/javascript">
	<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
		function showSitFX(obj, idx){
			$('daValidare').value = obj.value;				
			
			var nodeList = document.getElementsByTagName('span');
			for(var i=0;i<nodeList.length;i++){
				var currEl=nodeList[i];
				if (currEl.id.StringStartsWith('sitfx_')){
					if (currEl.id == 'sitfx_'+idx){
						currEl.style.display='';
					}else{
						currEl.style.display='none';
					}
				}
			}			
		}
	</c:if>
	
	
	function visualizzaAltreIstanze(divId){
		dijit.byId(divId).show();		
		visualizzaListaIstanze();		
	}

	function visualizzaListaIstanze(){
		var _ts=new Date().getTime();
		var isCivico = true;
		var isEsponente = true;
		var isColore = true;
		var isAccessoTipo = true;
		var isAccessoNumero = true;
		var isAccessoDescrizione = true; 
		var contentDiv = $('listaIstanzeAltriIndirizziDiv');
		idIstanzeStradario = '';
		isCivico = $('_civico').checked;
		isEsponente = $('_esponente').checked;
		isColore = $('_colore').checked;
		isAccessoTipo = $('_accessoTipo').checked;
		isAccessoNumero = $('_accessoNumero').checked;
		/* isAccessoDescrizione = $('_accessoDescrizione').checked; */
		contentDiv = $('listaIstanzeAltriIndirizziDiv');
		idIstanzeStradario = $('stradario_id_hidden').value;
		var civico = $('civico_id').value;
		var esponente = '';
		if($('esponente_id')){
		 	esponente=$('esponente_id').value;
		}
		var colore = '';
		if($('colore_id')){
			colore = $('colore_id').value;
		}
		var accessoTipo = $('accessotipo_id').value;
		var accessoNumero = $('accessonumero_id').value;
		var accessoDescrizione = $('accessodescrizione_id').value;
		
		var mostraLinkIstanze = false;	
		<c:if test="${istanzestradarioCommand.displayMode eq istanzestradarioCommand.displayConstants.EDIT}">
			mostraLinkIstanze  = true;
		</c:if>
		
		
		if(idIstanzeStradario != ''){		
				new Ajax.Request('<%=request.getContextPath()%>/ajax/listaAltreIstanzeStradario.htm', {
				  method: 'post',
				  parameters: {
					  codiceStradario: idIstanzeStradario,  
					  	isCivico:isCivico, 
					  	isEsponente:isEsponente, 
					  	isColore:isColore,
					  	civico: civico,
					  	esponente: esponente,
					  	isAccessoTipo : isAccessoTipo,
					  	isAccessoNumero : isAccessoNumero,
					  	isAccessoDescrizione : isAccessoDescrizione,
					  	accessoTipo : accessoTipo,
					  	accessoNumero : accessoNumero,
					  	accessoDescrizione : accessoDescrizione,
					  	
					  	colore: colore,
					  	_ts:_ts,
					  	codiceIstanza: ${istanzestradarioCommand.entity.istanza.id.codice},
			  			urlBack: escape('../istanzestradario/view.htm?codice=${istanzestradarioCommand.entity.id.codice}&software=${istanzestradarioCommand.entity.istanza.software.codice}'),
					  	showLinkIstanze: mostraLinkIstanze				  	
				  },
				  onSuccess: function(transport){
					  var response = transport.responseText;		
					  contentDiv.innerHTML = response;
					  applyStyle(); 							 
				    },
				  onFailure: function(transport){ 
					var response = transport.responseText;
				    alert(response); 
				    contentDiv.innerHTML=response;  
				  }						  
				  });
			}
	}


	// Salva la preferenza sulla configurazione dell'ultimo catasto scelto dall'utente
	function savePreference(obj)
	{
		var codiceCatasto=obj.value;
		saveUserPreference('<%=WebConstants.CONF_UTENTE_CATASTO_SCELTO%>',codiceCatasto);	
	}
	
	</script>
	<div id="functions">
		<ul>
			<c:if test="${istanzestradarioCommand.displayMode eq istanzestradarioCommand.displayConstants.NEW}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${istanzestradarioCommand.displayMode eq istanzestradarioCommand.displayConstants.EDIT}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
					<li><a href="javascript:confermaDuplicaIstanzastradario('message_dialog_confirm_duplica')"><fmt:message key="button.duplica" /></a></li>
			</c:if>
			<c:choose>
			   	<c:when test="${VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT_IN_REQUEST eq 'SIT_QUAESTIOFLORENZIA' }">			   	
				   	<c:if test="${empty istanzestradarioCommand.entity.idPuntoSit}">
						<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
					</c:if>
					<c:if test="${not empty istanzestradarioCommand.entity.idPuntoSit}">					
						<li class="buttondisabled"><a href="javascript:alert('<fmt:message key="javascript.alert.istanze_stradario_cancellazionestradario_geolocalizzato" />');"><fmt:message key="button.delete" /></a></li>
					</c:if>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				</c:otherwise>
			</c:choose>	
			</c:if>
			<li><a href="javascript:tabIstanzeCollegate('istanze_collegateDiv',${istanzestradarioCommand.entity.istanza.id.codice})";><fmt:message key="button.copia_localizzazione_ist_collegate" /></a></li>
			<li><a href="javascript:doHref('../istanze/view.htm?codice=${istanzestradarioCommand.entity.istanza.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
	<%-- Crea la finestra di dialogo che descrive all'operatore l'operazione che sta per fare e gli dà la possibilità di effettuarla o
		 annullarla
	--%>
	<script type="text/javascript">		
	
	
	function tabIstanzeCollegate(divId,idIstanza){
		
		dijit.byId(divId).show();
		IstanzeCollegateTab(idIstanza);
	}
	
	function IstanzeCollegateTab(idIstanza) {
		
		new Ajax.Request(
					'${pageContext.request.contextPath}/ajax/istanzeCollegate.htm?codice='+ idIstanza+'&contesto='+'+<%=WebConstants.CONTESTO_COPIA_DA_ISTAN_COLL_LOCALIZZAZIONE%>+',
					{
						method : 'post',
						onSuccess : function(transport) {							
							var response = transport.responseText;					
							$("istanze_collegate_tab").innerHTML =response;
							applyStyle();
						},
						onFailure : function(transport) {
							var response = transport.responseText;
							alert(response);
						}
					});
	}
	
	
	function confermaDuplicaIstanzastradario(divId){
			dijit.byId(divId).show();
	}
	function salvaInDocIstanza(codOggetto){	
		alert("Attenzione, l'allegato sarà salvato nei documenti dell'istanza");
		new Ajax.Request('${pageContext.request.contextPath}/documentiistanza/ajaxSaveOggettoInDocIstanza.htm', {
					method: 'post',	
					parameters: {codiceIstanza: ${codiceIstanza},codiceOggetto:codOggetto},
					onSuccess: function(transport){
					 alert(transport.responseText);
					 dijit.showTooltip(transport.responseText, dojo.byId(obj));
					 setTimeout(function(){dijit.hideTooltip(dojo.byId(obj))},1000);	
					},
					onFailure: function(transport){ 
					alert(transport.responseText);
					  $(id).innerHTML= transport.responseText;
					  $(id).className='error_checkbox';
					  $(id).style.display='';
					  $(id).pulsate();
					  $(id).fade();
					 }						    		 
			});
		
		/*
		if(jQuery('#html_export_id').html()!='')
		{
			var valore=jQuery('#html_export_id').html();
		}
		new Ajax.Request('${pageContext.request.contextPath}/documentiistanza/ajaxTrasformAndInsertVisuraInPdf.htm', {
					method: 'post',	
					parameters: {codice: ${codiceIstanza},value:valore},
					onSuccess: function(transport){
					 alert(transport.responseText);
					 dijit.showTooltip(transport.responseText, dojo.byId(obj));
					 setTimeout(function(){dijit.hideTooltip(dojo.byId(obj))},1000);	
					},
					onFailure: function(transport){ 
					alert(transport.responseText);
					  $(id).innerHTML= transport.responseText;
					  $(id).className='error_checkbox'
					  $(id).style.display='';
					  $(id).pulsate();
					  $(id).fade();
					 }						    		 
			});
		*/
		}
	
	
	    
	
	

	


	</script>
	<div style="display: none;" align="center" dojoType="dijit.Dialog" id="message_dialog_confirm_duplica" title="<fmt:message key="label.messaggio_conferma"/>">
 		<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width:200px;height: 120px">
			<div align="center">
				<div align="center"><fmt:message key="javascript.confirm.istanzestradario.duplica" /></div>
				<br class="clear" />
				<div style="float: none;" id="functions">
					<ul>
			    	<li><a href="javascript:doSubmit('duplicaStradario.htm?codice=${istanzestradarioCommand.entity.id.codice}','',document.inviodati);" ><fmt:message key="button.ok" /></a></li>
					<li><a href="javascript:void(0)" onClick="dijit.byId('message_dialog_confirm_duplica').hide()"><fmt:message key="button.annulla" /></a></li>
			   		</ul>
			   	</div>
				</div>				
		</div>
	</div>	
	
</body>
</html>