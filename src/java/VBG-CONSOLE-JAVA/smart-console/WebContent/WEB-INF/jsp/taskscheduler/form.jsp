<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${taskscheduler.displayMode==taskscheduler.displayConstants.NEW}">
			<fmt:message key="taskscheduler.label.nuovo_taskscheduler.title" />
		</c:if> 
		<c:if test="${taskscheduler.displayMode==taskscheduler.displayConstants.VIEW}">
			<fmt:message key="taskscheduler.label.dettaglio_taskscheduler.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${taskscheduler.displayMode==taskscheduler.displayConstants.NEW}">
			<fmt:message key="taskscheduler.label.nuovo_taskscheduler.title" />
		</c:if> 
		<c:if test="${taskscheduler.displayMode==taskscheduler.displayConstants.VIEW}">
			<fmt:message key="taskscheduler.label.dettaglio_taskscheduler.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="taskscheduler" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="taskscheduler" />
		    </jsp:include>
			<table>
				<tr>
					<td><label class="required">*</label><fmt:message key="label.operazione" /></td>
					<td colspan="3">
						<spring-form:select id="operazione_id" path="entity.taskbase.task" onchange="impostahelp2()">
							<c:forEach items="${taskscheduler.operazioneList}" var="operazione">
								<spring-form:option value="${operazione.task}" label="${operazione.task}" title="${operazione.descrizione}"></spring-form:option>								
							</c:forEach>							
						</spring-form:select>	
						<init:help idHelp="help2"/>											
						<spring-form:errors path="entity.taskbase" cssClass="error"/>
					</td>
				</tr>								
				<tr>
					<td>
						<label class="required">*</label><fmt:message key="label.descrizione_operazione" />
					</td>
					<td colspan="3">
						<spring-form:textarea id="descrizione_id" path="entity.descrizione" rows="4" cols="70"/>
						<spring-form:errors path="entity.descrizione" cssClass="error"/>
					</td>
				</tr>				
				<%-- Intervallo: Giorni Ore Minuti --%>
				<tr>
					<td>
						<label class="required">*</label><fmt:message key="taskscheduler.label.esegui_operazione_ogni" />
					</td>
					<td>
						<spring-form:input id="giorni_id" path="giorni" size="3" maxlength="3" cssStyle="text-align:right;" onchange="javascript:checkGiorni(this)"/>					
						<spring-form:input id="ore_id" path="ore" size="2" maxlength="2" cssStyle="text-align:right;" onchange="javascript:checkOre(this)"/>					
						<spring-form:input id="minuti_id" path="minuti" size="2" maxlength="2" cssStyle="text-align:right;" onchange="javascript:checkMinuti(this)"/>
						<spring-form:errors path="entity.intervallo" cssClass="error"/>						
					</td>
				</tr>						
				<%-- Prossima esecuzione --%>
				<tr>
					<td>
						<label class="required">*</label><fmt:message key="taskscheduler.label.prossimaesecuzione" />
					</td>
					<td>									
						<spring-form:input  tabindex="6" id="prossimaesecuzione_id" path="entity.prossimaesecuzione" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
		   				<init:calendar idImage="caldata1" idInput="prossimaesecuzione_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
		   				<spring-form:errors	path="entity.prossimaesecuzione" cssClass="error" />						
					</td>
					<td>
						<spring-form:input id="hhmmss_id" path="hhmmss" size="8" maxlength="8" onblur="isValidTime(this,true);"/>
						<init:help idHelp="help3" textKey="taskscheduler.help.prossimaesecuzione"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.attivo" />
					</td>
					<td colspan="3">
						<spring-form:checkbox id="attivo_id" path="entity.attivo" value="1" />
						<spring-form:errors path="entity.attivo" cssClass="error"/>					
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="label.in_esecuzione" />
					</td>
					<td colspan="3">
						<spring-form:checkbox id="inesecuzione_id" path="entity.inesecuzione" value="1" />
						<init:help idHelp="help5" textKey="taskscheduler.help.inesecuzione"/>	
						<spring-form:errors path="entity.inesecuzione" cssClass="error"/>					
					</td>
				</tr>						
			</table>
			<script type='text/javascript'>
				$('descrizione_id').focus();
				
				impostahelp2();
				function impostahelp2(){
					jQuery('#help2_tooltip').html(jQuery('#operazione_id').find("option:selected").attr("title"));
				}			
				
				function checkGiorni(obj){
					var value=obj.value.replace(",",".");
				    if (isNaN(value)){
				        alert('Devi inserire un numero intero positivo');
				        obj.value="";
						obj.focus();
						return false;
				    } 
				    if (value.indexOf(".")>0){
				       alert('Devi inserire un numero intero positivo');
				       obj.value="";
				   	   obj.focus();
				   	   return false;
				    }
				    if (value<0){
					       alert('Devi inserire un numero intero positivo');
					       obj.value="";
					   	   obj.focus();
					   	   return false;
					}
				    return true;
				}
				
				function checkOre(obj){
					var value=obj.value.replace(",",".");
				    if (isNaN(value)){
				        alert('Devi inserire un numero intero positivo');
				        obj.value="";
						obj.focus();
						return false;
				    } 
				    if (value.indexOf(".")>0){
				       alert('Devi inserire un numero intero positivo');
				       obj.value="";
				   	   obj.focus();
				   	   return false;
				    }
				    if (value<0){
					       alert('Devi inserire un numero intero positivo');
					       obj.value="";
					   	   obj.focus();
					   	   return false;
					}
				    if (value>23){
					       alert('Devi inserire un numero intero positivo minore di 24');
					       obj.value="";
					   	   obj.focus();
					   	   return false;
					}
				    return true;
				}
				
				function checkMinuti(obj){
					var value=obj.value.replace(",",".");
				    if (isNaN(value)){
				        alert('Devi inserire un numero intero positivo');
				        obj.value="";
						obj.focus();
						return false;
				    } 
				    if (value.indexOf(".")>0){
				       alert('Devi inserire un numero intero positivo');
				       obj.value="";
				   	   obj.focus();
				   	   return false;
				    }
				    if (value<0){
					       alert('Devi inserire un numero intero positivo');
					       obj.value="";
					   	   obj.focus();
					   	   return false;
					}
				    if (value>59){
					       alert('Devi inserire un numero intero positivo minore di 60');
					       obj.value="";
					   	   obj.focus();
					   	   return false;
					}
				    return true;
				}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${taskscheduler.displayMode==taskscheduler.displayConstants.NEW}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${taskscheduler.displayMode==taskscheduler.displayConstants.VIEW}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('createParametri.htm?codice=${taskscheduler.entity.id.codice}','',document.inviodati)"><fmt:message key="button.parametri" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>