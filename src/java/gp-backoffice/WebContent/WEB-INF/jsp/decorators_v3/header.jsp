<%@page import="it.gruppoinit.pal.gp.core.domain.LinkPreferitiUtente"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>


 <div class="banner">
        <div class="row">
            <div class="col-md-7">
                <h1>
                    ${comune}
<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR"><a style="text-decoration: none;" href="../admin/view.htm"><small><fmt:message key="label.appname.extended"/></small></a></spring-security:authorize>
<spring-security:authorize  ifNotGranted="ROLE_ADMINISTRATOR"><a style="text-decoration: none;" href="../admin/view.htm"><img border="0" src="<%=request.getContextPath() %>/images/item_arrow.gif"/></a>
<small><fmt:message key="label.appname.extended"/></small>
</spring-security:authorize>
					
                </h1>
            </div>

			<div style="float: right;" id="logo-div" >
				<img id="logo-img" src="${pageContext.request.contextPath}/images/sporvic3.png" title="${product.name }  ${app_version} - (build: ${db_version})"/>
			</div>
            <div class="col-md-4 informazioni">
				<c:if test="${not empty applicationScope.MESSAGGIO_AGGIORNAMENTO_APPLICATIVO}">
					<span class="header_alert_message">
						<img src="${pageContext.request.contextPath}/images/warning.gif" alt="warning" align="bottom" />
						${applicationScope.MESSAGGIO_AGGIORNAMENTO_APPLICATIVO}
					</span>
				</c:if>            
                
                
                
            </div>
        </div>
        <script type="text/javascript">
        /*
        	jQuery(document).ready(function(){
            	jQuery("#logo-div").tooltip({
					content: "<p style='font-weight: bold;'>${product.name }</p> ${app_version} <p style='font-size: 9px;'>(build: ${db_version})</p>",
					//content: "TooltiPPPPPP",
					position: {my: "right top", at: "center bottom"},
					collision: 'fliptop',
					of: 'logo-img'
                })
            });
        */
        </script>
</div>