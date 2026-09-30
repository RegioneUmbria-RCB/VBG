<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="amministrazioni.label.lista_email.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="amministrazioni.label.lista_email.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
        
      
       <div class="parametriDiv">
			<div class="etichetta">
	            <div>
	             <fmt:message key="label.amministrazione" />:
	        	</div>
           </div>
         <div class="parametro">
       		 <div>
		          <c:out value="${amministrazioni.amministrazione}"/>
		     </div>
		 </div>
         </div> 
			<form name="emailForm" action="listemail.htm">
				<jmesa:springTableFacade
					id="email_id" 
					items="${emailList}" 
					var="email_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.DataEmailFilterMatcherMap" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="viewEmailDetail.htm?codice=${email_var.id.codice}">${email_var.id.codice}</a>
                            </jmesa:htmlColumn>							
							<jmesa:htmlColumn property="data" titleKey="label.data_email" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataEmailCustomFilter" headerStyle="width: 10%"/>
                            <jmesa:htmlColumn property="oggetto" titleKey="label.oggetto_email" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="10%">
								<a class="dettaglioColumn" href="viewEmailDetail.htm?codice=${email_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${email_var.oggetto}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
                <input type="hidden" value="${codice}" name="codice"/>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='listemail.htm?codice=${codice}&';
				var _captionTab='<fmt:message key="amministrazioni.label.lista_mail.title" />';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('view.htm?codice=${codice}','');"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>