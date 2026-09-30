package it.gruppoinit.pal.gp.areariservata.web.util;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.AltriSoggettiType;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.CampoDinamicoType;
import it.init.sigepro.rte.types.CampoSchedaType;
import it.init.sigepro.rte.types.CampoStaticoType;
import it.init.sigepro.rte.types.CircoscrizioneType;
import it.init.sigepro.rte.types.CittadinanzaType;
import it.init.sigepro.rte.types.CodiceDescrizioneType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DatiIscrizioneAlboType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.EstremiAttoType;
import it.init.sigepro.rte.types.FrazioneType;
import it.init.sigepro.rte.types.IscrizioneRegistroType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.PosizioneCampoType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.ProprietaCampoDinamicoType;
import it.init.sigepro.rte.types.QuartiereType;
import it.init.sigepro.rte.types.RegistroREAType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RuoloType;
import it.init.sigepro.rte.types.SchedaType;
import it.init.sigepro.rte.types.ValoreCampoDinamicoType;
import it.init.sigepro.rte.types.ValoriCampoCheckboxType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StcDomainHelper {

    private static final Logger log = LoggerFactory.getLogger(StcDomainHelper.class);

    public static PersonaGiuridicaType populatePersonaGiuridicaType(Anagrafe anagrafe) {

	PersonaGiuridicaType pGiuridica = getNewPersonaGiuridicaType();
	pGiuridica.setCodiceFiscale(anagrafe.getCodicefiscale());
	pGiuridica.setPartitaIva(anagrafe.getPartitaiva());
	pGiuridica.setRagioneSociale(anagrafe.getNominativo());
	Formegiuridiche formaGiuridica = anagrafe.getFormagiuridica();
	if (formaGiuridica != null) {
	    pGiuridica.setNaturaGiuridica(formaGiuridica.getFormagiuridica());
	}
	// Iscrizione CCIAA (registro ditte)
	IscrizioneRegistroType iscrizioneCCIA = pGiuridica.getIscrizioneCCIAA();
	if (anagrafe.getComunecomregditte() != null) {
	    // Comune di iscrizione CCIAA
	    ComuneType comuneCCIA = iscrizioneCCIA.getComune();
	    comuneCCIA.setCodiceCatastale(anagrafe.getComunecomregditte().getCf());
	    comuneCCIA.setComune(anagrafe.getComunecomregditte().getComune());
	}
	if (anagrafe.getDataregditte() != null) {
	    // Data iscrizione CCIAA
	    GregorianCalendar dataCCIA = new GregorianCalendar();
	    dataCCIA.setTime(anagrafe.getDataregditte());
	    iscrizioneCCIA.setData(Utilities.getXMLGregorianCalendar(dataCCIA));
	}
	// Numero iscrizione CCIAA
	iscrizioneCCIA.setNumero(anagrafe.getRegditte());
	// Iscrizione REA
	RegistroREAType iscrizioneREA = pGiuridica.getIscrizioneREA();
	iscrizioneREA.setNumero(anagrafe.getNumiscrrea());
	if (anagrafe.getProvinciarea() != null) {
	    iscrizioneREA.setSiglaProvincia(anagrafe.getProvinciarea());
	}
	if (anagrafe.getDataiscrrea() != null) {
	    GregorianCalendar dataREA = new GregorianCalendar();
	    dataREA.setTime(anagrafe.getDataiscrrea());
	    iscrizioneREA.setData(Utilities.getXMLGregorianCalendar(dataREA));
	}
	// Indirizzo corrispondenza
	if (StringUtils.isNotBlank(anagrafe.getIndirizzocorrispondenza())) {
	    LocalizzazioneType indirizzoCorrispondenza = pGiuridica.getIndirizzoCorrispondenza();
	    indirizzoCorrispondenza.setIndirizzo(anagrafe.getIndirizzocorrispondenza());
	    indirizzoCorrispondenza.setCap(anagrafe.getCapcorrispondenza());
	    indirizzoCorrispondenza.setCivico("");
	    if (anagrafe.getComunecorrispondenza() != null && anagrafe.getComunecorrispondenza().getCodicecomune() != null) {
		ComuneType comuneCorr = indirizzoCorrispondenza.getComune();
		comuneCorr.setCodiceCatastale(anagrafe.getComunecorrispondenza().getCf());
		comuneCorr.setComune(anagrafe.getComunecorrispondenza().getComune());
	    }
	    indirizzoCorrispondenza.setLocalita(anagrafe.getCittacorrispondenza());
	    indirizzoCorrispondenza.setProvincia(anagrafe.getProvinciacorrispondenza());
	}
	// Sede legale
	if (StringUtils.isNotBlank(anagrafe.getIndirizzo())) {
	    LocalizzazioneType _sedeLegale = pGiuridica.getSedeLegale();
	    _sedeLegale.setCap(anagrafe.getCap());
	    _sedeLegale.setCivico("");
	    _sedeLegale.setIndirizzo(anagrafe.getIndirizzo());
	    _sedeLegale.setLocalita(anagrafe.getCitta());
	    _sedeLegale.setProvincia(anagrafe.getProvincia());
	    if (anagrafe.getComuneResidenza() != null) {
		ComuneType _comuneSedeLegale = _sedeLegale.getComune();
		Comuni comuneSedeLegale = anagrafe.getComuneResidenza();
		_comuneSedeLegale.setCodiceCatastale(comuneSedeLegale.getCf());
		_comuneSedeLegale.setComune(comuneSedeLegale.getComune());
	    }
	}
	// Altri dati
	pGiuridica.setFax(anagrafe.getFax());
	pGiuridica.setTelefono(anagrafe.getTelefono());
	pGiuridica.setEmail(anagrafe.getEmail());
	pGiuridica.setPec(anagrafe.getPec());
	return pGiuridica;
    }

    public static RichiedenteType populateRichiedenteType(Anagrafe anagrafe) {

	RichiedenteType richiedenteType = getNewRichiedenteType();
	PersonaFisicaType pFisica = populatePersonaFisicaType(anagrafe);
	richiedenteType.setAnagrafica(pFisica);
	return richiedenteType;
    }

    public static PersonaFisicaType populatePersonaFisicaType(Anagrafe anagrafe) {

	PersonaFisicaType pFisica = getNewPersonaFisicaType();
	pFisica.setCodiceFiscale(anagrafe.getCodicefiscale());
	pFisica.setCognome(anagrafe.getNominativo());
	pFisica.setNome(anagrafe.getNome() == null ? "" : anagrafe.getNome());
	pFisica.setSesso(anagrafe.getSesso());
	Titoli titolo = anagrafe.getTitolo();
	if (titolo != null) {
	    pFisica.setTitolo(titolo.getTitolo());
	}
	if (anagrafe.getDatanascita() != null) {
	    GregorianCalendar dataNascita = new GregorianCalendar();
	    dataNascita.setTime(anagrafe.getDatanascita());
	    pFisica.setDataNascita(Utilities.getXMLGregorianCalendar(dataNascita));
	}
	if (anagrafe.getComuneNascita() != null) {
	    Comuni comuneNascita = anagrafe.getComuneNascita();
	    ComuneType _comuneNascita = pFisica.getComuneNascita();
	    _comuneNascita.setCodiceCatastale(comuneNascita.getCf());
	    _comuneNascita.setComune(comuneNascita.getComune());
	}
	// Residenza
	//l'indirizzo è l'unico tag obbligatorio, quindi se vuoto l'ogg locType non deve essere settato.
	// in alternativa popolare a stringa vuota l'indirizzo.
	LocalizzazioneType _locResidenza = pFisica.getResidenza();
	_locResidenza.setIndirizzo(anagrafe.getIndirizzo() == null ? "" : anagrafe.getIndirizzo());
	_locResidenza.setCivico("");
	_locResidenza.setLocalita(anagrafe.getCitta());
	_locResidenza.setCap(anagrafe.getCap());
	_locResidenza.setProvincia(anagrafe.getProvincia());
	if (anagrafe.getComuneResidenza() != null) {
	    Comuni comuneResidenza = anagrafe.getComuneResidenza();
	    ComuneType _comuneResidenza = _locResidenza.getComune();
	    _comuneResidenza.setCodiceCatastale(comuneResidenza.getCf());
	    _comuneResidenza.setComune(comuneResidenza.getComune());
	}
	if (!EntityUtils.isNestedPropertyBlank(anagrafe.getCittadinanza(), "codice")) {
	    CittadinanzaType cittadinanzaType = pFisica.getCittadinanza();
	    cittadinanzaType.setId(anagrafe.getCittadinanza().getCodice().toString());
	    cittadinanzaType.setDescrizione(anagrafe.getCittadinanza().getCittadinanza());
	    cittadinanzaType.setCodiceCatastale(anagrafe.getCittadinanza().getCf());
	}
	pFisica.setEmail(anagrafe.getEmail());
	pFisica.setTelefono(anagrafe.getTelefono());
	pFisica.setPec(anagrafe.getPec());
	return pFisica;
    }

    public static RichiedenteType getNewRichiedenteType() {

	RichiedenteType r = new RichiedenteType();
	r.setAnagrafica(getNewPersonaFisicaType());
	r.setRuolo(new RuoloType());
	return r;
    }

    public static LocalizzazioneType getNewLocalizzazioneType() {

	LocalizzazioneType loc = new LocalizzazioneType();
	loc.setComune(new ComuneType());
	return loc;
    }

    public static PersonaFisicaType getNewPersonaFisicaType() {

	PersonaFisicaType p = new PersonaFisicaType();
	p.setCittadinanza(new CittadinanzaType());
	p.setComuneNascita(new ComuneType());
	p.setProcura(getNewDocumentiType());
	p.setResidenza(getNewLocalizzazioneType());
	p.setDatiIscrizioneAlbo(getNewDatiIscrizioneAlbo());
	return p;
    }

    public static PersonaGiuridicaType getNewPersonaGiuridicaType() {

	PersonaGiuridicaType p = new PersonaGiuridicaType();
	p.setIndirizzoCorrispondenza(getNewLocalizzazioneType());
	p.setIscrizioneCCIAA(getNewIscrizioneRegistroType());
	p.setIscrizioneREA(new RegistroREAType());
	//p.setLegaleRappresentante(getNewPersonaFisicaType());
	p.setLegaleRappresentante(null);
	p.setSedeLegale(getNewLocalizzazioneType());
	return p;
    }

    public static AltriSoggettiType getNewAltriSoggettiType() {

	AltriSoggettiType a = new AltriSoggettiType();
	a.setAnagraficaCollegata(getNewAnagrafeType());
	a.setSoggetto(getNewAnagrafeType());
	a.setTipoRapporto(new RuoloType());
	return a;
    }

    public static AnagrafeType getNewAnagrafeType() {

	AnagrafeType a = new AnagrafeType();
	a.setPersonaFisica(getNewPersonaFisicaType());
	a.setPersonaGiuridica(getNewPersonaGiuridicaType());
	return a;
    }

    public static DocumentiType getNewDocumentiType() {

	DocumentiType d = new DocumentiType();
	AllegatiType a = new AllegatiType();
	AllegatoBinarioType ab = new AllegatoBinarioType();
	a.setFile(ab);
	d.setAllegati(a);
	return d;
    }

    public static DatiIscrizioneAlboType getNewDatiIscrizioneAlbo() {

	DatiIscrizioneAlboType d = new DatiIscrizioneAlboType();
	d.setTipoOrdineProfessionisti(new CodiceDescrizioneType());
	return d;
    }

    public static IscrizioneRegistroType getNewIscrizioneRegistroType() {

	IscrizioneRegistroType i = new IscrizioneRegistroType();
	i.setComune(new ComuneType());
	return i;
    }

    public static LocalizzazioneNelComuneType getNewLocalizzazioneNelComuneType() {

	LocalizzazioneNelComuneType l = new LocalizzazioneNelComuneType();
	l.setCircoscrizione(new CircoscrizioneType());
	l.setFrazione(new FrazioneType());
	l.setQuartiere(new QuartiereType());
	return l;
    }

    public static ProcedimentoType getNewProcedimentoType() {

	ProcedimentoType p = new ProcedimentoType();
	p.setEstremiAtto(new EstremiAttoType());
	return p;
    }

    public static CampoSchedaType getNewCampoSchedaType() {

	CampoSchedaType campo = new CampoSchedaType();
	campo.setCampoDinamico(StcDomainHelper.getNewCampoDinamicoType());
	// campo.setCampoStatico(StcDomainHelper.getNewCampoStaticoType());
	return campo;
    }

    public static CampoDinamicoType getNewCampoDinamicoType() {

	CampoDinamicoType d = new CampoDinamicoType();
	// d.setProprieta(StcDomainHelper.getNewProprietaCampoDinamicoType());
	d.setValoreUtente(new ValoreCampoDinamicoType());
	return d;
    }

    public static ProprietaCampoDinamicoType getNewProprietaCampoDinamicoType() {

	ProprietaCampoDinamicoType p = new ProprietaCampoDinamicoType();
	p.setPosizione(new PosizioneCampoType());
	p.setValoriCheckBox(new ValoriCampoCheckboxType());
	return p;
    }

    public static CampoStaticoType getNewCampoStaticoType() {

	CampoStaticoType c = new CampoStaticoType();
	c.setPosizione(new PosizioneCampoType());
	return c;
    }

    public static ProcedimentoType populateProcedimentoType(Inventarioprocedimenti procedimento) {

	ProcedimentoType p = new ProcedimentoType();
	p.setCodice(procedimento.getId().getCodice().toString());
	p.setDescrizione(procedimento.getProcedimento());
	return p;
    }

    public static SchedaType populateSchedaType(Dyn2Modellit dyn2Modellit) {

	SchedaType s = new SchedaType();
	s.setCodice(dyn2Modellit.getId().getCodice().toString());
	s.setNome(dyn2Modellit.getCodiceScheda());
	s.setDescrizione(dyn2Modellit.getDescrizione());
	return s;
    }

    @SuppressWarnings("rawtypes")
    public static String marshalObject(Object jaxbElement, Class clazz) throws Exception {

	String ret = "";
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
	    Marshaller marshaller = jaxbContext.createMarshaller();
	    StringWriter stringWriter = new StringWriter();
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.marshal(jaxbElement, stringWriter);
	    ret = stringWriter.toString();
	} catch (Exception e) {
	    log.error("marshalObject()", e);
	    throw e;
	}
	return ret;
    }

    @SuppressWarnings("rawtypes")
    public static Object unmarshalObject(String xml, Class clazz) throws Exception {

	Object obj = null;
	try {
	    InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
	    JAXBContext context = JAXBContext.newInstance(clazz);
	    javax.xml.bind.Unmarshaller u = context.createUnmarshaller();
	    obj = u.unmarshal(is);
	} catch (Exception e) {
	    log.error("unmarshalObject()", e);
	    throw e;
	}
	return obj;
    }
}
