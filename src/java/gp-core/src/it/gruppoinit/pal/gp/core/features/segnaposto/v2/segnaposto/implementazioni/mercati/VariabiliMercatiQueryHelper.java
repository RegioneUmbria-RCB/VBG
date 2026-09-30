package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.mercati;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class VariabiliMercatiQueryHelper extends BaseQueryHelper {

    private Integer codiceistanza;

    public VariabiliMercatiQueryHelper(SessionFactoryImplementor sfi, Integer codiceistanza) {

	super();
	String hibernateDialect = sfi.getDialect().toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	this.codiceistanza = codiceistanza;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceistanza);
	q.setString(2, ORMHelper.getIdcomune());
	q.setInteger(3, codiceistanza);
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("progressivo", Hibernate.INTEGER);
	q.addScalar("concid", Hibernate.INTEGER);
	q.addScalar("numconcessione", Hibernate.STRING);
	q.addScalar("dataconcessione", Hibernate.DATE);
	q.addScalar("titolare", Hibernate.STRING);
	q.addScalar("tipoconcessione", Hibernate.STRING);
	q.addScalar("scadenzaconcessione", Hibernate.DATE);
	q.addScalar("stagionaleda", Hibernate.STRING);
	q.addScalar("stagionalea", Hibernate.STRING);
	q.addScalar("causaleconcessione", Hibernate.STRING);
	q.addScalar("mercato", Hibernate.STRING);
	q.addScalar("giorno", Hibernate.STRING);
	q.addScalar("numposteggio", Hibernate.STRING);
	q.addScalar("mqposteggio", Hibernate.BIG_DECIMAL);
	q.addScalar("idposteggio", Hibernate.INTEGER);
	q.addScalar("via", Hibernate.STRING);
	q.addScalar("datastorico", Hibernate.DATE);
	q.addScalar("noteposteggio", Hibernate.STRING);
    }

    private String[] titolare = new String[] { "titolare.nominativo", "titolare.nome" };
    private String[] stradario = new String[] { "stradario.prefisso", "stradario.descrizione" };

    @Override
    public String buildQuery() {

	return "select " + // 
		" 99999999 as progressivo, autorizzazioni.id as concid, autorizzazioni.autoriznumero as numconcessione, " + // 
		" autorizzazioni.autorizdata as dataconcessione, " + // 
		applyConcatFunction("' '", titolare) +
		" as titolare, " + // 
		" concessionitipi.descrizione as tipoconcessione, autorizzazioni.datascadenza as scadenzaconcessione, " + // 
		" autorizzazioni_concessioni.stagionaleda as stagionaleda, autorizzazioni_concessioni.stagionalea as stagionalea, " + // 
		" caus_attuale.descrizione as causaleconcessione, mercati.descrizione as mercato, mercati_uso.descrizione as giorno, " + // 
		" mercati_d.codiceposteggio as numposteggio, mercati_d.superficie as mqposteggio, autorizzazioni_concessioni.fk_idposteggio as idposteggio, " + // 
		applyConcatFunction("' '", stradario) +
		" as via, " + // 
		dataStorico() +
		" as datastorico, mercati_d.note as noteposteggio  " + // 
		" from " + // 
		" autorizzazioni  " + //
		" inner join autorizzazioni_concessioni on  " + //
		" autorizzazioni.idcomune = autorizzazioni_concessioni.idcomune and autorizzazioni.id = autorizzazioni_concessioni.fk_idaut_attuale " + //
		" inner join concessionitipi on " + //
		" concessionitipi.tipoconcessione = autorizzazioni_concessioni.fk_tipoconcessione  " + //
		" inner join anagrafe titolare on " + //
		" titolare.idcomune = autorizzazioni.idcomune and titolare.codiceanagrafe = autorizzazioni.fk_codiceanagrafe  " + //
		" inner join concessionicausali caus_attuale on " + //
		" caus_attuale.idcomune = autorizzazioni.idcomune and caus_attuale.codicecausale = autorizzazioni.fk_causale_acquisizione      " + //
		" inner join mercati on  " + //
		" mercati.idcomune = autorizzazioni_concessioni.idcomune and mercati.codicemercato = autorizzazioni_concessioni.fk_codicemercato  " + //
		" inner join mercati_d on " + //
		" mercati_d.idcomune = autorizzazioni_concessioni.idcomune and mercati_d.idposteggio = autorizzazioni_concessioni.fk_idposteggio   " + //
		"    inner join mercati_uso on " + //
		" mercati_uso.idcomune = autorizzazioni_concessioni.idcomune and mercati_uso.id = autorizzazioni_concessioni.fk_idmercatiuso      " + //
		" left join stradario on " + //
		" stradario.idcomune = mercati_d.idcomune and stradario.codicestradario = mercati_d.fkcodicestradario " + // 
		" where autorizzazioni.idcomune = ? and autorizzazioni.fkidistanza  = ?" + //
		" union " + // 
		" select " + // 
		" autorizzazioni_subentri.id as progressivo, autorizzazioni_subentri.fk_idaut_attuale as concid, " + // 
		" autorizzazioni_subentri.autoriznumero as numconcessione, autorizzazioni_subentri.autorizdata as dataconcessione, " + // 
		applyConcatFunction("' '", titolare) +
		" as titolare, concessionitipi.descrizione as tipoconcessione, " + // 
		" autorizzazioni_subentri.datascadenza as scadenzaconcessione, aut_sub_conc.stagionaleda as stagionaleda, " + // 
		" aut_sub_conc.stagionalea as stagionalea, caus_attuale.descrizione as causaleconcessione, " + // 
		" mercati.descrizione as mercato, mercati_uso.descrizione as giorno, mercati_d.codiceposteggio as numposteggio, " + // 
		" mercati_d.superficie as mqposteggio, aut_sub_conc.fk_idposteggio as idposteggio, " + // 
		applyConcatFunction("' '", stradario) +
		" as via, autorizzazioni_subentri.data_cessazione as datastorico, " + // 
		" mercati_d.note as noteposteggio " + // 
		" from " + // 
		" autorizzazioni_subentri  " + //
		"  inner join autorizzazioni_subentri_conc aut_sub_conc on " + //
		"   autorizzazioni_subentri.idcomune = aut_sub_conc.idcomune and autorizzazioni_subentri.id = aut_sub_conc.fk_autsub_id  " + //
		"  inner join concessionitipi on  " + //
		"   concessionitipi.tipoconcessione = aut_sub_conc.fk_tipoconcessione   " + //
		"  inner join anagrafe titolare on " + //
		"   titolare.idcomune = autorizzazioni_subentri.idcomune and titolare.codiceanagrafe = autorizzazioni_subentri.fk_codiceanagrafe " + //
		"  inner join concessionicausali caus_attuale on " + //
		"   caus_attuale.idcomune = autorizzazioni_subentri.idcomune and caus_attuale.codicecausale = autorizzazioni_subentri.fk_causale_acquisizione  " + //
		"  inner join mercati on  " + //
		"   mercati.idcomune = aut_sub_conc.idcomune and mercati.codicemercato = aut_sub_conc.fk_codicemercato  " + //
		"  inner join mercati_d on " + //
		"   mercati_d.idcomune = aut_sub_conc.idcomune and mercati_d.idposteggio = aut_sub_conc.fk_idposteggio  " + //
		"  inner join mercati_uso on  " + //
		"   mercati_uso.idcomune = aut_sub_conc.idcomune and mercati_uso.id = aut_sub_conc.fk_idmercatiuso   " + //
		"  left join stradario on " + //
		"   stradario.idcomune = mercati_d.idcomune and stradario.codicestradario = mercati_d.fkcodicestradario" + //
		" where autorizzazioni_subentri.idcomune = ? and autorizzazioni_subentri.fkidistanza = ?";
    }

    private String dataStorico() {

	// 
	switch (_dialetto) {
	    case MYSQL:
		return " COALESCE(autorizzazioni.data_cessazione, DATE_ADD(SYSDATE(), INTERVAL 100 YEAR)) ";
	    case ORACLE:
		return "  COALESCE(autorizzazioni.data_cessazione, ( SYSDATE + 36500) ) ";
	    default:
		break;
	}
	throw new NotImplementedException("Dialetto " + _dialetto + " non implementato per la funzione dataStorico()");
    }
}
