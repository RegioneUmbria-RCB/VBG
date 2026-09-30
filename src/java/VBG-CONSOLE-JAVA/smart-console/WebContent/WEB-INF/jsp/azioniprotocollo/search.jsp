<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.azioni_protocollo" /></title>
</head>
<body>
	<span class="titoloPagina"> <fmt:message
			key="label.azioni_protocollo" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search" />
	</jsp:include>



	<div id="subcontent">
		<spring-form:form commandName="azioniProtocollazioneCommand"
			name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="azioniProtocollazioneCommand" />
			</jsp:include>
			<jsp:include page="../includes/history.jsp">
				<jsp:param name="path" value="../azioniprotocollo/search" />
			</jsp:include>
			<table width="350px">
				<tr>
					<td><fmt:message key="label.numero_protocollo" /> *</td>
					<td><spring-form:input id="numero_id" path="datiProtocollo.numeroProtocollo" size="7" cssStyle="text-align: right"/></td>
				</tr>
				<tr>
					<td><fmt:message key="label.anno" /> *</td>
					<td><spring-form:input id="anno_id" path="datiProtocollo.annoProtocollo" size="7" cssStyle="text-align: right"/></td>
				</tr>

				
			</table>
			
			<c:set var="mostraNascondi"> style="display: none;" </c:set>
			<c:if test="${fn:length(configurazioniVerticalizzazionis)>1}">				
				<c:set var="mostraNascondi"></c:set>
			</c:if>

					<fieldset ${mostraNascondi }>
					<legend><fmt:message key="label.scelta_configurazioni_protocollo" /></legend>
					<fmt:message key="label.scelta_configurazioni_protocollo.help" />
						<ul>
						<c:forEach items="${configurazioniVerticalizzazionis}" var="cfg">							
								<li id="confScelta_id${cfg.comune.codicecomune}-${cfg.software.codice}" class="configurazione_id">
									<a href="javascript:scegliConfigurazione('${cfg.comune.codicecomune}','${cfg.software.codice}')"><fmt:message key="label.comune" />: ${cfg.comune.comune}, <fmt:message key="label.modulo" />: ${cfg.software.descrizione}</a>
								</li>							
						</c:forEach>
						</ul>
					</fieldset>	
									<input type="hidden" id="codicecomune_id" name="comune.codicecomune" value="${cfg.comune.codicecomune}" />
									<input type="hidden" id="softwarecodice_id" name="protSoftware.codice" value="${cfg.software.codice}" />
								
		</spring-form:form>
	</div>
	<script type="text/javascript">
	
	
	jQuery(document).ready(function() {
		jQuery('.configurazione_id').each(function(i, obj) {
		    if(i == 0){
		    	var str = obj.id.replace('confScelta_id','');
		    	var arr = str.split('-');
		    	var codiceComune = arr[0];
		    	var software = arr[1];
		    	scegliConfigurazione(codiceComune,software);
		    	return true;
		    }
		});
	});
	         
	
	function scegliConfigurazione(codcomune, psoftware){
		jQuery('#codicecomune_id').val(codcomune);
		jQuery('#softwarecodice_id').val(psoftware);
		jQuery('.configurazione_id').css("background-color", "#ffffff");
		jQuery('#confScelta_id'+codcomune+'-'+psoftware).css('background-color','#E5FACA');
	}
	
		function ajaxHistorySet(url) {

			var jhqr = jQuery.ajax({
				url : '../history/ajaxSet.htm?ReturnTo=' + url,
				context : document.body,
				cache : false,
				dataType : "html",
				success : function(data) {
				}
			});

		}

		function leggiProtocollo() {
			if (jQuery('#numero_id').val() == '') {
				alert('<fmt:message key="label.numero_protocollo" /> <fmt:message key="alert.required" />');
				return;
			}
			if (jQuery('#anno_id').val() == '') {
				alert('<fmt:message key="label.anno" /> <fmt:message key="alert.required" />');
				return;
			}
			ajaxHistorySet('${_urlback}');
			doSubmit('view.htm', '', document.inviodati);
		}
	</script>

	<div id="functions">
		<ul>
			<li><a href="javascript:leggiProtocollo();"><fmt:message
						key="button.ok" /></a></li>
			<li><a href="javascript:historyBack()"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>