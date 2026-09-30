<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">
  function getProgressBarValue(){	
		new Ajax.Request('<%=request.getContextPath()%>/ajax/getProgressBarValue.htm', {
			  method: 'post',	
			  onSuccess: function(transport){
			  	$('progressbarnumvalue').innerHTML=transport.responseText+'%';  
			  	$('progressbarvalue').style.width=(transport.responseText*2)+'px';			
			  },
			  onFailure: function(transport){}						    		 
		});
		setTimeout('getProgressBarValue()',5000);
  }
  Event.observe(window,'load',getProgressBarValue,false);
</script>
<style type="text/css">
  #progressbar{
     width: 200px;
     height: 10px;
     background: #fff;
     margin-top: 10px;
     border: 2px solid #999;
  }
  #progressbarvalue{
     width: 0;
     height: 8px;
     background: #f90;
     border: 1px solid #999;
  }
  #progressbarnumvalue{
     font: normal 11px Verdana, Arial, Helvetica, sans-serif;
     color: #000;
     margin-top: 10px;
  }
</style>
<div id="progressbarcontainer" style="display: none;">
	<div id="progressbar"><div id="progressbarvalue"></div></div>
	<span id="progressbarnumvalue"></span>
</div>