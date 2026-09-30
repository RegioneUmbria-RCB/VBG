<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ include file="../includes/taglibs.jsp" %>
<html>
	<head>
		<%@ include file="../includes/header.jsp" %>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#interrompi_nuova_istanza").click(function(){
					if(confirm("<fmt:message key='alert.interrompi-compilazione' />")){
						location.href="${pageContext.request.contextPath}/nuovaistanza/interrompi.htm";
					}
				});
				
				$.ajax({
					  url: "${pageContext.request.contextPath}/ajax/report.htm",					
					  cache: false
				});			
			});
		</script>	
	</head>
	<body>
		<%@ include file="../includes/testata.jsp" %>		
		<div class="onecolumn">
			<div class="sezione" style="float: right; border: 1px solid black; margin: 5px;">
				<div>
				<c:if test="${not empty nuovaIstanzaCommand.idDomanda }">
				${nuovaIstanzaCommand.idDomanda }
				</c:if><br />
				<button id="interrompi_nuova_istanza" class="menu_button"><fmt:message key="button.interrompi-compilazione" /></button>
				</div>
			</div>
			<decorator:body />
		</div>
		<%@ include file="../includes/footer.jsp" %>
	</body>
</html>