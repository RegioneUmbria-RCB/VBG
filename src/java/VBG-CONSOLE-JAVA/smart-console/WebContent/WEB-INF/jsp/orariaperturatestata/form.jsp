<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${orariaperturatestata.displayMode==orariaperturatestata.displayConstants.NEW}">
			<fmt:message key="orariaperturatestata.label.nuovo_orariaperturatestata.title" />
		</c:if> 
		<c:if test="${orariaperturatestata.displayMode==orariaperturatestata.displayConstants.VIEW}">
			<fmt:message key="orariaperturatestata.label.dettaglio_orariaperturatestata.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
	<c:if test="${orariaperturatestata.displayMode==orariaperturatestata.displayConstants.NEW}">
		<fmt:message key="orariaperturatestata.label.nuovo_orariaperturatestata.title" />
	</c:if> 
	<c:if test="${orariaperturatestata.displayMode==orariaperturatestata.displayConstants.VIEW}">
		<fmt:message key="orariaperturatestata.label.dettaglio_orariaperturatestata.title" />
	</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	    <jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../orariaperturatestata/list" />
	</jsp:include>	
    <c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${orariaperturatestata.entity.istanze.id.codice}</c:param>
	</c:import> 
		<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="orariaperturatestata" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="orariaperturatestata" />
		    </jsp:include>
			<table>				
				<tr>
					<td><fmt:message key="label.descrizione" /></td>
					<td colspan="3">						
						<script type="text/javascript">
							function ricercaTipiorarioAfterUpdate(inputField,listItem){								  
						   		var a = listItem.id;
								document.getElementById('tipiorario_id').value = inputField.value;
								document.getElementById('tipiorario_hidden').value = a;
								altreInformazioniTipiorario(a);
							}
							function altreInformazioniTipiorario(codiceTipiorario){
								var call_msg = new Ajax.Request('<%=request.getContextPath()%>/json/getTipiorario.htm?_ts='+new Date().getTime()+'&codiceTipiorario=' + codiceTipiorario, {
									  method: 'post',	
									  onSuccess: function(transport){ 										
										var json = transport.responseText.evalJSON();										
										var tipiorario = json.tipiorario;
										var tipiorariodettaglios = json.tipiorariodettaglios;
										aggiornaInformazioni(tipiorario.toPeriododaTransient, tipiorario.toPeriodoaTransient, tipiorariodettaglios);											
							  		  },
									  onFailure: function(transport){ 
							  			var responseTexts = transport.responseText;
							  			alert(responseTexts);
								  	  }						    		 
								});								
							}							
							function aggiornaInformazioni(toPeriododa, toPeriodoa, tipiorariodettaglios){
								var toPeriododaElem = $('periododaTransient_id');
								if(toPeriododa!=null){									
									toPeriododaElem.value = toPeriododa;
								}else{
									toPeriododaElem.value = '';
								}
								var toPeriodoaElem = $('periodoaTransient_id');
								if(toPeriodoa!=null){									
									toPeriodoaElem.value = toPeriodoa;			
								}else{
									toPeriodoaElem.value = '';
								}
								for (i=0;i<=6;i++){
									var oaDalleore = $('entity.orariaperturas[' + i + '].oaDalleore');
									if(tipiorariodettaglios[i].oaDalleore!=null){										
										oaDalleore.value = tipiorariodettaglios[i].oaDalleore;
									}else{
										oaDalleore.value = '';
									}
									var oaAlleore = $('entity.orariaperturas[' + i + '].oaAlleore');
									if(tipiorariodettaglios[i].oaAlleore!=null){										
										oaAlleore.value = tipiorariodettaglios[i].oaAlleore;
									}else{
										oaAlleore.value = '';
									}
									var oaDalleorepom = $('entity.orariaperturas[' + i + '].oaDalleorepom');
									if(tipiorariodettaglios[i].oaDalleorepom!=null){
										oaDalleorepom.value = tipiorariodettaglios[i].oaDalleorepom;
									}else{
										oaDalleorepom.value = '';
									}
									var oaAlleorepom = $('entity.orariaperturas[' + i + '].oaAlleorepom');
									if(tipiorariodettaglios[i].oaAlleorepom!=null){										
										oaAlleorepom.value = tipiorariodettaglios[i].oaAlleorepom;	
									}else{
										oaAlleorepom.value = '';
									}									
									var tipiapertura = $('codicetipiaperturaList[' + i + ']');
									if(tipiorariodettaglios[i].tipiapertura.id.codice!=null){										
										tipiapertura.value = tipiorariodettaglios[i].tipiapertura.id.codice;																				
									}else{
										tipiapertura.value = '';																				
									}									
								}							
							}							
						</script>										
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="tipiorario" />		
							<jsp:param name="propertyPath" value="entity.tipiorario" />				
							<jsp:param name="pathPropertyDescription" value="entity.tipiorario.toDescrizione" />
							<jsp:param name="pathPropertyCode" value="entity.tipiorario.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiorario.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipiorario" />							 
							<jsp:param name="afterUpdateElement" value="ricercaTipiorarioAfterUpdate" />							
						</jsp:include>	
						<c:if test="${orariaperturatestata.displayMode==orariaperturatestata.displayConstants.NEW}">
							<script type='text/javascript'>
								$('tipiorario_id').focus();
							</script>	
						</c:if>									
					</td>										
				</tr>						
				<tr>
					<td>
						<fmt:message key="label.periodo_dal" />
					</td>
					<td>
						<spring-form:input tabindex="2" id="periododaTransient_id" path="entity.periododaTransient" size="10" onblur="isValidPeriod(this,true);"/>
						<init:help idHelp="help1" textKey="orariaperturatestata.help.periododa"/>
						<spring-form:errors path="entity.periododa" cssClass="error"/>
					</td>
				</tr>				
				<tr>
					<td>
						<fmt:message key="label.al" />
					</td>
					<td>
						<spring-form:input tabindex="3" id="periodoaTransient_id" path="entity.periodoaTransient" size="10" onblur="isValidPeriod(this,true);"/>
						<init:help idHelp="help2" textKey="orariaperturatestata.help.periodoa"/>
						<spring-form:errors path="entity.periodoa" cssClass="error"/>
					</td>
				</tr>
			</table>				
			<br class="break" />			
			<fieldset style="width: 80%;" >				
				<div class="jmesa">				
					<%Integer tabIndex = 4; %>
					<table class="table">
						<thead>
							<tr class="header">
								<td><fmt:message key="label.giorno_settimana"/></td>
								<td><fmt:message key="label.inizio_mattina"/></td>
			                    <td><fmt:message key="label.fine_mattina"/></td>
			                    <td><fmt:message key="label.inizio_pomeriggio"/></td>
			                    <td><fmt:message key="label.fine_pomeriggio"/></td>
			                    <td><fmt:message key="label.tipologia_orario"/></td>              
							</tr>
						</thead>
				    	<tbody class="tbody">	
				    		<c:forEach items="${orariaperturatestata.entity.orariaperturas}" var="current" varStatus="a">
						    <tr>
						    	<td><label>${current.giornisettimana.gsDescrizione}</label></td>
						    	<spring:bind path="entity.orariaperturas[${a.index}].giornisettimana.id"> 			
									<input type="hidden" name="${status.expression}" value="${status.value}" />			
								</spring:bind>
						    	<td>
									<spring:bind path="entity.orariaperturas[${a.index}].oaDalleore">
										<input tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"  onblur="isValidOra(this,true);" size="5"/>						 
									</spring:bind>									
								</td>								
								<td>
									<spring:bind path="entity.orariaperturas[${a.index}].oaAlleore">
										<input  tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" onblur="isValidOra(this,true);" size="5"/>						 
									</spring:bind>									
								</td>								
								<td>
									<spring:bind path="entity.orariaperturas[${a.index}].oaDalleorepom">
										<input  tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" onblur="isValidOra(this,true);" size="5"/>						 
									</spring:bind>									
								</td>								
								<td>
									<spring:bind path="entity.orariaperturas[${a.index}].oaAlleorepom">
										<input  tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" onblur="isValidOra(this,true);" size="5"/>						 
									</spring:bind>									
								</td>	
								<td>
									<spring:bind path="codicetipiaperturaList">
										<select tabindex="<%=tabIndex++%>" name="${status.expression}" id="codicetipiaperturaList[${a.index}]">
											<c:set var="selected" value=""/>	
											<option value=""><fmt:message key="label.select.default"/></option>
											<c:forEach var="ta" items="${orariaperturatestata.tipiaperturaList}" varStatus="counter">
												<c:if test="${status.value[a.index] eq ta.id.codice}">
													<c:set var="selected" value="selected"/>
												</c:if>								 											            						 
												<option value="${ta.id.codice}" ${selected} >
														${ta.taDescrizione}
												</option>
												<c:remove var="selected"/>							
											</c:forEach>
										</select>
									</spring:bind>						
								</td>								
						    </tr>			    
							</c:forEach>
						</tbody>
					</table>	
			  	</div>		
			</fieldset>		
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${orariaperturatestata.displayMode==orariaperturatestata.displayConstants.NEW}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${orariaperturatestata.displayMode==orariaperturatestata.displayConstants.VIEW}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceIstanza=${orariaperturatestata.entity.istanze.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>