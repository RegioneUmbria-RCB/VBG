<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<c:set var="dyn2campiInputSize" value="67"/>
<c:if test="${not empty param.dyn2campiInputSize}">
	<c:set var="dyn2campiInputSize" value="${param.dyn2campiInputSize}"/>
</c:if>
<c:set var="dyn2campiAutocompleterAjax" value="findDyn2CampiCurrentSoftwareOrTT.htm?codiceSoftware="/>
<c:if test="${not empty param.dyn2campiAutocompleterAjax}">
	<c:set var="dyn2campiAutocompleterAjax" value="${param.dyn2campiAutocompleterAjax}"/>
</c:if>
<c:set var="dyn2campiMinChars" value="1"/>
<c:if test="${not empty dyn2campiMinChars}">
	<c:set var="dyn2campiMinChars" value="${param.dyn2campiMinChars}"/>
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
<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<%-- BEGIN SEZIONE RICERCA --%>
<c:if test="${readOnly eq false}">
	<div id="id1_${param.idElemento}" style="display:none;">
		<spring-form:input 
			id="${param.idElemento}_id1" 
			path="${param.pathdyn2campi}.nomecampo" 
			cssClass="searchbox" size="${dyn2campiInputSize}" 
			onblur=""
			onchange="checkValue(this,'${param.idElemento}_hidden');" 
			onkeydown="return searchAll(this,event)" />
		<init:autocompleter 
			methodAjax='${dyn2campiAutocompleterAjax}TT'  
			idHidden="${param.idElemento}_hidden" 
			idInput="${param.idElemento}_id1" 
			inputTitleKey="label.ricerca_tipimovimento" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
	</div>
	<div id="id2_${param.idElemento}" style="display:inline;">
		<spring-form:input 
			id="${param.idElemento}_id2" 
			path="${param.pathdyn2campi}.nomecampo" 
			cssClass="searchbox" 
			size="${dyn2campiInputSize}"
			onblur="" 
			onchange="checkValue(this,'${param.idElemento}_hidden');" 
			onkeydown="return searchAll(this,event);"/>
		<init:autocompleter 
			methodAjax='${dyn2campiAutocompleterAjax}'  
			idHidden="${param.idElemento}_hidden"  
			idInput="${param.idElemento}_id2" 
			inputTitleKey="label.ricerca_tipi_causali_oneri" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
	</div>
	<spring-form:errors path="${param.pathdyn2campi}" cssClass="error"/> 
	<spring-form:hidden 
		id="${param.idElemento}_hidden" 
		path="${param.pathdyn2campi}.id.codice"  />
	
	<input type="checkbox" id="id_flag_${param.idElemento}" onclick="switchAutocompleter${param.idElemento}();"/>
	<init:help idHelp="helpCausaliOneri" textKey="help.causali_oneri_archivi_base"/>
	
	<%-- END SEZIONE RICERCA --%>
	
	<script type="text/javascript">
		function switchAutocompleter${param.idElemento}(){
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
	<spring-form:input id="${param.idElemento}_id" size="${dyn2campiInputSize}" 
			path="${param.pathdyn2campi}.nomecampo" readonly="true"/>
	<spring-form:errors path="${param.pathdyn2campi}" cssClass="error"/> 
	<spring-form:hidden 
		id="${param.idElemento}_hidden" 
		path="${param.pathdyn2campi}.id.codice"  />

</c:if>