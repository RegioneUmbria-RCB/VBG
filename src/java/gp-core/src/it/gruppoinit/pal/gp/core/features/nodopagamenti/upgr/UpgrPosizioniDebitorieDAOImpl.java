package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.SoftwareComuneDataBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione.UpgrRiferimentiDettPosizione;

@Repository
public class UpgrPosizioniDebitorieDAOImpl extends BaseDAOImpl implements IUpgrPosizioniDebitorieDAO {

    @Override
    public Class getEntityClass() {

	return DettPosizioneDebitoria.class;
    }

    private static final String SQL_TROVA_RECORD_SENZA_COME_E_SOFTWARE = "SELECT idcomune,id FROM dett_posizione_debitoria WHERE codicecomune IS NULL AND software IS NULL ORDER BY idcomune";

    @Override
    public List<UpgrRiferimentiDettPosizione> getElencoPosizioniSenzaCodiceComuneSoftware() {

	SQLQuery q = getSession().createSQLQuery(SQL_TROVA_RECORD_SENZA_COME_E_SOFTWARE);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(UpgrRiferimentiDettPosizione.class));
	return q.list();
    }

    private static final String SQL_UPDATE_COMUNE_SOFTWARE = "update dett_posizione_debitoria set codicecomune=?,software=? where idcomune=? and id=? and codicecomune is null and software is null";

    @Override
    public void aggiornaComuneESoftwarePosizioneDebitoria(UpgrRiferimentiDettPosizione posizione, ISoftwareComuneData softwareAndcomune) {

	SQLQuery query = getSession().createSQLQuery(SQL_UPDATE_COMUNE_SOFTWARE).addSynchronizedEntityClass(DettPosizioneDebitoria.class);
	query.setString(0, softwareAndcomune.getCodiceComune());
	query.setString(1, softwareAndcomune.getSoftware());
	query.setString(2, posizione.getIdcomune());
	query.setInteger(3, posizione.getId());
	query.executeUpdate();
    }

    private static final String SQL_TROVA_COMUNE_SOFTWARE_ISTONERI = "SELECT ISTANZE.CODICECOMUNE, ISTANZE.SOFTWARE FROM istanzeoneri INNER JOIN ISTONERI_DETT_POSIZIONI ON ISTONERI_DETT_POSIZIONI.idcomune=istanzeoneri.idcomune AND ISTONERI_DETT_POSIZIONI.fk_istanzeoneri_id=istanzeoneri.id INNER JOIN istanze ON istanze.idcomune=istanzeoneri.idcomune AND istanze.codiceistanza=istanzeoneri.CODICEISTANZA WHERE ISTONERI_DETT_POSIZIONI.idcomune=? AND ISTONERI_DETT_POSIZIONI.fk_dettposdebitoria_id=?";

    @Override
    public SoftwareComuneDataBean findInfoDettaglioPosizioneDebitoria(String idcomune, Integer idDettPosizioneDebitoria) {

	SQLQuery q = getSession().createSQLQuery(SQL_TROVA_COMUNE_SOFTWARE_ISTONERI);
	q.addScalar("codicecomune", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.setString(0, idcomune);
	q.setInteger(1, idDettPosizioneDebitoria);
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(SoftwareComuneDataBean.class));
	q.setFirstResult(0);
	q.setMaxResults(1);
	List<SoftwareComuneDataBean> l = q.list();
	if (l.isEmpty()) {
	    return null;
	}
	return l.get(0);
    }

    private static final String SQL_TROVA_COMUNE_SOFTWARE_PRESENZE = "SELECT MERCATI.CODICECOMUNE, MERCATI.SOFTWARE FROM MERCATIPRESENZE_D INNER JOIN MERCATIPRESENZE_T ON MERCATIPRESENZE_T.idcomune=MERCATIPRESENZE_D.idcomune AND MERCATIPRESENZE_T.ID=MERCATIPRESENZE_D.FKIDTESTATA INNER JOIN MERCATI ON MERCATI.idcomune=MERCATIPRESENZE_T.idcomune AND MERCATI.CODICEMERCATO=MERCATIPRESENZE_T.FKCODICEMERCATO WHERE MERCATIPRESENZE_D.idcomune=? AND MERCATIPRESENZE_D.FK_PAY_POS_DEB=?";

    @Override
    public SoftwareComuneDataBean findInfoByIdDettPosizioneDebitoriaManifestazioni(String idcomune, Integer idDettPosizioneDebitoria) {

	SQLQuery q = getSession().createSQLQuery(SQL_TROVA_COMUNE_SOFTWARE_PRESENZE);
	q.addScalar("codicecomune", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.setString(0, idcomune);
	q.setInteger(1, idDettPosizioneDebitoria);
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(SoftwareComuneDataBean.class));
	q.setFirstResult(0);
	q.setMaxResults(1);
	List<SoftwareComuneDataBean> l = q.list();
	if (l.isEmpty()) {
	    return null;
	}
	return l.get(0);
    }
}
