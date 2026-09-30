<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.LinkPreferitiUtente"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>

        
       	<div class="border-bottom">
            <div class="navbar navbar-static-top">

                <div class="navbar-header">

                    <!-- 
                        Bottone che comanda l'apertura/chiusura del menu
                    -->
                    <div class="open-menu pull-left">
                        <i class="fa fa-reorder"></i>
                    </div>

                    <div class="navbar-brand">
                        <h1>
                        <spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
                        	<a style="" href="../admin/view.htm">Net.Bu.K.</a>
 						</spring-security:authorize>
 						<spring-security:authorize  ifNotGranted="ROLE_ADMINISTRATOR">
 							Net.Bu.K.
 						</spring-security:authorize>
                            	<small style="font-weight: bolder;">${comune}</small>
                            	<%if(!ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)){ %>
                            		<small style=" font-style: italic;"> - <c:import url="/ajax/findCurrentSoftware.htm" /></small>
                            	<%}else{ %>
                            		<small style=" font-style: italic;"> - Archivi di base</small>
                            	<%} %>

                        </h1>
                        <c:if test="${not empty applicationScope.MESSAGGIO_AGGIORNAMENTO_APPLICATIVO}">
							<div class="header_alert_message">
								<img src="${pageContext.request.contextPath}/images/warning.gif" alt="warning" align="bottom" />
								${applicationScope.MESSAGGIO_AGGIORNAMENTO_APPLICATIVO}
							</div>
						</c:if>						
                    </div>
                </div>
                <div style="float: right;padding-top:15px; padding-right:15px;"><a href="${pageContext.request.contextPath}/welcome/logout.htm"><i class="glyphicon glyphicon-off"></i> Esci</a></div>
            </div>
        </div>