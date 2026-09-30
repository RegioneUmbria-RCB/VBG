<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="form.gestionepresenze.title.searchassenze" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.gestionepresenze.title.searchassenze" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>
<div id="subcontent">

<%
String  mercatiUso="display:none;";
%>
	
	<spring-form:form commandName="assenzeFilter" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="assenzeFilter" />
    </jsp:include>
    <table>
		<tr>
            <td><fmt:message key="form.gestionepresenze.anno" /></td>
            <td>
                <spring-form:select id="anno_id"  path="anno">
				    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${anniList}" itemValue="anno" itemLabel="anno" />
				</spring-form:select>
			</td>
     	</tr>
		<tr>
			<td>
				<fmt:message key="form.gestionepresenze.mercato" />
			</td>
			<td>
				<script type='text/javascript'>
					function setHiddenFieldmercati(inputField,listItem){
						var a = listItem.id;
						document.getElementById('mercati_id').value = inputField.value;
						document.getElementById('mercati_hidden').value = a;
						$('mercati_id_choices').fade();	 
						mercatiUsoDisplay();
					}
				</script>
				<spring-form:input id="mercati_id" path="mercati.descrizione" cssClass="searchbox" onchange="checkValue(this,'mercati_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67"/>
				<init:autocompleter methodAjax="findMercati.htm" afterUpdateElement="setHiddenFieldmercati" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
				<spring-form:errors path="mercati" cssClass="error"/> 
				<spring-form:hidden id="mercati_hidden" path="mercati.id.codice" />
			</td>
		</tr>
        <tr id="mercatiUso" style="<%=mercatiUso%>">
			<td><fmt:message key="form.gestionepresenze.mercatiUso" /></td>
			<td>
				<spring-form:select tabindex="9" id="selectMercatiUso" path="mercatiUso.id.codice">
                   <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
				   <spring-form:option value="${registrazioniFilter.mercatiUso.id.codice}" label="${registrazioniFilter.mercatiUso.descrizione}"></spring-form:option>
				</spring-form:select>
				<spring-form:errors path="mercatiUso.id.codice" cssClass="error"/> 
			</td>
		</tr>
       <tr>
			<td>
				<fmt:message key="form.gestionepresenze.assenze" />
			</td>
			<td>
				<spring-form:input cssStyle="text-align: right" id="assenze_id" path="assenze" size="8" onchange="checkNumberValue(this,true);"/>
				<spring-form:errors path="assenze" cssClass="error"/> 
			</td>
		</tr>
	</table>
	
   
	<script type='text/javascript'>
	$('anno_id').focus();
    <%-- Script per la visualizzazione degli usi una volta scelto il mercato--%>
	function removeOptionSelected(opt)
	{
	  var elSel = document.getElementById(opt);
	  var i;
	  for (i = elSel.length - 1; i>=0; i--) {
	    if (elSel.options[i]) {
	       elSel.remove(i);
	    }
	  }
	}

	function mercatiUsoDisplay(){
		removeOptionSelected("selectMercatiUso");
		var idMercato=document.getElementById("mercati_hidden").value;
		new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiUso.htm', {
			  method: 'post',
			  parameters: {code: idMercato, limit: 12},
			  onSuccess: function(transport){
				  var response = transport.responseText;
				  $("mercatiUso").appear();
				  var opts=response.split(",");
				  for(var i=0;i<((opts.length)-1); i++ ){
					  var j=i+1;
					  try{
					  	$('selectMercatiUso').add(new Option( opts[j], opts[i]),  $('selectMercatiUso').options[i]);
					  }
					  catch(e){ //in IE, try the below version instead of add()
						  $('selectMercatiUso').add(new Option( opts[j], opts[i]));
					  }
					  i++;
				  }
			    },
			  onFailure: function(){  }
							  
			  });
	}
		
		<%-- Script che controlla che sia inserito un numero nel campo numero presenze--%>
		function checkSubmit(){

        var result='true';
		if ($('assenze_id').value == ''){
			alert('<fmt:message key="field.numeropresenze.required" />');
			
			result= false;
		}
		if ($('anno_id').value == ''){
			alert('<fmt:message key="field.anno.required" />');

			result=false;
		
		}
		return result; 	
	}	
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:if(checkSubmit()){doSubmit('controlloAssenze.htm','',document.inviodati)}"><fmt:message key="button.search" /></a></li> 
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>