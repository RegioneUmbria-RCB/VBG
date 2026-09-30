<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="alberoproc.label.alberoproctipisoggetto" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="alberoproc.label.alberoproctipisoggetto" />
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
		<spring-form:form commandName="alberoprocTipisoggetto" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocTipisoggetto" />
		    </jsp:include>
		    <spring-form:hidden path="alberoproc.id.codice" />
			<table>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
					<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="tipisoggetto" />
							<jsp:param name="propertyPath" value="tipisoggetto" />
							<jsp:param name="pathPropertyDescription" value="tipisoggetto.tiposoggetto" />
							<jsp:param name="pathPropertyCode" value="tipisoggetto.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipisoggetto.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipisoggetto" />
							<jsp:param name="afterUpdateElement" value="mostraDescrizioneEstesa" />
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione_estesa" />
					</td>
					<td>
						<spring:bind path="overrideDescrizione">
							<input type="text" id="overrideDescrizione_id" name="${status.expression}" value="${status.value}" size="100" maxlength="400">
						</spring:bind> 						
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.obbligatorio" />
					</td>
					<td>
						 <spring-form:checkbox id="obbligatorio_id" path="obbligatorio" value="1" />				
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.occorrenze_max" />
					</td>
					<td>					
						<spring:bind path="occorrenzeMax">
							<input 
								inputmode="numeric"
							    oninput="this.value = this.value.replace(/\D+/g, '')"  
							    maxlength="2" size="3"
								name="${status.expression}" value="${status.value}">
						</spring:bind> 
						<fmt:message key="label.occorrenze_max.help" />
					</td>
				</tr>				
			</table>
			
		</spring-form:form>
	</div>
	<script type="text/javascript">
	
	async function mostraDescrizioneEstesa(inputField,listItem){
		
		 var a = listItem.id;
		 document.getElementById('tipisoggetto_id').value = inputField.value;
		 document.getElementById('tipisoggetto_hidden').value = a;
		 mostraDescrizione(a);
	}
	
	const ovDescEl = document.getElementById('overrideDescrizione_id');
	
	async function mostraDescrizione(id){
		
				vbg.mostraModalCaricamento();
				
	    		const data = new URLSearchParams();
				data.append('id', id);
												
				const response = await fetch("${pageContext.request.contextPath}/tipisoggetto/ajaxDescrizioneEstesa.htm", {
	                method: "POST",
	                cache: "no-cache",
	                body: data
				});
										
				let desc = await response.text();
			 	vbg.nascondiModalCaricamento();
			 	if(desc != ''){
				 	if(ovDescEl.value===''){
				 		ovDescEl.value = desc;
				 	}else{
				 		if(confirm('Il campo <fmt:message key="label.descrizione_estesa" /> contiene già un valore sovrascriverlo con quello impostato nel tipo soggetto?')){
				 			ovDescEl.value = desc;	
				 		}
				 	}
			 	}
			}
		
	
	</script>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertTipisoggetto.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=${alberoproc.id.codice}#artipisoggetto_anchor','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>