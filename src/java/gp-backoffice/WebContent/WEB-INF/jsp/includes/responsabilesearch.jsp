<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<c:set var="autocompleterInputSize" value="67" />
<c:if test="${not empty param.autocompleterInputSize}">
	<c:set var="autocompleterInputSize"
		value="${param.autocompleterInputSize}" />
</c:if>


<c:set var="tipologiaResponsabilie" value="" />
<c:if test="${not empty param.tipologiaResponsabilie}">
	<c:set var="tipologiaResponsabilie" value="${param.tipologiaResponsabilie}" />
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

<c:if test="${empty param.propertyPath}">
	[Resposabilesearch.jsp]  Attenzione !! non è stato settato il parametro propertyPath.
</c:if>
<c:if test="${empty param.pathPropertyDescription}">
	[Resposabilesearch.jsp]  Attenzione !! non è stato settato il parametro pathPropertyDescription.
</c:if>
<c:if test="${empty param.pathPropertyCode}">
	[Resposabilesearch.jsp]  Attenzione !! non è stato settato il parametro pathPropertyCode.
</c:if>

<c:if test="${empty param.id_help}">
	[Resposabilesearch.jsp]  Attenzione !! non è stato settato il id_help.
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
			onchange="checkValue(this,'${param.idElemento}_hidden')"
			onkeydown="return searchAll(this,event)" />
		<init:autocompleter 
			methodAjax='findResponsabili.htm'  
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
			onchange="checkValue(this,'${param.idElemento}_hidden')"
			onkeydown="return searchAll(this,event)" />
		<init:autocompleter 
			methodAjax='findResponsabili.htm?tipologiaResponsabilie=${param.tipologiaResponsabilie}'  
			idHidden="${param.idElemento}_hidden"  
			idInput="${param.idElemento}_id2" 
			inputTitleKey="${param.titleKey}" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
			
	</div>
	<spring-form:errors path="${param.propertyPath}" cssClass="error" />
	<spring-form:hidden id="${param.idElemento}_hidden"
		path="${param.pathPropertyCode}" />
	
	
	<input type="checkbox" id="id_flag_${param.idElemento}" onclick="switchAutocompleter${param.idElemento}();" tabIndex="-1" />
	<input type="hidden" id="${param.idElemento}_software_hidden">
	<!--<init:help idHelp="help_${param.id_help}" textKey="help.ricerca_responsabile"/> -->
	
	<%-- END SEZIONE RICERCA --%>
	
	<script type="text/javascript">
		function switchAutocompleter${param.idElemento}(){
			if($('id_flag_${param.idElemento}').checked){
			    $('id1_${param.idElemento}').style.display="inline";
			    $('id2_${param.idElemento}').style.display="none";
			    $('${param.idElemento}_id2').value='';
			    $('${param.idElemento}_hidden').value='';
			    $('${param.idElemento}_software_hidden').value='';
			    
			}else{
				$('id1_${param.idElemento}').style.display="none";
				$('id2_${param.idElemento}').style.display="inline";
				$('${param.idElemento}_id1').value='';
			    $('${param.idElemento}_hidden').value='';
			    $('${param.idElemento}_software_hidden').value='';
			}
		}
		
	</script>
</c:if>
<c:if test="${readOnly eq true}">
	<spring-form:input id="${param.idElemento}_id" size="${autocompleterInputSize}" 
			path="${param.propertyPath}" readonly="true"/>
	<spring-form:errors path="${param.propertyPath}" cssClass="error"/> 
	<spring-form:hidden 
		id="${param.idElemento}_hidden" 
		path="${param.propertyPath}.id.codice"  />

</c:if>