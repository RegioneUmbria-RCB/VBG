<%@page import="org.apache.commons.lang.BooleanUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Responsabili"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%
String commandPropertyPathParam = request.getParameter("commandPropertyPath");
if(commandPropertyPathParam.indexOf(".")>0){
    commandPropertyPathParam = commandPropertyPathParam.replace(".","_");
}


Responsabili userLogged = (Responsabili)session.getAttribute(WebConstants.USER_lOGGED_SESSION_VARIABLE_NAME);

boolean showTutti = false;
if(userLogged!=null){
    
    if(BooleanUtils.isTrue(userLogged.getFlagGesttutticomuni())){
		showTutti = true;
    }
}


%>
<c:set var="uniqueIdentifier" scope="page" value="<%= commandPropertyPathParam%>"/>

<c:if test="${fn:length(comuniassociatiListInRequest) > 0}">
<%--
	<c:if test="${fn:length(comuniassociatiListInRequest) > 1}">
--%>
			<tr>
				<td><fmt:message key="label.combocomuni" /></td>
				<td colspan="${param.colspan}">
					<c:choose>
						<c:when test="${param.readOnly eq 'true'}">
							<%--
							<c:forEach items="${comuniassociatiListInRequest}" var="comune">
								<c:if test="${comune.codicecomune eq param.comune}">
									<input id="${uniqueIdentifier}" type="hidden" name="${param.commandPropertyPath}.codicecomune" value="${ comune.codicecomune }"/>
									<b>${comune.comune}</b>									
								</c:if>
							</c:forEach>
							 --%>			
							 		<spring-form:hidden path="${param.commandPropertyPath}.codicecomune"/>	
							 		<spring-form:input path="${param.commandPropertyPath}.comune" readonly="true" size="50" cssStyle="border: none; font-weight:bolder"/>
						</c:when>
						<c:otherwise>
							<spring-form:select path="${param.commandPropertyPath}.codicecomune" id="${uniqueIdentifier}">
								<%if(showTutti){ %>						
									<spring-form:option value="">
                                            <c:choose>
                                                 <c:when test="${param.emptyLabelTutti eq 'true'}">
													<fmt:message key="label.select.default"/>
                                                 </c:when>
                                                 <c:otherwise>
                                                    <fmt:message key="label.tutti" />
                                                 </c:otherwise>
                                            </c:choose>
                                    </spring-form:option>
								<%} %>
								<spring-form:options items="${comuniassociatiListInRequest}" itemLabel="comune" itemValue="codicecomune"/>
							</spring-form:select>
							<spring-form:errors path="${param.commandPropertyPath}" cssClass="error"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<%--
		</c:if>
 		
		<c:if test="${fn:length(comuniassociatiListInRequest) == 1}">
			<c:forEach items="${comuniassociatiListInRequest}" var="comune">
				<input type="hidden" name="${param.commandPropertyPath}.codicecomune" value="${ comune.codicecomune }" id="${uniqueIdentifier}"/>
			</c:forEach>
		</c:if>	
		 --%>	
</c:if>