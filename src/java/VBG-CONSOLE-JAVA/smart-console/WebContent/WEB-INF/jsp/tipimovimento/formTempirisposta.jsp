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
   <!-- Variabile settate per sapere quanti sono il numero di record sulla tabella
        per l'utilizzo dei javascript che assegnano a tutti i campi di tempo di attesa
        passato e per il javascript che selezione e deseleziona tutti i checkbox sui tempi ti attesa  -->
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
		 <c:if test="${not empty tempirispostaCommand.tempirispostaHelpers}">
		    <div class="jmesa">
				<table border="1" class="table" >
					<thead>
						<tr class="header">
							<td><fmt:message key="label.procedura" /></td>
							<td align="right">
							    <input id="tempi_risposta_id" style="text-align:right;"   type="text"  size="5" title="<fmt:message key="tipimovimento.label.scegli_tempi_risposta" />"/>
							    <a href="javascript:assegna(${numProcedure},${numAmministrazioni});" ><img src="../images/add.gif" alt="" title="<fmt:message key="tipimovimento.label.assegna_tempi_risposta" />"/></a>
							     <input id="checkbox_select_all_id" type="checkbox" onclick="javascript:selezionaAndDeselezionaTutti(${numProcedure},${numAmministrazioni});" title="<fmt:message key="tipimovimento.label.seleziona_deseleziona" />"/>
							</td>
						</tr>
					</thead>	
			       
			        <c:forEach items="${tempirispostaCommand.tempirispostaHelpers}" var="tempirispostahelper" varStatus="a">
				    <tr>
				        <td>${tempirispostahelper.tipiprocedure.procedura}</td>  
				   		<td>
					    <!--TABELLA CHE STAMPA LA?MMINISTRAZIONE E I TEMPI DI ATTESA PER OGNUNA -->
					    <!-- START -->
					    	<table border="1"  width="100%">
							   <thead>
							   <tr class="header">
							       <td><fmt:message key="label.amministrazione"/></td>							
							       <td colspan="2"><fmt:message key="label.attesa"/></td>
							   </tr>
							   </thead>
							   <c:forEach items="${tempirispostahelper.amministrazionis}" var="amministrazioneHelper" varStatus="b" >
					             <%
					                int i = 0;
					             %>
						       <tr class="<%=(i % 2) == 0 ? "odd" : "even"%>">
								   <td width="100%">${amministrazioneHelper.amministrazioni.amministrazione}</td> 
									<td>
									 	<spring:bind  path="tempirispostaHelpers[${a.index}].amministrazionis[${b.index}].attesa">
							            	<input id="tempirisposta${a.index}${b.index}" style="text-align:right;"   type="text" name="${status.expression}" value="${status.value}" size="6"/>						 
						             	   	<spring-form:errors path="tempirispostaHelpers[${a.index}].amministrazionis[${b.index}].attesa" cssClass="error" /> 
						             	</spring:bind>
						            </td>
						            <td>
						   				<spring:bind path="tempirispostaHelpers[${a.index}].amministrazionis[${b.index}].calcoladainizioistanza">
						   					<spring-form:checkbox id="checkbox_tempiattesa_id${a.index}${b.index}" path="tempirispostaHelpers[${a.index}].amministrazionis[${b.index}].calcoladainizioistanza"  />
						   					<script type='text/javascript'>
						   						$('checkbox_tempiattesa_id${a.index}${b.index}').title='<fmt:message key="tipimovimento.label.tempo_di_risposta"/>';
						   					</script>	
						  				</spring:bind>
						  			</td>	
						  			<%
							  			i++;
							  		%>
							   </tr>
						       </c:forEach>
							   
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
			for(i=0 ;i<numprocedure ;i++){
				for(j=0;j<numamministrazioni;j++)
				{
					if($('tempirisposta'+i+j).value==''){
						$('tempirisposta'+i+j).value=$('tempi_risposta_id').value;
					}
				}
			}
			$('tempi_risposta_id').value='';
		}

		function selezionaAndDeselezionaTutti(numprocedure,numamministrazioni)
		{	
			if($('checkbox_select_all_id').checked){
				for(i=0 ;i<numprocedure ;i++){
					for(j=0;j<numamministrazioni;j++)
					{
						$('checkbox_tempiattesa_id'+i+j).checked=true;
				    }
				}    
			}else{
				for(i=0 ;i<numprocedure ;i++){
					for(j=0;j<numamministrazioni;j++)
					{
						$('checkbox_tempiattesa_id'+i+j).checked=false;
				    }
				}
			}
		}
	</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		 
		<ul>
			<c:if test="${not empty tempirispostaCommand.tempirispostaHelpers}">
				<li><a href="javascript:doSubmit('insertTempirispostaContromovimento.htm','',document.inviodati)"><fmt:message key="button.save" /></a></li>
				<li><a href="javascript:doSubmit('deleteTempirispostaContromovimento.htm','<fmt:message key="javascript.confirm.delete"/>',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('view.htm?codice=${tempirispostaCommand.entity.tipimovimento.id.tipomovimento}&software=${tempirispostaCommand.entity.tipimovimento.software.codice}','')"><fmt:message key="button.back" /></a></li>
		   
		</ul>
	</div>
</body>
</html>