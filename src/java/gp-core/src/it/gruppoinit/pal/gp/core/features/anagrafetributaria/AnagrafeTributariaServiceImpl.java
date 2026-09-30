package it.gruppoinit.pal.gp.core.features.anagrafetributaria;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AtEsitiErroreTracciato;
import it.gruppoinit.pal.gp.core.domain.AtEsitoErrori;
import it.gruppoinit.pal.gp.core.domain.AtEsitoGruppo;
import it.gruppoinit.pal.gp.core.domain.AtTestata;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ATTipologiaTracciatoEnum;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ListaEsitiTracciatoBean;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.TestataEsitoTracciatoModel;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.AnTribDettaglioRigheTracciato;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.AnTribErrori;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.AnTribEsitoGruppo;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.AnagrafeTribRigheEsito;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.RiferimentiIstanza;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserErrori;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserErroriTracciato;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserGruppi;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnagrafeTribParserEsito;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser.ATParserFactory;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser.ITracciatoATParser;

@Service
public class AnagrafeTributariaServiceImpl implements AnagrafeTributariaService {

    @Autowired
    private AnagrafeTributariaDAO anagrafeTributariaDAO;
    @Autowired
    private ATParserFactory atParserFactory;
    @Autowired
    private IstanzeDAO istanzeDAO;

    @Override
    public TestataEsitoTracciatoModel getTestataById(Integer idTestata) {

	TestataEsitoTracciatoModel ret = new TestataEsitoTracciatoModel();
	AtTestata t = (AtTestata) anagrafeTributariaDAO.getById(AtTestata.class, new PkId(idTestata));
	ret.setDescrizione(t.getDescrizione());
	ret.setId(t.getId().getCodice());
	String tipologia = t.getTipologia();
	ATTipologiaTracciatoEnum tip = ATTipologiaTracciatoEnum.fromName(tipologia);
	ret.setTipologia(tip.value());
	ret.setNumeroGruppi(anagrafeTributariaDAO.countGruppiPerTestata(idTestata));
	return ret;
    }

    @Override
    public TestataEsitoTracciatoModel salvaNuovoEsitoTracciato(String descrizione, Integer codiceResponsabile, String contenutoFileEsiti,
	    String contenutoFileTracciato) {

	ITracciatoATParser parser = atParserFactory.getParser(contenutoFileTracciato);
	AnagrafeTribParserEsito esito = parser.parse(contenutoFileEsiti, contenutoFileTracciato);
	Integer idTestata = salvaEsitiTracciati(descrizione, codiceResponsabile, esito);
	TestataEsitoTracciatoModel m = getTestataById(idTestata);
	return m;
    }

    private Integer salvaEsitiTracciati(String descrizione, Integer codiceResponsabile, AnagrafeTribParserEsito esito) {

	AtTestata testata = new AtTestata();
	testata.setDescrizione(descrizione);
	testata.setDataInserimento(Calendar.getInstance().getTime());
	testata.setCodiceResponsabile(codiceResponsabile);
	testata.setTipologia(esito.getTipologia().name());
	testata.setSoftware(ORMHelper.getSoftware());
	anagrafeTributariaDAO.saveEntity(testata);
	Integer idTestata = testata.getId().getCodice();
	List<AnTribParserGruppi> gruppi = esito.getGruppi();
	int i = 0;
	for (AnTribParserGruppi anParserGruppi : gruppi) {
	    AtEsitoGruppo g = new AtEsitoGruppo();
	    g.setFkidAtTestata(idTestata);
	    g.setOrdine(i);
	    if (anParserGruppi.getIstanzeTrovate().size() == 1) {
		g.setCodiceistanza(anParserGruppi.getIstanzeTrovate().get(0).getCodiceIstanza());
	    }
	    anagrafeTributariaDAO.saveEntity(g);
	    int idGruppo = g.getId().getCodice();
	    List<AnTribParserErrori> errori = anParserGruppi.getErrori();
	    for (AnTribParserErrori err : errori) {
		AtEsitoErrori erDom = new AtEsitoErrori();
		erDom.setDescrizioneErrore(err.getErrore());
		erDom.setTipoErrore(err.getTipologiaErrore());
		erDom.setFkidAtEsitoGruppo(idGruppo);
		erDom.setIntestazione(err.getIntestazione());
		anagrafeTributariaDAO.saveEntity(erDom);
		erDom.setIntestazione(err.getIntestazione());
		int idEsitoErr = erDom.getId().getCodice();
		List<AnTribParserErroriTracciato> righeTracciato = err.getRigheTracciato();
		for (AnTribParserErroriTracciato rt : righeTracciato) {
		    AtEsitiErroreTracciato t = new AtEsitiErroreTracciato();
		    t.setFkidAtesitoErrori(idEsitoErr);
		    t.setPosNelTracciato(rt.getPosizioneNelTracciato());
		    t.setTracciatoRecord(rt.getRigaTracciato());
		    anagrafeTributariaDAO.saveEntity(t);
		}
	    }
	    i++;
	}
	return idTestata;
    }

