<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${fovisuracampi.displayMode==fovisuracampi.displayConstants.NEW}">
			<fmt:message key="fovisuracampi.label.nuovo_fovisuracampi.title" />
		</c:if> 
		<c:if test="${fovisuracampi.displayMode==fovisuracampi.displayConstants.VIEW}">
			<fmt:message key="fovisuracampi.label.dettaglio_fovisuracampi.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${fovisuracampi.displayMode==fovisuracampi.displayConstants.NEW}">
			<fmt:message key="fovisuracampi.label.nuovo_fovisuracampi.title" />
		</c:if> 
		<c:if test="${fovisuracampi.displayMode==fovisuracampi.displayConstants.VIEW}">
			<fmt:message key="fovisuracampi.label.dettaglio_fovisuracampi.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="fovisuracampi" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="fovisuracampi" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.tipo_campo" />
					</td>
					<td>
						<spring-form:select id="foVisuraContestiBase_id" path="foVisuraCampiHelper.contestoBase.id" onchange="view()">
							<spring-form:option value="" label=""></spring-form:option>
							<spring-form:options items="${fovisuracampi.foVisuraContestiBases}" itemValue="id" itemLabel="contesto"></spring-form:options>
						</spring-form:select>
					</td>					
				</tr>
			</table>						
			<c:if test="${fovisuracampi.displayMode==fovisuracampi.displayConstants.VIEW}">	
			<br class="break" />			
			<fieldset style="width: 40%"><legend>${fovisuracampi.foVisuraCampiHelper.contestoBase.contesto}</legend>		
				<div class="jmesa">				
					<%
						Integer tabIndex = 2;						
					%>
					<table class="table">
						<thead>
							<tr class="header">
								<td ><fmt:message key="label.campo"/></td>
								<td align="right"><fmt:message key="label.posizione"/></td>
							</tr>
						</thead>
				    	<tbody class="tbody">
				    		<%int x=0; %>	
				    		<c:forEach items="${fovisuracampi.foVisuraCampiHelper.contestoBase.foVisuraContestiCampiBases}" var="current" varStatus="a">
								<c:forEach items="${fovisuracampi.foVisuraCampiHelper.foVisuraCampis}" var="currentcampo" varStatus="b">
								<c:if test="${current.foVisuraCampiBase.id==currentcampo.foVisuraCampiBase.id}">
									<tr class="<%=(x%2)==0?"odd":"even"%>">
										<td>
											<label>${current.foVisuraCampiBase.campo}</label>
										</td>												
										<td align="right" width="8%">
											<spring:bind path="foVisuraCampiHelper.foVisuraCampis[${b.index}].posizione">
												<input tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" size="3" maxlength="3" onchange="checkNumberInt(this);javascript:isPositiveNumber(this)" style="text-align:right;padding: 0 2px 0 0"/>						 
											</spring:bind>				
										</td>							
									</tr>
								</c:if>								
								</c:forEach>
								<%x++; %>
							</c:forEach>							
						</tbody>
					</table>	
			  	</div>		
			</fieldset>							
			</c:if>					
			<script type='text/javascript'>								
				function view(){					
					if(document.getElementById("foVisuraContestiBase_id").value!=null && document.getElementById("foVisuraContestiBase_id").value!=""){
						idContesto = document.getElementById("foVisuraContestiBase_id").value;
						doHref('view.htm?idContesto='+ idContesto);
					}else{
						vis_errore(document.getElementById("foVisuraContestiBases_id"),"Contesto non Specificato");
					}
				}						
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${fovisuracampi.displayMode==fovisuracampi.displayConstants.VIEW}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('create.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>