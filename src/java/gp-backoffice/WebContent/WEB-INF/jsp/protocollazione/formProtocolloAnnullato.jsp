<%@ include file="../includes/taglibs.jsp" %>
<c:if test="${empty errori}">
	<fieldset>
		<legend><fmt:message key="label.motivi_annullamento" /></legend> 
		<div style="width: 300px;"><b>${datiProtocolloAnnullato.motivoAnnullamento}</b></div>
	</fieldset>	
	<br />
	<fieldset>
		<legend><fmt:message key="label.note_annullamento" /></legend> 
		<div style="width: 300px;"><b>${datiProtocolloAnnullato.noteAnnullamento}</b></div>
	</fieldset>
</c:if>
<c:if test="${not empty errori}">
	${errori}
</c:if>