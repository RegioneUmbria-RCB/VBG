<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.domain.web.TempirispostaCommand"%>
<%@page import="java.util.List"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.TempirispostaHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.AmministrazioniHelper"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="tipimovimento.label.tempi_di_risposta.title" />
	</title>
</head>
<body>
   <%-- Variabile settate per sapere quanti sono il numero di record sulla tabella
        per l'utilizzo dei javascript che assegnano a tutti i campi di tempo di attesa
        passato e per il javascript che selezione e deseleziona tutti i checkbox sui tempi ti attesa  --%>
   <%
	    TempirispostaCommand tempirispostaCommand = (TempirispostaCommand) request.getAttribute("tempirispostaCommand");
   	    List<TempirispostaHelper> list=new ArrayList<TempirispostaHelper>();
	    Integer numeroprocedure =new Integer(0);
	    Integer numAmministrazioniTotali =new Integer(0);
	    if(!tempirispostaCommand.getTempirispostaHelpers().isEmpty())
	    {
			 list=tempirispostaCommand.getTempirispostaHelpers();	
	   		 numeroprocedure = tempirispostaCommand.getTempirispostaHelpers().size();
	   		 numAmministrazioniTotali = list.get(0).getAmministrazionis().size();
	    }
		
		pageContext.setAttribute("numProcedure", numeroprocedure);
		pageContext.setAttribute("numAmministrazioni", numAmministrazioniTotali);
	%>	    
	<span class="titoloPagina">
			<fmt:message key="tipimovimento.label.tempi_di_risposta.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
		<div class="etichetta">
	      	<div><fmt:message key="tipimovimento.label.movimento"/>:</div>
	        <div><fmt:message key="tipimovimento.label.codice_tipocontromovimento"/>:</div>
	    </div>        
	    <div class="parametro">
	      	<div>${tempirispostaCommand.entity.tipimovimento.movimento}</div>
	        <div>${tempirispostaCommand.entity.tipicontromovimento.movimento}</div>
        </div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="tempirispostaCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tempirispostaCommand" />
		    </jsp:include>
		    
		 <input type="hidden" name="tipomovimento" value="${tempirispostaCommand.entity.tipimovimento.id.tipomovimento}" />
		 <input type="hidden" name="tipocontromovimento" value="${tempirispostaCommand.entity.tipicontromovimento.id.tipomovimento}" />
		    
		 <c:if test="${not empty tempirispostaCommand.tempirispostaHelpers}">
		    <div class="jmesa">
				<table border="1" class="table" >
					<thead>
						<tr class="header">
							<td><fmt:message key="label.procedura" /></td>
							<td align="right">
							    <fmt:message key="label.assegna" />&nbsp;<input id="tempi_risposta_id" style="text-align:right;"   type="text"  size="5" title="<fmt:message key="tipimovimento.label.scegli_tempi_risposta" />"/>
							    <select id="assegna-sovrascrivi" name="compatibility" title="Indicare [<fmt:message key='label.si' />] per sovrascrivere tutte le configurazioni fatte, [<fmt:message key='label.no' />] per assegnare solamente i valori non presenti">
							    	<option value=""><fmt:message key="label.select.default" /></option>
							    	<option value="1"><fmt:message key="label.si" /></option>
							    	<option value="0"><fmt:message key="label.no" /></option>
							    </select>
							    <a class="vbg-btn btn-aggiungi" href="javascript:assegna(${numProcedure},${numAmministrazioni});" title="<fmt:message key="tipimovimento.label.assegna_tempi_risposta" />"></a>							    
							    &nbsp;&nbsp;&nbsp;&nbsp;
							    &nbsp;&nbsp;&nbsp;&nbsp;
							    &nbsp;&nbsp;&nbsp;&nbsp;
							    <input id="checkbox_select_all_id" type="checkbox" onclick="javascript:selezionaAndDeselezionaTutti(${numProcedure},${numAmministrazioni});" title="<fmt:message key="tipimovimento.label.seleziona_deseleziona" />"/>
							</td>
						</tr>
					</thead>	
			       
			        <c:forEach items="${tempirispostaCommand.tempirispostaHelpers}" var="tempirispostahelper" varStatus="a">
				    <tr>
				        <td>${tempirispostahelper.tipiprocedure.procedura}</td>  
				   		<td><table  width="100%">
							   <thead>
							   <tr class="header">
							       <td  width="70%"><fmt:message key="label.amministrazione"/></td>							
							       <td  width="30%"colspan="2"><fmt:message key="label.attesa"/></td>
							   </tr>
							   </thead>
<c:forEach items="${tempirispostahelper.amministrazionis}" var="amministrazioneHelper" varStatus="b" ><tr><td>${amministrazioneHelper.amministrazioni.amministrazione}</td> 
<td>
<spring:bind  path="tempirispostaHelpers[${a.index}].amministrazionis[${b.index}].attesa">
<input class="assegna_tempi" style="text-align:right;" 
	data-codiceamministrazione="${amministrazioneHelper.amministrazioni.id.codice}" 
	data-codiceprocedura="${tempirispostahelper.tipiprocedure.id.codice}"
	type="text" name="a_${tempirispostahelper.tipiprocedure.id.codice}_${amministrazioneHelper.amministrazioni.id.codice}" value="${status.value}" size="6"/>
