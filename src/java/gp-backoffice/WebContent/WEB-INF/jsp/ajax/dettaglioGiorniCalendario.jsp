<%@ include file="../includes/taglibs.jsp"%>

<c:forEach items="${giorniSettimana}" var="giornoSettimana" varStatus="index">
	<c:if test="${not empty giornoSettimana.gsValore}">
		<c:if test="${giornoSettimana.transientSelected eq true}">
		 <input type="checkbox" name="giorniSettimana[${index.index}].transientSelected" checked="checked"/>${giornoSettimana.gsDescrizione}
		</c:if>
		<c:if test="${giornoSettimana.transientSelected eq false}">
		 <input type="checkbox" name="giorniSettimana[${index.index}].transientSelected"/>${giornoSettimana.gsDescrizione}
		</c:if>
	</c:if>
</c:forEach>