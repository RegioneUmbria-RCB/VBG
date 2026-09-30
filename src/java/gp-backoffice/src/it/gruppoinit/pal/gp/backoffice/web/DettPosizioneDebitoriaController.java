package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.paevolution.ws.pagamenti_types.EsitoDocumentoPosizioneDebitoriaType;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoResponseBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DatiPagamento;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.PagamentoOfflineModel;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Controller
@SessionAttributes(value = { "pagamentoOffline" })
public class DettPosizioneDebitoriaController extends BaseController<DettPosizioneDebitoria> {

    @Autowired
    private TipimodalitapagamentoService modalitaPagamentoService;
    @Autowired
    private DettPosizioneDebitoriaService service;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;

    @Override
    protected void setPageAttributes(Model model) {

	// non utilizzato
    }

    @Override
    protected void fixMergeEntityProperty(DettPosizioneDebitoria entity) {

	// non utilizzato
    }

    @Override
    protected void fixRenderEntityProperty(DettPosizioneDebitoria entity) {

	// non utilizzato
    }

    @RequestMapping
    public void ajaxDettaglioPosizione(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	String parIdDettPosizioneDebitoria = request.getParameter("idDettPosizioneDebitoria");
	if (StringUtils.isBlank(parIdDettPosizioneDebitoria)) {
	    return;
	}
	Integer idDettPosizioneDebitoria = Integer.parseInt(parIdDettPosizioneDebitoria);
	DettPosizioneDebitoriaResponseType posizione = this.service.dettaglioPosizione(request.getContextPath(), idDettPosizioneDebitoria);
	String retVal = this.dettPosizioneDebitoriaResponseTypeToJson(posizione);
	response.setContentType("application/json");
	response.getOutputStream().write(retVal.getBytes());
    }

    @RequestMapping
    public void ajaxVerificaStato(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	Integer idDettPosizioneDebitoria = Integer.parseInt(request.getParameter("idDettPosizioneDebitoria"));
	String retVal = this.dettPosizioneDebitoriaResponseTypeToJson(this.service.verificaStato(request.getContextPath(), idDettPosizioneDebitoria));
	response.setContentType("application/json");
	response.getOutputStream().write(retVal.getBytes());
    }

    @RequestMapping
    public void ajaxGeneraFattura(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Integer idDettPosizioneDebitoria = Integer.parseInt(request.getParameter("idDettPosizioneDebitoria"));
	EsitoDocumentoPosizioneDebitoriaType fattura = this.service.generaFattura(idDettPosizioneDebitoria);
	if (!fattura.isEsito()) {
	    response.setStatus(500);
	    response.setContentType("text/plain");
	    response.getOutputStream().write(fattura.getMessaggio().getBytes());
	    response.getOutputStream().flush();
	    return;
	}
	byte[] buffer = new byte[10240];
	InputStream input = fattura.getDocumento().getInputStream();
	response.setContentType("application/octet-stream");
	response.addHeader("content-disposition", "inline; filename=\"" + fattura.getNomeDocumento() + "\"");
	for (int length = 0; (length = input.read(buffer)) > 0;) {
	    response.getOutputStream().write(buffer, 0, length);
	}
    }

    @RequestMapping
    public void ajaxGeneraAvviso(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Integer idDettPosizioneDebitoria = Integer.parseInt(request.getParameter("idDettPosizioneDebitoria"));
	EsitoDocumentoPosizioneDebitoriaType avviso = this.service.generaAvviso(idDettPosizioneDebitoria);
	if (!avviso.isEsito()) {
	    response.setStatus(500);
	    response.setContentType("text/plain");
	    response.getOutputStream().write(avviso.getMessaggio().getBytes());
	    response.getOutputStream().flush();
	    return;
	}
	if (avviso.getDocumento() == null) {
	    response.setContentType("text/plain");
	    return;
	}
	byte[] buffer = new byte[10240];
	InputStream input = avviso.getDocumento().getInputStream();
	response.setContentType("application/octet-stream");
	response.addHeader("content-disposition", "inline; filename=\"" + avviso.getNomeDocumento() + "\"");
	for (int length = 0; (length = input.read(buffer)) > 0;) {
	    response.getOutputStream().write(buffer, 0, length);
	}
    }

    @RequestMapping
    public String pagaOffline(Model model, HttpServletRequest request, HttpServletResponse response) {

	Integer idDettPosizioneDebitoria = Integer.parseInt(request.getParameter("idDettPosizioneDebitoria"));
	String urlRitorno = request.getParameter("urlRitorno");
	DatiPagamento datiPagamento = DatiPagamento
		.fromDettPosizioneDebitoriaResponseType(this.service.dettaglioPosizione(request.getContextPath(), idDettPosizioneDebitoria));
	List<Tipimodalitapagamento> modalitaPagamento = modalitaPagamentoService.findAll(null, null, false);
	model.addAttribute("pagamentoOffline", new PagamentoOfflineModel(datiPagamento, modalitaPagamento));
	model.addAttribute("urlRitorno", urlRitorno);
	return "dettposizionedebitoria/pagaoffline";
    }

