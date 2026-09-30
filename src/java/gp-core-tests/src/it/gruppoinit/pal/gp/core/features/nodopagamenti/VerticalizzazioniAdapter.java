package it.gruppoinit.pal.gp.core.features.nodopagamenti;

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

public abstract class VerticalizzazioniAdapter implements VerticalizzazioniService {

    @Override
    public void insert(Verticalizzazioni entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(Verticalizzazioni entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(Verticalizzazioni entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Verticalizzazioni> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Verticalizzazioni findById(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Verticalizzazioni bindDomainObject(Verticalizzazioni entity, Class<?> idClass, String idPath) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PkId newIdFromSequencetable(Verticalizzazioni entity) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Verticalizzazioniparametri> getVerticalizzazioniparametri(String modulo) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametri(String modulo, String parametro) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComune(String modulo, String parametro, String codiceComune) {

	return null;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComuneESoftware(String modulo, String parametro, String codiceComune,
	    String software) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getVerticalizzazioniparametriValore(String modulo, String parametro) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametri(String modulo, String parametro, String codiceSoftware) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Verticalizzazioni findByModulo(String modulo) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean isAttiva(String modulo) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isAttiva(String modulo, String software) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Verticalizzazioni findByModuloEComune(String modulo, String codiceComune) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean isAttivaPerComune(String modulo, String codiceComune) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isAttivaPerComuneESoftware(String modulo, String software, String codiceComune) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isAttivaAndParametroEqualsToValore(String modulo, String parametro, String valore) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public List<Verticalizzazioni> findByVerticalizzazionibase(Verticalizzazionibase verticalizzazionibase) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<Verticalizzazioni> findByVerticalizzazionibaseAndCheckConfigurabilePerOperatore(Verticalizzazionibase verticalizzazionibase) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean isInstallazioneEnterprise() {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void resetObjectCached() {

	// TODO Auto-generated method stub
    }

    @Override
    public Map<String, String> getVerticalizzazioniparametriMap(String modulo) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Verticalizzazioni findByComuneAndModulo(Verticalizzazionibase verticalizzazionibase, String codicecomune, String software) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void insert(String modulo, String codicecomune, String software) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(String modulo, String codicecomune, String software) {

	// TODO Auto-generated method stub
    }

    @Override
    public boolean checkConfigurazioneMultiplaPerComune(String modulo, String parametro) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean checkConfigurazioneMultiplaPerSoftware(String modulo, String parametro) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public List<VerticalizzazioniconfigurazioniHelper> findListaConfigurazioniPerComuneESoftware(String modulo, String parametro) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<ChiaveValoreBean<Comuni, Software>> findComuniESoftware(String modulo, String... parametro) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<ConfigurazioneRegoleParametroHelper> findConfigurazioneComune(String modulo, String codiceComune) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean isAttivaPerQualsiasiSoftware(String modulo) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isAttivaSicurezzaSistema() {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean getBoolean(String modulo, String parametro, String valoreConfrontoTrue) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean getBoolean(String modulo, String parametro, String valoreConfrontoTrue, Boolean defaultValue) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public String getString(String modulo, String parametro) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getString(String modulo, String parametro, String defaultValue) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Date getDate(String modulo, String parametro, Date defaultValue) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Date getDate(String modulo, String parametro, String formato, Date defaultValue) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer getInteger(String modulo, String parametro) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer getInteger(String modulo, String parametro, Integer defaultValue) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public BigDecimal getBigDecimal(String modulo, String parametro) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public BigDecimal getBigDecimal(String modulo, String parametro, BigDecimal defaultValue) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<CodiceDescrizioneBean> findComuniPerRegolaEParametro(String modulo, String parametro) {

	// TODO Auto-generated method stub
	return null;
    }
}
