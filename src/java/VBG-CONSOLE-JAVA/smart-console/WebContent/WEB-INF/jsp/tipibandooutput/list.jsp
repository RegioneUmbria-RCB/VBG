<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.tipibandooutput.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.tipibandooutput.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <span class="parametri"><fmt:message key="form.tipibando.title.prefix"/> <label>${tipigraduatoriet.tipibando.descrizione}</label></span>
		<span class="parametri"><fmt:message key="form.tipigraduatoriet.title"/> <label>${tipigraduatoriet.descrizione}</label></span>
		<div id="subcontent">
			<form name="tipibandooutputForm" action="list.htm">
				<jmesa:springTableFacade
					id="tipibandooutput_id" 
					items="${tipibandooutputList}" 
					var="tipibandooutput_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${tipibandooutput_var.id.codice}">${tipibandooutput_var.id.codice}</a>
                            </jmesa:htmlColumn>							
							<jmesa:htmlColumn property="tipocalcolo" titleKey="form.tipibandooutput.tipocalcolo" />
							<jmesa:htmlColumn property="tipibandoinput.etichetta" titleKey="form.tipibandooutput.tipoinput" />
                            <jmesa:htmlColumn property="dyn2CampiRif.nomecampo" titleKey="form.tipibandooutput.dyn2CampiRif" />
                            <jmesa:htmlColumn property="dyn2CampiOut.nomecampo" titleKey="form.tipibandooutput.dyn2CampiOut" /> 
			                <jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="view.htm?codice=${tipibandooutput_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${tipibandooutput_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
           <input type="hidden" value="${tipigraduatoriet.id.codice}" name="tipigraduatoriet.id.codice" /> 
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?tipigraduatoriet.id.codice=${tipigraduatoriet.id.codice}&';
				var _captionTab='<fmt:message key="form.tipibandooutput.title.list"/>';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm?tipigraduatoriet.id.codice=${tipigraduatoriet.id.codice}','');"><fmt:message
		key="button.new" /></a></li>
    <li><a href="javascript:doHref('../tipigraduatoriet/view.htm?codice=${tipigraduatoriet.id.codice}','');"><fmt:message
		key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>