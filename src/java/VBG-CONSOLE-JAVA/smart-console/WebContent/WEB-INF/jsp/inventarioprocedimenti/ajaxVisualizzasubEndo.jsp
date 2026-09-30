<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${empty invprocEndos}">		
	<!-- non attivo -->
	</c:when>
	<c:otherwise>
		<div class="jmesa">
			<div>
				<fmt:message key="label.inventarioprocendo.title" />:&nbsp;<b>${inventarioprocedimenti.procedimento }</b>
			</div>
			<table class="table" style="width: 100%;">
				<thead>
					<tr class="header">			
						<td><fmt:message key="label.endoprocedimento" /></td>
						<td><fmt:message key="label.necessario" /></td>
						<td><fmt:message key="label.pubblica" /></td>
						<td><fmt:message key="label.disabilitato" /></td>
						<c:if test="${_COMUNIASSOCIATI_ eq true }">
							<td><fmt:message key="label.comune" /></td>					
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${invprocEndos}" var="endo_var" varStatus="a">
						<tr>
							<td>${endo_var.inventarioprocEndoD.procedimento}</td>
							<td>
								<c:if test="${endo_var.flagNecessario eq true}">
									<fmt:message key="label.si"/>
								</c:if>
								<c:if test="${endo_var.flagNecessario eq false}">
									<fmt:message key="label.no"/>
								</c:if>
							</td>							
							<td>
								<c:if test="${endo_var.flagPubblica eq true}">
									<fmt:message key="label.si"/>
								</c:if>
								<c:if test="${endo_var.flagPubblica eq false}">
									<fmt:message key="label.no"/>
								</c:if>
							</td>
							<td>
								<c:if test="${endo_var.inventarioprocEndoD.disabilitato eq true}">
									<fmt:message key="label.si"/>
								</c:if>
								<c:if test="${endo_var.inventarioprocEndoD.disabilitato eq false}">
									<fmt:message key="label.no"/>
								</c:if>
							</td>
							<c:if test="${_COMUNIASSOCIATI_ eq true }">
								<td>
									<c:choose>
										<c:when test="${empty endo_var.comune.comune}">
											<fmt:message key="label.tutti" />
										</c:when>
										<c:otherwise>${endo_var.comune.comune}</c:otherwise>
									
									</c:choose>
								</td>						
							  </c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
				
		</div>
	</c:otherwise>
</c:choose>				
