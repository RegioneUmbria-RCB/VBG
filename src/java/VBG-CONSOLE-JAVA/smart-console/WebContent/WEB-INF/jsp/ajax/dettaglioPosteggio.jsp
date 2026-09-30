<%@ include file="../includes/taglibs.jsp" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><div id="ajax_container">
<b class="rnd_top"><b class="rnd_b1"></b><b class="rnd_b2"></b><b class="rnd_b3"></b><b class="rnd_b4"></b></b>
	<div class="rnd_content">
		
		
			<fieldset><legend><fmt:message key="label.dettaglio_posteggio"/></legend>
				<table>
					<tr>
						<td><fmt:message key="label.tipo_spazio" /></td>
						<td><b>${posteggio.tipoSpazio.tipospazio}</b></td>
					</tr>
					<tr>
						<td><fmt:message key="label.larghezza" /></td>
						<td>
						<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggio.larghezza}"/>
						<c:if test="${posteggio.larghezza!=null}">
						<fmt:message key="label.metri" />
						</c:if>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.lunghezza" /></td>
						<td>
						<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggio.lunghezza}"/>
						<c:if test="${posteggio.lunghezza!=null}">
						<fmt:message key="label.metri" />
						</c:if>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.superficie" /></td>
						<td>
						<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggio.superficie}"/>
						<c:if test="${posteggio.superficie!=null}">
						<fmt:message key="label.metri_quadri" />
						</c:if>
						</td>
					</tr>
				</table>	
			</fieldset>
			
			
	</div>
<b class="rnd_bottom"><b class="rnd_b4"></b><b class="rnd_b3"></b><b class="rnd_b2"></b><b class="rnd_b1"></b></b>
</div>