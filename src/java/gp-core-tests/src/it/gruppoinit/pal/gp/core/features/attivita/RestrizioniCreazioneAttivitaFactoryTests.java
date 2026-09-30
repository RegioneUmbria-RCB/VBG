package it.gruppoinit.pal.gp.core.features.attivita;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.features.attivita.restrizioni.RestrizioneVerificataBean;
import it.gruppoinit.pal.gp.core.features.attivita.restrizioni.RestrizioniCreazioneAttivitaFactory;
import it.gruppoinit.pal.gp.core.features.attivita.restrizioni.RestrizioniCreazioneAttivitaParams;
import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;
import it.gruppoinit.pal.gp.core.service.VwIAttivitalistaService;

public class RestrizioniCreazioneAttivitaFactoryTests {

    private IVerticalizzazioneIAttivitaService getVerticalizzazioneService() {

	return new FakeVerticalizzazioneIAttivitaService();
    }

    private VwIAttivitalistaService getVwIAttivitalistaService() {

	List<Integer> lista = new ArrayList<Integer>();
	lista.add(1983);
	return new FakeVwIAttivitalistaService(lista);
    }

    @Test
    public void RestrizioniCreazioneAttivitaFactoryConLocalizzazioniNullNonTornaErrore() {

	RestrizioniCreazioneAttivitaParams parametri = new RestrizioniCreazioneAttivitaParams("TEST COMPANY SRL", null, true);
	RestrizioniCreazioneAttivitaFactory factory = new RestrizioniCreazioneAttivitaFactory(this.getVerticalizzazioneService(),
		this.getVwIAttivitalistaService(), parametri);
	Assert.assertTrue("La factory è stata creata correttamente senza localizzazioni", (factory != null));
    }

    @Test
    public void RestrizioniCreazioneAttivitaFactoryConDenominazioneNullNonTornaErrore() {

	RestrizioniCreazioneAttivitaParams parametri = new RestrizioniCreazioneAttivitaParams(null, null, true);
	RestrizioniCreazioneAttivitaFactory factory = new RestrizioniCreazioneAttivitaFactory(this.getVerticalizzazioneService(),
		this.getVwIAttivitalistaService(), parametri);
	Assert.assertTrue("La factory è stata creata correttamente senza denominazione", (factory != null));
    }

    @Test
    public void RestrizioniCreazioneAttivitaFactoryVerificaConParametriNullTornaArrayListVuoto() {

	RestrizioniCreazioneAttivitaParams parametri = new RestrizioniCreazioneAttivitaParams(null, null, true);
	RestrizioniCreazioneAttivitaFactory factory = new RestrizioniCreazioneAttivitaFactory(this.getVerticalizzazioneService(),
		this.getVwIAttivitalistaService(), parametri);
	List<RestrizioneVerificataBean> elenco = factory.verificaRestrizioni();
	Assert.assertTrue("Le verifiche con denominazione null e localizzazioni null tornano una lista vuota", elenco.isEmpty());
    }

    @Test
    public void RestrizioniCreazioneAttivitaFactoryVerificaConDenominazionePresenteTornaArrayListValorizzato() {

	RestrizioniCreazioneAttivitaParams parametri = new RestrizioniCreazioneAttivitaParams("TEST COMPANY SRL", null, true);
	RestrizioniCreazioneAttivitaFactory factory = new RestrizioniCreazioneAttivitaFactory(this.getVerticalizzazioneService(),
		this.getVwIAttivitalistaService(), parametri);
	List<RestrizioneVerificataBean> elenco = factory.verificaRestrizioni();
	Assert.assertTrue("Sono state trovate 1 occorrenze", elenco.size() == 1);
    }

    @Test
    public void RestrizioniCreazioneAttivitaFactoryVerificaConLocalizzazionePresenteTornaArrayListValorizzato() {

	Stradario stradario = new Stradario();
	stradario.setPrefisso("VIA");
	stradario.setDescrizione("MANDRELLO");
	Istanzestradario localizzazione = new Istanzestradario();
	localizzazione.setStradario(stradario);
	localizzazione.setCivico("33");
	Set<Istanzestradario> localizzazioni = new HashSet<Istanzestradario>();
	localizzazioni.add(localizzazione);
	RestrizioniCreazioneAttivitaParams parametri = new RestrizioniCreazioneAttivitaParams(null, localizzazioni, true);
	RestrizioniCreazioneAttivitaFactory factory = new RestrizioniCreazioneAttivitaFactory(this.getVerticalizzazioneService(),
		this.getVwIAttivitalistaService(), parametri);
	List<RestrizioneVerificataBean> elenco = factory.verificaRestrizioni();
	Assert.assertTrue("Sono state trovate più occorrenze", elenco.size() == 1);
    }

    @Test
    public void RestrizioniCreazioneAttivitaFactoryVerificaConLocalizzazionePresenteEDenominazionePresenteTornaArrayListValorizzato() {

	Stradario stradario = new Stradario();
	stradario.setPrefisso("VIA");
	stradario.setDescrizione("MANDRELLO");
	Istanzestradario localizzazione = new Istanzestradario();
	localizzazione.setStradario(stradario);
	localizzazione.setCivico("33");
	Set<Istanzestradario> localizzazioni = new HashSet<Istanzestradario>();
	localizzazioni.add(localizzazione);
	RestrizioniCreazioneAttivitaParams parametri = new RestrizioniCreazioneAttivitaParams("TEST COMPANY SRL", localizzazioni, true);
	RestrizioniCreazioneAttivitaFactory factory = new RestrizioniCreazioneAttivitaFactory(this.getVerticalizzazioneService(),
		this.getVwIAttivitalistaService(), parametri);
	List<RestrizioneVerificataBean> elenco = factory.verificaRestrizioni();
	Assert.assertTrue("Sono state trovate 2 occorrenze", elenco.size() == 2);
    }
}
