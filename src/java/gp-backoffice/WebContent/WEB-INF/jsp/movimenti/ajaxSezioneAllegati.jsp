<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%--
<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>

    private PkId id;
    private Integer codiceIstanza;
    private Integer codiceMovimento;
    private String descrizioneMovimento;
    private Timestamp dataMovimento;
    private String responsabileMovimento;
    private Date dataregistrazione;
    private String descrizione;
    private Integer codiceOggetto;
    private String nomeFile;
    private String note;
    private Boolean flagPubblica;
    private String amministrazione;
    private String tipomovimento;
    private String desctipomovimento;
    private String stcIddocumento;
    private String stcIdallegato;
    private Integer controllook;
    private boolean transientSegnaPerInvio;
    private String descrizioneEstesa;
    private String idBase;
    private Integer dimensioneFile;
    private String numeroprotocollo;
    private Date dataprotocollo;
    private String protocolloAndData;
    private String codicecomune;
    private String tipoDocumento;
    private String messageId;
    private boolean transientSegnaPerInvioPec;

 --%>
<c:if test="${not empty allegati}">
<vbg-modal id="movimenti_allegati_id_${param.codicemovimento}_modal">
	<div slot="body">
		<h1>
			<fmt:message key="label.movimenti_allegati" />
		</h1>
		<div class="vbg-form">
		
				<c:forEach items="${allegati}" var="movimentiallegati_var">		
				<p>								
					<c:if test="${movimentiallegati_var.codiceOggetto !=null }">
					<jsp:include page="../includes/visualizzaOggetto.jsp" >
			  						<jsp:param name="idElemento" value="mall${movimentiallegati_var.id.codice }" />
			  						<jsp:param name="fileId" value="${movimentiallegati_var.codiceOggetto}" />
			  						<jsp:param name="mostralabel" value="true"/>
			  						<jsp:param name="mostraNomeFile" value="true"/>
								<jsp:param name="readonly" value="true"/>
							</jsp:include>
					</c:if>
					<c:if test="${movimentiallegati_var.codiceOggetto==null && movimentiallegati_var.stcIdallegato!=null && movimentiallegati_var.stcIddocumento!=null}">
						<jsp:include page="../includes/visualizzaOggettoSTC.jsp" >
			   						<jsp:param name="codicemovimento" value="${movimentiallegati_var.codiceMovimento}" />
			   						<jsp:param name="codiceistanza" value="${movimentiallegati_var.codiceIstanza}" />
									<jsp:param name="stcIddocumento" value="${movimentiallegati_var.stcIddocumento}" />
									<jsp:param name="stcIdallegato" value="${movimentiallegati_var.stcIdallegato}" />
									<jsp:param name="codiceRiferimento" value="${movimentiallegati_var.id.codice}" />
									<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_MOVIMENTO%>" />	
									<jsp:param name="indice" value="mov_all${movimentiallegati_var.id.codice}"/>	
									<jsp:param name="readonly" value="true"/>
									<jsp:param name="label" value="${movimentiallegati_var.descrizione}"/>   							
							</jsp:include>		
					</c:if>
				</p>	
				</c:forEach>													
		
		</div>
	</div>
	
</vbg-modal>
<a class="vbg-btn btn-allegato" href="javascript: void 0" title="<fmt:message key="label.click_per_visualizzare"/>"
	 onclick="document.getElementById('movimenti_allegati_id_${param.codicemovimento}_modal').open();">
</a>
</c:if>				