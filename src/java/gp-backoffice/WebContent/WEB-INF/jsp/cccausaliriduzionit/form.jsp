<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${cccausaliriduzionit.entity.id.codice==null}">
			<fmt:message key="label.nuovo_cccausaliriduzionit.title" />
		</c:if> 
		<c:if test="${cccausaliriduzionit.entity.id.codice!=null}">
			<fmt:message key="label.dettaglio_cccausaliriduzionit.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${cccausaliriduzionit.entity.id.codice==null}">
			<fmt:message key="label.nuovo_cccausaliriduzionit.title" />
		</c:if> 
		<c:if test="${cccausaliriduzionit.entity.id.codice!=null}">
			<fmt:message key="label.dettaglio_cccausaliriduzionit.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="cccausaliriduzionit" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="cccausaliriduzionit" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="entity.descrizione" size="70" />
						<spring-form:errors path="entity.descrizione" cssClass="error"/>
					</td>
					
				</tr>
			</table>	
			<!-- Righe di dettaglio (CC_CAUSALIRIDUZIONI_R) -->
			
			<c:if test="${cccausaliriduzionit.entity.id.codice!=null}">
			<div class="titoloSezione"><fmt:message key="label.causali_riduzione_aumento" /></div>
				<div class="jmesa" >
					<table width="100%" border="0"  cellpadding="0"  cellspacing="0"  class="table">
							<thead>
								<tr class="header">
									<td width="40%"><fmt:message key="label.causale" /></td>
									<td><fmt:message key="label.importo_percentuale" /></td>
									<td width="5%"><fmt:message key="label.azioni" /></td>
								</tr>
							</thead>
							 <%
			   	 				int i=0;
			    			 %>
							<tbody class="tbody">
								<c:forEach items="${cccausaliriduzionit.entity.ccCausaliriduzionirs}" var="ccCausaliriduzionir" varStatus="a">
				    				<tr class="<%=(i%2)==0?"odd":"even"%>">
				    					<td>${ccCausaliriduzionir.descrizione}</td>
				    					<td >
				    						<fmt:formatNumber minFractionDigits="2"  value="${ccCausaliriduzionir.riduzioneperc}"></fmt:formatNumber>  
				    					</td>
				    			        <td>
				    			           	<%-- 
					    			        	<a class="dettaglioColumn" href="view.htm?codice=${ccCausaliriduzionir.id.codice}" title="<fmt:message key="label.edit.record" />${cccausaliriduzionit_var.id.codice}">
													<label><fmt:message key="label.edit.record.image" /></label>
												</a>
											--%>
											<a class="eliminaRiga" href="javascript:doSubmit('deleteccCausaliriduzionir.htm?codiceCausaleR=${ccCausaliriduzionir.id.codice}&codiceCausaleT=${cccausaliriduzionit.entity.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)" title="<fmt:message key="label.elimina" />${cccausaliriduzionit_var.id.codice}">
												<label><fmt:message key="label.elimina" /></label>
											</a>
				    			        </td>
				    			        
				    				</tr>
						    	<%i++;%>
			    				</c:forEach>
                                   <c:if test="${cccausaliriduzionit.displayMode eq cccausaliriduzionit.displayConstants.EDIT}">
                                   <tr>
                                   		<td>
	                                   		<spring-form:input id="descrizione_r_id" path="ccCausaliriduzionir.descrizione" size="50" />
											<spring-form:errors path="ccCausaliriduzionir.descrizione" cssClass="error"/>
											
                                		</td>
                                		<td colspan="2">
	                                   		<spring-form:input id="riduzioneperc_id" path="ccCausaliriduzionir.riduzioneperc" cssStyle="text-align:right;" size="10" maxlength="10" onblur="checkNumberValue(this);"/>
											<spring-form:errors path="ccCausaliriduzionir.riduzioneperc" cssClass="error"/> 
                                            <init:help idHelp="help_descrizione_r_id" textKey="help.ccCausaliriduzionir_riduzioneperc"/>
									   
									   		<a  class="vbg-btn btn-salva" href="javascript:doSubmit('insertccCausaliriduzionir.htm','',document.inviodati)" title="<fmt:message key="label.salva" />">
												<label><fmt:message key="label.salva" /></label>
											</a>
											<a  class="undo" href="view.htm?codice=${cccausaliriduzionit.entity.id.codice}" title="<fmt:message key="label.annulla" />">
												<label><fmt:message key="label.annulla" /></label>
											</a>
									   </td>
									  
                                   </tr>
                                   </c:if>
                                   <c:if test="${cccausaliriduzionit.displayMode ne cccausaliriduzionit.displayConstants.EDIT}">
                                   <tr>
                                       <td colspan="3" ><a style="float: left;" class="addColumn" href="addccCausaliriduzionir.htm?codice=${cccausaliriduzionit.entity.id.codice}" title="<fmt:message key="label.aggiungi" />">
												<label><fmt:message key="label.aggiungi" /></label>
											</a>
									   </td>
                                   </tr>
                                   </c:if>
							</tbody>
					</table>
				</div>
			</c:if>
			
			<script type='text/javascript'>
				$('descrizione_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${cccausaliriduzionit.entity.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${cccausaliriduzionit.entity.id.codice!=null && cccausaliriduzionit.displayMode ne cccausaliriduzionit.displayConstants.EDIT}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>