<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commedilizietipopareri.id.codice==null}">
			<fmt:message key="commedilizietipopareri.label.nuovo_commedilizietipopareri.title" />
		</c:if> 
		<c:if test="${commedilizietipopareri.id.codice!=null}">
			<fmt:message key="commedilizietipopareri.label.dettaglio_commedilizietipopareri.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commedilizietipopareri.id.codice==null}">
			<fmt:message key="commedilizietipopareri.label.nuovo_commedilizietipopareri.title" />
		</c:if> 
		<c:if test="${commedilizietipopareri.id.codice!=null}">
			<fmt:message key="commedilizietipopareri.label.dettaglio_commedilizietipopareri.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
		
<script type="text/javascript">
	function tuttiSw(){
		if($('id_flag').checked){
		    $('id1').style.display="inline";
		    $('id2').style.display="none";
		}else
		{
			$('id1').style.display="none";
			$('id2').style.display="inline";
		}
		
	}	
</script>
   <%
      String  mittente="display:appear;";
      String  destinatario="display:appear;";
      String  swSettato="display:none;";
      String  swTT="display:inline;";
    %>
	<div id="subcontent">
		<spring-form:form commandName="commedilizietipopareri" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizietipopareri" />
		    </jsp:include>
		    
		    <div class="vbg-form">
		    	<fieldset>
		    		<legend>Parere</legend>
		    		<div class="form-group">
			    		<label><fmt:message key="label.descrizione" /></label>
			    		<spring-form:input id="descrizione_id" path="descrizione" size="70" cssStyle="padding-left: 24px;" />
						<spring-form:errors path="descrizione" cssClass="error"/>
			    	</div>
			    	<div class="form-group">
			    		<label><fmt:message key="commedilizietipopareri.label.esito" /></label>
			    		<spring-form:select path="esito">
							<spring-form:option value=""></spring-form:option>
							<spring-form:option value="true"><fmt:message key="label.positivo" /></spring-form:option>
							<spring-form:option value="false"><fmt:message key="label.negativo" /></spring-form:option>
						</spring-form:select>
						<spring-form:errors path="esito" cssClass="error"/>
			    	</div>
		    	</fieldset>
		    	
		    	

		    	
		    	
		    </div>
			<script type='text/javascript'>
				
				function convalida(){
					
					return true;
				}
			</script>	
		</spring-form:form>
		
	
		
	</div>
	<div class="form-group">
		<div class="form-button">
		
			<c:if test="${commedilizietipopareri.id.codice==null}">
				<a class="btn btn-primary"  href="javascript:if(convalida()){doSubmit('insert.htm','',document.inviodati)}"><fmt:message key="button.insert" /></a>
			</c:if>
			<c:if test="${commedilizietipopareri.id.codice!=null}">
				<a class="btn btn-primary"  href="javascript:if(convalida()){doSubmit('update.htm','',document.inviodati)}"><fmt:message key="button.update" /></a>
				<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>
	</div>
	
	
	<c:if test="${commedilizietipopareri.id.codice!=null}">		

		<div id="dettaglioSchede" style="padding-top:20px" class="vbg-form">
		<fieldset>
			<legend><fmt:message key="label.tipomovimento" /></legend>
			<table width="100%">       
				<tr>
					<td>		     
						<input id="movimento_id" name="movimento" class="searchbox" size="80"
							 onkeydown="return searchAll(this,event)" /> *
						<init:autocompleter 
							methodAjax="findMovimentiTuttiSoftware.htm" 
								idHidden="movimento_id_hidden" 
								idInput="movimento_id" inputTitleKey="" minChars="1" />
						<input type="hidden" id="movimento_id_hidden" name="movimento_id_hidden" />    	
			 		</td>
				</tr>
				<tr>
					<td>		     
						<input id="software_id" name="software" class="searchbox" size="80"
							 onkeydown="return searchAll(this,event)" /> *
						<init:autocompleter 
							methodAjax="findSoftware.htm" 
								 idHidden="software_id_hidden" 
								idInput="software_id" inputTitleKey="" minChars="1" />
						<input type="hidden" id="software_id_hidden" name="software_id_hidden" />    	
			 		</td>
				</tr>				
				<tr>
					<td><div id="messaggioErrore" class="error_header"></div></td>
				</tr>
			</table>	
			<div class="form-group">
				<div class="form-button">
					<a class="btn btn-primary"  href="javascript:nuovoMovimento()"><fmt:message key="button.insert" /></a>
				</div>	
			</div>				
			<div id="dettaglioMovimenti">
				
			</div>
		</div>	
		</fieldset>
