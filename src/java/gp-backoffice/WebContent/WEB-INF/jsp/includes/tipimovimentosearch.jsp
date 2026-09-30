<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<c:set var="tipimovimentoInputSize" value="67"/>
<c:if test="${not empty param.tipimovimemtoInputSize}">
	<c:set var="tipimovimentoInputSize" value="${param.tipimovimentoInputSize}"/>
</c:if>
<c:set var="tipimovimentoAutocompleterAjax" value="findTipiMovimentoForSoftware.htm?codice="/>
<c:if test="${not empty param.tipimovimentoAutocompleterAjax}">
	<c:set var="tipimovimentoAutocompleterAjax" value="${param.tipimovimentoAutocompleterAjax}"/>
</c:if>
<c:set var="tipimovimentoMinChars" value="1"/>
<c:if test="${not empty param.tipimovimentoMinChars}">
	<c:set var="tipimovimentoMinChars" value="${param.tipimovimentoMinChars}"/>
</c:if>
<c:set var="ajaxCallBack" value="" />
<c:if test="${not empty param.ajaxCallBack}">
	<c:set var="ajaxCallBack" value="${param.ajaxCallBack}" />
</c:if>
<c:set var="afterUpdateElement" value="" />
<c:if test="${not empty param.afterUpdateElement}">
	<c:set var="afterUpdateElement" value="${param.afterUpdateElement}" />
</c:if>
<c:set var="readOnly" value="false" />
<c:if test="${not empty param.readOnly}">
	<c:set var="readOnly" value="${param.readOnly}" />
</c:if>
<c:set var="includiDisabilitate" value="false" />
<c:if test="${not empty param.includiDisabilitate}">
	<c:set var="includiDisabilitate" value="${param.includiDisabilitate}" />
</c:if>
<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<%-- BEGIN SEZIONE RICERCA --%>
<c:if test="${readOnly eq false}">
	<div id="id1_${param.idElemento}" style="display:none;">
		<spring-form:input 
			id="${param.idElemento}_id1" 
			path="${param.pathTipomovimento}.descrizioneEstesa" 
			cssClass="searchbox" size="${tipimovimentoInputSize}" 
			onchange="checkValue(this,'${param.idElemento}_hidden')" 
			onkeydown="return searchAll(this,event)" />
		<init:autocompleter 
			methodAjax='${tipimovimentoAutocompleterAjax}TT&includiDisabilitate=${includiDisabilitate}'  
			idHidden="${param.idElemento}_hidden" 
			idInput="${param.idElemento}_id1" 
			inputTitleKey="label.ricerca_tipimovimento" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
	</div>
	<div id="id2_${param.idElemento}" style="display:inline;">
		<spring-form:input 
			id="${param.idElemento}_id2" 
			path="${param.pathTipomovimento}.descrizioneEstesa" 
			cssClass="searchbox" 
			size="${tipimovimentoInputSize}" 
			onchange="checkValue(this,'${param.idElemento}_hidden')" 
			onkeydown="return searchAll(this,event)"/>
		<init:autocompleter 
			methodAjax='${tipimovimentoAutocompleterAjax}&includiDisabilitate=${includiDisabilitate}'  
			idHidden="${param.idElemento}_hidden"  
			idInput="${param.idElemento}_id2" 
			inputTitleKey="label.ricerca_tipimovimento" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
	</div>
	<spring-form:errors path="${param.pathTipomovimento}" cssClass="error"/> 
	<spring-form:hidden 
		id="${param.idElemento}_hidden" 
		path="${param.pathTipomovimento}.id.tipomovimento"  />
	
	<input type="checkbox" id="id_flag_${param.idElemento}" onclick="switchAutocompleter${param.idElemento}();"/>
	<init:help idHelp="help${param.idElemento}_id" textKey="help.tipimovimenti_archivi_base"/>
	
	<%-- END SEZIONE RICERCA --%>
	
	<script type="text/javascript">
		var switchAutocompleter${param.idElemento}  = function(){
			if($('id_flag_${param.idElemento}').checked){
			    $('id1_${param.idElemento}').style.display="inline";
			    $('id2_${param.idElemento}').style.display="none";
			}else{
				$('id1_${param.idElemento}').style.display="none";
				$('id2_${param.idElemento}').style.display="inline";
			}
		}	
	</script>
</c:if>
<c:if test="${readOnly eq true}">
	<spring-form:input id="${param.idElemento}_id" size="${tipimovimentoInputSize}" 
			path="${param.pathTipomovimento}.descrizioneEstesa" readonly="true"/>
	<spring-form:errors path="${param.pathTipomovimento}" cssClass="error"/> 
	<spring-form:hidden 
		id="${param.idElemento}_hidden" 
		path="${param.pathTipomovimento}.id.tipomovimento"  />

</c:if>