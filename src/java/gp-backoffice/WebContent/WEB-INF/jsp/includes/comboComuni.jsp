<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%
boolean useTable = true;

String renderFormGroup = request.getParameter("renderFormGroup");
if(renderFormGroup!=null){
    useTable = false;
}
String commandPropertyPathParam = request.getParameter("commandPropertyPath");
if(commandPropertyPathParam.indexOf(".")>0){
    commandPropertyPathParam = commandPropertyPathParam.replace(".","_");
}
%>
<c:set var="uniqueIdentifier" scope="page" value="<%= commandPropertyPathParam%>"/>

<%
String codiceComuneSelezionato = request.getParameter(request.getParameter("commandPropertyPath")+".codicecomune");
if(codiceComuneSelezionato==null || codiceComuneSelezionato.equals("")){
    codiceComuneSelezionato = request.getParameter("comune");
}
pageContext.setAttribute("codiceComuneSelezionatoVal", codiceComuneSelezionato);
%>

<c:if test="${fn:length(comuniassociatiListInRequest) > 0}">
	<c:if test="${fn:length(comuniassociatiListInRequest) > 1}">
	
	<%if (useTable){%>
			<tr>
				<td><fmt:message key="label.combocomuni" /></td>
				<td colspan="${param.colspan}">
	<%}else{%>
			<div class="form-group">
						<label><fmt:message key="label.combocomuni" /></label>
	<%}%>
				
					<c:choose>
						<c:when test="${param.readOnly eq 'true'}">
							<c:forEach items="${comuniassociatiListInRequest}" var="comune">
								<c:if test="${comune.codicecomune eq param.comune}">
									<input id="${uniqueIdentifier}" type="hidden" name="${param.commandPropertyPath}.codicecomune" value="${ comune.codicecomune }"/>
									<b>${comune.comune}</b>									
								</c:if>
							</c:forEach>							
						</c:when>
						<c:otherwise>
						
							<select name="${param.commandPropertyPath}.codicecomune" id="${uniqueIdentifier}">
								<c:if test="${param.mostraTutti eq 'true'}">									
									<option value="">
                                         <c:choose>
                                              <c:when test="${param.emptyLabelTutti eq 'true'}">
										<fmt:message key="label.select.default"/>
                                              </c:when>
                                              <c:otherwise>
                                                 <fmt:message key="label.tutti" />
                                              </c:otherwise>
                                         </c:choose>
                                    </option>
                                 </c:if>
                                    
									<c:forEach var="comuni" items="${comuniassociatiListInRequest}">
										<c:set var="comuneselected"></c:set>
										<c:if test="${ comuni.codicecomune eq codiceComuneSelezionatoVal}">
											<c:set var="comuneselected"> selected="selected" </c:set>
										</c:if>
										<option value="${comuni.codicecomune}" ${comuneselected}>${comuni.comune}</option>
									</c:forEach>
							</select>
							
						</c:otherwise>
					</c:choose>
	<%if (useTable){%>
			</td>
			</tr>
	<%}else{%>
			</div>
	<%}%>				
			
		</c:if>
		<c:if test="${fn:length(comuniassociatiListInRequest) == 1}">
			<c:forEach items="${comuniassociatiListInRequest}" var="comune">
				<input type="hidden" name="${param.commandPropertyPath}.codicecomune" value="${ comune.codicecomune }" id="${uniqueIdentifier}"/>
			</c:forEach>
		</c:if>		
</c:if>