<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
		
	<!-- dialog per la visualizzazione di messaggi di errore -->
	<div dojoType="dijit.Dialog" id="errorDialogDiv" title="<fmt:message key="03" />: ">
		<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 400px;height: 200px; overflow: auto; " >
			<div id="errorDialogContentDiv" class="error_header" >
			</div>
		</div>
	</div>
	<!-- dialog per la visualizzazione di messaggi -->
	<div dojoType="dijit.Dialog" id="dialogDiv" title="<fmt:message key="02" />">
		<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 400px;height: 200px; overflow: auto; " >
			<div id="dialogContentDiv" >
			</div>
		</div>
	</div>
		
	<script type="text/javascript">
		/**
		 * funzione che visualizza un messaggio di errore
		 */
		function displayErrorMessage(errorMessage, dialogTitle){
			
			if(dialogTitle){
				jQuery('#errorDialogDiv').attr("title", dialogTitle);
			}
			else{
				jQuery('#errorDialogDiv').attr("title", "<fmt:message key="03" />: ");
			}
			jQuery('#errorDialogContentDiv').html("<span>" + errorMessage + "</span>");
			dijit.byId('errorDialogDiv').show();
		}
		
		/**
		 * funzione che visualizza un messaggio generico
		 */
		function displayMessage(message, dialogTitle){
			
			if(dialogTitle){
				jQuery('#dialogDiv').attr("title", dialogTitle);
			}
			else{
				jQuery('#dialogDiv').attr("title", "<fmt:message key="02" />");
			}
			jQuery('#dialogContentDiv').html("<span>" + message + "</span>");
			dijit.byId('dialogDiv').show();
		}
		
		/**
		 * funzione per la gestione di errori restituiti dalle chiamate ajax
		 */
		function mostraErroriCallback(jqXHR, textStatus, errorThrown){
			enableFunctions();
			var errorMessage = '<fmt:message key="03" /> ';
			if(errorThrown){
				errorMessage += errorThrown;
			}
			displayErrorMessage(errorMessage);
		}
		
	</script>
