<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglibs.jsp" %>
<%
	String uri = request.getRequestURI();
	String ctx = request.getContextPath();
	String res = uri.substring(uri.indexOf(ctx)+ctx.length());
%>

<script type="text/javascript">
	function showHelp(modulo,isBase){
		var mod=''; 
		if(document.getElementById(modulo) !=null){
			 mod=document.getElementById(modulo).value;
		}
		var ts=(new Date()).getTime();
	    var url = '<%=request.getContextPath() %>/help/ajaxshowHelp.htm?ts_='+ts+'&modulo='+mod+'&isBase='+isBase;
	    var pars = 'contenttype=<%=res%>';
	    var target = 'helpContent';
	    var myAjax = new Ajax.Updater(target, url, {method: 'get', parameters: pars});	          
	    dijit.byId('infoHelp').show();
	}
	
	function saveBaseHelp(){
		var qs = getQueryStringFromForm(document.help);
		new Ajax.Request('${pageContext.request.contextPath}/help/ajaxSaveHelpbase.htm?'+qs, {
			  method: 'post',	
			  onSuccess: function(transport){},
			  onFailure: function(transport){}						    		 
		});
	}
	
	function saveHelp(){
		var qs = getQueryStringFromForm(document.help);
		new Ajax.Request('${pageContext.request.contextPath}/help/ajaxSaveHelp.htm?'+qs, {
			  method: 'post',	
			  onSuccess: function(transport){},
			  onFailure: function(transport){}						    		 
		});
	}
	
	var anteprimaDlg;
	dojo.addOnLoad(function() {
		anteprimaDlg = new dijit.Dialog({
	        title: "<fmt:message key="label.anteprima" />",
	        style: "width:500px;"
	    });
	});
	
	function showAnteprima(id_campo_anteprima) {
		anteprimaDlg.attr("content",document.getElementById(id_campo_anteprima).value);
		anteprimaDlg.show();
	}
</script>
<a class="help_pagina" href="javascript:showHelp()" title="<fmt:message key="label.help.title.display" />">
	<fmt:message key="label.help.display" />
</a>
<div id="infoHelp" dojoType="dijit.Dialog" title="<fmt:message key='label.help.title.display' />" style="display: none;">
    <div style="width: 800px; height: 700px;" id="helpContent"></div>
</div>
