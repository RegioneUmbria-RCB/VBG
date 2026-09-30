package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.mercati;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Repository
public class ReplaceVariabiliMercatoDAOImpl extends BaseDAOImpl implements ReplaceVariabiliMercatoDAO {

    @Override
    public VariabiliMercatoResultBean sostituisciSegnaposto(Integer codiceIstanza, TipoFileEnum tipoFile) {

	VariabiliMercatoResultBean ret = new VariabiliMercatoResultBean();
	List<VariabiliMercatiBean> list = getDati(codiceIstanza);
	String numConcessione = "";
	String dataConcessione = "";
	String titolare = "";
	String tipoConcessione = "";
	String scadConcessione = "";
	String stagDaConcessione = "";
	String stagAConcessione = "";
	String causConcessione = "";
	String mercato = "";
	String giornoMercato = "";
	String numPosteggio = "";
	String mqPosteggio = "";
	String posteggioVia = "";
	String dataCessione = "";
	String posteggioNote = "";
	String precAutNum = "";
	String precAutData = "";
	String precResponsabile = "";
	String precTitolare = "";
	String precComune = "";
	String merceologie = "";
	for (VariabiliMercatiBean v : list) {
	    numConcessione = concatenaValore(v.getNumconcessione(), numConcessione);
	    dataConcessione = concatenaValoreData(v.getDataconcessione(), dataConcessione);
	    titolare = concatenaValore(v.getTitolare(), titolare);
	    tipoConcessione = concatenaValore(v.getTipoconcessione(), tipoConcessione);
	    scadConcessione = concatenaValoreData(v.getScadenzaconcessione(), scadConcessione);
	    stagDaConcessione = concatenaValore(formattaStagionale(v.getStagionaleda()), stagDaConcessione); // formattaStagionale
	    stagAConcessione = concatenaValore(formattaStagionale(v.getStagionalea()), stagAConcessione); //formattaStagionale
	    causConcessione = concatenaValore(v.getCausaleconcessione(), causConcessione);
	    mercato = concatenaValore(v.getMercato(), mercato);
	    giornoMercato = concatenaValore(v.getGiorno(), giornoMercato);
	    numPosteggio = concatenaValore(v.getNumposteggio(), numPosteggio);
	    mqPosteggio = concatenaValoreBigDecimal(v.getMqposteggio(), mqPosteggio);
	    posteggioVia = concatenaValore(v.getVia(), posteggioVia);
	    dataCessione = concatenaValoreData(v.getDatastorico(), dataCessione);
	    posteggioNote = concatenaValore(v.getNoteposteggio(), posteggioNote);
	    List<VariabiliAutprecedenteBean> autprec = getAutPrec(v);
	    if (!autprec.isEmpty()) {
		VariabiliAutprecedenteBean autPrec = autprec.get(0);
		precAutNum = concatenaValore(autPrec.getPrecautnum(), precAutNum);
		precAutData = concatenaValoreData(autPrec.getPrecautdata(), precAutData);
		precResponsabile = concatenaValore(autPrec.getPrecresponsabile(), precResponsabile);
		precTitolare = concatenaValore(autPrec.getPrectitolare(), precTitolare);
		precComune = concatenaValore(autPrec.getPreccomune(), precComune);
	    }
	    // TODO MERCEOLOGIE
	}
	ret.setNumConcessione(eliminaUltimoRitornoACapo(numConcessione, tipoFile));
	ret.setDataConcessione(eliminaUltimoRitornoACapo(dataConcessione, tipoFile));
	ret.setTitolare(eliminaUltimoRitornoACapo(titolare, tipoFile));
	ret.setTipoConcessione(eliminaUltimoRitornoACapo(tipoConcessione, tipoFile));
	ret.setScadConcessione(eliminaUltimoRitornoACapo(scadConcessione, tipoFile));
	ret.setStagDaConcessione(eliminaUltimoRitornoACapo(stagDaConcessione, tipoFile));
	ret.setStagAConcessione(eliminaUltimoRitornoACapo(stagAConcessione, tipoFile));
	ret.setCausConcessione(eliminaUltimoRitornoACapo(causConcessione, tipoFile));
	ret.setMercato(eliminaUltimoRitornoACapo(mercato, tipoFile));
	ret.setGiornoMercato(eliminaUltimoRitornoACapo(giornoMercato, tipoFile));
	ret.setNumPosteggio(eliminaUltimoRitornoACapo(numPosteggio, tipoFile));
	ret.setMqPosteggio(eliminaUltimoRitornoACapo(mqPosteggio, tipoFile));
	ret.setPosteggioVia(eliminaUltimoRitornoACapo(posteggioVia, tipoFile));
	ret.setDataCessione(eliminaUltimoRitornoACapo(dataCessione, tipoFile));
	ret.setPosteggioNote(eliminaUltimoRitornoACapo(posteggioNote, tipoFile));
	ret.setPrecAutNum(eliminaUltimoRitornoACapo(precAutNum, tipoFile));
	ret.setPrecAutData(eliminaUltimoRitornoACapo(precAutData, tipoFile));
	ret.setPrecResponsabile(eliminaUltimoRitornoACapo(precResponsabile, tipoFile));
	ret.setPrecTitolare(eliminaUltimoRitornoACapo(precTitolare, tipoFile));
	ret.setPrecComune(eliminaUltimoRitornoACapo(precComune, tipoFile));
	ret.setMerceologie(eliminaUltimoRitornoACapo(merceologie, tipoFile));
	return ret;
    }

