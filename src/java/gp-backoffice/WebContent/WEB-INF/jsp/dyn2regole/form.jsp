<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.web.Dyn2RegoleCommand"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${regolaCommand.regola.id.codice==null}">
			<fmt:message key="dyn2regole.title.nuovo" />
		</c:if> 
		<c:if test="${regolaCommand.regola.id.codice!=null}">
			<fmt:message key="dyn2regole.title.dettaglio" />
		</c:if>
	</title>
	<%-- TODO spostare lo script da scripts/cart a scripts dopo aver unificato le versioni di jQUery ed effettuato i test per le regressioni --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.tmpl.min.js"></script>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${regolaCommand.regola.id.codice==null}">
			<fmt:message key="dyn2regole.title.nuovo" />
		</c:if> 
		<c:if test="${regolaCommand.regola.id.codice!=null}">
			<fmt:message key="dyn2regole.title.dettaglio" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../dyn2regole/view" />
	</jsp:include>
	<jsp:include page="../includes/dialogs.jsp"/>
	<div id="subcontent">
		<spring-form:form commandName="regolaCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="regolaCommand" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="dyn2regole.label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione" path="regola.descrizione" maxlength="200" size="80"/>
						<spring-form:errors path="regola.descrizione" cssClass="error"/>
					</td>
				</tr>
			</table>
			<br/>
			<c:if test="${regolaCommand.regola.id.codice!=null}">
			<span class="titoloPagina"><fmt:message key="dyn2regole.title.listaespressioni" /></span>
			<div class="jmesa">
				<table id="expressions_table">
					<thead>
						<tr class="header">
							<td>Progr.</td>
							<td>e/o</td>
							<td>(</td>
							<td>Campo dinamico</td>
							<td>Tag XML Pratica STC</td>
							<c:if test="${isCartAttivo eq true }"><td>Id semantico CART</td></c:if>
							<td>Operatore confronto</td>
							<td>Valore confronto</td>
							<td>)</td>
							<td><fmt:message key="button.delete" /></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${regolaCommand.espressioni}" var="expr" varStatus="iter" >
						<tr class="riga_espressione" id="riga_espressione_${iter.index}">
							<td>
								<spring-form:hidden path="espressioni[${iter.index}].id.codice" id="exprid_${iter.index}"/>
								<%-- <spring-form:input path="espressioni[${iter.index}].progressivo" size="3" maxlength="3" id="progressivo_${iter.index}" readonly="true"/>--%>
							    <input type="text" name="espressioni[${iter.index}].progressivo" value="${iter.index}" readonly="readonly" size="3"></input>
							</td>
							<td>
								<spring-form:select path="espressioni[${iter.index}].operatoreLogico" id="operatoreLogico_${iter.index}">
									<spring-form:option value="">&nbsp;</spring-form:option>
									<spring-form:option value="AND">e</spring-form:option>
									<spring-form:option value="OR">o</spring-form:option>
								</spring-form:select>
							</td>
							<td>
								<spring-form:select path="espressioni[${iter.index}].parentesiAperta" id="parentesiAperta_${iter.index}">
									<spring-form:option value="">&nbsp;</spring-form:option>
									<spring-form:option value="(">(</spring-form:option>
									<spring-form:option value="((">((</spring-form:option>
									<spring-form:option value="(((">(((</spring-form:option>
								</spring-form:select>
							</td>
							<td class="inline-ui-cell">
								<jsp:include page="../includes/autocompletergenerico.jsp" >
									<jsp:param name="idElemento" value="dyn2Campi_${iter.index}" />
									<jsp:param name="propertyPath" value="espressioni[${iter.index}].dyn2Campi" />										
									<jsp:param name="pathPropertyDescription" value="espressioni[${iter.index}].dyn2Campi.nomecampo" />
									<jsp:param name="pathPropertyCode" value="espressioni[${iter.index}].dyn2Campi.id.codice" />
									<jsp:param name="autocompleterAjax" value="findDyn2CampiCurrentSoftwareOrTT.htm" />	
									<jsp:param name="autocompleterInputSize" value="50" />	
								</jsp:include>
								<a class="vbg-btn btn-cerca" href="javascript:void(0)" onclick="ricercaCampoFiltrandoPerModello(${iter.index})" title="<fmt:message key="help.ricerca_dyn2campi_da_dyn2Modelli" />">
									<!-- <img src="<%=request.getContextPath() %>/images/search.gif"  />  -->
								</a>
								<!-- DIV CHE RAPPRESENTA IL DIALOG PER SELEZIONARE UN CAMPO PARTENDO 
								     DA UN MODELLO -->
								<!-- START -->     
								<script type='text/javascript'>
								// Mostra pannello di ricerc acampi filtrando per modello	
									function ricercaCampoFiltrandoPerModello(index){
										
										dijit.byId('pannelloSceltaDyn2campiDiv'+index).show();
									}
									
									function filter${iter.index}(element, entry) {
										return entry + "&codiceModello=" + document.getElementById("dyn2Modellit${iter.index}_hidden").value;								
									}
									// sETTA IL VALORE DEL CAMPO SCELTO SULLA PAGINA PRINCIPALE
									function setValuesAndGo(index){
										
										jQuery('#dyn2Campi_'+index+'_id').val(jQuery('#dyn2CampiR_'+index+'_id').val());
										jQuery('#dyn2Campi_'+index+'_hidden').val(jQuery('#dyn2CampiR_'+index+'_hidden').val());
										jQuery('#dyn2CampiR_'+index+'_id').val('');
										jQuery('#dyn2CampiR_'+index+'_hidden').val('');
										dijit.byId('pannelloSceltaDyn2campiDiv'+index).hide();
									}
									//CHIUDE IL PANNELLO
									function closeSceltaCampi(index)
									{
										jQuery('#dyn2CampiR_'+index+'_id').val('');
										jQuery('#dyn2CampiR_'+index+'_hidden').val('');
										dijit.byId('pannelloSceltaDyn2campiDiv'+index).hide();
									}
								</script>
								<!-- PANNELLO RICERCA -->
								<div dojoType="dijit.Dialog" id="pannelloSceltaDyn2campiDiv${iter.index}" title="Scegli il campo"  style="height: auto;min-width: 400px;">
									<div style="height: 500px;">	
										<table>
										<tr>
											<td>
												<fmt:message key="label.modello" />
											</td>
											<td>
											<jsp:include page="../includes/autocompletergenericoTT.jsp" >
												<jsp:param name="idElemento" value="dyn2Modellit${iter.index}" />		
												<jsp:param name="propertyPath" value="dyn2Modellit" />				
												<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
												<jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
												<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm?codicesoftware=" />
												<jsp:param name="help" value="help.modelli_archivi_base" />
												<jsp:param name="id_help" value="modello${iter.index}" /> 
											</jsp:include>
											</td>
										</tr>
										<tr>
											<td>
												<fmt:message key="label.campo" />
											</td>
											<td>
											<jsp:include page="../includes/autocompletergenerico.jsp" >
												<jsp:param name="idElemento" value="dyn2CampiR_${iter.index}" />
												<jsp:param name="propertyPath" value="espressioni[${iter.index}].dyn2Campi" />										
												<jsp:param name="pathPropertyDescription" value="espressioni[${iter.index}].dyn2Campi.nomecampo" />
												<jsp:param name="pathPropertyCode" value="espressioni[${iter.index}].dyn2Campi.id.codice" />
												<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?codiceSoftware=" />
												<jsp:param name="ajaxCallBack" value="filter${iter.index}" />
												<jsp:param name="help" value="help.modelli_archivi_base" />
												<jsp:param name="id_help" value="campo${iter.index}" />
											</jsp:include>
											</td>
										</tr>
									</table>
							            
									<div id="functions">
										<ul>
											<li id="OkId"><a href="javascript:setValuesAndGo(${iter.index})"><fmt:message key="button.ok" /></a></li>
											<li><a href="javascript:closeSceltaCampi(${iter.index})"><fmt:message key="button.annulla" /></a></li>
										</ul>
									</div>
									</div>
								</div>
								<!-- END -->
							</td>
							<td>
								<spring-form:input path="espressioni[${iter.index}].attributoStc" id="attributoStc_${iter.index}"/>
							</td>
							<c:if test="${isCartAttivo eq true }">
							<td>
								<spring-form:input path="espressioni[${iter.index}].idSemanticoCart" id="idSemanticoCart_${iter.index}"/>
							</td>
							</c:if>
							<td>
								<spring-form:select path="espressioni[${iter.index}].operatoreConfronto" id="operatoreConfronto_${iter.index}">
									<%--<spring-form:option value="">&nbsp;</spring-form:option> --%>
									<spring-form:option value="EQ">uguale a</spring-form:option>
									<spring-form:option value="NOT_EQ">diverso da</spring-form:option>
									<spring-form:option value="GT">maggiore di</spring-form:option>
									<spring-form:option value="GT_EQ">maggiore o uguale a</spring-form:option>
									<spring-form:option value="LT">minore di</spring-form:option>
									<spring-form:option value="LT_EQ">minore o uguale a</spring-form:option>
									<spring-form:option value="IS_NULL">vuoto</spring-form:option>
									<spring-form:option value="NOT_IS_NULL">non vuoto</spring-form:option>
									<spring-form:option value="IN">ha uno dei valori</spring-form:option>
									<spring-form:option value="NOT_IN">non ha nessuno dei valori</spring-form:option>
									<spring-form:option value="MATCHES">corrisponde a regexp</spring-form:option>
									<spring-form:option value="NOT_MATCHES">non corrisponde a regexp</spring-form:option>
								</spring-form:select>
							</td>
							<td>
								<spring-form:input path="espressioni[${iter.index}].valoreConfronto" id="valoreConfronto_${iter.index}"/>
							</td>
							<td>
								<spring-form:select path="espressioni[${iter.index}].parentesiChiusa" id="parentesiChiusa${iter.index}">
									<spring-form:option value="">&nbsp;</spring-form:option>
									<spring-form:option value=")">)</spring-form:option>
									<spring-form:option value="))">))</spring-form:option>
									<spring-form:option value=")))">)))</spring-form:option>
								</spring-form:select>
							</td>
							<td align="center">
								<c:if test="${expr.id.codice != null }">
								<a id="linkCancella_${iter.index}" class="vbg-btn btn-elimina" title="<fmt:message key="button.delete"/>" 
								href="javascript:doHref('${regolaCommand.prefixPopup}deleteEspressione.htm?codice=${expr.id.codice}','<fmt:message key="alert.delete_espressione_regola" />');">
								</a>
								</c:if>
								
								<c:if test="${expr.id.codice == null }">
								<a id="linkCancella_${iter.index}" class="vbg-btn btn-elimina" title="<fmt:message key="button.delete" />"
								href="javascript:doSubmit('${regolaCommand.prefixPopup}deleteEspressioneNonSalvata.htm?index=${iter.index}','',document.inviodati);" >
								</a>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<%-- template jQuery per l'aggiunta di nuove righe --%>
				<script id="template_riga_espressione" type="text/x-jquery-tmpl">
						<tr class="riga_espressione" id="riga_espressione_${placeholder}">
							<td>
								<input type='hidden' name="espressioni[\${index}].id.codice" id="exprid_\${index}"/>
								<input type='text' name="espressioni[\${index}].progressivo" size="3" maxlength="3" id="progressivo_\${index}"/>
							</td>
							<td>
								<select name="espressioni[\${index}].operatoreLogico" id="operatoreLogico_\${index}">
									<option value="">&nbsp;</option>
									<option value="AND">e</option>
									<option value="OR">o</option>
								</select>
							</td>
							<td>
								<select name="espressioni[\${index}].parentesiAperta" id="parentesiAperta_\${index}">
									<option value="">&nbsp;</option>
									<option value="(">(</option>
								</select>
							</td>
							<td>
								
								<input type='text' name="espressioni[\${index}].dyn2Campi" id="dyn2Campi_\${index}"/>
                               <%--
                                 <jsp:include page="../includes/autocompletergenerico.jsp" >
									<jsp:param name="idElemento" value="dyn2Campi_\${index}" />
									<jsp:param name="propertyPath" value="espressioni[\${index}].dyn2Campi" />										
									<jsp:param name="pathPropertyDescription" value="espressioni[\${index}].dyn2Campi.etichetta" />
									<jsp:param name="pathPropertyCode" value="espressioni[\${index}].dyn2Campi.id.codice" />
									<jsp:param name="autocompleterAjax" value="findDyn2CampiCurrentSoftwareOrTT.htm" />	
									<jsp:param name="autocompleterInputSize" value="45" />	
									<jsp:param name="titleKey" value="label.ricerca_tipiarchivioistanze" />
								</jsp:include>
								
                                <spring-form:input id="dyn2Campi_\${index}"
									path="espressioni[\${index}].dyn2Campi" cssClass="searchbox"
									size="45"
									onchange="${inputIdOnChange}"
									onkeydown="return searchAll(this,event)" />
								
								<init:autocompleter methodAjax='findDyn2CampiCurrentSoftwareOrTT.htm'
									
									idHidden="dyn2Campi_1_hidden" idInput="dyn2Campi_1" />
									<spring-form:errors path="espressioni[1].dyn2Campi" cssClass="error" />
									<spring-form:hidden id="dyn2Campi_1_hidden"
									path="${param.pathPropertyCode}" />
								--%>
							</td>
							<td>
								<input type='text' name="espressioni[\${index}].attributoStc" id="attributoStc_\${index}"/>
							</td>
							<td>
								<input type='text' name="espressioni[\${index}].idSemanticoCart" id="idSemanticoCart_\${index}"/>
							</td>
							<td>
								<select name="espressioni[\${index}].operatoreConfronto" id="operatoreConfronto_\${index}">
									<option value="">&nbsp;</option>
									<option value="EQ">uguale a</option>
									<option value="NOT_EQ">diverso da</option>
									<option value="GT">maggiore di</option>
									<option value="GT_EQ">maggiore o uguale a</option>
									<option value="LT">minore di</option>
									<option value="LT_EQ">minore o uguale a</option>
									<option value="IS_NULL">vuoto</option>
									<option value="NOT_IS_NULL">non vuoto</option>
									<option value="IN">ha uno dei valori</option>
									<option value="NOT_IN">non ha nessuno dei valori</option>
									<option value="MATCHES">corrisponde a regexp</option>
									<option value="NOT_MATCHES">non corrisponde a regexp</option>
								</select>
							</td>
							<td>
								<input type='text' name="espressioni[\${index}].valoreConfronto" id="valoreConfronto_\${index}"/>
							</td>
							<td>
								<select name="espressioni[\${index}].parentesiChiusa" id="parentesiChiusa_\${index}">
									<option value="">&nbsp;</option>
									<option value=")">)</option>
								</select>
							</td>
							<td align="center">
								<a id="linkCancella_\${index}" class="linkCancella vbg-btn btn-elimina" href="javascript:void(0);" title="<fmt:message key="button.delete" />">
								</a>
							</td>
						</tr>
				</script>
			</div>
			<div>
				<a class="vbg-btn btn-aggiungi" href="javascript:doSubmit('${regolaCommand.prefixPopup}addEspressione.htm','',document.inviodati);" title="<fmt:message key="button.aggiungi" />">
					<!-- <img src="<%=request.getContextPath() %>/images/add.png"  />  -->
				</a>
			</div>
			</c:if>

			<script type='text/javascript'>
				jQuery(document).ready(function(){
					jQuery("#descrizione").focus();
					jQuery("a.linkCancella").click(cancellaEspressione);
					jQuery("#linkAggiungi").click(aggiungiEspressione);
				});
				// NON UTILIZZATA
				cancellaEspressione = function(event){
					//TODO determinare l'indice della riga da cancellare
					var img = jQuery(this).toggleClass('btn-attesa');
					//img.attr('src',"<%=request.getContextPath() %>/images/spinner.gif");
					var row = jQuery(this).parents("tr.riga_espressione");
					var rowIndex = jQuery('tr.riga_espressione').index(row);
					var callParams = {
							index: rowIndex
					}
					var ajaxCallOpts = {
							  url: "<%=request.getContextPath() %>/dyn2regole/ajaxDeleteExpression.htm?",
							  type: "POST", 
							  dataType: "json",
							  data: callParams,
							  success: cancellaEspressioneCallback,
							  error: ajaxErrorCallback
					};
					jQuery.ajax(ajaxCallOpts);
				};
				// NON UTILIZZATA
				cancellaEspressioneCallback = function(data, code, jqXHR){
					if(data){
						if(data.removedIndex){
							//rimuovo la riga all'indice specificato
							var removeIdx = Number(data.removedIndex);
							var delRow = jQuery('tr.riga_espressione').eq(removeIdx);
							delRow.remove();
							//modifico gli indici delle righe successive a quella eliminata (negli attributi name e id dei campi)
							var readNext = true;
							while(readNext){
								removeIdx++;
								
								if(field.size() > 0){
									//id
									var field = jQuery('#exprid_' + removeIdx);
									field.attr('id','exprid_' + (removeIdx-1));
									field.attr('name','espressioni[' + (removeIdx-1) + '].id.codice');
									//progressivo
									field = jQuery('#progressivo_' + removeIdx);
									field.attr('id','progressivo_' + (removeIdx-1));
									field.attr('name','espressioni[' + (removeIdx-1) + '].progressivo');
									//op. logico
									field = jQuery('#operatoreLogico_' + removeIdx);
									field.attr('id','operatoreLogico_' + (removeIdx-1));
									field.attr('name','espressioni[' + (removeIdx-1) + '].operatoreLogico');
									//par. aperta
									field = jQuery('#parentesiAperta_' + removeIdx);
									field.attr('id','parentesiAperta_' + (removeIdx-1));
									field.attr('name','espressioni[' + (removeIdx-1) + '].parentesiAperta');
									//campo dinamico
									field = jQuery('#dyn2Campi_' + removeIdx);
									field.attr('id','dyn2Campi_' + (removeIdx-1));
									field.attr('name','espressioni[' + (removeIdx-1) + '].dyn2Campi.id.codice');
									//attributo STC
									field = jQuery('#attributoStc_' + removeIdx);
									field.attr('id','attributoStc_' + (removeIdx-1));
									field.attr('name','espressioni[' + (removeIdx-1) + '].attributoStc');
									//id semantico CART
									field = jQuery('#idSemanticoCart_' + removeIdx);
									field.attr('id','idSemanticoCart_' + (removeIdx-1));
									field.attr('name','espressioni[' + (removeIdx-1) + '].idSemanticoCart');
									//op. confronto
									field = jQuery('#operatoreConfronto_' + removeIdx);
									field.attr('id','operatoreConfronto_' + (removeIdx-1));
									field.attr('name','espressioni[' + (removeIdx-1) + '].operatoreConfronto');
									//valore confronto
									field = jQuery('#valoreConfronto_' + removeIdx);
									field.attr('id','valoreConfronto_' + (removeIdx-1));
									field.attr('name','espressioni[' + (removeIdx-1) + '].valoreConfronto');
									//par. chiusa
									field = jQuery('#parentesiChiusa_' + removeIdx);
									field.attr('id','parentesiChiusa_' + (removeIdx-1));
									field.attr('name','espressioni[' + (removeIdx-1) + '].parentesiChiusa');
								}
								else{
									readNext = false;
								}
							}
						}
						else if(data.error){
							displayErrorMessage(data.error,"Errore");
						}
					}
				};
				// NON UTILIZZATA
				aggiungiEspressione = function(){
					var rows = jQuery('tr.riga_espressione');
					var nextIndex = rows.size();
					var template = jQuery("#template_riga_espressione");
					var tabella = jQuery("table#expressions_table");
					if(template && template.length > 0){
						//$.tmpl(template,{index: maxProg}).appendTo(tabella.find('tbody'));
						template.tmpl({index: nextIndex}).appendTo(tabella.find('tbody'));
					}
				};
				
				ajaxErrorCallback = function(jqXHR, errorType, exception){
					var errMsg = "";
					//if(errorType)errMsg += "tipo errore: " + errorType + " ";
					if(exception){
						if(exception.message){
							exception = exception.message;
						}
						errMsg += "messaggio: " + exception;
					}
					var status = jqXHR.status;
					if(status){
						errMsg += "<br/>Status: " + status;
					}
					displayErrorMessage("Si è verificato un errore nella comunicazione con il server: " + errMsg,"Errore");
				};
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${regolaCommand.regola.id.codice==null}">
				<li><a href="javascript:doSubmit('${regolaCommand.prefixPopup}insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${regolaCommand.regola.id.codice!=null}">
				<li><a href="javascript:doSubmit('${regolaCommand.prefixPopup}update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<%-- <li><a href="javascript:void();" title="dyn2regole.button.verificasintassi.alt"><fmt:message key="dyn2regole.button.verificasintassi" /></a></li>--%>
				<li><a href="javascript:doSubmit('${regolaCommand.prefixPopup}validaEspressioni.htm','',document.inviodati)" title="dyn2regole.button.verificasintassi.alt"><fmt:message key="dyn2regole.button.verificasintassi" /></a></li>
				<c:if test="${regolaCommand.popup eq false or empty regolaCommand.popup}">
					<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			    </c:if>
			</c:if>
			<c:if test="${regolaCommand.popup eq false or empty regolaCommand.popup}">
				<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
			</c:if>
			<c:if test="${regolaCommand.popup eq true}">
				<li><a href="javascript:self.close()"><fmt:message key="button.back" /></a></li>
			</c:if>
		</ul>
	</div>
	
	<c:if test="${regolaCommand.popup eq true}">
		<%if(StringUtils.defaultString(request.getParameter("done"),"false").equalsIgnoreCase("true")){ 		
		String desc = ((Dyn2RegoleCommand)request.getAttribute("regolaCommand")).getRegola().getDescrizione().replace("'","\\'"); 		
		%>			
		<script type="text/javascript">
		jQuery(document).ready(function(){
			//alert(${regolaCommand.popupCaller});
			opener.jQuery('#${regolaCommand.popupCaller}').val('<%= desc%>');
			opener.jQuery('#${regolaCommand.popupCaller}').val('<%= desc%>');
			opener.jQuery('#dyn2RegoleAttivo_hidden').val('${regolaCommand.regola.id.codice}');
			opener.jQuery('#${regolaCommand.popupCaller}').change();
			opener.jQuery('#${regolaCommand.popupCaller}').change();
		});
		</script>				
		<%} %>
	</c:if>	
	
</body>
</html>