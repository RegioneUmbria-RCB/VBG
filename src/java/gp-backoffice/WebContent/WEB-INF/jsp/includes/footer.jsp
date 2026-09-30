<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>

<script type="text/javascript">

<!--
		function changeLang(lang){
			if(document.URL.match(new RegExp("\\?","g"))){
				if(document.URL.match(new RegExp("lang=","g"))){
					var string = document.URL;
					var reg = new RegExp("lang=.*","g");
					string = string.replace(reg,"lang="+lang);
					window.location.replace(string);
				}else{
					window.location.replace(document.URL+"&lang="+lang);
				}
			}else{
				window.location.replace(document.URL+"?lang="+lang);
			}
		}
		function changeTheme(theme){
			if(document.URL.match(new RegExp("\\?","g"))){
				if(document.URL.match(new RegExp("theme=","g"))){
					var string = document.URL;
					var reg = new RegExp("theme=.*","g");
					string = string.replace(reg,"theme="+theme);
					window.location.replace(string);
				}else{
					window.location.replace(document.URL+"&theme="+theme);
				}
			}else{
				window.location.replace(document.URL+"?theme="+theme);
			}
		}
//-->
</script>
<script type="text/javascript">
<!--
	function changePrefs(){
		$('header_prefs').appear();
		$('header_edit').fade();		
	}
//-->
</script>

<p id="legal">
	<fmt:message key="label.brand" />&nbsp;&nbsp;&bull;&nbsp;&nbsp;
	<a href="http://validator.w3.org/check/referer" class="xhtml" title="This page validates as XHTML">Valid <abbr title="eXtensible HyperText Markup Language">XHTML</abbr></a>
	&nbsp;&nbsp;&bull;&nbsp;&nbsp;<a href="http://jigsaw.w3.org/css-validator/check/referer" class="css" title="This page validates as CSS">Valid <abbr title="Cascading Style Sheets">CSS</abbr></a>
</p>
	
