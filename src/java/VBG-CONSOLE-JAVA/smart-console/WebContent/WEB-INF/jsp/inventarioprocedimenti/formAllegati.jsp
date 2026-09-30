<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="java.util.Set"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.TipoDownload"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Allegati"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if
	test="${allegati.id.codice==null}">
	<fmt:message key="inventarioprocedimenti.label.nuovo_allegato.title" />
</c:if> <c:if test="${allegati.id.codice!=null}">
	<fmt:message
		key="inventarioprocedimenti.label.dettaglio_allegato.title" />
</c:if></title>
</head>
<body>

	<span class="titoloPagina"> <c:if
		test="${allegati.id.codice==null}">
		<fmt:message key="inventarioprocedimenti.label.nuovo_allegato.title" />
	</c:if> <c:if test="${allegati.id.codice!=null}">
	<fmt:message key="inventarioprocedimenti.label.dettaglio_allegato.title" />
	</c:if> 
	</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form" />
		</jsp:include>
			<div id="subcontent">
			<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${inventarioprocedimenti.entity.procedimento}" /></div>
			</div>
			</div>
			<br class="clear" />
			<spring-form:form commandName="allegati" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp">
					<jsp:param name="commandName" value="allegati" />
				</jsp:include>
				<table>
				
					<c:set var="isReadOnly" scope="page" value="false"></c:set>
					<c:if test="${ _VIEW_  eq true }">
						<c:set var="isReadOnly" scope="page" value="true"></c:set>
					</c:if>
				
				
				<c:choose>
					<c:when test="${_COMUNIASSOCIATI_ eq true }">
						<tr id="elementIdBeforeCombo">
							<td width="15%"></td>
							<td></td>
						</tr>						
						<jsp:include page="../includes/comboComuni.jsp">
							<jsp:param name="mostraTutti" value="true" />
							<jsp:param name="readOnly" value="${ isReadOnly }" />
							<jsp:param name="commandPropertyPath" value="comune" />
							<jsp:param name="colspan" value="2" />
							<jsp:param name="comune" value="${allegati.comune.codicecomune}" />
							<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
						</jsp:include>
					</c:when>
					<c:otherwise>
						<spring-form:hidden path="comune.codicecomune" />
					</c:otherwise>	
				</c:choose>
					<tr>
						<td>
							<fmt:message key="inventarioprocedimenti.label.allegato" />
						</td>
						<td>
							<spring-form:input id="allegato_id" path="allegato" size="60" /> 
							<spring-form:errors path="allegato" cssClass="error" />
						</td>
					</tr>
					<c:if test="${allegati.id.codice!=null  && allegati.costo!=null && allegati.costo>0}">
					<tr>
						<td>
							<fmt:message key="inventarioprocedimenti.label.costo" />
						</td>
						<td>
							<spring-form:input readonly="true" id="costo_id" path="costo" cssStyle="text-align:right;" size="10" onblur="checkNumberValue(this);" /> 
							<spring-form:errors path="costo" cssClass="error" />
						    <init:help idHelp="costo_help" textKey="label.help.costo"/>
						    
						</td>
					</tr>
					</c:if>
					<tr>
						<td><fmt:message key="inventarioprocedimenti.label.indirizzoweb" />
						</td>
						<td><spring-form:input id="indirizzoweb_id"
							path="indirizzoweb" size="60" /> <spring-form:errors
							path="indirizzoweb" cssClass="error" /></td>
					</tr>
					<tr>
						<td><fmt:message key="inventarioprocedimenti.label.flag_pubblica" /></td>
					 	<td>
							<spring-form:select  path="pubblica"> 
								<spring-form:option value=""><fmt:message key='label.seleziona'/></spring-form:option>
								<spring-form:option value="0" ><fmt:message key='label.scPubblica_0' /></spring-form:option>
								<spring-form:option value="1"><fmt:message key='label.scPubblica_1' /></spring-form:option>
								<spring-form:option value="2" ><fmt:message key='label.scPubblica_2' /></spring-form:option>
								<spring-form:option value="3" ><fmt:message key='label.scPubblica_3' /></spring-form:option>
							</spring-form:select>
							<spring-form:errors path="pubblica" cssClass="error" /> 
							<fmt:message key="inventarioprocedimenti.label.descrizione_flag_pubblica" />
						</td>
					</tr>
					<tr>
						<td><fmt:message
							key="inventarioprocedimenti.label.flag_richiesto" /></td>
						<td><spring-form:checkbox path="richiesto" /> 
						<spring-form:errors path="richiesto" cssClass="error" /> <fmt:message
							key="inventarioprocedimenti.label.descrizione_flag_richiesto" /></td>
					</tr>
					<tr>
						<td><fmt:message key="inventarioprocedimenti.label.ordine" /></td>
						<td><spring-form:input id="ordine_id" path="ordine"
							cssStyle="text-align:right;" size="8" /> 
							<spring-form:errors	path="ordine" cssClass="error" />
						</td>
					</tr>
					<tr>
						<td><fmt:message
							key="inventarioprocedimenti.label.flag_foRichiedefirma" /></td>
						<td><spring-form:checkbox path="foRichiedefirma" /> 
						<spring-form:errors
							path="foRichiedefirma" cssClass="error" /> <fmt:message
							key="inventarioprocedimenti.label.descrizione_foRichiedefirma" />
						</td>
					</tr>
					<tr>
					
						<td><fmt:message key="label.documento" /></td>
						<td><jsp:include page="../includes/oggetti.jsp">
							<jsp:param name="idElemento" value="oggettoIdCodice" />
							<jsp:param name="codiceOggetto" value="${allegati.oggetti.id.codice}" />
							<jsp:param name="idComuneOggetto" value="${allegati.oggetti.id.idcomune}" />
							<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
							<jsp:param name="nomefileId" value="oggetto_nomefile" />
							<jsp:param name="overrideExtensionsAllowed" value="pdf|rtf|doc|odt" />
						</jsp:include> 
						<spring-form:hidden path="oggetti.id.codice" id="oggetto_id_codice" />
						<spring-form:hidden path="oggetti.nomefile"	id="oggetto_nomefile" />
						
						</td>
					</tr>
					<tr>
						<td><fmt:message
							key="inventarioprocedimenti.label.foTipodownload" /></td>
						<td>	
						
						
							<select name="tipoDownloads" multiple="true" size="5">
								<c:forEach items="${tipoDownloads}" var="tdl">
									<%
									// System.out.println("----------------------------------------------");
										TipoDownload tdl_var = (TipoDownload)pageContext.getAttribute("tdl");
									// System.out.println("tdl_var: "+tdl_var);
									// System.out.println("tdl_var.codice: "+tdl_var.getCodice());
										Allegati a = (Allegati)request.getAttribute("allegati");
										Set<TipoDownload> tdlss = a.getTipoDownloads();
										String checked = "";
										for (TipoDownload td: tdlss){
										   // System.out.println("td.codice: "+tdl_var.getCodice());
										    if(td.getCodice().equals(tdl_var.getCodice())){
												checked = " selected=\"selected\" ";
												break;
										    }
																    
										}
										// System.out.println("selected: "+checked);
										// System.out.println("----------------------------------------------");
										pageContext.setAttribute("checked_var", checked);
									%>
									
										<option value="${tdl.codice}" ${checked_var}>${tdl.descrizione}</option>
									</c:forEach>
								</select>					
						
							
						<init:help idHelp="help_foTipodownload" textKey="inventarioprocedimenti.label.descrizione_tipo_download"/>
						<fmt:message key="label.select_multiplo" />
						<spring-form:errors path="foTipodownload" cssClass="error"/>						
						</td>
					</tr>					
					<tr>
						<td><fmt:message key="label.note_frontend" /> <init:help idHelp="help_id_note_frontend" textKey="label.note_frontend.help"/></td>
						<td>
							<spring-form:textarea id="id_note_frontend" path="noteFrontend" rows="4" cols="100"></spring-form:textarea>
							<spring-form:errors path="noteFrontend" cssClass="error"/> 							
							
						</td>
					</tr>	
				</table>
			</spring-form:form>
			</div>
			
			<script type="text/javascript">
			
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
			<div id="functions">
			<ul>

