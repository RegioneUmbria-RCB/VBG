<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.web.AnagrafeCommand"%>
<%@page
	import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.operatori_esterni.carica_pratica.title" /></title>

<style type="text/css">

		.inputfile {
			width: 0.1px;
			height: 0.1px;
			opacity: 0;
			overflow: hidden;
			position: absolute;
			z-index: -1;
		}
		
		.inputfile + label {
			font-size: 1.25em;
			font-weight: 700;
		    color: white;
		    background-color: black;
		    display: inline-block;
		    margin-right: 0.30em;
		    /* padding-right: 0.30em; */
		    padding: 0.50em;
		}
		
		.inputfile + label {
			cursor: pointer;
		}
		
		.inputfile:focus + label, .inputfile + label:hover {
    		background-color: #b61218;
		}
		
		.inputfile:focus + label {
			outline: 1px dotted #000;
			outline: -webkit-focus-ring-color auto 5px;
		}
		
		.inputfile + label * {
			pointer-events: none;
		}
		
		.inputfile + label svg {
		 	width: 2em;
		 	height: 1em;
		    vertical-align: middle;
		    fill: currentColor;
		    margin-top: -0.25em;
		    /* 4px */
		    margin-right: 0.30em;
		    /* 4px */
		}
		
		/* .inputfile + label {
		
		 	width: 10em;
		    height: 2em;
		    /* vertical-align: middle;
		    fill: currentColor;
		    margin-top: -0.25em;
		    /* 4px */
		    /* margin-right: 0.25em;
		} */
		
		.inputfile-1 + label {
    		color: #f1e5e6;
		    background-color: #d3394c;
		}

		.inputfile-1:focus + label,
		.inputfile-1.has-focus + label,
		.inputfile-1 + label:hover {
		    background-color: #722040;
		}
		
		.disabled {
			/* pointer-events: none; */
		}
		
		/* .upload_title {
			padding: 0px 0px 45px 0px;
	    	font-size: 16px;
	    	font-weight: bold;
	    	color: #696969;
	    	display: inline;
		}
		
		#pageTitle {
			font-size: 24px;
		}
		
		.contenuto_cella {
			width: 250px;
		    display: inline-block;
		    float: right;
		    text-align: left;
		} 
		 */
		/* input:invalid {
		  border: 2px solid black;
		} */

