<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<fmt:message key="form.foarjsteps.title" />
</title>
</head>
<body>
	<span class="titoloPagina"> <fmt:message key="form.foarjsteps.title" /> </span>

	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../foArjSteps/view" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="foArjSteps" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="foArjSteps" />
			</jsp:include>
			<table>
				<tr>
					<td><fmt:message key="label.step_base" /></td>
					<td><c:if test="${foArjSteps.id.codice==null}">
							<spring-form:select id="foArjStepsBase_id"
								path="foArjStepsBase.nomeStep" onchange="impostaValori(this);">
								<spring-form:option value="">
									<fmt:message key="label.select.default" />
								</spring-form:option>
								<spring-form:options items="${arjStepsBases}"
									itemValue="nomeStep" itemLabel="descrizioneEstesa" />
							</spring-form:select>							
						</c:if> 
						<c:if test="${foArjSteps.id.codice!=null}">
							<spring-form:hidden path="foArjStepsBase.nomeStep" />
						</c:if>
						
						<spring-form:errors path="foArjStepsBase" cssClass="error" />
						</td>

				</tr>
				<tr>
					<td><fmt:message key="label.titolo" /></td>
					<td><spring-form:input id="titolo_id" path="titolo" size="70" />
						<spring-form:errors path="titolo" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="label.descrizione" /></td>
					<td><spring-form:textarea id="descrizione_id"
							path="descrizione" cols="70" rows="10" /> <spring-form:errors
							path="descrizione" cssClass="error" /></td>
				</tr>

				<tr>
					<td><fmt:message key="label.ordine" /></td>
					<td><spring-form:input cssStyle="text-align: right"
							id="ordine_id" path="ordine" size="5" /> <spring-form:errors
							path="ordine" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="label.abilitato" /></td>
					<td><spring-form:checkbox id="abilitato_id" path="abilitato" />
						<spring-form:errors path="abilitato" cssClass="error" /></td>
				</tr>
			</table>
		</spring-form:form>
	</div>

	<div style="display: none;">
		<c:forEach items="${arjStepsBases}" var="arjStepsBases_var">
			<div id="${arjStepsBases_var.nomeStep}_titolo">${arjStepsBases_var.titolo}</div>
			<div id="${arjStepsBases_var.nomeStep}_descrizione">${arjStepsBases_var.descrizione}</div>
		</c:forEach>
	</div>
	<script type="text/javascript">
		function impostaValori(obj) {
			var nomeStep = getSelectTextAndValue(obj);
			if (nomeStep[1] != '') {
				$('titolo_id').value = $(nomeStep[1] + '_titolo').innerText;
				$('descrizione_id').value = $(nomeStep[1] + '_descrizione').innerHTML;
				tinyMCE.get('descrizione_id').setContent($('descrizione_id').value);
			}
		}

		initTextEditors();
		/*
		tinyMCE.init({
					mode : "exact",
					elements : "descrizione_id",
					theme : "advanced",
					theme_advanced_toolbar_location : "top",
					theme_advanced_toolbar_align : "left",
					plugins : "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
					theme_advanced_buttons1 : "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
					theme_advanced_buttons2 : "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
					/ theme_advanced_buttons3: "link,unlink,anchor,image,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,media,advhr",
					forced_root_block : false,
					force_br_newlines : true,
					force_p_newlines : false
				});
		*/
		
		function eliminaParametro(id){
			doHref('deleteParametro.htm?codiceStep=${foArjSteps.id.codice}&codice=' + id,'<fmt:message key="javascript.confirm.delete" />');			
		}
		
		jQuery(function(){
			// 
			jQuery('.parametri').each(function (){
				var obj = jQuery(this);
				var idparametro = obj.data("value");
				console.log("id:"+idparametro);
				var jqxhr = jQuery.ajax({
					  url: '${pageContext.request.contextPath}/ajax/ajaxVisualizzaParametroStep.htm',
					  context: document.body,
					  cache: false,				
					  dataType: "html",
					  data: "codice="+ idparametro ,
					  success: function(dataResult) { 
						  obj.html( dataResult );		

						},
					  error: function(dataError){						  
						  obj.html( obj.data("valore") );			
					  }	
					});		
				
				
				
				
			});
		});
		
		
	</script>

	<div id="functions">
		<ul>
			<c:if test="${foArjSteps.id.codice==null}">
				<li><a
					href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message
							key="button.insert" /></a></li>
			</c:if>
			<c:if test="${foArjSteps.id.codice!=null}">
				<li><a
					href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message
							key="button.update" /></a></li>
				<li><a
					href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
							key="button.delete" /></a></li>
			</c:if>
			<li><a
				href="javascript:doHref('../foarjstepstestata/view.htm?codice=${foArjSteps.foArjStepsTestata.id.codice}','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>	
		<c:if test="${foArjSteps.id.codice!=null}">
		
		<br class="clear"/>
		
		
					<c:if test="${not empty listaParametri}">
					
					<fieldset>
					<div class="jmesa">
						<table border="0" width="70%" cellpadding="2" cellspacing="1"
							class="table">
							<thead>
								<tr class="header">
									<td><fmt:message key="label.parametro" /></td>
									<td><fmt:message key="label.valore" /></td>
									<td><fmt:message key="label.azioni" /></td>
								</tr>
							</thead>
							
							<tbody class="tbody">
								<%
								    int i = 1;
								%>
								<c:forEach var="listaParametri_var" items="${listaParametri}"
									varStatus="listaParametriStatus">
		
									<tr class="<%=(i % 2) == 0 ? "odd" : "even"%>">
										<td>${listaParametri_var.foArjStepsParamsBase.chiave}</td>
										<td>
										
											<div style="display: inline;" class="parametri" 
												data-value="${listaParametri_var.id.codice}" 
												data-valore="${listaParametri_var.valore}" id="param_${listaParametri_var.id.codice}"></div>										
										
										</td>
										<td>
											<a style="border: none;" class="eliminaRiga" style="float: none;" href="javascript:eliminaParametro(${listaParametri_var.id.codice})" title="<fmt:message key="label.elimina" />">
								             <label><fmt:message key="label.elimina.image" /></label>
									        </a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					</fieldset>
				</c:if>
			<c:if test="${not empty listaParametriRimasti}">				
				<div style="display: none;">
					<c:forEach items="${listaParametriRimasti}"
						var="arjStepsParamsBases_var">
						<div id="${arjStepsParamsBases_var.chiave}_titolo">${arjStepsParamsBases_var.chiave}</div>
						<div id="${arjStepsParamsBases_var.chiave}_descrizione">${arjStepsParamsBases_var.descrizione}</div>
					</c:forEach>
				</div>
				<script type="text/javascript">
					function nuovoParametro() {
						
						jQuery('#nuovoParametroDiv').show();
						jQuery('#nuovoParametroLinkMOD_id').show();
						jQuery('#nuovoParametroLinkAnnulla_id').show();
						jQuery('#nuovoParametroLink_id').hide();
						
					}
					function showDescrizione(obj) {
						var nomeStep = getSelectTextAndValue(obj);
						if (nomeStep[1] != '') {
							$('parametro_descrizione_id').innerText = $(nomeStep[1]
									+ '_descrizione').innerText;
						}
						
						if(nomeStep[1]==='QRXML_BASE_ID'){
							jQuery('.selettoreAutoCompleter').show();
							jQuery('.selettoreText').hide();
						}else{
							jQuery('.selettoreText').show();
							jQuery('.selettoreAutoCompleter').hide();
						}
					}
					
					function annulla(){
						jQuery('#nuovoParametroDiv').hide();
						jQuery('#nuovoParametroLinkMOD_id').hide();
						jQuery('#nuovoParametroLinkAnnulla_id').hide();
						jQuery('#nuovoParametroLink_id').show();					
					}
					
					function salvaNuovoParametro(){
						var chiave = jQuery('#parametro_chiave_id').val();
						if(chiave==''){
							alert("Attenzione è obbligatorio specificare un parametro");
						}else{
							
							if(jQuery('#selettoreText_id').is(':visible')){
								jQuery('#parametro_valore_id').val(jQuery('#parametro_valore_testo_id').val());
							}
							
							doSubmit('insertParametro.htm','',document.invioDatiNuovo);
						}					
					}

					
				</script>
				
				<div id="nuovoParametroDiv" style="display: none;">
					<form name="invioDatiNuovo" method="post">
					<input type="hidden" name="codiceStep" value="${foArjSteps.id.codice}" />
						<table>
							<tr>
								<td><fmt:message key="label.parametro" /></td>
								<td><select name="parametroId" id="parametro_chiave_id"
									onchange="showDescrizione(this)">
										<option value="">
											<fmt:message key="label.select.default" />
										</option>
										<c:forEach items="${listaParametriRimasti}" var="lpr_var">
											<option value="${lpr_var.id}">${lpr_var.chiave}</option>
										</c:forEach>
								</select> <span id="parametro_descrizione_id" style="font-weight: bolder;"></span></td>
							</tr>
							<tr>
								<td><fmt:message key="label.valore" /></td>
								<td>
								
									<div class="selettoreText" id="selettoreText_id" style="display: none;">
										<input type="text" size="100" name="parametroValoreTesto" id="parametro_valore_testo_id"/>
									</div>
									<div class="selettoreAutoCompleter" style="display: none;">										
											<input type="text" id="term_id"
												name="term" class="searchbox"
												size="50" autocomplete="on"
												onchange="checkValue(this,'parametro_valore_testo_id');"
												onkeydown="return searchAll(this,event)" />
											<init:autocompleter methodAjax='ajaxFindQuadro.htm'												
												idHidden="parametro_valore_id" idInput="term_id"
												 minChars="1" />
										
									</div>										
									<input type="hidden" name="parametroValore" id="parametro_valore_id" />
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="functions">
					<ul>
						<li id="nuovoParametroLink_id"><a href="javascript:nuovoParametro()"><fmt:message
									key="button.nuovo_parametro" /></a></li>
						<li style="display: none;" id="nuovoParametroLinkMOD_id"><a href="javascript:salvaNuovoParametro()"><fmt:message
									key="button.save" /></a></li>
						<li style="display: none;" id="nuovoParametroLinkAnnulla_id"><a href="javascript:annulla()"><fmt:message
									key="button.annulla" /></a></li>									
					</ul>
				</div>
				
		</c:if>	
			
		</c:if>


	</div>
</body>
</html>
