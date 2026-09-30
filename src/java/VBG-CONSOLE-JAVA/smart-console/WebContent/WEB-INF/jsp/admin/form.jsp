<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.pannello_di_amministrazione" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.pannello_di_amministrazione" />	
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../admin/view" />
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="administration" />
	</jsp:include>
	<!-- §§§BEGIN§§§ -->
	<div id="reportAllineamentoAnagrafiche"></div>
	<c:if test="${inite:isEnterprise()}">
	<!-- Dichiarazione delle variabile scope:application che ci permette di capire se c'è già il messaggio di aggiornamento -->
	<c:if test="${not empty param.messaggio_aggiornamento }">
		<c:set var="MESSAGGIO_AGGIORNAMENTO_APPLICATIVO" scope="application">${param.messaggio_aggiornamento }</c:set>
	</c:if>	
	<c:if test="${not empty param.elimina_messaggio_aggiornamento }">
		<c:remove var="MESSAGGIO_AGGIORNAMENTO_APPLICATIVO" scope="application" />
	</c:if>
		<div id="functions">
		    <div class="titoloSezione">
					<fmt:message key="label.utility_generale" ></fmt:message>
			</div>
			<ul>
				<c:if test="${isCacheManager eq true}">
					<li><a href="javascript:doHref('../admin/clearCache.htm','')">Reset Cache</a></li>
				</c:if>
				<li><a href="javascript:doHref('../admin/clearLabelCache.htm','')">Reset Label Cache</a></li>
				<li><a href="javascript:doHref('../admin/resetClassCache.htm','')">Reset Cache delle classi</a></li>
				<li><a href="javascript:doHref('../admin/resetAllCache.htm','')">Reset All Cache</a></li>
				<li><a href="javascript:doHref('../admin/tail.htm','')">Logs</a></li>
				<li><a href="javascript:doHref('../admin/view.htm?viewDSInfo=true','')">Data Source Info</a></li>
				<li><a href="javascript:doHref('../admin/cancellazioneEndo.htm','')">Cancella Endo</a></li>
				<li><a href="javascript:doHref('../admin/cancellazioneAnagrafiche.htm','Confermate l\'operazione di cancellazione di tutte le schede anagrafiche?')">Cancella Anagrafe</a></li>
				<li><a href="javascript:doHref('../admin/resetApplicationParams.htm','')">Reload parametri</a></li>
				<li><a href="javascript:historySet('${_urlback}','../jobrepository/list.htm','')">Scheduler</a></li>
				<c:choose>
					<c:when test="${empty applicationScope.MESSAGGIO_AGGIORNAMENTO_APPLICATIVO}">
						<li><a href="javascript:createMessaggioAggiornamento('message_aggiornamento');">Messaggio aggiornamento</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="javascript:eliminaMessaggioAggiornamento();">Elimina messaggio aggiornamento</a></li>
					</c:otherwise>
				</c:choose>
				<li><a href="javascript:doHref('../admin/listSoftwareAttivi.htm','')">Gestione Software Attivi</a></li>
				<li><a href="javascript:void(0)" onclick="abilitaCancellazioneMaster()">Abilita cancellazione MASTER</a></li>
			</ul>
			<script type="text/javascript">
						function abilitaCancellazioneMaster(){
							jQuery.ajax({
								  url: '../admin/ajaxAbilitaCancellazioneMasterSuSlave.htm',
								  context: document.body,
								  cache: false,
								  data: "abilita=true",
								  dataType: "text",
								  success: function(data) {
										document.location.reload();
								  },
								  error: function(jqXHR, textStatus, errorThrown){
										console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
								}
							});
						}
		</script>
			<br />			
			<br/>	
			<div class="rigaSeparazioneContinua"></div>
			<ul>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		    </ul>
		</div>
	</c:if>
	
	<!-- §§§END§§§ -->
	<br class="clear" />
	<div id="subcontent">
		<c:if test="${not empty caches }">
			<fieldset><legend>Cache rimosse</legend>
			<ul>
				<c:forEach items="${caches}" var="cache">
					<li><b>${cache}</b></li>			
				</c:forEach>
			</ul>
			</fieldset>
		</c:if>
		<c:if test="${not empty labelCache }">
			<br />
			<label>Rimossa cache delle label!</label>
		</c:if>
		<c:if test="${not empty securityparams }">
			<fieldset><legend>Lista parametri ricaricati</legend>
			<ul>
				<c:forEach items="${securityparams}" var="sparam">
					<li><b>${sparam.key}</b>: ${sparam.value}</li>			
				</c:forEach>
			</ul>	
			</fieldset>
		</c:if>
		<c:if test="${not empty pooledDSList }">
			<fieldset><legend>Data Source Info</legend>
				<ul>
					<c:forEach items="${pooledDSList}" var="ds">
						<li><b>Data Source name</b>: ${ds.dataSourceName}</li>
						<li>
							<b>User</b>:
							<c:forEach items="${ds.allUsers}" var="au">
								${au}&nbsp;
							</c:forEach>
						</li>
						<ul>
							<li><b>Num. Connections</b>: ${ds.numConnectionsAllUsers}</li>
							<ul>
								<li><b>Num. Busy Connections</b>: ${ds.numBusyConnectionsAllUsers}</li>
								<li><b>Num. Idle Connections</b>: ${ds.numIdleConnectionsAllUsers}</li>
							</ul>
						</ul>	
						<ul>
							<li><b>ThreadPool size</b>: ${ds.threadPoolSize}</li>
							<ul>
								<li><b>Num. Active Threads in ThreadPool</b>: ${ds.threadPoolNumActiveThreads}</li>
								<li><b>Num. Idle Threads in ThreadPool</b>: ${ds.threadPoolNumIdleThreads}</li>
								<li><b>Num. Task Pending in ThreadPool</b>: ${ds.threadPoolNumTasksPending}</li>
							</ul>
						</ul>
						<br />		
					</c:forEach>
				</ul>	
			</fieldset>
		</c:if>	
		<c:if test="${not empty CLASS_CACHE_REMOVED }">
			<fieldset><legend>Cache degli oggetti nelle classi</legend>
			${CLASS_CACHE_REMOVED}
			</fieldset>
		</c:if>
		
		
		<c:if test="${not empty RISULTATO }">
			<fieldset><legend>Risultati</legend>
				${RISULTATO}
			</fieldset>
		</c:if>
		
	</div>
	
			
	
	<script type="text/javascript">
		var messaggioAggiornamento = function(){
			//var messaggio = prompt("Inserisci il messaggio","Attenzione! Alle ore hh:mm del giorno dd/MM/yyyy l'accesso al programma sarà disabilitato per manutenzione. Salvare il proprio lavoro.");
			var messaggio=document.getElementById('message_id').value;
			if(messaggio!=null && messaggio!=''){
				document.location.href='view.htm?messaggio_aggiornamento='+escape(messaggio);
			}
		};
		
		var eliminaMessaggioAggiornamento = function(){
			document.location.href='view.htm?elimina_messaggio_aggiornamento=true';		
		};
		
		function createMessaggioAggiornamento(divId){
				dijit.byId(divId).show();
		}

		function allineaPG(){			
			var jhqrPr = jQuery.ajax({
				  url: 'ajaxUpdateAllineaAnagrafichePG.htm',
				  context: document.body,
				  cache: false,					  
				  dataType: "html"
			});
			showReport();
			dijit.byId('allinea_anagrafe').hide();
		}
		function allineaPF(){
			var jhqrPr = jQuery.ajax({
				  url: 'ajaxUpdateAllineaAnagrafichePF.htm',
				  context: document.body,
				  cache: false,					  
				  dataType: "html"
			});
			showReport();
			dijit.byId('allinea_anagrafe').hide();
		}
		function allineaTutte(){
			var jhqrPr = jQuery.ajax({
				  url: 'ajaxUpdateAllineaAnagrafiche.htm',
				  context: document.body,
				  cache: false,					  
				  dataType: "html"
			});
			showReport();
			dijit.byId('allinea_anagrafe').hide();
		}
		function fermaAllinea(){
			doHref('stopAllineamentoAnagrafe.htm','');
		}
	
		function showReporTimer(){
			setTimeout("showReport()", 5000);
		}
		
		
		function ajaxAllineaAnagraficheInElaborazione(){
			
			
		}
		
		function showReport(){
			
			var jhqrPr = jQuery.ajax({
				  url: 'ajaxReportAllineaAnagrafiche.htm',
				  context: document.body,
				  cache: false,					  
				  dataType: "json",
				  success: function(data, textStatus, jqXHR){
					  if(data){					  
						  if(data.report.chiave!='99'){
						  	jQuery('#reportAllineamentoAnagrafiche').html(data.report.valore + "&nbsp;<img src='${pageContext.request.contextPath}/images/spinner.gif' />");
						  	
						  }else{
							  jQuery('#reportAllineamentoAnagrafiche').html(data.report.valore);
						  }
					  }					  
				  }
			});
			showReporTimer();
		}
				 
		function mostraFunzionianagrafe(){
			
			var jhqrPr = jQuery.ajax({
				  url: 'ajaxAllineaAnagraficheInElaborazione.htm',
				  context: document.body,
				  cache: false,					  
				  dataType: "json",
				  success: function(data, textStatus, jqXHR){
					  if(data){					  
						  if(data.allineaAnagraficheInCorso == 'true'){
						  	jQuery('#warning_allineamento_anagrafe').html("<span class=\"error_header\">Attenzione un'altra elaborazione è in corso... cliccare ferma esecuzione per sbloccarla</span>");
						  	jQuery( ".all_anag_denied" ).hide();
						  }
					  }					  
				  }
			});
			dijit.byId('allinea_anagrafe').show();
		}
		
	</script>
	
		<div style="display: none;" align="center" dojoType="dijit.Dialog" id="message_aggiornamento" title="Messaggio di aggiornamento">
	 	<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width:600px;height: 200px">
			<div align="center">
				<textarea style="font-size: small;" id="message_id" rows="5" cols="90"  >Attenzione! Alle ore hh:mm del giorno <%=Utilities.getToday(false) %> l'accesso al programma sarà disabilitato per manutenzione. Salvare il proprio lavoro.</textarea>
				<br class="clear" />
				<div style="float: none;" id="functions">
					<ul>
			    	<li><a href="javascript:messaggioAggiornamento();" ><fmt:message key="button.ok" /></a></li>
					<li><a href="javascript:void(0)" onClick="dijit.byId('message_aggiornamento').hide()"><fmt:message key="button.annulla" /></a></li>
			   		</ul>
			   	</div>
			</div>		
		</div>
		
		
</body>
</html>