</style>
</head>
<body>
	<span class="titoloPagina" id="pageTitle"> <init:editLabel key="label.operatori_esterni.carica_pratica.title" role="ROLE_EDITLABEL" />
	</span>
	<br/>
	<span><init:editLabel key="label.operatori_esterni.carica_pratica.descrizione" role="ROLE_EDITLABEL" /></span>

	<div id="subcontent">

		<spring-form:form commandName="operazioniEsterneCommand" id="inviodati_id" name="inviodati"
			enctype="multipart/form-data" method="post"
			action="${pageContext.request.contextPath}/operazioniesterne/caricaPraticaUpdate.htm">
			<br />
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="operazioniEsterneCommand" />
			</jsp:include>
			<table width="100%" style="border-collapse: separate;">
				
				<tr>
					<td style="width:155px;">
					
						<div class="upload_title">
							<label for="numero_protocollo"><fmt:message key="label.numero_protocollo" /></label>
						</div>
						
					</td>
					
					<td>
						<div class="contenuto_cella">
							<spring-form:input 
 								path="numero_protocollo" 
 								cssClass="inputRed" 
 								id="numero_protocollo" 
 								size="10"
								onchange="javascript:resetErrorField(this, 'numero_protocollo_error');"  />
							<span id="numero_protocollo_error" class="error"></span>
							
						</div>
					</td>
					
				</tr>
				<tr>
					<td style="width:155px;">
						<div class="upload_title">
							<label for="data_protocollo"><fmt:message key="label.data_protocollo" /></label>
						</div>
						
					</td>
					
					<td class="inline-ui-cell">
						<div class="contenuto_cella">
					
		
							<spring-form:input
								cssClass="inputRed" 
								id="data_protocollo_id" 
								path="data_protocollo" size="10"
								onblur="isValidDate(this,true);" 
								onchange="javascript:resetErrorField(this, 'data_protocollo_error');" /> 
							<init:calendar
								imagePath="/images/cal.gif" idImage="calData" idInput="data_protocollo_id"
								textKey="label.calendar" />
							<span id="data_protocollo_error" class="error"></span>
							<%-- <spring-form:errors path="data_protocollo" cssClass="error"/> --%> 
						</div>
						
					</td>
					
				</tr>
				
				<tr>
					
					<td style="width:155px;vertical-align: top;">
						<div class="upload_title"><span>File zip</span></div>
					</td>
					<td colspan="2" style="display:inline-block;">
						<div class="contenuto_cella">
							<input type="file" name="fileUploadFromExtApp" id="file"
								onchange="return validateZipFileExt(this, 'file_zip_error');"
								class="inputfile inputfile-1"
								data-multiple-caption="{count} files selected" multiple /> 
							
								<!-- Aggiungere al tag input con name="fileUploadFromExtApp" la classe css class="inputfile inputfile-1", e scommentare questa label -->

								<label for="file"> <svg xmlns="http://www.w3.org/2000/svg"
									width="20" height="17" viewBox="0 0 20 17"> <path
									d="M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 
						    		11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 
						    		2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 
						    		1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z" />
									</svg> <span><fmt:message key="label.operatori_esterni.carica_pratica.scegli_file" /></span>
								</label>  
							<span id="file_zip_error" class="error"></span>
							<br></br> <br></br>
							
						</div>
						
					</td>
				</tr>
				
			</table>
		</spring-form:form>
	</div>

	<div id="functions">
		<ul>
			<li><a href="javascript:caricaPratica();"><fmt:message key="button.operatori_esterni.invia" /></a></li>
		</ul>
	</div>

	<script type="text/javascript">
	
		
	
		function validateZipFileExt(zipFile, idZipFileError) {
			
	      if (!/(\.zip)$/i.test(zipFile.value)) {
	        alert("Formato file non valido inserire un file zip.");
	        zipFile.form.reset();
	        zipFile.focus();
	        return false;
	      }
	      
	      resetErrorField(zipFile, idZipFileError);
	      
	      return true;
		}
		
		function resetErrorField(inputField, idOfErrorFieldAssociated) {
			// debugger;
			if (inputField.value.length > 1 && document.getElementById(idOfErrorFieldAssociated).innerHTML !== '' ) {
				document.getElementById(idOfErrorFieldAssociated).innerHTML = "";
			}
			
		}
	    
		function caricaPratica() {
			
			var msg = '<fmt:message key="label.operatori_esterni.carica_pratica.javascript_conferma" />';
			
			// per test:
			//doSubmit('', msg, jQuery('#inviodati_id'));
			// debugger;
			console.log('oggetto ',document.getElementById('file').value);
			if (!checkIfInputFieldsAreEmpty()) {
				if (document.getElementById('file').value.toLowerCase().endsWith(".zip")) {
					doSubmit('', msg, jQuery('#inviodati_id'));
				}
			} 

		}
		
		function checkIfInputFieldsAreEmpty(){
			
			//debugger;
			var numeroProtocollo = document.getElementById('numero_protocollo').value;
			var dataProtocollo = document.getElementById('data_protocollo_id').value;
			var fileZipUpload = document.getElementById('file').files;
	
			var isEmpty = false;
			
			if (numeroProtocollo.length < 1) {
				document.getElementById('numero_protocollo_error').innerHTML = "Obbligatorio";
				isEmpty = true;
			} else {
				document.getElementById('numero_protocollo_error').innerHTML = "";
			}
			
			if (dataProtocollo.length < 1) {
				document.getElementById('data_protocollo_error').innerHTML = "Obbligatorio";
				isEmpty = true;
			} else {
				document.getElementById('data_protocollo_error').innerHTML = "";
			}
			
			if ( document.getElementById('file').files.length == 0) {
				document.getElementById('file_zip_error').innerHTML = "Obbligatorio";
				isEmpty = true;
			} else {
				document.getElementById('file_zip_error').innerHTML = "";
			}
			
			return isEmpty;
			
		}

		'use strict';
		;
		(function(document, window, index) {
			var inputs = document.querySelectorAll('.inputfile');
			Array.prototype.forEach.call(inputs,
				function(input) {
					var label = input.nextElementSibling, labelVal = label.innerHTML;
	
					input.addEventListener('change',
						function(e) {
							var fileName = '';
							if (this.files && this.files.length > 1) {
								fileName = (this.getAttribute('data-multiple-caption') || '')
									.replace('{count}', this.files.length);
							} else {
								fileName = e.target.value.split('\\').pop();
							}	

							if (fileName)
								label.querySelector('span').innerHTML = fileName;
							else
								label.innerHTML = labelVal;
						}
					);
	
					// Firefox bug fix
					input.addEventListener('focus', function() {
						input.classList.add('has-focus');
					});
					input.addEventListener('blur', function() {
						input.classList.remove('has-focus');
					});
				}
			);
		}(document, window, 0));
		
		/* jQuery(document).on('input', '#data_protocollo_id', function(){
			resetErrorField(document.getElementById('#data_protocollo_id'), document.getElementById('#data_protocollo_error'));
		}); */
		
	</script>
</body>
</html>