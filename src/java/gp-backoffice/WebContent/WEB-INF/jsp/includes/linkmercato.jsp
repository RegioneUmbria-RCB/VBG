<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<%-- 
PARAMETRI:
	codiceMercato
	descrizioneMercato
--%><div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.manifestazione" />:</div>
	    	</div>
	    	<div class="parametro">
	    	  <div>
	    	  	<a href="${pageContext.request.contextPath }/mercati/view.htm?codice=${param.codiceMercato}">
	    	  		${param.descrizioneMercato}
	    	  	</a>
	    	  </div>
	    	   <a href="${pageContext.request.contextPath }/mercatid/list.htm?codicemercato=${param.codiceMercato}">
	    	  	>> Visualizza posteggi <<
	    	   </a>
			</div>
		</div>