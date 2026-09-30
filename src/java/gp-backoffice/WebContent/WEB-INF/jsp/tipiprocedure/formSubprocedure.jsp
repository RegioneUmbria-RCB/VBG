<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${subprocedure.id.codice==null}">
			<fmt:message key="tipiprocedure.label.nuovo_subprocedure.title" />
		</c:if> 
		<c:if test="${subprocedure.id.codice!=null}">
			<fmt:message key="tipiprocedure.label.dettaglio_subprocedure.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${subprocedure.id.codice==null}">
			<fmt:message key="tipiprocedure.label.nuovo_subprocedure.title" />
		</c:if> 
		<c:if test="${subprocedure.id.codice!=null}">
			<fmt:message key="tipiprocedure.label.dettaglio_subprocedure.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<span class="parametri"><fmt:message key="tipiprocedure.label.codice_procedura"/><label> ${tipiprocedure.id.codice}</label></span>
	<span class="parametri"><fmt:message key="label.procedura"/><label> ${tipiprocedure.procedura}</label></span>		
		<spring-form:form commandName="subprocedure" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="subprocedure" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="tipiprocedure.label.titolosubprocedura" />
					</td>
					<td>
						<spring-form:input id="titolosubprocedura_id" path="titolosubprocedura" size="40" />
						<spring-form:errors path="titolosubprocedura" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.ordine" />
					</td>
					<td>
						<spring-form:input cssStyle="text-align:right;" id="numerosubprocedura_id" path="numerosubprocedura" size="4" />
						<spring-form:errors path="numerosubprocedura" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td width="25%" style="vertical-align: top;">
						<fmt:message key="tipiprocedure.label.subprocedura" />
					</td>
					<td>
						<spring-form:textarea id="subprocedura_id" path="subprocedura" rows="4" cols="72"></spring-form:textarea>
						<spring-form:errors path="subprocedura" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
					    <spring-form:textarea id="note_id" path="note" cols="50" rows="8" />
						<spring-form:errors path="note" cssClass="error"/>
					</td>
				</tr>
				
			</table>
			<script type='text/javascript'>
				$('subprocedura_id').focus();
				
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
					tinyMCE.init({
							mode: "exact",   									
							elements: "subprocedura_id",
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
				}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${subprocedure.id.codice==null}">
				<li><a href="javascript:doSubmit('insertSubprocedura.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${subprocedure.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateSubprocedura.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteSubprocedura.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listsubprocedure.htm?codicetipoprocedura=${tipiprocedure.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>