<%
			
		Allegati a  = (Allegati)request.getAttribute("allegati");

		if(ORMHelper.isConsoleLocale()){ %>
			
			<%
			if(a.getId().getIdcomune().equalsIgnoreCase(ORMHelper.getIdcomune())){
			%>
				
				<c:if test="${allegati.id.codice==null}">
					<li><a
						href="javascript:doSubmit('insertAllegati.htm','',document.inviodati)"><fmt:message
						key="button.insert" /></a></li>
				</c:if>
				<c:if test="${allegati.id.codice!=null}">
					<li><a
						href="javascript:doSubmit('updateAllegati.htm','',document.inviodati)"><fmt:message
						key="button.update" /></a></li>
					<li><a
						href="javascript:doSubmit('deleteAllegati.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
						key="button.delete" /></a></li>
				</c:if>
			<%} %>
			
		<%}else{ %>
		
			<%
			if(a.getId().getIdcomune().equalsIgnoreCase(ORMHelper.getIdcomune())){
			%>
				<c:if test="${allegati.id.codice==null}">
					<li><a
						href="javascript:doSubmit('insertAllegati.htm','',document.inviodati)"><fmt:message
						key="button.insert" /></a></li>
				</c:if>
				<c:if test="${allegati.id.codice!=null}">
					<li><a
						href="javascript:doSubmit('updateAllegati.htm','',document.inviodati)"><fmt:message
						key="button.update" /></a></li>
					<li><a href="javascript:doHref('../allegatidocsoggfirmatari/list.htm?codiceallegato=${allegati.id.codice}&idcomunerecord=${allegati.id.idcomune}','')"><fmt:message key="button.soggetti" /></a></li>						
					<li><a
						href="javascript:doSubmit('deleteAllegati.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
						key="button.delete" /></a></li>
				</c:if>			
							
			<%} %>
		<%} %>	
				
				
				
				<li><a
					href="javascript:doHref('listallegati.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}&codicecomune=${inventarioprocedimenti.entity.id.idcomune}','')"><fmt:message
					key="button.back" /></a></li>
			</ul>
			</div>
</body>
</html>