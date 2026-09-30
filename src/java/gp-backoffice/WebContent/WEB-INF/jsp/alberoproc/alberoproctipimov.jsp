<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="alberoproc.label.alberoproctipimovimento" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="alberoproc.label.alberoproctipimovimento" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	
	<div class="parametriDiv">
       		<div class="etichetta"> 
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionepadre}
	        	</div>
	        	<br />
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionebreve}
	        	</div>
	        </div>
    </div>
    <div class="clear"></div>
		<spring-form:form commandName="alberoprocMovimenti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocMovimenti" />
		    </jsp:include>
		    <spring-form:hidden path="alberoproc.id.codice" />
			<table>
				<tr>
					<td>
					    <fmt:message key="label.tipimovimento" /><label class="required">*</label>
					</td>
					<td class="inline-ui-cell">
							<script type="text/javascript">
								var searchTT = false;
								function tipomovimentoCallBack(inputField,listItem){
									var a = listItem.id;
									document.getElementById('tipimovimento_id').value = inputField.value;
									document.getElementById('tipimovimento_hidden').value = a;
								}
								function filterMovimentiForSoftware(element, entry) {
									if(searchTT){
										return entry + "&codice=TT";
									}
									return entry + "&codice=<%=ORMHelper.getSoftware()%>" ;
								}
								function tuttiSw(){
									searchTT = false;
									if($('tuttiSw_id').checked){
									    searchTT = true;
									}
								}
							</script>
					    <spring-form:input id="tipimovimento_id" path="tipimovimento.movimento" cssClass="searchbox" size="67" onchange="checkValue(this,'tipimovimento_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
					    <init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm'  idHidden="tipimovimento_hidden"  idInput="tipimovimento_id" callBack="filterMovimentiForSoftware" afterUpdateElement="tipomovimentoCallBack" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter>
						<spring-form:errors path="tipimovimento" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_hidden" path="tipimovimento.id.tipomovimento" />
						<input type="checkbox" id="tuttiSw_id" onclick="tuttiSw();" />
	                	<init:help idHelp="help4" textKey="help.movimenti_archivi_base" />
					</td>
			    </tr>
			    <tr>
			    	<td>
						<fmt:message key="label.amministrazione" /><label class="required">*</label>
					</td>
					<td class="inline-ui-cell">
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioni" />		
							<jsp:param name="propertyPath" value="amministrazioni" />				
							<jsp:param name="pathPropertyDescription" value="amministrazioni.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="amministrazioni.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=true" />	
							<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
						</jsp:include>		
					</td>
			    </tr>
			</table>
			
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:inserisci()"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=${alberoproc.id.codice}#artipisoggetto_anchor','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<script type="text/javascript">
	function inserisci(){
		if(jQuery('#amministrazioni_hidden').val().length <=0 || jQuery('#tipimovimento_hidden').val().length <=0){
			 alert("Tutti i parametri sono obbligatori");
		}else{
			doSubmit('insertTipimovimento.htm','',document.inviodati)
		}
	}
	
	</script>
</body>
</html>