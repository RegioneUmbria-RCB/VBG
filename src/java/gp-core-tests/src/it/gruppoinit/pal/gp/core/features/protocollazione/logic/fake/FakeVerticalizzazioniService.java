package it.gruppoinit.pal.gp.core.features.protocollazione.logic.fake;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.ConfigurazioneRegoleParametroHelper;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniconfigurazioniHelper;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.FakeVerticalizzazioneParametro;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class FakeVerticalizzazioniService implements VerticalizzazioniService {

    private boolean attiva;
    private String valore;
    private List<FakeVerticalizzazioneParametro> fParametri = new ArrayList<FakeVerticalizzazioneParametro>();

    public FakeVerticalizzazioniService() {

	fParametri.add(new FakeVerticalizzazioneParametro("PROTOCOLLO_ATTIVO", "IS_SMISTAMENTO_MULTIPLO", "E256", "CO", "0"));
	fParametri.add(new FakeVerticalizzazioneParametro("PROTOCOLLO_ATTIVO", "GESTIONE_PEC", "E256", "CO", "0"));
	fParametri.add(new FakeVerticalizzazioneParametro("PROTOCOLLO_ATTIVO", "GESTIONE_PEC", "E256", "TT", "0"));
	fParametri.add(new FakeVerticalizzazioneParametro("PROTOCOLLO_ATTIVO", "FLUSSI_VER_FIRMA_DOC_PRINC", "E256", "TT", "A"));
    }

    public static FakeVerticalizzazioniService AttivaConGetStringValorizzato(String valore) {

	FakeVerticalizzazioniService v = new FakeVerticalizzazioniService();
	v.attiva = true;
	v.valore = valore;
	return v;
    }

    public static FakeVerticalizzazioniService Attiva() {

	FakeVerticalizzazioniService v = new FakeVerticalizzazioniService();
	v.attiva = true;
	return v;
    }

    public static FakeVerticalizzazioniService NonAttiva() {

	FakeVerticalizzazioniService v = new FakeVerticalizzazioniService();
	v.attiva = false;
	return v;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComune(String modulo, String parametro, String codiceComune) {

	return getVerticalizzazioniparametriPerComuneESoftware(modulo, parametro, codiceComune, "TT");
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComuneESoftware(String modulo, String parametro, String codiceComune,
	    String software) {

	for (FakeVerticalizzazioneParametro fParametro : fParametri) {
	    if (fParametro.getModulo().equalsIgnoreCase(modulo) && fParametro.getParametro().equalsIgnoreCase(parametro)
		    && fParametro.getCodiceComune().equalsIgnoreCase(codiceComune) && fParametro.getSoftware().equalsIgnoreCase(software)) {
		Verticalizzazioniparametri vertParam = new Verticalizzazioniparametri();
		vertParam.setId(new PkId());
		vertParam.setComune(new Comuni(codiceComune));
		vertParam.setSoftware(new Software(software));
		vertParam.setValore(fParametro.getValore());
		return vertParam;
	    }
	}
	return null;
    }

    @Override
    public boolean isAttiva(String modulo) {

	return this.attiva;
    }

    @Override
    public boolean isAttiva(String modulo, String software) {

	return this.attiva;
    }

    @Override
    public boolean isAttivaPerComune(String modulo, String codiceComune) {

	return this.attiva;
    }

    @Override
    public boolean isAttivaPerComuneESoftware(String modulo, String software, String codiceComune) {

	return this.attiva;
    }

    @Override
    public boolean isAttivaAndParametroEqualsToValore(String modulo, String parametro, String valore) {

	return this.attiva;
    }

    @Override
    public Verticalizzazioni findByModuloEComune(String modulo, String codiceComune) {

	return null;
    }

    @Override
    public List<Verticalizzazioni> findByVerticalizzazionibase(Verticalizzazionibase verticalizzazionibase) {

	return null;
    }

    @Override
    public Set<Verticalizzazioni> findByVerticalizzazionibaseAndCheckConfigurabilePerOperatore(Verticalizzazionibase verticalizzazionibase) {

	return null;
    }

    @Override
    public boolean isInstallazioneEnterprise() {

	return false;
    }

    @Override
    public void resetObjectCached() {

    }

    @Override
    public Map<String, String> getVerticalizzazioniparametriMap(String modulo) {

	return null;
    }

    @Override
    public Verticalizzazioni findByComuneAndModulo(Verticalizzazionibase verticalizzazionibase, String codicecomune, String software) {

	return null;
    }

    @Override
    public void insert(String modulo, String codicecomune, String software) {

    }

    @Override
    public void delete(String modulo, String codicecomune, String software) {

    }

    @Override
    public boolean checkConfigurazioneMultiplaPerComune(String modulo, String parametro) {

	return false;
    }

    @Override
    public boolean checkConfigurazioneMultiplaPerSoftware(String modulo, String parametro) {

	return false;
    }

    @Override
    public List<VerticalizzazioniconfigurazioniHelper> findListaConfigurazioniPerComuneESoftware(String modulo, String parametro) {

	return null;
    }

    @Override
    public List<ChiaveValoreBean<Comuni, Software>> findComuniESoftware(String modulo, String... parametro) {

	return null;
    }

    @Override
    public List<ConfigurazioneRegoleParametroHelper> findConfigurazioneComune(String modulo, String codiceComune) {

	return null;
    }

    @Override
    public boolean isAttivaPerQualsiasiSoftware(String modulo) {

	return this.attiva;
    }

    @Override
    public boolean isAttivaSicurezzaSistema() {

	return false;
    }

    @Override
    public boolean getBoolean(String modulo, String parametro, String valoreConfrontoTrue) {

	return false;
    }

    @Override
    public boolean getBoolean(String modulo, String parametro, String valoreConfrontoTrue, Boolean defaultValue) {

	return false;
    }

    @Override
    public String getString(String modulo, String parametro) {

	return valore;
    }

    @Override
    public String getString(String modulo, String parametro, String defaultValue) {

	return valore;
    }

    @Override
    public Date getDate(String modulo, String parametro, Date defaultValue) {

	return null;
    }

    @Override
    public Date getDate(String modulo, String parametro, String formato, Date defaultValue) {

	return null;
    }

    @Override
    public Integer getInteger(String modulo, String parametro) {

	return null;
    }

    @Override
    public Integer getInteger(String modulo, String parametro, Integer defaultValue) {

	return null;
    }

    @Override
    public BigDecimal getBigDecimal(String modulo, String parametro) {

	return null;
    }

    @Override
    public BigDecimal getBigDecimal(String modulo, String parametro, BigDecimal defaultValue) {

	return null;
    }

    @Override
    public List<CodiceDescrizioneBean> findComuniPerRegolaEParametro(String modulo, String parametro) {

	return null;
    }

    @Override
    public List<String> findValoreByModuloEParametro(String modulo, String parametro) {

	return null;
    }

    @Override
    public List<Verticalizzazioni> findAttivazioni(String modulo) {

	return null;
    }

    @Override
    public void insert(Verticalizzazioni entity) {

    }

    @Override
    public void update(Verticalizzazioni entity) {

    }

    @Override
    public void delete(Verticalizzazioni entity) {

    }

    @Override
    public List<Verticalizzazioni> findAll(Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public Verticalizzazioni findById(PkId id) {

	return null;
    }

    @Override
    public Verticalizzazioni bindDomainObject(Verticalizzazioni entity, Class<?> idClass, String idPath) {

	return null;
    }

    @Override
    public PkId newIdFromSequencetable(Verticalizzazioni entity) {

	return null;
    }

    @Override
    public List<Verticalizzazioniparametri> getVerticalizzazioniparametri(String modulo) {

	return null;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametri(String modulo, String parametro) {

	return null;
    }

    @Override
    public String getVerticalizzazioniparametriValore(String modulo, String parametro) {

	return null;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametri(String modulo, String parametro, String codiceSoftware) {

	return null;
    }

    @Override
    public Verticalizzazioni findByModulo(String modulo) {

	return null;
    }
}
