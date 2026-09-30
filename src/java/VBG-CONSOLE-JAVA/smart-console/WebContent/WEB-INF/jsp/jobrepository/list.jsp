<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="jobrepository.label.lista.title" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="jobrepository.label.lista.title" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />	
</jsp:include>
<div id="subcontent">
<form name="jobrepositoryForm" action="list.htm">
<jmesa:springTableFacade id="jobrepository_id" items="${jobrepositoryList}" var="jobrepository_var" stateAttr="restore">
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="id" titleKey="label.codice" width="2%">
				<a href="view.htm?codice=${jobrepository_var.id}">${jobrepository_var.id}</a>
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="jobName" titleKey="jobrepository.jobName" />			
			<jmesa:htmlColumn property="description" titleKey="jobrepository.description" />
			<jmesa:htmlColumn property="alias" titleKey="jobrepository.idcomunealias" />
			<jmesa:htmlColumn property="modulo" titleKey="jobrepository.software" />
			<jmesa:htmlColumn property="triggerType" titleKey="jobrepository.triggerType" />
			<jmesa:htmlColumn property="startDelay" titleKey="jobrepository.startDelay" />
			<jmesa:htmlColumn property="repeatInterval" titleKey="jobrepository.repeatInterval" />
			<jmesa:htmlColumn property="cronExpression" titleKey="jobrepository.cronExpression" />
			<jmesa:htmlColumn property="jobClassName" titleKey="jobrepository.jobClassName" />
			<jmesa:htmlColumn property="active" titleKey="jobrepository.label.active" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist" />
			<jmesa:htmlColumn property="" title="Stato" width="5%">
				<div id="${jobrepository_var.jobName }" class="stato_del_job" />
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade>
</form>
<script type="text/javascript">
	var _jmesaUrl = 'list.htm';
	var _captionTab = "<fmt:message key="jobrepository.label.lista.title" />";
</script></div>
<div id="functions">
	<ul>
		<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
		<li><a href="javascript:doHref('schedule.htm','<fmt:message key="jobrepository.alert.schedule" />');"><fmt:message key="button.schedule" /></a></li>
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
<script type="text/javascript">
	var wk = function worker() {
		jQuery.ajax({
		    url: '../jobrepository/ajaxGetCurrentlyExecutingJobs.htm',
		    context: document.body,
			cache: false,					  
			dataType: "json",
		    success: function(data) {
			    jQuery('.stato_del_job').html("");
		    	jQuery.each(data.jobs, function(i, item) {
			    	jQuery('#'+item.chiave).html("<label style='background-color:green;color:white;padding:2px;font-weight:bold' title='Orari di inizio\nCorrente: "+item.valore.fireTime+"\nPrecedente: "+item.valore.previousFireTime+"\nSuccessivo: "+item.valore.nextFireTime+"'>ATTIVO</label>");
			    }); 	
		    },
		    complete: function() {
		      // Schedule the next request when the current one's complete
		      setTimeout(worker, 5000);
		    }
		  });
	};
	wk();
</script>
</body>
</html>