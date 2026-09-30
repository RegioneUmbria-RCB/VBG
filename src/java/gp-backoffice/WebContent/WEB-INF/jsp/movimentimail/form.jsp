<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="movimentimail.label.dettaglio_movimentimail.title" />
	</title>
    
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="movimentimail.label.dettaglio_movimentimail.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">

		<spring-form:form commandName="movimentimail" name="inviodati">
		<c:if test="${param.invio eq 'KO'}">
            <div id="invio_msg" class="error_header" >
            	<fmt:message key="error.inviomail"/>
            </div>
             <script type="text/javascript" >
		    	$('invio_msg').pulsate({ pulses: 2, duration: 1.0 });
		    </script>
            </c:if>
            <c:if test="${param.invio eq 'OK'}">
            <div id="invio_msg" class="success_header" >
            	<fmt:message key="label.mailinviata"/>
            </div>
             <script type="text/javascript" >
		    	$('invio_msg').pulsate({ pulses: 2, duration: 1.0 });
		    </script>
    		</c:if>
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="movimentimail" />
		    </jsp:include>		    
			<table>
			    <tr>
			    	<td><fmt:message key="label.account_mail_cfg" /></td>
			    	<td><b>${movimentimail.mailConfig.descrizione}</b></td>
			    </tr>
				<tr>
					<td class="mail_style">
						<fmt:message key="movimentimail.label.mittente" />
					</td>
					<td>
						<spring-form:input id="mittente_id" path="mittente" size="50" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="mail_style">
						<fmt:message key="movimentimail.label.destinatario" />
					</td>
					<td>
						<spring-form:input id="destinatario_id" path="destinatario"  size="100" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="mail_style">
						<fmt:message key="movimentimail.label.destinatariocc" />
					</td>
					<td>
						<spring-form:input id="destinatariocc_id" path="destinatariocc"  size="100" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="mail_style">
						<fmt:message key="movimentimail.label.destinatariobcc" />
					</td>
					<td>
						<spring-form:input id="destinatariobcc_id" path="destinatariobcc"  size="100" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="mail_style">
						<fmt:message key="movimentimail.label.oggetto" />
					</td>
					<td>
						<spring-form:input id="oggetto_id" path="oggetto"  size="100" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="mail_style">
						<fmt:message key="movimentimail.label.corpo" />
					</td>
					<td>
						<spring-form:textarea id="corpo_id" path="corpo" readonly="true" cols="100" rows="10"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('mittente_id').focus();
			</script>	
		</spring-form:form>
		<br/><br/><br/>
		<span class="titoloTabella">
		<fmt:message key="movimentimail.label.lista_movimentimailallegati.title" /></span>
		<form name="movimentimailallegatiForm" action="view.htm?codice=${movimentimail.id.codice }">
			<jmesa:springTableFacade
				id="movimentimailallegati_id" 
				items="${movimentimail.movimentimailallegatis}" 
				var="movimentimailallegati_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="documento" titleKey="movimentimailalleagati.label.documento.table" />
						<jmesa:htmlColumn property="oggetto.nomefile" titleKey="movimentimailalleagati.label.oggetto_nomefile.table" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">							
							<jsp:include page="../includes/visualizzaOggetto.jsp" >
	       						<jsp:param name="idElemento" value="mall${movimentimailallegati_var.id.codice }" />
	       						<jsp:param name="fileId" value="${movimentimailallegati_var.oggetto.id.codice}" />
	       						<jsp:param name="mostralabel" value="false"/>
								<jsp:param name="readonly" value="true"/>
	   						</jsp:include>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${movimentimail.id.codice }" name="codice"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='view.htm?codice=${movimentimail.id.codice }&';
			var _captionTab='<fmt:message key="movimentimail.label.lista_movimentimailallegati.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<c:choose>					
				<c:when test="${userlogged.flagCancelladocumentistc eq true}">				
					<li><a href="javascript:cancellaDocIstanzaConfirm();"><fmt:message key="button.delete" /></a></li>
					<div dojoType="dijit.Dialog" id="cancellaDocumentiIstanzeDialogDiv" title="<fmt:message key="label.conferma_cancellazione" />" style="display: none;">
							<input type="checkbox" id="cancellazionedocistanzachk_id" onclick="showHideDiv('doDeleteId')"/>
							<label for="cancellazionedocistanzachk_id">
								<fmt:message key="label.messaggio_cancellazione_mail_per_operatore">
									<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
									<fmt:param>${movimentimail.oggetto}</fmt:param>
								</fmt:message>
							</label>
							<div id="functions">
								<ul>
									<li style="display: none;" id="doDeleteId"><a href="javascript:doSubmit('delete.htm?codicemovimento=${codicemovimento}&codiceistanza=${codiceistanza}','',document.inviodati)"><fmt:message key="button.delete" /></a></li>
									<li><a href="javascript:void 0" onclick="dijit.byId('cancellaDocumentiIstanzeDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
								</ul>
							</div>
							<br class="clear" />										
					</div>
					
					<script type="text/javascript">
						function cancellaDocIstanzaConfirm(){
							dijit.byId('cancellaDocumentiIstanzeDialogDiv').show();
						}	
					</script>
					
						
				</c:when>
				<c:otherwise>
					<li class="buttondisabled"><a href="javascript:alert('<fmt:message key="javascript.alert.operatore_non_puo_cancellare_documento_stc" />');"><fmt:message key="form.oggetti.delete" /></a></li>
				</c:otherwise>
			</c:choose>			
			<li><a href="javascript:closePopup();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<script type="text/javascript">
	
		tinyMCE.init({
				mode: "exact",   			
			elements: "corpo_id", 
				theme: "advanced",
				theme_advanced_toolbar_location: "top",
				theme_advanced_toolbar_align: "left",
				plugins: "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
			  	theme_advanced_buttons1: "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
				theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
				theme_advanced_buttons3: "link,unlink,anchor,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,advhr",
				forced_root_block : false,
		        force_br_newlines : true,
		        force_p_newlines : false
		});
	
		function closePopup(){
			if (window.opener && !window.opener.closed) {
					window.close();
				}else{
					doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')
					}			
		}
	</script>
</body>
</html>