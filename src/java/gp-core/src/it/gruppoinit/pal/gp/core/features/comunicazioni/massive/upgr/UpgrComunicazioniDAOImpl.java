package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.upgr;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.MassiveParametri;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTMassive;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni;

@SuppressWarnings("rawtypes")
@Repository
public class UpgrComunicazioniDAOImpl extends BaseDAOImpl implements IUpgrComunicazioniDAO {

    private static final String COMUNICAZIONI_SENZA_TIPOLOGIA = "select mercatipresenze_t_massive.idcomune as idcomune ,mercatipresenze_t_massive.fkid_massive_testata as codice from mercatipresenze_t_massive where not exists ( select 1 from massive_parametri where massive_parametri.idcomune = mercatipresenze_t_massive.idcomune and massive_parametri.fkid_testata = mercatipresenze_t_massive.fkid_massive_testata and chiave = ?) group by mercatipresenze_t_massive.idcomune ,mercatipresenze_t_massive.fkid_massive_testata order by mercatipresenze_t_massive.idcomune ,mercatipresenze_t_massive.fkid_massive_testata";

    @Override
    public List<PkId> cercaLeComunicazioniSenzaTipoCom() {

	SQLQuery query = getSession().createSQLQuery(COMUNICAZIONI_SENZA_TIPOLOGIA) //
		.addSynchronizedEntityClass(MercatipresenzeTMassive.class) //
		.addSynchronizedEntityClass(MassiveParametri.class);
	query.setString(0, ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE_PARAM);
	query.addScalar("codice", Hibernate.INTEGER);
	query.addScalar("idcomune", Hibernate.STRING);
	query.setResultTransformer(Transformers.aliasToBean(PkId.class));
	return query.list();
    }

    @Override
    public Class getEntityClass() {

	return null;
    }
}
