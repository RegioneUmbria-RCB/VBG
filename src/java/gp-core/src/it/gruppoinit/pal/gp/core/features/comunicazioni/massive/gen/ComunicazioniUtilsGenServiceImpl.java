package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziId;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDestinatari;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaMassiveDDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoServiziService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ListaParametriprotocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.model.ComunicazioneMassivaRigaModelGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ComunicazioniCommissioniCommand;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ProtocollaParametriCommand;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneMassivaRigaModel;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class ComunicazioniUtilsGenServiceImpl implements ComunicazioniUtilsGenService {

    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private IAppIoServiziService appIoServiziService;
    @Autowired
    private IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO;
    @Autowired
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    @Autowired
    private IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO;
    @Autowired
    private AutorizzazioniService autorizzazioniService;

    @Override
    public void ajaxAggiungiAllegatiCompilabili(Model model, ComunicazioniCommissioniCommand comunicazioniCommissioniCommand,
	    Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (verificaAllegatoCompilabileGiaAggiunto(codiceLetteretipo, comunicazioniCommissioniCommand)) {
	    return;
	}
	Letteretipo letteretipo = letteretipoService.findById(new PkId(codiceLetteretipo));
	comunicazioniCommissioniCommand.getAllegaticompilabili().add(letteretipo);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
	return;
    }

    public boolean verificaAllegatoCompilabileGiaAggiunto(Integer codiceLetteretipo,
	    ComunicazioniCommissioniCommand comunicazioniCommissioniCommand) {

	if (comunicazioniCommissioniCommand != null) {
	    for (Letteretipo letteretipo : comunicazioniCommissioniCommand.getAllegaticompilabili()) {
		if (letteretipo.getId().getCodice().equals(codiceLetteretipo)) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public void ajaxRimuoviAllegatoCompilabile(Model model, ComunicazioniCommissioniCommand comunicazioniCommissioniCommand,
	    Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Letteretipo letteretipo = letteretipoService.findById(new PkId(codiceLetteretipo));
	comunicazioniCommissioniCommand.getAllegaticompilabili().remove(letteretipo);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
    }

    @Override
    public void ajaxAggiungiFirmatario(Model model, ComunicazioniCommissioniCommand comunicazioniCommissioniCommand, Integer codiceFirmatario,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (verificaFirmatarioGiaAggiunto(codiceFirmatario, comunicazioniCommissioniCommand)) {
	    return;
	}
	Responsabili responsabili = responsabiliService.findById(new PkId(codiceFirmatario));
	comunicazioniCommissioniCommand.getFirmatari().add(responsabili);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
	return;
    }

    public boolean verificaFirmatarioGiaAggiunto(Integer codiceFirmatario, ComunicazioniCommissioniCommand comunicazioniCommissioniCommand) {

	if (comunicazioniCommissioniCommand != null) {
	    for (Responsabili firmatario : comunicazioniCommissioniCommand.getFirmatari()) {
		if (firmatario.getId().getCodice().equals(codiceFirmatario)) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public void ajaxRimuoviFirmatario(Model model, ComunicazioniCommissioniCommand comunicazioniCommissioniCommand, Integer codiceFirmatario,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	Responsabili firmatario = responsabiliService.findById(new PkId(codiceFirmatario));
	comunicazioniCommissioniCommand.getFirmatari().remove(firmatario);
	model.addAttribute("comunicazioniCommissioniCommand", comunicazioniCommissioniCommand);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
    }

    @Override
    public void ajaxSetParametriProtocollazione(ComunicazioniCommissioniCommand comunicazioniCommissioniCommand, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, JAXBException {

	ListaParametriprotocolloPerEnteHelper l = this.jsonToParametriProtocolloPerEnteHelper(request);
	ProtocollaParametriCommand prot = new ProtocollaParametriCommand();
	prot.setParametriPerEnte(new ArrayList<IParametriProtocolloPerEnteHelper>(l.getListParamprotoPerEnte()));
	comunicazioniCommissioniCommand.setProtocollaParametriCommand(prot);
	response.setContentType("text/plain");
	response.getOutputStream().write("OK".getBytes());
    }

    protected ListaParametriprotocolloPerEnteHelper jsonToParametriProtocolloPerEnteHelper(HttpServletRequest request)
	    throws JAXBException, IOException {

	return Utilities.unMarshallJsonStream(request.getInputStream(), ListaParametriprotocolloPerEnteHelper.class, false);
    }

    @Override
    public void getDataMailFromMailTipoById(MailtipoService mailTipoService, String mailTipoId, HttpServletRequest request,
	    HttpServletResponse response) {

	Mailtipo mailTipo = mailTipoService.findById(new PkId(Integer.parseInt(mailTipoId)));
	Map<String, String> jsonMap = new HashMap<String, String>();
	jsonMap.put("oggetto", mailTipo.getOggetto());
	jsonMap.put("corpo", mailTipo.getCorpo());
	jsonMap.put("descrizione", mailTipo.getDescrizione());
	ObjectMapper objectMapper = new ObjectMapper();
	String json;
	try {
	    json = objectMapper.writeValueAsString(jsonMap);
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	    throw new RuntimeException("Error eseguendo la get sul parsing");
	}
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	try {
	    response.getWriter().write(json);
	} catch (IOException e) {
	    throw new RuntimeException("Error in response");
	}
    }

    @Override
    public List<AppIoServizi> findAllAppIoServizi() {

	return appIoServiziService.findAll(null, null);
    }

    @Override
    public void ajaxGetRigaDettagliata(Model model, HttpServletRequest request, HttpServletResponse response, ContestoComunicazioneEnum contesto)
	    throws IOException, JAXBException {

	Integer idRiga = Integer.parseInt(request.getParameter("idRiga"));
	MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(idRiga);
	ComunicazioneMassivaRigaModelGen riga = (ComunicazioneMassivaRigaModelGen) ComunicazioneMassivaRigaModel.fromMassiveDettaglio(dettaglio,
		new WorkFlowComunicazioniGenService(null, null, null, null, null, null, null, null, null, null, null),
		new ComunicazioneMassivaRigaModelGen(), appIoCodaMassiveDDAO);
	if (ContestoComunicazioneEnum.MERCATI == contesto) {
	    List<Integer> autorizzazioni = comunicazioniMassiveGenDAO.getAutorizzazioniFromDettaglio(idRiga);
	    if (autorizzazioni != null && !autorizzazioni.isEmpty()) {
		List<String> lista = new ArrayList<String>();
		for (Integer autorizzazione : autorizzazioni) {
		    Autorizzazioni aut = autorizzazioniService.findById(new PkId(autorizzazione));
		    if (aut != null && aut.getId() != null && aut.getId().getCodice() != null) {
			Set<AutorizzazioniConcessioni> autorizzazioniConcessionisForFkAutconcAutatt = aut
				.getAutorizzazioniConcessionisForFkAutconcAutatt();
			if (autorizzazioniConcessionisForFkAutconcAutatt != null && !autorizzazioniConcessionisForFkAutconcAutatt.isEmpty()) {
			    lista.add(autorizzazioniConcessionisForFkAutconcAutatt.iterator().next().getTransientEstremiConcessione());
			} else {
			    lista.add(aut.getTransientEstremiAut());
			}
		    }
		    //		    lista.add(autorizzazione != null
		    //			    ? aut.getAutoriznumero() + " del " + Utilities.formatDate(aut.getAutorizdata(), false) + " " +
		    //			      aut.getAutorizcomune().getComune()
		    //			    : " ");
		}
		riga.setAutorizzazioni(lista);
	    }
	}
	if (ContestoComunicazioneEnum.ISTANZE == contesto) {
	    List<Istanze> istanze = comunicazioniMassiveGenDAO.getIstanzeListFromDettaglio(idRiga);
	    if (istanze != null && !istanze.isEmpty()) {
		List<String> lista = new ArrayList<String>();
		for (Istanze istanza : istanze) {
		    lista.add(
			    istanza.getNumeroistanza() != null
				    ? istanza.getNumeroistanza() +
					    " del " +
					    Utilities.formatDate(istanza.getData(), false) +
					    " " +
					    istanza.getComune().getComune()
				    : " ");
		}
		riga.setIstanze(lista);
	    }
	}
	String richiesta = Utilities.marshalJsonObject(riga, ComunicazioneMassivaRigaModelGen.class, false, Utilities.JAXB_ENCODING_UTF_8);
	response.setContentType("application/json");
	response.getOutputStream().write(richiesta.getBytes("utf-8"));
    }

    @Override
    public AppIoServizi findAppIoServizioById(String guid) {

	AppIoServiziId id = new AppIoServiziId();
	id.setIdentificativoServizio(guid);
	return appIoServiziService.findById(id);
    }

    @Override
    public void insertMassiveDettDestinatari(MassiveDettDestinatari destinatari) {

	comunicazioniMassiveDettaglioDAO.insertMassiveDettDestinatari(destinatari);
    }
}