    @RequestMapping
    public String registraPagamentoOffline(Model model, @ModelAttribute("pagamentoOffline") PagamentoOfflineModel datiPagamentoOffline,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	this.service.registraPagamentoOffline(datiPagamentoOffline.getDatiPagamento());
	return "redirect:../history/back.htm?GoTo=%2F";
    }

    @RequestMapping
    public void ajaxGeneraRicevuta(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Integer idDettPosizioneDebitoria = Integer.parseInt(request.getParameter("idDettPosizioneDebitoria"));
	EsitoDocumentoPosizioneDebitoriaType ricevutaTelematica = this.service.generaRicevutaTelematica(idDettPosizioneDebitoria);
	if (!ricevutaTelematica.isEsito()) {
	    response.setStatus(500);
	    response.setContentType("text/plain");
	    response.getOutputStream().write(ricevutaTelematica.getMessaggio().getBytes());
	    response.getOutputStream().flush();
	    return;
	}
	byte[] buffer = new byte[10240];
	InputStream input = ricevutaTelematica.getDocumento().getInputStream();
	response.setContentType("application/octet-stream");
	response.addHeader("content-disposition", "inline; filename=\"" + ricevutaTelematica.getNomeDocumento() + "\"");
	for (int length = 0; (length = input.read(buffer)) > 0;) {
	    response.getOutputStream().write(buffer, 0, length);
	}
    }

    @RequestMapping
    public void ajaxModificaDataScadenza(@RequestParam("idDettPosizioneDebitoria") Integer idDettPosizioneDebitoria,
	    @RequestParam("dataScadenza") Date dataScadenza, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	try {
	    this.nodoPagamentiService.modificaDataScadenzaPosizioneDebitoria(idDettPosizioneDebitoria, dataScadenza);
	} catch (FunzioneBusinessRemotaException e) {
	    response.setStatus(500);
	    response.setContentType("text/plain");
	    response.getOutputStream().write(("Non è stato possibile modificare la data di scadenza a causa di " + e.getMessage()).getBytes());
	    response.getOutputStream().flush();
	}
    }

    @RequestMapping
    public void attivaSessionePagamento(Model model, HttpServletRequest request, HttpServletResponse response)
	    throws FunzioneBusinessRemotaException, IOException, JAXBException {

	Integer idDettPosizioneDebitoria = Integer.parseInt(request.getParameter("idDettPosizioneDebitoria"));
	AttivaSessionePagamentoResponseBean res = this.service.attivaSessionePagamento(idDettPosizioneDebitoria);
	if (BooleanUtils.isFalse(res.getEsito())) {
	    response.setStatus(500);
	    response.setContentType("text/plain");
	    if (res.getDescEsito() != null) {
		response.getOutputStream().write(res.getDescEsito().getBytes());
	    } else {
		response.getOutputStream().write("Non è stato possibile attivare la sessione di pagamento.".getBytes());
	    }
	    response.getOutputStream().flush();
	    return;
	}
	String retVal = this.attivaSessionePagamentoResponseBeanToJson(res);
	response.setContentType("application/json");
	response.getOutputStream().write(retVal.getBytes());
    }

    public static class AjaxAnnullaPosizioneDebitoriaRequest {

	private Integer idDettPosizioneDebitoria;
	private String noteAnnullamento;

	public Integer getIdDettPosizioneDebitoria() {

	    return idDettPosizioneDebitoria;
	}

	public void setIdDettPosizioneDebitoria(Integer idDettPosizioneDebitoria) {

	    this.idDettPosizioneDebitoria = idDettPosizioneDebitoria;
	}

	public String getNoteAnnullamento() {

	    return noteAnnullamento;
	}

	public void setNoteAnnullamento(String noteAnnullamento) {

	    this.noteAnnullamento = noteAnnullamento;
	}
    }

    @RequestMapping()
    public void ajaxAnnullaPosizioneDebitoria(@ModelAttribute AjaxAnnullaPosizioneDebitoriaRequest req, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	//Integer idDettPosizioneDebitoria = Integer.parseInt(request.getParameter("idDettPosizioneDebitoria"));
	try {
	    this.service.annullaPosizioneDebitoria(req.getIdDettPosizioneDebitoria(), req.getNoteAnnullamento());
	} catch (Exception e) {
	    response.setStatus(500);
	    response.setContentType("text/plain");
	    response.getOutputStream().write(e.getMessage().getBytes());
	    response.getOutputStream().flush();
	}
    }

    private String attivaSessionePagamentoResponseBeanToJson(AttivaSessionePagamentoResponseBean response) throws JAXBException {

	String richiesta = Utilities.marshalJsonObject(response, AttivaSessionePagamentoResponseBean.class, false, Utilities.JAXB_ENCODING_UTF_8);
	return richiesta;
    }

    private String dettPosizioneDebitoriaResponseTypeToJson(DettPosizioneDebitoriaResponseType posizione) throws JAXBException {

	return Utilities.marshalJsonObject(posizione, DettPosizioneDebitoriaResponseType.class, false, Utilities.JAXB_ENCODING_UTF_8);
    }
}
