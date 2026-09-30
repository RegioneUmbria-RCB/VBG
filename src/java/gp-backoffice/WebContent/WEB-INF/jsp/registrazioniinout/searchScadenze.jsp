<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="form.registrazioniInOut.title.search" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="form.registrazioniInOut.title.search" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include>
    <jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../registrazioniinout/searchScadenze" />
    </jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="registrazioniInOutCommand" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="registrazioniInOutCommand" />
	    </jsp:include>
	    <table>
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.registrazioniCausali" />
			</td>
			<td>
				<spring-form:select id="regCausali" path="filter.registrazioniCausali.id.codice">
				    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${registrazioniCausaliList}" itemValue="id.codice"	itemLabel="descrizione" />
				</spring-form:select>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.conti" />
			</td>
			<td>
				<spring-form:input id="conti_id" path="filter.conti.descrizioneConto" cssClass="searchbox" onchange="checkValue(this,'conti_hidden')" size="80" onkeydown="javascript:return searchAll(this,event)" />
				<init:autocompleter methodAjax="findConti.htm" idHidden="conti_hidden" idInput="conti_id" inputTitleKey="label.ricerca_conto"/>
				<spring-form:errors path="filter.conti" cssClass="error"/> 
				<spring-form:hidden id="conti_hidden" path="filter.conti.id.codice"  />
			</td>
		</tr>
        <tr>
			<td><fmt:message key="form.registrazioniFilter.scadenza" /></td>
			<td>
                <fmt:message key="label.from" />
			    <spring-form:input id="dataInizio_id" path="filter.dataInizio" size="10" maxlength="10"  onblur="isValidDate(this,true);"/>
			    <init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="dataInizio_id" textKey="label.calendar"/> 
			  	<spring-form:errors	path="filter.dataInizio" cssClass="error" />
            	<fmt:message key="label.to" /> 
			    <spring-form:input id="dataFine_id" path="filter.dataFine"	size="10" maxlength="10"  onblur="isValidDate(this,true);"/> 
				<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="dataFine_id" textKey="label.calendar"/>
				<spring-form:errors path="filter.dataFine" cssClass="error" />
			</td>            
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.anagrafe" />
			</td>
			<td>
				<script type="text/javascript">
				function setHiddenFieldanagrafe(inputField,listItem){
					var a = listItem.id;
					//array che contiene l'id dei campi separati da '#'
					//i primi due valori sono l'id e la descrizione dell'anagrafe
					var arrayMercati=a.split('#');
					var arrayAnagrafe= inputField.value.split('-');
					if(arrayMercati.length>1 || arrayAnagrafe.length>1){
						document.getElementById('anagrafe_id').value = arrayMercati[1];
						document.getElementById('anagrafe_hidden').value = arrayMercati[0];
						var replace=new String();
						replace=arrayAnagrafe[0].substring(6,arrayAnagrafe[0].length);
						document.getElementById('mercati_id').value = replace;
						document.getElementById('mercati_hidden').value = arrayMercati[2];
						removeOptionSelected("selectMercatiUso");
						removeOptionSelected("selectPosteggio");
						try{
							
							$('selectPosteggio').add(new Option( arrayAnagrafe[2], arrayMercati[4]),  $('selectPosteggio').options[0]);
						}catch(e){ //in IE, try the below version instead of add()
							
						   $('selectPosteggio').add(new Option( arrayAnagrafe[2], arrayMercati[4]));
					  	}
						try{
						  	$('selectMercatiUso').add(new Option( arrayAnagrafe[1], arrayMercati[3]),  $('selectMercatiUso').options[0]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectMercatiUso').add(new Option(arrayAnagrafe[1], arrayMercati[3]));
						  }
					}else{
						document.getElementById('anagrafe_id').value = arrayAnagrafe[0];
						document.getElementById('anagrafe_hidden').value = arrayMercati[0];
						document.getElementById('mercati_id').value = '';
						document.getElementById('mercati_hidden').value = '';
						removeOptionSelected("selectMercatiUso");
						removeOptionSelected("selectPosteggio");
					}
					$('anagrafe_id_choices').fade();	 
				}
				</script>
				<spring-form:input id="anagrafe_id" path="filter.anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden')" size="80" onkeydown="javascript:return searchAll(this,event,3)"/>
				<init:autocompleter methodAjax="findAnagrafeRegistrazioniForMercati.htm" afterUpdateElement="setHiddenFieldanagrafe" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente"/>
				<spring-form:errors path="filter.anagrafe" cssClass="error"/> 
				<spring-form:hidden id="anagrafe_hidden" path="filter.anagrafe.id.codice"  />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniFilter.mercati" /></td>
			<td>
				<script type="text/javascript">
				function setHiddenFieldmercati(inputField,listItem){
					var a = listItem.id;
					//array che contiene l'id dei campi separati da '#'
					//i primi due valori sono l'id e la descrizione dell'anagrafe
					var arrayMercati=a.split('#');
					var arrayAnagrafe= inputField.value.split('-');
					if(arrayMercati.length>1 || arrayAnagrafe.length>1){
						document.getElementById('mercati_id').value = arrayMercati[1];
						document.getElementById('mercati_hidden').value = arrayMercati[0];
						var replace=new String();
						replace=arrayAnagrafe[0].substring(6,arrayAnagrafe[0].length);
						document.getElementById('anagrafe_id').value = replace;
						document.getElementById('anagrafe_hidden').value = arrayMercati[2];
						removeOptionSelected("selectMercatiUso");
						removeOptionSelected("selectPosteggio");
						try{
							$('selectPosteggio').add(new Option( arrayMercati[6], arrayMercati[5]),  $('selectPosteggio').options[0]);
						}catch(e){ //in IE, try the below version instead of add()
							
						   $('selectPosteggio').add(new Option( arrayMercati[6], arrayMercati[5]));
					  	}
						try{
						  	$('selectMercatiUso').add(new Option( arrayMercati[4], arrayMercati[3]),  $('selectMercatiUso').options[0]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectMercatiUso').add(new Option(arrayMercati[4], arrayMercati[3]));
						  }
					}else{
						document.getElementById('mercati_id').value = arrayAnagrafe[0];
						document.getElementById('mercati_hidden').value = arrayMercati[0];
						mercatiUsoDisplay();	 
						assegnaPosteggio();
					}
					$('mercati_id_choices').fade();
					
				}
				</script>
				<spring-form:input id="mercati_id" path="filter.mercati.descrizione" cssClass="searchbox" onchange="checkValue(this,'mercati_hidden');eliminaMercati(this);" size="80" onkeydown="javascript:return searchAll(this,event)" />
				<init:autocompleter methodAjax="findMercatiForAnagrafe.htm" afterUpdateElement="setHiddenFieldmercati" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
				<spring-form:errors path="filter.mercati" cssClass="error"/> 
				<spring-form:hidden id="mercati_hidden" path="filter.mercati.id.codice" />
			</td>
		</tr>	
		<tr id="mercatiUso">
			<td><fmt:message key="form.registrazioni.mercatiUso" /></td>
			<td>
				<spring-form:select id="selectMercatiUso" path="filter.mercatiUso.id.codice">
				<spring-form:option value="${registrazioniInOutCommand.filter.mercatiUso.id.codice}" label="${registrazioniInOutCommand.filter.mercatiUso.descrizione}"></spring-form:option>
				</spring-form:select><spring-form:errors path="filter.mercatiUso.id.codice" cssClass="error"/> 
			</td>
		</tr>
		<tr id="mercatiPosteggio">
			<td><fmt:message key="form.registrazioni.mercati.posteggio" /></td>
			<td>
				<spring-form:select id="selectPosteggio" path="filter.posteggio.id.codice">
				<spring-form:option value="" ><fmt:message key="label.select.default" /></spring-form:option>
				</spring-form:select><spring-form:errors path="filter.posteggio.id.codice" cssClass="error"/> 
			</td>
		</tr>
        <tr>
			<td valign="top"><fmt:message key="form.registrazioniFilter.alberoproc" /></td>
			<td class="inline-ui-cell">
			 <%-- 
				<spring-form:input id="alberoproc_id" path="filter.alberoproc.scDescrizione" cssClass="searchbox" onchange="checkValue(this,'alberoproc_hidden')" size="80" onkeydown="javascript:return searchAll(this,event)" />
				<init:autocompleter methodAjax="findAlberoproc.htm" idHidden="alberoproc_hidden" idInput="alberoproc_id" inputTitleKey="label.ricerca_intervento"/>
				<spring-form:errors path="filter.alberoproc" cssClass="error"/> 
				<spring-form:hidden id="alberoproc_hidden" path="filter.alberoproc.id.codice" />
			 --%>
				 <jsp:include page="../includes/searchAlberoProc.jsp">
					<jsp:param name="propertyPath" value="filter.alberoproc" />								
					<jsp:param name="pathPropertyDescription" value="filter.alberoproc.scDescrizione" />
					<jsp:param name="pathPropertyCode" value="filter.alberoproc.id.codice" />
					<jsp:param name="isSelectLeafDisable" value="true" />
					<jsp:param name="isSelectNodoPadre" value="true" />
				 </jsp:include>
			</td>
		</tr>
		<%--
		<tr>
			<td>
				<fmt:message key="form.registrazioniInOut.amministrazioni" />
			</td>
			<td>
				<spring-form:input id="amministrazioni_id" path="filter.amministrazioni.amministrazione" cssClass="searchbox" onchange="checkValue(this,'amministrazioni_hidden')" size="80" onkeydown="javascript:return searchAll(this,event)"/>
				<init:autocompleter methodAjax="findAmministrazioni.htm?tutteLeAmministrazioni=false" idHidden="amministrazioni_hidden" idInput="amministrazioni_id" inputTitleKey="label.ricerca_amministrazione"/>
				<spring-form:errors path="filter.amministrazioni" cssClass="error"/> 
				<spring-form:hidden id="amministrazioni_hidden" path="filter.amministrazioni.id.codice"  />
			</td>
		</tr>	
		--%>
		<tr>
			<td><fmt:message key="form.registrazioniFilter.importo.maggiore" /></td>
			<td>
				<spring-form:input id="importo_id" path="filter.importo" size="10" cssStyle="text-align:right;"/>
				<spring-form:errors path="filter.importo" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniFilter.daincassare.maggiore" /></td>
			<td>
				<spring-form:input id="saldo_id" path="filter.saldo" size="10" cssStyle="text-align:right;"/>
				<spring-form:errors path="filter.saldo" cssClass="error"/>
			</td>
		</tr>
		</table>
	
		<script type='text/javascript'>
		$('regCausali').focus();
		function searchAll(inputField,evt){
			 var charCode = (evt.which) ? evt.which : event.keyCode;
			 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
			   inputField.value='%';
			 }
		}
		function eliminaMercati(){
			if(document.getElementById('mercati_id').value == ''){
				document.getElementById('mercati_id').value = '';
				document.getElementById('mercati_hidden').value = '';
	
				removeOptionSelected("selectMercatiUso");
				removeOptionSelected("selectPosteggio");
				try{
					$('selectPosteggio').add(new Option('<fmt:message key="label.select.default" />', ''),  $('selectPosteggio').options[0]);
				}catch(e){ //in IE, try the below version instead of add()
					
				   $('selectPosteggio').add(new Option( '<fmt:message key="label.select.default" />',''));
			  	}
			}

			}
		
		function assegnaPosteggio(){
			removeOptionSelected("selectPosteggio");
			try{
				$('selectPosteggio').add(new Option('<fmt:message key="label.select.default" />', ''),  $('selectPosteggio').options[0]);
			}catch(e){ //in IE, try the below version instead of add()
				
			   $('selectPosteggio').add(new Option( '<fmt:message key="label.select.default" />',''));
		  	}
			var idMercato=document.getElementById("mercati_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findPosteggioMercato.htm', {
				  method: 'post',
				  parameters: {code: idMercato, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("mercatiPosteggio").appear();
					  var opts=response.split(",");
					  for(var i=0;i<((opts.length)-1); i++ ){
						  var j=i+1;
						  try{
						  	$('selectPosteggio').add(new Option( opts[j], opts[i]),  $('selectPosteggio').options[i]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectPosteggio').add(new Option( opts[j], opts[i]));
						  }
						  i++;
					  }
				    },
				  onFailure: function(){  }
								  
				  });
												
			}
		function mercatiUsoDisplay(){
				removeOptionSelected("selectMercatiUso");
				var idMercato=document.getElementById("mercati_hidden").value;
				new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiUso.htm', {
					  method: 'post',
					  parameters: {code: idMercato, limit: 12},
					  onSuccess: function(transport){
						  var response = transport.responseText;
						  var opts=response.split(",");
						  for(var i=0;i<((opts.length)-1); i++ ){
							  var j=i+1;
							  try{
							  	$('selectMercatiUso').add(new Option( opts[j], opts[i]),  $('selectMercatiUso').options[i]);
							  }
							  catch(e){ //in IE, try the below version instead of add()
								  $('selectMercatiUso').add(new Option( opts[j], opts[i]));
							  }
							  i++;
						  }
					    },
					  onFailure: function(){  }
									  
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
		function goToScadenzeList(){
			var goToUrl = "../registrazioniinout/listScadenze.htm?1=1";
			goToUrl = escape(goToUrl);
			doSubmit('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'',document.inviodati,'GET',true);
						
		}
		</script>
	    </spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:goToScadenzeList()"><fmt:message key="button.search" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				
		</ul>
	</div>
</body>
</html>