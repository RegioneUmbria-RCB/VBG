<%@ include file="../includes/taglibs.jsp" %>
<c:forEach items="${listaMov}" var="tm_var">
	<div style="padding: 1px;">
		<span><a class="eliminaRiga" style="float: none;" 
			href="javascript:eliminaResptm('${tm_var.id.codiceresponsabile}','${tm_var.id.tipomovimento}','${tipo}')" 
			title="<fmt:message key="label.elimina" /> ${tm_var.id.tipomovimento}">
		      	 <label><fmt:message key="label.elimina.image" /></label>
		</a></span>
		<span style="font-weight: bold;">${tm_var.tipimovimento.descrizioneEstesa} (${tm_var.tipimovimento.software.descrizione})</span>
	</div>
</c:forEach>