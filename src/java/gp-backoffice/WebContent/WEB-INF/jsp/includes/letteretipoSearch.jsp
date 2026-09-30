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
<c:set var="onchangeCallback" value="checkValue(this,'${param.idElemento}_hidden')" />
<c:if test="${not empty param.onchangeCallback}">
	<c:set var="onchangeCallback" value="${param.onchangeCallback}" />
</c:if>
<c:if test="${empty param.propertyPath}">
	[letteretipoSearch.jsp]  Attenzione !! non è stato settato il parametro propertyPath.
</c:if>
<c:if test="${empty param.pathPropertyDescription}">
	[letteretipoSearch.jsp]  Attenzione !! non è stato settato il parametro pathPropertyDescription.
</c:if>
<c:if test="${empty param.pathPropertyCode}">
	[letteretipoSearch.jsp]  Attenzione !! non è stato settato il parametro pathPropertyCode.
</c:if>

<%-- 
<c:if test="${empty param.help}">
	[AutocompletergenericoTT.jsp]  Attenzione !! non è stato settato il parametro help.
</c:if>
--%>
<c:if test="${empty param.id_help}">
	[letteretipoSearch.jsp]  Attenzione !! non è stato settato il id_help.
</c:if>


<c:set var="readOnly" value="false" />
<c:if test="${not empty param.readOnly}">
	<c:set var="readOnly" value="${param.readOnly}" />
</c:if>
<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<%-- BEGIN SEZIONE RICERCA --%>
<c:if test="${readOnly eq false}">
	<div id="id1_${param.idElemento}" style="display:none;">
		<spring-form:input 
			id="${param.idElemento}_id1" 
			path="${param.pathPropertyDescription}" cssClass="searchbox"
			size="${autocompleterInputSize}"
			onchange="${onchangeCallback}"
			onkeydown="return searchAll(this,event)" />
		<init:autocompleter 
			methodAjax='findLettereTipo.htm?codicesoftware=TT'  
			idHidden="${param.idElemento}_hidden" 
			idInput="${param.idElemento}_id1" 
			inputTitleKey="${param.titleKey}" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
		
	</div>
	<div id="id2_${param.idElemento}" style="display:inline;">
		<spring-form:input 
			id="${param.idElemento}_id2" 
			path="${param.pathPropertyDescription}" cssClass="searchbox"
			size="${autocompleterInputSize}"
			onchange="${onchangeCallback}"
			onkeydown="return searchAll(this,event)" />
		<init:autocompleter 
			methodAjax='findLettereTipo.htm'  
			idHidden="${param.idElemento}_hidden"  
			idInput="${param.idElemento}_id2" 
			inputTitleKey="${param.titleKey}" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
			
	</div>
	<spring-form:errors path="${param.propertyPath}" cssClass="error" />
	<spring-form:hidden id="${param.idElemento}_hidden"
		path="${param.pathPropertyCode}" />
	
	
	<input type="checkbox" id="id_flag_${param.idElemento}" onclick="switchAutocompleterletteretipo();"/>
	<%--
	<input type="hidden" id="${param.idElemento}_software_hidden">
	<init:help idHelp="help_${param.id_help}" textKey="help.ricerca_per_software_TT"/>
	 --%>
	
</c:if>
<c:if test="${readOnly eq true}">
	<spring-form:input id="${param.idElemento}_id" size="${autocompleterInputSize}" 
			path="${param.propertyPath}" readonly="true"/>
	<spring-form:errors path="${param.propertyPath}" cssClass="error"/> 
	<spring-form:hidden 
		id="${param.idElemento}_hidden" 
		path="${param.propertyPath}.id.codice"  />

</c:if>