package it.gruppoinit.pal.gp.core.features.sistema;

import java.math.BigDecimal;
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
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class FakeVerticalizzazioneServiceImpl implements VerticalizzazioniService {

    private boolean attiva;
    private String valore;

    public static FakeVerticalizzazioneServiceImpl AttivaConGetStringValorizzato(String valore) {

	FakeVerticalizzazioneServiceImpl v = new FakeVerticalizzazioneServiceImpl();
	v.attiva = true;
	v.valore = valore;
	return v;
    }

    public static FakeVerticalizzazioneServiceImpl Attiva() {

	FakeVerticalizzazioneServiceImpl v = new FakeVerticalizzazioneServiceImpl();
	v.attiva = true;
	return v;
    }

    public static FakeVerticalizzazioneServiceImpl NonAttiva() {

	FakeVerticalizzazioneServiceImpl v = new FakeVerticalizzazioneServiceImpl();
	v.attiva = false;
	return v;
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
    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComune(String modulo, String parametro, String codiceComune) {

	return null;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComuneESoftware(String modulo, String parametro, String codiceComune,
	    String software) {

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

    @Override
    public boolean isAttiva(String modulo) {

	return this.attiva;
    }

    @Override
    public boolean isAttiva(String modulo, String software) {

	return false;
    }

    @Override
    public Verticalizzazioni findByModuloEComune(String modulo, String codiceComune) {

	return null;
    }

    @Override
    public boolean isAttivaPerComune(String modulo, String codiceComune) {

	return false;
    }

    @Override
    public boolean isAttivaPerComuneESoftware(String modulo, String software, String codiceComune) {

	return false;
    }

    @Override
    public boolean isAttivaAndParametroEqualsToValore(String modulo, String parametro, String valore) {

	return false;
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

	return false;
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

	// TODO Auto-generated method stub
	return null;
    }
}
