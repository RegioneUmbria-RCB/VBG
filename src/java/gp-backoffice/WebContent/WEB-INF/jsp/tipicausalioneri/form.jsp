<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipicausalioneri.id.codice==null}">
			<fmt:message key="tipicausalioneri.label.nuovo_tipicausalioneri.title" />
		</c:if> 
		<c:if test="${tipicausalioneri.id.codice!=null}">
			<fmt:message key="tipicausalioneri.label.dettaglio_tipicausalioneri.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${tipicausalioneri.id.codice==null}">
			<fmt:message key="tipicausalioneri.label.nuovo_tipicausalioneri.title" />
		</c:if> 
		<c:if test="${tipicausalioneri.id.codice!=null}">
			<fmt:message key="tipicausalioneri.label.dettaglio_tipicausalioneri.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<div class="vbg-form">	
		<%String bollo="display:none;"; %>
			<spring-form:form commandName="tipicausalioneri" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
			        <jsp:param name="commandName" value="tipicausalioneri" />
			    </jsp:include>
			<fieldset>
				<legend><fmt:message key="label.causale_onere"/></legend>
			    <div class="form-group">
					<label><fmt:message key="tipicausalioneri.label.raggruppamentocausalioneri" /></label>
					<spring-form:select id="raggruppamentocausalioneri_id" path="raggruppamentocausalioneri.id.codice" >
						<spring-form:option value="0" label=""></spring-form:option>
						<spring-form:options items="${raggruppamentocausalioneriList}" itemLabel="rcoDescr" itemValue="id.codice"/>
					</spring-form:select>
					<spring-form:errors path="raggruppamentocausalioneri.id.codice" cssClass="error"/>
				</div>
				<div class="form-group">
					<label><fmt:message key="tipicausalioneri.label.coDescrizione" /></label>
					<spring-form:input id="coDescrizione_id" path="coDescrizione" size="70" />
					<spring-form:errors path="coDescrizione" cssClass="error"/>
				</div>
				<c:if test="${ vert_people_attivo eq true or vert_sieder_attivo eq true }">
					<div class="form-group">
						<label>
							<fmt:message key="tipicausalioneri.label.codicecausalepeople" />
							<c:if test="${vert_sieder_attivo eq true}">/SIEDER</c:if>
	                    </label>
						<spring-form:input id="codicecausalepeople_id" path="codicecausalepeople" size="30" maxlength="50"/>
						<spring-form:errors path="codicecausalepeople" cssClass="error"/>		
					</div>			
				</c:if>			

				<div class="form-group">
					<label><fmt:message key="tipicausalioneri.label.coOrdinamento" /></label>				
					<spring-form:input id="coOrdinamento_id" path="coOrdinamento" maxlength="4" size="4"/>
					<spring-form:errors path="coOrdinamento" cssClass="error"/>
				</div>
				<c:if test="${vert_pagamenti_attivo eq true}">
					<div class="form-group">
						<label><fmt:message key="tipicausalioneri.label.pagamentiregulus" /></label>					
						<spring-form:checkbox id="pagamentiregulus_id" path="pagamentiregulus" onclick="displayBollo();"/>
						<init:help idHelp="help_pagamentiregulus" textKey="tipicausalioneri.help.pagamentiregulus"/>
						<spring-form:errors path="pagamentiregulus" cssClass="error"/>
					</div>
					<div class="form-group" id="bollo_id" style="<%=bollo %>">
						<label><fmt:message key="tipicausalioneri.label.causalebollo" /></label>					
						<spring-form:select id="causalebollo_id" path="causalebollo.id.codice" >
							<spring-form:option value="0" label=""></spring-form:option>
							<spring-form:options items="${causalibolloList}" itemLabel="coDescrizione" itemValue="id.codice"/>
						</spring-form:select>
						<spring-form:errors path="causalebollo.id.codice" cssClass="error"/>
					</div>			
				</c:if>
				<c:if test="${tipicausalioneri.id.codice!=null}">
					<div class="form-group">
						<label><fmt:message key="tipicausalioneri.label.coSerichiedeendo" /></label>					
						<spring-form:checkbox id="coSerichiedeendo_id" path="coSerichiedeendo" disabled="true"/>
						<init:help idHelp="help_coSerichiedeendo" textKey="tipicausalioneri.help.coSerichiedeendo"/>
						<spring-form:errors path="coSerichiedeendo" cssClass="error"/>
					</div>			
				</c:if>
				<c:if test="${tipicausalioneri.id.codice==null}">
					<div class="form-group">
						<label><fmt:message key="tipicausalioneri.label.coSerichiedeendo" /></label>					
						<spring-form:checkbox id="coSerichiedeendo_id" path="coSerichiedeendo"/>
						<init:help idHelp="help_coSerichiedeendo" textKey="tipicausalioneri.help.coSerichiedeendo"/>
						<spring-form:errors path="coSerichiedeendo" cssClass="error"/>
					</div>
				</c:if>
				<div class="form-group">
					<label><fmt:message key="tipicausalioneri.label.coDisabilitato" /></label>					
					<spring-form:checkbox id="coDisabilitato_id" path="coDisabilitato"/>
					<init:help idHelp="help_coDisabilitato" textKey="tipicausalioneri.help.coDisabilitato"/>
					<spring-form:errors path="coDisabilitato" cssClass="error"/>
				</div>
				<c:if test="${verticalizzazioni_nodoPagamenti_attiva eq true}">				
					<div class="form-group">
	                    <label><fmt:message key="tipicausalioneri.label.flagGenerafattura" /></label>
						<spring-form:checkbox id="flagGenerafattura_id" path="flagGenerafattura"/>
						<init:help idHelp="help_flagGenerafattura" textKey="tipicausalioneri.help.flagGenerafattura"/>
						<spring-form:errors path="flagGenerafattura" cssClass="error"/>
					</div>
					<div class="form-group">
	                	<label><fmt:message key="tipicausalioneri.label.flagGeneraavviso" /></label>					
						<spring-form:checkbox id="flagGeneraavviso_id" path="flagGeneraavviso"/>
						<init:help idHelp="help_flagGeneraavviso" textKey="tipicausalioneri.help.flagGeneraavviso"/>
						<spring-form:errors path="flagGeneraavviso" cssClass="error"/>
					</div>
				</c:if>
				<c:if test="${tipicausalioneri.flgTipicausaliinteressi==false || tipicausalioneri.flgTipicausaliinteressi==null}">
					<div class="form-group">
						<label><fmt:message key="tipicausalioneri.label.interessi_di_mora"/></label>
						<jsp:include page="../includes/autocompletergenericoTT.jsp" >
							<jsp:param name="idElemento" value="tipicausalioneriMora" />		
							<jsp:param name="propertyPath" value="tipicausalioneriMora" />				
							<jsp:param name="pathPropertyDescription" value="tipicausalioneriMora.coDescrizione" />
							<jsp:param name="pathPropertyCode" value="tipicausalioneriMora.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipicausalioneriMora.htm?codicesoftware=" />	
							<jsp:param name="titleKey" value="label.ricerca_tipi_causalioneri_mora" />
							<jsp:param name="id_help" value="help_tipicausalioneriMora" />
							<jsp:param name="help" value="help.search_famiglie_e_categorie_endo_archivi_base" />
						</jsp:include>
					</div>
				</c:if>
				<c:if test="${tipicausalioneri.id.codice!=null && tipicausalioneri.flgTipicausaliinteressi==true}">
					<div class="form-group">
						<td >
						<br class="break" />
						<fieldset><legend><fmt:message key="label.percentuali" /></legend>
							<div class="jmesa">
								<table  border="0" cellpadding="2" cellspacing="0" class="table">
									<thead>
									<tr class="header">
										<td width="5%"><fmt:message key="tipicausalioneri.label.da_maggiore" /> </td>
										<td width="5%" ><fmt:message key="tipicausalioneri.label.a_minore_uguale" /><init:help idHelp="help_minore_uguale" textKey="help.tipicausalioneri.minore_uguale"/></td>
										<td width="5%"><fmt:message key="tipicausalioneri.label.percentuale_applicare" /></td>
										<td width="5%"><fmt:message key="label.elimina" /></td>
									</tr>
									</thead>
									<tbody class="tbody">
									<%int y=0;%>
									<c:if test="${fn:length(tipicausalioneri.tipicausalioninteressis)>0}">
									<c:forEach items="${tipicausalioneri.tipicausalioninteressis}" var="tipicausalioneriMora" varStatus="count">
									<tr class="<%=(y%2)==0?"odd":"even"%>">
										<c:if test="${tipicausalioneriMora.id.codice!=null}">
										<td width="5%">
										    ${tipicausalioneriMora.ggritardopagamentoPrecedente}
										</td>
										</c:if>
										<c:if test="${tipicausalioneriMora.id.codice==null}">
											<td width="5%"></td>
										</c:if>
                                        <td width="5%" style="text-align: right;">
											<spring:bind path="tipicausalioninteressis[${count.index}].ggritardopagamento">
												<input style="text-align: right;" type="text" name="${status.expression}" value="${status.value}" size="4"/>	
											</spring:bind> 
										</td>
										<td width="5%" style="text-align: right;">
											<spring:bind path="tipicausalioninteressis[${count.index}].percentuale">
												<input style="text-align: right;" type="text" name="${status.expression}" value="${status.value}" size="6" onblur="checkNumberValue(this);" />
											</spring:bind> 
										</td>
										<td width="5%">
											<a class="eliminaRiga" href="javascript:doHref('../tipicausalioneri/deleteTipicausaliInteressi.htm?codice=${tipicausalioneriMora.id.codice}&indiceLista=${count.index}','<fmt:message key="javascript.confirm.delete" />');" title="<fmt:message key="label.elimina" />">
												<label><fmt:message key="label.elimina" /></label>
											</a>							
										</td>
									</tr>
									<%y++; %>			
									</c:forEach>
									</c:if>
									</tbody>
									<tfoot>
										<tr align="left" class="odd">
											<td  colspan="4">
												<a class="addColumn" href="javascript:doHref('../tipicausalioneri/addTipicausaliInteressi.htm?codice=${tipicausalioneri.id.codice}','<fmt:message key="javascript.alert.dati_non_salvati" />');" 
														title="<fmt:message key="label.nuovo" />">
												<label><fmt:message key="label.nuovo" /></label></a>
											</td>					
										</tr>
									</tfoot>				
									</table>		
							</div>			
						</fieldset>
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty contiList}">
					<div class="form-group">
						<label><fmt:message key="label.conto" /></label>						
						<spring-form:select id="conti_id" path="contoAttivo" >
							<spring-form:option value="" label=""></spring-form:option>
							<spring-form:options items="${contiList}" itemLabel="descrizione" itemValue="id.codice"/>
						</spring-form:select>						
					</div>				
				</c:if>
			</fieldset>			
			<c:if test="${vert_pagamenti_attivo eq true}">
				<script type='text/javascript'>
					displayBollo();
					function displayBollo(){
						if($('pagamentiregulus_id').checked){
							$('bollo_id').appear();
						}else{
							$('bollo_id').fade();
							}
					}
				</script>
			</c:if>	
			<script type='text/javascript'>
				$('coDescrizione_id').focus();
			</script>	
		</spring-form:form>
		</div>
	</div>
	<div class="form-button">
		<c:if test="${tipicausalioneri.id.codice==null}">
			<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
		</c:if>
		<c:if test="${tipicausalioneri.id.codice!=null}">
			<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
			<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
		</c:if>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>