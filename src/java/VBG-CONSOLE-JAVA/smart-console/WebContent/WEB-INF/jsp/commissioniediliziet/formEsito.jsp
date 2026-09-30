<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.esito_commissione" />
	</title>
</head>
<body>
     

	<span class="titoloPagina">
			<fmt:message key="label.esito_commissione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	 <%
        int size =(Integer)request.getAttribute("numeroPresenti");
        pageContext.setAttribute("sizePresenti",size);
     %>
	<jsp:include page="../includes/history.jsp">
   		<jsp:param name="path" value="../commissioniediliziet/createEsitoCommissioniedilizieR" />
	</jsp:include>
    <c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${commissioniediliziet.commissioniedilizieR.movimento.istanza.id.codice}</c:param>
	</c:import>
	<br class="clear" />
    
	<div id="subcontent">
		<spring-form:form commandName="commissioniediliziet" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commissioniediliziet" />
		    </jsp:include>

			<div class="jmesa">
				<table border="0"  cellpadding="2" cellspacing="0" class="table">
			    <thead>
					<tr class="header">
						<td><fmt:message key="label.discussione_votazione" /></td>
						<td><fmt:message key="label.carica" /></td>
						<td><fmt:message key="label.presente" /></td>
						<td><fmt:message key="label.voto" /></td>
						<td>
							<select id="lista_id" onchange="cambiaTuttiVotazioni()">
								<option value="" label="Seleziona">Seleziona</option>
							    <c:forEach  items="${listVoti}" var="voti">
							    	<option id="option_id" value="${voti.id}" label="${voti.descrizione}">${voti.descrizione}</option>
							    </c:forEach>
							</select>
							<init:help idHelp="lista_id_help" textKey="help.commissioniediliziet.select_votazione" /> 
						</td>
				</tr>
			    <c:set scope="page" value="${fn:length(listVoti)}" var="sizeList"></c:set> 
			    <c:set scope="page" value="${fn:length(commissioniediliziet.listaAppello)}" var="sizePresenti"></c:set> 
			   
			    <script type='text/javascript'>
			      function cambiaTuttiVotazioni()
			      {  
			    	var indexSelect=$('lista_id').selectedIndex;
			  	    for(var i=0;i<${sizePresenti};i++){
						document.getElementById('select_id'+i).options[indexSelect].selected=true;
			  	    }  
			      }
			    </script>
			    
			    </thead>
			    <tbody class="tbody">				
			    <c:forEach items="${commissioniediliziet.listaAppello}" var="appello" varStatus="a">
			    	<tr>
			    		<td>
					    	${appello.commedilizieAppello.componente}
			    		</td>
			    		
				    	<td>
				    	  	${appello.commedilizieAppello.commedilizieCarica.descrizione}							
				    	</td>
				    	
				        <td>
					        <spring-form:checkbox id="check_presente${a.index}" path="listaAppello[${a.index}].presente"  onclick="javascript:isAllowedVoto('check_presente${a.index}','voto_id${a.index}')" />
						</td>
						<c:if test="${appello.commedilizieAppello.commedilizieCarica.dirittovoto == true}">
						<td colspan="2" id="voto_id${a.index}">
							 <spring-form:select id="select_id${a.index}" path="listaAppello[${a.index}].transientVoto">
								  <spring-form:option value="">Seleziona</spring-form:option>
								  <spring-form:options id="voto_lista_id${a.index}" items="${listVoti}" itemLabel="descrizione" itemValue="id"/>
							 </spring-form:select>
						</td>
						</c:if>
						 
			    </tr>
			    </c:forEach>
			    </tbody>
				
				
			</table>
			</div>
			
			<table width="100%" >
			    <tr class="titoloSezione" > 
			     	<td colspan="4"><fmt:message key="label.pareri" /></td>
			    </tr>
			   	<tr>
			    	<td><fmt:message key="label.tipologia_parere" /></td>
			    	<c:if test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice==null}">
			    	<td>
			    		 <spring-form:select path="commissioniedilizieR.commedilizieTipopareri.id.codice">
						 <spring-form:options items="${commissioniediliziet.listTipopareri}" itemLabel="descrizione" itemValue="id.codice"/>
						 </spring-form:select>
					</td>
					</c:if>
					<c:if test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice!=null}">
					<td>
						<spring-form:input path="commissioniedilizieR.commedilizieTipopareri.descrizione" readonly="true" size="30"/>
					</td>
					</c:if>
				</tr>
				<tr>
			    	<td><fmt:message key="label.template_pareri" /></td>
			    	<td>
						<select id="mailtipo_id" onchange="recuperaParere(this);">
						<option></option>
						<c:forEach items="${mailtipos}" var="mailtipi_var">
							<option label="${mailtipi_var.descrizione}" value="${mailtipi_var.id.codice}">${mailtipi_var.descrizione}</option>
						</c:forEach>
						</select>
					</td>
					
				</tr>
			   	<tr>
			    	<td><fmt:message key="label.parere" /></td>
			    	<c:if test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice==null}">
			    	<td>
				    	<spring-form:textarea id="parere_id" path="parere" cols="60" rows="8" />
				    	<spring-form:errors path="commissioniedilizieR.movimento.parere" cssClass="error"/>	
			    	</td>
			    	</c:if>
			    	<c:if test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice!=null}">
			    	<td>
			    		<spring-form:textarea id="parere_id" path="commissioniedilizieR.movimentoRientro.parere" cols="60" rows="8" />
			    		<spring-form:hidden path="parere"/>
				    	<spring-form:errors path="commissioniedilizieR.movimento.parere" cssClass="error"/>	
			    	</td>
			    	</c:if>
				</tr>
				
				
			</table>
			
			
		</spring-form:form>
	</div>
	<script type='text/javascript'>
		isVota(${sizePresenti});
		function recuperaParere(elem)
		{
			var code=elem[elem.selectedIndex].value;
			if(code!=''){
		 	new Ajax.Request('${pageContext.request.contextPath}/jsonmail/recuperaOggettoCorpo.htm?codiceistanza=${commissioniediliziet.commissioniedilizieR.movimento.istanza.id.codice}&codicemovimento=${commissioniediliziet.commissioniedilizieR.movimento.id.codice}', { 
			 	method:'post',
			 	parameters:{chiave_ricerca : code}, 
	  			onSuccess: function(transport){
	  			 	var json = transport.responseText.evalJSON();
	      			var mailtipo = json.mailtipo;
	      			//$('oggetto_id').value=mailtipo.oggetto;
	      			$('parere_id').value=mailtipo.corpo;
	    		},
	    		onFailure: function(transport){
	  				printResult(transport, "Errore durante il recupero dell'oggetto e del corpo dell'e-mail");
	    		}
			});
			}else{
				//$('oggetto_id').value='';
	     			$('parere_id').value='';
				}
		}
		function isVota(size)
		{
			
			for (i=0;i<size;i++)
			{
				if(document.getElementById('check_presente'+i).checked==false)
				{
					if($('voto_id'+i)!=null){
						$('voto_id'+i).style.display='none';
					}
				}
			}
		}
		function isAllowedVoto(obj,voto)
		{
			if($(voto)!=null)
			{
				if(document.getElementById(obj).checked==false)
				{
					$(voto).style.display='none';
				}else
				{
					$(voto).style.display='';
				}
			}
		}
	</script>
	
	<div id="functions">
		<ul>
			<c:if test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice==null}">
				<li><a href="javascript:doSubmit('insertEsitoCommissioniedilizieR.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice!=null}">
				<li><a href="javascript:doSubmit('insertEsitoCommissioniedilizieR.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteEsitoCommissioniedilizieR.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doSubmit('listCommissioniedilizieR.htm?codiceCommissione=${commissioniediliziet.commissioniedilizieR.commissioniedilizieT.id.codice}','',document.inviodati)"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>