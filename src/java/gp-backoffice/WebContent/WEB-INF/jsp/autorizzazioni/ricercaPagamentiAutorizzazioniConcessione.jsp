<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.autorizzazioni.ricerca.title" /></title>
<style type="text/css">
	.alert{
		position: relative;
	  	padding: 1.5rem;
	    padding-left: 1.5rem;
		margin-bottom: 1rem;
		border: 1px solid var(--form-element-border-color);;
	    border-left-width: 1px;
	    border-left-style: solid;
	    border-left-color: rgb(93, 112, 131);
		border-left-color: #5d7083;
		border-left-style: solid;
		border-left-width: 1px;
		padding-left: 4em;
		background-color: #fff;
		border-radius: 0;
		color: #1a1a1a;
	
	}
	.alert-danger{
		background-position: 20px 16px;
  		background-repeat: no-repeat;
  		background-size: 32px 32px;
  		border-left: 8px solid hsl(350,60%,50%);
	
	}
	.danger{
		color: #d9534f;	  	
	}

</style>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.autorizzazioni.ricerca.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search" />
	</jsp:include>
	<div id="subcontent">
		<div id="error_hidden" class="alert alert-danger" style="display:none">
			<i class="fa fa-exclamation-circle fa-lg danger" aria-hidden="true"></i>
		</div>
		<div class="vbg-form">
			<fieldset>
				<legend><fmt:message key="label.autorizzazioni.ricerca.title"/></legend>
				<div class="form-group">
					<label><fmt:message key="label.autorizzazioni.ricerca.numero" /></label>
					<input id="concessioneNumeroAutorizzazione_id" name="autoriz_numero" size="10" />							
				</div> 
				
				<div class="form-group">
					<label><fmt:message key="label.autorizzazioni.ricerca.comune" /></label>
					<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="comune"/>		
					<jsp:param name="propertyPath" value="comune" />				
					<jsp:param name="pathPropertyDescription" value="comune.descrizioneEstesa" />
					<jsp:param name="pathPropertyCode" value="comune.codicecomune" />
					<jsp:param name="autocompleterAjax" value="findEnteautorizzazione.htm" />
					<jsp:param name="titleKey" value="label.ricerca_comune" />
					<jsp:param name="id_help" value="help_comuni" />
				</jsp:include>
				</div>
				<div id="functions">
					<ul>
						<li><a href="javascript:cerca();"><fmt:message key="button.search" /></a></li>
						<li><a href="javascript:doHref('../mercati/list.htm','');"><fmt:message key="button.back" /></a></li>
					</ul>
				</div>
			</fieldset>
			
		</div>
	</div>
<script type="text/javascript">
	async function cerca(){
		const numero = document.getElementById('concessioneNumeroAutorizzazione_id').value;
		const codiceComune = document.getElementById('comune_hidden').value;
		try
		{
			const postParams = { request: { numeroAutorizzazione: numero,codiceComune: codiceComune } };
			window.vbg.mostraModalCaricamento();
			
        	const response = await fetch('../autorizzazioni/findAutorizzazioniConcessione.htm?numeroAutorizzazione='+numero+'&codiceComune='+codiceComune, {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
        	});
        	
        	const jsResponse = await response.json();
        	if (jsResponse.response === "ok") {
        		
				document.location.href='../autorizzazioni/listPagamentiAutorizzazioniConcessione.htm?numeroAutorizzazione='+numero+'&codiceComune='+codiceComune;
			} else {
				const error = document.getElementById('error_hidden');
				error.innerHTML = jsResponse.response;
				error.style.display ='';
			}
        	window.vbg.nascondiModalCaricamento();

			return null;
		} 
		catch(error) {                                    
            alert(error);
        }
		finally{                                  
            
        }
		
		
	}

</script>
</body>
</html>