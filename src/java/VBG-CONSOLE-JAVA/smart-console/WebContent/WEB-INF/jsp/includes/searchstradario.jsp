<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>


<c:set var="stradarioInputSize" value="67" />
<c:if test="${not empty param.stradarioInputSize}">
	<c:set var="stradarioInputSize" value="${param.stradarioInputSize}" />
</c:if>
<c:set var="stradarioAutocompleterAjax" value="findStradario.htm" />
<c:if test="${not empty param.stradarioAutocompleterAjax}">
	<c:set var="stradarioAutocompleterAjax"
		value="${param.stradarioAutocompleterAjax}" />
</c:if>
<c:set var="stradarioMinChars" value="2" />
<c:if test="${not empty stradarioMinChars}">
	<c:set var="stradarioMinChars" value="${param.stradarioMinChars}" />
</c:if>
<c:set var="ajaxCallBack" value="" />
<c:if test="${not empty param.ajaxCallBack}">
	<c:set var="ajaxCallBack" value="${param.ajaxCallBack}" />
</c:if>
<c:set var="afterUpdateElement" value="" />
<c:if test="${not empty param.afterUpdateElement}">
	<c:set var="afterUpdateElement" value="${param.afterUpdateElement}" />
</c:if>
<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<%-- BEGIN SEZIONE ANAGRAFE PRINCIPALE --%>



<spring-form:input id="${param.idElemento}"
	path="${param.pathStradario}.descrizioneCompleta"
	cssClass="searchbox"
	onkeydown="javascript:return searchAll(this,event);"
	onchange="checkValue(this,'${param.idElemento}_hidden');"
	size="${stradarioInputSize}" />
<init:autocompleter methodAjax="${stradarioAutocompleterAjax}"
	idHidden="${param.idElemento}_hidden" idInput="${param.idElemento}"
	minChars="${stradarioMinChars}"
	inputTitleKey="label.ricerca_stradario"
	afterUpdateElement="${afterUpdateElement}"
	callBack="${ajaxCallBack}" />

<spring-form:hidden path="${param.pathStradario}.id.codice"
	id="${param.idElemento}_hidden" />
<spring-form:errors path="${param.pathStradario}" cssClass="error" />
<%-- END SEZIONE STRADARIO PRINCIPALE--%>


<%-- BEGIN ICONE FUNZIONALITA' --%>
<%-- se il parametro non è specificato --%>
<c:if test="${empty param.stradarioHideFunctions || param.stradarioHideFunctions eq false}">
	<a class="addColumn" style="float: none;" 
		href="javascript:nuovaStradario${param.idElemento}('${param.idElemento}_hidden');" 
		title='<fmt:message key="label.inserisci_stradario"/>'><label><fmt:message key="label.add.record.image" /></label></a>						
	<%-- END ICONE FUNZIONALITA' --%>
</c:if>
<%-- BEGIN JAVASCRIPT FUNZIONALITA' AGGIUNTIVE --%>
<script type="text/javascript">
		
				
		jQuery(document).ready(function(){
			isAllowedInsertStradario();
		});
		
		function isAllowedInsertStradario()
		{
			new Ajax.Request('${pageContext.request.contextPath}/stradario/ajaxIsAllowedInsertStardario.htm?ts_='+new Date().getTime(), {
				  method: 'post',	
				  onSuccess: function(transport){
					var isAllowed = transport.responseText;
					if(isAllowed=='true'){
						showDiv('imgAggiungi${param.idElemento}');
					}else{
						hideDiv('imgAggiungi${param.idElemento}');							
					}					
			      },
				  onFailure: function(transport){ 
					  console.error(transport.responseText);
				  }						    		 
			});
		}
				

		function nuovaStradario${param.idElemento}(objId){
			var caller = '${param.idElemento}';	
			var nuovaAnagrafe${param.idElemento}Win = window.open("<%=request.getContextPath()%>/stradario/popupcreate.htm?popupCaller="+caller,69,"status=1,menubar=0,scrollbars=1,width=800, height=600");					
			
		}
		
	</script>