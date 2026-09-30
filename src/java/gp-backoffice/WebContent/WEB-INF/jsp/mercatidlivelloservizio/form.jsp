<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.mercatidlivelloservizio.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.mercatidlivelloservizio.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.NEW}">
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatidlivelloservizio/create" />
	</jsp:include>
	</c:if>
	<c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.VIEW}">
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatidlivelloservizio/view" />
	</jsp:include>
	</c:if>
	<div id="subcontent">
		<spring-form:form commandName="mercatidlivelloservizio" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercatidlivelloservizio" />
		    </jsp:include>
		    
		    <table border="0" width="100%">
	             
	             <c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.VIEW}">
				 
				  <tr>
					<td width="30%">
						<fmt:message key="label.mercati_uso" />
					</td>
					<td>
						<spring-form:input path="mercatiUso.descrizione" size="70"  />
					</td>
				 </tr>
				 <tr>
					<td width="30%">
						<fmt:message key="label.tipologia_livello_servizio" />
					</td>
					<td>
						<spring-form:input path="entity.mercatiLivelloServizio.descrizione" size="70"  /> 
						<c:if test="${!servizioattivo}">
							<fmt:message key="help.label.mercatiLivelloServizio_non_attivo" />
						</c:if>
						
					</td>
				 </tr>
				 
				 </c:if>
				 
	             <c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.NEW}">
	             <tr>
	             
	             
					<td width="30%">
						<fmt:message key="label.mercati_uso" />
					</td>
					<td>
					    
						<script type="text/javascript">
							function filtermercato(element, entry) { 
								return entry + "&codiceMercato=${codicemercato}";
							}
						</script>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="mercatiUso" />
							<jsp:param name="propertyPath" value="mercatiUso" />
							<jsp:param name="pathPropertyDescription" value="mercatiUso.descrizione" />
							<jsp:param name="pathPropertyCode" value="mercatiUso.id.codice" />
							<jsp:param name="autocompleterAjax" value="findMercatiUsoAndMercato.htm" />	
							<jsp:param name="ajaxCallBack" value="filtermercato"/>						
							<jsp:param name="titleKey" value="label.ricerca_livello_servizio" />
						</jsp:include>
					</td>
				</tr>
	            
	            <tr>
					<td>
						<fmt:message key="label.tipologia_livello_servizio" />
					</td>
					<td>
						<script type="text/javascript">
							function filteruso(element, entry) { 
								return entry + "&codiceUso=" + document.getElementById("mercatiUso_hidden").value;
							}
							
							function setRangeDate(inputField,listItem){
								var a = listItem.id;
								document.getElementById('mercatiLivelloServizio_id').value = inputField.value;
								document.getElementById('mercatiLivelloServizio_hidden').value = a;
								changeValue(a);
							}
							
						</script>
					<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="mercatiLivelloServizio" />
							<jsp:param name="propertyPath" value="entity.mercatiLivelloServizio" />
							<jsp:param name="pathPropertyDescription" value="entity.mercatiLivelloServizio.descrizione" />
							<jsp:param name="pathPropertyCode" value="entity.mercatiLivelloServizio.id.codice" />
							<jsp:param name="autocompleterAjax" value="findMercatiLivelloServizio.htm" />
							<jsp:param name="ajaxCallBack" value="filteruso"/>
							<jsp:param name="afterUpdateElement" value="setRangeDate"/>
							<jsp:param name="titleKey" value="label.ricerca_livello_servizio" />
						</jsp:include>
						<spring-form:errors path="entity.mercatiLivelloServizio.descrizione" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				 
				<tr>
					<td>
						<fmt:message key="label.usa_mq_posteggio" />
					</td>
					<td>
						<spring-form:checkbox id="usaMqPosteggio_id" path="entity.usaMqPosteggio" />
						<fmt:message key="help.label.usa_mq_posteggio" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.fattore_moltiplicativo" />
					</td>
					<td>
						<spring-form:input id="fattoreMoltiplicativo_id" path="entity.fattoreMoltiplicativo" size="6" onblur="checkNumberValue(this);" />
						<spring-form:errors path="entity.fattoreMoltiplicativo" cssClass="error"/>
					</td>
				</tr>
	            
				<tr>
					<td>
						<fmt:message key="label.data_inizio_validita" />
					</td>
					<td>
						<spring-form:input id="data_id" path="entity.dataInizio" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
						<spring-form:errors path="entity.dataInizio" cssClass="error"/>
					
						<fmt:message key="label.data_fine_validita" />
						<spring-form:input id="datafine_id" path="entity.dataFine" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="datafine_id" textKey="label.calendar"/>
						<spring-form:errors path="entity.dataFine" cssClass="error"/>
						
						<fmt:message key="help.mercatidlivelloservizio.data_inzio_validita" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;
					</td>
					<td>
						Range date ammesso: <label id="id_range"/>
					</td>
				</tr>
			</table>
			
			
			
		</spring-form:form>
	</div>
	
	<script type="text/javascript">
				
				jQuery(document).ready(function(){
					
					changeValue(${mercatidlivelloservizio.entity.mercatiLivelloServizio.id.codice})
						
					
					});
				
	            
              	function changeValue(codiceserv) {
              		if(codiceserv!=null)
              		{
       				jQuery.ajax({
             			  url: '${pageContext.request.contextPath}/mercatidlivelloservizio/ajaxRangeDate.htm?codiceservizio='+codiceserv,
             			  context: document.body,
             			  cache: false,					  
             			  dataType: "json",
             			  method: 'post',
             			  	  success: function(data){
             				  if(data){
             					var da=data.da;
             					var a;
             					if(data.a!="")
             					{a= data.a}
             					else{a = '-';}
             					jQuery("#id_range").html("dal "+ da +" al " +a).css("font-weight","Bold");
             					
	             			  }					  
             			  }
             			  
             		});
              	   }
                 }

       				
	    </script>
	
	
	
	<div id="functions">
		<ul>
			<c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.NEW}">
				<li>
				<c:if test="${empty mercatidlivelloservizio.listacodici}">
					<a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
				</c:if>
				<c:if test="${not empty mercatidlivelloservizio.listacodici}">
					<a href="javascript:doSubmit('insertLivelloserviziPosteggi.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
				</c:if>
				</li>
				
				
				
			</c:if>
			<c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.VIEW}">
				<c:if test="${servizioattivo}">
					<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				</c:if>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<br />
	
	 <c:if test="${empty mercatidlivelloservizio.listacodici && not empty mercatiDLivelloServizioHelpers}">
		<c:forEach items="${mercatiDLivelloServizioHelpers}" var="mercatidservizio" varStatus="a">
		    
			<div class="jmesa">
					<div class=titoloTabella><fmt:message key="label.giorno" />: <b>${mercatidservizio.mercatiUso.descrizione}</b>
					</div>
					<table border="0"  cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td width="30%"><fmt:message key="label.descrizione" /></td>
								<td width="5%"><fmt:message key="label.usa_mq_posteggio" /></td>
								<td width="5%"><fmt:message key="label.fattore_moltiplicativo" /></td>
								<td width="5%"><fmt:message key="label.tariffa" /></td>
								<td width="5%"><fmt:message key="label.data_inizio" /></td>
								<td width="5%"><fmt:message key="label.data_fine" /></td>
								<c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.NEW}">
									<td width="5%"><fmt:message key="label.azioni" /></td>
								</c:if>
				            </tr>
						</thead>
						<tbody class="tbody">
						<%int l=1;%>
						<c:if test="${not empty mercatidservizio.mercatiDLivelloServizios}">
						<c:forEach items="${mercatidservizio.mercatiDLivelloServizios}" var="serv_var">
						<tr class="<%=(l%2)==0?"odd":"even"%>">
						      <td>${serv_var.mercatiLivelloServizio.descrizione}</td>
						      
						      <td align="center">
    		                    <c:if test="${serv_var.usaMqPosteggio eq false}"><fmt:message key="label.no" /></c:if>
								<c:if test="${serv_var.usaMqPosteggio eq true}"><fmt:message key="label.si" /></c:if>
			              	  </td>
    		                  <td><fmt:formatNumber value="${serv_var.fattoreMoltiplicativo}" minFractionDigits="2"></fmt:formatNumber></td>
						      <td><fmt:formatNumber value="${serv_var.mercatiLivelloServizio.tariffa}" minFractionDigits="2"></fmt:formatNumber></td>
    		                  <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${serv_var.dataInizio}"/></td>
    		                  <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${serv_var.dataFine}"/></td>
    		                  <c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.NEW}">
    		                  <td>
    		                  	<a class="dettaglioColumn" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatidlivelloservizio/view.htm?codice=${serv_var.id.codice}','')" title="<fmt:message key="label.edit.record" />${formula_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
								<%-- 
								<a class="eliminaRiga" href="javascript:doHref('deleteSingolaFormula.htm?codice=${formula_var.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />  " >
										<label><fmt:message key="label.elimina" /></label>
								--%>
    		                  </td>
    		                  </c:if>
			             </tr>
						<%l++; %>
						</c:forEach>
						</c:if>
						<c:if test="${empty mercatidservizio.mercatiDLivelloServizios}">
							<tr class="even">
								<c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.NEW}">
								<td colspan="7"><fmt:message key="html.statusbar.noResultsFound" /></td>
								</c:if>
								<c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.VIEW}">
								<td colspan="6"><fmt:message key="html.statusbar.noResultsFound" /></td>
								</c:if>
							</tr>
						</c:if>
						
						</tbody>
					</table>
					
					</div>
		</c:forEach>
		
		</c:if>
		
	
	
	
	    
	    
	   
	
</body>
</html>