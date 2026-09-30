<%@ include file="../includes/taglibs.jsp" %>
<div class="jmesa">
	
			<c:forEach items="${listUsers}" var="user_var" varStatus="a">
			<div>
				${ user_var	 }
			</div>
			</c:forEach>
		
</div>