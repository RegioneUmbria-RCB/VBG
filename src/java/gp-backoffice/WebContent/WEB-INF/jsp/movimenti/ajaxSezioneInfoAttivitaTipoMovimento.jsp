<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%--

	private TipimovimentoId id;
    private String descrizioneEstesa;
    private String movimento;
    private Short sistema;
    private Short codicelettera;
    private Boolean flagRichiestaintegrazione;
    private Boolean flagInterruzione;
   
    private Boolean flagProroga;
    private Integer ggproroga;
  
    private Boolean flagOperante;
    private Boolean flagNonoperante;
    private Boolean flagCds;
    private Boolean flagRegistro;
    private Tipologiaregistri tipologiaregistri;
    private Boolean flagNoamminterna;
    private Boolean flagUsadalprotocollo;
    private Boolean flagPubblicamovimento;
    private Boolean flagPubblicaparere;
    private FoSoggettiesterni foSoggettiesterni;
    private Boolean flagStc;
    private Boolean flagCamcom;
    private Boolean flagFinesospinterr;
    private Boolean flagPubblicaallegati;
    private Boolean flagDisdavisionare;
    private Mailtipo mailtipoByFkTipimovricTelMailtipo;
    private Mailtipo mailtipoByFkTipimovcomTelMailtipo;
    private Mailtipo mailtipoOggProt;
    private Statiistanza statoistanza;
    private Letteretipo letteretipo;
    private Boolean flagDisabilitato;
    private Boolean flgInvialinkallmail;
    private Boolean flgProtocollalinkall;
    private Boolean flagRiportaProtIstanza;
    private Integer flagSostDocumentale;
    private Boolean flagIntegrCheckFirma;
    private Boolean flagPubblSchede;
    private Boolean flagAccediSchede;
    private Boolean flagAggiornaRiepilogo;
    private Boolean flagAutorizProroga;
    private Boolean flagAutorizPreavv;
    private Boolean flagAutorizRinnovo;
    private Boolean flagAutorizModifica;
    private Boolean flagFoRichiamaSit;
 --%>
<c:set var="amenoUno" value="false"></c:set>
<div id="info-att-mov-${mov.id.codice}-content" style="display: none">
	<div class="vbg-form">
		<fieldset>
			<legend>Caratteristiche di <b>${mov.tipomovimento.id.tipomovimento} - ${mov.tipomovimento.movimento}</b></legend>
		<c:if test="${mov.tipomovimento.flagRichiestaintegrazione eq true}">
			<c:set var="amenoUno" value="true"></c:set>
			<div class="form-group">
					<label title="<fmt:message key="tipimovimento.label.flag_richiesta_integrazione"/>"><fmt:message key="tipimovimento.label.flag_richiesta_integrazione"/> <i class="fas fa-check fa-lg"></i></label> 
			</div>			
		</c:if>
		<c:if test="${mov.tipomovimento.flagInterruzione eq true}">
			<c:set var="amenoUno" value="true"></c:set>
			<div class="form-group">
					<label title="<fmt:message key="tipimovimento.label.flag_interruzione"/>"><fmt:message key="tipimovimento.label.flag_interruzione"/>  <i class="fas fa-check fa-lg"></i></label> 
			</div>
		</c:if>
		<c:if test="${mov.tipomovimento.flagProroga eq true}">
			<c:set var="amenoUno" value="true"></c:set>
			<div class="form-group">
					<label title="<fmt:message key="tipimovimento.label.flag_proroga"/>"><fmt:message key="tipimovimento.label.flag_proroga"/> (${mov.tipomovimento.ggproroga} gg) <i class="fas fa-check fa-lg"></i></label> 
			</div>
		</c:if>
		<c:if test="${mov.tipomovimento.flagOperante eq true}">
			<c:set var="amenoUno" value="true"></c:set>
			<div class="form-group">
					<label title="<fmt:message key="tipimovimento.label.flag_operante"/>"><fmt:message key="tipimovimento.label.flag_operante"/> <i class="fas fa-check fa-lg"></i></label> 
			</div>
		</c:if>
		<c:if test="${mov.tipomovimento.flagNonoperante eq true}">
			<c:set var="amenoUno" value="true"></c:set>
			<div class="form-group">
					<label title="<fmt:message key="tipimovimento.label.flag_nonoperante"/>"><fmt:message key="tipimovimento.label.flag_nonoperante"/> <i class="fas fa-check fa-lg"></i></label> 
			</div>
		</c:if>	
		<c:if test="${mov.tipomovimento.flagFinesospinterr eq true}">
			<c:set var="amenoUno" value="true"></c:set>
			
			<div class="form-group">
					<label title="<fmt:message key="tipimovimento.label.flag_fine_sospensione_interruzione"/>"><fmt:message key="tipimovimento.label.flag_fine_sospensione_interruzione"/> <i class="fas fa-check fa-lg"></i></label> 
			</div>							
		</c:if>
		</ul>
		</fieldset>
	</div>
</div>

<c:if test="${ amenoUno }">
	<span>
	<a href="javascript: void 0" onclick="apriModalInfoAttivitaMovimento('info-att-mov-${mov.id.codice}-content')" ><i class="fas fa-cogs fa-lg"></i></a></span>
</c:if>
	
			