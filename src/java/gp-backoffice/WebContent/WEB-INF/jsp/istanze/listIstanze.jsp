<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.net.URLEncoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="label.archivio_istanze" /></title>
		<script type="text/javascript">
			vbg.ready(() => {
				let buttonMostraInMappa = document.querySelector('.mostra-mappa');
				buttonMostraInMappa.addEventListener('click',async (e) => {
					e.preventDefault();
					window.vbg.mostraModalCaricamento();
					try
					{
						let urlMappa = await recuperaUrlMappa();
						location.replace(urlMappa);
					}
					catch(error) {
						console.log(error);
						alert('Si sono verificati errori durante l\'apertura della mappa: ' + error);
					}
					window.vbg.nascondiModalCaricamento();
				});
			});
			
			async function recuperaUrlMappa(){

				let idIstanzeStradario = '${istanzestradarioCommand.entity.id.codice}';
				
				const postParams = { request : [ idIstanzeStradario ] };
							
				const response = await fetch('../istanzestradariocartografico/jsonMostraIstanzeInMappa.htm', {
	        		method: 'POST',
	                headers: {
	                    'Accept': 'application/json',
	                    'Content-Type': 'application/json',
	                },
	                body: JSON.stringify(postParams)
	        	});
								
				const jsResponse = await response.json();
				
				if(jsResponse.esito.esito == "KO"){
					throw new Error(jsResponse.esito.exceptions.join(' - '));
				}
				
				return jsResponse.url;
			}
		</script>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.archivio_istanze" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
		<a class="sezioneDatiPiu" 
			id="id_link_preferenze" 
			href="javascript:showHidePanelBase('preferenzeColonne_id', 'id_link_preferenze', '', '${pageContext.request.contextPath}/images/','div',false);"	
			title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.modifica_preferenze_lista_istanze"/>">
			<label for="id_link_preferenze"><fmt:message key="label.modifica_preferenze_lista_istanze"/></label>
		</a>
		<div id="preferenzeColonne_id" style="display:none;">
			<fieldset>
			<div><fmt:message key="label.spiegazione_salvataggio_preferenze_lista_istanze" /></div>	
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISAUTORIZZAZIONI_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISAUTORIZZAZIONI%>',this)" ${CONF_UTENTE_LISTISTANZA_VISAUTORIZZAZIONI_CHECKED}  />
				<label for="CONF_UTENTE_LISTISTANZA_VISAUTORIZZAZIONI_id"><fmt:message key="label.autorizzazioni" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISCODPRATEL_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISCODPRATEL%>',this)" ${CONF_UTENTE_LISTISTANZA_VISCODPRATEL_CHECKED}  />
				<label for="CONF_UTENTE_LISTISTANZA_VISCODPRATEL_id"><fmt:message key="label.codice_pratica_telematica" /></label>
			</div>	
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISLINKDOCISTANZA_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISLINKDOCISTANZA%>',this)" ${CONF_UTENTE_LISTISTANZA_VISLINKDOCISTANZA_CHECKED}  />
				<label for="CONF_UTENTE_LISTISTANZA_VISLINKDOCISTANZA_id"><fmt:message key="label.documenti_istanza" /></label>
			</div>	
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISNUMOPERATOREINCARICO_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISNUMOPERATOREINCARICO%>',this)" ${CONF_UTENTE_LISTISTANZA_VISNUMOPERATOREINCARICO_CHECKED}/>
				<label for="CONF_UTENTE_LISTISTANZA_VISNUMOPERATOREINCARICO_id"><fmt:message key="label.operatore_in_carico" /></label>
			</div>			
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISNUMPROTOCOLLO_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISNUMPROTOCOLLO%>',this)" ${CONF_UTENTE_LISTISTANZA_VISNUMPROTOCOLLO_CHECKED}/>
				<label for="CONF_UTENTE_LISTISTANZA_VISNUMPROTOCOLLO_id"><fmt:message key="label.numero_protocollo" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISDATAPROTOCOLLO_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISDATAPROTOCOLLO%>',this)" ${CONF_UTENTE_LISTISTANZA_VISDATAPROTOCOLLO_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISDATAPROTOCOLLO_id"><fmt:message key="label.data_protocollo" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISTIPOLOGIAISTANZA_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISTIPOLOGIAISTANZA%>',this)" ${CONF_UTENTE_LISTISTANZA_VISTIPOLOGIAISTANZA_CHECKED}  />
				<label for="CONF_UTENTE_LISTISTANZA_VISTIPOLOGIAISTANZA_id"><fmt:message key="label.tipologia_istanza" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISRICHIEDENTE_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISRICHIEDENTE%>',this)" ${CONF_UTENTE_LISTISTANZA_VISRICHIEDENTE_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISRICHIEDENTE_id"><fmt:message key="label.richiedente" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISRICHIEDENTESTORICO_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISRICHIEDENTESTORICO%>',this)" ${CONF_UTENTE_LISTISTANZA_VISRICHIEDENTESTORICO_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISRICHIEDENTESTORICO_id"><fmt:message key="label.richiedente_storico" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISTECNICO_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISTECNICO%>',this)" ${CONF_UTENTE_LISTISTANZA_VISTECNICO_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISTECNICO_id"><fmt:message key="label.tecnico" /></label>
			</div>						
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISOPERATORE_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISOPERATORE%>',this)" ${CONF_UTENTE_LISTISTANZA_VISOPERATORE_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISOPERATORE_id"><fmt:message key="label.operatore" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISRESPPROC_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISRESPPROC%>',this)" ${CONF_UTENTE_LISTISTANZA_VISRESPPROC_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISRESPPROC_id"><fmt:message key="label.responsabile_procedimento" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISISTRUTTORE_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISISTRUTTORE%>',this)" ${CONF_UTENTE_LISTISTANZA_VISISTRUTTORE_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISISTRUTTORE_id"><fmt:message key="label.responsabile_istruttoria" /></label>
			</div>	
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISISTRUTTORE_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISDENOMINAZIONEATTIVITA%>',this)" ${CONF_UTENTE_LISTISTANZA_VISDENOMINAZIONEATTIVITA_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISDENOMINAZIONEATTIVITA_id"><fmt:message key="label.denominazione_attivita" /></label>
			</div>				
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISLOCALIZZAZIONE_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISLOCALIZZAZIONE%>',this)" ${CONF_UTENTE_LISTISTANZA_VISLOCALIZZAZIONE_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISLOCALIZZAZIONE_id"><fmt:message key="label.localizzazione" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISINDIRIZZIISTANZA_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISINDIRIZZIISTANZA%>',this)" ${CONF_UTENTE_LISTISTANZA_VISINDIRIZZIISTANZA_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISINDIRIZZIISTANZA_id"><fmt:message key="label.altri_indirizzi" /> [<fmt:message key="label.I" />]</label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISENDOPROCEDIMENTI_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISENDOPROCEDIMENTI%>',this)" ${CONF_UTENTE_LISTISTANZA_VISENDOPROCEDIMENTI_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISENDOPROCEDIMENTI_id"><fmt:message key="label.endoprocedimenti" /> [<fmt:message key="label.P" />]</label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISINTERVENTO_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISINTERVENTO%>',this)" ${CONF_UTENTE_LISTISTANZA_VISINTERVENTO_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISINTERVENTO_id"><fmt:message key="label.alberoproc" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISPROCEDURA_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISPROCEDURA%>',this)" ${CONF_UTENTE_LISTISTANZA_VISPROCEDURA_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISPROCEDURA_id"><fmt:message key="label.procedura" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISLAVORI_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISLAVORI%>',this)" ${CONF_UTENTE_LISTISTANZA_VISLAVORI_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISLAVORI_id"><fmt:message key="label.lavori" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_LAVORIESTESI_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISLAVORIESTESI%>',this)" ${CONF_UTENTE_LISTISTANZA_VISLAVORIESTESI_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISLAVORIESTESI_id"><fmt:message key="label.note" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISARCHIVIO_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISARCHIVIO%>',this)" ${CONF_UTENTE_LISTISTANZA_VISARCHIVIO_CHECKED}  />
				<label for="CONF_UTENTE_LISTISTANZA_VISARCHIVIO_id"><fmt:message key="label.archivio_pratiche" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISPOSIZIONEARCHIVIO_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISPOSIZIONEARCHIVIO%>',this)" ${CONF_UTENTE_LISTISTANZA_VISPOSIZIONEARCHIVIO_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISPOSIZIONEARCHIVIO_id"><fmt:message key="label.posizione_in_archivio" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISSORTEGGIATE_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISSORTEGGIATE%>',this)" ${CONF_UTENTE_LISTISTANZA_VISSORTEGGIATE_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISSORTEGGIATE_id"><fmt:message key="label.istanze_sorteggiate" /> [<fmt:message key="label.S" />]</label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISSTATOISTANZA_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISSTATOISTANZA%>',this)" ${CONF_UTENTE_LISTISTANZA_VISSTATOISTANZA_CHECKED}  />
				<label for="CONF_UTENTE_LISTISTANZA_VISSTATOISTANZA_id"><fmt:message key="label.stato_istanza" /></label>
			</div>
			<div>
				<input type="checkbox" id="CONF_UTENTE_LISTISTANZA_VISCOMUNE_id" 
				onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTISTANZA_VISCOMUNE%>',this)" ${CONF_UTENTE_LISTISTANZA_VISCOMUNE_CHECKED} />
				<label for="CONF_UTENTE_LISTISTANZA_VISCOMUNE_id"><fmt:message key="label.comune" /> </label>
			</div>			
			<script type="text/javascript">
				function salvaPreferenza(nomeparametro, objchk){
					var valore = "0";	
					if(objchk.checked==true){
						valore="1";	
					}
					saveUserPreference(nomeparametro, valore);
					dijit.showTooltip('<div id="status_msg" class="success_header"><fmt:message key="02" /></div>', dojo.byId(objchk.id));
					setTimeout(function(){dijit.hideTooltip(dojo.byId(objchk.id))},1500);
				}	
			</script>
			<div id="functions">
				<ul>
					<li><a href="javascript:document.location.reload();"><fmt:message key="button.ricarica_pagina" /></a></li>
				</ul>
			</div>
			&nbsp;
			</fieldset>
		</div>
		<c:if test="${istanzeCommand.istanzeFilter.ricercaVeloce eq true }">
			<p>
				<div class="warning_header"><fmt:message key="label.ricerca_veloce_istanze.message" /></div>
			</p>
		</c:if>
		<jsp:include page="../includes/history.jsp">
		    <jsp:param name="path" value="../istanze/listIstanze" />
		</jsp:include>
			<form name="inviodati" action="listIstanze.htm">
				${htmltable}
			</form>
			<script type="text/javascript">
				var _jmesaUrl='listIstanze.htm?';
				var _captionTab='<fmt:message key="label.archivio_istanze" />';
			</script>
		</div>
		<div class="form-button">
		    <a class="btn btn-primary" href="javascript:historySet('${_urlback}', '../istanze/create.htm', '')"><fmt:message key="button.new" /></a>
			<c:if test="${cartograficoAttivo eq true }">
			<a class="btn btn-primary mostra-mappa" href="javascript: void 0;"><fmt:message key="button.mostra_in_mappa" /></a>
			</c:if>
			<a class="btn btn-primary" href="javascript:doHref('createComunicazioneMa.htm','')"><fmt:message key="button.new_comunicazione" /></a>
	        <a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	    </div>
	</body>
</html>