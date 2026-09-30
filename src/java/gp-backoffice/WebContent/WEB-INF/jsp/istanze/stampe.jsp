<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.stampa" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message
			key="stampe" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanze/view" />
		<jsp:param name="qs"
			value="codice%3D${istanzeCommand.entity.id.codice}%26software%3D${ istanzeCommand.entity.software.codice }" />
	</jsp:include>
	<div id="subcontent">
	<spring-form:form commandName="istanzeCommand" name="inner_inviodati">	
		<table id="cerca_documenti_tipo" width="100%" class="table">
			<tr>
				<td nowrap="nowrap" width="2%"><label><fmt:message
							key="stampe.codice" /></label></td>
							
							<td class="inline-ui-cell">
							 <jsp:include page="../includes/letteretipoSearch.jsp" >
									<jsp:param name="idElemento" value="letteretipo" />		
									<jsp:param name="propertyPath" value="letteretipo" />				
									<jsp:param name="pathPropertyDescription" value="letteretipo.descrizione" />
									<jsp:param name="pathPropertyCode" value="letteretipo.id.codice" />
									<jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />
									<jsp:param name="afterUpdateElement" value="getCodiceLetteraScelta" />
									<jsp:param name="titleKey" value="label.ricerca_documenti_tipo" />
									<jsp:param name="id_help" value="help_doc_tipo" />
								</jsp:include>
								<fmt:message key="help.ricerca_per_software_TT" />
								</td>
							
			</tr>
					<spring-form:hidden id="codiceIstanza_hidden" path="entity.id.codice" />
					<spring-form:hidden id="codiceLettera_hidden" path=""/>
		</table>
		<script type="text/javascript">
			function switchAutocompleterletteretipo(){
				if($('id_flag_letteretipo').checked){
				    $('id1_letteretipo').style.display="inline";
				    $('id2_letteretipo').style.display="none";
				    $('letteretipo_id2').value='';
				    $('letteretipo_hidden').value='';
				}else{
					$('id1_letteretipo').style.display="none";
					$('id2_letteretipo').style.display="inline";
					$('letteretipo_id1').value='';
				    $('letteretipo_hidden').value='';
				}
			}
			
			function getCodiceLetteraScelta(inputField,listItem){
				 $('codiceLettera_hidden').value=listItem.id;
			}
			
			function createAllegato(){
				doHref('../istanze/createLetteraTipo.htm?codiceIstanza='+$('codiceIstanza_hidden').value+'&codiceMovimento=${param.codiceMovimento}&codiceLetteraTipo='+ $('codiceLettera_hidden').value,'');
				enableFunctions();
			}
		</script>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:createAllegato();"><fmt:message key="label.stampa" /></a></li>
			<!--<li><a href="javascript:doHref('../istanze/view.htm?codice=${istanzeCommand.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>-->
			<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>