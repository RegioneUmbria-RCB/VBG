<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<fieldset>
	<legend>
		<fmt:message key="label.adeguamento_iva.title" />
	</legend>
	<div style="border: 1px dotted; padding: 2px;">
		<fmt:message key="label.adeguamento_iva.help" />
	</div>
	<form name="updateAggiornaIVA_Form" action="updateAggiornaIVA.htm">
		<div>
			<span style="width: 120px;"><fmt:message key="label.adeguamento_iva.nuovo_valore" />:</span>
			<span style="width: 100px;"><input	type="text"  size="10" maxlength="10" onblur="checkNumberInt(this)" style="text-align: right;" name="valoreIva" />%</span>
		</div>
		<%--
		<div>
			<span style="width: 120px;"><fmt:message key="label.adeguamento_iva.data_scadenza" />:</span>
			<span style="width: 100px;"><input	type="text" size="10" maxlength="10" onblur="isValidDate(this,true);" name="data" /></span>
		</div>
		 --%>
		<div id="functions">
			<ul>
				<li><a
					href="javascript:doSubmit('updateAggiornaIVA.htm','<fmt:message key="label.adeguamento_iva.confirm_javascript"/>',document.updateAggiornaIVA_Form)"><fmt:message
							key="button.update" /></a></li>
			</ul>
		</div>
	</form>
</fieldset>