<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div id="ajax_container" >
	<b class="rnd_top"><b class="rnd_b1"></b><b class="rnd_b2"></b><b class="rnd_b3"></b><b class="rnd_b4"></b></b>
	<div class="rnd_content" >	
			<a onclick="chiudiTabMessaggio(${messaggio.id.codice});" title="<fmt:message key="label.chiudi" /> ">
				<img src="${pageContext.request.contextPath}/images/cross.gif" alt="<fmt:message key="label.chiudi" /> " align="right" height="10"/>
			</a>	
			<br>	
			<fieldset><legend><fmt:message key="messaggi.label.dettaglio_messaggio"/></legend>					
				<table >									
					<tr>
						<td><fmt:message key="label.data" /></td>
						<td><b><fmt:formatDate pattern="<%= WebConstants.DATE_FORMAT_PATTERN %>" value="${messaggio.dataMessaggio}"/></b></td>
					</tr>
					<tr>
						<td><fmt:message key="messaggi.label.autore" /></td>
						<td><b>${messaggio.autore}</b></td>
					</tr>
					<tr>
						<td><fmt:message key="messaggi.label.oggetto" /></td>
						<td><b>${messaggio.oggetto}</b></td>
					</tr>
					<tr>
						<td></td>
						<td><b>${messaggio.corpo}</b></td>
					</tr>	
				</table>					
			</fieldset>					
	</div>	
	<b class="rnd_bottom"><b class="rnd_b4"></b><b class="rnd_b3"></b><b class="rnd_b2"></b><b class="rnd_b1"></b></b>
</div>