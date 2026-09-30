<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.ParseException"%>
<%@ page
	import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${graduatoriet.id.codice==null}">
		<fmt:message key="form.bandi.graduatoriet.title.create" />
	</c:if> <c:if test="${graduatoriet.id.codice!=null}">
		<fmt:message key="form.bandi.graduatoriet.title.view" />
	</c:if></title>
</head>
<body>
	<span class="titoloPagina"> <c:if
			test="${graduatoriet.id.codice==null}">
			<fmt:message key="form.bandi.graduatoriet.title.create" />
		</c:if> <c:if test="${graduatoriet.id.codice!=null}">
			<fmt:message key="form.bandi.graduatoriet.title.view" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../bandi/viewGraduatoria" />
		<jsp:param value="codice%3D${graduatoriet.id.codice}" name="qs" />
	</jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
			<div class="etichetta">
				<div>
					<fmt:message key="form.bandi.descrizione" />
					:
				</div>
			</div>
			<div class="parametro">
				<div>${graduatoriet.bandi.descrizione}</div>
			</div>
		</div>
		<br class="clear" />
		<spring-form:form commandName="graduatoriet" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="graduatoriet" />
			</jsp:include>
			<spring-form:hidden path="bandi.id.codice" />
			<spring-form:hidden path="id.codice" />

			<table>
				<tr>
					<td><fmt:message key="form.bandi.graduatoriet.descrizione" /></td>
					<td><spring-form:input id="descrizione_id" path="descrizione"
							size="70" /><label> <fmt:message
								key="label.selasciatovuoto" /> <fmt:message
								key="form.bandi.graduatoriet.tipigraduatoriet" /></label> <spring-form:errors
							path="descrizione" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message
							key="form.bandi.graduatoriet.tipigraduatoriet" /></td>
					<c:if test="${tipigraduatorietList == null }">
						<td>${graduatoriet.tipigraduatoriet.descrizione}</td>
						<spring-form:hidden path="tipigraduatoriet.id.codice" />
					</c:if>

					<c:if test="${tipigraduatorietList != null }">
						<td><spring-form:select path="tipigraduatoriet.id.codice">
								<spring-form:option value="">
									<fmt:message key="label.select.default" />
								</spring-form:option>
								<spring-form:options items="${tipigraduatorietList}"
									itemValue="id.codice" itemLabel="descrizione" />
							</spring-form:select> <spring-form:errors path="tipigraduatoriet" cssClass="error" />

						</td>
					</c:if>

				</tr>
				<tr>
					<td><fmt:message key="label.numero_determina_approvazione" /></td>
					<td><spring-form:input tabindex="6" id="descrizione_id"
							path="numeroDetermina" size="20" /> <spring-form:errors
							path="numeroDetermina" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="label.data_determina_approvazione" /></td>
					<td><spring-form:input tabindex="7" id="dataDetermina_id"
							path="dataDetermina" size="10" maxlength="10"
							onblur="isValidDate(this,true);" /> <init:calendar
							imagePath="/images/cal.gif" idImage="calDataDetermina"
							idInput="dataDetermina_id" textKey="label.calendar" /> <spring-form:errors
							path="dataDetermina" cssClass="error" /></td>
				</tr>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<%
		    String urlBack = "../bandi/viewGraduatoria.htm?codice="
							+ request.getParameter("codice");
					pageContext.setAttribute("URL_BACK", urlBack);
					pageContext.setAttribute("URL_STAMPA_LETTERE_TIPO",
							BackofficeNETConstants.getURL_STAMPA_LETTERE_TIPO());
					pageContext.setAttribute("URL_ISTANZE_DYN2_MODELLI",
							BackofficeNETConstants.getURL_ISTANZE_DYN2_MODELLI());

					String displayCriteri = "display:none";
		%>
		<script type="text/javascript">var urlback = '${URL_BACK}';</script>
		<c:set var="_URL_STAMPA_LETTERE_TIPO"
			value="${URL_STAMPA_LETTERE_TIPO}?idgraduatoria=${graduatoriet.id.codice}" />
		<c:set var="_URL_STAMPA_LETTERE_TIPO"
			value="${inite:geturlto(pageContext.request, _URL_STAMPA_LETTERE_TIPO, _urlback, null, true)}" />
		<ul>
			<c:if test="${graduatoriet.id.codice==null}">
				<li><a
					href="javascript:doSubmit('insertGraduatoria.htm','',document.inviodati)"><fmt:message
							key="button.insert" /></a></li>
			</c:if>
			<c:if test="${graduatoriet.id.codice!=null}">
				<li><a
					href="javascript:doSubmit('updateGraduatoria.htm','',document.inviodati)"><fmt:message
							key="button.update" /></a></li>
				<li><a
					href="javascript:doSubmit('deleteGraduatoria.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
							key="button.delete" /></a></li>
				<li><a href="javascript:void 0"
					onclick="window.open('${_URL_STAMPA_LETTERE_TIPO}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message
							key="button.print" /></a></li>
				<c:if test="${not empty graduatoriedCampi}">
					<li><a
						href="javascript:historySet('${_urlback}','../graduatorietcom/list.htm?codiceGraduatoria=${graduatoriet.id.codice}','')" />
					<fmt:message key="button.gestioni_comunicazioni" /></a></li>
				</c:if>
			</c:if>
			<li><a
				href="javascript:doHref('view.htm?codice=${graduatoriet.bandi.id.codice}','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
	<c:if test="${not empty graduatoriedCampi}">
		<br class="clear" />
		<div id="inserimentoMassivoDiv" style="display: none">

			<fieldset>
				<legend>
					<fmt:message key="form.istanze.movimentoDaInserire.legend" />
				</legend>
				<div>
					<table>
						<tr>
							<td><fmt:message key="form.istanze.movimentoDaInserire" /></td>
							<td><input type="text" id="tipoMovimento_id" name="tp"
								class="searchbox"
								onchange="checkValue(this,'tipoMovimento_hidden')"
								onkeydown="javascript:return searchAll(this,event)" size="60" />
								<init:autocompleter methodAjax="findTipiMovimento.htm"
									idHidden="tipoMovimento_hidden" idInput="tipoMovimento_id"
									inputTitleKey="label.ricerca_tipo_movimento" /> <input
								id="tipoMovimento_hidden" type="hidden" name="tpc" /></td>
						</tr>
						<c:if test="${not empty tipibandooutputList}">
							<tr>
								<td><fmt:message key="form.istanze.dagraduatoria.soggetto" /></td>
								<td><select id="soggettoMov_id" name="soggettoMovimento">

										<option value="<%=WebConstants.MERCATO_ASSEGNATARI%>">
											<fmt:message
												key="form.istanze.dagraduatoria.soggetto.assegnatari" />
										</option>
										<option value="<%=WebConstants.MERCATO_NONASSEGNATARI%>">
											<fmt:message
												key="form.istanze.dagraduatoria.soggetto.nonassegnatari" />
										</option>

										<option value="<%=WebConstants.MERCATO_TUTTI%>">
											<fmt:message key="form.istanze.dagraduatoria.soggetto.tutti" />
										</option>
								</select></td>
							</tr>
						</c:if>
						<c:if test="${empty tipibandooutputList}">
							<input id="soggettoMov_id" type="hidden" name="soggettoMovimento"
								value="<%=WebConstants.MERCATO_TUTTI%>" />
						</c:if>
					</table>
					<div id="functions">
						<ul>
							<li><a href="javascript:goToIstanzeListDaGraduatoria();"><fmt:message
										key="button.insertMovimenti" /></a></li>
						</ul>
					</div>
				</div>
			</fieldset>
		</div>
	</c:if>
	<%--   --%>
	<script type="text/javascript">

	function displayCriteri(){	
		var criteri=document.getElementById("criteri");
		    var size=document.getElementById("sizeCriteri").value;
		    var i;
		    var sizeInt=parseInt(size);
		    
		    for(i = 1; i <= sizeInt;i++){	
			    var id='criteriOrder' + i;
			   	var criteriOrder=document.getElementById(id);
		    	 
				if(criteri.checked){
					criteriOrder.style.display='';
				 } else{
					 criteriOrder.style.display='none';
				}
				if(criteri.checked){
					
				}
		    }
	
	}
