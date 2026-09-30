<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${alberoprocEndo.id.codiceinventario==0}">
			<fmt:message key="alberoproc.label.nuovo_alberoprocEndo.title" />
		</c:if> 
		<c:if test="${alberoprocEndo.id.codiceinventario!=0}">
			<fmt:message key="alberoproc.label.dettaglio_alberoprocEndo.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${alberoprocEndo.id.codiceinventario==0}">
			<fmt:message key="alberoproc.label.nuovo_alberoprocEndo.title" />
		</c:if> 
		<c:if test="${alberoprocEndo.id.codiceinventario!=0}">
			<fmt:message key="alberoproc.label.dettaglio_alberoprocEndo.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
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
		<spring-form:form commandName="alberoprocEndo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocEndo" />
		    </jsp:include>
		     <%
		  	  String inventario="";
		      String inventario_auto="display:none;";
		    %>
			<table>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_famigliendo" />
					</td>
					<td>
					<jsp:include page="../includes/autocompletergenericoTT.jsp" >
						<jsp:param name="idElemento" value="famigliaendo" />		
						<jsp:param name="propertyPath" value="inventarioprocedimento.tipoendo.tipifamiglieendo" />				
						<jsp:param name="pathPropertyDescription" value="inventarioprocedimento.tipoendo.tipifamiglieendo.tipo" />
						<jsp:param name="pathPropertyCode" value="inventarioprocedimento.tipoendo.tipifamiglieendo.id.codice" />
						<jsp:param name="autocompleterAjax" value="findTipifamiglieendo.htm?codicesoftware=" />	
						<jsp:param name="titleKey" value="label.ricerca_tipo_famiglia_endo" />
						<jsp:param name="id_help" value="help_famiglia" />
						<jsp:param name="help" value="help.search_famiglie_e_categorie_endo_archivi_base" />
					</jsp:include>
					</td>
					
					<%-- 
					<td>
						<input id="famiglia_id"  class="searchbox" onchange="checkValue(this,'famiglia_hidden');" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter methodAjax="findTipifamiglieendoSWeTT.htm" idHidden="famiglia_hidden" idInput="famiglia_id" inputTitleKey="label.ricerca_tipo_famiglia_endo"></init:autocompleter>
						<input type="hidden" id="famiglia_hidden"  />
					</td>
					--%>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_tipiendo" />
					</td>
					<td>
						<script type="text/javascript">
							function filtertipiendo(element, entry) { 
								return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+"&codicesoftware="+document.getElementById("famigliaendo_software_hidden").value;
							}
						</script>
						<%-- 
						<input id="tipiendo_id"  class="searchbox" onchange="checkValue(this,'tipiendo_hidden');" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter callBack="filtertipiendo" methodAjax="findTipiendoSWeTT.htm" idHidden="tipiendo_hidden" idInput="tipiendo_id" inputTitleKey="label.ricerca_tipiendo"></init:autocompleter>
						<input type="hidden" id="tipiendo_hidden"  />
						--%>
						
						<input id="tipiendo_id"  class="searchbox" onchange="checkValue(this,'tipiendo_hidden');" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter callBack="filtertipiendo" methodAjax="findTipiendo.htm" idHidden="tipiendo_hidden" idInput="tipiendo_id" inputTitleKey="label.ricerca_tipiendo"></init:autocompleter>
						<input name="inventarioprocedimento.tipiendo.id.codice" type="hidden" id="tipiendo_hidden"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" />
					</td>
					<td>
						<script type="text/javascript">
							function inventarioCallBack(inputField,listItem){
								var a = listItem.id;
								document.getElementById('inventarioprocedimento_id').value = inputField.value;
								document.getElementById('inventarioprocedimento_hidden').value = a;
								document.getElementById('inventarioprocedimento_id_hidden').value = a;
								$('inventarioprocedimento_id_choices').fade();	
							}
							function filterinventario(element, entry) { 
								return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_hidden").value+"&codicesoftware="+document.getElementById("famigliaendo_software_hidden").value;
							}
						</script>
						<spring-form:input id="inventarioprocedimento_id" path="inventarioprocedimento.procedimento" cssClass="searchbox" onchange="checkValue(this,'inventarioprocedimento_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter methodAjax="findInventarioprocByFamigliaAndCategoriaEndoAndSoftware.htm" idHidden="inventarioprocedimento_hidden" idInput="inventarioprocedimento_id" callBack="filterinventario" afterUpdateElement="inventarioCallBack" inputTitleKey="label.ricerca_inventarioprocedimento"/>
						<spring-form:errors path="inventarioprocedimento" cssClass="error"/> 
						<spring-form:hidden id="inventarioprocedimento_hidden" path="inventarioprocedimento.id.codice"  />
						<spring-form:hidden id="inventarioprocedimento_id_hidden" path="id.codiceinventario"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_flagPrincipale" />
					</td>
					<td>
						<spring-form:checkbox id="flagPrincipale_id" path="flagPrincipale" onchange="changeCheckboxValueEndo()" />
						<init:help idHelp="helpflagPrincipale" textKey="alberoproc.help.alberoprocEndo_flagPrincipale"/>
						<spring-form:errors path="flagPrincipale" cssClass="error"/>
					</td>
				</tr>				
				
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_azione" />
					</td>
					<td>
						<spring-form:select id="azione_id" path="azione.azId" >
							<option  value="0"/>
							<spring-form:options items="${azionis}" itemLabel="azDescrizione" itemValue="azId" />
						</spring-form:select>
						<spring-form:errors path="azione" cssClass="error"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_flagRichiesto" />
					</td>
					<td>
						<spring-form:checkbox id="flagRichiesto_id" path="flagRichiesto" onchange="changeCheckboxValueEndo()" />
						<init:help idHelp="helpflagRichiesto" textKey="alberoproc.help.alberoprocEndo_flagRichiesto"/>
						<spring-form:errors path="flagRichiesto" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_flagPubblica" />
					</td>
					<td>
						<spring-form:checkbox id="flagPubblica_id" path="flagPubblica" />
						<init:help idHelp="helpflagPubblica" textKey="alberoproc.help.alberoprocEndo_flagPubblica"/>
						<spring-form:errors path="flagPubblica" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_flagUsaBO" />
					</td>
					<td>
						<spring-form:checkbox id="flagRichiestoBoId" path="flagRichiestoBo" />
						<init:help idHelp="helpflagRichiestoBo" textKey="alberoproc.help.flag_usa_BO"/>
						<spring-form:errors path="flagRichiestoBo" cssClass="error"/>
					</td>
				</tr>

			</table>
			<script type='text/javascript'>
				$('inventarioprocedimento_id').focus();
				
				
			 	
			 	
			 	
			 	
			 	function changeCheckboxValueEndo(){
					jQuery('#flagRichiestoBoId${a.index}').attr('checked','checked');
			 	    setReadOnly${a.index}('#flagPrincipale_id','#flagRichiesto_id');
				}	
			 	
			 
		 	   function setReadOnly(principaleId,richiestoId)
		 	   {
		 		  var principale=jQuery(principaleId).is(':checked');
		 		  var richiesto=jQuery(richiestoId).is(':checked');
		 		 
		 		  if(richiesto || principale)
		 		  {
		 			jQuery("#flagRichiestoBoId${a.index}").prop("disabled", true);
		 		  }
		 		  if(!richiesto && !principale)
		 		  {
		 			 jQuery("#flagRichiestoBoId${a.index}").prop("disabled", false);
		 		  }
		 		 
		 	   }
		 	
		 	</script>
				
				

			</script>	
		</spring-form:form>
	</div>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertEndo.htm?alberoproc.id.codice='+${alberoproc.id.codice},'',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice='+${alberoproc.id.codice},'')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>