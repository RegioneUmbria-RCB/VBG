<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>

<c:set var="agg_timeout" value="true"></c:set>

<c:if test="${param.settimeout eq 'false'}">
	<c:set var="agg_timeout" value="false"></c:set>
</c:if>
<script type="text/javascript">
// 	$(document).ready(function(){
//		messaggioAggiornamento();
//	});

function messaggioAggiornamento(){
	blockUIEnabled = false;
	$.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/cart/ajaxGetMessaggioAggiornamento.htm",
        data: "",
        dataType: "text",
		success: function(data) {
			if(data!=''){
				$('#messaggioAggiornamento_id').html(data);
				$('#messaggioAggiornamento_id').show();
				$("#messaggioAggiornamento_id").effect( "pulsate", {times:3}, 10000 );
			}else{
				$('#messaggioAggiornamento_id').hide();							
			}
	  },
	  error: function(jqXHR, textStatus, errorThrown){
		  
		  $('#messaggioAggiornamento_id').hide();
	}
	});
	
	blockUIEnabled = true;
	<c:if test="${agg_timeout eq true}">
		setTimeout(messaggioAggiornamento, 180000);
	</c:if>
}

</script>