</c:if>
	
	
	</div>
<script type="text/javascript">


function validaInserimento(){
	
	assegnaContenutoHTML('messaggioErrore', '');
	
	if(document.getElementById('movimento_id_hidden').value==''){

		assegnaContenutoHTML('messaggioErrore', '<fmt:message key="label.tipomovimento" />: <fmt:message key="field.required" />');
		return false;
	}
	
	if(document.getElementById('software_id_hidden').value==''){
		
		assegnaContenutoHTML('messaggioErrore', '<fmt:message key="label.software" />: <fmt:message key="field.required" />');
		return false;
	}
	return true;
}

function svuotaCampi(){
	
	
	document.getElementById('movimento_id').value='';
	document.getElementById('software_id').value='';	
	document.getElementById('movimento_id_hidden').value='';
	document.getElementById('software_id_hidden').value='';
}
	const eliminaMovimento = async (elemento) => { 
		
		
		if(confirm('<fmt:message key="javascript.confirm.delete" />')){
			const formData = new FormData();
			
	        formData.append('codice', ${commedilizietipopareri.id.codice});
	        formData.append('software', elemento.dataset.software);
	       
	        spinnerOn();
			let response = await fetch("ajaxEliminaMovimento.htm" , {
	            method: "POST",
	            cache: "no-cache",
	            body: formData
	        });
			
			let ris = await response.json();
			spinnerOff();
			if(ris.esito){
				caricaMovimenti();	
			}else{
				assegnaContenutoHTML('messaggioErrore', ris.errore);
			}
		}
	}


	const nuovoMovimento = async () => {
		
		
		if(validaInserimento()){
			spinnerOn();
			const formData = new FormData();
		
	        formData.append('codice', ${commedilizietipopareri.id.codice});
	        formData.append('software', document.getElementById('software_id_hidden').value);
	        formData.append('tipomovimento', document.getElementById('movimento_id_hidden').value);
	        
			let response = await fetch("ajaxAssegnaMovimento.htm" , {
	            method: "POST",
	            cache: "no-cache",
	            body: formData
	        });
			
			let ris = await response.json();
			spinnerOff();
			
			svuotaCampi();
			
			if(ris.esito){
				caricaMovimenti();	
			}else{
				assegnaContenutoHTML('messaggioErrore', ris.errore);
			}
			
		}
	}

	const caricaMovimenti = async () => {

		spinnerOn();
                        const response = await fetch("ajaxFindMovimenti.htm?codice=${commedilizietipopareri.id.codice}", {
                            method: "GET",
                            cache: "no-cache",
                            headers: {
                                'Content-Type': 'application/json'
                            }
                        });
                        let ris = await response.json();
                        console.log(ris);

			svuotaContenutoMovimenti();
			let contenutoDiv = `
							<table  class="vbg-table">
							<thead>
							<tr>
								<th><fmt:message key="label.software" /></th>
								<th><fmt:message key="label.tipomovimento" /></th>
								<th><fmt:message key="label.azioni" /></th>
							</tr>
							</thead><tbody>		 			
							   `;

		

			ris.each(conf =>{ 	
								contenutoDiv +=  `
									<tr>
										<td>\${conf.software} (\${conf.codice_software})</div>
										<td>\${conf.descrizione_movimento} (\${conf.tipo_movimento})</div>
										<td><a href="javascript: void 0" onclick="eliminaMovimento(this)" 
												data-software="\${conf.codice_software}"												
												aria-hidden="true" 
												class="form-group fa fa-trash"><fmt:message key="label.elimina" /></a></td>
									</tr>`;

				});
		contenutoDiv +=  `</tbody></table>`;

		
		spinnerOff();
		assegnaContenutoHTML('dettaglioMovimenti', contenutoDiv);
	}

function assegnaContenutoHTML(divId, contenutoHTML) {
	
	document.getElementById(divId).innerHTML = contenutoHTML;
}	

function svuotaContenutoMovimenti(){

	assegnaContenutoHTML('dettaglioMovimenti', '');

}
function spinnerOn(){
	 vbg.mostraModalCaricamento();
}
function spinnerOff(){
	 vbg.nascondiModalCaricamento();
	
}

vbg.ready(() => {


	<c:if test="${commedilizietipopareri.id.codice!=null}">		   
		caricaMovimenti();
	</c:if>

	});


</script>	
</body>
</html>