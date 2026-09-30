<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="commedilizietipologie.label.tipologiedett" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="commedilizietipologie.label.tipologiedett" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	
<script type="text/javascript">
	function tuttiSw(){
		if($('id_flag').checked){
		    $('id1').style.display="inline";
		    $('id2').style.display="none";
		}else
		{
			$('id1').style.display="none";
			$('id2').style.display="inline";
		}
		
	}	
</script>
   <%
      String  mittente="display:appear;";
      String  destinatario="display:appear;";
      String  swSettato="display:none;";
      String  swTT="display:inline;";
    %>
	<div id="subcontent">
		<spring-form:form commandName="commedilizietipologiedett" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizietipologiedett" />
		    </jsp:include>
		    <div class="vbg-form">
		    	<fieldset>
		    		<legend><fmt:message key="label.tipimovimento" /></legend>
		    		
		    		<div class="form-group">
		    			<label><fmt:message key="label.software" /></label>
		    							    				
		            	<select id="software_id" name="filtrosoftware"  >
		            		<c:forEach items="${softareAttiviDaConfiguare}" var="softw">
		            			<option value="${softw.codice}">${softw.descrizione}</option>
		            		</c:forEach>		            		
						</select>
							
		    		</div>
		    		<div class="form-group">
		    			<label><fmt:message key="label.tipimovimento" /></label>
		    			
		    			
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
									var selectObj = document.getElementById('software_id');
									var software=getSelectTextAndValue(selectObj);
									//alert(software);
									return entry + "&codice=" + software[0];
								}
								function tuttiSw(){
									searchTT = false;
									if(document.getElementById('tuttiSw_id').checked){
									    searchTT = true;
									}
								}
							</script>		    			
		    			
		    			
		    			<spring-form:input id="tipimovimento_id" path="tipimovimento.movimento" cssClass="searchbox" size="67" onchange="checkValue(this,'tipimovimento_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
					    <init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm'  idHidden="tipimovimento_hidden"  idInput="tipimovimento_id" callBack="filterMovimentiForSoftware" afterUpdateElement="tipomovimentoCallBack" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter>
						<spring-form:errors path="tipimovimento.movimento" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_hidden" path="tipimovimento.id.tipomovimento" />
						<input type="checkbox" id="tuttiSw_id" onclick="tuttiSw();" />
	                	<init:help idHelp="help4" textKey="help.movimenti_archivi_base" />
		    			
		    		</div>
		    		
		    	</fieldset>
		    </div>			
			<script type='text/javascript'>
				

				function convalida(){
					if(document.getElementById('tipimovimento_hidden').value==''){
						alert('<fmt:message key="field.required" />');
						return false;
					}	
					return true;
				}
			</script>	
		</spring-form:form>
	</div>
	<div >		
		<a class="btn btn-primary" href="javascript:if(convalida()){doSubmit('insertDettaglio.htm?codice=${param.codice}','',document.inviodati);}"><fmt:message key="button.insert" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('view.htm?codice=${param.codice}','')"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>