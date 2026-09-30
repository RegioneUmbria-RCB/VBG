<%@ include file="../includes/taglibs.jsp" %>
<script type="text/javascript">
		jQuery(document).ready(function(){
			getEventiNonLetti("eventi_id", '${param.codIstanza}', '${param.codMov}');
		});
		var listEventi;
		function getEventiNonLetti(id, codIstanza, codMov){
			var now = new Date();
			var ts = 'ts='+now.getTime();
			var ci = '&codIstanza='+codIstanza;
			if(codIstanza='')ci='';
			var cm = '&codMov='+codMov;
			if(codMov='')cm='';
			new Ajax.Request('${pageContext.request.contextPath}/ajax/findEventiNonLetti.htm?'+ts+ci+cm, {
				  method: 'get',	
				  onSuccess: function(transport){
					listEventi = transport.responseText;
					if(transport.responseText != ''){
						$(id).style.display="block";
					}else{
						$(id).style.display="none";
					}
			      },
				  onFailure: function(transport){ 
					$(id).innerHTML="<ul style='list-style-type: none;'><li><a href=\"#\">Errore durante il recupero degli eventi</a></li></ul>";
					$(id).style.display="block";
				  }						    		 
			});
		}
		function viewHideListEventi(){
			if($('eventi_list_id').style.display=="block"){
				getEventiNonLetti("eventi_id", '${param.codIstanza}', '${param.codMov}');
				$('eventi_list_id').style.display="none";
			}else{
				$('eventi_list_id').innerHTML = listEventi;
				$('eventi_list_id').style.display="block";
				applyStyle();
			}
		}
</script>
<div class="notifichePopup" id="eventi_id">
	<div class="notifichePopupInner">
		<a href="javascript: void 0" onclick="viewHideListEventi();">
			<fmt:message key="label.eventi_non_letti" />
		</a>
	</div>
	<div id="eventi_list_id" style="height:450px; position: relative; display: none; overflow: auto;">&nbsp;</div>
</div>