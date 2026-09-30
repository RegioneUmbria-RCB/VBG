<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div class="parametriDiv">
	<div class="etichetta">
		<div><fmt:message key="label.anagrafe" />:</div>
		<div><fmt:message key="label.indirizzo" />:</div>
	</div>		
	<div class="parametro">       		 	
		<div>${anagrafe.descrizioneRichiedente}</div>
		<div>${anagrafe.descrizioneResidenza}</div>
	</div>
</div>