    @Override
    public AtEsitoGruppo aggiornaGruppoConIstanza(Integer idGruppo, Integer codiceIstanza) {

	AtEsitoGruppo g = (AtEsitoGruppo) anagrafeTributariaDAO.getById(AtEsitoGruppo.class, new PkId(idGruppo));
	g.setCodiceistanza(codiceIstanza);
	anagrafeTributariaDAO.saveEntity(g);
	return g;
    }

    @Override
    public void aggiornaValidaErrore(Integer idRigaErrore, boolean segnaValido) {

	AtEsitoErrori g = (AtEsitoErrori) anagrafeTributariaDAO.getById(AtEsitoErrori.class, new PkId(idRigaErrore));
	g.setFlagVerificato(segnaValido);
	anagrafeTributariaDAO.saveEntity(g);
    }

    @Override
    public List<ListaEsitiTracciatoBean> findEsitiSalvati(Integer offset, Integer limit) {

	return anagrafeTributariaDAO.findEsitiSalvati(offset, limit);
    }

    @Override
    public void delete(Integer codice) {

	anagrafeTributariaDAO.eliminaEsito(codice);
    }

    @Override
    public AnagrafeTribRigheEsito getRigheEsito(Integer idTestata, Integer offset, Integer limit) {

	AnagrafeTribRigheEsito esito = new AnagrafeTribRigheEsito();
	esito.setOffset(offset);
	esito.setLimit(limit);
	esito.setTotal(anagrafeTributariaDAO.countGruppiPerTestata(idTestata));
	List<AtEsitoGruppo> gruppi = anagrafeTributariaDAO.findByAtTestata(idTestata, offset, limit);
	Map<Integer, RiferimentiIstanza> istanzeTrovate = new HashMap<Integer, RiferimentiIstanza>();
	for (AtEsitoGruppo g : gruppi) {
	    AnTribEsitoGruppo ateg = new AnTribEsitoGruppo();
	    ateg.setIdEsitoGruppo(g.getId().getCodice());
	    List<AtEsitoErrori> errori = anagrafeTributariaDAO.findByAtEsitoGruppi(g.getId().getCodice());
	    for (AtEsitoErrori errore : errori) {
		ateg.getErrori().add(AnTribErrori.fromAtEsitoErrori(errore));
	    }
	    if (g.getCodiceistanza() != null) {
		RiferimentiIstanza is = istanzeTrovate.get(g.getCodiceistanza());
		if (null == is) {
		    is = recuperaIstanza(g.getCodiceistanza());
		}
		ateg.setIstanza(is);
	    }
	    esito.getRisultati().add(ateg);
	}
	return esito;
    }

    private RiferimentiIstanza recuperaIstanza(Integer codiceistanza) {

	Istanze i = istanzeDAO.findById(new PkId(codiceistanza));
	RiferimentiIstanza ri = new RiferimentiIstanza();
	ri.setCodice(String.valueOf(codiceistanza));
	ri.setNumero(i.getNumeroistanza());
	ri.setRichiedente(i.getRichiedente().getDescrizioneRichiedente());
	return ri;
    }

    @Override
    public List<AnTribDettaglioRigheTracciato> getDettaglioRigheTracciato(Integer idGruppo) {

	return anagrafeTributariaDAO.findRigheTracciatoByAtEsitoGruppi(idGruppo);
    }
}