</spring:bind>
</td><td>
<spring:bind path="tempirispostaHelpers[${a.index}].amministrazionis[${b.index}].calcoladainizioistanza">						   					
<c:set var="checked"></c:set><c:if test="${status.value eq true }"><c:set var="checked"> checked="checked" </c:set></c:if>
<input type="checkbox" 
	id="c_${tempirispostahelper.tipiprocedure.id.codice}_${amministrazioneHelper.amministrazioni.id.codice}"
  class="assegna_tempi_chk" ${checked} name="c_${tempirispostahelper.tipiprocedure.id.codice}_${amministrazioneHelper.amministrazioni.id.codice}" title="<fmt:message key='tipimovimento.label.tempo_di_risposta'/>" />	
</spring:bind>
</td></tr></c:forEach>
						     </table>
						</td> 
			         </tr>
				     </c:forEach>
				</table>
			</div>
			</c:if>
	<script type='text/javascript'>
		function assegna(numprocedure,numamministrazioni)
		{
			let sovrascrivi = document.getElementById('assegna-sovrascrivi').value;
			if(sovrascrivi =='' ){
				alert('Attenzione! Selezionare se sovrascrivere tutte le configurazioni [Si] o aggiornare solo quelle vuote [No]');
				return;
			}
			let valoreTempiRisposta = document.getElementById('tempi_risposta_id').value;
			let det = document.querySelectorAll('.assegna_tempi');
			det.forEach((tempiRisposta)=>{
				if(tempiRisposta.value=='' || sovrascrivi =='1'){
					tempiRisposta.value = valoreTempiRisposta;
				}
			});	
			document.getElementById('tempi_risposta_id').value='';
		}
		
		function salvaTempiRisposta(){
			privSalvaTempiRisposta() ;
		}
		
		async function privSalvaTempiRisposta() {
			
			try{
					const url = "../tipimovimento/ajaxInsertTempirispostaContromovimento.htm";
					const pTipomovimento = "${tempirispostaCommand.entity.tipimovimento.id.tipomovimento}";
					const pTipocontromovimento = "${tempirispostaCommand.entity.tipicontromovimento.id.tipomovimento}";
					let det = document.querySelectorAll('.assegna_tempi');
					
					console.log("size: "+det.length);
					let postData  = { request: {tipomovimento: pTipomovimento, tipocontromovimento: pTipocontromovimento, tempi : [] } }; 
					det.forEach( tempiRispostaField =>{
						
						
						var tempo = {};
 						
						let codiceamministrazione = tempiRispostaField.getAttribute('data-codiceamministrazione');
						let codiceprocedura = tempiRispostaField.getAttribute('data-codiceprocedura');
						let calcolaDaInizioIstanza = document.getElementById("c_"+codiceprocedura+"_"+codiceamministrazione).checked;

						
						tempo['codiceamministrazione'] = codiceamministrazione;
						tempo['codiceprocedura'] = codiceprocedura;
						tempo['tempo'] =  tempiRispostaField.value;
						tempo['calcolaDaInizioIstanza']= calcolaDaInizioIstanza;
						postData.request.tempi.push(tempo);
				
					});						
					
					salvaTempoJson(url, postData);
					
			}finally{
				
				
				
			}
		}
		
		async function salvaTempoJson(url, postData){
			vbg.mostraModalCaricamento();
			const response = await fetch(url, {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postData)
        	});
        	
        	const jsResponse = await response.json();        	
        	document.location.reload();	
        	vbg.nascondiModalCaricamento();
		}
		
		function selezionaAndDeselezionaTutti(numprocedure,numamministrazioni)
		{	
			
			let checkAll =  document.getElementById('checkbox_select_all_id').checked;
			
			let det = document.querySelectorAll('.assegna_tempi_chk');
			det.forEach((tempiRispostaChk)=>{
				tempiRispostaChk.checked=checkAll;
			});	
			document.getElementById('tempi_risposta_id').value='';
		}
	</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		 
		<ul>
			<c:if test="${not empty tempirispostaCommand.tempirispostaHelpers}">
				<li><a href="javascript:salvaTempiRisposta();"><fmt:message key="button.save" /></a></li>
				<li><a href="javascript:doSubmit('deleteTempirispostaContromovimento.htm','<fmt:message key="javascript.confirm.delete"/>',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('view.htm?codice=${tempirispostaCommand.entity.tipimovimento.id.tipomovimento}&software=${tempirispostaCommand.entity.tipimovimento.software.codice}','')"><fmt:message key="button.back" /></a></li>
		   
		</ul>
	</div>
</body>
</html>