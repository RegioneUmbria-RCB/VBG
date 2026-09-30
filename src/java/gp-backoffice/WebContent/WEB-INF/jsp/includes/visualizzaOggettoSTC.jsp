<%@page import="java.net.URLEncoder"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>

<!-- DESCRIZIONE PARAMETRI -->
<%--      
    codiceistanza		: codice dell'istanza
  	stcIddocumento		: valore stcIddocumento
	stcIdallegato		: valore stcIdallegato
	codiceRiferimento	: valore che indica il codice chiave del record,obbl. solo nel caso difunzionalità di backup
	contesto			: Contesto istaze_all,doc_istanze,mov_allegati,obbl. solo nel caso difunzionalità di backup 
	indice				: indice univoco ,obbl. solo nel caso difunzionalità di backup
	readonly			: se mostrare o meno le funzioni di modifica/inserimento dati (può essere nullo)	
	label				: una descrizione (Opzionale)
--%>
<%
String lcl_stcIddocumento = URLEncoder.encode(StringUtils.defaultString(request.getParameter("stcIddocumento"))) ;
String lcl_stcIdallegato= URLEncoder.encode(StringUtils.defaultString(request.getParameter("stcIdallegato")));
pageContext.setAttribute("lcl_stcIddocumento", lcl_stcIddocumento);
pageContext.setAttribute("lcl_stcIdallegato", lcl_stcIdallegato);
%>
<a class="visualizzaDocColumn" 
	target="_blank" href="../stc/visualizzaAllegato.htm?codiceIstanza=${param.codiceistanza}&codiceMovimento=${param.codicemovimento}&stcIddocumento=${lcl_stcIddocumento}&stcIdallegato=${lcl_stcIdallegato}"  
	title="<fmt:message key="label.visualizza_allegato"/>" >
   	<label><fmt:message key="label.visualizza.image" /></label>
</a>
<c:if test="${param.readonly ne true }">
	<a class="vbg-btn btn-salva"   href="javascript:confermaOperazioneBackupStc${param.indice}('message_dialog_confirm${param.indice}');" title="<fmt:message key="label.salva_allegato_locale"/>" >
	   	<label><fmt:message key="label.visualizza.image" /></label>
	</a>
			<%-- Crea la finestra di dialogo che avverte l'operatore che l'operazione che sta facendo potrebbe durare alcuni minuti  
			     In quanto è un operazione di backup	
			--%>
			<script type="text/javascript">

			function confermaOperazioneBackupStc${param.indice}(divId){
					dijit.byId(divId).show();
			}
			function preparaCopia${param.indice}(){
				
				glbAjaxHistorySet(URLDecode('${_urlback}'));
				window.vbg.mostraModalCaricamento();
				setTimeout('document.location.href = "../stc/prepareCopiaAllegatoInLocale.htm?codiceIstanza=${param.codiceistanza}&codiceMovimento=${param.codicemovimento}&codice=${param.codiceRiferimento}&contesto=${param.contesto}&stcIddocumento=${fn:replace(lcl_stcIddocumento, "'", "\\'")}&stcIdallegato=${fn:replace(lcl_stcIdallegato, "'", "\\'")}"',50);
			}
			
			</script>
			<div style="display: none;" align="center" dojoType="dijit.Dialog" id="message_dialog_confirm${param.indice}" title="<fmt:message key="label.messaggio_conferma"/>">
			 	<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width:400px;height:150px;">
					<div align="center">
						<div align="center"><fmt:message key="javascript.confirm.backup_allegati_stc" /></div>
						<br class="clear" />
						<div style="float: none;" id="functions">
							<ul>
							<li><a href="javascript:preparaCopia${param.indice}()" ><fmt:message key="button.ok" /></a></li>					    	
							<li><a href="javascript:void(0)" onClick="dijit.byId('message_dialog_confirm${param.indice}').hide()"><fmt:message key="button.annulla" /></a></li>
					   		</ul>
					   	</div>
					   	<br class="clear" />
					</div>		
				</div>
			</div>
</c:if>
${param.label}