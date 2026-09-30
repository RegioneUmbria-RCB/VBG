<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<c:set var="autocompleterInputSize" value="67" />
<c:if test="${not empty param.autocompleterInputSize}">
	<c:set var="autocompleterInputSize"
		value="${param.autocompleterInputSize}" />
</c:if>
<c:set var="autocompleterAjax" value="" />
<c:if test="${not empty param.autocompleterAjax}">
	<c:set var="autocompleterAjax" value="${param.autocompleterAjax}" />
</c:if>
<c:set var="autocompleterMinChars" value="1" />
<c:if test="${not empty param.autocompleterMinChars}">
	<c:set var="autocompleterMinChars"
		value="${param.autocompleterMinChars}" />
</c:if>
<c:set var="ajaxCallBack" value="" />
<c:if test="${not empty param.ajaxCallBack}">
	<c:set var="ajaxCallBack" value="${param.ajaxCallBack}" />
</c:if>
<c:set var="afterUpdateElement" value="" />
<c:if test="${not empty param.afterUpdateElement}">
	<c:set var="afterUpdateElement" value="${param.afterUpdateElement}" />
</c:if>
<c:set var="codicesoftware" value="" />
<c:if test="${not empty param.autocompleterAjax}">
	<c:set var="autocompleterAjax" value="${param.autocompleterAjax}" />
</c:if>
<c:if test="${empty param.pathPropertyDescription}">
	[autocompletergenerico-no-bind-TT.jsp]  Attenzione !! non è stato settato il parametro pathPropertyDescription.
</c:if>
<c:if test="${empty param.pathPropertyCode}">
	[autocompletergenerico-no-bind-TT.jsp]  Attenzione !! non è stato settato il parametro pathPropertyCode.
</c:if>
<c:if test="${empty param.id_help}">
	[autocompletergenerico-no-bind-TT.jsp]  Attenzione !! non è stato settato il id_help.
</c:if>


<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<%-- BEGIN SEZIONE RICERCA --%>

	<div id="id1_${param.idElemento}" style="display:none;">
		<input type="text" 
			id="${param.idElemento}_id1" 
			name="${param.pathPropertyDescription}" class="searchbox"
			size="${autocompleterInputSize}"
			onchange="checkValue(this,'${param.idElemento}_hidden')"
			onkeydown="return searchAll(this,event)" />
		<init:autocompleter 
			methodAjax='${autocompleterAjax}TT'  
			idHidden="${param.idElemento}_hidden" 
			idInput="${param.idElemento}_id1" 
			inputTitleKey="${param.titleKey}" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
		
	</div>
	<div id="id2_${param.idElemento}" style="display:inline;">
		<input type="text"  
			id="${param.idElemento}_id2" 
			name="${param.pathPropertyDescription}" class="searchbox"
			size="${autocompleterInputSize}"
			onchange="checkValue(this,'${param.idElemento}_hidden')"
			onkeydown="return searchAll(this,event)" />
		<init:autocompleter 
			methodAjax='${autocompleterAjax}'  
			idHidden="${param.idElemento}_hidden"  
			idInput="${param.idElemento}_id2" 
			inputTitleKey="${param.titleKey}" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
			
	</div>
	
	<input type="hidden"  id="${param.idElemento}_hidden"
		name="${param.pathPropertyCode}" />
	
	
	<input type="checkbox" id="id_flag_${param.idElemento}" onclick="switchAutocompleter${param.idElemento}();" tabIndex="-1" />
	<input type="hidden" id="${param.idElemento}_software_hidden">
	<init:help idHelp="help_${param.id_help}" textKey="help.ricerca_per_software_TT"/>
	
	<%-- END SEZIONE RICERCA --%>
	
	<script type="text/javascript">
		document.querySelector('#${param.idElemento}_id1').disabled  = true;
		function switchAutocompleter${param.idElemento}(){
			
			if(document.querySelector('#id_flag_${param.idElemento}').checked){
				document.querySelector('#id1_${param.idElemento}').style.display="inline";
				document.querySelector('#id2_${param.idElemento}').style.display="none";
				document.querySelector('#${param.idElemento}_id2').disabled = true;
				document.querySelector('#${param.idElemento}_id1').disabled = false;
				document.querySelector('#${param.idElemento}_id2').value='';
				document.querySelector('#${param.idElemento}_hidden').value='';
				document.querySelector('#${param.idElemento}_software_hidden').value='TT';
				
			}else{
				document.querySelector('#id1_${param.idElemento}').style.display="none";
				document.querySelector('#${param.idElemento}_id1').disabled = true;
				document.querySelector('#${param.idElemento}_id2').disabled = false;
				document.querySelector('#id2_${param.idElemento}').style.display="inline";
				document.querySelector('#${param.idElemento}_id1').value='';
				document.querySelector('#${param.idElemento}_hidden').value='';
				document.querySelector('#${param.idElemento}_software_hidden').value='';
			}
		}
		
	</script>
