package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.ProtocolloSoggettoCommand;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari.IndirizzoMailResolver;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.fake.FakeAnagrafeService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.fake.FakeIstanzeService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.fake.FakeOggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.fake.FakeVerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.fake.FakeVerticalizzazioniService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

public class ProtocollazioneServiceTest {

    private ProtocollazioneServiceImpl r = new ProtocollazioneServiceImpl();

    @Test
    public void lanciaEventoProtTornaFalseSeStringaVuota() {

	String numeroProtocollo = " ";
	Assert.assertFalse(r.lanciaEventoProtocollazioneEseguita(numeroProtocollo));
    }

    @Test
    public void lanciaEventoProtTornaFalseSeCarattereSpecialeConSpazi() {

	String numeroProtocollo = " - ";
	Assert.assertFalse(r.lanciaEventoProtocollazioneEseguita(numeroProtocollo));
    }

    @Test
    public void lanciaEventoProtTornaFalseSeCarattereSpeciale() {

	String numeroProtocollo = "-";
	Assert.assertFalse(r.lanciaEventoProtocollazioneEseguita(numeroProtocollo));
    }

    @Test
    public void lanciaEventoProtTornaTrueSeNumeroFornito() {

	String numeroProtocollo = "123";
	Assert.assertTrue(r.lanciaEventoProtocollazioneEseguita(numeroProtocollo));
    }

    @Test
    public void lanciaEventoProtTornaTrueSeNumeroFornitoECaratereSpeciale() {

	String numeroProtocollo = "123 - ";
	Assert.assertTrue(r.lanciaEventoProtocollazioneEseguita(numeroProtocollo));
    }

    @Test
    public void protocollaInPartenzaSovrascriveMailMittentePersonaFisicaConIlDomicilioElettronico() {

	String token = "1212342345";
	String idComune = "E256";
	String codiceComune = "E256";
	String software = "CO";
	String classifica = "10.1";
	String flusso = "A";
	String tipoDocumento = "PEC";
	String domicilioElettronico = "stefano.mendichi@alveo.it";
	String pecAnagrafe = "stefano.mendichi@provapec.it";
	String documentoPrincipale = "1";
	boolean inserimentoAutomatico = true;
	//
	Istanze istanza = new Istanze(1);
	istanza.setDomicilioElettronico(domicilioElettronico);
	//
	Anagrafe anagrafe = new Anagrafe();
	anagrafe.setId(new PkId(idComune, 1));
	anagrafe.setPec(pecAnagrafe);
	//
	IVerticalizzazioneProtocolloAttivoService vertProtocolloAttivoService = FakeVerticalizzazioneProtocolloAttivoServiceImpl.Attiva();
	OggettiMetadatiService oggettiMetadatiService = new FakeOggettiMetadatiService(true);
	AnagrafeService anagrafeService = new FakeAnagrafeService(anagrafe);
	IstanzeService istanzeService = new FakeIstanzeService(istanza);
	VerticalizzazioniService vertService = new FakeVerticalizzazioniService();
	//
	ProtocolloMezzi mezzo = new ProtocolloMezzi();
	Amministrazioni amministrazione = new Amministrazioni();
	amministrazione.setId(new PkId(idComune, 1));
	ProtocolloSoggettoCommand destinatario = ProtocolloSoggettoCommand.fromAmministrazione(amministrazione, mezzo);
	//
	IndirizzoMailResolver imr = new IndirizzoMailResolver(vertProtocolloAttivoService, codiceComune, software);
	ProtocolloSoggettoCommand mittente = ProtocolloSoggettoCommand.fromRichiedente(imr, anagrafe, domicilioElettronico, mezzo);
	List<ProtocolloSoggettoCommand> mittenti = new ArrayList<ProtocolloSoggettoCommand>();
	mittenti.add(mittente);
	//
	ProtocollazioneCommand command = new ProtocollazioneCommand();
	command.getComune().setCodicecomune(codiceComune);
	command.getProtSoftware().setCodice(software);
	command.setClassifica(classifica);
	command.setFlusso(flusso);
	command.setTipoDocumento(tipoDocumento);
	command.setInserimentoAutomatico(inserimentoAutomatico);
	command.setDestinatario(destinatario);
	command.setMittentis(mittenti);
	command.setFlgProtocollalinkall(false);
	command.setMettiAllaFirma(false);
	command.setDocumentoPrincipale(documentoPrincipale);
	command.setToken(token);
	command.setEntity(istanza);
	r.setOggettiMetadatiService(oggettiMetadatiService);
	r.setVerticalizzazioniService(vertService);
	r.setAnagrafeService(anagrafeService);
	r.setIstanzeService(istanzeService);
	r.protocolla(ProtocolloSourceEnum.ON_LINE, command, software, codiceComune);
    }
}
