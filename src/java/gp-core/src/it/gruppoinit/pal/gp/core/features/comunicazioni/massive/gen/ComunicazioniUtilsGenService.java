package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.springframework.ui.Model;

import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDestinatari;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ComunicazioniCommissioniCommand;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;

public interface ComunicazioniUtilsGenService {

    void ajaxAggiungiAllegatiCompilabili(Model model, ComunicazioniCommissioniCommand comunicazioniCommissioniCommand, Integer codiceLetteretipo,
	    HttpServletRequest request, HttpServletResponse response) throws IOException;

    void ajaxRimuoviAllegatoCompilabile(Model model, ComunicazioniCommissioniCommand comunicazioniCommissioniCommand, Integer codiceLetteretipo,
	    HttpServletRequest request, HttpServletResponse response) throws IOException;

    void ajaxAggiungiFirmatario(Model model, ComunicazioniCommissioniCommand comunicazioniCommissioniCommand, Integer codiceFirmatario,
	    HttpServletRequest request, HttpServletResponse response) throws IOException;

    void ajaxRimuoviFirmatario(Model model, ComunicazioniCommissioniCommand comunicazioniCommissioniCommand, Integer codiceFirmatario,
	    HttpServletRequest request, HttpServletResponse response) throws IOException;

    void ajaxSetParametriProtocollazione(ComunicazioniCommissioniCommand comunicazioniCommissioniCommand, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, JAXBException;

    void getDataMailFromMailTipoById(MailtipoService mailTipoService, String mailTipoId, HttpServletRequest request, HttpServletResponse response);

    List<AppIoServizi> findAllAppIoServizi();

    void ajaxGetRigaDettagliata(Model model, HttpServletRequest request, HttpServletResponse response, ContestoComunicazioneEnum contesto)
	    throws IOException, JAXBException;

    AppIoServizi findAppIoServizioById(String guid);

    void insertMassiveDettDestinatari(MassiveDettDestinatari destinatari);
}
