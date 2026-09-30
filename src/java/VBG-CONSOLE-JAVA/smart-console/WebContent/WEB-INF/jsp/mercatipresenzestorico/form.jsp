<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.gestione_presenze_storico.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.gestione_presenze_storico" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="mercatipresenzeStorico" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatipresenzeStorico" />
    </jsp:include>
    <fieldset>
    		<legend>Dati Autorizzazione</legend>
		    <table>
		    	<tr>
		    		<td>
			    		<fmt:message key="label.numero" />:
			    	</td>
			    	<td>
			    		${mercatipresenzeStorico.autorizzazioni.autoriznumero}
			    	</td>
			    </tr>
		   		<tr>
		   			<td>
			    		<fmt:message key="label.data" />:
			    	</td>
			    	<td>
			    		<fmt:formatDate value="${mercatipresenzeStorico.autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
			   		</td>
			   	</tr>
		    	<tr>
		    		<td>
			    		<fmt:message key="label.comune" />:
			    	</td>
				    <td>
				    	${mercatipresenzeStorico.autorizzazioni.autorizcomune.comune}
					</td>
				</tr> 
				<tr>
					<td>
			    		<fmt:message key="label.registro" />:
			    	</td>
			    	<td>
			    		${mercatipresenzeStorico.autorizzazioni.tipologiaregistro.trDescrizione}
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.anagrafe" />:
					</td>
					<td>
						${mercatipresenzeStorico.autorizzazioni.anagrafe.descrizioneRichiedente}
					</td>
				</tr>
			</table>
	</fieldset>
    <fieldset>
    <legend>Nuovo record</legend>
	<table>
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
				<spring-form:errors path="mercatoUso" cssClass="error"/>
			</td>
		</tr>
		<tr id="mercatiPosteggio">
			<td><fmt:message key="form.registrazioni.mercati.posteggio" /></td>
			<td>
				<spring-form:select id="selectPosteggio" path="posteggio.id.codice" >
					<%-- <spring-form:option value=""><fmt:message key='label.select.default' /></spring-form:option> --%>
					<spring-form:option value="${var_posteggio.id.codice}" >${var_posteggio.codiceposteggio}</spring-form:option>
				</spring-form:select>
				<spring-form:errors path="posteggio" cssClass="error" /> 
			</td>
		</tr>		
		<tr>
			<td>
				<fmt:message key="form.mercatiPresenzeStorico.anno" />
			</td>
			<td>
				<spring-form:input path="anno" maxlength="4" size="4" cssStyle="text-align:right;"/>
				<spring-form:errors path="anno" cssClass="error" /> 
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
		<tr>
			<td>
				<fmt:message key="label.presenze" />
			</td>
			<td>
				<spring-form:input path="numeropresenze" size="4" cssStyle="text-align:right;"/>
				<spring-form:errors path="numeropresenze" cssClass="error" /> 
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.presenze_come_proprietario" />
			</td>
			<td>
				<spring-form:input path="numPresProprietario" size="4" cssStyle="text-align:right;" />
				<spring-form:errors path="numPresProprietario" cssClass="error" /> 
			</td>
		</tr>
	</table>
	</fieldset>
	<script type='text/javascript'>
		//$('anagrafe_id').focus();
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
					  select.options[select.options.length] = new Option("<fmt:message key='label.select.default' />","");
					  for(var i=1;i<opts.length;i++){
						  if(usoSelezionato == opts[i-1]){
							  select.options[select.options.length] = new Option(opts[i],opts[i-1],false,true);
						  }else{
						  	select.options[select.options.length] = new Option(opts[i],opts[i-1],false,false);
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
			doSubmit('insert.htm','',document.inviodati);
		}

		if($('mercato_id').value!=''){
			populateMercatiUso();
			populatePosteggiMercato();
		}
	</script>
	</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${mercatipresenzeStorico.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','<fmt:message key="javascript.confirm.update" />',document.inviodati)"><fmt:message key="button.save" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<c:if test="${mercatipresenzeStorico.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.save" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('listStorico.htm?autId=${mercatipresenzeStorico.autorizzazioni.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>