</script>

	<c:if test="${graduatoriet.id.codice!=null}">
		<br />
		<br />
		<span class="titoloTabella"> <fmt:message
				key="form.bandi.graduatoriet.istanze.title.list" />
		</span>
	</c:if>
	<c:if test="${not empty graduatoriedCampi}">
		<div class="jmesa">
			<table border="0" width="70%" cellpadding="2" cellspacing="0"
				class="table">
				<thead>
					<tr class="header">
						<td><fmt:message key="form.graduatoried.posizione" /></td>
						<td width="20%"><fmt:message
								key="form.graduatoried.richiedente" /></td>
						<td><fmt:message key="label.istanza" /></td>
						<td title="<fmt:message key="label.scheda_dinamica" />"><fmt:message
								key="label.S" /></td>
								
						<td><fmt:message key="form.graduatoried.criteriordinamento" /><input
							type="checkbox" id="criteri" onclick="displayCriteri()"
							title="<fmt:message key="form.graduatoried.visualizzacriteri" />" /></td>
							<c:forEach items="${tipibandooutputList}" var="tipibandooutput"><td>${tipibandooutput.dyn2CampiOut.etichetta }</td></c:forEach>
						<c:if
							test="${mercatoIsPresente eq true && isRilasciaConcessionePerIstanza}">
							<td><fmt:message key="form.graduatoried.posteggio" /> | <fmt:message
									key="form.graduatoried.concessioni" /></td>
						</c:if>
						
						<c:if test="${isPresenticomunicazioni eq true }">
							<td><fmt:message key="label.azioni" /></td>			
						</c:if>
					</tr>
				</thead>
				<tbody class="tbody"><%
					    int i = 1;
					%><c:forEach var="graduatoriedCampi_var" items="${graduatoriedCampi}"
						varStatus="graduatoriedCampiStatus">
						<tr class="<%=(i % 2) == 0 ? "odd" : "even"%>">
							<td>${graduatoriedCampi_var.posizione}</td>
							<td><br />${graduatoriedCampi_var.istanza.transientDescrizioneRichiedenteQualitaAzienda}</td>
							<td><a 
								href="javascript:istanze('${graduatoriedCampi_var.istanza.id.codice }','${graduatoriedCampi_var.istanza.software }');">${graduatoriedCampi_var.istanza.numeroistanza
									}</a></td>
							<td><c:set var="_URL_ISTANZE_DYN2_MODELLI"
									value="${URL_ISTANZE_DYN2_MODELLI }?CodiceIstanza=${graduatoriedCampi_var.istanza.id.codice}" /><a
								href="${inite:linkschede(pageContext.request, URL_BACK, graduatoriedCampi_var.istanza.software, false, graduatoriedCampi_var.istanza.id.codice)}"
								title="<fmt:message key="label.scheda_dinamica" /> <fmt:message key="label.istanza" /> ${graduatoriedCampi_var.istanza.numeroistanza}"><fmt:message
										key="label.S" /></a></td>
							<td><div id="criteriOrder<%=i%>" class="jmesa"
									style="<%=displayCriteri%>">
									<table class="table"><thead class="header"><td><fmt:message key="form.campigraduatoria.etichetta" /></td><td><fmt:message key="form.campigraduatoria.valore" /></td></thead><%
											    int j = 0;
											%><c:forEach var="campi_var"
												items="${graduatoriedCampi_var.campigraduatorias}"
												varStatus="campiStatus">
												<tr class="<%=(j % 2) == 0 ? "odd" : "even"%>">
													<td>${campi_var.dyn2Campi.etichetta}</td>
													<td><c:choose><c:when test="${campi_var.dyn2Campi.tipodato == 'ui'}"><c:set var="campo_data" value="${campi_var.valore}" /><jsp:useBean id="campo_data" type="java.lang.String" /><%
																    DateFormat myDateFormat = new SimpleDateFormat(
																										"yyyyMMdd");
																								Date myDate = null;
																								try {

																									myDate = myDateFormat.parse(campo_data);

																								} catch (ParseException e) {
																									e.printStackTrace();
																								}
																								DateFormat myDateFormatOut = new SimpleDateFormat(
																										"dd/MM/yyyy");
																								String outdate = myDateFormatOut.format(myDate);
																								out.print(outdate);
																%></c:when><c:otherwise>${campi_var.valore}</c:otherwise></c:choose></td></tr><%
												    j++;
												%></c:forEach></table></div></td><c:forEach items="${graduatoriedCampi_var.bandoOutputList}" var="bandoOutput"><td>${bandoOutput.valore }</td></c:forEach>
							<c:if test="${mercatoIsPresente eq true}">
								<c:choose>
									<c:when test="${not empty graduatoriedCampi_var.concESub }">
										<td>
											<table width="100%" border="1"
												id="conc_table${graduatoriedCampi_var.istanza.id.codice}">
												<c:forEach items="${graduatoriedCampi_var.concESub }"
													var="conc"><tr>
														<td>${conc.codiceposteggio}</td>
														<td align="right"><a href="javascript:autorizzazioni('${graduatoriedCampi_var.istanza.id.codice}','${conc.id.codice }')" title="<fmt:message key="label.edit.record" />"> <c:if
																	test="${conc.flagAttiva ne true}">
																	<label	title="Cessata il <fmt:formatDate value='${conc.dataCessazione }' pattern='dd/MM/yyyy' /> ">[C]</label>
																</c:if><c:if test="${not empty conc.autoriznumeroSubentro}"><label title="Subentro">[S]</label>
																</c:if> ${conc.transientEstremiAut}, ${conc.mercatiUsoDescrizione}</a></td>
													</tr>
												</c:forEach>
											</table>
										</td>
									</c:when>
									<c:otherwise>
										<c:if test="${isRilasciaConcessionePerIstanza}">
											<td>
												<table width="100%" border="1" id="conc_table${graduatoriedCampi_var.istanza.id.codice}">
													<tr id="nuova_conc${graduatoriedCampi_var.istanza.id.codice}">
														<td><a href="javascript:createConcessione('${graduatoriedCampi_var.istanza.id.codice}')" title="<fmt:message key="button.nuovaconcessione" />"><fmt:message
																	key="button.nuovaconcessione" /></a></td>
													</tr>
												</table>
											</td>
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${isPresenticomunicazioni eq true }">
								<td><a class="azioni_istanza" data-codice="${graduatoriedCampi_var.id.codice}" href="#"><img src="<%=request.getContextPath()%>/images/verticalizzazioni.gif" title="<fmt:message key="label.altre_operazioni" />" class="elab_image" /></a></td>			
							</c:if>
						</tr><%
						    i++;
						%></c:forEach>
				</tbody>
			</table>

			<div id="functions">
				<c:if test="${isMostraBottoneRilasciaConc && !graduatoriet.tipigraduatoriet.flagEsprArtTemp}">
					<li><a href="javascript:assegnaConcessioni('assegnaConcessioniAlleIstanzeInGratuatoria.htm?codice=${graduatoriet.id.codice}');"><fmt:message key="button.rilascia_concessioni" /></a></li>
				</c:if>
				<c:if test="${isMostraBottoneRilasciaPos}">
					<li><a href="javascript:doHref('assegnaPosizioniPosteggiMassivamente.htm?codiceGraduatoria=${graduatoriet.id.codice}','<fmt:message key="alert.alert_posizioni" />');"><fmt:message key="button.assegna_posizioni" /></a></li>
				</c:if>
				<c:if test="${isMostraBottoneRilasciaConc && graduatoriet.tipigraduatoriet.flagEsprArtTemp}">
					<li><a href="javascript:historySet('${_urlback}','../bandi/createConcessioniTemporanee.htm?codiceGraduatoria=${graduatoriet.id.codice}','')" /><fmt:message key="button.rilascia_concessioni" /></a></li>
				</c:if>
				
				<c:if test="${graduatoriet.tipigraduatoriet.flagPianorotazione && !isPianorotazioneCreato}">
					<li><a href="javascript:historySet('${_urlback}','../bandi/pianorotazione.htm?codiceGraduatoria=${graduatoriet.id.codice}','')" /><fmt:message key="button.crea_piano_rotazione" /></a></li>
			    </c:if>
			    <c:if test="${graduatoriet.tipigraduatoriet.flagPianorotazione && isPianorotazioneCreato}">
					<li><a href="javascript:historySet('${_urlback}','../bandi/viewPianorotazione.htm?codiceGraduatoria=${graduatoriet.id.codice}','')" /><fmt:message key="button.piano_rotazione" /></a></li>
			    </c:if>
			</div>

			<input id="sizeCriteri" type="hidden" value="${sizeCriteri}" />

		</div>


		<div id="functions">
			<script type="text/javascript">
	
	function autorizzazioni(codiceIstanza,codiceAutorizzazione){
		historySet('${_urlback}', '../autorizzazioni/viewConcessione.htm?codiceIstanza='+codiceIstanza+'&codiceAutorizzazione='+codiceAutorizzazione, '');
	}
	
	function istanze(codiceIstanza, software){
		historySet('${_urlback}','../istanze/view.htm?codice='+codiceIstanza+'&software='+software);			
	}
	function assegnaConcessioni(url){
		
		var codiceComune = jQuery('#get_codice_comune').val();	
		doHref(url+'&codiceComune='+codiceComune);		
	}
	
		function goToIstanzeListDaGraduatoria(){
					if($('tipoMovimento_hidden').value == ''){
						alert('<fmt:message key="form.istanze.movimentoDaInserire.alert" />');
						$('tipoMovimento_id').focus();
						return;
					}
					var goToUrl = "../istanze/listDaGraduatoria.htm?codiceGraduatoria="+${graduatoriet.id.codice}+"&soggettoMovimento="+$('soggettoMov_id').value+"&codiceMovimento="+$('tipoMovimento_hidden').value;
					goToUrl = escape(goToUrl);
					doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'');
					
		}
		function createConcessione(codiceIstanza){		
			window.open("<%=request.getContextPath()%>/autorizzazioni/createConcessione.htm?codiceIstanza="
											+ codiceIstanza
											+ "&decorator=popup", 69,
									"status=1,menubar=0,scrollbars=1,width=800, height=600");
				}
		
		
		

		jQuery(function(){ 
			
			
    		jQuery( ".azioni_istanza" ).click(function() {
    			
    			apriDialogComunicazioni(jQuery(this).data("codice"));
	  		});
			
    	});

		function creaAllegatoPerGraduatoria(obj,codicelettera){
			
			if(confirm("Confermate la creazione dell\'allegato?")){
			var url = '${pageContext.request.contextPath}/bandi/ajaxcreaallegatocomunicazioni.htm?codicegraduatoriadcom=' + jQuery(obj).data("codice")+"&codicelettera="+codicelettera;
  			
  			var ajaxOpts = {
  					 
  					context: this,
  					type: 'POST',
  					success: function(data){
  						jQuery("#myDialogText").html(data);
  						jQuery("#myDialog").dialog({
  							 resizable: false,
  							 modal: true,
  							 width:'90%',
  							 title: 'Comunicazioni'
  							}
  						);
  						enableFunctions();
  					},
  					error: function(data){
  						jQuery("#myDialogErrText").html(data.responseText);
  						jQuery("#myDialogErr").dialog({
  							 resizable: false,
  							 modal: true,
  							 width:'90%',
  							 title: 'Errore'
  							}
  						);
  						enableFunctions();
  					}
  				};
  				disableFunctions();			
  				jQuery.ajax(url,ajaxOpts);  
			}
			
		}
		
		function apriDialogComunicazioni(codice){
			
			
			var url = '${pageContext.request.contextPath}/bandi/ajaxmostracomunicazioni.htm?codicegraduatoriad=' + codice;
  			
  			var ajaxOpts = {
  					 
  					context: this,
  					type: 'POST',
  					success: function(data){
  						jQuery("#myDialogText").html(data);
  						jQuery("#myDialog").dialog({
  							 resizable: false,
  							 modal: true,
  							 width:'90%',
  							 title: 'Comunicazioni'
  							}
  						);
  						enableFunctions();
  					},
  					error: function(data){
  						jQuery("#myDialogErrText").html(data.responseText);
  						jQuery("#myDialogErr").dialog({
  							 resizable: false,
  							 modal: true,
  							 width:'90%',
  							 title: 'Errore'
  							}
  						);
  						enableFunctions();
  					}
  				};
  			disableFunctions();  			    			
  			jQuery.ajax(url,ajaxOpts); 
			
		}
		
			</script>
		</div>
	</c:if>
	<c:if test="${empty graduatoriedCampi && graduatoriet.id.codice!=null}">
		<br />
		<label class="error"><fmt:message
				key="label.la_graduatoria_non_ha_prodotto_risultato" /></label>
	</c:if>
	
	<div id="myDialog"><div id="myDialogText"></div></div>
	<div id="myDialogErr"><div id="myDialogErrText"></div></div>
	
</body>
</html>
