<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.AlberoprocAteco" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Ateco" %>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="java.net.URLEncoder" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>	
		<fmt:message key="alberoproc.label.parametri_fascicolazione.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="alberoproc.label.parametri_fascicolazione.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../alberoproc/view" />
	</jsp:include>
		
	<div id="subcontent">
		<div class="parametriDiv">
       		<div class="parametro"> 
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionepadre}
	        	</div>
	        </div>
	    </div>
	    <div class="clear"></div>
	    
			<spring-form:form commandName="alberoproc" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
			        <jsp:param name="commandName" value="alberoproc" />
			    </jsp:include>
				<table width="100%">
						<tr>
							<td><fmt:message key="alberoproc.label.scFascclassifica" /></td>
							<td colspan="3">
								<c:if test="${not empty classificaList}">
								<spring-form:select path="scFascclassifica">
									<spring-form:option value=""></spring-form:option>
									<spring-form:options items="${classificaList}" itemLabel="descrizione" itemValue="codice" />
								</spring-form:select>
								</c:if>
								<c:if test="${empty classificaList}">
									<spring-form:input id="scFascclassifica_id" path="scFascclassifica" size="70" />
								</c:if>
								<init:help idHelp="help_scFascclassifica" textKey="alberoproc.help.scFascclassifica" />
								<spring-form:errors path="scFascclassifica" cssClass="error"/>	
							</td>
						</tr>						
						<tr>
							<td><fmt:message key="alberoproc.label.scFasccodtesto" /></td>
							<td colspan="3">
								<spring-form:select path="scFasccodtesto" >
									<spring-form:option value=""></spring-form:option>
									<spring-form:options items="${mailtipoList}" itemLabel="descrizione" itemValue="id.codice"/>
								</spring-form:select>
								<init:help idHelp="help_scFasccodtesto" textKey="alberoproc.help.scFasccodtesto"/>
								<spring-form:errors path="scFasccodtesto" cssClass="error"/> 
							</td>
						</tr>
						<tr>
							<td><fmt:message key="alberoproc.label.scFascautomatica" /></td>
							<td colspan="3">
								<spring-form:select path="scFascautomatica" id="scFascautomatica_id" onblur="ConvalidaFascicolazione();" multiple="true" size="5"> 
									<spring-form:option value="8"  id="id_8"><fmt:message key='alberoproc.label.scFascautomatica_8' /></spring-form:option>
									<spring-form:option value="0"  id="id_0"><fmt:message key='alberoproc.label.scFascautomatica_0' /></spring-form:option>
									<spring-form:option value="1"  id="id_1"><fmt:message key='alberoproc.label.scFascautomatica_1' /></spring-form:option>
									<spring-form:option value="2"  id="id_2"><fmt:message key='alberoproc.label.scFascautomatica_2' /></spring-form:option>
									<spring-form:option value="4"  id="id_4"><fmt:message key='alberoproc.label.scFascautomatica_4' /></spring-form:option>
								</spring-form:select>
								<init:help idHelp="help_scFascautomatica" textKey="alberoproc.help.scFascautomatica"/>
								<fmt:message key="label.select_multiplo" />
								<spring-form:errors path="scFascautomatica" cssClass="error"/> 
							</td>
						</tr>
				</table>
				
				<script type='text/javascript'>
				
				
				fascautomatica(${alberoproc.scFascautomatica});
	
				function fascautomatica(valueProt){
					if($('scFascautomatica_id')){
						var protAut=valueProt;
						if(protAut){
							if(protAut==3){
			
								$('scFascautomatica_id').options[2].selected=true;
								$('scFascautomatica_id').options[3].selected=true;
							}
							if(protAut==5){
			
								$('scFascautomatica_id').options[2].selected=true;
								$('scFascautomatica_id').options[4].selected=true;
							}
							if(protAut==6){
			
								$('scFascautomatica_id').options[3].selected=true;
								$('scFascautomatica_id').options[4].selected=true;
							}
							if(protAut==7){
								$('scFascautomatica_id').options[2].selected=true;
								$('scFascautomatica_id').options[3].selected=true;
								$('scFascautomatica_id').options[4].selected=true;
							}
						}
					}
				}
				
				function ConvalidaFascicolazione(){
					//Verifica configurazione campo protocollazione automatica
					var somma = 0;
					var nonProtocollare = false;
					if( $('scFascautomatica_id') )	{
						for(var count=0; count < $('scFascautomatica_id').options.length; count++) { 
								if ($('scFascautomatica_id').options[count].selected){
								somma = somma + parseInt($('scFascautomatica_id').options[count].value);
								if($('scFascautomatica_id').options[count].value == 0){
									nonProtocollare = true;		   
								}
								}
						}
						if ((somma > 0) && nonProtocollare)
						{
							alert("Attenzione!! Non si può selezionare la voce \"Non fascicolare\" in modalità multipla.");
							return false;	 
						}
						if ((somma > 8))
						{
							alert("Attenzione!! Non si può selezionare la voce \"Controlla l'impostazione dei rami padre\" in modalità multipla.");
							return false;	 
						}
					}				
					return true;	
				}

				</script>
			</spring-form:form>
			<div id="functions">
			<ul>
				<li><a href="javascript:doSubmit('updateParametriFascicolazione.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
			</div>
	</div>
	
</body>
</html>