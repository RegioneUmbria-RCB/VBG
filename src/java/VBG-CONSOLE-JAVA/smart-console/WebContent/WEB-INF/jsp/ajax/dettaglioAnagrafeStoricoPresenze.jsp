<%@ include file="../includes/taglibs.jsp" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><div id="ajax_container">
<b class="rnd_top"><b class="rnd_b1"></b><b class="rnd_b2"></b><b class="rnd_b3"></b><b class="rnd_b4"></b></b>
	<div class="rnd_content" >
		
			<fieldset><legend><fmt:message key="form.mercatiPresenzeStorico.dettaglioanagrafiche"/></legend>
				<table>
				<c:forEach items="${mercatipresenzeStoricoList}" var="storicopresenze_var">
					<tr style="background-color: white;">
						<td style="background-color: white;"><fmt:message key="form.mercatiPresenzeStorico.anagrafe" />:</td>
						<td style="background-color: white;"><b>${storicopresenze_var.anagrafe.descrizioneRichiedente}</b></td>
						<td style="background-color: white;"><fmt:message key="form.mercatiPresenzeStorico.presenzetotali" />:</td>
						<td style="background-color: white;"><b>${storicopresenze_var.presenzeTotali}</b></td>
					</tr>
				</c:forEach>
				</table>	
			</fieldset>
	</div>
<b class="rnd_bottom"><b class="rnd_b4"></b><b class="rnd_b3"></b><b class="rnd_b2"></b><b class="rnd_b1"></b></b>
</div>