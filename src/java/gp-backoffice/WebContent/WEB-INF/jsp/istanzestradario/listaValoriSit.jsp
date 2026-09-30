<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:choose>
  	<c:when test="${isEccezioneRemota eq true}">
  		${errori}
  	</c:when>
 	<c:otherwise>
		<c:if test="${not empty errori}">
			${errori} <init:help idHelp="help_1${param.idCampo}" textKey="service_error.sit.valore_non_trovato.help"/>
		</c:if>
		<c:if test="${empty errori}">
			<c:if test="${empty listaValori}">
				<fmt:message key="html.statusbar.noResultsFound"/>
			</c:if>
		</c:if>
		<br class="clear" />		
		<c:if test="${not empty listaValori}">
		<c:choose>
			<c:when test="${fn:length(listaValori) > 1 or not empty errori or isClick eq 'true'}">									   
				   <div style="overflow: auto; height: 100px;">
					 <table style="width: 100%; border: 0px;  padding: 0px;" class="table">			
				        <%
					    	int i=0;
					    %>
						<tbody class="tbody">	
						<c:forEach items="${listaValori}" var="valore" varStatus="c">														
							<tr class="<%=(i%2)==0?"odd":"even"%>">
								<td style="min-height: 12px;">
								<c:choose>								    
								    <c:when test="${valore eq ' '}">
										<a href="javascript:assegna('${param.idCampo}','${valore}');" style="text-decoration: underline">&nbsp;</a>
								    </c:when>
								    <c:when test="${valore eq ''}">
								    	<a href="javascript:assegna('${param.idCampo}','${valore}');"> Senza Valore </a>
								    </c:when>
								    <c:otherwise>
								    	<a href="javascript:assegna('${param.idCampo}','${valore}');"> ${valore}</a>
								    </c:otherwise>
								</c:choose>
								</td>
							</tr>
						<%i++;%>
						</c:forEach>
						</tbody>		
				   </table>
				 </div>				 
				 <c:if test="${not empty errori}">		
					<div id="functions">
						<ul>
							<li><a href="javascript:confermaScelta();"><fmt:message key="label.conferma"/></a></li>
						</ul>
					</div>
				</c:if>
				<br class="clear" />
			</c:when>			
			<c:otherwise>
						<c:forEach items="${listaValori}" var="valore" varStatus="c">				
							<script type="text/javascript">
							jQuery(document).ready(function(){
								document.getElementById('${param.idCampo}').value = '${valore}';
					          	aggiornaCampiSit(document.getElementById('${param.idCampo}'));			          	
					          	dialogResult.hide();
					          	dialogResult.hide();
							});
							</script>								
						</c:forEach>	
			</c:otherwise>
		</c:choose>	
		</c:if>
	</c:otherwise>
</c:choose>
<div id="myFiltersValues" style="overflow: auto; height: 100px; display: none;"></div>