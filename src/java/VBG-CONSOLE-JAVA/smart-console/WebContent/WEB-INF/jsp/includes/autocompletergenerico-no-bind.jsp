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
<c:set var="readOnly" value="false" />
<c:if test="${not empty param.readOnly}">
    <c:set var="readOnly" value="${param.readOnly}" />
</c:if>
<c:set var="inputIdOnChange" value="checkValue(this,'${param.idElemento}_hidden')" />
<c:if test="${not empty param.inputIdOnChange}">
    <c:set var="inputIdOnChange" value="${param.inputIdOnChange}" />
</c:if>
<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<%-- BEGIN SEZIONE RICERCA --%>
<c:if test="${readOnly eq false}">  
    <input id="${param.idElemento}_id"
        type="text"
        name="${param.pathPropertyDescription}"
        size="${autocompleterInputSize}"
        onchange="${inputIdOnChange}"
        class="searchbox ${param.cssClass}"
        onkeydown="return searchAll(this,event)" />
    <init:autocompleter methodAjax='${autocompleterAjax}'
        callBack="${ajaxCallBack}" afterUpdateElement="${afterUpdateElement}"
        idHidden="${param.idElemento}_hidden" idInput="${param.idElemento}_id"
        inputTitleKey="${param.titleKey}" minChars="${autocompleterMinChars}" />
        
</c:if>
<c:if test="${readOnly eq true}">   
    <input id="${param.idElemento}_id"
        name="${param.pathPropertyDescription}" 
        size="${autocompleterInputSize}" 
        class="${param.cssClass}"
        readonly="true" />

</c:if>

    <input type="hidden"  id="${param.idElemento}_hidden"
        name="${param.pathPropertyCode}" />     
<%-- END SEZIONE RICERCA --%>