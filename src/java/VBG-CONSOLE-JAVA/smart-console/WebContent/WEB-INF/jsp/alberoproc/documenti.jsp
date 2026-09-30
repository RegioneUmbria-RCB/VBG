<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${alberoprocDocumenti.id.codice==null}">
			<fmt:message key="alberoproc.label.nuovo_alberoprocDocumenti.title" />
		</c:if> 
		<c:if test="${alberoprocDocumenti.id.codice!=null}">
			<fmt:message key="alberoproc.label.dettaglio_alberoprocDocumenti.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${alberoprocDocumenti.id.codice==null}">
			<fmt:message key="alberoproc.label.nuovo_alberoprocDocumenti.title" />
		</c:if> 
		<c:if test="${alberoprocDocumenti.id.codice!=null}">
			<fmt:message key="alberoproc.label.dettaglio_alberoprocDocumenti.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<%
	String modellodomanda="display:none;";
	%>
	<div id="subcontent">
		<div class="parametriDiv">
	       		<div class="etichetta"> 
		        	<div>
		        		${alberoproc.vwAlberoproc.scDescrizionepadre}
		        	</div>
		        	<br />
		        	<div>
		        		${alberoproc.vwAlberoproc.scDescrizionebreve}
		        	</div>
		        </div>
	    </div>
	    <div class="clear"></div>
		<spring-form:form commandName="alberoprocDocumenti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocDocumenti" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:textarea id="descrizione_id" path="descrizione" cols="60" rows="5"/>
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td> 
				</tr>
				<tr>
					<td><fmt:message key="alberoproc.label.alberoprocDocumenti_oggetto" /></td>
					<td>
						<jsp:include page="../includes/oggetti.jsp" >
			       			<jsp:param name="idElemento" value="oggettoIdCodice" />
			   				<jsp:param name="codiceOggetto" value="${alberoprocDocumenti.oggetto.id.codice}" />
			   				<jsp:param name="idComuneOggetto" value="${alberoprocDocumenti.oggetto.id.idcomune}" />
			   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
			   				<jsp:param name="nomefileId" value="oggetto_nomefile" />
		    			</jsp:include>
		    			<spring-form:hidden path="oggetto.id.codice" id="oggetto_id_codice"/>
		    			<spring-form:hidden path="oggetto.nomefile" id="oggetto_nomefile"/>
		    			<spring-form:errors path="oggetto" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea id="note_id" path="note" cols="100" rows="5"/>
						<spring-form:errors path="note" cssClass="error"/>
					</td>
				</tr>
				
				<tr>
					<td><fmt:message key="label.note_frontend" /> <init:help idHelp="help_id_note_frontend" textKey="label.note_frontend.help"/></td>
					<td>
						<spring-form:textarea id="id_note_frontend" path="noteFrontend" rows="4" cols="100"></spring-form:textarea>
						<spring-form:errors path="noteFrontend" cssClass="error"/> 
						
					</td>
				</tr>	
				
				<tr>
					<td>
						<fmt:message key="label.ordine" />
					</td>
					<td>
						<spring-form:input path="ordine" maxlength="7" size="7"/>
						<spring-form:errors path="ordine" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocDocumenti_pubblica" />
					</td>
					<td>
						<spring-form:select id="pubblica_id" path="pubblica" onchange="viewModelloDomanda();">
							<spring-form:option value="0" ><fmt:message key='label.scPubblica_0' /></spring-form:option>
							<spring-form:option value="1"><fmt:message key='label.scPubblica_1' /></spring-form:option>
							<spring-form:option value="2" ><fmt:message key='label.scPubblica_2' /></spring-form:option>
							<spring-form:option value="3" ><fmt:message key='label.scPubblica_3' /></spring-form:option>
						</spring-form:select>
						<init:help idHelp="help_pubblica" textKey="alberoproc.help.alberoprocDocumenti_pubblica"/>
						<spring-form:errors path="pubblica" cssClass="error"/> 
				    </td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocDocumenti_richiesto" />
					</td>
					<td>
						<spring-form:checkbox path="richiesto" />
						<init:help idHelp="help_richiesto" textKey="alberoproc.help.alberoprocDocumenti_richiesto"/>
						<spring-form:errors path="richiesto" cssClass="error"/>
					</td>
				</tr>
				<tr id="flgDomandafo_id" style="<%=modellodomanda %>">
					<td>
						<fmt:message key="alberoproc.label.alberoprocDocumenti_flgDomandafo" />
					</td>
					<td>
						<spring-form:checkbox id="flgDomandafo_id" path="flgDomandafo" />
						<init:help idHelp="help_flgDomandafo" textKey="alberoproc.help.alberoprocDocumenti_flgDomandafo"/>
						<spring-form:errors path="flgDomandafo" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocDocumenti_foRichiedefirma" />
					</td>
					<td>
						<spring-form:checkbox path="foRichiedefirma" />
						<init:help idHelp="help_foRichiedefirma" textKey="alberoproc.help.alberoprocDocumenti_foRichiedefirma"/>
						<spring-form:errors path="foRichiedefirma" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocDocumenti_foTipodownload" />
					</td>
					<td>
						<spring-form:select path="tipoDownloads" multiple="true" size="5">
							<spring-form:options  items="${tipoDownloads}" itemLabel="descrizione" itemValue="codice" />
						</spring-form:select>
						<init:help idHelp="help_foTipodownload" textKey="alberoproc.help.alberoprocDocumenti_foTipodownload"/>
						<fmt:message key="label.select_multiplo" />
						<spring-form:errors path="foTipodownload" cssClass="error"/>
					</td>
				</tr>
				<c:if test="${not empty alberoprocDocumenticats  }">
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocDocumenti_alberoprocDocumenticat" />
					</td>
					<td>
						<spring-form:select path="alberoprocDocumenticat.id.codice" >
							<spring-form:option value=""></spring-form:option>
							<spring-form:options  items="${alberoprocDocumenticats}" itemLabel="descrizione" itemValue="id.codice" />
						</spring-form:select>
						<init:help idHelp="help_alberoprocDocumenticat" textKey="alberoproc.help.alberoprocDocumenti_alberoprocDocumenticat"/>
						<spring-form:errors path="alberoprocDocumenticat" cssClass="error"/>
					</td>
				</tr>
				</c:if>
			</table>
			<script type='text/javascript'>
				$('descrizione_id').focus();
				
				viewModelloDomanda();
				 
				function viewModelloDomanda(){
					if($('pubblica_id').value==1 || $('pubblica_id').value==2){
						$('flgDomandafo_id').appear();
					}else{
						$('flgDomandafo_id').fade();
					}
				}
				
				
				// Se il 
				// 1- codEndo == null allora creo la stringa di plugins senza l'opzione salva, non voglio permettere di salvare il singolo campo di testo in inserimento (evita errori di validazione)
				// 2- codEndo != null allora creo la stringa di plugins con l'opzione salva
				
				
				var plugins="safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable";
				
				// Chiamo la funzione che inizializza l'editor di testo
				jQuery(document).ready(function(){
					inizializzazioneEditor(plugins);
					}
				);
				/*
				Funzione che crea l'editor di testo
				*/
				function inizializzazioneEditor(plugins)
				{
					initTextEditors();
					/*
					tinyMCE.init({
							mode: "exact",   									
							elements: "id_note_frontend",
							// mode : "textareas",
							theme: "advanced",
							theme_advanced_toolbar_location: "top",
							theme_advanced_toolbar_align: "left",
							plugins: plugins,
							theme_advanced_buttons1: "save,newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,fullscreen,|,code",
						  	theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent",
						  	save_onsavecallback : "Editor_Save",
						  	forced_root_block : false,
					        force_br_newlines : true,
					        force_p_newlines : false,
					        theme_advanced_resizing : true
						});
					*/
				}
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${alberoprocDocumenti.id.codice==null}">
				<li><a href="javascript:doSubmit('insertDocumenti.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${alberoprocDocumenti.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateDocumenti.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('view.htm?codice=' + ${alberoproc.id.codice} + '#doc_anchor','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>