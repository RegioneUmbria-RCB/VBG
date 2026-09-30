<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="form.mercatipresenzeStorico.title.search" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.mercatipresenzeStorico.title.search" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../mercatipresenzestorico/createSearch" />
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="mercatipresenzeStorico" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatipresenzeStorico" />
    </jsp:include>
	<table>
		<tr>
			<td>
				<fmt:message key="label.estremi_autorizzazione" />
			</td>
			<td>
				<fmt:message key="label.numero" />
				<spring-form:input id="autoriznumero_id" path="autorizzazioni.autoriznumero" size="10" onchange="scriviSuCampo();" onblur="hideChoises();"/>
				<fmt:message key="label.data" />
				<spring-form:input id="autorizdata_id" path="autorizzazioni.autorizdata" size="10" onchange="scriviSuCampo();" onblur="hideChoises();"/>
	    		<fmt:message key="label.comune" />
		    	<spring-form:input id="autorizcomune_id" path="autorizzazioni.autorizcomune.comune"  size="20" onchange="scriviSuCampo();" onblur="hideChoises();"/>
	    		<fmt:message key="label.registro" />
	    		<spring-form:input id="autorizregistro_id" path="autorizzazioni.tipologiaregistro.trDescrizione" size="40" onchange="scriviSuCampo();" onblur="hideChoises();"/>
	    		Risultati
	    		<select name="limit" id="limit_id" onchange="scriviSuCampo();" onblur="hideChoises();">
	    			<option value="10">10</option>
	    			<option value="20" selected="selected">20</option>
	    			<option value="100">100</option>
	    			<option value="1000">1000</option>
	    		</select>
	    		<div id="autorizzazione_id_indicator" class="indicator" style="display: none;">&nbsp;</div>
			</td>	
		</tr>
		<tr>
			<td></td>
			<td>
				<input type="text" id="autorizzazione_estremi_id" size="100" style="height: 0; border: 0; color: white;"/>
				<spring-form:hidden id="autorizzazione_hidden" path="autorizzazioni.id.codice" />
				<div id="autorizzazione_id_choices" class="autocomplete" style="display: none"></div>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiPresenzeStorico.anagrafe" />
			</td>
			<td>
				<script type="text/javascript">
					function setHiddenFieldAnagrafe(inputField,listItem){
						var a = listItem.id;
						$('anagrafe_id').value = inputField.value;
						$('anagrafe_hidden').value = a;
						$('anagrafe_id_choices').fade();
						$('autorizzazione_hidden').value = "";
						$('autoriznumero_id').value = "";
						$('autorizdata_id').value = "";
						$('autorizcomune_id').value = "";
						$('autorizregistro_id').value = "";
					}	
				</script>
				<spring-form:input id="anagrafe_id" path="anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden');autorizzazioniClear(this);" onkeydown="javascript:return searchAll(this,event,3)" size="67"/>
				<init:autocompleter methodAjax="findAnagrafe.htm" afterUpdateElement="setHiddenFieldAnagrafe" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente"/>
				<spring-form:errors path="anagrafe" cssClass="error"/> 
				<spring-form:hidden id="anagrafe_hidden" path="anagrafe.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiPresenzeStorico.mercato" />
			</td>
			<td>
				<script type="text/javascript">
					function setHiddenFieldmercato(inputField,listItem){
						var a = listItem.id;
						$('mercato_id').value = inputField.value;
						$('mercato_hidden').value = a;
						$('mercato_id_choices').fade();
						populateMercatiUso();
						populatePosteggiMercato();
					}	
				</script>
				<spring-form:input id="mercato_id" path="mercato.descrizione" cssClass="searchbox" onchange="checkValue(this,'mercato_hidden');mercatoClear(this);"  onkeydown="javascript:return searchAll(this,event)" size="67"/>
				<init:autocompleter methodAjax="findMercati.htm" afterUpdateElement="setHiddenFieldmercato" idHidden="mercato_hidden" idInput="mercato_id" inputTitleKey="label.ricerca_manifestazione"/>
				<spring-form:errors path="mercato" cssClass="error"/> 
				<spring-form:hidden id="mercato_hidden" path="mercato.id.codice"  />
			</td>
		</tr>
		<tr id="mercatiUso">
			<td><fmt:message key="form.mercatiPresenzeStorico.mercatouso" /></td>
			<td>
				<spring-form:select id="selectMercatiUso" path="mercatoUso.id.codice" >
					<spring-form:option value="${mercatipresenzeStorico.mercatoUso.id.codice}" >${mercatipresenzeStorico.mercatoUso.descrizione}</spring-form:option>
				</spring-form:select>
			</td>
		</tr>
		<tr id="mercatiPosteggio">
			<td><fmt:message key="form.registrazioni.mercati.posteggio" /></td>
			<td>
				<spring-form:select id="selectPosteggio" path="posteggio.id.codice" >
					<spring-form:option value=""><fmt:message key='label.select.default' /></spring-form:option>
					<c:if test="${not empty posteggiMercato}">
					<c:forEach items="${posteggiMercato}" var="var_posteggio">
					<spring-form:option value="${var_posteggio.id.codice}" >${var_posteggio.codiceposteggio}</spring-form:option>
					</c:forEach>
					</c:if>
				</spring-form:select>
				<spring-form:errors path="posteggio" cssClass="error" /> 
			</td>
		</tr>		
		<tr>
			<td>
				<fmt:message key="form.mercatiPresenzeStorico.anno" />
			</td>
			<td>
				<spring-form:select path="anno" >
				<spring-form:option value="0" ><fmt:message key='label.select.default' /></spring-form:option>
				<spring-form:options items="${anni}" />	
				</spring-form:select>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.categoria_merceologica" />
			</td>
			<td>
			<spring-form:select path="catMerc">
				<spring-form:option value=""><fmt:message key='label.select.default' /></spring-form:option>
				<c:forEach items="${catMercList}" var="currCatMerc">
					<spring-form:option value="${currCatMerc}" label="${currCatMerc}" />
				</c:forEach>
			</spring-form:select>
			</td>
		</tr>
	</table>
	
	<script type='text/javascript'>
		$('autoriznumero_id').focus();
		function autorizzazioniClear(){
			if(document.getElementById('anagrafe_id').value==''){
				removeOptionSelected("selectAutorizzazione");
			}
		}
		function mercatoClear(){
			if(document.getElementById('mercato_id').value==''){
				removeOptionSelected("selectMercatiUso");
			}
		}
		function populateMercatiUso(){
			var usoSelezionato = '${mercatipresenzeStorico.mercatoUso.id.codice}';
			removeOptionSelected("selectMercatiUso");
			var idMercato=document.getElementById("mercato_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiUso.htm', {
				  method: 'post',
				  parameters: {code: idMercato, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  var opts=response.split(",");
					  var select = $('selectMercatiUso');			
					  for(var i=0;i<opts.length-1;i++){
						  if(usoSelezionato == opts[i]){
							  select.options[select.options.length] = new Option(opts[i+1],opts[i],false,true);
						  }else{
						  	select.options[select.options.length] = new Option(opts[i+1],opts[i],false,false);
						  }
						  i++;
					  }
				    },
				  onFailure: function(){ alert("Errore nel recupero dei giorni!"); }
								  
				  });
		}
		function populatePosteggiMercato(){
			var posteggioSelezionato = '${mercatipresenzeStorico.posteggio.id.codice}';
			removeOptionSelected("selectPosteggio");
			var idMercato=document.getElementById("mercato_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findPosteggioMercato.htm', {
				  method: 'post',
				  parameters: {code: idMercato, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  var opts=response.split(",");
					  var select = $('selectPosteggio');
					  select.options[select.options.length] = new Option("<fmt:message key='label.select.default' />","");			
					  for(var i=1;i<opts.length;i++){
						  if(posteggioSelezionato == opts[i-1]){
							  select.options[select.options.length] = new Option(opts[i],opts[i-1],false,true);  
						  }else{
						  	select.options[select.options.length] = new Option(opts[i],opts[i-1],false,false);
						  }
						  i++;
					  }
				    },
				  onFailure: function(){ 
					alert("Errore nel recupero dei posteggi!");	 
				  }			  
			});								
		}

		function historySet(url){
			var d = new Date();
			new Ajax.Request('<%=request.getContextPath()%>/history/ajaxSet.htm', {
				  method: 'get',
				  parameters: {ReturnTo: url, limit: 12, ts: d.getTime()},
				  onSuccess: function(transport){},
				  onFailure: function(){}			  
			});
		}

		
		function removeOptionSelected(opt){
		  var elSel = document.getElementById(opt);
		  var i;
		  for (i = elSel.length - 1; i>=0; i--) {
		    if (elSel.options[i]) {
		       elSel.remove(i);
		    }
		  }
		}
		function doSubmitValidate(){
			var codAnag = $('anagrafe_hidden').value;
			var codAut = $('autorizzazione_hidden').value;
			var codMercato = $('mercato_hidden').value;
			if(codAnag || codAut || codMercato){
				historySet('../mercatipresenzestorico/createSearch.htm?resetAttrs=false');
				doSubmit('list.htm','',document.inviodati);
			}else{
				if(!(codAnag || codAut || codMercato)){
					alert("Specificare l'anagrafe o l'autorizzazione o il mercato!");
					$('autoriznumero_id').focus();
					return;
				}
			}
		}
		if($('mercato_id').value!=''){
			populateMercatiUso();
			populatePosteggiMercato();
		}
		
		var aut_autocompleter;

		function hideChoises(){
			if(aut_autocompleter){
				aut_autocompleter.hide();
			}
		}
		
		function scriviSuCampo(){
			
			var autoriznumero = $('autoriznumero_id').value;
			if(!isValidDate($('autorizdata_id'),true)){
				return;
			}
			var autorizdata = $('autorizdata_id').value;
			var autorizcomune = $('autorizcomune_id').value;
			var autorizregistro = $('autorizregistro_id').value;
			if(autoriznumero=='' && autorizdata=='' && autorizcomune=='' && autorizregistro==''){
				// alert("Per attivare la ricerca inserire un valore");
			}else{
				var estremi = autoriznumero+","+autorizdata+","+autorizcomune+","+autorizregistro;
				$('autorizzazione_estremi_id').value = estremi;
				$('autorizzazione_hidden').value = "";
				$('anagrafe_id').value = "";
				$('anagrafe_hidden').value = "";
				var limit = $('limit_id').value;
				aut_autocompleter = new Ajax.Autocompleter(
					    "autorizzazione_estremi_id", 
					    "autorizzazione_id_choices", 
					    "${pageContext.request.contextPath}/ajax/findAutorizzazioniDaEstremi.htm?limit="+limit+"&", 
					    {
						    paramName: "textToSearch",
						    indicator: "autorizzazione_id_indicator",
						    minChars: 1,
						    frequency: 0.1,
						    afterUpdateElement: setHiddenFieldautorizzazione_id
						 }
				);
				aut_autocompleter.activate();
			}
		}
		function setHiddenFieldautorizzazione_id(inputField,listItem){
			var idAut = listItem.id;
			var idAnagrafe = listItem.name;
			var estremiAut = inputField.value;
			var estremiAutArray = estremiAut.split(',');

			$('autoriznumero_id').value=estremiAutArray[0];
			$('autorizdata_id').value=estremiAutArray[1];
			$('autorizcomune_id').value=estremiAutArray[2];
			$('autorizregistro_id').value=estremiAutArray[3];
			$('anagrafe_id').value=estremiAutArray[4];

			$('autorizzazione_hidden').value=idAut;
			$('anagrafe_hidden').value=idAnagrafe;
			$('autorizzazione_id_choices').fade();
		}
	</script>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmitValidate()"><fmt:message key="button.search" /></a></li>
	<li><a href="createSearch.htm?resetAttrs=true"><fmt:message key="button.reset" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
