<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipiorario.displayMode==tipiorario.displayConstants.NEW}">
			<fmt:message key="tipiorario.label.nuovo_tipiorario.title" />
		</c:if> 
		<c:if test="${tipiorario.displayMode==tipiorario.displayConstants.VIEW}">
			<fmt:message key="tipiorario.label.dettaglio_tipiorario.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${tipiorario.displayMode==tipiorario.displayConstants.NEW}">
			<fmt:message key="tipiorario.label.nuovo_tipiorario.title" />
		</c:if> 
		<c:if test="${tipiorario.displayMode==tipiorario.displayConstants.VIEW}">
			<fmt:message key="tipiorario.label.dettaglio_tipiorario.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="tipiorario" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipiorario" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input tabindex="1" id="toDescrizione_id" path="entity.toDescrizione" size="70" />						
						<spring-form:errors path="entity.toDescrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.periodo_dal" />
					</td>
					<td>
						<spring-form:input tabindex="2" id="toPeriododaTransient_id" path="entity.toPeriododaTransient" size="10" onblur="isValidPeriod(this,true);"/>
						<init:help idHelp="help1" textKey="tipiorario.help.toPeriododa"/>
						<spring-form:errors path="entity.toPeriododa" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.al" />
					</td>
					<td>
						<spring-form:input tabindex="3" id="toPeriodoaTransient_id" path="entity.toPeriodoaTransient" size="10" onblur="isValidPeriod(this,true);"/>
						<init:help idHelp="help2" textKey="tipiorario.help.toPeriodoa"/>
						<spring-form:errors path="entity.toPeriodoa" cssClass="error"/>
					</td>
				</tr>				
			</table>
	<script type='text/javascript'>
		$('toDescrizione_id').focus();
	</script>	
	<br />	
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
			    <c:forEach items="${tipiorario.entity.tipiorariodettaglios}" var="current" varStatus="a">
			    <tr>
			    	<td><label>${current.giornisectimana.gsDescrizione}</label></td>
			    	<spring:bind path="entity.tipiorariodettaglios[${a.index}].giornisectimana.id"> 

						<input type="hidden" name="${status.expression}" value="${status.value}" />

					</spring:bind>
			    	<td>
						<spring:bind path="entity.tipiorariodettaglios[${a.index}].oaDalleore">
							<input tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" value="${status.value}"  onblur="isValidOra(this,true);" size="5"/>						 
						</spring:bind>									
					</td>
					
					<td>
						<spring:bind path="entity.tipiorariodettaglios[${a.index}].oaAlleore">
							<input  tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" value="${status.value}" onblur="isValidOra(this,true);" size="5"/>						 
						</spring:bind>									
					</td>
					
					<td>
						<spring:bind path="entity.tipiorariodettaglios[${a.index}].oaDalleorepom">
							<input  tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" value="${status.value}" onblur="isValidOra(this,true);" size="5"/>						 
						</spring:bind>									
					</td>
					
					<td>
						<spring:bind path="entity.tipiorariodettaglios[${a.index}].oaAlleorepom">
							<input  tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" value="${status.value}" onblur="isValidOra(this,true);" size="5"/>						 
						</spring:bind>									
					</td>
					<td>
						<spring:bind path="codicetipiaperturaList">
							<select tabindex="<%=tabIndex++%>" name="${status.expression}" id="codicetipiaperturaList[${a.index}]">
								<c:set var="selected" value=""/>	
								<option value=""><fmt:message key="label.select.default"/></option>
								<c:forEach var="ta" items="${tipiorario.tipiaperturaList}" varStatus="counter">
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
			<c:if test="${tipiorario.displayMode==tipiorario.displayConstants.NEW}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${tipiorario.displayMode==tipiorario.displayConstants.VIEW}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>