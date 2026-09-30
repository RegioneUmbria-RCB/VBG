<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI --%>
<c:set var="codiceIstanza" value="${param.codiceIstanza }"/>
<c:set var="_returnTo" value="${param.returnTo }"/>
<c:set var="codiceMovimento" value="${param.codiceMovimento }"/>
<c:set var="funzioneRichiesta" value="richiestaPraticaIstanza"/>
<c:set var="flagStc" value="${param.flagStc}"/>
<c:set var="inviatoConStc" value="${param.inviatoConStc}"/>
<c:set var="creatoDaStc" value="${param.creatoDaStc}"/>
<c:set var="idAttDest" value="${param.idAttDest}"/>
<c:set var="statoAttDest" value="${param.statoAttDest}"/>
<%-- 
funzioni possibili:
	richiestaPraticaIstanza: 
		visualizza la richiesta pratica a partire da una pratica creata da STC
	richiestaPraticaMovimento: 
		visualizza la richiesta pratica a partire da un movimento che ha creato pratiche o è stato creato tramite STC
		se il movimento è da notificare allora compare lo warning con title da notificare
		se il movimento è inviato allora compare l'icona dei computer out e link a collegamento pratica
		se il movimento è stato creato da STC allora compare l'icona dei computer in e link a collegamento pratica
		parametri obbligatori:
			codiceIstanza
			codiceMovimento
			funzioneRichiesta=richiestaPraticaMovimento
			flagStc 'true' o 'false' preso dal tipomovimento
			inviatoConStc  1 (Inviato) o 0 (non inviato) o 2 (disattivato d operatore) preso dal movimento
			creatoDaStc 'true' o 'false' preso dal movimento
 --%>
<c:if test="${not empty param.funzioneRichiesta}">
	<c:set var="funzioneRichiesta" value="${param.funzioneRichiesta}"/>
</c:if>
<%-- END RECUPERO PARAMETRI--%>
<%-- BEGIN SEZIONE FUNZIONI --%>
<c:choose>
<c:when test="${funzioneRichiesta eq 'richiestaPraticaIstanza' }">
	<span class="vbg-stack elab_image elab_image_38" onclick="javascript:historySet('${_returnTo }', '../stc/gotoPraticaCollegata.htm?software=<%= ORMHelper.getSoftware() %>&codiceIstanza=${codiceIstanza}', '');">
		<i class="vbg-stack-btn btn-stc"></i>
		<i class="vbg-stack-btn btn-stc-in" title="<fmt:message key="label.pratica_creata_da_stc"/>" ></i>
	</span>