    private String eliminaUltimoRitornoACapo(String valore, TipoFileEnum tipoFile) {

	if (StringUtils.isBlank(valore)) {
	    return valore;
	}
	if (StringUtils.defaultString(valore).indexOf(SEGNAPOSTO_RITORNO_CAPO) >= 0) {
	    valore = valore.substring(0, valore.lastIndexOf(SEGNAPOSTO_RITORNO_CAPO));
	}
	return valore.replace(SEGNAPOSTO_RITORNO_CAPO, TipoFileEnum.getRitornoACapo(tipoFile));
    }

    private String formatBigdecimal(BigDecimal bd) {

	bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
	DecimalFormat df = new DecimalFormat();
	df.setMaximumFractionDigits(2);
	df.setMinimumFractionDigits(0);
	df.setGroupingUsed(false);
	return df.format(bd).replace(".", ",");
    }

    private static final String SEGNAPOSTO_RITORNO_CAPO = "RITORNOACAPO";

    private String concatenaValoreBigDecimal(BigDecimal mqposteggio, String valorePrecedente) {

	if (mqposteggio == null) {
	    return valorePrecedente + SEGNAPOSTO_RITORNO_CAPO;
	}
	return valorePrecedente + formatBigdecimal(mqposteggio) + SEGNAPOSTO_RITORNO_CAPO;
    }

    private String concatenaValoreData(Date data, String valorePrecedente) {

	return valorePrecedente + getData(data) + SEGNAPOSTO_RITORNO_CAPO;
    }

    private String concatenaValore(String valoreDaAccodare, String valorePrecedente) {

	return valorePrecedente + StringUtils.defaultString(valoreDaAccodare) + SEGNAPOSTO_RITORNO_CAPO;
    }

    private String getData(Date data) {

	if (data != null) {
	    return Utilities.formatDate(data, false);
	}
	return "";
    }

    private List<VariabiliAutprecedenteBean> getAutPrec(VariabiliMercatiBean v) {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	VariabiliAutPrecQueryHelper hq = new VariabiliAutPrecQueryHelper(sfi, v);
	SQLQuery q = getSession().createSQLQuery(hq.buildQuery());
	hq.setFilterValues(q);
	hq.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(VariabiliAutprecedenteBean.class));
	return q.list();
    }

    private List<VariabiliMercatiBean> getDati(Integer codiceIstanza) {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	VariabiliMercatiQueryHelper hq = new VariabiliMercatiQueryHelper(sfi, codiceIstanza);
	SQLQuery q = getSession().createSQLQuery(hq.buildQuery());
	hq.setFilterValues(q);
	hq.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(VariabiliMercatiBean.class));
	return q.list();
    }

    private String formattaStagionale(String stagionale) {

	if (StringUtils.isBlank(stagionale) || stagionale.length() != 4) {
	    return stagionale;
	}
	return stagionale.substring(0, 2) + "/" + stagionale.substring(2, 4);
    }

    @Override
    public Class getEntityClass() {

	return null;
    }
}
