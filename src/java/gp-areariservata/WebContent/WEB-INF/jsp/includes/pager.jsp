<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<script type="text/javascript">
			$(document).ready(function(){
				$(".step").button();
				$(".disabilitato").button("disable");
				var label = $( ".corrente" ).button( "option", "label" );
				$( ".corrente" ).button( "option", "label", "<b style='font-size:1.3em'>"+label+"</b>" );
			});
</script>
<div id="steps-content">
	<div class="steps">
		<ul>
			<c:if test="${PREVIOUS_STEP != null }">
				<li class="step" onclick="indietro();" title="<fmt:message key='label.indietro' />">&lt;&lt; <fmt:message key="label.indietro" /></li>
			</c:if>
			<c:forEach items="${STEPS_HELPER.steps }" var="step">
				<c:choose>
					<c:when test="${step.ordinePager eq CURRENT_STEP.ordinePager }">
						<li class="step corrente" title="${step.titolo }"><c:out value="${step.ordinePager }"></c:out></li>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${step.ordinePager < CURRENT_STEP.ordinePager }">							
								<li class="step" onclick="vai_a_step('${step.ordinePager }');" title="${step.titolo }"><c:out value="${step.ordinePager }"></c:out></li>
							</c:when>
							<c:otherwise>
								<li class="step disabilitato" title="${step.titolo }"><c:out value="${step.ordinePager }"></c:out></li>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<li class="step" onclick="avanti();" title="<fmt:message key='label.avanti' />"><fmt:message key="label.avanti" /> &gt;&gt;</li>		
		</ul>
	</div>
</div>
<input type="hidden" name="pager_step" />
<input type="hidden" name="pager_azione" />
<c:if test="${not empty param.formName}">
	<script type="text/javascript">
		var formName = '${param.formName}';
	</script>
</c:if>
<c:if test="${empty param.formName}">
	<script type="text/javascript">
		var formName = 0;
	</script>
</c:if>
<script type="text/javascript">
	<%-- vai_a_step deve salvare i dati perchè potrei saltare ad uno step successivo --%>
	function vai_a_step(ordinePager){
		if ($.isFunction(window.pageAction)){
			pageAction();
		}
		$.blockUI();
		document.forms[formName].pager_step.value=ordinePager;
		document.forms[formName].pager_azione.value='S';
		document.forms[formName].submit();
		
	}
	function avanti(){
		if ($.isFunction(window.pageAction)){
			pageAction();
		}
		$.blockUI();
		document.forms[formName].pager_step.value='';
		document.forms[formName].pager_azione.value='A';
		document.forms[formName].submit();
		
	}
	function indietro(){
		if ($.isFunction(window.pageAction)){
			pageAction();
		}
		$.blockUI();
		document.location.href='../nuovaistanza/indietro.htm?pager_azione=I';
	}
</script>