</c:when>
<c:when test="${funzioneRichiesta eq 'richiestaPraticaMovimento' }">
	<c:if test="${flagStc eq true}">
		<c:if test="${inviatoConStc eq 1}">
			<a class="vbg-stack elab_image elab_image_38" href="javascript:historySet('${_returnTo }', '../stc/praticaCollegata.htm?software=<%= ORMHelper.getSoftware() %>&codiceIstanza=${codiceIstanza}&codiceMovimento=${codiceMovimento}&mittente=1', '');">
			<i class="vbg-stack-btn btn-stc"></i>						
				<c:if test="${not empty idAttDest}">
					<c:if test="${empty statoAttDest}">					
						<i class="vbg-stack-btn btn-stc-out-attesa" title="<fmt:message key="label.movimento_notificato_con_stc"/> (<fmt:message key="notifica_stc_in_attesa_di_consegna_al_destinatario"/>)" ></i>
					</c:if>
					<c:if test="${statoAttDest eq 'OK'}">
						<i class="vbg-stack-btn btn-stc-out-ok" title="<fmt:message key="label.movimento_notificato_con_stc"/>" ></i>
					</c:if>
					<c:if test="${statoAttDest eq 'KO'}">
						<i class="vbg-stack-btn btn-stc-out-ko" title="<fmt:message key="label.movimento_notificato_con_stc"/> (<fmt:message key="notifica_stc_errore_di_consegna_al_destinatario"/>)" ></i>	
					</c:if>
				</c:if>
				<c:if test="${empty idAttDest}">
					<i class="vbg-stack-btn btn-stc-out-ok" title="<fmt:message key="label.movimento_notificato_con_stc"/>" ></i>
				</c:if>
			</a>
		</c:if>				
		<c:if test="${inviatoConStc eq 0 or empty inviatoConStc }">
			<span class="elab_image vbg-btn btn-avvisi" title="<fmt:message key="label.movimento_da_notificare_con_stc"/>" 
			onclick="javascript:historySet('${_returnTo}','../movimenti/associaEnteDestinatario.htm?codiceMovimento=${codiceMovimento}', '');">		
			</span>
			<a class="elab_image vbg-btn btn-altreopzioni" id="imgAltreOperazioni${param.codiceMovimento }" 
			href="javascript:tabAltreOperazioni${param.codiceMovimento}('${param.codiceMovimento}_hidden','${param.codiceMovimento}');" title="<fmt:message key="label.altre_operazioni" />">
			</a>
			<%-- INIZIO APERTURA PANNELLO ALTRE OPERAZIONI --%>
		    <div dojoType="dijit.Dialog" id="altreOperazioniDialogDiv${param.codiceMovimento}_hidden" title="<fmt:message key="label.altre_operazioni" />: ">
				<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 400px; height: 200px;">
				<table>
					<tr><td colspan="2"><fmt:message key="label.seleziona_operazione_help" /><td></tr>
					<tr><td colspan="2">&nbsp;<td></tr>
					<tr>
						<td><fmt:message key="label.seleziona_operazione" /></td>
			        	<td>
			        		<select id="list${param.codiceMovimento}" name="list${param.codiceMovimento}"></select>
			        	</td>	
			        </tr>
        		</table>
        		<div id="functions" style="padding-top: 5em;">
					<ul>
						<li><a class="button${param.idElemento}" href="javascript:void 0"><fmt:message key="button.ok" /></a></li>
						<li><a href="javascript:void 0" onclick="closeTabAltreOperazioni('${param.codiceMovimento}_hidden');"><fmt:message key="button.annulla" /></a></li>
					</ul>
				</div>	
        	</div>
		  </div> 
		  <%-- END APERTURA PANNELLO ALTRE OPERAZIONI --%>
		 <script type="text/javascript">
		        var codiceAnagrafe ;
		        var myDialog;
				function tabAltreOperazioni${param.codiceMovimento}(id,codicemovimento){
					myDialog=dijit.byId("altreOperazioniDialogDiv"+id).show();
					getAltreOperazioniDisponibiliMovimentoSTC${param.codiceMovimento}();
				}
				
				function closeTabAltreOperazioni(id)
				{
					myDialog=dijit.byId("altreOperazioniDialogDiv"+id).hide();	
				}
				 
				jQuery(document).ready(function () {
					jQuery(".button${param.idElemento}").click(function(){
						if(jQuery("#list${param.codiceMovimento} option:selected").val() == 'Disattiva Notifica')
						{
							"inviatoConStc", "codicemovimento"
							historySet('${_urlback}','../movimenti/updateProperty.htm?codice=${param.codiceMovimento}&propertyToUpdate=inviatoConStc','');
						}
						
						
						if(jQuery("#list${param.codiceMovimento} option:selected").val() == 'Seleziona')
						{
							
						}
						
				    });
				});
			    
				function getAltreOperazioniDisponibiliMovimentoSTC${param.codiceMovimento}()
				{
					new Ajax.Request('${pageContext.request.contextPath}/json/getAltreOperazioniDisponibiliMovimentoSTC.htm', { 
					 		method:'post',
					 		//parameters:{chiave_ricerca : code}, 
			  				onSuccess: function(transport){
			  			 	var json = transport.responseText.evalJSON();
			  			    jQuery("#list${param.codiceMovimento} option").remove();
			  			 	for (var i = 0; i < json.item.length; i++) {
			  	                //options += '<option value="' + json.item[i] + '">' + json.item[i] + '</option>';
			  	                jQuery("#list${param.codiceMovimento}").append( // Append an object to the inside of the select box
					    			jQuery("<option></option>") // Yes you can do this.
					                .text(json.item[i])
					                .val(json.item[i])
			  	            );
			  	            }
			  			},
			    		 onFailure: function(transport){
			  				printResult(transport, "Errore durante il recupero dell'informazioni");
			    		}
					});
				}
							
		</script>					
		 <%-- END SEZIONE ICONA ALTRE OPERAZIONI --%>
		  		
		</c:if>
		<c:if test="${inviatoConStc eq 2}">
			<i class="vbg-btn btn-stc-da-notificare" title="<fmt:message key="label.movimento_da_notificare_con_stc_disattivato_da_operatore"/>"></i>
		</c:if>				
	</c:if>
	<c:if test="${creatoDaStc eq true}">
		<a class="vbg-stack elab_image elab_image_38"
		href="javascript:historySet('${_returnTo }', '../stc/praticaCollegata.htm?software=<%= ORMHelper.getSoftware() %>&codiceIstanza=${codiceIstanza}&codiceMovimento=${codiceMovimento}&mittente=0', '');" 
		title="<fmt:message key="label.movimento_creato_da_stc"/>">
			<i class="vbg-stack-btn btn-stc-in"></i>					
			<!-- <img src="${pageContext.request.contextPath}/images/retestc_in.gif" class="elab_image elab_image_38" />  -->
		</a>
	</c:if>
</c:when>
</c:choose>
<%-- END SEZIONE FUNZIONI --%>
