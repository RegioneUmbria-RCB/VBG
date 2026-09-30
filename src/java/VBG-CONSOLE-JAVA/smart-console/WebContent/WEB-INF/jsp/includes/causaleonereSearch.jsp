<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<c:set var="causaleonereInputSize" value="67"/>
<c:if test="${not empty param.causaleonereInputSize}">
	<c:set var="causaleonereInputSize" value="${param.causaleonereInputSize}"/>
</c:if>
<c:set var="causaleonereAutocompleterAjax" value="findTipicausalioneri.htm?codice="/>
<c:if test="${not empty param.causaleonereAutocompleterAjax}">
	<c:set var="causaleonereAutocompleterAjax" value="${param.causaleonereAutocompleterAjax}"/>
</c:if>
<c:set var="causalioneriMinChars" value="1"/>
<c:if test="${not empty causalioneriMinChars}">
	<c:set var="causalioneriMinChars" value="${param.causalioneriMinChars}"/>
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
			path="${param.pathCausaleonere}.coDescrizione" 
			cssClass="searchbox" size="${causaleonereInputSize}" 
			onblur="entrataUscitaAttivo('${param.idElemento}_hidden');"
			onchange="checkValue(this,'${param.idElemento}_hidden');" 
			onkeydown="return searchAll(this,event)" />
		<init:autocompleter 
			methodAjax='${causaleonereAutocompleterAjax}TT'  
			idHidden="${param.idElemento}_hidden" 
			idInput="${param.idElemento}_id1" 
			inputTitleKey="label.ricerca_tipimovimento" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
	</div>
	<div id="id2_${param.idElemento}" style="display:inline;">
		<spring-form:input 
			id="${param.idElemento}_id2" 
			path="${param.pathCausaleonere}.coDescrizione" 
			cssClass="searchbox" 
			size="${causaleonereInputSize}"
			onblur="entrataUscitaAttivo('${param.idElemento}_hidden');" 
			onchange="checkValue(this,'${param.idElemento}_hidden');" 
			onkeydown="return searchAll(this,event);"/>
		<init:autocompleter 
			methodAjax='${causaleonereAutocompleterAjax}'  
			idHidden="${param.idElemento}_hidden"  
			idInput="${param.idElemento}_id2" 
			inputTitleKey="label.ricerca_tipi_causali_oneri" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="${afterUpdateElement}"/>
	</div>
	<spring-form:errors path="${param.pathCausaleonere}" cssClass="error"/> 
	<spring-form:hidden 
		id="${param.idElemento}_hidden" 
		path="${param.pathCausaleonere}.id.codice"  />
	
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
		function entrataUscitaAttivo(){
			var codiceCausale=document.getElementById('${param.idElemento}_hidden');
			
			new Ajax.Request(
			'${pageContext.request.contextPath}/istanzeoneri/ajaxIsCausaleCollegabileAdEndo.htm?codiceCausale='+ codiceCausale.value,
			{
				method : 'post',
				onSuccess : function(transport) {
					var response = transport.responseText;
					if(response=='no')
					{
						 $('id_flagentrata_uscita').style.display="none";
						 $('id_flag_entrata').style.display="";
						 $('id_tr_amministrazione').style.display="none";
						 $('id_tr_endo').style.display="none";
						 $('id_td_perc_ribasso').style.display = "none";
						 $('inventarioprocedimenti_hidden').value='';
						 $('inventarioprocedimenti_id').value='';
						 $('amministrazioni_hidden').value='';
						 $('amministrazioni_id').value='';
						 $('id_tr_importo_istruttoria').style.display = "none";
					}else
					{
						 $('id_flagentrata_uscita').style.display="";
						 $('id_flag_entrata').style.display="none";
						 $('id_tr_amministrazione').style.display="";
						 $('id_tr_endo').style.display="";
						 //$('id_td_perc_ribasso').style.display = "";
						 
						 
					}
				},
				onFailure : function(transport) {
					var response = transport.responseText;
					alert(response);
				}
				});
			}
	</script>
</c:if>
<c:if test="${readOnly eq true}">
	<spring-form:input id="${param.idElemento}_id" size="${causaleonereInputSize}" 
			path="${param.pathCausaleonere}.coDescrizione" readonly="true"/>
	<spring-form:errors path="${param.pathCausaleonere}" cssClass="error"/> 
	<spring-form:hidden 
		id="${param.idElemento}_hidden" 
		path="${param.pathCausaleonere}.id.codice"  />

</c:if>